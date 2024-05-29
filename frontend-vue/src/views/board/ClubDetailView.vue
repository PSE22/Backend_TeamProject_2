<template>글 상세보기</template>

<script>
import ClubService from '@/services/board/ClubService';
export default {
  data() {
    return {
      ClubBoard: {},
    };
  },
  methods: {
    async getClubBoard(boardId) {
      // TODO: 공통 상품 상세조회 서비스 실행
      // TODO: 비동기 코딩 : async ~ await
      try {
        let response = await ClubService.get(boardId);
        this.simpleProduct = response.data; // spring 전송
        // 로깅
        console.log(response.data);
      } catch (e) {
        console.log(e); // 콘솔에 에러 출력
      }
    },
  },
  mounted() {
    // TODO: 화면이 뜰 때 상품 상세조회 : 상품번호(snpo)
    this.getClubBoard(this.$route.params.boardId);
  },
};
</script>

<style></style>
