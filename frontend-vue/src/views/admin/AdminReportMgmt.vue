<template>
  <div class="container">
    <AdminSidebar />
    <div class="main-content">
      <div class="header">
        <h2>신고관리</h2>
      </div>
      <button class="dept-button" @click="retrieveReport">게시글</button>
      <button class="dept-button">신고</button>
      <!-- 게시글 관리 -->
      <div class="row">
        <table class="table">
          <thead class="table-light text-center">
            <tr>
              <th class="col-1">신고자</th>
              <th class="col-1">글번호</th>
              <th class="col-3">제목</th>
              <th class="col-2">신고사유</th>
              <th class="col-2">작성자</th>
              <th class="col-3"></th>
            </tr>
          </thead>
          <tbody class="table-group-divider align-middle">
            <tr v-for="(data, index) in report" :key="index">
              <td class="text-center">{{ data.reMemberId }}</td>
              <td class="text-center">{{ data.boardId }}</td>
              <td
                @click="goBoardDetail(data.bocode, data.smcode, data.boardId)"
              >
                {{ data.boardTitle }}
              </td>
              <td class="text-center">{{ data.reportReason }}</td>
              <td class="text-center" v-if="data.status === 'Y'">
                신고 확정 시 확인
              </td>
              <td class="text-center" v-if="data.status === 'N'">
                {{ data.boMemberId }}
              </td>
              <td class="text-center">
                <div v-if="data.status === 'Y'">
                  <button
                    type="button"
                    class="btn btn-primary"
                    @click="confirmReDelete(data.reportId)"
                  >
                    확인
                  </button>
                  <button
                    class="delete-button"
                    @click="confirmBoDelete(data.boardId)"
                  >
                    삭제
                  </button>
                </div>
                <div v-if="data.status === 'N'">
                  게시글 삭제 완료
                  <span
                    class="badge text-bg-secondary"
                    @click="confirmReDelete(data.reportId)"
                    >영구삭제</span
                  >
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <b-pagination
          class="col-12 mb-3 justify-content-center"
          v-model="page"
          :total-rows="count"
          :per-page="pageSize"
          @click="retrieveReport"
        ></b-pagination>
      </div>
      <!-- 댓글 관리 -->
      <div class="row">
        <table class="table">
          <thead class="table-light text-center">
            <tr>
              <th class="col-1">신고자</th>
              <th class="col-1">글번호</th>
              <th class="col-3">내용</th>
              <th class="col-2">신고사유</th>
              <th class="col-2">작성자</th>
              <th class="col-3"></th>
            </tr>
          </thead>
          <tbody class="table-group-divider align-middle">
            <tr v-for="(data, index) in report" :key="index">
              <td class="text-center">{{ data.reMemberId }}</td>
              <td class="text-center">{{ data.boardId }}</td>
              <td
                @click="goBoardDetail(data.bocode, data.smcode, data.boardId)"
              >
                {{ data.boardTitle }}
              </td>
              <td class="text-center">{{ data.reportReason }}</td>
              <td class="text-center" v-if="data.status === 'Y'">
                신고 확정 시 확인
              </td>
              <td class="text-center" v-if="data.status === 'N'">
                {{ data.boMemberId }}
              </td>
              <td class="text-center">
                <div v-if="data.status === 'Y'">
                  <button
                    type="button"
                    class="btn btn-primary"
                    @click="confirmReDelete(data.reportId)"
                  >
                    확인
                  </button>
                  <button
                    class="delete-button"
                    @click="confirmBoDelete(data.boardId)"
                  >
                    삭제
                  </button>
                </div>
                <div v-if="data.status === 'N'">
                  게시글 삭제 완료
                  <span
                    class="badge text-bg-secondary"
                    @click="confirmReDelete(data.reportId)"
                    >영구삭제</span
                  >
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <b-pagination
          class="col-12 mb-3 justify-content-center"
          v-model="page"
          :total-rows="count"
          :per-page="pageSize"
          @click="retrieveReport"
        ></b-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import BoardDetailService from "@/services/board/BoardDetailService";
import ReportService from "@/services/admin/ReportService";
import AdminSidebar from "@/components/common/AdminSidebar.vue";
import AdminService from "@/services/admin/AdminService";

export default {
  components: {
    AdminSidebar,
  },
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
    // 신고 목록 가져오기
    async retrieveReport() {
      try {
        let response = await ReportService.getAll(this.page - 1, this.pageSize);
        this.report = response.data.content;
        this.count = response.data.totalElements;
        // 로깅
        console.log("report data:", this.report); // 데이터 출력
        console.log("글 목록", response.data);
      } catch (e) {
        console.log(e); // 웹브라우저 콘솔탭에 에러표시
      }
    },
    // 게시글 삭제
    async deleteBoard(boardId) {
      try {
        let response = await BoardDetailService.deleteBoard(boardId);
        console.log("삭제", response);
        console.log("삭제", boardId);
        alert("게시글이 삭제되었습니다.");
        this.retrieveReport(); // 신고 목록을 다시 가져옴
      } catch (error) {
        console.log("삭제 에러", error);
        alert("게시글 삭제에 실패했습니다.");
      }
    },
    // 게시글 삭제 확인
    confirmBoDelete(boardId) {
      if (confirm("게시글을 삭제 하시겠습니까?")) {
        this.deleteBoard(boardId);
      }
    },
    // 신고 삭제
    async deleteReport(reportId) {
      try {
        let response = await AdminService.deleteReport(reportId);
        console.log(response);
        console.log("신고 아이디", reportId);
        alert("신고가 삭제되었습니다.");
        this.retrieveReport(); // 신고 목록을 다시 가져옴
      } catch (e) {
        console.log("에러", e);
        alert("신고 삭제가 실패하였습니다.");
      }
    },
    // 게시글 삭제 확인
    confirmReDelete(reportId) {
      if (confirm("신고를 삭제 하시겠습니까?")) {
        this.deleteReport(reportId);
      }
    },
    // 게시글로 이동
    goBoardDetail(bocode, smcode, boardId) {
      this.$router.push(`/board/club/${bocode}/${smcode}/${boardId}`);
    },
  },
  mounted() {
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

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  background-color: #f4f4f9;
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

.dept-button {
  background-color: black;
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
