<template>
	<div class=" ml-5 mr-5 shadow-lg p-3 mb-5 bg-white rounded">
		<span>
			<h1>{{ sheetTitle }}</h1>
		</span>
		<span>
			<!-- Modal start-->
			<div class="modal fade" id="startModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-body">
							<h1 class="text-danger">시트가 시작됩니다.</h1>
							<h2 class="text-danger">모두에게 메일을 발송합니다.</h2>
							<h2 class="text-danger">메일 발송을 취소할 수 없습니다.</h2>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
							<button type="button" class="btn btn-success" @click="startSheet" data-dismiss="modal">시작</button>
						</div>
					</div>
				</div>
			</div>
			<!-- Modal end-->
			<div class="modal fade" id="endModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-body">
							<h1 class="text-danger">시트가 종료됩니다.</h1>
							<h2 class="text-danger">종료 후 항목 수정이 불가능합니다.</h2>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
							<button type="button" class="btn btn-danger" @click="endSheet" data-dismiss="modal">종료</button>
						</div>
					</div>
				</div>
			</div>
		</span>
		<!--loading-->
		<div class="text-center">
			<span v-if="loading" class="spinner-border text-center" role="status" style="width: 200px;height: 200px">
				<span class="sr-only">Loading...</span>
			</span>
			<div v-if="loading" class="mt-5"><h1>로딩중...</h1></div>
		</div>
		<!--content-->
		<div v-if="!loading">
			<div class="float-left mb-3">
				<div class="dropdown">
					<a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						전체보기
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
						<a class="dropdown-item" href="#">전체보기</a>
						<a class="dropdown-item" href="#">응답자</a>
						<a class="dropdown-item" href="#">미응답자</a>
					</div>
				</div>
			</div>
			<div class="float-right mb-3">
				<button v-if="sheetStatus != 'WAIT'" class="btn btn-outline-dark mr-2">엑셀 내보내기</button>
				<span v-if="sheetStatus == 'PROCEEDING'">
					<button class="btn btn-outline-danger mr-2">미응답자 메일 발송</button>
					<button class="btn btn-danger mr-2" data-toggle="modal" data-target="#endModal">시트 종료</button>
				</span>
				<span v-if="sheetStatus == 'WAIT'">
					<button class="btn btn-success mr-2" data-toggle="modal" data-target="#startModal">시트 시작</button>
				</span>
			</div>
			<div>
				<span v-if="sheetStatus == 'WAIT'">
					<h4 class="mb-5">(대기중)</h4>
				</span>
				<span v-if="sheetStatus == 'PROCEEDING'">
					<h4 class="mb-5">(진행중)</h4>
				</span>
				<span v-if="sheetStatus == 'FINISHED'">
					<h4 class="mb-5">(종료)</h4>
				</span>
			</div>
			<div style="overflow:auto; overflow-y:hidden">
				<table class="table table-bordered" style="text-align: center">
					<thead>
						<tr class="table-active">
							<th scope="col">#</th>
							<th scope="col">이름</th>
							<th scope="col">직책</th>
							<th scope="col">팀</th>
							<th scope="col" v-if="sheetStatus != 'WAIT'">응답상태</th>
							<th v-for="(question, index) in sheetQuestions" :key="index" scope="col">{{ question }}</th>
							<th scope="col" v-if="sheetStatus != 'WAIT'">응답날짜</th>
							<th scope="col" v-if="sheetStatus != 'WAIT'">수정날짜</th>
							<!--							<th scope="col" v-if="sheetStatus == 'PROCEEDING'">응답 수정</th>-->
							<th scope="col" v-if="sheetStatus == 'PROCEEDING'">메일 전송</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(line, index) in responseData" :key="index">
							<th scope="row">{{ index + 1 }}</th>
							<td>{{ line.name }}</td>
							<td>{{ line.position }}</td>
							<td>{{ line.teamName }}</td>
							<td v-if="sheetStatus != 'WAIT'">
								<span v-if="line.requestStatus == 'YES'" style=""><span class="dot dot-yes"></span></span>
								<span v-else>
									<div v-if="sheetStatus == 'PROCEEDING'" class="spinner-border spinner-border-sm text-danger" role="status">
										<span class="sr-only">Loading...</span>
									</div>
									<div v-else>
										<span class="dot dot-no"></span>
									</div>
								</span>
							</td>
							<td v-for="(text, index2) in line.response" :key="index2">{{ text }}</td>
							<td v-if="sheetStatus != 'WAIT'">{{ line.responseDate != null ? line.responseDate.split('T').join('  ') : '' }}</td>
							<td v-if="sheetStatus != 'WAIT'">{{ line.modifiedDate != null ? line.modifiedDate.split('T').join('  ') : '' }}</td>
							<!--							<td v-if="sheetStatus == 'PROCEEDING'"><Button class="btn btn-info">Edit</Button></td>-->
							<td v-if="sheetStatus == 'PROCEEDING'">
								<Button v-if="line.requestStatus == 'YES'" class="btn btn-outline-warning">수정 요청</Button>
								<Button v-if="line.requestStatus == 'NO'" class="btn btn-outline-warning">재전송</Button>
							</td>
						</tr>
						<tr class="table-active">
							<th>예시</th>
							<td>-</td>
							<td>-</td>
							<td>-</td>
							<td v-if="sheetStatus != 'WAIT'">-</td>
							<td v-for="(example, index3) in sheetExamples" :key="index3">{{ example }}</td>
							<td v-if="sheetStatus != 'WAIT'">-</td>
							<td v-if="sheetStatus != 'WAIT'">-</td>
							<!--							<td v-if="sheetStatus == 'PROCEEDING'">-</td>-->
							<td v-if="sheetStatus == 'PROCEEDING'">-</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>

