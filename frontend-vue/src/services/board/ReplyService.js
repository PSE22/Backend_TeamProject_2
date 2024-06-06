import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class ReplyService {
  // 글번호로 댓글 조회
  getReply(boardId) {
    return http.get(`/board/board-detail/reply?boardId=${boardId}`, {
      headers: LoginHeader(),
    });
  }
  // 대댓글 조회
  getReReply(boardId, replyId) {
    return http.get(`/board/board-detail/re-reply?boardId=${boardId}&replyId=${replyId}`, {
        headers: LoginHeader(),
      });
  }
  // 댓글 수 조회
  getReplyCount(boardId) {
    return http.get(`/board/board-detail/reply/count?boardId=${boardId}`, {
      headers: LoginHeader(),
    });
  }
  // 새 댓글 등록
  createReply(data) {
    return http.post(`/board/board-detail/reply`, data, {
      headers: LoginHeader(),
    });
  }
}

export default new ReplyService();
