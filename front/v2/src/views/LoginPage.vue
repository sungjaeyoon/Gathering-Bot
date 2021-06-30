<template>
	<div>
		<img src="@/assets/robot.png" class="rounded mx-auto d-block my-5" alt="..." style="height: 150px" />
		<div class="login-card border mx-auto my-5 rounded">
			<!--card title-->
			<div class="rounded-top login-card-head">
				<p>
					LOGIN
				</p>
			</div>
			<!--card body-->
			<div class="my-4 text-left mx-5">
				<!--login form-->
				<div>
					<!--username-->
					<div class="mb-2 font-weight-bold">이메일</div>
					<div class="mb-4">
						<input type="text" v-model="email" class="border rounded w-100" />
					</div>
					<!--password-->
					<div class="mb-2 font-weight-bold">비밀번호</div>
					<div class="mb-4">
						<input type="password" v-model="password" class="border rounded w-100" v-on:keydown.enter="signIn" />
						※ 초기 비밀번호는 new1234! 입니다.
					</div>
					<div>
						<button @click="signIn" class="rounded sign-in-button w-100 py-2">Sign in</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import sha256 from 'crypto-js/sha256';
import { loginUser } from '@/api';
import { saveEmailToCookie, saveIdToCookie, savePositionToCookie, saveTokenToCookie, saveNameToCookie } from '@/utils/cookie';
export default {
	name: 'LoginPage',
	components: {},
	data() {
		return {
			email: '',
			password: '',
			showModal: false
		};
	},
	methods: {
		valid() {
			if (this.email == '') {
				alert('이메일을 입력해주세요.');
				return false;
			}
			if (this.password == '') {
				alert('비밀번호를 입력해주세요.');
				return false;
			}
			return true;
		},
		async signIn() {
			if (!this.valid) {
				return;
			}
			const userData = {
				email: this.email,
				// password: this.password
				password: sha256(this.password).toString()
			};

			const response = await loginUser(userData);

			if (response.data.status == 200) {
				this.$store.commit('setUserdata', response.data.data);
				saveEmailToCookie(response.data.data.email);
				saveIdToCookie(response.data.data.id);
				savePositionToCookie(response.data.data.position);
				saveTokenToCookie(response.data.data.token);
				saveNameToCookie(response.data.data.name);
				this.$router.replace(this.$route.query.redirect || '/');
			} else if (response.data.status == 400) {
				alert(response.data.message);
			} else if (response.data.status == 404) {
				alert(response.data.message);
			} else {
				alert(response.data.message);
			}
		}
	}
};
</script>

<style scoped>
.login-card {
	width: 450px;
	background-color: white;
}
.login-card-head {
	height: 70px;
	border-bottom: 2px solid #2cbbb6;
	font-weight: bold;
	position: relative;
}
.login-card-head p {
	font-size: 30px;
	margin: 0;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}
sign-in-button {
	height: 60px !important;
	font-size: 30px;
	color: white;
	border: 0;
}
</style>
