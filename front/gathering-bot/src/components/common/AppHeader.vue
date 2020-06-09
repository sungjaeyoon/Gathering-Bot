<template>
	<div class="container mb-5">
		<div class="row no-gutters">
			<div class="col-5">
				<router-link to="/" style="color: white; text-decoration: none">
					<img src="@/assets/logo.png" style="width: 15%" alt="" />
				</router-link>
			</div>
			<div class="col-6">
				<!--not login-->
				<template v-if="!isUserLogin">
					<span class="header-button float-right">
						<router-link to="/login" style="color: gray; text-decoration: none">로그인</router-link>
					</span>
					<span class="header-button float-right">
						<router-link to="/signup" class="header-button-text">회원가입</router-link>
					</span>
				</template>
				<!--logined-->
				<template v-else>
					<span class="header-button float-right">
						<router-link to="/sheet/new" class="header-button-text">시트 추가</router-link>
					</span>
					<span class="header-button float-right">
						<router-link to="/sheet/new" class="header-button-text">시트 목록</router-link>
					</span>
					<span class="header-button float-right">
						<a href="javascript:;" v-on:click="logoutUser" class="header-button-text">로그아웃</a>
					</span>
				</template>
			</div>
		</div>
	</div>
</template>

<script>
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
export default {
	name: 'AppVar',
	computed: {
		isUserLogin() {
			return this.$store.getters.isLogin;
		},
	},
	methods: {
		logoutUser() {
			this.$store.commit('clearUsername');
			this.$router.push('/login');
		},
	},
};
</script>

<style scoped>
.row {
	margin-top: 3%;
}

.header-button {
	font-size: 20px;
	font-weight: bold;
	margin-left: 3%;
	padding: 2%;
	border-radius: 0.5em;
	border: 2px solid black;
	cursor: pointer;
}

.header-button-text {
	color: black;
	text-decoration: none;
}
</style>
