<template>
	<div class="container loginForm mx-auto mt-5" style="width: 500px">
		<form @submit.prevent="login" class="form">
			<div class="form-group">
				<label for="email">Email</label>
				<input id="email" class="form-control" type="text" v-model="email" @blur="checkEmail" />
				<span class="badge badge-danger mt-1" v-if="!availableEmail">이메일 형식이 잘못되었습니다.</span>
			</div>
			<div class="form-group mb-3">
				<label for="password">Password</label>
				<input id="password" class="form-control" type="password" v-model="password" />
			</div>
			<button type="submit" class="btn">
				로그인
			</button>
		</form>
		<div v-if="message" style="width: 400px" class="mx-auto badge badge-danger">{{ message }}</div>
	</div>
</template>

<script>
import { loginUser } from '@/api';
import { validateEmail } from '@/common/validation';

export default {
	name: 'LoginForm',
	data() {
		return {
			email: '',
			password: '',
			availableEmail: true,
			message: '',
		};
	},
	methods: {
		async login() {
			if (this.email == '') {
				alert('이메일을 입력해주세요.');
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
			const userData = {
				email: this.email,
				password: this.password,
			};
			const response = await loginUser(userData);

			if (response.data.state == 'fail') {
				this.message = response.data.message;
			} else if (response.data.state == 'success') {
				this.$store.commit('setUserdata', response.data);
				this.$router.push('/');
			}
		},
		checkEmail() {
			if (validateEmail(this.email)) {
				this.availableEmail = true;
			} else {
				this.availableEmail = false;
			}
		},
	},
};
</script>

<style scoped>
.loginForm {
	padding: 3%;
	background-color: #7d2ae8;
	border-radius: 1em;
	color: white;
	font-size: 25px;
	font-weight: bold;
}

.btn {
	background-color: #00c4cc;
	color: white;
	font-size: 20px;
}
</style>
