import { createStore } from 'vuex'

const member = JSON.parse(localStorage.getItem("member"));

export default createStore({
  state: {
    loggedIn: member ? true : false, // 로그인 여부
    member: member ? member : null,  // 로그인 정보(웹토큰 속성 있음)
  },
  getters: {
  },
  mutations: {
    loginSuccess(state, member) {
      state.loggedIn = true;
      state.member = member;
    },
    // TODO: 로그인 실패 함수
    loginFailure(state) {
      state.loggedIn = false;
      state.member = null;
    },
    // TODO: 로그아웃 함수
    logout(state) {
      state.loggedIn = false;
      state.member = null;
    },
    // TODO: 회원가입 성공 함수
    registerSuccess(state) {
      state.loggedIn = false;
    },
    // TODO: 회원가입 실패 함수
    registerFailure(state) {
      state.loggedIn = false;
    },
  },
  actions: {
  },
  modules: {
  }
})
