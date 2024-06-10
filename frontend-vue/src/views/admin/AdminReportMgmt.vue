<template>
  <div class="container">
    <div class="sidebar">
      <h1 class="sidebar-title">{{ loginMember.memberName }}님</h1>
      <hr class="sidebar-divider" />
      <ul class="sidebar-list">
        <router-link to="/admin/register-mgmt" class="profile-link"
          ><li class="sidebar-menu mb-5">회원가입승인</li></router-link
        >
        <router-link to="/admin/register-deny-mgmt" class="profile-link"
          ><li class="sidebar-menu mb-5">반려회원관리</li></router-link
        >
        <router-link to="/admin/edit-mgmt" class="profile-link"
          ><li class="sidebar-menu mb-5">회원정보변경</li></router-link
        >
        <router-link to="/admin/report-mgmt" class="profile-link"
          ><li class="sidebar-menu">신고글관리</li></router-link
        >
      </ul>
    </div>
    <div class="main-content">
      <div class="row">
        <table class="table">
          <thead class="table-light text-center">
            <tr>
              <th scope="col">신고자</th>
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">신고사유</th>
              <th scope="col">작성자</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody class="table-group-divider align-middle">
            <tr v-for="(data, index) in report" :key="index" @click="goBoardDetail">
              <td class="col-1 text-center">{{ data.reMemberId }}</td>
              <td class="col-1 text-center">{{ data.boardId }}</td>
              <td class="col-1 text-center">{{ data.boardTitle }}</td>
              <td class="col-1 text-center">{{ data.reportReason }}</td>
              <td class="col-1 text-center">{{ data.boMemberId }}</td>
              <td class="col-1 text-center">
                <div>
                  <button
                    type="button"
                    class="btn btn-primary"
                    data-bs-toggle="modal"
                  >
                    반려
                  </button>
                  <button class="delete-button" @click="registerDelete(data)">
                    삭제
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <b-pagination
        class="col-12 mb-3 justify-content-center"
        v-model="page"
        :total-rows="count"
        :per-page="pageSize"
        @click="retrieveReport"
      ></b-pagination>
    </div>
  </div>
</template>

<script>
import ReportService from "@/services/admin/ReportService";
import MemberService from "@/services/member/MemberService";

export default {
  data() {
    return {
      loginMember: {},
      report: [],
      boardId: this.$route.params.boardId, // 현재 글 ID 가져오기
      bocode: this.$route.params.bocode,
      smcode: this.$route.params.smcode,

      page: 1, // 현재 페이지 번호
      count: 0, // 전체 데이터 개수
      pageSize: 10, // 화면에 보여질 개수
    };
  },
  methods: {
    async retrieveReport() {
      try {
        let response = await ReportService.getAll(this.page - 1, this.pageSize);
        this.report = response.data.content;
        this.count = response.data.totalElen;
        // 로깅
        console.log("report data:", this.report); // club 데이터 출력
        console.log("글 목록", response.data);
      } catch (e) {
        console.log(e); // 웹브라우저 콘솔탭에 에러표시
      }
    },
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
    goBoardDetail(bocode, smcode, boardId) {
      this.$router.push(`/board/club/${bocode}/${smcode}/${boardId}`);
    },
  },
  mounted() {
    this.getProfile();
    this.retrieveReport();
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
</style>
