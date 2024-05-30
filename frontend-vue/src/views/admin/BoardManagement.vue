<template>
  <div class="container">
    <div class="header">
      <h2>게시판목록</h2>
      <p>게시판을 생성/수정/삭제합니다.</p>
    </div>
    <div class="controls">
      <span class="total-count">총 게시판 수: {{ boards.length }}</span>
      <button class="add-board-button" @click="toggleAddBoardForm">게시판 추가</button>
    </div>
    <div v-if="showForm" class="form-container">
      <input v-model="newBoardCd" placeholder="게시판 코드" />
      <input v-model="newBoardUpCmCd" placeholder="상위 게시판 코드 (선택사항)" />
      <input v-model="newBoardName" placeholder="게시판명" />
      <input v-model="newBoardComment" placeholder="게시판설명" />
      <button @click="addBoard">추가</button>
    </div>

    <div v-if="showEditForm" class="form-container">
      <input v-model="editBoardData.cmCd" placeholder="게시판 코드" disabled />
      <input v-model="editBoardData.upCmCd" placeholder="상위 게시판 코드 (선택사항)" />
      <input v-model="editBoardData.cmCdName" placeholder="게시판명" />
      <input v-model="editBoardData.cmCdComment" placeholder="게시판설명" />
      <button @click="updateBoard">저장</button>
      <button @click="cancelEdit">취소</button>
    </div>
    
    <table class="board-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>게시판 코드</th>
          <th>상위 게시판 코드</th>
          <th>게시판명</th>
          <th>게시판설명</th>
          <th>기능</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(board, index) in boards" :key="index">
          <td>{{ index + 1 }}</td>
          <td>{{ board.cmCd }}</td>
          <td>{{ board.upCmCd }}</td>
          <td>{{ board.cmCdName }}</td>
          <td>{{ board.cmCdComment }}</td>
          <td>
            <button class="edit-button" @click="editBoard(board)">수정</button>
            <button class="delete-button">삭제</button>
          </td>
        </tr>
      </tbody>
    </table>

  </div>
</template>

<script>
import BoardManageService from "@/services/board/BoardManageService";

export default {
  data() {
    return {
      boards: [], // 게시판 데이터를 담을 배열
      showForm: false,
      newBoardCd: '',
      newBoardName: '',
      newBoardComment: '',
      newBoardUpCmCd: '',
      showEditForm: false,
      editBoardData: {
        cmCd: '',
        upCmCd: '',
        cmCdName: '',
        cmCdComment: ''
      }
    };
  },
  methods: {
    async fetchBoards() {
      try {
        const response = await BoardManageService.getAll();
        this.boards = response.data;
      } catch (error) {
        console.error('Error fetching boards:', error);
      }
    },
    toggleAddBoardForm() {
      this.showForm = !this.showForm;
    },
    async addBoard() {
      try {
        await BoardManageService.postBoard(this.newBoardCd, this.newBoardName, this.newBoardComment, this.newBoardUpCmCd);
        this.showForm = false; // 폼을 숨김
        this.newBoardCd = '';
        this.newBoardName = '';
        this.newBoardComment = '';
        this.newBoardUpCmCd = '';
        this.fetchBoards(); // 새로운 게시판을 추가한 후 게시판 목록을 다시 불러옴
      } catch (error) {
        console.error('Error adding board:', error);
      }
    },
    editBoard(board) {
      this.editBoardData = { ...board };
      this.showEditForm = true;
    },
    async updateBoard() {
      try {
        await BoardManageService.updateBoard(
          this.editBoardData.cmCd,
          this.editBoardData.cmCdName,
          this.editBoardData.cmCdComment,
          this.editBoardData.upCmCd
        );
        this.showEditForm = false;
        this.fetchBoards();
      } catch (error) {
        console.error('Error updating board:', error);
      }
    },
    cancelEdit() {
      this.showEditForm = false;
      this.editBoardData = {
        cmCd: '',
        upCmCd: '',
        cmCdName: '',
        cmCdComment: ''
      };
    }
  },
  mounted() {
    this.fetchBoards(); // 컴포넌트가 마운트될 때 게시판 데이터를 가져옴
  }
};
</script>

<style scoped>
.container {
  width: 80%;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
}

.controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
}

.total-count {
  font-weight: bold;
}

.add-board-button {
  padding: 5px 10px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}

.board-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.board-table th,
.board-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

.board-table th {
  background-color: #f4f4f4;
}

.manage-button,
.edit-button,
.delete-button {
  padding: 5px 10px;
  border: none;
  cursor: pointer;
  margin-right: 5px;
  border-radius: 5px;
}

.manage-button {
  background-color: #17a2b8;
  color: white;
}

.edit-button {
  background-color: #ffc107;
  color: white;
}

.delete-button {
  background-color: #dc3545;
  color: white;
}

.form-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 20px;
}
</style>
