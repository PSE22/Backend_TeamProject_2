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
                    <h1 class="h4 text-gray-900 mb-4">비밀번호 찾기</h1>
                  </div>
                  <!-- 사용법 : @submit.prevent="함수" -->
                  <!-- prevent : submit 의 기본 속성(다른 곳으로 이동) 막기 -->
                  <form class="user" @submit.prevent="findPassword">
                    <div class="form-group">
                      <input
                        type="text"
                        class="form-control form-control-user mb-3"
                        placeholder="ID 입력"
                        name="memberId"
                        v-model="memberId"
                        @blur="findMember"
                      />
                    </div>
                    <div class="form-group">
                      <input
                        type="text"
                        class="form-control form-control-user mb-3"
                        placeholder="이메일 입력"
                        name="memberEmail"
                        v-model="to"
                        @keyup.enter="findPassword"
                      />
                    </div>

                    <button class="btn btn-primary btn-user w-100 mb-3">
                      임시 비밀번호 발급
                    </button>
                  </form>
                  <div v-if="message" class="alert alert-success mt-3">
                    {{ message }}
                  </div>
                  <hr />
                  <div class="text-center">
                    <a class="small" href="/login"> 로그인 </a>
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
import MemberService from "@/services/member/MemberService";

export default {
  data() {
    return {
        member: {},
        memberId: "",
        to: "",
        message: ""
    };
  },
  methods: {
    async findMember() {
      try {
        let response = await MemberService.get(this.memberId);
        this.member = response.data;
        console.log(response.data);
      } catch (e) {
        console.log(e);
      }
    },

    async findPassword() {
      try {
        await LoginService.find(this.to, this.memberId);
        if (this.to === this.member.memberEmail) {
          this.message = "임시 비밀번호가 발급되었습니다."
          console.log(this.message);
        } else if (this.to !== this.member.memberEmail) {
          this.message = "회원정보가 일치하지 않습니다."
          console.log(this.message);
        } 
      } catch (e) {
        console.log(e);
      }
    },
  },
  mounted() {},
};
</script>
<style></style>
