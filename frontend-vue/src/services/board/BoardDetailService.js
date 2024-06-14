import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class BoardDetailService {
  // 로그인된 회원 정보 조회
  getMember(memberId) {
    return http.get(`/board/board-detail/member?memberId=${memberId}`, {
      headers: LoginHeader(),
    });
  }
  // 게시글, 작성자 정보 조회
  getBoard(boardId) {
    return http.get(`/board/board-detail?boardId=${boardId}`, {
      headers: LoginHeader(),
    });
  }
  // 코드번호로 코드명 조회
  getCmCd(cmCd) {
    return http.get(`/board/board-detail/cmCd?cmCd=${cmCd}`, {
      headers: LoginHeader(),
    });
  }
  //   // 글번호로 투표 조회
  //   getVote(boardId) {
  //     return http.get(`/board/board-detail/vote?boardId=${boardId}`, {
  //       headers: LoginHeader(),
  //     });
  //   }
  // 글번호로 장소 조회 (게시글 하나당 장소 하나)
  getPlace(boardId) {
    return http.get(`/board/board-detail/place?boardId=${boardId}`, {
      headers: LoginHeader(),
    });
  }

  // 파일 조회
  getFile(uuid) {
    return http.get(`/file-download/${uuid}`, {
      headers: LoginHeader(),
    });
  }
  // 글번호로 이미지 조회
  getImg(boardId) {
    return http.get(`/board/board-detail/board-img?boardId=${boardId}`);
  }
  // 추천 데이터 존재 확인
  getRecommend(boardId, memberId) {
    return http.get(
      `/board/board-detail/recommend-exist?boardId=${boardId}&memberId=${memberId}`,
      {
        headers: LoginHeader(),
      }
    );
  }
  // 추천 데이터 저장
  createRecommend(recommend) {
    return http.post("/board/board-detail/recommend-exist", recommend, {
      headers: LoginHeader(),
    });
  }
  // 신고글 저장
  createReport(report) {
    return http.post("/board/board-detail/report", report, {
      headers: LoginHeader(),
    });
  }
  // 추천 데이터 삭제
  deleteRecommend(boardId, memberId) {
    return http.delete(
      `/board/board-detail/recommend-exist?boardId=${boardId}&memberId=${memberId}`,
      {
        headers: LoginHeader(),
      }
    );
  }
  // 추천 수 조회
  getRecommendCnt(boardId) {
    return http.get(`/board/board-detail/recommend-count?boardId=${boardId}`, {
      headers: LoginHeader(),
    });
  }
  // 게시글 삭제
  deleteBoard(boardId) {
    return http.delete(`/board/board-detail/delete/${boardId}`, {
      headers: LoginHeader(),
    });
  }
}

export default new BoardDetailService();
