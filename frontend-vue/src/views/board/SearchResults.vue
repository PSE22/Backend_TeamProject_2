<template>
    <div class="search-results-container">
      <h2>{{ boardTitle }}에 대한 검색 결과 (총 {{ totalItems }}건)</h2>
      <table class="table">
        <thead>
          <tr>
            <th>#</th>
            <th>제목</th>
            <th>게시판</th>
            <th>닉네임</th>
            <th>등록일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(board, index) in boards" :key="index">
            <td>{{ board.boardId }}</td>
            <td>
              <router-link :to="`/board/${board.boardId}`" class="custom-link" >{{ board.boardTitle }}</router-link>
            </td>
            <td>{{ board.boCode === board.cmCode ? board.cmCodeName : '' }}</td>
            <td>{{ board.nickName }}</td>
            <td>{{ board.addDate }}</td>
          </tr>
        </tbody>
      </table>
      <b-pagination
        v-model="currentPage"
        :total-rows="totalItems"
        :per-page="pageSize"
        @input="searchDate"
      ></b-pagination>
    </div>
  </template>
  
  <script>
  import MainSearchService from '@/services/board/MainSearchService';
  
  export default {
    data() {
      return {
        boards: [],
        currentPage: 1,
        pageSize: 10,
        totalItems: 0,
        boardTitle: '' // 검색어 저장
      };
    },
    watch: {
      '$route.query.boardtitle': {
        immediate: true,
        handler(newVal) {
          this.boardTitle = newVal;
          this.searchDate();
        }
      },
      currentPage: {
        handler() {
          this.searchDate();
        },
        immediate: true
      }
    },
    methods: {
      async searchDate() {
        try {
          const response = await MainSearchService.searchBoards(this.boardTitle, this.currentPage - 1, this.pageSize);
          this.boards = response.data.content;
          this.totalItems = response.data.totalElements;
        } catch (error) {
          console.error("Error fetching search results:", error);
        }
      }
    },
  };
  </script>
  
  <style scoped>
  .search-results-container {
    padding: 20px;
  }
  .table {
    width: 100%;
    margin-top: 20px;
  }

  /* RouterLink 커스텀 스타일 */
.custom-link {
  color: black; /* 기본 텍스트 색상 */
  text-decoration: none; /* 밑줄 제거 */
}
  </style>
  