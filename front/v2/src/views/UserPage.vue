<template>
	<div>
		<div class="rounded mx-auto border my-5 z-depth-1" style="background-color: white;width: 500px">
			<div class="py-5 my-1 text-left mx-5">
				<h3 class="text-center mb-3">비밀번호 변경</h3>
				<div>
					<div class="mb-2 font-weight-bold">이름</div>
					<div class="mb-4">{{ name }}</div>
					<div class="mb-2 font-weight-bold">이메일</div>
					<div class="mb-4">{{ email }}</div>
					<!--password-->
					<div class="mb-2 font-weight-bold">현재 비밀번호</div>
					<div class="mb-4">
						<input type="password" v-model="currentPassword" class="border rounded w-50" maxlength="18" />
					</div>
					<div class="mb-2 font-weight-bold">변경할 비밀번호</div>
					<div class="mb-4">
						<input type="password" v-model="newPassword" class="border rounded w-50" maxlength="18" />
					</div>
					<div class="mb-2 font-weight-bold">변경 비밀번호 확인</div>
					<div class="mb-3">
						<input type="password" v-model="newPasswordConfirm" class="border rounded w-50" maxlength="18" />
					</div>
				</div>
				<div class="mb-3">※ 비밀번호는 단방향 암호화 되어 전송, 저장됩니다.</div>
				<button class="p-2 rounded" @click="changePassword">저장</button>
			</div>
		</div>
	</div>
</template>

<script>
import sha256 from 'crypto-js/sha256';
import { updateUserPassword } from '@/api';

export default {
	name: 'UserPage',
	data() {
		return {
			currentPassword: '',
			newPassword: '',
			newPasswordConfirm: '',
			email: '',
			name: ''
		};
	},
	mounted() {
		this.name = this.$store.state.name;
		this.email = this.$store.state.email;
	},
	methods: {
		async changePassword() {
			if (this.currentPassword == '') {
				alert('현재 패스워드를 입력해주세요.');
				return;
			}
			if (this.newPassword == '') {
				alert('새로운 패스워드를 입력해주세요.');
				return;
			}
			if (this.newPasswordConfirm == '') {
				alert('패스워드 확인을입력해주세요.');
				return;
			}
			if (this.newPassword != this.newPasswordConfirm) {
				alert('패스워드와 확인값이 일치하지 않습니다.');
				return;
			}
			const data = {
				id: this.$store.state.id,
				email: this.email,
				currentPassword: sha256(this.currentPassword).toString(),
				newPassword: sha256(this.newPassword).toString()
			};
			try {
				const response = await updateUserPassword(data);
				if (response.data.status != 200) {
					alert(response.data.message);
				} else {
					alert('변경 완료!');
					this.currentPassword = '';
					this.newPasswordConfirm = '';
					this.newPassword = '';
				}
			} catch (e) {
				alert('서버에러');
			}
		}
	}
};
</script>

<style scoped></style>
