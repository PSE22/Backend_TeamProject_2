<template>
  <div class="container">
    <div class="sidebar">
      <h1 class="sidebar-title">{{ member.memberName }}님</h1>
      <hr class="sidebar-divider" />
      <ul class="sidebar-list">
        <router-link to="/profile-edit" class="profile-link"><li class="sidebar-menu mb-5">회원정보수정</li></router-link>
        <router-link to="/profile-edit/password" class="profile-link"><li class="sidebar-menu mb-5">비밀번호변경</li></router-link>
        <router-link to="/profile-edit/nickname" class="profile-link"><li class="sidebar-menu mb-5">닉네임변경</li></router-link>
        <router-link to="/profile-activity" class="profile-link"><li class="sidebar-menu">활동내역</li></router-link>
      </ul>
    </div>
    <div class="main-content">
      <div class="section">
        <router-link to="/profile" class="profile-main"><h3>Profile</h3></router-link>
        <hr />
        <div class="user-info">
          <div class="details">
            <div class="detail-item">
              <span class="label">이메일</span>
              <input class="edit-box" v-model="member.memberEmail" />
            </div>
            <div class="detail-item">
              <span class="label">번호</span>
              <input class="edit-box" v-model="member.memberExt" />
            </div>
            <div class="detail-item">
              <span class="label">부서</span>
              <select class="edit-box" v-model="member.deptCode">
                <option value="DE01">영업팀</option>
                <option value="DE02">인사팀</option>
                <option value="DE03">행정팀</option>
                <option value="DE04">보안팀</option>
              </select>
            </div>
            <div class="detail-item">
              <span class="label">직급</span>
              <select class="edit-box" v-model="member.posCode">
                <option value="PO01">사원</option>
                <option value="PO02">주임</option>
                <option value="PO03">대리</option>
                <option value="PO04">과장</option>
              </select>
            </div>
          </div>
          <button class="edit-button" @click="editProfile">수정요청</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MemberService from "@/services/member/MemberService";
import LoginService from "@/services/login/LoginService";

export default {
  data() {
    return {
      member: {},
    };
  },
  methods: {
    async getProfile() {
      try {
        let response = await MemberService.get(
          this.$store.state.member?.memberId
        );
        this.member = response.data;
        console.log(response.data);
      } catch (e) {
        console.log(e);
      }
    },
    async editProfile() {
      try {
        let data = {
          memberId: this.member.memberId,
          memberPw: this.member.memberPw,
          memberName: this.member.memberName,
          memberEmail: this.member.memberEmail,
          memberExt: this.member.memberExt,
          nickname: this.member.nickname,
          memberCode: "AT03",
          deptCode: this.member.deptCode,
          posCode: this.member.posCode,
        };
        let response = await MemberService.updateProfile(data);
        console.log(response.data);
        LoginService.logout();
        this.$store.commit("logout");
        alert("관리자 승인 후 로그인 가능합니다.")
        this.$router.push("/login");
      } catch (e) {
        console.log(e);
      }
    },
  },
  mounted() {
    alert("수정 요청 후 자동 로그아웃됩니다.\n이후 관리자 승인 시까지 로그인이 불가하오니 주의바랍니다.")
    this.getProfile();
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
  background: #b3000f; /* #b3000f 색상 */
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

.section h3 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.details {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.detail-item:last-child {
  border-bottom: none;
}

.label {
  font-size: 18px;
  font-weight: 500;
}

.edit-box {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
  outline: none;
  transition: border-color 0.3s, box-shadow 0.3s;
  width: 70%;
}

.edit-box:focus {
  border-color: #b3000f;
  box-shadow: 0 0 5px rgba(179, 0, 15, 0.5);
}

.edit-button {
  background-color: #b3000f; /* #b3000f 색상 */
  border: none;
  padding: 10px 20px;
  border-radius: 20px;
  color: white;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s;
  display: block;
  margin: 20px auto 0;
}

.edit-button:hover {
  background-color: #80000b; /* #b3000f 색상의 어두운 버전 */
}
</style>