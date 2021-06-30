<template>
	<span>
		<button class="rounded p-2 mx-1" @click="modal = true">시트 시작</button>
		<mdb-modal size="sm" :show="modal" @close="modal = false" centered>
			<mdb-modal-header style="background-color: #c9ede4">
				<mdb-modal-title>시트 시작</mdb-modal-title>
			</mdb-modal-header>
			<mdb-modal-body>
				<div>
					<h3>
						시트가 시작됩니다.
					</h3>
				</div>
				<div>
					메일이 발송되며 취소할 수 없습니다.
				</div>
			</mdb-modal-body>
			<mdb-modal-footer>
				<button class=" px-3 py-2 rounded" @click="start">시작</button>
			</mdb-modal-footer>
		</mdb-modal>
	</span>
</template>

<script>
import { mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody, mdbModalFooter } from 'mdbvue';
import { sendMailAll, startSheet } from '@/api';
export default {
	name: 'StartSheetModal',
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
		async start() {
			try {
				const response = await startSheet(this.$route.params.sheetId);
				if (response.data.status != 200) {
					alert(response.data.message);
				}
			} catch (e) {
				alert('서버에러');
			}
			this.sendMailAll();
			this.$router.push('/list');
			return;
		},
		sendMailAll() {
			sendMailAll(this.$route.params.sheetId);
		}
	}
};
</script>

<style scoped></style>
