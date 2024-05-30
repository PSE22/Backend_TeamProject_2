import http from "@/utils/http-common";

class MemberService {
    // 회원 상세 조회
    get(memberId) {
        return http.get(`/member/profile/${memberId}`);
    }

    // 회원 전체 조회
    getMember() {
        return http.get(`/member/profile`);
    }

    // 회원정보 수정
    updateProfile(data) {
        return http.put(`/member/profile-edit`, data);
    }

    // 비밀번호 변경
    updatePassword(data) {
        return http.put(`/member/profile-edit/password`, data);
    }

    // 활동내역 조회
    getActivity(memberId) {
        return http.get(`/member/profile-activity/${memberId}`);
    }

    // 본인 게시글 전체 조회
    getAllPost(memberId, page, size) {
        return http.get(`/member/activity/${memberId}?page=${page}&size=${size}`);
    }
}

export default new MemberService();