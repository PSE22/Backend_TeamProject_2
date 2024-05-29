import http from "@/utils/http-common";

class MemberService {
    // 회원 상세 조회
    get(memberId) {
        return http.get(`/member/profile/${memberId}`);
    }

    // 회원 정보 수정
    update(memberId, data) {
        return http.put(`/member/profile-edit/${memberId}`, data);
    }
}

export default new MemberService();