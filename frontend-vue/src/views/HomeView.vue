<template>
  <div class="main-page">
    <div class="hot-topics-container">
      <h2>핫토픽 게시판</h2>
      <div v-for="hotTopic in hotTopics" :key="hotTopic" class="hot-topic-item">
        <div class="content">
          <div class="good">{{ hotTopic.good }}</div>
          <div class="title">{{ hotTopic.title }}</div>
        </div>
        <div class="sub-info">
          <span class="nickname">별명: {{ hotTopic.nickname }}</span>
          <span class="reply_count">댓글 {{ hotTopic.reply_count }}</span>
          <div class="add_date">{{ hotTopic.add_date }}</div>
        </div>
      </div>
    </div>

    <div class="board-container">
      <div v-for="(group, index) in groupedBoards" :key="index" class="board-section">
        <h2>{{ group.board }}</h2>
        <div v-for="item in group.items" :key="item.title" class="board-item">
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
// import boardService from '@/services/board/MainPage';

export default {
  data() {
    return {
      boards: [],
      groupedBoards: [],
      hotTopics: [] // 데이터 배열 추가
    };
  },

  methods: {
    async fetchBoardData() {
      try {
        const data = await boardService.getBoardData();
        this.processBoardData(data);
      } catch (error) {
        console.error('Error fetching board data:', error);
      }
    },
    
    async fetchHotTopics() {
      try {
        const data = await boardService.getHotTopics();
        console.log("Hot Topics Data:", data);
        this.hotTopics = data.map(item => ({
          title: item[1],
          nickname: item[2],
          good: item[3],
          reply_count: item[4],
          add_date: item[5]
        }));
      } catch (error) {
        console.error('핫토픽 데이터를 가져오는 중 오류 발생:', error);
      }
    },

    processBoardData(data) {
      const groups = data.reduce((acc, item) => {
        const code = item[0];
        if (!acc[code]) {
          acc[code] = {
            board: item[0],
            items: []
          };
        }
        acc[code].items.push({
          title: item[1],
          nickname: item[2],
          good: item[3],
          reply_count: item[4],
          add_date: item[5]
        });
        return acc;
      }, {});
      
      this.groupedBoards = Object.values(groups);
    }
  },

  mounted() {
    this.fetchBoardData();
    this.fetchHotTopics(); // 핫토픽 데이터 불러오기
  },
}
</script>
<style scoped>

</style>