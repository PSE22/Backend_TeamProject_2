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
  // 비밀번호 찾기
  {
    path: "/find-password",
    component: () => import("../views/FindPassword.vue"),
  },
  // 회원 프로필 메인
  {
    path: "/profile",
    component: () => import("../views/profile/MyProfile.vue"),
  },
  // 부서게시판
  {
    path: "/board/dept",
    name: 'TeamDetail',
    component: () => import("../views/board/DeptBoardView.vue"),
  },
  // 부서게시판 상세글 조회
  {
    path: "/board/dept/:smcode/:boardId",
    component: () => import("../views/board/DeptBoardDetailView.vue"),
  },
  // 동호회게시판
  {
    path: "/board/club",
    component: () => import("../views/board/ClubView.vue"),
  },
  // 중메뉴게시판
  {
    path: "/board/club/:code",
    component: () => import("../views/board/ClubSmView.vue"),
  },
  // 자유게시판
  {
    path: "/board/free",
    component: () => import("../views/board/FreeBoard.vue"),
  },
  // 자유게시판 등록(글쓰기)
  {
    path: "/board/free-write",
    component: () => import("../views/board/FreeWrite.vue"),
  },
  {
    path: '/board/main-search',
    name: 'SearchResults',
    component: () => import("../views/board/SearchResults.vue"),
  },
  {
    path: '/board/management',
    component: () => import("../views/admin/BoardManagement.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
