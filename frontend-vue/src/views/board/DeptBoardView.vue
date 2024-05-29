<template>
    <div class="w-80 p-3">
        <h1 class="text-center mb-5 mt-5">부서 게시판</h1>
        <!-- 게시판 소메뉴 버튼 (부서) -->
        <div class="d-flex justify-content-center mb-5">
            <button class="custom-btn col" @click="smcodeChange('DE01')">
                영업팀
            </button>
            <button class="custom-btn col" @click="smcodeChange('DE02')">
                인사팀
            </button>
            <button class="custom-btn col" @click="smcodeChange('DE03')">
                행정팀
            </button>
            <button class="custom-btn col" @click="smcodeChange('DE04')">
                보안팀
            </button>
        </div>
        <table class="table table-hover">
            <!-- 테이블 제목 -->
            <thead class="table-light text-center">
                <tr>
                    <th scope="col">글번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">작성자</th>
                    <th scope="col">작성일</th>
                </tr>
            </thead>
            <!-- 테이블 : 공지사항 -->
            <tbody>
                <tr v-for="(data, index) in deptNotice" :key="index"  @click="goBoardDetail(smcode, data.boardId)">
                    <td class="text-center col-1">{{ data.boardId }}</td>
                    <td class="col-5">
                        <span class="badge text-bg-dark me-2">공지</span>{{ data.boardTitle }}
                    </td>
                    <td class="text-center col-2">{{ data.memberName }}</td>
                    <td class="text-center col-2">{{ data.addDate }}</td>
                </tr>
            </tbody>
            <!-- 테이블 : 일반글 -->
            <tbody>
                <tr v-for="(data, index) in board" :key="index"  @click="goBoardDetail(smcode, data.boardId)">
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
                <select class="form-select form-select-sm" v-model="pageSize" @change="pageSizeChange">
                    <option v-for="(data, index) in pageSizes" :key="index" :value="data">
                        {{ data }}
                    </option>
                </select>
            </div>
            <div class="col-1">
                <button type="button" class="btn btn-dark">글쓰기</button>
            </div>
        </div>
        <div class="row">
            <b-pagination class="col-12 mb-3 justify-content-center" v-model="page" :total-rows="count"
                :per-page="pageSize" @click="retrieveDept"></b-pagination>
            <!-- {/* paging 끝 */} -->

            <!-- {/* 검색어 start */} -->
            <div class="col-md-4 mx-auto">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="검색어를 입력해주세요" v-model="searchTitle"
                        @keyup.enter="retrieveDept" />
                    <button class="btn btn-outline-secondary" type="button" @click="retrieveDept">
                        검색
                    </button>
                </div>
            </div>
            <!-- {/* 검색어 end */} -->
        </div>
    </div>
</template>

<script>
import DeptBoardService from "@/services/board/DeptBoardService";
export default {
    data() {
        return {
            deptNotice: [],
            board: [],
            searchTitle: "",
            smcode: "DE01",

            page: 1, // 현재 페이지 번호
            count: 0, // 전체 데이터 개수
            pageSize: 10, // 화면에 보여질 데이터 개수

            pageSizes: [10, 20, 30], // 화면에 보여질 개수 배열
        };
    },
    methods: {
        // 테스트
        smcodeChange(dept) {
            this.smcode = dept;
            this.retrieveDeptNotice();
            this.retrieveDept();
        },

        // 부서 게시판 - 공지글 전체 조회
        async retrieveDeptNotice() {
            try {
                let response = await DeptBoardService.getNotice(this.smcode);
                this.deptNotice = response.data;
                console.log("공지글 : ", response.data);
            } catch (e) {
                console.log("retrieveDeptNotice() 에러 : ", e);
            }
        },
        // 부서 게시판 - 일반글 전체 조회
        async retrieveDept() {
            try {
                let response = await DeptBoardService.getAll(
                    this.searchTitle,
                    this.smcode,
                    this.page - 1,
                    this.pageSize
                );
                const { board, totalItems } = response.data;
                this.board = board;
                this.count = totalItems;
                console.log("일반글 : ", response.data);
            } catch (e) {
                console.log("retrieveDept() 에러 : ", e);
            }
        },
        // 게시글 상세 페이지로 이동
        goBoardDetail(smcode, boardId) {
            this.$router.push(`/board/dept/${smcode}/${boardId}`);
        },
        // 페이징 : select 태그에 바인딩
        pageSizeChange() {
            this.page = 1;          // 현재패이지번호 : 1
            this.retrieveDept();    // 재조회
        },
    },
    mounted() {
        this.retrieveDept();
        this.retrieveDeptNotice();
    },
};
</script>

<style>
.custom-btn {
    background-color: transparent;
    /* 배경 투명 */
    border: none;
    /* 테두리 없음 */
    color: #333;
    /* 글씨 색상 */
    font-size: 16px;
    /* 글씨 크기 */
    padding: 10px 20px;
    /* 안쪽 여백 */
    margin: 0 5px;
    /* 버튼 간격 */
    cursor: pointer;
    /* 커서 스타일 */
    position: relative;
    /* indicator 위치 조정 */
}

.custom-btn.active,
.custom-btn:hover {
    color: #000000;
    /* 활성/호버 시 글씨 색상 */
}

.custom-btn.active::after,
.custom-btn:hover::after {
    /* indicator 스타일 */
    content: "";
    position: absolute;
    left: 0;
    bottom: -2px;
    /* 아래 여백 */
    width: 100%;
    height: 2px;
    background-color: #000000;
    /* indicator 색상 */
}
</style>