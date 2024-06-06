<template>
  <div align="center" class="mt-5">
    <h2>자유 게시판</h2>
  </div>
  <div class="container mt-3 free-box">
    <!-- 자유게시판 / 닉네임 -->
    <div align="center">
      <div class="row">
        <div class="form-group col-4">
          <input type="text" class="form-control" disabled v-model="bocode" />
        </div>
        <div class="form-group col-3">
          <input
            type="text"
            class="form-control"
            name="nickname"
            disabled
            v-model="nickName"
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
        <!-- 관리자만 보이게 해야함 -->
        <div
          v-if="isAdmin"
          class="col-md-2 d-flex align-items-center justify-content-end"
        >
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
            <button
              type="button"
              class="btn btn-outline-dark"
              data-bs-toggle="modal"
              data-bs-target="#free-vote-modal"
            >
              <i class="bi bi-bar-chart-line"></i> 투표추가
            </button>
            <!-- 투표추가 모달 -->
            <div
              class="modal fade"
              id="free-vote-modal"
              tabindex="-1"
              aria-labelledby="exampleModalLabel-5"
              aria-hidden="true"
            >
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel-5">
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
                      v-model.lazy="vote.voteList.vote4"
                    />
                    <input
                      type="text"
                      class="form-control mb-3"
                      placeholder="5. 항목을 입력하세요"
                      name="boardTitle"
                      v-model.lazy="vote.voteList.vote5"
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
              data-bs-target="#free-place-modal"
              @click="relayout"
            >
              <i class="bi bi-geo-alt"></i> 장소추가
            </button>

            <!-- Modal -->
            <div
              class="modal fade"
              id="free-place-modal"
              tabindex="-1"
              aria-labelledby="exampleModalLabel-5"
              aria-hidden="true"
            >
              <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel-5">
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
                    <button type="button" class="btn btn-primary">확인</button>
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
          투표가 등록되었습니다. (또는 투표명)
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

    <!-- 등록 버튼 -->
    <div class="fixed-button">
      <button type="button" class="btn btn-primary" @click="saveFreeBoard">
        등록
      </button>
    </div>
  </div>
</template>

<script>
import FreeBoardService from "@/services/board/FreeBoardService";
export default {
  data() {
    return {
      isAdmin:
        this.$store.state.member != null &&
        this.$store.state.member.memberCode === "AT01",

      freeBoard: {},

      bocode: [],
      board: [],
      vote: {
        voteName: "",
        voteList: {
          vote1: "",
          vote2: "",
          vote3: "",
          vote4: "",
          vote5: "",
        },
        delDate: this.selectedDaily,
      },
      files: [], // 파일 리스트를 저장하기 위한 배열

      submitted: false,
      selectedDaily: new Date().toISOString(),

      // 카카오맵 api
      map: null,
      infowindow: null,
      markers: [],
      options: {
        //지도를 생성할 때 필요한 기본 옵션
        center: {
          lat: 33.450701,
          lng: 126.570667,
        }, //지도의 중심좌표.
        level: 3, //지도의 레벨(확대, 축소 정도)
      },
    };
  },
  methods: {
    async saveFreeBoard() {
      try {
        let data = {
          board: this.board,
          voteDtos: this.vote.voteList, // vote 데이터를 함께 전송
          place: this.place, // place 데이터를 함께 전송
          boardFileDtos: this.files.map((file) => ({
            fileBid: null, // 새로운 파일이므로 ID는 null
            boardId: null, // 새로 생성된 게시글의 ID와 연결
            uuid: file.data.name, // 파일 이름을 UUID로 사용
          })),
        };
        console.log(data);
        // TODO: 벡엔드로 객체 추가 요청
        let response = await FreeBoardService.create(data);
        // TODO: 콘솔에 결과 출력
        console.log(response);
        this.submitted = true;
      } catch (e) {
        console.log(e);
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
    // 주소 부분
    relayout() {
      this.delayFunction(() => {
        this.map.relayout();
        this.retrieveMap("부산 부산진구 중앙대로 749 4층");
      }, 500);
    },
    delayFunction(callback, delay) {
      setTimeout(callback, delay);
    },
    retrieveMap(address) {
      let kakao = window.kakao;
      var container = this.$refs.map;
      const { center, level } = this.options;

      let map = new kakao.maps.Map(container, {
        center: new kakao.maps.LatLng(center.lat, center.lng),
        level,
      }); //지도 생성 및 객체 리턴
      this.map = map;

      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();

      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(address, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

          // 결과값으로 받은 위치를 마커로 표시합니다
          var marker = new kakao.maps.Marker({
            map: map,
            position: coords,
          });

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          var infowindow = new kakao.maps.InfoWindow({
            content:
              '<div style="width:150px;text-align:center;padding:6px 0;">모임장소</div>',
          });
          infowindow.open(map, marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          map.setCenter(coords);
        }
      });
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.retrieveMap("부산 부산진구 중앙대로 749 4층");
    } else {
      const script = document.createElement("script");
      // 로드 완료 후 retrieveMap() 메서드 실행 추가
      script.onload = () => {
        this.retrieveMap("부산 부산진구 중앙대로 749 4층");
        // 지도 로드 완료 후 searchPlaces() 메서드 실행
        this.searchPlaces();
      };
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=55b411073309a73c48d56caa594311c8"; // 발급받은 API 키로 변경
      document.head.appendChild(script);
    }
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
