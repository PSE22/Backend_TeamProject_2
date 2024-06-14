<template>
    <div class="container mt-3 board-detail-container">
        <!-- 글 수정/삭제 버튼 : 글쓴이만 보이게 -->
        <div class="row mb-3 board-button">
            <button class="col-1 btn btn-warning me-2" @click="moveToDeptEdit">수정</button>
            <button class="col-1 btn btn-danger">삭제</button>
        </div>
        <!-- 게시글 -->
        <div class="card mb-3">
            <div class="card-header text-white">
                부서게시판 <i class="bi bi-caret-right"></i> {{ cmcd?.cmCdName }}
            </div>
            <div class="card-body">
                <h5 class="card-title">{{ board?.boardTitle }}</h5>
                <p class="card-text-name">{{ board?.memberName }}</p>
                <p class="card-text-date">{{ board?.addDate }}</p>
                <hr>
                <p class="card-text-content">{{ board?.boardContent }}</p>
                <div class="board-images mb-3">
                    <div v-for="(data, index) in boardImage" :key="index" class="mb-2">
                        <img :src="data.fileUrl" class="img-fluid" alt="이미지">
                    </div>
                </div>
                <hr>
                <div class="d-flex justify-content-start">
                    <!-- 추천 아이콘 -->
                    <div v-if="recommendIcon" @click="toggleRecommend" class="me-3" type="button"><i
                            class="bi bi-hand-thumbs-up"></i> {{ recommendCnt }} </div>
                    <div v-else class="me-3" type="button"><i class="bi bi-hand-thumbs-up-fill"></i> {{ recommendCnt }}
                    </div>
                    <!-- 댓글 아이콘 -->
                    <div class="me-3" type="button"><i class="bi bi-chat-text"></i> {{ replyCount }} </div>
                    <!-- 신고 아이콘 -->
                    <div class="me-3" type="button" data-bs-toggle="modal" data-bs-target="#reportModal"><i
                            class="bi bi-exclamation-triangle"></i>신고</div>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="reportModal" tabindex="-1" aria-labelledby="reportModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form class="was-validated">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="reportModalLabel">게시글 신고</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-2 row">
                                <label for="staticWriter" class="col-sm-2 col-form-label">작성자</label>
                                <div class="col-sm-10">
                                    <input type="text" readonly class="form-control-plaintext" id="staticWriter"
                                        v-model="board.memberName">
                                </div>
                            </div>
                            <div class="mb-2 row">
                                <label for="staticTitle" class="col-sm-2 col-form-label">내용</label>
                                <div class="col-sm-10">
                                    <input type="text" readonly class="form-control-plaintext" id="staticTitle"
                                        v-model="board.boardTitle">
                                </div>
                            </div>
                            <div class="mb-2">
                                <hr />
                                <textarea rows="10" class="form-control" placeholder="신고 사유를 입력하세요." required
                                    v-model.lazy="reportReason"></textarea>
                                <div class="invalid-feedback">
                                    신고 사유는 필수 입력값입니다.
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                            <button type="submit" class="btn btn-danger" @click="createReport">신고</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>












        <!-- 댓글 목록 -->
        <div class="reply-content mb-3">
            <ul v-for="(data, index) in reply" :key="index" class="list-group mb-3">
                <!-- 댓글 -->
                <li v-if="data.replyId" class="list-group-item">
                    <div class="reply-name">{{ data.memberName }}</div>
                    <div class="reply-content">{{ data.reply }}</div>
                    <img v-if="data.fileUrl" :src="data.fileUrl" class="img-fluid img-thumbnail w-25" alt="이미지" />
                    <div class="d-flex justify-content-between mt-2">
                        <div class="reply-date">{{ data.addDate }}</div>
                        <div>
                            <button class="btn btn-secondary me-3" @click="openReplyUpdate(data.replyId)">수정</button>
                            <button class="btn btn-secondary me-3">삭제</button>
                            <button class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#reportReplyModal"
                                @click="openReplyReport(data)">
                                <i class="bi bi-exclamation-triangle"></i>신고
                            </button>
                        </div>
                    </div>
                    <button class="btn btn-secondary mt-2" @click="openReReply(data.replyId)">대댓글쓰기</button>

                    <!-- 대댓글 작성 폼 (대댓글쓰기 버튼 클릭 시 나타남) -->
                    <div v-if="showWriteReReply && parentId === data.replyId" class="card mb-3 mt-3">
                        <div class="card-body">
                            <div class="reply-name">{{ memberInfo.memberName }} (익명게시판은 별명으로 변경하세요)</div>

                            <textarea v-model.lazy="reReplyTextarea" placeholder="대댓글을 남겨보세요"
                                class="form-control mb-2 reply-content"></textarea>
                            <div class="d-flex justify-content-between">
                                <!-- 파일첨부 -->
                                <input class="form-control file-upload-input w-auto" type="file"
                                    @change="selectReReplyFile($event)" />
                                <button class="btn btn-primary file-upload-button"
                                    @click="createReReply(data.replyId)">등록</button>
                            </div>
                        </div>
                    </div>
                </li>
                <!-- 댓글 수정 버튼 클릭 시 보일 부분 -->
                <li v-if="replyUpdate && replyUpdateId === data.replyId" class="list-group-item">
                    <div class="reply-name">{{ data.memberName }} </div>
                    <textarea v-model.lazy="data.reply" placeholder="댓글을 남겨보세요"
                        class="form-control mb-2 reply-content"></textarea>
                    <div class="d-flex">
                        <!-- 파일첨부 -->
                        <input class="form-control file-upload-input w-auto mb-2" type="file" ref="replyFile"
                            @change="selectReplyFile" />
                    </div>
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-secondary" @click="replyUpdate = true">취소</button>
                        <button class="btn btn-secondary" @click="updateReply(data)">등록</button>
                    </div>
                </li>

                <!-- 대댓글 -->
                <li v-for="(reReply, index) in data.reReplies" :key="index" class="list-group-item reReply-container">
                    <div class="reply-name"><i class="bi bi-arrow-return-right"></i> {{ reReply.memberName }} </div>
                    <div class="reply-content">{{ reReply.reply }}</div>
                    <img v-if="reReply.fileUrl" :src="reReply.fileUrl" class="img-fluid img-thumbnail w-25" alt="이미지" />
                    <div class="d-flex justify-content-between mt-2">
                        <div class="reply-date">{{ reReply.addDate }}</div>
                        <div>
                            <button class="btn btn-secondary me-2">수정</button>
                            <button class="btn btn-secondary me-2">삭제</button>
                            <button class="btn btn-secondary" data-bs-toggle="modal"
                                data-bs-target="#reportReReplyModal" @click="openReplyReport(reReply)">
                                <i class="bi bi-exclamation-triangle"></i>신고
                            </button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>














        <!-- 댓글 작성 -->
        <div class="card mb-3">
            <div class="card-body">
                <div class="reply-name">{{ memberInfo.memberName }} (익명게시판은 별명으로 변경하세요)</div>
                <textarea v-model.lazy="replyTextarea" placeholder="댓글을 남겨보세요"
                    class="form-control mb-2 reply-content"></textarea>
                <div class="d-flex justify-content-between">
                    <!-- 파일첨부 -->
                    <input class="form-control file-upload-input w-auto" type="file" ref="replyFile"
                        @change="selectReplyFile" />
                    <button class="btn btn-primary file-upload-button" @click="createReply">등록</button>
                </div>
            </div>
        </div>
        <!-- 목록으로 돌아가기 버튼 -->
        <div class="d-grid">
            <button class="col-1 btn btn-secondary" @click="this.$router.push('/board/dept/' + smcode)">목록</button>
        </div>
    </div>
