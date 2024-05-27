import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class ClubService {
  // 동호회 전체조회
  getAll(boardTitle, page, size) {
    return http.get(
      `/board/club?boardTitle=${boardTitle}&page=${page}&size=${size}`,
      {
        headers: LoginHeader(),
      }
    );
  }
  // 공지사항 전체조회
  getNoticeFive() {
    return http.get(`/board/club-notice`, {
      headers: LoginHeader(),
    });
  }
  // 중메뉴
  getSmcode() {
    return http.get(`/board/club-smcode`, {
      headers: LoginHeader(),
    });
  }
}

export default new ClubService();
