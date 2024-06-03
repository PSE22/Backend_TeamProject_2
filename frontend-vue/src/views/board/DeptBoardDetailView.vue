<template>
    <div class="w-80 p-3 board-detail-container">
        <!-- 글 수정/삭제 버튼 : 글쓴이만 보임 -->
        <div class="row board-button">
            <button  class="col">수정</button>
            <button class="col">삭제</button>
        </div>
        <!-- 게시글 -->
        <div class="row board-content">
            <!-- 글 제목 부분 -->
            <div>부서게시판 > {{ cmcd?.cmCdName }}</div>
            <div>{{ board?.boardTitle }}</div>
            <div>작성자 | {{ board?.memberName }}</div>
            <div>작성일 | {{ board?.addDate }}</div>
            <hr>
            <!-- 글 내용 부분 -->
            <div>투표</div>
            <div>글내용 : {{ board?.boardContent }}</div>
            <div>지도</div>
            <div v-for="(data, index) in boardImage" :key="index">
                <img :src="data.fileUrl" alt="이미지">
            </div>
            <hr>
            <div>추천수 : {{ board?.good }} | 댓글수 : {{ replyCount }} | 신고버튼</div>
        </div>
        <!-- 댓글 작성 -->
        <div class="row reply-write">
            <div>{{ memberInfo.memberName }} (익명게시판은 별명으로 변경하세요)</div>
            <textarea>댓글 작성란</textarea>
            <div class="row">
                <button class="col">파일선택버튼</button>
                <p class="col">파일명</p>
                <button class="col">등록버튼</button>
            </div>
        </div>
        <!-- 댓글 목록 -->
        <div class="row reply-content">
            <ul v-for="(data, index) in reply" :key="index" class="list-group">
                <li class="list-group-item">
                    <div>{{ data.memberName }}</div>
                    <div>{{ data.reply }}</div>
                    <img v-if="data.fileUrl" :src="data.fileUrl" height="200px" width="300px" alt="이미지">
                    <div>
                        <span> {{ data.addDate }} </span>
                        <button>수정</button>
                        <button>삭제</button>
                        <button>신고</button>
                    </div>
                </li>
            </ul>
        </div>
        <!-- 목록으로 돌아가기 버튼 -->
        <div class="row">
            <button @click="this.$router.push('/board/dept/' + smcode)">목록</button>
        </div>
    </div>
</template>
<script>
import BoardDetailService from '@/services/board/BoardDetailService';

export default {
    data() {
        return {
            member: this.$store.state.member,       // 현재 로그인된 회원 가져오기
            boardId: this.$route.params.boardId,    // 현재 글 ID 가져오기
            smcode: this.$route.params.smcode,      // 현재 소메뉴 코드 가져오기

            auth: "",                   // 로그인 사용자 권한 체크
            memberInfo: "",             // 회원정보
            board: "",                  // 게시글 
            cmcd: "",                   // 부서코드, 부서명
            vote: "",                   // 투표
            place: "",                  // 장소
            boardImage: "",             // 글 이미지 배열
            reply: "",                  // 댓글 배열
            replyCount: "",             // 댓글수
        }
    },
    methods: {
        // 회원 권한 체크
        async checkAuth() {
            if (this.member.memberCode === "AT01") {
                this.auth = "A"
            } else if (this.member.memberId === this.memberInfo.memberId) {
                this.auth = "B"
            } 
        },
        // 로그인된 회원 정보 가져오기
        async retrieveMember() {
            try {
                let response = await BoardDetailService.getMember(this.member.memberId);
                this.memberInfo = response.data;
                console.log("memberInfo 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveMember 에러", e);
            }
        },
        // 게시글, 작성자 정보 가져오기
        async retrieveBoard() {
            try {
                let response = await BoardDetailService.getBoard(this.boardId);
                this.board = response.data;
                console.log("board 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveBoard 에러", e);
            }
        },
        // 코드번호로 코드명 가져오기
        async retrieveCode() {
            try {
                let response = await BoardDetailService.getCmCd(this.smcode);
                this.cmcd = response.data;
                console.log("code 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveCode 에러", e);
            }
        },
        // 글번호로 투표 가져오기
        async retrieveVote() { },
        // 글번호로 장소 가져오기
        async retrievePlace() { },
        // 글번호로 이미지 가져오기
        async retrieveImg() {
            try {
                let response = await BoardDetailService.getImg(this.boardId);
                this.boardImage = response.data;
                console.log("image 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveImg 에러", e);
            }
        },
        // 글번호로 댓글 가져오기
        async retrieveReply() {
            try {
                let response = await BoardDetailService.getReply(this.boardId);
                this.reply = response.data;
                console.log("reply 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveReply 에러", e);
            }
        },
        // 댓글 수 가져오기
        async retrieveReplyCount() {
            try {
                let response = await BoardDetailService.getReplyCount(this.boardId);
                this.replyCount = response.data;
                console.log("replyCount 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveReplyCount 에러", e);
            }
        }
    },
    mounted() {
        console.log("부서코드 : ", this.smcode, "/ 글번호 : ", this.boardId, "/ 로그인ID : ", this.member.memberId);
        console.log("우짤", this.$store.state.member);
        this.retrieveMember();
        this.retrieveBoard();
        this.retrieveCode();
        this.retrieveVote();
        this.retrievePlace();
        this.retrieveImg();
        this.retrieveReply();
        this.retrieveReplyCount();
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