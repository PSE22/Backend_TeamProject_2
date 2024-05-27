<template>
  <header class="header">
    <div class="header-top">
      <div class="header-left">
        <input type="text" placeholder="검색어를 입력하세요" class="search-input" v-model="searchQuery"/>
        <button @click="executeSearch" class="search-button">검색</button>
      </div>
      <div class="header-center">
        <h1>서울쥐</h1>
      </div>
      <div class="header-right">
        <div class="notification-icon" @click="toggleNotifications">
          🔔 <span class="notification-count">{{ notificationCount }}</span>
        </div>
        <a href="#">로그인</a>
        <a href="#">회원가입</a>
        <a href="/" @click.prevent="handleLogout">로그아웃</a>
      </div>
    </div>
    <nav class="header-nav">
      <ul>
        <li><a href="#">부서 게시판</a></li>
        <li><a href="#">동호회 게시판</a></li>
        <li><router-link to="/free" class="router-link">자유 게시판</router-link></li>
        <li><a href="#">건의 게시판</a></li>
        <li><a href="#">칭찬 게시판</a></li>
      </ul>
    </nav>
    <!-- 알림 팝업 -->
    <div v-if="showNotifications" class="notifications-popup">
      <div v-for="notification in notifications" :key="notification.notifyId" class="notification-item">

        <p @click="handleNotificationClick(notification)">{{ notification.notiContent }}</p>
        <button @click.stop="deleteNotification(notification.notifyId)">X</button>
        <small>{{ notification.addDate }}</small>
      </div>
    </div>
  </header>
</template>

<script>
import NotificationService from "@/services/board/NotificationService";
import LoginService from "@/services/login/LoginService";

export default {
  data() {
    return {
      notificationCount: 0,
      notifications: [],
      showNotifications: false,
      searchQuery: '',
    };
  },
  created() {
    this.fetchNotificationsCount();
    this.fetchNotifications();
  },
  methods: {
    fetchNotificationsCount() {
      const memberId = 'member1'; // 임시 memberId
      NotificationService.fetchNotificationCount(memberId)
        .then(response => {
          this.notificationCount = response.data;
        })
        .catch(error => {
          console.error('Error fetching notification count:', error);
        });
    },
    fetchNotifications() {
      const memberId = 'member1'; // 임시 memberId
      NotificationService.getNotificationsByMemberId(memberId)
        .then(response => {
          this.notifications = response.data;
        })
        .catch(error => {
          console.error('Error fetching notifications:', error);
        });
    },
    deleteNotification(notifyId) {
      NotificationService.deleteNotification(notifyId)
        .then(() => {
          this.notifications = this.notifications.filter(n => n.notifyId !== notifyId);
          this.fetchNotificationsCount(); // Update the notification count
        })
        .catch(error => {
          console.error('Error deleting notification:', error);
        });
    },
    handleNotificationClick(notification) {
      // 상태 업데이트 후 URL로 리다이렉트
      NotificationService.markAsRead(notification.notifyId)
        .then(() => {
          window.location.href = notification.notiUrl; // 해당 알림의 URL로 리다이렉트
        })
        .catch(error => {
          console.error('Error marking notification as read:', error);
        });
    },
    toggleNotifications() {
      this.showNotifications = !this.showNotifications;
    },
    handleLogout() {
      LoginService.logout();
      this.$store.commit("logout");
      this.$router.push("/login");
    },
    executeSearch() {
      console.log("Searching for:", this.searchQuery);
    }
  }
};
</script>
<style scoped>

</style>