package org.example.backend.repository.profile;

import org.example.backend.model.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName : org.example.backend.repository.profile
 * fileName : NotificationRepository
 * author : kimtaewan
 * date : 2024-05-22
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-22         kimtaewan          최초 생성
 */
@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {
    List<Notify> findByMemberId(String memberId);
}
