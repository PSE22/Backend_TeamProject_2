<template>
    <div class="w-80 p-3 board-detail-container">
        <!-- 글 수정/삭제 버튼 : 글쓴이만 보임 -->
        <div class="row board-button">
            <button class="col">수정</button>
            <button class="col">삭제</button>
        </div>
        <!-- 게시글 -->
        <div class="row board-content">
            <!-- 글 상단 부분 -->
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
            <!-- 글 하단 버튼 -->
            <div class="row">
                <!-- 버튼 -->
                <div v-if="recommendIcon" @click="toggleRecommend" class="col-1" type="button"><i
                        class="bi bi-hand-thumbs-up"></i> {{ recommendCnt }} </div>
                <div v-else @click="toggleRecommend" class="col-1" type="button"><i
                        class="bi bi-hand-thumbs-up-fill"></i> {{ recommendCnt }} </div>
                <!-- 댓글 -->
                <div class="col-1" type="button"><i class="bi bi-chat-text"></i> {{ replyCount }} </div>
                <!-- 신고 -->
                <div class="col-1" type="button" data-bs-toggle="modal" data-bs-target="#reportModal"><i
                        class="bi bi-exclamation-triangle"></i>신고</div>
                <!-- Modal -->
                <div class="modal fade" id="reportModal" tabindex="-1" aria-labelledby="reportModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="reportModalLabel">게시글 신고</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
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
                                    <form class="was-validated">
                                        <textarea rows="10" class="form-control" placeholder="신고 사유를 입력하세요." required
                                            v-model="reportReason"></textarea>
                                        <div class="invalid-feedback">
                                            신고 사유는 필수 입력값입니다.
                                        </div>
                                    </form>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                <button type="button" class="btn btn-danger" @click="createReport">신고</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 댓글 목록 -->
        <div class="row reply-content">
            <ul v-for="(data, index) in reply" :key="index" class="list-group">
                <!-- 댓글 -->
                <li class="list-group-item">
                    <div>{{ data.memberName }}</div>
                    <div>{{ data.reply }}</div>
                    <img v-if="data.fileUrl" :src="data.fileUrl" height="200px" width="300px" alt="이미지">
                    <div class="row">
                        <div class="col-3"> {{ data.addDate }} </div>
                        <div class="col-1">수정</div>
                        <div class="col-1">삭제</div>
                        <!-- 댓글 신고 -->
                        <div class="col-1" type="button" data-bs-toggle="modal" data-bs-target="#reportReplyModal"  @click="openReplyReport(data)">
                            <i class="bi bi-exclamation-triangle"></i>신고</div>
                        <!-- Modal -->
                        <div class="modal fade" id="reportReplyModal" tabindex="-1" 
                            aria-labelledby="reportReplyModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="reportReplyModalLabel">댓글 신고</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-2 row">
                                            <label for="staticWriter" class="col-sm-2 col-form-label">작성자</label>
                                            <div class="col-sm-10">
                                                <input type="text" readonly class="form-control-plaintext"
                                                    id="staticWriter" v-model="reply.memberName">
                                            </div>
                                        </div>
                                        <div class="mb-2 row">
                                            <label for="staticTitle" class="col-sm-2 col-form-label">내용</label>
                                            <div class="col-sm-10">
                                                <input type="text" readonly class="form-control-plaintext"
                                                    id="staticTitle" v-model="reply.reply">
                                            </div>
                                        </div>
                                        <div class="mb-2">
                                            <hr />
                                            <form class="was-validated">
                                                <textarea rows="10" class="form-control" placeholder="신고 사유를 입력하세요."
                                                    required v-model="reportReason"></textarea>
                                                <div class="invalid-feedback">
                                                    신고 사유는 필수 입력값입니다.
                                                </div>
                                            </form>
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">닫기</button>
                                        <button type="submit" class="btn btn-danger"
                                            @click="createReplyReport">신고</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button @click="openReReply(data.replyId)">대댓글쓰기</button>
                    <!-- 대댓글 작성 폼 -->
                    <div v-if="parentId === data.replyId" class="row reply-write">
                        <div>{{ memberInfo.memberName }} (익명게시판은 별명으로 변경하세요)</div>
                        <textarea v-model="reReplyTextarea" placeholder="대댓글을 남겨보세요"></textarea>
                        <div class="row">
                            <i class="col bi bi-camera" type="button">파일선택</i>
                            <p class="col">파일명</p>
                            <button class="col" @click="createReReply(data.replyId)">대댓글등록버튼</button>
                        </div>
                    </div>
                </li>
                <!-- 대댓글 -->
                <div>
                    <li v-for="(reReply, index) in data.reReplies" :key="index" class="list-group-item"
                        style="background-color: red">
                        <div>{{ reReply.memberName }}</div>
                        <div>{{ reReply.reply }}</div>
                        <img v-if="reReply.fileUrl" :src="reReply.fileUrl" height="200px" width="300px" alt="이미지">
                        <div>
                            <span> {{ reReply.addDate }} </span>
                            <button>수정</button>
                            <button>삭제</button>
                            <button>신고</button>
                        </div>
                    </li>
                </div>
            </ul>
        </div>
        <!-- 댓글 작성 -->
        <div class="row reply-write">
            <div>{{ memberInfo.memberName }} (익명게시판은 별명으로 변경하세요)</div>
            <textarea v-model="replyTextarea" placeholder="댓글을 남겨보세요"></textarea>
            <div class="row">
                <i class="col bi bi-camera" type="button">파일선택</i>
                <p class="col">파일명</p>
                <button class="col" @click="createReply">댓글등록버튼</button>
            </div>
        </div>
        <!-- 목록으로 돌아가기 버튼 -->
        <div class="row">
            <button @click="this.$router.push('/board/dept/' + smcode)">목록</button>
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

            modalOpen: "modal",

            replyTextarea: "",          // 댓글 입력
            reReplyTextarea: "",        // 대댓글 입력
            parentId: "",               // 현재 대댓글의 상위 댓글의 replyId
            recommendIcon: true,        // 추천 아이콘 (true는 빈 아이콘)
            reportReason: "",           // 신고사유 입력

            auth: "",                   // 로그인 사용자 권한 체크
            memberInfo: "",             // 회원정보
            board: "",                  // 게시글 
            cmcd: "",                   // 부서코드, 부서명
            vote: "",                   // 투표
            place: "",                  // 장소
            boardImage: "",             // 글 첨부 이미지
            recommend: "",              // 추천 존재 여부
            recommendCnt: "",           // 추천 수
            reply: [],                  // 댓글 목록
            replyCount: "",             // 댓글수
        }
    },
    methods: {
        // 회원 권한 체크
        async checkAuth() {
            if (this.member.memberCode === "AT01") {
                // 관리자 로그인
                this.auth = "A"
            } else if (this.member.memberId) {
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
        async retrieveVote() {

        },
        // 글번호로 장소 가져오기
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
            } else {
                this.deleteRecommend().then(() => {
                    // 추천 삭제 후, 추천 수 다시 불러오기
                    this.retrieveRecommendCnt();
                }).catch(e => {
                    console.error("추천 삭제 실패: ", e);
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
        // 추천 삭제함수
        async deleteRecommend() {
            try {
                let response = await BoardDetailService.deleteRecommend(this.boardId, this.member.memberId);
                console.log(response.data);
            } catch (e) {
                console.log("deleteRecommend 에러", e);
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
                    alert("정말로 신고하시겠습니까?");
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
                    // 각 댓글 객체에 대댓글을 추가
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
        // 새 댓글 등록
        async createReply() {
            try {
                let temp = {
                    boardId: this.boardId,
                    memberId: this.member.memberId,
                    reply: this.replyTextarea,
                }
                let response = await ReplyService.createReply(temp);
                console.log("댓글 전송 : ", response.data);
                this.retrieveReply();
                this.retrieveReplyCount();
                this.replyTextarea = "";
            } catch (e) {
                console.log("createReply 에러", e);
            }
        },
        // 대댓글 쓰기 버튼 클릭 시 호출
        openReReply(replyId) {
            // 현재 댓글의 ID를 저장
            this.parentId = replyId;
        },
        // 새 대댓글 등록
        async createReReply(replyId) {
            try {
                let temp = {
                    boardId: this.boardId,
                    reReply: replyId,
                    memberId: this.member.memberId,
                    reply: this.reReplyTextarea,
                }
                let response = await ReplyService.createReply(temp);
                console.log("대댓글 전송 : ", response.data);
                this.retrieveReply();
                this.retrieveReplyCount();
                this.replyTextarea = "";
                this.parentId = "";
            } catch (e) {
                console.log(e);
            }
        },
        // 반복문의 현재 댓글 정보 (작성자, 내용)를 저장
        openReplyReport(data) {
            this.reply.memberName = data.memberName;
            this.reply.reply = data.reply;
        },
        // 댓글 신고 저장
        async createReplyReport() {
            try {
                if (!this.reportReason) {
                    alert("신고 사유를 입력해주세요.");
                } else {
                    alert("정말로 신고하시겠습니까?");
                    let report = {
                        memberId: this.member.memberId,
                        boardId: this.boardId,
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
    },
    mounted() {
        console.log("부서코드 : ", this.smcode, "/ 글번호 : ", this.boardId, "/ 로그인ID : ", this.member.memberId);
        this.retrieveMember();
        this.retrieveBoard();
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