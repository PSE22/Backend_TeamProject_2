<template>
  <div>
    <div id="content-wrapper" class="d-flex flex-column">
      <div id="content">
        <!-- 자유게시판 시작 -->
        <div class="container-fluid">
          <!-- 검색어 -->
          <div class="row mb-3 mt-3 justify-content-center">
            <div class="col-12 w-50 input-group">
              <input
                type="text"
                class="form-control"
                placeholder="제목 검색"
                v-model="searchBoardTitle"
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

          <div class="card shadow mb-4">
            <div
              class="card-header py-3 d-flex justify-content-between align-items-center text-center"
            >
              <h5 class="m-0 font-weight-bold text-primary flex-grow-1">
                자유 게시판
              </h5>
              <button type="button" class="btn btn-primary">등록</button>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <div v-if="!submitted">
                  <div class="row">
                    <div class="col-sm-12">
                      <table
                        class="table table-bordered dataTable text-center"
                        width="100%"
                        cellspacing="0"
                        role="grid"
                        aria-describedby="dataTable_info"
                        style="width: 100%"
                      >
                        <thead>
                          <tr role="row">
                            <th>추천수</th>
                            <th>제목</th>
                            <th>닉네임</th>
                            <th>등록일</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr v-for="(data, index) in board" :key="index">
                            <td>{{ data.good }}</td>
                            <td>{{ data.boardTitle }}</td>
                            <td>{{ data.nickname }}</td>
                            <td>{{ data.addDate }}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
                <!-- 페이지 시작 -->
                <div class="row">
                  <div class="col-sm-12 col-md-5">
                    <div
                      class="dataTables_info"
                      role="status"
                      aria-live="polite"
                    >
                      검색결과 총 {{ count }} 건
                    </div>
                  </div>
                  <div class="col-sm-12 col-md-7">
                    <div class="dataTables_paginate paging_Simple_numbers">
                      <b-pagination
                        v-model="page"
                        :total-rows="count"
                        :per-page="pageSize"
                        @click="retrieveFreeBoard"
                      ></b-pagination>
                    </div>
                  </div>
                </div>
                <!-- 페이지 끝 -->
              </div>
            </div>
          </div>
          <!-- /.container-fluid -->
        </div>
      </div>
      <!-- 게시판 끝 -->
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
