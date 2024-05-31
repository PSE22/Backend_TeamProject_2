<template>
  <header class="header">
    <div class="header-top">
      <div class="header-left">
        <input
          type="text"
          placeholder="검색어를 입력하세요"
          class="search-input"
          v-model="searchQuery"
        />
        <button @click="executeSearch" class="search-button">검색</button>
      </div>
      <div class="header-center">
        <router-link to="/" class="custom-link">
          <h1>서울쥐</h1>
        </router-link>
      </div>
      <div class="header-right">
        <div
          v-if="isLoggedIn"
          class="notification-icon"
          @click="toggleNotifications"
        >
          🔔 <span class="notification-count">{{ notificationCount }}</span>
        </div>
        <a><router-link to="/login"                v-if="!isLoggedIn" >로그인</router-link></a>
        <a><router-link to="/register"             v-if="!isLoggedIn" >회원가입</router-link></a>
        <a href="/" @click.prevent="handleLogout"  v-if="isLoggedIn" >로그아웃</a>
      </div>
    </div>
    <nav class="header-nav">
      <ul>
        <li>
          <router-link to="/board/dept" class="router-link"
            >부서 게시판</router-link
          >
        </li>
        <li>
          <router-link to="/board/club" class="router-link"
            >동호회 게시판</router-link
          >
        </li>
        <li>
          <router-link to="/board/free" class="router-link"
            >자유 게시판</router-link
          >
        </li>
        <li>
          <router-link to="/board/suggest" class="router-link"
            >건의 게시판</router-link
          >
        </li>
        <li>
          <router-link to="/board/praise" class="router-link"
            >칭찬 게시판</router-link
          >
        </li>
      </ul>
    </nav>
    <!-- 알림 팝업 -->
    <div v-if="showNotifications" class="notifications-popup">
      <button class="mark-all-read" @click="markAllAsRead">모두 확인</button>
      <div
        v-for="notification in notifications"
        :key="notification.notifyId"
        class="notification-item"
      >
        <p @click="handleNotificationClick(notification)">
          {{ notification.notiContent }}
        </p>
        <div class="notification-footer">
          <small>{{ notification.addDate }}</small>
          <button @click.stop="deleteNotification(notification.notifyId)">
            X
          </button>
        </div>
      </div>
    </div>
    <!-- 채팅창 -->
    <div class="chat-container" v-if="isLoggedIn && showChat">
      <div class="chat-header">
        <h2>Chat</h2>
        <button @click="toggleChat">닫기</button>
      </div>
      <div class="chat-messages">
        <div v-for="message in messages" :key="message.id" class="chat-message">
          <strong>{{ message.nickname }}:</strong> {{ message.content }}
        </div>
      </div>
      <div class="chat-input">
        <input
          type="text"
          v-model="newMessage"
          @keyup.enter="sendMessage"
          placeholder="Type your message"
        />
        <button @click="sendMessage">전송</button>
      </div>
    </div>
    <button class="chat-toggle-button" @click="toggleChat" v-if="isLoggedIn && !showChat">
      채팅 열기
    </button>
  </header>
</template>

<script>
import NotificationService from "@/services/board/NotificationService";
import LoginService from "@/services/login/LoginService";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
const stompClient = Stomp.over(new SockJS("http://localhost:8000/chat"));

export default {
  data() {
    return {
      notificationCount: 0,
      notifications: [],
      showNotifications: false,
      searchQuery: "",
      newMessage: "",
      messages: [],
      stompClient: null,
      showChat: false,
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.loggedIn;
    },
  },
  watch: {
    isLoggedIn(LoggedIn) {
      if (LoggedIn) {
        this.fetchNotificationsCount();
        this.fetchNotifications();
      }
    },
  },
  methods: {
    connect() {
      stompClient.connect(
        {},
        (frame) => {
          console.log("Connected: " + frame);
          stompClient.subscribe("/topic/message", (message) => {
            const receivedMessage = JSON.parse(message.body);
            console.log("Received: ", receivedMessage); // 로그 추가
            this.messages.push(receivedMessage);
          });
        },
        (error) => {
          console.error("Error connecting: " + error);
        }
      );
    },
    disconnect() {
      if (stompClient) {
        stompClient.disconnect();
      }
    },
    sendMessage() {
      if (stompClient && this.newMessage.trim()) {
        const message = {
          memberId: this.$store.state.member.memberId,
          content: this.newMessage.trim(),
        };
        stompClient.send("/app/message", JSON.stringify(message));
        this.newMessage = "";
      }
    },
    async fetchNotificationsCount() {
      try {
        const response = await NotificationService.fetchNotificationCount(
          this.$store.state.member.memberId
        );
        this.notificationCount = response.data;
      } catch (error) {
        console.error("Error fetching notification count:", error);
      }
    },
    async fetchNotifications() {
      try {
        const response = await NotificationService.getNotificationsByMemberId(
          this.$store.state.member.memberId
        );
        this.notifications = response.data;
      } catch (error) {
        console.error("Error fetching notifications:", error);
      }
    },
    async deleteNotification(notifyId) {
      try {
        await NotificationService.deleteNotification(notifyId);
        this.notifications = this.notifications.filter(
          (n) => n.notifyId !== notifyId
        );
        await this.fetchNotificationsCount();
      } catch (error) {
        console.error("Error deleting notification:", error);
      }
    },
    async handleNotificationClick(notification) {
      try {
        await NotificationService.markAsRead(notification.notifyId);
        window.location.href = notification.notiUrl;
      } catch (error) {
        console.error("Error marking notification as read:", error);
      }
    },
    async markAllAsRead() {
      try {
        await NotificationService.markAsReadAll(
          this.$store.state.member.memberId
        );
        await this.fetchNotifications();
        await this.fetchNotificationsCount();
      } catch (error) {
        console.error("Error marking all notifications as read:", error);
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
    toggleChat() {
      this.showChat = !this.showChat;
    },
    executeSearch() {
      if (this.searchQuery.trim()) {
        this.$router.push({
          path: "/board/main-search",
          query: { boardtitle: this.searchQuery.trim() },
        });
      }
    },
  },
  mounted() {
    if (this.isLoggedIn) {
      this.fetchNotificationsCount();
      this.fetchNotifications();
      this.connect();
    }
  },
  beforeUnmount() {
    this.disconnect();
  },
};
</script>

<style scoped>
@import "@/assets/css/home.css";

.chat-container {
  z-index: 1;
  position: fixed;
  bottom: 0;
  right: 0;
  width: 300px;
  height: 400px;
  border: 1px solid #ccc;
  background: #fff;
  display: flex;
  flex-direction: column;
}

.chat-header {
  display: flex;
  justify-content: space-between;
  padding: 10px;
  background: #f1f1f1;
  border-bottom: 1px solid #ccc;
}

.chat-messages {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
}

.chat-message {
  margin-bottom: 10px;
}

.chat-input {
  display: flex;
  border-top: 1px solid #ccc;
  padding: 10px;
}

.chat-input input {
  flex: 1;
  padding: 5px;
}

.chat-input button {
  padding: 5px 10px;
}

.chat-toggle-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 10px 20px;
  background: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
}
</style>
