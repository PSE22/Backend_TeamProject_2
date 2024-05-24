package org.example.backend.service.profile;

import org.springframework.stereotype.Service;

import java.util.Random;

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

    private final Random random = new Random();

    public int generateRandomNumber() {
        return random.nextInt(999999) + 1;
    }
}
