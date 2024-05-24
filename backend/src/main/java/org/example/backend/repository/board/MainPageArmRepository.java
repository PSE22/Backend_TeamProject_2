package org.example.backend.repository.board;

import org.example.backend.model.dto.MainPageArmDto;
import org.example.backend.model.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName : org.example.backend.repository.board
 * fileName : MainPageArmRepository
 * author : GGG
 * date : 2024-05-24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-24         GGG          최초 생성
 */
@Repository
public interface MainPageArmRepository extends JpaRepository<Notify, Integer> {
    @Query(value = "SELECT n.NOTIFY_ID AS notifyId, " +
            "       n.MEMBER_ID AS memberId, " +
            "       n.NOTI_CONTENT AS notiContent, " +
            "       n.NOTI_URL AS notiUrl, " +
            "       n.ADD_DATE AS addDate, " +
            "       n.NOTI_CHECK AS notiCheck " +
            "FROM TB_NOTIFY n " +
            "JOIN TB_MEMBER m ON n.MEMBER_ID = m.MEMBER_ID " +
            "WHERE n.MEMBER_ID = :memberId " +
            "AND n.NOTI_CHECK = 'N' " +
            "ORDER BY n.ADD_DATE DESC", nativeQuery = true)
    List<MainPageArmDto> NotiByMemberId(@Param("memberId") String memberId);


    @Query("SELECT COUNT(n) FROM Notify n WHERE n.memberId = :memberId AND n.notiCheck = 'N'")
    int CountNotiByMemberId(@Param("memberId") String memberId);
}
