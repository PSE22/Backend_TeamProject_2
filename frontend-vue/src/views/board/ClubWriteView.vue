<template>
  <div align="center">
    <h1 class="text-center mb-5 mt-5">동호회 게시판</h1>
    <div class="container mt-3 free-box">
      <!-- 자유게시판 / 닉네임 입력 -->
      <div align="center">
        <div class="row">
          <div class="col-4">
            <input
              type="text"
              class="form-control"
              placeholder="동호회 게시판"
              disabled
            />
          </div>
          <div class="col-4">
            <select
              class="form-select"
              aria-label="Default select example"
              v-model="selectBocode"
            >
              <option
                v-for="(data, index) in bocode"
                :key="index"
                :value="data.cmCd"
              >
                {{ data.cmCdName }}
              </option>
            </select>
          </div>
          <div class="col-4">
            <select
              class="form-select"
              aria-label="Default select example"
              v-model="selectSmcode"
            >
              <option
                v-for="(data, index) in smcode"
                :key="index"
                :value="data.cmCd"
              >
                {{ data.cmCdName }}
              </option>
            </select>
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
              v-model.lazy="club.boardTitle"
            />
          </div>
          <div class="col-md-2 d-flex align-items-center justify-content-end">
            <div class="form-check">
              <input
                class="form-check-input"
                type="checkbox"
                v-model.lazy="club.noticeYn"
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
                        v-model.lazy="vote.voteName"
                      />
                      <hr />
                      <h5 class="text-start">항목 추가</h5>
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="1. 항목을 입력하세요"
                        name="boardTitle"
                        v-model.lazy="vote.voteList.vote1"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="2. 항목을 입력하세요"
                        name="boardTitle"
                        v-model.lazy="vote.voteList.vote2"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="3. 항목을 입력하세요"
                        name="boardTitle"
                        v-model.lazy="vote.voteList.vote3"
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
                        종료일 :
                        <input
                          type="date"
                          v-model="vote.delDate"
                          :min="minDate"
                        />
                      </h5>
                    </div>
                    <div class="modal-footer">
                      <button
                        @reset="vote"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                        @click="resetVoteForm"
                      >
                        초기화
                      </button>
                      <button
                        type="button"
                        class="btn btn-primary"
                        data-bs-dismiss="modal"
                        @click="submitVote"
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
              <!-- 장소 Modal -->
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
                            placeholder="주소"
                            @keypress.enter="openPostcode"
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
                        취소
                      </button>
                      <button
                        type="button"
                        class="btn btn-primary"
                        data-bs-dismiss="modal"
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
          <div class="col-md-4 mb-4 mb-md-0">
            <span>{{ address }}</span>
          </div>
        </div>
      </div>

      <!-- 안내 메시지 -->
      <div class="row">
        <div class="col-md-12">
          <div
            class="alert alert-success"
            role="alert"
            v-if="showSuccessMessage"
          >
            투표가 등록되었습니다.
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
            v-model="club.boardContent"
          ></textarea>
        </div>
      </div>
      <!-- 파일 첨부 -->
      <div class="row mt-3">
        <div class="col-md-12">
          <input
            type="file"
            ref="file"
            class="form-control"
            multiple
            @change="selectImage"
          />
          <div class="mt-2 file-list"></div>
        </div>
      </div>

      <!-- 등록 버튼 -->
      <div class="fixed-button">
        <button type="button" class="btn btn-primary" @click="saveClub">
          등록
        </button>
      </div>
    </div>
  </div>
</template>

