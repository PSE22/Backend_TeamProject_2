<template>
  <div align="center" class="mt-5" to="login">
    <h2>로그인</h2>
  </div>
  <div class="container mt-5 login-box">
    <div align="center" class="mb-3">
      <div class="logo">
        <h2>서울쥐</h2>
      </div>
    </div>
    <div class="mb-1">
      <!-- 아이디 박스 -->
      <label for="text" class="form-label"></label>
      <input
        type="text"
        class="form-control"
        placeholder="아이디"
        name="text"
        v-model="user.userId"
      />
      <!-- 비밀번호 박스 -->
    </div>
    <div class="mb-3">
      <label for="password" class="form-label"></label>
      <input
        type="password"
        class="form-control"
        placeholder="비밀번호"
        name="password"
        v-model="user.userPw"
      />
    </div>
    <!-- 로그인 박스 -->
    <div align="center">
      <button
        type="button"
        class="btn btn-primary me-auto col-12"
        @click="handleLogin"
      >
        로그인
      </button>
    </div>

    <!-- 회원 가입 -->
    <nav class="mt-3 mb-3" align="center">
      <router-link align="right" to="/signup">회원가입</router-link>
    </nav>
    <router-view />
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
      },
    };
  },
  methods: {
    // 함수 정의
    async handleLogin() {
      try {
        let response = await LoginService.login(this.member);
        console.log(response.data);
        localStorage.setItem("member", JSON.stringify(response.data));
        this.$store.commit("loginSuccess", response.data);
        this.$router.push("/");
      } catch (e) {
        this.$store.commit("loginFailure");   
        console.log(e); 
      }
    },
  },

  // TODO: 화면이 뜰때 실행되는 함수
  mounted() {
    if (this.$store.state.loggedIn == true) {
      this.$router.push("/");
    }
  },
};
</script>
<style>
.login-box {
  margin: 150px auto;
  background-color: rgba(255, 255, 255, 1);
  padding: 40px 30px;
  border: 3px solid #505050;
  width: 500px;
  height: 400px;
}

.social-container {
  margin: 30px 0;
}
.social-container a {
  background-color: rgb(206, 206, 206);
  border-radius: 50%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin: 0 20px;
  height: 60px;
  width: 60px;
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
