import http from "@/utils/http-common";
import LoginHeader from "../login/LoginHeader";

class BoardManageService{
    // cm-code 전체조회
    getAll(){
        return http.get('/admin/board-mgmt', {
            headers: LoginHeader(),
        });
    }

    // 게시판 추가
    postBoard(cmCd, cmCdName, cmCdComment, upCmCd) {
        return http.post('/admin/add', {
            cmCd: cmCd,
            cmCdName: cmCdName,
            cmCdComment: cmCdComment,
            upCmCd: upCmCd
        }, {
            headers: LoginHeader(),
        });
    }
    
        // 게시판 수정
        updateBoard(cmCd, cmCdName, cmCdComment, upCmCd) {
            return http.put('/admin/update', {
                cmCd: cmCd,
                cmCdName: cmCdName,
                cmCdComment: cmCdComment,
                upCmCd: upCmCd
            }, {
                headers: LoginHeader(),
            });
        }
}

export default new BoardManageService();