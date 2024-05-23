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
    path: "/signup",
    component: () => import("../views/SignUpView.vue"),
  },
  // 회원가입
  {
    path: "/club",
    component: () => import("../views/board/ClubView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