</template>


<script>
import BoardDetailService from '@/services/board/BoardDetailService';
import ReplyService from '@/services/board/ReplyService';

export default {
    data() {
        return {
            member: this.$store.state.member,       // 현재 로그인된 회원 가져오기
            boardId: this.$route.params.boardId,    // 현재 글 ID 가져오기
            smcode: this.$route.params.smcode,      // 현재 소메뉴 코드 가져오기

            auth: "",                     // 로그인 사용자 권한 체크
            replyTextarea: "",
            reReplyTextarea: "",
            parentId: "",                 // 현재 대댓글의 상위 댓글의 replyId
            replyUpdateId: null,          // 현재 수정 중인 댓글 ID
            recommendIcon: true,          // 추천 아이콘 (true는 빈 아이콘)
            reportReason: "",             // 신고사유 입력
            replyUpdate: false,            // true 이면 댓글 수정란 보이게
            currentFile: undefined,       // 댓글파일선택
            currentReFile: undefined,     // 대댓글파일선택
            showWriteReReply: false,      // 대댓글쓰기

            // retrieve 
            memberInfo: "", // 회원정보
            board: "", // 게시글
            cmcd: "", // 부서코드, 부서명
            vote: "", // 투표
            place: "", // 장소
            boardImage: "", // 글 첨부 이미지
            recommend: "", // 추천 존재 여부
            recommendCnt: "", // 추천 수
            reply: [], // 댓글 목록
            replyCount: "", // 댓글수
        };
    },
    methods: {
        // 회원 권한 체크
        async checkAuth() {
            if (this.member?.memberCode === "AT01") {
                // 관리자 로그인
                this.auth = "A";
            } else if (this.member?.memberCode === "AT02") {
                if (this.member?.memberId === this.board?.memberId) {
                    // 글쓴이 로그인
                    this.auth = "B";
                }
            } else {
                // 기타 회원
                this.auth = "C";
            }
        },
        // 로그인된 회원 정보 가져오기
        async retrieveMember() {
            try {
                let response = await BoardDetailService.getMember(this.member.memberId);
                this.memberInfo = response.data;
                console.log("memberInfo 데이터 : ", response.data);
                this.checkAuth();
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
        // ❎ 글번호로 투표 가져오기
        async retrieveVote() {

        },
        // ❎ 글번호로 장소 가져오기
        async retrievePlace() {

        },
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
        // 추천 데이터 존재 여부 가져오기
        async retrieveRecommend() {
            try {
                let response = await BoardDetailService.getRecommend(this.boardId, this.member.memberId);
                this.recommend = response.data;
                console.log("recommend 데이터 : ", response.data);
                if (response.data === 1) {
                    this.recommendIcon = false;
                } else {
                    this.recommendIcon = true;
                }
            } catch (e) {
                console.log("retrieveRecommend 에러", e);
            }
        },
        // 추천 버튼 클릭 시 호출 
        toggleRecommend() {
            this.recommendIcon = !this.recommendIcon;
            if (this.recommendIcon == false) {
                this.saveRecommend().then(() => {
                    // 추천 저장 후, 추천 수 다시 불러오기
                    this.retrieveRecommendCnt();
                }).catch(e => {
                    console.error("추천 저장 실패:", e);
                });
            }
        },
        // 추천 저장함수
        async saveRecommend() {
            try {
                let recommend = {
                    boardId: this.boardId,
                    memberId: this.member.memberId
                };
                let response = await BoardDetailService.createRecommend(recommend);
                console.log(response.data);
            } catch (e) {
                console.log("saveRecommend 에러", e);
            }
        },
        // 추천 수 가져오기
        async retrieveRecommendCnt() {
            try {
                let response = await BoardDetailService.getRecommendCnt(this.boardId);
                this.recommendCnt = response.data;
                console.log("추천 수 : ", response.data);
            } catch (e) {
                console.log("retrieveRecommendCnt 에러", e)
            }
        },
        // 글 신고 저장
        async createReport() {
            try {
                if (!this.reportReason) {
                    alert("신고 사유를 입력해주세요.");
                } else {
                    let report = {
                        memberId: this.member.memberId,
                        boardId: this.boardId,
                        reportReason: this.reportReason,
                    }
                    let response = await BoardDetailService.createReport(report);
                    console.log(response.data);
                    alert("신고가 완료되었습니다.");
                    this.reportReason = "";
                }
            } catch (e) {
                console.log("createReport 에러", e);
            }
        },
        // 글번호로 댓글 가져오기
        async retrieveReply() {
            try {
                let response = await ReplyService.getReply(this.boardId);
                this.reply = response.data;

                // 각 댓글에 대한 대댓글 가져오기
                for (let i = 0; i < this.reply.length; i++) {
                    let comment = this.reply[i];
                    // 대댓글 가져오기
                    let reReplyResponse = await ReplyService.getReReply(this.boardId, comment.replyId);
                    // 각 댓글 객체에 대댓글 객체 추가
                    this.reply[i].reReplies = reReplyResponse.data;
                }
                console.log("reply 데이터 : ", this.reply);
            } catch (e) {
                console.log("retrieveReply 에러", e);
            }
        },
        // 댓글 수 가져오기
        async retrieveReplyCount() {
            try {
                let response = await ReplyService.getReplyCount(this.boardId);
                this.replyCount = response.data;
                console.log("replyCount 데이터 : ", response.data);
            } catch (e) {
                console.log("retrieveReplyCount 에러", e);
            }
        },
        // 반복문의 현재 댓글 정보(작성자, 댓글ID, 댓글내용)를 저장
        openReplyReport(reReply) {
            this.reply.memberName = reReply.memberName;
            this.reply.replyId = reReply.replyId;
            this.reply.reply = reReply.reply;
        },
        // 댓글 신고 저장
        async createReplyReport() {
            try {
                if (!this.reportReason) {
                    alert("신고 사유를 입력해주세요.");
                } else {
                    let report = {
                        memberId: this.member.memberId,
                        replyId: this.reply.replyId,
                        reportReason: this.reportReason,
                    }
                    let response = await ReplyService.createReplyReport(report);
                    console.log(response.data);
                    alert("신고가 완료되었습니다.");
                    this.reportReason = "";
                }
            } catch (e) {
                console.log("createReplyReport 에러", e);
            }
        },


        // 댓글 파일 선택상자에서 파일 선택
        selectReplyFile() {
            this.currentFile = this.$refs.replyFile.files[0];
            console.log("댓글 파일 선택 :::", this.currentFile);
        },
        // 댓글 + 파일 저장
        async createReply() {
            try {
                let temp = {
                    boardId: this.boardId,
                    memberId: this.member.memberId,
                    reply: this.replyTextarea,
                    reReply: ""
                }
                let response = await ReplyService.createReply(temp, this.currentFile);
                console.log("댓글 전송 : ", response);
                this.retrieveReply();
                this.retrieveReplyCount();
                this.replyTextarea = "";
                this.currentFile = undefined;
            } catch (e) {
                // 현재 선택된 이미지 변수 초기화
                this.currentFile = undefined;
                console.log(e);
            }
        },
        // 대댓글 쓰기 버튼 클릭 시 호출
        openReReply(replyId) {
            this.parentId = replyId;     // 현재 댓글ID를 저장
            this.showWriteReReply = !this.showWriteReReply;     // 대댓글쓰기창 토글
        },
        // 대댓글 파일 선택상자에서 파일 선택
        selectReReplyFile() {
            this.currentReFile = event.target.files[0];
            console.log("대댓글 파일 선택 ::: ", this.currentReFile);
        },
        // 대댓글 + 파일 저장
        async createReReply(reReplyData) {
            try {
                let temp = {
                    boardId: this.boardId,
                    memberId: this.member.memberId,
                    reply: this.reReplyTextarea,
                    reReply: reReplyData,
                }
                let response = await ReplyService.createReply(temp, this.currentReFile);
                console.log("대댓글 전송 : ", response);
                this.retrieveReply();
                this.retrieveReplyCount();
                this.replyTextarea = "";
                this.currentReFile = undefined;
            } catch (e) {
                this.currentReFile = undefined;
                console.log(e);
            }
        },






        // ❎ 댓글 수정 버튼 클릭 시 호출
        openReplyUpdate(replyId) {
            this.replyUpdate = true;
            this.replyUpdateId = replyId;
        },
        // ❎ 댓글 수정 후 등록
        async updateReply(data) {
            try {
                let temp = {
                    reply: data.reply
                }
                let response = await ReplyService.updateReply(temp, this.currentFile);
                alert("댓글이 수정되었습니다.");
                console.log("댓글 수정 : ", response.data);
                this.retrieveReply();
                this.retrieveReplyCount();
            } catch (e) {
                console.log("updateReply 에러", e);
            }
        },

        // 글 수정 페이지로 이동
        moveToDeptEdit() {
            this.$router.push(`/board/dept-edit/${this.smcode}/${this.boardId}`);
        },
    },
    mounted() {
        console.log(
            "부서코드 : ",
            this.smcode,
            "/ 글번호 : ",
            this.boardId,
            "/ 로그인ID : ",
            this.member.memberId
        );
        console.log("권한 ::: ", this.auth);
        this.retrieveMember();
        this.retrieveBoard();
        this.checkAuth();
        this.retrieveCode();
        this.retrieveVote();
        this.retrievePlace();
        this.retrieveImg();
        this.retrieveRecommend();
        this.retrieveRecommendCnt();
        this.retrieveReply();
        this.retrieveReplyCount();
    },
}
</script>

<style scoped>
/* 전체 컨테이너 */
.board-detail-container {
    background-color: #f8f9fa;
    padding: 20px;
    border-radius: 10px;
}

/* 게시글 수정/삭제 버튼 */
.board-button .btn {
    border-radius: 50px;
    font-weight: bold;
}

/* 게시판 헤더 */
.card-header {
    background-color: #b3000f;
    color: white;
    font-size: 1.2em;
}

/* 글 제목 */
.card-title {
    margin-bottom: 15px;
    font-weight: 600;
}

/* 게시판 작성자 부분 */
.card-text-name {
    font-size: 0.8em;
    margin-bottom: 5px;
    font-weight: bold;
}

/* 게시판 작성일 부분 */
.card-text-date {
    font-size: 0.8em;
    margin-bottom: 10px;
}

/* 댓글 목록 */
.reply-content .list-group-item {
    padding: 20px 30px;
    border: none;
    border-bottom: 1px solid #e9ecef;
}

/* 댓글 작성자 */
.reply-name {
    margin-bottom: 5px;
    font-weight: bold;
}

/* 댓글 내용 */
.reply-content {
    margin-bottom: 10px;
}

/* 댓글 작성일 */
.reply-date {
    font-size: 0.9em;
    margin-bottom: 10px;
}

/* 대댓글 */
.reReply-container {
    padding: 20px 30px;
    background-color: rgb(218, 218, 218);
}

/* 모달창 */
.modal-content {
    border-radius: 10px;
}

/* 모달 헤더 */
.modal-header {
    background-color: #b3000f;
    color: white;
}

/* 파일 업로드 input */
.file-upload-input {
    /* width: 600px; */
}

/* 파일 업로드 버튼 */
.file-upload-button {
    width: 100px;
    display: inline-block;
}

.btn-danger {
    background-color: #b3000f;
    border-color: #b3000f;
}
</style>
