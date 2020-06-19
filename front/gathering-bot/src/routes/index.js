import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store/index';

Vue.use(VueRouter);

const router = new VueRouter({
	mode: 'history',
	routes: [
		{
			path: '/',
			component: () => import('@/views/MainPage.vue')
		},
		{
			path: '/login',
			component: () => import('@/views/LoginPage.vue')
		},
		{
			path: '/signup',
			component: () => import('@/views/SignUpPage.vue')
		},
		{
			path: '/sheets',
			component: () => import('@/views/SheetList.vue'),
			meta: { auth: true }
		},
		{
			path: '/sheets/new',
			component: () => import('@/views/InsertSheetPage.vue'),
			meta: { auth: true }
		},
		{
			path: '/sheets/:id',
			component: () => import('@/views/SheetDetail.vue'),
			meta: { auth: true }
		},
		{
			path: '/response/:sheetId/:userId/:token',
			component: () => import('@/views/ResponsePage.vue')
		},
		{
			path: '/success',
			component: () => import('@/views/SuccessPage.vue')
		},
		{
			path: '*',
			component: () => import('@/views/NotFoundPage.vue')
		}
	]
});

router.beforeEach((to, from, next) => {
	if (to.meta.auth && !store.getters.isLogin) {
		console.log('인증이 필요합니다.');
		next('/login');
		return;
	}
	next();
});

export default router;
