<template>
	<div class="container mb-5">
		<div class="row no-gutters">
			<div class="col-5">
				<router-link to="/" style="color: white; text-decoration: none">
					<div class="dot shadow-lg">G</div>
				</router-link>
			</div>
			<div class="col-6">
				<!--not login-->
				<template v-if="!isUserLogin">
					<span class="float-right mx-2">
						<router-link to="/login" class="btn btn-dark">로그인</router-link>
					</span>
					<span class="float-right mx-2">
						<router-link to="/signup" class="btn btn-dark">회원가입</router-link>
					</span>
				</template>
				<!--logined-->
				<template v-else>
					<span class="float-right mx-2">
						<router-link to="/sheets/new" class="btn btn-dark">시트 추가</router-link>
					</span>
					<span class="float-right mx-2">
						<router-link to="/sheets" class="btn btn-dark">시트 목록</router-link>
					</span>
					<span class="float-right mx-2">
						<a href="javascript:;" v-on:click="logoutUser" class="btn btn-dark">로그아웃</a>
					</span>
				</template>
			</div>
		</div>
	</div>
</template>

<script>
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { deleteCookie } from '@/utils/cookie';
export default {
	name: 'AppVar',
	computed: {
		isUserLogin() {
			return this.$store.getters.isLogin;
		}
	},
	methods: {
		logoutUser() {
			this.$store.commit('clearUsername');
			deleteCookie('id');
			deleteCookie('username');
			deleteCookie('teamname');
			deleteCookie('token');
			deleteCookie('position');
			deleteCookie('email');
			this.$router.push('/login');
		}
	}
};
</script>

<style scoped>
.row {
	margin-top: 3%;
}
.dot {
	height: 80px;
	width: 80px;
	text-align: center;
	font-size: 50px;
	font-weight: bold;
	line-height: 80px;
	background-color: #00c4cc;
	border-radius: 50%;
	display: inline-block;
}
</style>
