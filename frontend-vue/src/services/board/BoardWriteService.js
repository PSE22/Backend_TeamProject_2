import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class BoardWrite {
  // 게시물 저장
  create(board, vote, place, files) {
    console.log("저장되나", board, vote, place, files)
    return http.post("/board-write", board, vote, place, files, {
        headers: LoginHeader(),
    });
  }
}

export default new BoardWrite();