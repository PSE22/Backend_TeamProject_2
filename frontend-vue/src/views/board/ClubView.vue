<template>
  <div>
    <h1>동호회 게시판</h1>
    <!-- {/* paging 시작 */} -->
    <div class="col-12 w-25 mb-3">
      페이지 수:
      <select
        class="form-select form-select-sm"
        v-model="pageSize"
        @change="pageSizeChange"
      >
        <option v-for="(data, index) in pageSizes" :key="index" :value="data">
          {{ data }}
        </option>
      </select>
    </div>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">글번호</th>
          <th scope="col">작성자</th>
          <th scope="col">제목</th>
          <th scope="col">작성일</th>
        </tr>
      </thead>
      <tbody class="table-danger">
        <tr v-for="(data, index) in clubNotice" :key="index">
          <td>{{ data.boardId }}</td>
          <td>{{ data.memberName }}</td>
          <td>{{ data.boardTitle }}</td>
          <td>{{ data.addDate }}</td>
        </tr>
      </tbody>
      <tbody>
        <tr v-for="(data, index) in club" :key="index">
          <td>{{ data.boardId }}</td>
          <td>{{ data.memberName }}</td>
          <td>{{ data.boardTitle }}</td>
          <td>{{ data.addDate }}</td>
        </tr>
      </tbody>
    </table>
    <b-pagination
      class="col-12 mb-3"
      v-model="page"
      :total-rows="count"
      :per-page="pageSize"
      @click="retrieveClub"
    ></b-pagination>
    <!-- {/* paging 끝 */} -->
  </div>
</template>

<script>
import ClubService from "@/services/board/ClubService";
export default {
  data() {
    return {
      clubNotice: [],
      club: [],
      // 공통 페이징 속성
      page: 1, // 현재 페이지 번호
      count: 0, // 전체 데이터 개수
      pageSize: 10, // 화면에 보여질 개수

      pageSizes: [10, 20, 30], // 화면에 보여질 개수 배열
    };
  },
  methods: {
    async retrieveClub() {
        console.log("page", this.page)
      try {
        // TODO: 공통 장바구니 전체 조회 서비스 함수 실행
        // TODO: 비동기 코딩
        let response = await ClubService.getAll(
          this.code,
          this.page - 1,
          this.pageSize
        );
        const { club, totalItems } = response.data;
        this.club = club;
        this.count = totalItems;
        // 로깅
        console.log(response.data); // 웹브라우저 콘솔탬에 백앤드 데이터 표시
      } catch (e) {
        console.log(e); // 웹브라우저 콘솔탭에 에러표시
      }
    },
    async retrieveClubNotice() {
      try {
        // TODO: 공통 장바구니 전체 조회 서비스 함수 실행
        // TODO: 비동기 코딩
        let response = await ClubService.getNoticeFive();
        this.clubNotice = response.data;
        console.log(response.data); // 웹브라우저 콘솔탬에 백앤드 데이터 표시
      } catch (e) {
        console.log(e); // 웹브라우저 콘솔탭에 에러표시
      }
    },
    // TODO: 공통 페이징 함수 : select 태그
    pageSizeChange() {
      this.page = 1; // 현재패이지번호 : 1
      this.retrieveClub(); // 재조회
    },
  },
  mounted() {
    this.retrieveClubNotice();
    this.retrieveClub();
  },
};
</script>

<style></style>
