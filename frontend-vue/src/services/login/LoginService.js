import http from "@/utils/http-common";

class LoginService {
    // TODO: 로그인 공통함수
    login(member) {
        let data = {
            memberId: member.memberId,
            memberPw: member.memberPw,
        };
        return http.post("/auth/login", data);
    }

    // TODO: 로그아웃 공통함수
    logout() {
        localStorage.removeItem("member");
    }

    // TODO: 회원가입 공통함수
    signup(member) {
        let data = {
            memberId: member.memberId,
            memberPw: member.memberPw,
            rePw: member.memberPw,
            memberExt: member.memberExt,
            memberName: member.memberName,
            memberCode: member.memberCode,
            deptCode: member.deptCode,
        }
        console.log("뭐세요?:::: ", data);
        return http.post("/auth/signup", data);
    }

    // TODO: 아이디 중복확인 함수
    reId(memberId) {
        let data = {
            memberId: memberId
        };
        return http.get(`/auth/signup/${memberId}?memberId=${memberId}`, data);
    }
}

export default new LoginService();