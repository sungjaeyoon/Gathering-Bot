<template>
	<div class="container">
		<form @submit.prevent="submitForm">
			<div>
				<label for="email">email</label>
				<input type="text" id="email" v-model="email" />
			</div>
			<div>
				<label for="name">성함</label>
				<input type="text" id="name" v-model="name" />
			</div>
			<div>
				<label for="password">비밀번호</label>
				<input type="password" id="password" v-model="password" />
			</div>
			<div>
				<label for="passwordConfirm">비밀번호 확인</label>
				<input type="password" id="passwordConfirm" v-model="passwordConfirm" />
			</div>
			<div>
				<label for="position">직책</label>
				<input type="text" id="position" v-model="position" />
			</div>
			<div>
				<label for="teamName">팀 이름</label>
				<input type="text" id="teamName" v-model="teamName" />
			</div>
			<button type="submit">회원가입</button>
		</form>
	</div>
</template>

<script>
import { registerUser } from '@/api/index';
export default {
	name: 'SignupForm',
	data() {
		return {
			email: '',
			name: '',
			password: '',
			passwordConfirm: '',
			position: '',
			teamName: '',
		};
	},
	methods: {
		async submitForm() {
			const userData = {
				email: this.email,
				name: this.name,
				password: this.password,
				position: this.position,
				teamName: this.teamName,
			};
			const response = await registerUser(userData);
		},
	},
};
</script>

<style scoped></style>
