<template>
    <div class="w-80 p-3 board-detail-container">
        <!-- 글 수정/삭제 버튼 : 글쓴이만 보임 -->
        <div class="row board-button">
            <button class="col">수정</button>
            <button class="col">삭제</button>
        </div>
        <!-- 게시글 container -->
        <div class="row board-content">
            <!-- 글 제목 부분 -->
            <div>부서게시판 > {{ cmcd?.cmCdName }}</div>
            <div>{{ board?.boardTitle }}</div>
            <div>작성자 | {{ board?.memberName }}</div>
            <div>작성일 | {{ board?.addDate}}</div>
            <hr>
            <!-- 글 내용 부분 -->
            <div>투표</div>
            <div>글내용 : {{ board?.boardContent}}</div>
            <div>지도</div>
            <div v-for="(data, index) in image" :key="index">
                <img :src="data.fileUrl" alt="이미지">
            </div>
            <hr>
            <div>추천수 : {{ board?.good }} | 댓글수 :  | 신고버튼</div>
        </div>
        <!-- 댓글 작성 -->
        <div class="row reply-write">
            <div>작성자명 (익명게시판은 input 태그로 변경)</div>
            <textarea>댓글 작성란</textarea>
            <div class="row">
                <button class="col">파일선택버튼</button>
                <p class="col">파일명</p>
                <button class="col">등록버튼</button>
            </div>
        </div>
        <!-- 댓글 목록 -->
        <div class="row reply-content">
            <div>댓글1</div>
            <div>댓글2</div>
            <div>댓글3</div>
            <div>댓글수정시</div>
        </div>
        <!-- 목록으로 돌아가기 버튼 -->
        <div class="row">
            <button>목록</button>
        </div>
    </div>
</template>
<script>
import BoardDetailService from '@/services/board/BoardDetailService';

export default {
    data() {
        return {
            boardId: this.$route.params.boardId,
            smcode: this.$route.params.smcode,
            image: "",
            board: "",
            cmcd: "",
        }
    },
    methods: {
        // 게시글, 작성자 정보 조회
        async retrieveBoard() {
            try {
                let response = await BoardDetailService.getBoard(this.boardId);
                this.board = response.data;
                console.log("board 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveBoard 에러", e);
            }
        },
        // 코드번호로 코드명 조회
        async retrieveCode() {
            try {
                let response = await BoardDetailService.getCmCd(this.smcode);
                this.cmcd = response.data;
                console.log("code 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveCode 에러", e);
            }
        },
        // 글번호로 이미지 조회
        async retrieveImg() {
            try {
                let response = await BoardDetailService.getImg(this.boardId);
                this.image = response.data;
                console.log("image 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveImg 에러", e);
            }
        }
    },
    mounted() {
        console.log("부서코드 : ", this.smcode, "/ 글번호 : ", this.boardId);
        this.retrieveBoard();
        this.retrieveCode();
        this.retrieveImg();
    },
}
</script>
<style>
/* 글 상세 컨테이너 */
.board-detail-container {
    background-color: yellow;
}

/* 글 내용 */
.board-content {
    background-color: aqua;
}

/* 댓글 작성 */
.reply-write {
    background-color: bisque;
}

/* 댓글 목록 */
.reply-content {
    background-color: cadetblue;
}
</style>