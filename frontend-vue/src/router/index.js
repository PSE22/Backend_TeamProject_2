import { createRouter, createWebHistory } from "vue-router";

const routes = [
  // Home
  {
    path: "/",
    component: () => import("../views/HomeView.vue"),
  },
  // 로그인
  {
    path: "/login",
    component: () => import("../views/LoginView.vue"),
  },
  // 회원가입
  {
    path: "/register",
    component: () => import("../views/RegisterView.vue"),
  },
  // 동호회게시판
  {
    path: "/club",
    component: () => import("../views/board/ClubView.vue"),
  },
  // 중메뉴게시판
  {
    path: "/club/:code",
    component: () => import("../views/board/ClubSmView.vue"),
  },
  // 자유게시판
  {
    path: "/free",
    component: () => import("../views/board/FreeBoard.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
