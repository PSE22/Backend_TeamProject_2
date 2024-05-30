<template>
  <div class="container">
    <div class="sidebar">
      <h1 class="sidebar-title">{{ member.memberName }}님</h1>
      <hr class="sidebar-divider" />
      <ul class="sidebar-list">
        <router-link to="/profile-edit" class="profile-link"
          ><li class="sidebar-menu mb-5">회원정보수정</li></router-link
        >
        <router-link to="/profile-edit/password" class="profile-link"
          ><li class="sidebar-menu mb-5">비밀번호변경</li></router-link
        >
        <router-link to="/profile-edit/nickname" class="profile-link"
          ><li class="sidebar-menu mb-5">닉네임변경</li></router-link
        >
        <router-link to="/profile-activity" class="profile-link"
          ><li class="sidebar-menu">활동내역</li></router-link
        >
      </ul>
    </div>
    <div class="main-content">
      <div class="row">
        <table class="table">
          <thead class="table-light text-center">
            <tr>
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">등록일</th>
            </tr>
          </thead>
          <tbody class="table-group-divider align-middle">
            <tr v-for="(data, index) in board" :key="index">
              <td class="col-1 text-center">{{ data.boardId }}</td>
              <td class="col-7 text-center">{{ data.boardTitle }}</td>
              <td class="col-2 text-center">{{ data.addDate }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- {/* paging 시작 */} -->
      <b-pagination
        class="col-12 mb-3 justify-content-center"
        v-model="page"
        :total-rows="count"
        :per-page="pageSize"
        @click="retrieveBoard"
      ></b-pagination>
      <!-- {/* paging 끝 */} -->
    </div>
  </div>
</template>

<script>
import MemberService from "@/services/member/MemberService";

export default {
  data() {
    return {
      member: {},
      message: "",

      board: [],

      page: 1,
      count: 0,
      pageSize: 10,
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

    async retrieveBoard() {
      try {
        let response = await MemberService.getAllPost(
          this.$store.state.member?.memberId,
          this.page - 1,
          this.pageSize
        );
        const { board, totalItems } = response.data;
        this.board = board;
        this.count = totalItems;
        console.log(response.data);
      } catch (e) {
        console.log(e);
      }
    },
  },
  mounted() {
    this.getProfile();
    this.retrieveBoard();
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
