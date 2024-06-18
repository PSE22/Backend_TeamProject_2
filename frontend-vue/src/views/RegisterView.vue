<template>
  <div>
    <div class="card mt-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row justify-content-center">
          <div class="col-lg-7">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">회원가입</h1>
              </div>
              <form class="user" @submit.prevent="handleRegister">
                <div class="form-group row">
                  <div class="col-sm-9 mb-3 mb-sm-0">
                    <input
                      type="text"
                      class="form-control form-control-user mb-3"
                      placeholder="아이디 입력"
                      name="memberId"
                      v-model="member.memberId"
                    />
                  </div>
                  <div class="col-sm-3 mb-3 mb-sm-0" align="right">
                    <input
                      type="button"
                      id="idcheck"
                      class="btn btn-secondary"
                      @click="heckMemberId"
                      value="중복확인"
                    >
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input
                      type="password"
                      class="form-control form-control-user mb-3"
                      placeholder="비밀번호 입력"
                      name="memberPw"
                      v-model="member.memberPw"
                    />
                  </div>
                  <div class="col-sm-6">
                    <input
                      type="password"
                      class="form-control form-control-user mb-3"
                      id="exampleRepeatPassword"
                      placeholder="비밀번호 재확인"
                      name="rePw"
                      v-model="member.rePw"
                    />
                  </div>
                  <div v-if="!isPasswordMatch">
                    비밀번호가 일치하지 않습니다.
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control form-control-user mb-3"
                      placeholder="이름 입력"
                      name="memberName"
                      v-model="member.memberName"
                    />
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control form-control-user mb-3"
                      placeholder="이메일 입력"
                      name="memberEmail"
                      v-model="member.memberEmail"
                    />
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control form-control-user mb-3"
                      placeholder="번호 입력"
                      name="memberExt"
                      v-model="member.memberExt"
                    />
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control form-control-user mb-3"
                      placeholder="닉네임 입력"
                      name="nickname"
                      v-model="member.nickname"
                    />
                  </div>
                  <div>
                    <p>부서분류</p>
                  </div>
                  <div class="form-group">
                    <select
                      class="form-select form-control-select form-select-sm mb-3"
                      name="deptCode"
                      v-model="member.deptCode"
                    >
                      <option value="DE01">영업</option>
                      <option value="DE02">인사</option>
                      <option value="DE03">행정</option>
                      <option value="DE04">보안</option>
                    </select>
                  </div>
                  <div>
                    <p>직급분류</p>
                  </div>
                  <div class="form-group">
                    <select
                      class="form-select form-control-select form-select-sm mb-3"
                      name="deptCode"
                      v-model="member.posCode"
                    >
                      <option value="PO01">사원</option>
                      <option value="PO02">주임</option>
                      <option value="PO03">대리</option>
                      <option value="PO04">과장</option>
                    </select>
                  </div>
                </div>
                <button
                  type="submit"
                  class="btn btn-primary btn-user w-100 mb-3"
                >
                  등록
                </button>
              </form>
              <p v-if="message" class="alert alert-primary" role="alert">
                {{ message }}
              </p>
              <p v-if="errorMessage" class="alert alert-danger" role="alert">
                {{ errorMessage }}
              </p>
              <hr />
              <div class="text-center">
                <a href="/login"> 로그인 </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import LoginService from "@/services/login/LoginService";

export default {
  data() {
    return {
      member: {
        memberId: "",
        memberPw: "",
        rePw: "",
        memberName: "",
        memberEmail: "",
        memberExt: "",
        nickname: "",
        memberCode: "AT03",
        deptCode: "DE01",
        posCode: "PO01",
      },
      message: "",
      errorMessage: ""
    };
  },
  // TODO: 함수 정의
  methods: {
    async heckMemberId() {
      var memberId = this.member.memberId.trim();
      if (memberId.length < 8 || !/^[a-zA-Z0-9]+$/.test(memberId)) {
        alert("아이디는 영문자와 숫자 조합의 8자리 이상이어야 합니다.");
        return;
      }
      try {
        this.successMessage = "";
        let response = await LoginService.reId(this.member.memberId);
        this.message = "사용가능한 ID 입니다.";
        return response.data;
      } catch (e) {
        this.message = "중복된 ID 입니다.";
        console.log(e);
      }
    },
    async handleRegister() {
      this.message = ""; // 성공 메세지 초기화
      try {
        let temp = {
          memberId: this.member.memberId,
          memberPw: this.member.memberPw,
          memberExt: this.member.memberExt,
          memberName: this.member.memberName,
          memberEmail: this.member.memberEmail,
          memberCode: this.member.memberCode,
          deptCode: this.member.deptCode,
          posCode: this.member.posCode,
          nickname: this.member.nickname,
        };
        // TODO: 공통 사용자 등록 서비스 함수 실행
        let response = await LoginService.register(temp);
        // 공유 저장소의 register 성공 함수 실행
        this.$store.commit("registerSuccess");
        this.message = "사용자가 등록되었습니다";
        this.$router.push("/login");
        // 로깅
        console.log(response.data);
      } catch (e) {
        // 공유 저장소의 register 실패 함수 실행
        // this.$store.commit("registerFailure");
        this.message = "";
        this.errorMessage = this.handleAxiosError(e);
        console.log(e); // 에러 출력
      }
    },
    handleAxiosError(error) {
      if (error.response) {
        return error.response.data;
      }
    },
  },
  computed: {
    isPasswordMatch() {
      return this.member.memberPw === this.member.rePw;
    },
  },
  mounted() {
    // 회원가입 => 로그인 상태이면 회원이므로 가입 불필요
    if (this.$store.state.loggedIn == true) {
      this.$router.push("/"); // 홈으로 강제 이동
    }
  },
};
</script>
<style></style>
