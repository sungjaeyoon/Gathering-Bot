<template>
	<div>
		<button class="mb-2 rounded m-0 py-2 px-4 float-right" @click="showModal">저 장</button>
		<mdb-modal size="sm" :show="modal" @close="modal = false" centered>
			<mdb-modal-header style="background-color: #c9ede4">
				<mdb-modal-title>시트 저장</mdb-modal-title>
			</mdb-modal-header>
			<mdb-modal-body>
				저장하시겠습니까?
			</mdb-modal-body>
			<mdb-modal-footer>
				<button class="px-3 py-2 rounded" @click="save">저장</button>
			</mdb-modal-footer>
		</mdb-modal>
	</div>
</template>

<script>
import { mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody, mdbModalFooter } from 'mdbvue';
export default {
	name: 'SaveSheetModal',
	components: {
		mdbModal,
		mdbModalHeader,
		mdbModalTitle,
		mdbModalBody,
		mdbModalFooter
	},
	props: {
		title: String,
		finished: String,
		shareType: String,
		content: String,
		table: Array,
		userList: Array
	},
	data() {
		return {
			modal: false
		};
	},
	methods: {
		validSheet() {
			if (this.title == 0) {
				alert('제목은 필수입니다.');
				return false;
			}
			if (this.title.length > 50) {
				alert('제목이 길이를 초과했습니다.');
				return false;
			}
			if (this.finished == '') {
				alert('완료기한은 필수입니다.');
				return false;
			}
			if (this.userList.length == 0) {
				alert('수신자는 필수입니다.');
				return false;
			}
			if (this.content == '') {
				alert('내용은 필수입니다.');
				return false;
			}
			if (this.content.length >= 40000) {
				alert('내용의 길이를 초과했습니다.');
				return false;
			}
			if (this.table.length == 0) {
				alert('취합 내용은 필수입니다.');
				return false;
			}
			for (let i = 0; i < this.table.length; i++) {
				if (this.table[i].content == '') {
					alert(i + 1 + ' 번째 항목이 빈칸입니다.');
					return false;
				}
				if (this.table[i].content.length >= 200) {
					alert(i + 1 + ' 번째 항목가 길이를 초과했습니다.');
					return false;
				}
				if (this.table[i].example == '') {
					alert(i + 1 + ' 번째 예시가 빈칸입니다.');
					return false;
				}
				if (this.table[i].example.length >= 200) {
					alert(i + 1 + ' 번째 예시가 길이를 초과했습니다.');
					return false;
				}
			}
			return true;
		},
		showModal() {
			if (!this.validSheet()) {
				return;
			}
			this.modal = true;
		},
		save() {
			this.$emit('save');
		}
	}
};
</script>

<style scoped></style>
