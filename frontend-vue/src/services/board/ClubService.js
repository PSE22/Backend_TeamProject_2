import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class ClubService {
  // 전체조회 공통함수
  getAll(code, page, size) {
    return http.get(`/board/club?code=${code}&page=${page}&size=${size}`, {
      headers: LoginHeader(),
    });
  }
  // 전체조회 공통함수
  getNoticeFive(code) {
    return http.get(`/board/club-notice?code=${code}`, {
      headers: LoginHeader(),
    });
  }
}

export default new ClubService();
