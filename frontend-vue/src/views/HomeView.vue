<template>
  <div class="main-page">
    <div class="board-container">
      <div class="board-section hot-topics-section">
        <h2>핫토픽 게시판</h2>
        <div v-for="(hotTopic, index) in hotTopics.slice(0, 5)" :key="index" class="board-item">
          <div class="content">
            <div class="good">{{ hotTopic.good }}</div>
            <div class="title">{{ hotTopic.boardTitle }}</div>
          </div>
          <div class="sub-info">
            <span class="nickname">별명: {{ hotTopic.nickName }}</span>
            <span class="reply_count">댓글 {{ hotTopic.totalReplyCount }}</span>
            <div class="add_date">{{ hotTopic.addDate }}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="board-container">
      <div v-if="freeBoards.length" class="board-section">
        <h2>자유게시판</h2>
        <div v-for="(item, index) in freeBoards.slice(0, 7)" :key="index" class="board-item">
          <div class="content">
            <div class="good">{{ item.good }}</div>
            <div class="title">{{ item.boardTitle }}</div>
          </div>
          <div class="sub-info">
            <span class="nickname">별명: {{ item.nickname }}</span>
            <span class="reply_count">댓글 {{ item.totalReplyCount }}</span>
            <div class="add_date">{{ item.add_date }}</div>
          </div>
        </div>
      </div>
      <div v-if="suggestionBoards.length" class="board-section">
        <h2>건의게시판</h2>
        <div v-for="(item, index) in suggestionBoards.slice(0, 7)" :key="index" class="board-item">
          <div class="content">
            <div class="good">{{ item.good }}</div>
            <div class="title">{{ item.boardTitle }}</div>
          </div>
          <div class="sub-info">
            <span class="nickname">별명: {{ item.nickname }}</span>
            <span class="reply_count">댓글 {{ item.totalReplyCount }}</span>
            <div class="add_date">{{ item.add_date }}</div>
          </div>
        </div>
      </div>

      <div v-if="praiseBoards.length" class="board-section">
        <h2>칭찬게시판</h2>
        <div v-for="(item, index) in praiseBoards.slice(0, 7)" :key="index" class="board-item">
          <div class="content">
            <div class="good">{{ item.good }}</div>
            <div class="title">{{ item.boardTitle }}</div>
          </div>
          <div class="sub-info">
            <span class="nickname">별명: {{ item.nickname }}</span>
            <span class="reply_count">댓글 {{ item.totalReplyCount }}</span>
            <div class="add_date">{{ item.add_date }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MainPageService from '@/services/board/MainPage';

export default {
  data() {
    return {
      freeBoards: [],
      suggestionBoards: [],
      praiseBoards: [],
      hotTopics: []
    };
  },

  methods: {
    async fetchHotTopics() {
      try {
        const response = await MainPageService.getHotTopics();
        this.hotTopics = response.data;
      } catch (error) {
        console.error('Error fetching hot topics:', error);
      }
    },
    async fetchFreeBoards() {
      try {
        const response = await MainPageService.getFreeBoardData();
        this.freeBoards = response.data;
      } catch (error) {
        console.error('Error fetching free board data:', error);
      }
    },
    async fetchSuggestionBoards() {
      try {
        const response = await MainPageService.getSuggestionBoardData();
        this.suggestionBoards = response.data;
      } catch (error) {
        console.error('Error fetching suggestion board data:', error);
      }
    },
    async fetchPraiseBoards() {
      try {
        const response = await MainPageService.getPraiseBoardData();
        this.praiseBoards = response.data;
      } catch (error) {
        console.error('Error fetching praise board data:', error);
      }
    }
  },
  mounted() {
    this.fetchHotTopics();
    this.fetchFreeBoards();
    this.fetchSuggestionBoards();
    this.fetchPraiseBoards();
  }
}
</script>
<style scoped>
</style>
