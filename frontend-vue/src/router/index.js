import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // Home
  {
    path: '/',
    component: () => import('../views/HomeView.vue')
  },
  // 로그인
  {
    path: "/login",
    component: () => import("../views/LoginView.vue"),
  },
  // 회원가입
  {
    path: "/signup",
    component: () => import("../views/SignUpView.vue"),
  },
  // 자유게시판
  {
    path: "/free-board",
    component: () => import("../views/board/FreeBoard.vue"),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
