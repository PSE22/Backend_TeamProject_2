<template>
  <div class="w-75 p-3 container">
    <div class="sidebar">
      <h1 class="sidebar-title">ㅇㅇㅇ님</h1>
      <ul class="sidebar-list">
        <li class="sidebar-menu">회원정보수정</li>
        <li class="sidebar-menu">비밀번호변경</li>
        <li class="sidebar-menu">활동내역</li>
      </ul>
    </div>
    <div class="main-content">
      <div class="section">
        <h2>Profile</h2>
        <div class="user-info">
          <div class="details">
            <div class="detail-item">
              <span class="label">고현준</span>
              <span class="sub-label">toveny1</span>
              <button class="edit-button">실명수정</button>
            </div>
            <div class="detail-item">
              <span class="label">이메일</span>
              <button class="edit-button">수정</button>
            </div>
            <div class="detail-item">
              <span class="label">연락처</span>
              <button class="edit-button">수정</button>
            </div>
            <div class="detail-item">
              <span class="label">부서</span>
              <span class="label">직급</span>
              <button class="edit-button">수정</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>
.container {
    display: flex;
}
.sidebar {
  width: 200px;
  background-color: #f9f9f9;
  border-right: 1px solid #ddd;
  padding: 20px;
}

.sidebar-title {
  font-size: 24px;
  margin-bottom: 20px;
}

.sidebar-list {
  list-style: none;
  padding: 0;
}

.sidebar-menu {
  margin-bottom: 10px;
  font-size: 16px;
  cursor: pointer;
}

.main-content {
  flex-grow: 1;
  padding: 20px;
  background-color: #fff;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.profile-info {
  text-align: right;
}

.username {
  font-size: 18px;
  font-weight: bold;
}

.email {
  font-size: 14px;
  color: #777;
}

.section {
  margin-bottom: 20px;
}

.section h2 {
  font-size: 18px;
  margin-bottom: 10px;
  padding-bottom: 5px;
  border-bottom: 2px solid #ddd;
}

.user-info {
  display: flex;
}

.details {
  flex-grow: 1;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.label {
  font-size: 16px;
}

.sub-label {
  color: #777;
  font-size: 14px;
  display: block;
}

.edit-button,
.confirm-button {
  background-color: #eee;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.toggle-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
</style>