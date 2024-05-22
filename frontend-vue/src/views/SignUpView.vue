<template>
  <div align="center" class="mt-5">
    <h2>회원가입</h2>
  </div>
  <div class="container mt-5 signup-box">
    <div align="center">
      <div class="logo">
        <h2>서울쥐</h2>
      </div>
    </div>
    <!-- 회원가입 폼 -->
    <form class="user" @submit.prevent="handleSignUp">
      <!-- 아이디 -->
      <div align="center">
        <div class="row">
          <div class="col-9">
            <label for="memberId"></label>
            <input
              id="inputId"
              type="text"
              class="form-control"
              placeholder="아이디"
              name="memberId"
              v-model="member.memberId"
            />
          </div>

          <div class="col align-self-end">
            <div align="right">
              <button
                type="button"
                id="idcheck"
                class="btn btn-secondary"
                @click="heckMemberId"
              >
                중복확인
              </button>
            </div>
          </div>
        </div>
      </div>
      <!-- 비밀번호 -->
      <div align="center">
        <div class="row">
          <div class="form-group col">
            <label for="memberPw"></label>
            <input
              type="password"
              class="form-control"
              placeholder="비밀번호"
              name="memberPw"
              v-model="member.memberPw"
            />
          </div>
        </div>
      </div>

      <div align="center">
        <div class="row">
          <div class="form-group col">
            <label for="memberPw"></label>
            <input
              type="password"
              class="form-control"
              placeholder="비밀번호 재확인"
              name="memberPw"
              v-model="member.rePw"
            />
          </div>
        </div>
      </div>
      <!-- 비밀번호 일치 여부를 표시하는 메시지 -->
      <div v-if="!isPasswordMatch">비밀번호가 일치하지 않습니다.</div>

      <!-- 이름 -->
      <div align="center">
        <div class="row">
          <div class="form-group col">
            <label for="memberName"></label>
            <input
              type="text"
              class="form-control"
              placeholder="이름"
              name="memberName"
              v-model="member.memberName"
            />
          </div>
        </div>
      </div>


      <!-- 번호 -->
      <div align="center">
        <div class="row">
          <div class="col">
            <label for="memberExt"></label>
            <input
              type="text"
              class="form-control"
              placeholder="번호"
              name="memberExt"
              v-model="member.memberExt"
            />
          </div>
        </div>
      </div>

      <br />
      <!-- 회원분류 -->
      회원 :
      <div class="form-check form-check-inline mt-3">
        <input
          class="form-check-input"
          type="radio"
          name="memberCode"
          value="AT02"
          v-model="member.memberCode"
        />
        <label class="form-check-label" for="memberCode">일반회원</label>
      </div>

      <!-- 회원가입 -->
      <br />
      <div align="center">
        <button
          type="button"
          class="btn btn-primary btn-user mt-3 col-12"
          @click="handleSignUp"
        >
          회원가입
        </button>
      </div>
      <router-view />
    </form>
    <p v-if="message" class="alert alert-success" role="alert">
      {{ message }}
    </p>
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
        memberExt: "",
        memberName: "",
        memberCode: "",
        deptCode: "DE01",
      },
      message: "",
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
      this.failMessage = "";
      this.successMessage = "";
      try {
        this.successMessage = "";
        let response = await LoginService.reId(this.member.memberId);
        alert("사용 가능한 아이디입니다.");
        return response.data;
      } catch (e) {
        this.failMessage = "";
        alert("중복된 아이디 입니다.");
        console.log(e);
      }
    },
    async handleSignUp() {
      this.message = "";
      try {
        // TODO: 공통 사용자등록 서비스 함수 실행
        let response = await LoginService.signup(this.member);
        this.$store.commit("signUpSuccess");
        this.message = "사용자가 등록되었습니다.";
        alert("회원가입이 완료되었습니다.");
        this.$router.push("/login");
        console.log(response.data);
      } catch (e) {
        this.$store.commit("signupFailure");
        this.message = "에러 : " + e;
        console.log(e);
      }
    },
  },
  computed: {
    isPasswordMatch() {
      return this.member.memberPw === this.member.rePw;
    },
  },
};
</script>
<style>
.signup-box {
  margin: 100px auto;
  background-color: rgba(255, 255, 255, 1);
  padding: 40px 30px;
  border: 3px solid #505050;
  width: 600px;
  height: 750px;
}
.a1 {
  position: relative;
}
.a {
  position: absolute;
  top: 50%;
}

@font-face {
  font-family: "YClover-Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_231029@1.1/YClover-Bold.woff2")
    format("woff2");
  font-weight: 700;
  font-style: normal;
}

.logo {
  /* background-color: rgb(115, 235, 67); */
  font-size: 30px;
  font-family: "YClover-Bold";
}
</style>
