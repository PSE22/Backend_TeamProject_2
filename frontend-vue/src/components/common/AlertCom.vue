<template>
  <div>
    <div v-if="showPopup" class="popup">
      <div>{{ notificationData.notiContent }}</div>
      <div class="popup-footer">
        <button @click="openNotificationLink">확인</button>
        <button @click="closePopup">닫기</button>
      </div>
    </div>
  </div>
</template>

<script>
import AlertService from "@/services/AlertService";
import { NativeEventSource, EventSourcePolyfill } from "event-source-polyfill";
import { mapState } from "vuex";
import LoginHeader from "@/services/login/LoginHeader";
const EventSource = NativeEventSource || EventSourcePolyfill;

export default {
  data() {
    return {
      alerts: [],
      notificationData: null,
      eventSource: null,
      showPopup: false,
      retryCount: 0,
    };
  },
  mounted() {
    window.addEventListener("beforeunload", this.handleBeforeUnload);
  },
  computed: {
    ...mapState(["loggedIn", "member"]),
  },
  watch: {
    loggedIn(newValue) {
      if (newValue) {
        this.connectSSE();
      } else {
        this.disconnectSSE();
      }
    },
  },
  methods: {
    handleBeforeUnload() {
      this.disconnectSSE();
    },
    connectSSE() {
      if (this.member) {
        this.eventSource = new EventSource(
          `http://localhost:9000/api/connect/${this.member.memberId}`,
          { headers: LoginHeader() },
          { timeout: 3600000 }
        );

        this.eventSource.addEventListener("connect", (event) => {
          console.log("Connected:", event.data);
        });

        this.eventSource.addEventListener("notification", (event) => {
          console.log("Message received:", event.data);
          const notifyData = JSON.parse(event.data);
          this.notificationData = notifyData;
          this.alerts.push(notifyData.notiContent);
          this.showPopup = true;
          setTimeout(() => {
            this.closePopup();
          }, 10000);
        });

        this.eventSource.onerror = (error) => {
          console.error("EventSource failed:", error);

          // 오류가 발생한 경우 재연결 시도
          if (this.retryCount <= 10) {
            setTimeout(() => {
              this.connectSSE();
            }, 5000);
            this.retryCount++; // 재시도 횟수 증가
          } else {
            console.error("Retry limit exceeded.");
          }
        };
      }
    },
    disconnectSSE() {
      if (this.eventSource) {
        this.eventSource.close();
        this.eventSource = null;

        localStorage.removeItem("member");
        alert("장시간 연결로 로그아웃 되었습니다. 다시 로그인 해주세요.");
        this.$router.push("/login");
      }
    },
    openNotificationLink() {
      if (this.notificationData) {
        const notiUrl = this.notificationData.notiUrl;
        window.open(notiUrl, "_self");
        // 확인 버튼을 누르면 알림을 확인했다는 요청을 보냄
        AlertService.update(this.notificationData.notifyId);
      }
    },
    closePopup() {
      this.showPopup = false;
    },
  },
  beforeUnmount() {
    window.removeEventListener("beforeunload", this.handleBeforeUnload);
  },
};
</script>

<style scoped>
.popup {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  padding: 10px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.popup-footer {
  margin-top: 10px;
  text-align: center;
}

.popup-footer button {
  margin-right: 10px;
}
</style>
