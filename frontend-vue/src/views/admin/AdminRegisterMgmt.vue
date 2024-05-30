<template>
  <div class="container">
    <div class="sidebar">
      <h1 class="sidebar-title">{{ loginMember.memberName }}님</h1>
      <hr class="sidebar-divider" />
      <ul class="sidebar-list">
        <router-link to="" class="profile-link"
          ><li class="sidebar-menu mb-5">회원가입승인</li></router-link
        >
        <router-link to="" class="profile-link"
          ><li class="sidebar-menu mb-5">회원정보변경승인</li></router-link
        >
        <router-link to="" class="profile-link"
          ><li class="sidebar-menu mb-5">회원삭제</li></router-link
        >
        <router-link to="" class="profile-link"
          ><li class="sidebar-menu">신고글관리</li></router-link
        >
      </ul>
    </div>
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
            </tr>
          </thead>
          <tbody class="table-group-divider align-middle">
            <tr v-for="(data, index) in filteredMembers" :key="index">
              <td class="col-1 text-center">{{ data.memberId }}</td>
              <td class="col-1 text-center">{{ data.memberName }}</td>
              <td class="col-1 text-center">{{ data.memberEmail }}</td>
              <td class="col-1 text-center">{{ data.memberExt }}</td>
              <td class="col-1 text-center">{{ data.nickname }}</td>
              <td class="col-1 text-center">{{ data.deptCode }}</td>
              <td class="col-1 text-center">{{ data.posCode }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import MemberService from "@/services/member/MemberService";

export default {
  data() {
    return {
      loginMember: {},
      member: []
    };
  },
  computed: {
    filteredMembers() {
      return this.member.filter(m => m.memberCode === 'AT03');
    }
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
        let response = await MemberService.getMember();
        this.member = response.data;
        console.log(response.data);
      } catch (e) {
        console.log(e);
      }
    }
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
</style>
