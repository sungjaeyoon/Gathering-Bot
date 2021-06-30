<template>
	<div>
		<div v-if="loading" class="mt-5">
			<div class="spinner-border text-center" role="status" style="width: 150px;height: 150px">
				<span class="sr-only">Loading...</span>
			</div>
		</div>
		<div v-else>
			<h1 class="mt-5" style="font-family: KTBold">{{ title }}</h1>
			<div class="row mx-auto" style="width: 90%">
				<div class="col float-left">
					<mdb-dropdown class="float-left" style="background-color: white">
						<mdb-dropdown-toggle slot="toggle" class="drop-button px-4 py-2 z-depth-0 border">{{ typeWord }}</mdb-dropdown-toggle>
						<mdb-dropdown-menu>
							<mdb-dropdown-item @click="changeFilter('전체보기', '')">전체보기</mdb-dropdown-item>
							<mdb-dropdown-item @click="changeFilter('응답자', 'YES')">응답자</mdb-dropdown-item>
							<mdb-dropdown-item @click="changeFilter('미응답자', 'NO')">미응답자</mdb-dropdown-item>
						</mdb-dropdown-menu>
					</mdb-dropdown>
				</div>
				<!--        취합자 메뉴-->
				<template v-if="owner">
					<div class="col float-right">
						<div v-if="sheetStatus == 'WAIT'" class="float-right">
							<StartSheetModal></StartSheetModal>
							<ShowSheetModal v-bind:sheet="sheetInfo"></ShowSheetModal>
							<RemoveSheetModal></RemoveSheetModal>
						</div>
						<div v-if="sheetStatus == 'PROCEEDING'" class="float-right">
							<button class="rounded p-2 mx-1" @click="reloadSheet">새로고침</button>
							<ShowSheetModal v-bind:sheet="sheetInfo"></ShowSheetModal>
							<DownloadExcelModal v-bind:title="title" v-bind:questions="questions" v-bind:response-list="responseList"></DownloadExcelModal>
							<NoneUserMailModal></NoneUserMailModal>
							<EndSheetModal></EndSheetModal>
						</div>
						<div v-if="sheetStatus == 'FINISHED'" class="float-right">
							<ShowSheetModal v-bind:sheet="sheetInfo"></ShowSheetModal>
							<DownloadExcelModal v-bind:title="title" v-bind:questions="questions" v-bind:response-list="responseList"></DownloadExcelModal>
							<RemoveSheetModal></RemoveSheetModal>
						</div>
					</div>
				</template>
			</div>
			<div class="row mx-auto mt-3 z-depth-1  scrollbar-info" style="background-color: white;width: 90%;overflow: auto">
				<table class="mb-0 table table-hover border">
					<thead>
						<tr class="font-weight-bold" style="background-color: #C9EDE4">
							<th width="5%">번호</th>
							<th width="10%">팀</th>
							<th width="7%">이름</th>
							<th width="7%">직책</th>
							<th width="5%" v-if="sheetStatus != 'WAIT'">응답상태</th>
							<th v-for="(question, index) in questions" :key="index">{{ question }}</th>
							<th width="10%">답변시간</th>
							<th width="10%">수정시간</th>
							<th v-if="owner && sheetStatus == 'PROCEEDING'" width="10%">재전송</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(responseInfo, index) in filterList" :key="index">
							<td>{{ index + 1 }}</td>
							<td>{{ responseInfo.teamName }}</td>
							<td>{{ responseInfo.userName }}</td>
							<td>{{ responseInfo.position }}</td>
							<td v-if="sheetStatus != 'WAIT'">
								<span v-if="responseInfo.requestStatus == 'YES'" style=""><span class="dot dot-yes"></span></span>
								<span v-else><span class="dot dot-no"></span></span>
							</td>
							<td v-for="(response, index2) in responseInfo.response" :key="index2" class="text-left">{{ response }}</td>
							<td>{{ responseInfo.responseDate }}</td>
							<td>{{ responseInfo.modifiedDate }}</td>
							<td v-if="owner && sheetStatus == 'PROCEEDING'">
								<button v-if="responseInfo.requestStatus == 'NO'" class="rounded px-2" @click="sendEmailOne(responseInfo.id)">재전송</button>
								<ModifiedMailModal v-else v-bind:response-id="responseInfo.id"></ModifiedMailModal>
							</td>
						</tr>
						<tr style="background-color: #C9EDE4">
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td v-if="sheetStatus != 'WAIT'">예시</td>
							<td v-for="(example, index) in examples" :key="index">{{ example }}</td>
							<td></td>
							<td></td>
							<td v-if="owner && sheetStatus == 'PROCEEDING'"></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>

