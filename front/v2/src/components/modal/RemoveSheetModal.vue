<template>
	<span>
		<button class="button-danger rounded p-2 mx-1" @click="modal = true">시트 삭제</button>
		<mdb-modal size="sm" :show="modal" @close="modal = false" centered>
			<mdb-modal-header style="background-color: #c9ede4">
				<mdb-modal-title>시트 삭제</mdb-modal-title>
			</mdb-modal-header>
			<mdb-modal-body>
				<div>
					<h3>삭제 후 <br />복구가 불가능합니다.</h3>
				</div>
			</mdb-modal-body>
			<mdb-modal-footer>
				<button class="button-danger px-3 py-2 rounded" @click="remove">삭제</button>
			</mdb-modal-footer>
		</mdb-modal>
	</span>
</template>

<script>
import { mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody, mdbModalFooter } from 'mdbvue';
import { removeSheet } from '@/api';

export default {
	name: 'RemoveSheetModal',
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
		async remove() {
			try {
				const response = await removeSheet(this.$route.params.sheetId);
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
