import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class BoardWrite {
  // 게시물 저장
  create(board, vote, place, fileDtos, boardFileDtos) {
    console.log("저장되나", board, vote, place, fileDtos, boardFileDtos)
    return http.post("/board-write", board, vote, place, fileDtos, boardFileDtos, {
        headers: LoginHeader(),
    });
  }
}

export default new BoardWrite();