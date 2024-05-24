import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class FreeBoardService {
    // 전체조회
    getAll(boardTitle, page, size) {
        return http.get(`/board/free?boardTitle=${boardTitle}&page=${page}&size=${size}`,{
            headers: LoginHeader()
        });
    }
    // 상세조회
    get(boardId) {
        return http.get(`/board/free/${boardId}`,{
            headers: LoginHeader()
        });
    }
    // 게시글등록함수
    create(data) {
        return http.post("/board/free", data,{
            headers: LoginHeader()
        });
    }
    // 게시글수정함수
    update(boardId, data) {
        return http.put(`/board/free/${boardId}`, data,{
            headers: LoginHeader()
        });
    }
    // 게시글 삭제함수
    delete(boardId) {
        return http.delete(`/board/free/deletion/${boardId}`,{
            headers: LoginHeader()
        });
    }
}

export default new FreeBoardService();