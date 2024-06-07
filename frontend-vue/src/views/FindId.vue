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
                    <h1 class="h4 text-gray-900 mb-4">ID 찾기</h1>
                  </div>
                  <form class="user" @submit.prevent="findMember">
                    <div class="form-group">
                      <input
                        type="text"
                        class="form-control form-control-user mb-3"
                        placeholder="이메일 입력"
                        name="memberEmail"
                        v-model="memberEmail"
                      />
                    </div>

                    <button
                      class="btn btn-primary btn-user w-100 mb-3"
                    >
                      아이디 찾기
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
import MemberService from "@/services/member/MemberService";

export default {
  data() {
    return {
      member: {},
      memberEmail: "",
      message: "",
    };
  },
  methods: {
    async findMember() {
      try {
        let response = await MemberService.getId(this.memberEmail);
        this.member = response.data;
        console.log(response.data);
        this.message = this.member.memberId;
      } catch (e) {
        console.log(e);
      }
    },
  },
  mounted() {},
};
</script>

<style></style>