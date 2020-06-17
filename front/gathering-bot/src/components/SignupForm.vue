<template>
	<div class="container">
		<form @submit.prevent="submitForm" class="mx-auto my-auto signup-form shadow-lg p-3 mb-5 bg-white rounded" style="width: 500px">
			<div class="form-group mb-3">
				<label for="email">Email</label>
				<input type="text" class="form-control" id="email" v-model="email" placeholder="Enter email" @blur="checkDuplicate" />
				<span class="badge badge-dark mt-1" v-if="!availableEmail">이미 사용중인 이메일입니다.</span>
				<span class="badge badge-dark mt-1" v-if="!availableEmailForm">이메일 형식이 다릅니다.</span>
			</div>
			<div class="form-group mb-3">
				<label for="name">성함</label>
				<input type="text" class="form-control" id="name" v-model="name" placeholder="Enter name" />
			</div>
			<div class="form-group mb-3">
				<label for="password">비밀번호</label>
				<input type="password" class="form-control" id="password" v-model="password" placeholder="Enter password" />
			</div>
			<div class="form-group mb-3">
				<label for="passwordConfirm">비밀번호 확인</label>
				<input type="password" class="form-control" id="passwordConfirm" v-model="passwordConfirm" placeholder="Enter password" @blur="checkPasswordConfirm" />
				<span class="badge badge-dark mt-1" v-if="!samePassword">비밀번호가 다릅니다.</span>
			</div>
			<div class="form-group mb-3">
				<label for="position">직책</label>
				<select class="form-control" id="position" v-model="position">
					<option value="" selected disabled hidden>Choose your position</option>
					<option value="부장">부장</option>
					<option value="차장">차장</option>
					<option value="과장">과장</option>
					<option value="대리">대리</option>
					<option value="사원">사원</option>
					<option value="기타">기타</option>
				</select>
			</div>
			<div class="form-group mb-3">
				<label for="teamName">팀 이름</label>
				<select class="form-control" id="teamName" v-model="teamName">
					<option value="" selected disabled hidden>Choose your team</option>
					<option value="인증플랫폼">인증플랫폼</option>
					<option value="결제플랫폼">결제플랫폼</option>
					<option value="서비스플랫폼">서비스플랫폼</option>
				</select>
			</div>
			<button type="submit" class="btn btn-dark">회원가입</button>
		</form>
	</div>
</template>

<script>
import { registerUser } from '@/api/index';
import { checkDuplicateEmail } from '@/api/index';
import { validateEmail } from '@/common/validation';
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
			//boolean
			availableEmail: true,
			availableEmailForm: true,
			samePassword: true
		};
	},
	methods: {
		//버튼 클릭시
		async submitForm() {
			if (this.email == '') {
				alert('이메일 값은 필수입니다.');
				return;
			}
			if (this.email.length >= 100) {
				alert('이메일이 너무 깁니다.');
				return;
			}
			if (this.name == '') {
				alert('이름은 필수입니다.');
				return;
			}
			if (this.name.length >= 30) {
				alert('이름이 너무 깁니다.');
				return;
			}
			if (this.password == '') {
				alert('비밀번호는 필수입니다.');
				return;
			}
			if (this.password.length >= 20) {
				alert('패스워드가 너무 깁니다.');
				return;
			}
			if (this.position == '') {
				alert('직책은 필수입니다.');
				return;
			}
			if (this.teamName == '') {
				alert('팀 이름은 필수입니다.');
				return;
			}
			if (!this.availableEmail) {
				alert('중복된 이메일입니다.');
				return;
			}
			if (!this.availableEmailForm) {
				alert('이메일 형식이 다릅니다.');
				return;
			}
			if (!this.samePassword) {
				alert('비밀번호가 다릅니다.');
				return;
			}
			const userData = {
				email: this.email,
				name: this.name,
				password: this.password,
				position: this.position,
				teamName: this.teamName
			};
			const response = await registerUser(userData);
			if (response.data.status == 200) {
				alert('환영합니다.');
				this.$router.push('/login');
			} else {
				alert(response.data.message);
			}
		},
		//이메일 중복 검사
		async checkDuplicate() {
			this.availableEmail = true;
			if (!validateEmail(this.email)) {
				this.availableEmailForm = false;
				return;
			} else {
				this.availableEmailForm = true;
			}
			const response = await checkDuplicateEmail(this.email);
			if (!response.data) {
				this.availableEmail = false;
			} else {
				this.availableEmail = true;
			}
		},
		//비밀번호 일치확인
		checkPasswordConfirm() {
			if (this.password !== this.passwordConfirm) {
				this.samePassword = false;
			} else {
				this.samePassword = true;
			}
		}
	}
};
</script>

<style scoped>
.signup-form {
	padding: 3%;
	font-size: 20px;
	font-weight: bold;
}
</style>
