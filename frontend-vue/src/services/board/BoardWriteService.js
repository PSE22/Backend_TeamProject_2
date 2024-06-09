import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class BoardWrite {
  // 게시물 저장
  create(boardDto, voteDtos, fileDtos, placeDto) {
    console.log("저장되나", boardDto, voteDtos, fileDtos, placeDto )
    return http.post("/board/board-write", boardDto, voteDtos, fileDtos, placeDto, {
        headers: LoginHeader(),
    });
  }
}

export default new BoardWrite();