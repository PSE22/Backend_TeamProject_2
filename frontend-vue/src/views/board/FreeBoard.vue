<template>
  <div class="w-80 p-3">
    <h1 class="text-center mb-5 mt-5">자유 게시판</h1>
    <div>
      <table class="table table-hover">
        <thead class="table-light text-center">
          <tr>
            <th scope="col">글번호</th>
            <th scope="col">제목</th>
            <th scope="col">닉네임</th>
            <th scope="col">등록일</th>
            <th scope="col">추천수</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(data, index) in freeNotice" :key="index">
            <td class="text-center col-1">{{ data.boardId }}</td>
            <td class="col-5">
              <span class="badge text-bg-dark me-2">공지</span
              >{{ data.boardTitle }}
            </td>
            <td class="text-center col-2">{{ data.nickname }}</td>
            <td class="text-center col-2">{{ data.addDate }}</td>
          </tr>
        </tbody>
        <tbody>
          <tr v-for="(data, index) in board" :key="index">
            <td class="text-center">{{ data.boardId }}</td>
            <td>
              <router-link :to="`/free-view/${board.boardTitle}`">
                {{ data.boardTitle }}
              </router-link>
            </td>
            <td>{{ data.nickname }}</td>
            <td class="text-center">{{ data.addDate }}</td>
            <td class="text-center">{{ data.good }}</td>
          </tr>
        </tbody>
      </table>
      <!-- {/* paging 시작 */} -->
      <div class="row justify-content-between">
        <div class="col-4 w-25 mb-3">
          <select
            class="form-select form-select-sm"
            v-model="pageSize"
            @change="pageSizeChange"
          >
            <option
              v-for="(data, index) in pageSizes"
              :key="index"
              :value="data"
            >
              {{ data }}
            </option>
          </select>
        </div>
        <div class="col-1">
          <button type="button" class="btn btn-dark">등록</button>
        </div>
      </div>
      <div class="row">
        <b-pagination
          class="col-12 mb-3 justify-content-center"
          v-model="page"
          :total-rows="count"
          :per-page="pageSize"
          @click="retrieveFreeBoard"
        ></b-pagination>
        <!-- {/* paging 끝 */} -->
        <!-- {/* 검색어 start */} -->
        <div class="col-md-4 mx-auto">
          <div class="input-group mb-3">
            <input
              type="text"
              class="form-control"
              placeholder="제목 검색"
              v-model="searchBoardTitle"
              @keyup.enter="retrieveFreeBoard"
            />
            <button
              class="btn btn-outline-secondary"
              type="button"
              @click="retrieveFreeBoard"
            >
              검색
            </button>
          </div>
        </div>
        <!-- {/* 검색어 end */} -->
      </div>
    </div>
  </div>
</template>

<script>
import FreeBoardService from "@/services/board/FreeBoardService";
export default {
  data() {
    return {
      // 백엔드 연결
      board: [],
      freeNotice: [],
      submitted: false,
      searchBoardTitle: "",
      page: 1,
      count: 0,
      pageSize: 10,

      pageSizes: [10, 25, 50],
    };
  },
  methods: {
    async retrieveFreeBoard() {
      try {
        this.retrieveFreeNotice();

        let response = await FreeBoardService.getAll(
          this.searchBoardTitle,
          this.page - 1,
          this.pageSize
        );
        const { board, totalItems } = response.data;
        this.board = board;
        this.count = totalItems;
        console.log("전체조회", response.data);
      } catch (e) {
        console.log(e);
      }
    },
    async retrieveFreeNotice() {
      try {
        // TODO: 공통 장바구니 전체 조회 서비스 함수 실행
        // TODO: 비동기 코딩
        let response = await FreeBoardService.getNoticeFree();
        this.freeNotice = response.data;
        console.log(response.data); // 웹브라우저 콘솔탬에 백앤드 데이터 표시
      } catch (e) {
        console.log(e); // 웹브라우저 콘솔탭에 에러표시
      }
    },
    // TODO: 공통 페이징 함수 : select 태그
    pageSizeChange() {
      this.page = 1; // 현재패이지번호 : 1
      this.retrieveFreeBoard(); // 재조회
    },
  },
  mounted() {
    this.retrieveFreeBoard();
  },
};
</script>

<style>
.table th {
  white-space: nowrap; /* 줄바꿈 방지 */
  text-overflow: ellipsis; /* 텍스트 생략 */
  padding: 0 30px; /* 좌우 여백 추가 */
  text-align: center; /* 헤더 가운데 정렬 */
}
.table td {
  text-align: center; /* 내용 가운데 정렬 */
}
</style>
