<template>
	<div>
		<img src="@/assets/robot.png" class="rounded mx-auto d-block my-5" alt="..." style="height: 150px" />
		<div class="container loginForm mx-auto mt-5 shadow-lg p-3 mb-5 bg-white rounded" style="width: 500px">
			<h2 class="text-center">LOGIN</h2>
			<form @submit.prevent="login" class="form">
				<div class="form-group">
					<label for="email">Email</label>
					<input id="email" class="form-control" type="text" v-model="email" @blur="checkEmail" />
					<span class="badge badge-dark mt-1" v-if="!availableEmail">이메일 형식이 잘못되었습니다.</span>
				</div>
				<div class="form-group mb-3">
					<label for="password">Password</label>
					<input id="password" class="form-control" type="password" v-model="password" />
				</div>
				<button type="submit" class="btn btn-dark">
					로그인
				</button>
			</form>
			<div v-if="message" class="badge badge-dark">{{ message }}</div>
		</div>
	</div>
</template>

<script>
import { loginUser } from '@/api';
import { validateEmail } from '@/common/validation';
import { saveEmailToCookie, saveIdToCookie, savePositionToCookie, saveTeamNameToCookie, saveTokenToCookie, saveUserToCookie } from '@/utils/cookie';

export default {
	name: 'LoginForm',
	data() {
		return {
			email: '',
			password: '',
			availableEmail: true,
			message: ''
		};
	},
	methods: {
		async login() {
			if (this.email == '') {
				alert('이메일을 입력해주세요.');
				return;
			}
			if (this.email.length >= 100) {
				alert('이메일 길이가 너무 깁니다.');
				return;
			}
			if (!this.availableEmail) {
				alert('이메일 형식을 확인해주세요.');
				return;
			}
			if (this.password == '') {
				alert('패스워드를 입력해주세요.');
				return;
			}
			if (this.password.length >= 20) {
				alert('패스워드가 너무 깁니다.');
				return;
			}
			const userData = {
				email: this.email,
				password: this.password
			};
			const response = await loginUser(userData);

			if (response.data.status == 200) {
				this.$store.commit('setUserdata', response.data);
				saveEmailToCookie(response.data.email);
				saveIdToCookie(response.data.id);
				savePositionToCookie(response.data.position);
				saveTeamNameToCookie(response.data.teamName);
				saveTokenToCookie(response.data.token);
				saveUserToCookie(response.data.username);
				this.$router.push('/');
			} else if (response.data.status == 400) {
				this.message = response.data.message;
			} else if (response.data.status == 404) {
				alert(response.data.message);
			}
		},
		checkEmail() {
			if (this.email.length >= 100) {
				alert('이메일 길이가 너무 깁니다.');
				return;
			}
			if (validateEmail(this.email)) {
				this.availableEmail = true;
			} else {
				this.availableEmail = false;
			}
		}
	}
};
</script>

<style scoped>
.loginForm {
	font-weight: bold;
	padding: 3%;
	font-size: 20px;
}
</style>
