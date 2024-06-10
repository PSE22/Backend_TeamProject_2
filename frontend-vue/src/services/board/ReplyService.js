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
  // 댓글 등록
  createReply(data) {
    return http.post(`/board/board-detail/reply`, data, {
      headers: LoginHeader(),
    });
  }
  // 댓글 수정
  updateReply(replyId, data) {
    return http.put(`/board/board-detail/reply?replyId=${replyId}`, data, {
      headers: LoginHeader(),
    });
  }
  // 신고 댓글 저장
  createReplyReport(report) {
    return http.post("/board/board-detail/reply-report", report, {
      headers: LoginHeader(),
    });
  }

  // 댓글 파일 저장
  createReplyFile(image) {
    let formData = new FormData(); // form 객체
    formData.append("image", image); // 이미지

    return http.post("/board/board-detail/file/upload", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  }

}

export default new ReplyService();
