import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class ClubService {
  // 전체조회 공통함수
  getAll(boardTitle, page, size) {
    return http.get(`/board/club?boardTitle=${boardTitle}&page=${page}&size=${size}`, {
      headers: LoginHeader(),
    });
  }
  // 전체조회 공통함수
  getNoticeFive() {
    return http.get(`/board/club-notice`, {
      headers: LoginHeader(),
    });
  }
}

export default new ClubService();
