import http from "@/utils/http-common";

class MemberService {
    // 회원 상세 조회
    get(memberId) {
        return http.get(`/member/profile/${memberId}`);
    }

    // 신규 회원 전체 조회
    getMember(memberCode) {
        return http.get(`/member/profile-all/${memberCode}`);
    }

    // 기존 회원 부서별 전체 조회
    getMemberOfDept(memberCode, deptCode) {
        return http.get(`/member/profile-all/old/${memberCode}/${deptCode}`);
    }

    // 회원정보 수정
    updateProfile(data) {
        return http.put(`/member/profile-edit`, data);
    }

    // 회원정보 삭제
    deleteProfile(memberId) {
        return http.delete(`/member/profile/deletion/${memberId}`);
    }

    // 회원정보 삭제(하드)
    hardDeleteProfile(memberId) {
        return http.delete(`/member/profile/hard-deletion/${memberId}`);
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