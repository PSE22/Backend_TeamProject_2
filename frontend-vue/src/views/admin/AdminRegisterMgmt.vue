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
              <th scope="col">승인</th>
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
                  <button class="approve-button" @click="registerApprove(data)">
                    <span class="horizontal-text">승인</span>
                  </button>
                  <button class="reject-button" @click="registerReject(data)">
                    <span class="horizontal-text">반려</span>
                  </button>
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
  components : {
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
        let response = await MemberService.getMember("AT03");
        this.member = response.data;
        console.log(response.data);
      } catch (e) {
        console.log(e);
      }
    },

    async registerApprove(data) {
      try {
        data.memberCode = "AT02";
        let response = await MemberService.updateProfile(data);
        console.log(response.data);
        alert("회원가입이 승인되었습니다.");
        this.getAllProfile();
      } catch (e) {
        console.log(e);
      }
    },
    async registerReject(data) {
      try {
        data.memberCode = "AT04";
        let response = await MemberService.updateProfile(data);
        console.log(response.data);
        alert("회원가입이 반려되었습니다.");
        this.getAllProfile();
      } catch (e) {
        console.log(e);
      }
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

.approve-button {
  background-color: #4caf50;
}

.approve-button:hover {
  background-color: #45a049;
}

.reject-button {
  background-color: #ff9800;
}

.reject-button:hover {
  background-color: #e68900;
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
