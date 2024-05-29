import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class ClubService {
  // 동호회 전체조회
  getAll(boardTitle, bocode, page, size) {
    return http.get(
      `/board/club?boardTitle=${boardTitle}&bocode=${bocode}&page=${page}&size=${size}`,
      {
        headers: LoginHeader(),
      }
    );
  }
  // 공지사항 전체조회
  getNotice(bocode) {
    return http.get(`/board/club-notice?bocode=${bocode}`, {
      headers: LoginHeader(),
    });
  }
  // 중메뉴
  getSmcode() {
    return http.get(`/board/club-smcode`, {
      headers: LoginHeader(),
    });
  }
  // 중메뉴
  get(boardId) {
    return http.get(`/board/club/${boardId}`, {
      headers: LoginHeader(),
    });
  }
}

export default new ClubService();
