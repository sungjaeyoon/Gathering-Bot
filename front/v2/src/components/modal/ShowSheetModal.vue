<template>
	<span>
		<button class="rounded p-2 mx-1" @click="modal = true">시트 내용</button>
		<mdb-modal size="lg" :show="modal" @close="modal = false">
			<mdb-modal-header style="background-color: #c9ede4">
				<mdb-modal-title>시트 내용</mdb-modal-title>
			</mdb-modal-header>
			<mdb-modal-body>
				<div class="mx-3">
					<div class="row rounded">
						<div class="col-2 content-text border rounded-left">
							<p>
								시트 제목
							</p>
						</div>
						<div class="col-10 content-input border">
							<p class="w-100 border-0">
								<input v-model="sheet.title" class="pl-3 border-0  rounded-right" type="text" style="height: 45px; width: 100%" disabled />
							</p>
						</div>
					</div>
					<div class="row rounded my-1">
						<div class="col-2 content-text border rounded-left">
							<p>
								완료 기한
							</p>
						</div>
						<div class="col-10 content-input border">
							<p class="w-100 border-0">
								<input v-model="sheet.finishedDate" class="pl-3 border-0  rounded-right" type="text" style="height: 45px; width: 100%" disabled />
							</p>
						</div>
					</div>
					<div class="row rounded my-1">
						<div class="col-2 content-text border rounded-left">
							<p>
								시트 공유
							</p>
						</div>
						<div class="col-10 content-input border">
							<p class="w-100 border-0">
								<input v-model="sheetTypeWord" class="pl-3 border-0  rounded-right" type="text" style="height: 45px; width: 100%" disabled />
							</p>
						</div>
					</div>
					<div class="row border-top rounded-top">
						<div class="col-12 content-text border  rounded-top" style="height: 33px">
							<p>
								시트 내용
							</p>
						</div>
					</div>
					<div class="row border-left border-right border-bottom rounded-bottom">
						<div class="col-12 p-0 scrollbar-info rounded-bottom" style="background-color: white;overflow: scroll">
							<span class="m-1" v-html="sheet.content"></span>
						</div>
					</div>
				</div>
			</mdb-modal-body>
			<mdb-modal-footer>
				<button class="button-danger px-3 py-2 rounded" @click="modal = false">닫기</button>
			</mdb-modal-footer>
		</mdb-modal>
	</span>
</template>

<script>
import { mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody, mdbModalFooter } from 'mdbvue';
export default {
	props: {
		sheet: Object
	},
	name: 'ShowSheetModal',
	components: {
		mdbModal,
		mdbModalHeader,
		mdbModalTitle,
		mdbModalBody,
		mdbModalFooter
	},
	computed: {
		sheetTypeWord() {
			const type = this.sheet.shareType;
			if (type == 'PRIVATE') {
				return '취합자만 열람가능';
			} else if (type == 'PUBLIC') {
				return '수신자 모두 열람가능';
			} else {
				return '링크가 있는 모든 사용자 열람 가능';
			}
		}
	},
	data() {
		return {
			modal: false
		};
	}
};
</script>

<style scoped>
.content-text {
	background-color: #c9ede4;
	font-weight: bold;
	font-size: 20px;
	height: 45px;
	position: relative;
}

.content-text p {
	margin: 0;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	min-width: 100px;
	text-overflow: ellipsis;
}

.content-input {
	height: 45px;
	position: relative;
}

.content-input p {
	margin: 0;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	border-color: white;
}
</style>