<script>
let daum = window.daum;
import ClubService from "@/services/board/ClubService";
import BoardWriteService from "@/services/board/BoardWriteService";
// import KakaoMap from "@/components/map/KakaoMap.vue";
export default {
  components: {
    // KakaoMap,
  },
  data() {
    return {
      bocode: [], // 대분류 코드 가져오기
      smcode: [], // 소분류 코드 가져오기
      club: {},
      vote: {
        // 투표 저장
        voteName: "",
        voteList: {
          vote1: "",
          vote2: "",
          vote3: "",
          vote4: "",
          vote5: "",
        },
        delDate: "",
      },
      file: [
        {
          fileUrl: "",
        },
      ],
      currentFile: undefined,
      showSuccessMessage: false,
      submitted: false,
      selectBocode: "", // 추가
      selectSmcode: "", // 추가

      address: "",
      map: null,
      geocoder: null,
      marker: null,
      // searchAddress: "",
    };
  },
  methods: {
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
    execDaumPostcode() {
      new window.daum.Postcode({
        oncomplete: function (data) {
          var mapContainer = document.getElementById("map"), // 지도를 표시할 div
            mapOption = {
              center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
              level: 5, // 지도의 확대 레벨
            };
          var daum = window.daum;
          var map = new daum.maps.Map(mapContainer, mapOption);
          var marker = new daum.maps.Marker({
            position: new daum.maps.LatLng(37.537187, 127.005476),
            map: map,
          });
          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          document.getElementById("address").value = this.searchAddress;
          var geocoder = new daum.maps.services.Geocoder();
          // 주소로 상세 정보를 검색
          geocoder.addressSearch(data.address, function (results, status) {
            // 정상적으로 검색이 완료됐으면
            if (status === daum.maps.services.Status.OK) {
              var result = results[0]; //첫번째 결과의 값을 활용

              // 해당 주소에 대한 좌표를 받아서
              var coords = new daum.maps.LatLng(result.y, result.x);
              // 지도를 보여준다.
              mapContainer.style.display = "block";
              map.relayout();
              // 지도 중심을 변경한다.
              map.setCenter(coords);
              // 마커를 결과값으로 받은 위치로 옮긴다.
              marker.setPosition(coords);
            }
          });
        },
      }).open();
    },
    async retrieveBocode() {
      try {
        // TODO: 비동기 코딩
        let response = await ClubService.getBocode();
        this.bocode = response.data;
        console.log(response.data); // 웹브라우저 콘솔탬에 백앤드 데이터 표시
      } catch (e) {
        console.log(e); // 웹브라우저 콘솔탭에 에러표시
      }
    },
    async retrieveSmcode() {
      try {
        let response = await ClubService.getSmcode();
        this.smcode = response.data;
        // 로깅
        console.log(response.data); // 웹브라우저 콘솔탭
      } catch (e) {
        console.log(e); // 웹브라우저 콘솔탭
      }
    },
    async saveClub() {
      // 파일테이블 저장
      // let file = {
      //   // uuid: this.$route.params.uuid,
      //   fileUrl: "",
      // };
      // let fileList = [];
      // for (const data of this.club) {
      //   // file.uuid = data.uuid;
      //   file.fileUrl = data.fileUrl;
      //   fileList.push(file);
      // }
      try {
        // 임시 객체 변수
        let board = {
          memberId: this.$store.state.member.memberId,
          boardTitle: this.club.boardTitle,
          boardContent: this.club.boardContent,
          bocode: this.selectBocode,
          noticeYn: this.club.noticeYn ? "Y" : "N",
          smcode: this.selectSmcode,
        };
        let vote = this.vote;

        let place = this.address;
        // 예시: 장소 정보가 없을 경우 null
        let fileDtos = this.file; // 예시: 파일이 없을 경우 빈 배열
        let boardFileDtos = []; // 예시: 파일 정보가 없을 경우 빈 배열
        // 백앤드로 객체 추가 요청
        let response = await BoardWriteService.create(
          board,
          vote,
          place,
          fileDtos,
          boardFileDtos
        );
        // 콘솔에 결과 출력
        console.log(response);
        this.submitted = true;
        alert("게시글이 등록되었습니다.");
      } catch (e) {
        console.log(e);
      }
    },
    // 투표 취소 시 초기화
    resetVoteForm() {
      this.vote = {
        voteName: "",
        voteList: {
          vote1: "",
          vote2: "",
          vote3: "",
          vote4: "",
          vote5: "",
        },
        delDate: "",
      };
      this.showSuccessMessage = false;
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
    submitVote() {
      // 투표 항목 수 검사
      const voteItems = Object.values(this.vote.voteList).filter(
        (item) => item.trim() !== ""
      );
      if (voteItems.length < 2) {
        alert("투표 항목을 2개 이상 입력해주세요.");
        return;
      } else {
        this.showSuccessMessage = true;
      }
    },
    // 이미지 선택 변수 저장 함수
    selectImage() {
      this.currentFile = this.$refs.file.files;
    },
  },
  computed: {
    minDate() {
      const today = new Date();
      const tomorrow = new Date(today);
      tomorrow.setDate(tomorrow.getDate()); // 내일 날짜 계산
      return tomorrow.toISOString().slice(0, 10); // YYYY-MM-DD 형식으로 변환
    },
  },
  mounted() {
    this.retrieveBocode();
    this.retrieveSmcode();
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
</style>
