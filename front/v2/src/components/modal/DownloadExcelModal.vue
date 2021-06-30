<template>
	<span>
		<button class="rounded p-2 mx-1" @click="modal = true">엑셀 다운로드</button>
		<mdb-modal size="md" :show="modal" @close="modal = false">
			<mdb-modal-header style="background-color: #c9ede4">
				<mdb-modal-title>다운로드</mdb-modal-title>
			</mdb-modal-header>
			<mdb-modal-body>
				<download-excel class="btn btn-default" :before-generate="downloadFormat" :data="json_data" :fields="json_fields" worksheet="My Worksheet" :name="this.title + '.xls'"
					>엑셀 다운로드</download-excel
				>
			</mdb-modal-body>
		</mdb-modal>
	</span>
</template>

<script>
import JsonExcel from 'vue-json-excel';
import { mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody } from 'mdbvue';
export default {
	name: 'DownloadExcelModal',
	components: {
		mdbModal,
		mdbModalHeader,
		mdbModalTitle,
		mdbModalBody,
		downloadExcel: JsonExcel
	},
	props: {
		title: String,
		responseList: Array,
		questions: Array
	},
	data() {
		return {
			modal: false,
			json_fields: {},
			json_data: [],
			json_meta: [
				[
					{
						key: 'charset',
						value: 'utf-8'
					}
				]
			]
		};
	},
	methods: {
		downloadFormat() {
			let field = {
				팀명: 'teamName',
				성명: 'name',
				직책: 'position'
			};
			for (let i = 0; i < this.questions.length; i++) {
				field['Q' + (i + 1) + '.' + this.questions[i]] = 'q' + i;
			}
			this.json_fields = field;
			let dataList = [];
			for (let i = 0; i < this.responseList.length; i++) {
				const response = this.responseList[i];
				let data = {
					teamName: response.teamName,
					name: response.userName,
					position: response.position
				};
				for (let j = 0; j < response.response.length; j++) {
					data['q' + j] = response.response[j];
				}
				dataList.push(data);
			}
			this.json_data = dataList;
		}
	}
};
</script>

<style scoped></style>
