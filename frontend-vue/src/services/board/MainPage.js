// services/MainPage.js
import http from '@/utils/http-common';
import LoginHeader from "../login/LoginHeader";

class MainPageService {

      // 메인 게시판 데이터를 가져오는 함수
  getBoardData() {
    return http.get('/board/board-data', {
      headers: LoginHeader()
    });
  }

  // 핫토픽 데이터를 가져오는 함수
  getHotTopics() {
    return http.get('/board/hot-topics', {
      headers: LoginHeader()
    });
  }
}

export default new MainPageService();