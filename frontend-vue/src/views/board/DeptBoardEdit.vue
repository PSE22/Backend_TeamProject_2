<template>
  <div align="center">
    <h1 class="text-center mb-5 mt-5">부서 게시판</h1>
    <div class="container mt-3 free-box">
      <!-- 부서게시판 / 닉네임 입력 -->
      <div align="center">
        <div class="row">
          <div class="col-4">
            <input
              type="text"
              class="form-control"
              disabled
              v-model="board.bocodeName"
            />
          </div>
          <div class="col-4">
            <input
              type="text"
              class="form-control"
              disabled
              v-model="cmcd.cmcdName"
            />
          </div>
        </div>

        <!-- 제목 / 공지사항 -->
        <div class="row mt-3">
          <div class="col-md-10">
            <input
              type="text"
              class="form-control"
              placeholder="제목을 입력해주세요"
              name="boardTitle"
              v-model="board.boardTitle"
            />
          </div>
          <div class="col-md-2 d-flex align-items-center justify-content-end">
            <div class="form-check">
              <input
                class="form-check-input"
                type="checkbox"
                v-model="board.noticeYn"
              />
              <label class="form-check-label" for="noticeCheck">공지사항</label>
            </div>
          </div>
        </div>

        <!-- 투표 추가 / 장소 추가 -->
        <div class="row mt-3">
          <div class="col-md-2 mb-3">
            <div class="form-group">
              <!-- Button trigger modal -->
              <button
                type="button"
                class="btn btn-outline-dark"
                data-bs-toggle="modal"
                data-bs-target="#vote-modal"
              >
                <i class="bi bi-bar-chart-line"></i> 투표추가
              </button>
              <!-- 투표추가 모달 -->
              <div
                class="modal fade"
                id="vote-modal"
                tabindex="-1"
                aria-labelledby="exampleModalLabel"
                aria-hidden="true"
              >
                <div class="modal-dialog modal-dialog-centered">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h1 class="modal-title fs-5" id="exampleModalLabel">
                        투표 등록
                      </h1>
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div class="modal-body">
                      <h5 class="text-start">투표명</h5>
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="제목을 입력하세요"
                        name="boardTitle"
                        v-model="board.voteName"
                      />
                      <hr />
                      <h5 class="text-start">항목 추가</h5>
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="1. 항목을 입력하세요"
                        name="boardTitle"
                        v-model="vote.voteList.vote1"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="2. 항목을 입력하세요"
                        name="boardTitle"
                        v-model="vote.voteList.vote2"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="3. 항목을 입력하세요"
                        name="boardTitle"
                        v-model="vote.voteList.vote3"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="4. 항목을 입력하세요"
                        name="boardTitle"
                        v-model="vote.voteList.vote4"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="5. 항목을 입력하세요"
                        name="boardTitle"
                        v-model="vote.voteList.vote5"
                      />
                      <hr />
                      <h5 class="text-start">
                        종료일 : <input type="date" v-model="selectedDaily" />
                      </h5>
                    </div>
                    <div class="modal-footer">
                      <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                      >
                        취소
                      </button>
                      <button
                        type="button"
                        class="btn btn-primary"
                        @click="voteList"
                      >
                        등록
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              <!-- 투표추가 모달 끝 -->
            </div>
          </div>
          <div class="col-md-2 mb-3">
            <div align="center">
              <button
                type="button"
                class="btn btn-outline-dark"
                data-bs-toggle="modal"
                data-bs-target="#place-modal"
                @click="relayout"
              >
                <i class="bi bi-geo-alt"></i> 장소추가
              </button>

              <!-- Modal -->
              <div
                class="modal fade"
                id="place-modal"
                tabindex="-1"
                aria-labelledby="exampleModalLabel"
                aria-hidden="true"
              >
                <div class="modal-dialog modal-lg modal-dialog-centered">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h1 class="modal-title fs-5" id="exampleModalLabel">
                        장소 추가
                      </h1>
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div class="modal-body">
                      <div
                        id="map"
                        style="width: 100%; height: 500px"
                        ref="map"
                      ></div>
                    </div>
                    <div class="modal-footer">
                      <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                      >
                        취소
                      </button>
                      <button type="button" class="btn btn-primary">
                        확인
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- <KakaoMap/> -->
        </div>
        <div class="col-md-3 mb-3 mb-md-0">
          <div class="form-group">
            <span class="d-block">그린컴퓨터아카데미</span>
          </div>
        </div>
      </div>

      <!-- 안내 메시지 -->
      <div class="row">
        <div class="col-md-12">
          <div class="alert alert-info" role="alert">
            "투표명" 투표가 등록되었습니다.
          </div>
        </div>
      </div>

      <!-- 내용 입력 -->
      <div class="row">
        <div class="col-md-12">
          <textarea
            class="form-control"
            placeholder="내용을 입력해주세요"
            rows="5"
            name="boardContent"
            v-model="board.boardContent"
          ></textarea>
        </div>
      </div>

      <!-- 파일 첨부 -->
      <div class="row mt-3">
        <div class="col-md-12">
          <input
            type="file"
            class="form-control"
            multiple
            @change="handleFileUpload"
            ref="fileInput"
          />
          <div class="mt-2 file-list">
            <div v-for="(file, index) in files" :key="index" class="file-item">
              <p class="d-inline">{{ file.name }}</p>
              <button
                type="button"
                class="btn btn-danger btn-sm ms-2"
                @click="removeFile(index)"
              >
                X
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- 수정 버튼 -->
      <div class="fixed-button-container">
        <button type="button" class="btn btn-primary" @click="editBoard">
          수정
        </button>
        <button type="button" class="btn btn-danger" @click="deleteBoard">
          삭제
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import BoardDetailService from "@/services/board/BoardDetailService";
// import KakaoMap from "@/components/map/KakaoMap.vue";
export default {
  components: {
    // KakaoMap,
  },
  data() {
    return {
      member: this.$store.state.member, // 현재 로그인된 회원 가져오기
      boardId: this.$route.params.boardId, // 현재 글 ID 가져오기
      smcode: this.$route.params.smcode, // 현재 소메뉴 코드 가져오기

      auth: "", // 로그인 사용자 권한 체크
      memberInfo: "", // 회원정보
      board: {}, // 게시글
      cmcd: "", // 부서코드, 부서명
      vote: {
        // vote 객체를 빈 객체로 초기화합니다.
        voteList: {
          vote1: "",
          vote2: "",
          vote3: "",
          vote4: "",
          vote5: "",
        },
      },
      place: "", // 장소
      boardImage: "", // 글 첨부 이미지
      files: [], // 파일 리스트를 저장하기 위한 배열

      selectBocode: "",
    };
  },
  methods: {
    // 회원 권한 체크
    async checkAuth() {
      if (this.member.memberCode === "AT01") {
        // 관리자 로그인
        this.auth = "A";
      } else if (this.member.memberId) {
        this.auth = "B";
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
        let response = await BoardDetailService.getCmCd("BO01"); // BO01 코드를 가져오도록 수정
        // 부서게시판 코드명을 board 객체에 할당합니다.
        this.board.bocodeName = response.data.cmCdName;
        console.log("부서게시판 코드명: ", this.board.bocodeName);
      } catch (e) {
        console.log("retrieveCode 에러", e);
      }
    },
    // 글번호로 투표 가져오기
    async retrieveVote() {},
    // 글번호로 장소 가져오기
    async retrievePlace() {},
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
    // 파일추가
    handleFileUpload(event) {
      const newFiles = Array.from(event.target.files);
      this.files = this.files.concat(
        newFiles.map((file) => ({
          name: file.name,
          data: file,
        }))
      );
    },
    // 파일 선택 취소
    removeFile(index) {
      this.files.splice(index, 1);

      // 파일이 없을 경우 input 초기화
      if (this.files.length === 0) {
        this.$refs.fileInput.value = "";
      }
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
    this.retrieveMember();
    this.retrieveBoard();
    this.retrieveCode();
    this.retrieveVote();
    this.retrievePlace();
    this.retrieveImg();
  },
};
</script>

<style>
.free-box {
  margin: 100px auto;
  background-color: rgba(255, 255, 255, 1);
  padding: 40px 30px;
  border: 3px solid #505050;
  width: 800px;
  position: relative; /* 부모 요소를 기준으로 위치를 설정합니다 */
}

.fixed-button-container {
  position: absolute;
  bottom: -60px; /* free-box 아래에 위치를 조정 (간격 조정) */
  right: 0px; /* 오른쪽 끝에 위치를 조정 */
  display: flex;
  gap: 10px;
}

.file-list p {
  margin-bottom: 0;
}

.file-list .file-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
