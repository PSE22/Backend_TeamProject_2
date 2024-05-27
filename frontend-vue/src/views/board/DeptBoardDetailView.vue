<template>
    <div class="w-80 p-3 board-detail-container">
        <!-- 글 수정/삭제 버튼 : 글쓴이만 보임 -->
        <div class="row board-button">
            <button class="col">수정</button>
            <button class="col">삭제</button>
        </div>
        <!-- 글 내용 -->
        <div class="row board-content">
            <div>부서게시판 > 영업팀</div>
            <div>글 제목입니다.</div>
            <div>작성자 | 현준</div>
            <div>작성일 | 2024-05-27</div>
            <hr>
            <div>투표</div>
            <div>글내용</div>
            <div>지도</div>
            <div>이미지(반복문)</div>
            <hr>
            <div>추천수 | 댓글수 | 신고버튼</div>
        </div>
        <!-- 댓글 작성 -->
        <div class="row reply-write">
            <div>작성자명 (익명게시판은 input 태그로 변경)</div>
            <textarea>댓글 작성란</textarea>
            <div class="row">
                <button class="col">파일선택버튼</button>
                <p class="col">파일명</p>
                <button class="col">등록버튼</button>
            </div>
        </div>
        <!-- 댓글 목록 -->
        <div class="row reply-content">
            <div>댓글1</div>
            <div>댓글2</div>
            <div>댓글3</div>
            <div>댓글수정시</div>
        </div>
        <!-- 목록으로 돌아가기 버튼 -->
        <div class="row">
            <button>목록</button>
        </div>
    </div>
</template>
<script>
export default {

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