import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // Home
  {
    path: '/',
    component: () => import('../views/HomeView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
