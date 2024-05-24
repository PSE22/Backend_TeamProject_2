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
      <div v-for="(group, index) in groupedBoards" :key="index" class="board-section">
        <h2>{{ group.board }}</h2>
        <div v-for="(item, idx) in group.items.slice(0, 7)" :key="idx" class="board-item">
          <div class="content">
            <div class="good">{{ item.good }}</div>
            <div class="title">{{ item.title }}</div>
          </div>
          <div class="sub-info">
            <span class="nickname">별명: {{ item.nickname }}</span>
            <span class="reply_count">댓글 {{ item.reply_count }}</span>
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
      boards: [],
      groupedBoards: [],
      hotTopics: []
    };
  },

  methods: {
    async fetchBoardData() {
        try {
            const response = await MainPageService.getBoardData();
            if (response && response.data) {
                this.processBoardData(response.data);
            } else {
                console.error('No data returned from getBoardData');
            }
        } catch (error) {
            console.error('Error fetching board data:', error);
        }
    },

    async fetchHotTopics() {
      try {
        const data = await MainPageService.getHotTopics();
        this.hotTopics = data.data;
      } catch (error) {
        console.error('Error fetching hot topics:', error);
      }
    },

    processBoardData(data) {
        const groups = data.reduce((acc, item) => {
            const boardName = item.board; // 'BO03', 'BO04' 등의 코드 대신 이름을 사용
            if (!acc[boardName]) {
                acc[boardName] = { board: boardName, items: [] };
            }
            acc[boardName].items.push({
                title: item.boardTitle,
                nickname: item.nickName,
                good: item.good,
                reply_count: item.totalReplyCount,
                add_date: item.addDate
            });
            return acc;
        }, {});
        this.groupedBoards = Object.values(groups);
    }
},

  mounted() {
    this.fetchBoardData();
    this.fetchHotTopics();
  }
}
</script>
<style scoped>

</style>