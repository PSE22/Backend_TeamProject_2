<template>
  <div class="main-page">
    <div v-for="board in boards" :key="board.board" class="board-section">
      <h2>{{ board.board }}</h2>
      <table>
        <thead>
          <tr>
            <th>제목</th>
            <th>작성자</th>
            <th>추천수</th>
            <th>댓글수</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in board.items" :key="item.title">
            <td>{{ item.title }}</td>
            <td>{{ item.nickname }}</td>
            <td>{{ item.good }}</td>
            <td>{{ item.reply_count }}</td>
            <td>{{ item.add_date }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import boardService from '@/services/board/MainPage';

export default {
  data() {
    return {
      boards: [],
    };
  },
  mounted() {
    this.fetchBoardData();
  },
  methods: {
    async fetchBoardData() {
      try {
        const data = await boardService.getBoardData();

        const freeBoard = { board: 'FreeBoard', items: [] };
        const suggestionBoard = { board: 'SuggestionBoard', items: [] };
        const praiseBoard = { board: 'PraiseBoard', items: [] };

        data.forEach(item => {
          const boardItem = {
            title: item[1],
            nickname: item[2],
            good: item[3],
            reply_count: item[4],
            add_date: item[5],
          };

          if (item[0] === 'FreeBoard') {
            freeBoard.items.push(boardItem);
          } else if (item[0] === 'SuggestionBoard') {
            suggestionBoard.items.push(boardItem);
          } else if (item[0] === 'PraiseBoard') {
            praiseBoard.items.push(boardItem);
          }
        });

        this.boards = [freeBoard, suggestionBoard, praiseBoard];
      } catch (error) {
        console.error('Error fetching board data:', error);
      }
    },
  },
};
</script>

<style scoped>
.main-page {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.board-section {
  margin-bottom: 40px;
}

.board-section h2 {
  margin-bottom: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

thead th {
  background-color: #f4f4f4;
  padding: 10px;
  border: 1px solid #ddd;
}

tbody td {
  padding: 10px;
  border: 1px solid #ddd;
}

tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}
</style>