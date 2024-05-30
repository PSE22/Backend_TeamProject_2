<template>
  <header class="header">
    <div class="header-top">
      <div class="header-left">
        <input type="text" placeholder="검색어를 입력하세요" class="search-input" v-model="searchQuery"/>
        <button @click="executeSearch" class="search-button">검색</button>
      </div>
      <div class="header-center">
        <router-link to="/" class="custom-link">
        <h1>서울쥐</h1>
        </router-link>
      </div>
      <div class="header-right">
        <div v-if="isLoggedIn" class="notification-icon" @click="toggleNotifications">
          🔔 <span class="notification-count">{{ notificationCount }}</span>
        </div>
        <a><router-link to="/login"                v-if="!isLoggedIn" >로그인</router-link></a>
        <a><router-link to="/register"             v-if="!isLoggedIn" >회원가입</router-link></a>
        <a href="/" @click.prevent="handleLogout"  v-if="isLoggedIn" >로그아웃</a>
      </div>
    </div>
    <nav class="header-nav">
      <ul>
        <li><router-link to="/board/dept" class="router-link">부서 게시판</router-link></li>
        <li><router-link to="/board/club" class="router-link">동호회 게시판</router-link></li>
        <li><router-link to="/board/free" class="router-link">자유 게시판</router-link></li>
        <li><router-link to="/board/suggest" class="router-link">건의 게시판</router-link></li>
        <li><router-link to="/board/praise" class="router-link">칭찬 게시판</router-link></li>
      </ul>
    </nav>
    <!-- 알림 팝업 -->
    <div v-if="showNotifications" class="notifications-popup">
      <button class="mark-all-read" @click="markAllAsRead">모두 확인</button>
      <div v-for="notification in notifications" :key="notification.notifyId" class="notification-item">
        <p @click="handleNotificationClick(notification)">{{ notification.notiContent }}</p>
        <div class="notification-footer">
          <small>{{ notification.addDate }}</small>
          <button @click.stop="deleteNotification(notification.notifyId)">X</button>
        </div>
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
  computed: {
    isLoggedIn() {
      return this.$store.state.loggedIn;
    }
  },
  watch: {
    isLoggedIn(LoggedIn) {
      if (LoggedIn) {
        this.fetchNotificationsCount();
        this.fetchNotifications();
      }
    }
  },
  methods: {
    async fetchNotificationsCount() {
      try {
        const response = await NotificationService.fetchNotificationCount(this.$store.state.member.memberId);
        this.notificationCount = response.data;
      } catch (error) {
        console.error('Error fetching notification count:', error);
      }
    },
    async fetchNotifications() {
      try {
        const response = await NotificationService.getNotificationsByMemberId(this.$store.state.member.memberId);
        this.notifications = response.data;
      } catch (error) {
        console.error('Error fetching notifications:', error);
      }
    },
    async deleteNotification(notifyId) {
      try {
        await NotificationService.deleteNotification(notifyId);
        this.notifications = this.notifications.filter(n => n.notifyId !== notifyId);
        await this.fetchNotificationsCount();
      } catch (error) {
        console.error('Error deleting notification:', error);
      }
    },
    async handleNotificationClick(notification) {
      try {
        await NotificationService.markAsRead(notification.notifyId);
        window.location.href = notification.notiUrl;
      } catch (error) {
        console.error('Error marking notification as read:', error);
      }
    },
    async markAllAsRead() {
      try {
        await NotificationService.markAsReadAll(this.$store.state.member.memberId);
        await this.fetchNotifications();
        await this.fetchNotificationsCount();
      } catch (error) {
        console.error('Error marking all notifications as read:', error);
      }
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
  if (this.searchQuery.trim()) {
    this.$router.push({ path: '/board/main-search', query: { boardtitle: this.searchQuery.trim() } });
  }
}
},

  mounted() {
    if (this.isLoggedIn) {
      this.fetchNotificationsCount();
      this.fetchNotifications();
    }
  }
};
</script>
<style scoped>
  @import "@/assets/css/home.css";
</style>