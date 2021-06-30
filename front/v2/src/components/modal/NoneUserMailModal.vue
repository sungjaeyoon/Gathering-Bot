<template>
	<span>
		<button class="button-danger rounded p-2 mx-1" @click="modal = true">미응답자 메일 발송</button>
		<mdb-modal size="sm" :show="modal" @close="modal = false" centered>
			<mdb-modal-header style="background-color: #c9ede4">
				<mdb-modal-title>미응답자 메일 발송</mdb-modal-title>
			</mdb-modal-header>
			<mdb-modal-body>
				<div>
					<h3>메일이 재발송됩니다.</h3>
				</div>
				<div>10분 이내 재발송 내역이 있다면 <br />재발송되지 않습니다.</div>
			</mdb-modal-body>
			<mdb-modal-footer>
				<button class="button-danger px-3 py-2 rounded" @click="send">재발송</button>
			</mdb-modal-footer>
		</mdb-modal>
	</span>
</template>

<script>
import { mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody, mdbModalFooter } from 'mdbvue';
import { sendNoneMailAll } from '@/api';

export default {
	name: 'NoneUserMailModal',
	components: {
		mdbModal,
		mdbModalHeader,
		mdbModalTitle,
		mdbModalBody,
		mdbModalFooter
	},
	data() {
		return {
			modal: false
		};
	},
	methods: {
		send() {
			sendNoneMailAll(this.$route.params.sheetId);
			alert('발송완료');
			this.modal = false;
		}
	}
};
</script>

<style scoped></style>
