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
import AlertService from '@/services/AlertService'
import { NativeEventSource, EventSourcePolyfill } from "event-source-polyfill";
import { mapState } from "vuex";
const EventSource = NativeEventSource || EventSourcePolyfill;

export default {
  data() {
    return {
      alerts: [],
      notificationData: null,
      eventSource: null,
      showPopup: false,
    };
  },
  computed: {
    ...mapState(["loggedIn", "member"]),
  },
  created() {
    if (this.loggedIn) {
      this.connectSSE();
    }
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
    connectSSE() {
      if (this.member) {
        this.eventSource = new EventSource(
          `HTTP://localhost:8000/api/connect/${this.member.memberId}`
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
          // 연결이 끊어졌을 때 자동으로 재연결 시도
          setTimeout(() => {
            this.connectSSE();
          }, 60000); // 5초 후에 재연결 시도
        };
      }
    },
    disconnectSSE() {
      if (this.eventSource) {
        this.eventSource.close();
        this.eventSource = null;
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
    this.disconnectSSE();
  },
};
</script>

<style scoped>
#alerts {
  margin-top: 20px;
  cursor: pointer;
}

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
