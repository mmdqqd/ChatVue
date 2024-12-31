import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/register/index.vue')
  },
  {
    path: '/movie',
    name: 'Movie',
    component: () => import('@/views/movie/index.vue'),
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('token')
  
  if (to.meta.requiresAuth) {
    if (!isAuthenticated) {
      next('/login')
    } else {
      next()
    }
  } else {
    if (isAuthenticated && (to.path === '/login' || to.path === '/register')) {
      next('/movie')
    } else {
      next()
    }
  }
})

export default router
