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
              v-model="club.boardTitle"
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
                <div class="modal-dialog">
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
                        v-model="club.voteDtos"
                      />
                      <hr />
                      <h5 class="text-start">항목 추가</h5>
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="1. 항목을 입력하세요"
                        name="boardTitle"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="2. 항목을 입력하세요"
                        name="boardTitle"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="3. 항목을 입력하세요"
                        name="boardTitle"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="4. 항목을 입력하세요"
                        name="boardTitle"
                      />
                      <input
                        type="text"
                        class="form-control mb-3"
                        placeholder="5. 항목을 입력하세요"
                        name="boardTitle"
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
                      <button type="button" class="btn btn-primary">
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
                <div class="modal-dialog modal-lg">
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
                      <KakaoMap/>
                    </div>
                    <div class="modal-footer">
                      <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                      >
                        Close
                      </button>
                      <button type="button" class="btn btn-primary">
                        Save changes
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
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
import KakaoMap from "@/components/map/KakaoMap.vue";
export default {
  components: {
    KakaoMap,
  },
  data() {
    return {
      bocode: [],
      smcode: [],
      club: {},
      submitted: false,
      selectBocode: "", // 추가
      selectSmcode: "", // 추가
      selectedDaily: new Date().toISOString(),
    };
  },
  methods: {
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
      let vote = {
        voteName: "",
        voteListName: "",
        delDate: "",
      };
      let voteList = [];
      for (const data of this.club) {
        vote.voteName = data.voteName;
        vote.voteListName = data.voteListName;
        vote.delDate = data.delDate;
        voteList.push(vote);
      }
      try {
        // 임시 객체 변수
        let data = {
          board: {
            memberId: this.$store.state.member.memberId,
            boardTitle: this.club.boardTitle,
            boardContent: this.club.boardContent,
            bocode: this.selectBocode,
            noticeYn: this.club.noticeYn,
            smcode: this.selectSmcode,
          },
          voteDtos: [], // 예시: 투표 정보가 없을 경우 빈 배열
          place: {}, // 예시: 장소 정보가 없을 경우 null
          files: [], // 예시: 파일이 없을 경우 빈 배열
          boardFileDtos: [], // 예시: 파일 정보가 없을 경우 빈 배열
        };

        console.log("글쓰기", data);
        // 백앤드로 객체 추가 요청
        let response = await BoardWriteService.create(data);
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
