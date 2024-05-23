import http from '@/utils/http-common';
import LoginHeader from "../login/LoginHeader";

// 기존의 게시판 데이터를 가져오는 함수
const getBoardData = async () => {
  try {
    const response = await http.get('/board/board-data', { headers: LoginHeader() });
    return response.data;
  } catch (error) {
    console.error('Error fetching board data:', error);
    throw error;
  }
};

// 핫토픽 게시판 데이터를 가져오는 새로운 함수
const getHotTopics = async () => {
  try {
    const response = await http.get('/board/hot-topics', { headers: LoginHeader() });
    return response.data;
  } catch (error) {
    console.error('Error fetching hot topics:', error);
    throw error;
  }
};



// 모든 함수를 하나의 객체로 내보내기
export default {
  getBoardData,
  getHotTopics
};