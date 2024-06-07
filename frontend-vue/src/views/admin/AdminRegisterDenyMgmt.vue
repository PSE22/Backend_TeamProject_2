<template>
  <div class="container">
    <AdminSidebar/>
    <div class="main-content">
      <div class="row">
        <table class="table">
          <thead class="table-light text-center">
            <tr>
              <th scope="col">ID</th>
              <th scope="col">이름</th>
              <th scope="col">이메일</th>
              <th scope="col">번호</th>
              <th scope="col">닉네임</th>
              <th scope="col">부서</th>
              <th scope="col">직급</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody class="table-group-divider align-middle">
            <tr v-for="(data, index) in member" :key="index">
              <td class="col-1 text-center">{{ data.memberId }}</td>
              <td class="col-1 text-center">{{ data.memberName }}</td>
              <td class="col-1 text-center">{{ data.memberEmail }}</td>
              <td class="col-1 text-center">{{ data.memberExt }}</td>
              <td class="col-1 text-center">{{ data.nickname }}</td>
              <td class="col-1 text-center">{{ deptName(data.deptCode) }}</td>
              <td class="col-1 text-center">{{ posName(data.posCode) }}</td>
              <td class="col-1 text-center">
                <div class="button-container">
                  <button
                    type="button"
                    class="btn btn-primary"
                    data-bs-toggle="modal"
                    :data-bs-target="'#editModal-' + index"
                  >
                  <span class="horizontal-text">수정</span>
                  </button>
                  <button class="delete-button" @click="registerDelete(data)">
                    <span class="horizontal-text">삭제</span>
                  </button>
                </div>
                <!-- Modal -->
                <div
                  class="modal fade"
                  :id="'editModal-' + index"
                  tabindex="-1"
                >
                  <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h1 class="modal-title fs-5" id="modalLabel">
                          회원정보 수정
                        </h1>
                        <button
                          type="button"
                          class="btn-close"
                          data-bs-dismiss="modal"
                        ></button>
                      </div>
                      <div class="modal-body">
                        <h3 class="fs-5 mb-2">ID</h3>
                        <input
                          class="form-control mb-3"
                          v-model="data.memberId"
                        />
                        <h3 class="fs-5 mb-2">이름</h3>
                        <input
                          class="form-control mb-3"
                          v-model="data.memberName"
                        />
                        <h3 class="fs-5 mb-2">이메일</h3>
                        <input
                          class="form-control mb-3"
                          v-model="data.memberEmail"
                        />
                        <h3 class="fs-5 mb-2">번호</h3>
                        <input
                          class="form-control mb-3"
                          v-model="data.memberExt"
                        />
                        <h3 class="fs-5 mb-2">닉네임</h3>
                        <input
                          class="form-control mb-3"
                          v-model="data.nickname"
                        />
                        <h3 class="fs-5 mb-2">부서</h3>
                        <select
                          class="form-select mb-3"
                          v-model="data.deptCode"
                        >
                          <option value="DE01">영업팀</option>
                          <option value="DE02">인사팀</option>
                          <option value="DE03">행정팀</option>
                          <option value="DE04">보안팀</option>
                        </select>
                        <h3 class="fs-5 mb-2">직급</h3>
                        <select
                          class="form-select mb-3"
                          v-model="data.posCode"
                        >
                          <option value="PO01">사원</option>
                          <option value="PO02">주임</option>
                          <option value="PO03">대리</option>
                          <option value="PO04">과장</option>
                        </select>
                      </div>
                      <div class="modal-footer">
                        <button
                          type="button"
                          class="btn btn-secondary"
                          data-bs-dismiss="modal"
                        >
                          닫기
                        </button>
                        <button
                          type="submit"
                          class="btn btn-primary"
                          @click="registerModify(data)"
                        >
                          등록
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import MemberService from "@/services/member/MemberService";
import AdminSidebar from "@/components/common/AdminSidebar.vue";

export default {
  components: {
    AdminSidebar
  },
  data() {
    return {
      loginMember: {},
      member: [],
    };
  },
  methods: {
    async getProfile() {
      try {
        let response = await MemberService.get(
          this.$store.state.member?.memberId
        );
        this.loginMember = response.data;
        console.log(response.data);
      } catch (e) {
        console.log(e);
      }
    },

    async getAllProfile() {
      try {
        let response = await MemberService.getMember("AT04");
        this.member = response.data;
        console.log(response.data);
      } catch (e) {
        console.log(e);
      }
    },

    async registerModify(data) {
      try {
        data.memberCode = "AT02";
        let response = await MemberService.updateProfile(data);
        console.log(response.data);
        alert("회원정보가 변경되었습니다.");
      } catch (e) {
        console.log(e);
      }
    },
    async registerDelete(data) {
      let response = await MemberService.hardDeleteProfile(data.memberId);
      console.log(response.data);
      alert("회원 삭제 처리되었습니다.");
      this.getAllProfile();
    },

    deptName(deptCode) {
      if (deptCode === "DE01") {
        return "영업팀";
      } else if (deptCode === "DE02") {
        return "인사팀";
      } else if (deptCode === "DE03") {
        return "행정팀";
      } else if (deptCode === "DE04") {
        return "보안팀";
      } else {
        return deptCode;
      }
    },
    posName(posCode) {
      if (posCode === "PO01") {
        return "사원";
      } else if (posCode === "PO02") {
        return "주임";
      } else if (posCode === "PO03") {
        return "대리";
      } else if (posCode === "PO04") {
        return "과장";
      } else {
        return posCode;
      }
    },
  },
  mounted() {
    this.getProfile();
    this.getAllProfile();
  },
};
</script>

<style scoped>
.container {
  display: flex;
  height: 100vh;
  font-family: "Arial, sans-serif";
}

.profile-link {
  color: white;
  text-decoration: none;
}

.profile-main {
  color: black;
  text-decoration: none;
}

.sidebar {
  width: 220px;
  background: #b3000f;
  color: white;
  padding: 30px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 15px;
}

.sidebar-title {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 20px;
}

.sidebar-divider {
  border: 0;
  height: 1px;
  background: white;
  margin: 20px 0;
}

.sidebar-list {
  list-style: none;
  padding: 0;
  width: 100%;
}

.sidebar-menu {
  font-size: 18px;
  padding: 10px;
  cursor: pointer;
  transition: background 0.3s;
}

.sidebar-menu:hover {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 10px;
}

.main-content {
  flex-grow: 1;
  padding: 40px;
  background-color: #f4f4f9;
  border-radius: 15px;
  margin: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

button {
  color: white;
  border: none;
  padding: 10px 20px;
  margin: 5px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s ease;
}

.delete-button {
  background-color: #f44336;
}

.delete-button:hover {
  background-color: #d32f2f;
}

.button-container {
  display: flex;
  flex-direction: row;
  gap: 10px;
}

.horizontal-text {
  white-space: nowrap;
}
</style>
