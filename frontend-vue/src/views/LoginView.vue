<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-xl-10 col-lg-12 col-md-9">
        <div class="card mt-5">
          <div class="card-body p-0">
            <!-- {/* Nested Row within Card Body */} -->
            <div class="row justify-content-center">
              <div class="col-lg-7">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">로그인</h1>
                  </div>
                  <!-- 사용법 : @submit.prevent="함수" -->
                  <!-- prevent : submit 의 기본 속성(다른 곳으로 이동) 막기 -->
                  <form class="user" @submit.prevent="handleLogin">
                    <div class="form-group">
                      <input
                        type="text"
                        class="form-control form-control-user mb-3"
                        placeholder="ID 입력"
                        name="memberId"
                        v-model="member.memberId"
                      />
                    </div>
                    <div class="form-group">
                      <input
                        type="password"
                        class="form-control form-control-user mb-3"
                        placeholder="패스워드 입력"
                        name="memberPw"
                        v-model="member.memberPw"
                      />
                    </div>

                    <button class="btn btn-primary btn-user w-100 mb-3">
                      로그인
                    </button>
                  </form>
                  <div v-if="errorMessage" class="alert alert-danger mt-3">
                    {{ errorMessage }}
                  </div>
                  <hr />
                  <div class="text-center">
                    <a class="small" href="/forgot-password"> 비밀번호 찾기 </a>
                  </div>
                  <div class="text-center">
                    <a class="small" href="/register"> 회원가입 </a>
                  </div>
                </div>
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
        memberCode: "",
      },
      errorMessage: "",
    };
  },
  methods: {
    // 함수 정의
    async handleLogin() {
      try {
        let response = await LoginService.login(this.member);
        console.log("ggg",response.data);
        localStorage.setItem("member", JSON.stringify(response.data));
        this.$store.commit("loginSuccess", response.data);
        console.log("확인~", response.data)
        console.log("확인", response.data.memberCode);
        if (response.data.memberCode == "AT03") {
          LoginService.logout();
          this.$store.commit("logout");
          alert("승인 대기 중입니다.");
          this.$router.push("/login");
        } else {
        this.$router.push("/");
        }
      } catch (e) {
        this.$store.commit("loginFailure");
        this.errorMessage = "로그인에 실패했습니다. 다시 시도해 주세요.";
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
<style></style>
