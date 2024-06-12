<template>
    <div align="center">
      <h1 class="text-center mb-5 mt-5">자유 게시판</h1>
      <div class="container mt-3 free-box">
        <!-- 자유게시판 / 닉네임 입력 -->
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
                  v-model="isNoticeChecked"
                />
                <label class="form-check-label" for="noticeCheck">공지사항</label>
              </div>
            </div>
          </div>
  
          <!-- 장소 추가 -->
          <div class="row mt-3">
            <div class="col-md-3 mb-3 align-items-center d-flex">
              <div align="center">
                <button
                  type="button"
                  class="btn btn-outline-dark"
                  data-bs-toggle="modal"
                  data-bs-target="#place-modal-edit"
                  @click="relayout"
                >
                  <i class="bi bi-geo-alt"></i> 장소추가
                </button>
                <!-- 장소 Modal -->
                <div
                  class="modal fade"
                  id="place-modal-edit"
                  tabindex="-1"
                  aria-labelledby="exampleModalLabel"
                  aria-hidden="true"
                >
                  <div class="modal-dialog modal-lg modal-dialog-centered">
                    <div class="modal-content">
                      <div class="modal-header">
                        <div class="row">
                          <div class="col-auto">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">
                              장소 추가
                            </h1>
                          </div>
  
                          <div class="col-auto">
                            <input
                              class="form-control"
                              type="text"
                              v-model="address"
                              placeholder="주소검색 사용"
                              @keypress.enter="openPostcode"
                              disabled
                            />
                          </div>
                          <div class="col-auto">
                            <input
                              type="button"
                              @click="openPostcode"
                              value="주소 검색"
                              class="btn btn-dark"
                            />
                          </div>
                        </div>
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
                          ref="mapContainer"
                          style="
                            width: 100%;
                            height: 500px;
                            margin-top: 10px;
                            display: none;
                          "
                        ></div>
                      </div>
                      <div class="modal-footer">
                        <button
                          type="button"
                          class="btn btn-secondary"
                          @click="resetPlace"
                          data-bs-dismiss="modal"
                        >
                          초기화
                        </button>
                        <button
                          type="button"
                          class="btn btn-primary"
                          data-bs-dismiss="modal"
                          @click="placeEdit"
                        >
                          확인
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- 장소 Modal 끝 -->
              </div>
            </div>
            <!-- 기존 주소명 -->
            <div class="col-md-4 mb-4 mb-md-0" v-if="!placeExists">
              <span>{{ address.address }}</span>
            </div>
            <!-- 신규 주소명 -->
            <div class="col-md-4 mb-4 mb-md-0" v-if="placeExists">
              <span>{{ address.address }}</span>
            </div>
          </div>
        </div>
  
        <!-- 안내 메시지 -->
        <div class="row" v-if="voteExists">
          <div class="col-md-12">
            <div class="alert alert-info" role="alert">
              {{ vote.voteName }}
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
              <!-- 새로 업로드된 파일 -->
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
              <!-- 기존 파일 -->
              <div
                v-for="(existingFile, index) in existingFiles"
                :key="'existing_' + index"
                class="file-item"
              >
                <p class="d-inline">{{ existingFile.fileUrl }}</p>
                <button
                  type="button"
                  class="btn btn-danger btn-sm ms-2"
                  @click="removeExistingFile(index)"
                >
                  x
                </button>
              </div>
            </div>
          </div>
        </div>
  
        <!-- 수정 버튼 -->
        <div class="fixed-button">
          <button type="button" class="btn btn-primary" @click="editBoard">
            수정
          </button>
          <button type="button" class="btn btn-danger" @click="deleteBoard">
          취소
        </button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  let daum = window.daum;
  import BoardEditService from "@/services/board/BoardEditService";
  import BoardWrite from "@/services/board/BoardWriteService";
  
  export default {
    data() {
      return {
        member: this.$store.state.member, // 현재 로그인된 회원 가져오기
        boardId: this.$route.params.boardId, // 현재 글 ID 가져오기
  
        board: {
          noticeYn: "N",
          bocodeName: "", // board 객체에 bocodeName 속성 추가
        }, // 게시글
        vote: {},
        voteExists: false, // 투표가 생성되었는지 여부를 저장하는 변수
  
        place: "", // 장소
        placeExists: false, // 장소가 등록되었는지 여부를 저장하는 변수
        boardImage: "", // 글 첨부 이미지
        files: [], // 파일 리스트를 저장하기 위한 배열
  
        existingFiles: [], // 기존 파일 목록을 저장하는 배열
  
        address: "", // 주소 데이터
        map: null,
        geocoder: null,
        marker: null,
      };
    },
    methods: {
      async retrieveMember() {
        try {
          let response = await BoardEditService.getMember(this.member.memberId);
          this.memberInfo = response.data;
          console.log("memberInfo 데이터 : ", response.data);
        } catch (e) {
          console.log("retrieveMember 에러", e);
        }
      },
      async retrieveBoard() {
        try {
          let response = await BoardEditService.getBoard(this.boardId);
          this.board = response.data;
          // 공지사항 여부를 초기화
          if (this.board.noticeYn !== "Y") {
            this.board.noticeYn = "N";
          }
          console.log("board 데이터 : ", response.data);
        } catch (e) {
          console.log("retrieveBoard 에러", e);
        }
      },
      async retrieveUpCode() {
        try {
          let response = await BoardEditService.getCmCd("BO01"); // BO01 코드를 가져오도록 수정
          // 부서게시판 코드명을 board 객체에 할당합니다.
          this.board.bocodeName = response.data.cmCdName;
          console.log("부서게시판 코드명: ", this.board.bocodeName);
        } catch (e) {
          console.log("retrieveCode 에러", e);
        }
      },
      async retrieveVote() {},
      async retrievePlace() {
        try {
          const response = await BoardEditService.getPlace(this.boardId);
          if (response.data) {
            // 장소가 있으면 가져온 장소를 데이터에 할당합니다.
            this.address = response.data;
          } else {
            this.placeExists = false;
          }
        } catch (error) {
          console.error("장소를 가져오는 중 에러 발생:", error);
        }
      },
      placeEdit() {
        this.placeExists = true;
        console.log("장소 어디?? : ", this.address.address);
      },
      async retrieveImg() {
        try {
          let response = await BoardEditService.getImg(this.boardId);
          this.existingFiles = response.data; // 기존 파일 목록을 업데이트합니다.
          console.log("기존 이미지 데이터 : ", response.data);
        } catch (e) {
          console.log("retrieveImg 에러", e);
        }
      },
      handleFileUpload(event) {
        const newFiles = Array.from(event.target.files);
        this.files = this.files.concat(
          newFiles.map((file) => ({
            name: file.name,
            data: file,
          }))
        );
      },
      removeFile(index) {
        this.files.splice(index, 1);
  
        if (this.files.length === 0) {
          this.$refs.fileInput.value = "";
        }
      },
      removeExistingFile(index) {
        this.existingFiles.splice(index, 1);
      },
      loadDaumPostcodeScript() {
        const script = document.createElement("script");
        script.src =
          "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
        document.head.appendChild(script);
      },
      loadKakaoMapScript() {
        const script = document.createElement("script");
        script.src =
          "//dapi.kakao.com/v2/maps/sdk.js?appkey=55b411073309a73c48d56caa594311c8&libraries=services";
        script.onload = this.initMap;
        document.head.appendChild(script);
      },
      initMap() {
        const mapContainer = this.$refs.mapContainer;
        const mapOption = {
          center: new daum.maps.LatLng(37.537187, 127.005476),
          level: 5,
        };
        this.map = new daum.maps.Map(mapContainer, mapOption);
        this.geocoder = new daum.maps.services.Geocoder();
        this.marker = new daum.maps.Marker({
          position: new daum.maps.LatLng(37.537187, 127.005476),
          map: this.map,
        });
      },
      openPostcode() {
        new daum.Postcode({
          oncomplete: (data) => {
            this.address = data.address;
            this.geocoder.addressSearch(data.address, (results, status) => {
              if (status === daum.maps.services.Status.OK) {
                const result = results[0];
                const coords = new daum.maps.LatLng(result.y, result.x);
                this.$refs.mapContainer.style.display = "block";
                this.map.relayout();
                this.map.setCenter(coords);
                this.marker.setPosition(coords);
              }
            });
          },
        }).open();
      },
      resetPlace() {
        this.address = "";
        if (this.map) {
          this.map.setCenter(new daum.maps.LatLng(37.537187, 127.005476));
          this.map.relayout();
          this.marker.setPosition(new daum.maps.LatLng(37.537187, 127.005476));
        }
        this.$refs.mapContainer.style.display = "none";
      },
      async editBoard() {
        try {
          // Prepare the data for update
          const boardDto = this.board;
          const voteDtos = this.voteExists ? [this.vote] : [];
          const fileDtos = this.files.map((file) => file.data);
          const placeDto = this.placeExists ? this.address : null;
  
          // Call the update function
          await BoardWrite.update(boardDto, voteDtos, fileDtos, placeDto);
          alert("게시글이 수정되었습니다.");
          this.$router.push(`/board/${this.boardId}`); // Redirect to the board detail page
        } catch (error) {
          console.error("게시글 수정 중 에러 발생:", error);
          alert("게시글 수정 중 에러가 발생했습니다.");
        }
      },
    },
    computed: {
      minDate() {
        const today = new Date();
        const tomorrow = new Date(today);
        tomorrow.setDate(tomorrow.getDate()); // 내일 날짜 계산
        return tomorrow.toISOString().slice(0, 10); // YYYY-MM-DD 형식으로 변환
      },
      isNoticeChecked: {
        get() {
          return this.board.noticeYn === "Y";
        },
        set(value) {
          this.board.noticeYn = value ? "Y" : "N";
        },
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
      this.retrieveUpCode();
      this.retrieveVote();
      this.retrievePlace();
      this.retrieveImg();
      this.loadDaumPostcodeScript();
      this.loadKakaoMapScript();
    },
  };
  </script>
  
  <style>
.free-box {
  margin: 100px auto;
  background-color: rgba(255, 255, 255, 1);
  padding: 40px 30px;
  border: 1px solid #959595;
  width: 1000px;
  position: relative; /* 부모 요소를 기준으로 위치를 설정합니다 */
  border-radius: 10px;
}

.fixed-button {
  position: absolute;
  bottom: -60px; /* free-box 아래에 위치를 조정 (간격 조정) */
  right: 0px; /* 오른쪽 끝에 위치를 조정 */
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
  