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
                        v-model.lazy="club.voteName"
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
            v-model="club.boardContent"
          ></textarea>
        </div>
      </div>
      <!-- 파일 첨부 -->
      <div class="row mt-3">
        <div class="col-md-12">
          <input type="file" class="form-control" multiple />
          <div class="mt-2 file-list">
            <p>피는 꿈는다 꿈는 피에 뛰노는 심장온1.jpg</p>
            <p>피는 꿈는다 꿈는 피에 뛰노는 심장온1.jpg</p>
            <p>피는 꿈는다 꿈는 피에 뛰노는 심장온1.jpg</p>
          </div>
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
import ClubService from "@/services/board/ClubService";
import BoardWriteService from "@/services/board/BoardWriteService";
// import KakaoMap from "@/components/map/KakaoMap.vue";
export default {
  components: {
    // KakaoMap,
  },
  data() {
    return {
      bocode: [],
      smcode: [],
      club: {},
      vote: {
        voteName: "",
        voteList: {
          vote1 : "",
          vote2 : "",
          vote3 : "",
          vote4 : "",
          vote5 : "",
        },
        delDate: this.selectedDaily,
      },
      submitted: false,
      selectBocode: "", // 추가
      selectSmcode: "", // 추가
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
    relayout() {
      this.delayFunction(() => {
        this.map.relayout();
        this.retrieveMap("부산 부산진구 중앙대로 749 4층");
      }, 500); // 1초 후 실행
      // this.map.relayout();
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
      // map.relayout();

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
      // let vote = {
      //   voteName: "",
      //   voteList: "",
      //   delDate: this.selectedDaily,
      // };
      // let voteList = [];
      // for (const data of this.club) {
      //   vote.voteName = data.voteName;
      //   vote.voteList = data.voteList;
      //   vote.delDate = data.delDate;
      //   voteList.push(vote);
      // }
      let file = {
        uuid: this.$route.params.uuid,
        fileUrl: "",
      };
      let fileList = [];
      for (const data of this.club) {
        file.uuid = data.fileUrl;
        file.fileUrl = data.fileUrl;
        fileList.push(file);
      }

      try {
        // 임시 객체 변수
        let board = {
          memberId: this.$store.state.member.memberId,
          boardTitle: this.club.boardTitle,
          boardContent: this.club.boardContent,
          bocode: this.selectBocode,
          noticeYn: this.club.noticeYn,
          smcode: this.selectSmcode,
        };
        let vote = this.vote;

        let place = {
          placeName: this.club.placeName,
          lat: this.club.lat,
          lng: this.club.lng,
          address: this.club.address,
        }; // 예시: 장소 정보가 없을 경우 null
        let files = fileList; // 예시: 파일이 없을 경우 빈 배열
        // let boardFileDtos = []; // 예시: 파일 정보가 없을 경우 빈 배열
        // 백앤드로 객체 추가 요청
        let response = await BoardWriteService.create(board, vote, place, files);
        // 콘솔에 결과 출력
        console.log(response);
        this.submitted = true;
        alert("게시글이 등록되었습니다.");
      } catch (e) {
        console.log(e);
      }
    },
  },
  mounted() {
    this.retrieveBocode();
    this.retrieveSmcode();
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
</style>
