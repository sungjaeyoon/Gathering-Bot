<template>
	<span>
		<button class="button-danger rounded p-2 mx-1" @click="modal = true">시트 종료</button>
		<mdb-modal size="sm" :show="modal" @close="modal = false" centered>
			<mdb-modal-header style="background-color: #c9ede4">
				<mdb-modal-title>시트 종료</mdb-modal-title>
			</mdb-modal-header>
			<mdb-modal-body>
				<div>
					<h3>시트가 종료됩니다.</h3>
				</div>
				<div>
					답변이 수정되지 않습니다.
				</div>
			</mdb-modal-body>
			<mdb-modal-footer>
				<button class="button-danger px-3 py-2 rounded" @click="end">종료</button>
			</mdb-modal-footer>
		</mdb-modal>
	</span>
</template>

<script>
import { mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody, mdbModalFooter } from 'mdbvue';
import { endSheet } from '@/api';

export default {
	name: 'EndSheetModal',
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
		async end() {
			try {
				const response = await endSheet(this.$route.params.sheetId);
				if (response.data.status != 200) {
					alert(response.data.message);
				}
			} catch (e) {
				alert('서버에러');
			}
			this.$router.push('/list');
			return;
		}
	}
};
</script>

<style scoped></style>
