<template>
  <div class="w-80 p-3">
    <h1 class="text-center mb-5 mt-5">동호회 게시판</h1>
    <div class="d-flex justify-content-center mb-5" v-for="(data, index) in smcode" :key="index">
      <button class="custom-btn col" @click="this.$router.push('/club')">전체</button>
      <button class="custom-btn col" @click="handleButtonClick('견적문의')">
        {{ data.smCdName }}
      </button>
    </div>
    <div>
      <table class="table table-hover">
        <thead class="table-light text-center">
          <tr>
            <th scope="col">글번호</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(data, index) in clubNotice" :key="index">
            <td class="text-center col-1">{{ data.boardId }}</td>
            <td class="col-5">
              <span class="badge text-bg-dark me-2">공지</span
              >{{ data.boardTitle }}
            </td>
            <td class="text-center col-2">{{ data.memberName }}</td>
            <td class="text-center col-2">{{ data.addDate }}</td>
          </tr>
        </tbody>
        <tbody>
          <tr v-for="(data, index) in club" :key="index">
            <td class="text-center">{{ data.boardId }}</td>
            <td>{{ data.boardTitle }}</td>
            <td class="text-center">{{ data.memberName }}</td>
            <td class="text-center">{{ data.addDate }}</td>
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
          <button type="button" class="btn btn-dark">글쓰기</button>
        </div>
      </div>
      <div class="row">
        <b-pagination
          class="col-12 mb-3 justify-content-center"
          v-model="page"
          :total-rows="count"
          :per-page="pageSize"
          @click="retrieveClub"
        ></b-pagination>
        <!-- {/* paging 끝 */} -->
        <!-- {/* 검색어 start */} -->
        <div class="col-md-4 mx-auto">
          <div class="input-group mb-3">
            <input
              type="text"
              class="form-control"
              placeholder="검색어를 입력해주세요"
              v-model="searchTitle"
              @keyup.enter="retrieveClub"
            />
            <button
              class="btn btn-outline-secondary"
              type="button"
              @click="retrieveClub"
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
import ClubService from "@/services/board/ClubService";
export default {
  data() {
    return {
      clubNotice: [],
      club: [],
      searchTitle: "",
      smcode: [],
      // 공통 페이징 속성
      page: 1, // 현재 페이지 번호
      count: 0, // 전체 데이터 개수
      pageSize: 10, // 화면에 보여질 개수

      pageSizes: [10, 20, 30], // 화면에 보여질 개수 배열
    };
  },
  methods: {
    async retrieveClub() {
      console.log("page", this.page);
      try {
        this.retrieveClubNotice();
        // TODO: 공통 장바구니 전체 조회 서비스 함수 실행
        // TODO: 비동기 코딩
        let response = await ClubService.getAll(
          this.searchTitle,
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
    async retrieveSmcode() {
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
    this.retrieveClub();
  },
};
</script>

<style>
.custom-btn {
  background-color: transparent; /* 배경 투명 */
  border: none; /* 테두리 없음 */
  color: #333; /* 글씨 색상 */
  font-size: 16px; /* 글씨 크기 */
  padding: 10px 20px; /* 안쪽 여백 */
  margin: 0 5px; /* 버튼 간격 */
  cursor: pointer; /* 커서 스타일 */
  position: relative; /* indicator 위치 조정 */
}

.custom-btn.active,
.custom-btn:hover {
  color: #000000; /* 활성/호버 시 글씨 색상 */
}

.custom-btn.active::after,
.custom-btn:hover::after {
  /* indicator 스타일 */
  content: "";
  position: absolute;
  left: 0;
  bottom: -2px; /* 아래 여백 */
  width: 100%;
  height: 2px;
  background-color: #000000; /* indicator 색상 */
}
</style>