<script>
import StartSheetModal from '@/components/modal/StartSheetModal.vue';
import EndSheetModal from '@/components/modal/EndSheetModal.vue';
import RemoveSheetModal from '@/components/modal/RemoveSheetModal.vue';
import ShowSheetModal from '@/components/modal/ShowSheetModal.vue';
import NoneUserMailModal from '@/components/modal/NoneUserMailModal.vue';
import ModifiedMailModal from '@/components/modal/ModifiedMailModal';
import DownloadExcelModal from '@/components/modal/DownloadExcelModal';
import { mdbDropdown, mdbDropdownItem, mdbDropdownMenu, mdbDropdownToggle } from 'mdbvue';
import { getSheetDetail, sendMailOne, sendModifiedMail } from '@/api';

export default {
	name: 'SheetDetailPage',
	components: {
		mdbDropdown,
		mdbDropdownItem,
		mdbDropdownMenu,
		mdbDropdownToggle,
		StartSheetModal,
		EndSheetModal,
		RemoveSheetModal,
		ShowSheetModal,
		NoneUserMailModal,
		ModifiedMailModal,
		DownloadExcelModal
	},
	data() {
		return {
			owner: false,
			loading: true,
			content: '',
			createdDate: '',
			email: '',
			examples: [],
			finishedDate: '',
			lastSendMailDate: '',
			position: '',
			questions: [],
			responseList: [],
			filterList: [],
			shareType: '',
			sheetStatus: '',
			teamName: '',
			title: '',
			userName: '',
			typeWord: '전체보기',
			colNum: 0
		};
	},
	computed: {
		sheetInfo() {
			const sheet = {
				content: this.content,
				createdDate: this.createdDate,
				finishedDate: this.finishedDate,
				shareType: this.shareType,
				sheetStatus: this.sheetStatus,
				title: this.title
			};
			return sheet;
		}
	},
	mounted() {
		this.loadSheetDetail();
	},
	methods: {
		async loadSheetDetail() {
			try {
				const response = await getSheetDetail(this.$route.params.sheetId, this.$route.params.token);
				if (response.data.status != 200) {
					alert(response.data.message);
					this.$router.push('/notfound');
					return;
				}
				const data = response.data.data;
				// console.log(data);
				this.content = data.content;
				this.createdDate = data.createdDate;
				this.email = data.email;
				this.examples = data.example;
				this.finishedDate = data.finishedDate;
				this.lastSendMailDate = data.lastSendMailDate;
				this.position = data.position;
				this.questions = data.question;
				this.shareType = data.shareType;
				this.sheetStatus = data.sheetStatus;
				this.teamName = data.teamName;
				this.title = data.title;
				this.userName = data.userName;
				this.responseList = data.responseList;
				this.colNum = data.colNum;

				this.questions = this.questions.split('&&&&');
				this.examples = this.examples.split('&&&&');

				if (this.email == this.$store.state.email) {
					this.owner = true;
				}

				for (let i = 0; i < data.responseList.length; i++) {
					const resString = data.responseList[i].response;
					if (resString != null) {
						this.responseList[i].response = resString.split('&&&&');
					} else {
						let arr = [];
						for (let j = 0; j < this.colNum; j++) {
							arr.push('-');
						}
						this.responseList[i].response = arr;
					}
				}
				this.filterList = this.responseList;
			} catch (e) {
				alert('서버 에러');
			}
			this.loading = false;
		},
		changeFilter(typeWord, type) {
			this.typeWord = typeWord;
			if (type != '') {
				this.filterList = this.responseList.filter(data => data.requestStatus == type);
			} else {
				this.filterList = this.responseList;
			}
		},
		reloadSheet() {
			this.loading = true;
			this.loadSheetDetail();
		},
		downloadExcel() {},
		async sendEmailOne(responseId) {
			try {
				const response = await sendMailOne(responseId);
				if (response.data.status != 200) {
					alert(response.data.message);
				} else {
					alert('발송 완료');
				}
			} catch (e) {
				alert('서버 에러');
			}
		}
	}
};
</script>

<style scoped>
.drop-button:hover {
	background-color: #bbbbbb;
}
.dot {
	padding-top: 1%;
	height: 20px;
	width: 20px;
	background-color: #bbb;
	border-radius: 50%;
	display: inline-block;
}
.dot-yes {
	background-color: #5fe25f;
}
.dot-no {
	background-color: #e66c6c;
}
td,
th {
	min-width: 100px;
	max-width: 600px;
	word-wrap: break-word;
}
</style>
PRIMARY
