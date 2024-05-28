<template>
  <div align="center">
    <h2>자유 게시판</h2>
  </div>
  <div class="container mt-3 free-box">
    <!-- 자유게시판 / 닉네임 입력 -->
    <div align="center">
      <div class="row">
        <div class="form-group col-4">
          <input
            type="text"
            class="form-control"
            placeholder="자유게시판"
            disabled
          />
        </div>
        <div class="form-group col-3">
          <input
            type="text"
            class="form-control"
            placeholder="닉네임 입력"
            name="nickname"
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
          />
        </div>
        <div class="col-md-2 d-flex align-items-center justify-content-end">
          <div class="form-check">
            <input class="form-check-input" type="checkbox" id="noticeCheck" />
            <label class="form-check-label" for="noticeCheck">공지사항</label>
          </div>
        </div>
      </div>

      <!-- 투표 추가 / 장소 추가 / 그린컴퓨터아카데미 -->
      <div class="row mt-3">
        <div class="col-md-2 mb-3">
          <div class="form-group">
            <button type="button" class="btn btn-secondary btn-block">
              투표 추가
            </button>
          </div>
        </div>
        <div class="col-md-2 mb-3">
          <div class="form-group">
            <button type="button" class="btn btn-secondary btn-block">
              장소 추가
            </button>
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
        <button type="button" class="btn btn-primary">등록</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
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
