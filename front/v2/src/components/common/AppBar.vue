<template>
	<mdb-navbar style="background-color: #2CBBB6" container>
		<mdb-navbar-brand>
			<router-link to="/">
				<img src="@/assets/robot.png" width="40px" alt="" />
				<span class="ml-2">
					Gathering bot
				</span>
			</router-link>
		</mdb-navbar-brand>
		<mdb-navbar-toggler>
			<mdb-navbar-nav right class="mr-5">
				<template v-if="!isUserLogin">
					<mdb-nav-item class="mx-3" href="#" style="font-size: 22px;font-weight: bold;">
						<router-link to="/login" style="font-size: 22px;font-weight: bold;color: white">로그인</router-link>
					</mdb-nav-item>
				</template>
				<template v-else>
					<mdb-nav-item class="mx-3" href="#" style="font-size: 22px;font-weight: bold;"><router-link to="/insert" style="color: white">시트추가</router-link></mdb-nav-item>
					<mdb-nav-item class="mx-3" href="#" style="font-size: 22px;font-weight: bold;"><router-link to="/list" style="color: white">시트목록</router-link></mdb-nav-item>
					<mdb-nav-item class="mx-3" href="#" style="font-size: 22px;font-weight: bold;"><router-link to="/response/list" style="color: white">답변</router-link></mdb-nav-item>
					<mdb-nav-item class="mx-3" href="#" style="font-size: 22px;font-weight: bold;"><router-link to="/user" style="color: white">MY</router-link></mdb-nav-item>
					<mdb-nav-item class="mx-3" href="#" style="font-size: 22px;font-weight: bold;" @click="logoutUser"><router-link to="#" style="color: white">로그아웃</router-link> </mdb-nav-item>
				</template>
			</mdb-navbar-nav>
		</mdb-navbar-toggler>
	</mdb-navbar>
</template>

<script>
import { mdbNavbar, mdbNavbarBrand, mdbNavbarToggler, mdbNavbarNav, mdbNavItem } from 'mdbvue';
import { deleteCookie } from '@/utils/cookie';
export default {
	name: 'AppBar',
	components: {
		mdbNavbar,
		mdbNavbarBrand,
		mdbNavbarToggler,
		mdbNavbarNav,
		mdbNavItem
	},
	computed: {
		isUserLogin() {
			return this.$store.getters.isLogin;
		}
	},
	methods: {
		logoutUser() {
			this.$store.commit('clearUsername');
			deleteCookie('id');
			deleteCookie('name');
			deleteCookie('token');
			deleteCookie('position');
			deleteCookie('email');
			this.$router.push('/login');
		}
	}
};
</script>

<style scoped>
span {
	color: white;
	font-family: KTBold;
}
</style>
