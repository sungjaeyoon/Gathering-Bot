<template>
	<span>
		<button class="rounded px-2" @click="modal = true">수정 요청</button>
		<mdb-modal size="md" :show="modal" @close="modal = false" centered>
			<mdb-modal-header style="background-color: #c9ede4">
				<mdb-modal-title>수정 요청</mdb-modal-title>
			</mdb-modal-header>
			<mdb-modal-body>
				<div>
					<h5 class="text-left">수정 요청 사유 입력</h5>
				</div>
				<div class="w-100">
					<input type="text" v-model="message" class="border rounded w-100" maxlength="50" width="100px" />
				</div>
			</mdb-modal-body>
			<mdb-modal-footer>
				<button class="px-3 py-2 rounded" @click="sendEmail">요청</button>
			</mdb-modal-footer>
		</mdb-modal>
	</span>
</template>

<script>
import { mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody, mdbModalFooter } from 'mdbvue';
import { sendModifiedMail } from '@/api';

export default {
	name: 'ModifiedMailModal',
	props: {
		responseId: Number
	},
	components: {
		mdbModal,
		mdbModalHeader,
		mdbModalTitle,
		mdbModalBody,
		mdbModalFooter
	},
	data() {
		return {
			modal: false,
			message: ''
		};
	},
	methods: {
		async sendEmail() {
			if (this.message == '') {
				alert('수정 사유를 입력해주세요.');
				return;
			}
			try {
				const response = await sendModifiedMail(this.responseId, this.message);
				if (response.data.status != 200) {
					alert(response.data.message);
				} else {
					alert('발송 완료');
				}
			} catch (e) {
				alert('서버 에러');
			}
			this.modal = false;
		}
	}
};
</script>

<style scoped></style>