<script>
import { getDetail, startSheet, endSheet, sendMailAll } from '@/api';

export default {
	name: 'SheetDetail',
	data() {
		return {
			sheetTitle: '',
			sheetColNum: 0,
			sheetContent: '',
			sheetQuestions: [],
			sheetExamples: [],
			sheetCreatedDate: '',
			sheetStatus: '',
			loading: true,
			responseData: []
		};
	},
	mounted() {
		this.loadSheet();
		this.loadDetail();
	},
	methods: {
		async loadSheet() {},
		async loadDetail() {
			const responseDetail = await getDetail(this.$route.params.id);

			if (responseDetail.data.status != 200) {
				alert(responseDetail.data.message);
				return;
			}
			const responseDetailData = responseDetail.data;

			const sheet = responseDetailData.sheet;
			this.sheetTitle = sheet.title;
			this.sheetColNum = sheet.colNum;
			this.sheetContent = sheet.content;
			this.sheetQuestions = sheet.question.split('&&&&');
			this.sheetExamples = sheet.example.split('&&&&');
			this.sheetCreatedDate = sheet.createdDate;
			this.sheetStatus = sheet.status;

			const memberSheet = responseDetailData.memberSheet;
			this.responseData = [];
			for (let i = 0; i < memberSheet.length; i++) {
				const response = memberSheet[i].response;
				if (response != null) {
					const list = memberSheet[i].response.split('&&&&');
					memberSheet[i].response = list;
				} else {
					const list = [];
					for (let j = 0; j < this.sheetColNum; j++) {
						list.push('-');
					}
					memberSheet[i].response = list;
				}
				this.responseData.push(memberSheet[i]);
			}
			this.loading = false;
		},
		async startSheet() {
			this.loading = true;
			const response = await startSheet(this.$route.params.id);
			this.loading = false;
			this.sendEmailAll();
			await this.loadDetail();
		},
		async endSheet() {
			this.loading = true;
			const response = await endSheet(this.$route.params.id);
			this.loading = false;
			await this.loadDetail();
		},
		sendEmailAll() {
			sendMailAll(this.$route.params.id);
			console.log('send');
		}
	}
};
</script>

<style scoped>
h1,
h2,
h3,
h4 {
	text-align: center;
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
	background-color: green;
}
th {
	min-width: 70px;
}
td {
	min-width: 120px;
}
.dot-no {
	background-color: red;
}
</style>
