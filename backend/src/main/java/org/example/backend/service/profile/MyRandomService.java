package org.example.backend.service.profile;

import org.example.backend.model.entity.auth.Member;
import org.example.backend.repository.auth.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * packageName : org.example.backend.service.profile
 * fileName : MyRandomService
 * author : sjuk2
 * date : 2024-05-24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-24         sjuk2          최초 생성
 */
@Service
public class MyRandomService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static SecureRandom random = new SecureRandom();
    private final ConcurrentHashMap<String, LockAndRequestTime> memberLocks = new ConcurrentHashMap<>();

    public String generatePassword(String memberId) {
        LockAndRequestTime lockAndRequestTime = memberLocks.compute(memberId, (k, v) -> {
            if (v == null || v.isRequestExpired()) {
                return new LockAndRequestTime(new ReentrantLock(), LocalDateTime.now());
            } else {
                return v;
            }
        });

        Lock lock = lockAndRequestTime.getLock();
        if (!lock.tryLock()) {
            Duration remainingTime = Duration.between(LocalDateTime.now(), lockAndRequestTime.getRequestTime().plusMinutes(1));
            throw new IllegalStateException("이미 임시 비밀번호가 이메일로 발송되었습니다.\n" + remainingTime.toSeconds() + "초 후에 재발급 가능합니다.");
        }

        try {
            StringBuilder sb = new StringBuilder(10);
            for (int i = 0; i < 10; i++) {
                int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
                char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
                sb.append(rndChar);
            }

            Optional<Member> optionalMember = memberRepository.findById(memberId);
            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                String encodedPassword = passwordEncoder.encode(sb.toString());
                member.setMemberPw(encodedPassword);
                memberRepository.save(member);

                lockAndRequestTime.setRequestTime(LocalDateTime.now());
            }

            return sb.toString();
        } finally {
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            lock.unlock();
        }
    }

    private static class LockAndRequestTime {
        private final Lock lock;
        private LocalDateTime requestTime;

        public LockAndRequestTime(Lock lock, LocalDateTime requestTime) {
            this.lock = lock;
            this.requestTime = requestTime;
        }

        public Lock getLock() {
            return lock;
        }

        public LocalDateTime getRequestTime() {
            return requestTime;
        }

        public void setRequestTime(LocalDateTime requestTime) {
            this.requestTime = requestTime;
        }

        public boolean isRequestExpired() {
            return LocalDateTime.now().isAfter(requestTime.plusMinutes(1));
        }
    }
}
