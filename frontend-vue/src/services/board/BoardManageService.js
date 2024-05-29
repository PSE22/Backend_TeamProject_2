import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class BoardManageService{
    // cm-code 전체조회
    getAll(){
        return http.get('/board/management', {
            headers: LoginHeader(),
        });
    }

}

export default new BoardManageService();