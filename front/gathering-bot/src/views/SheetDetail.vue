<template>
	<div class=" ml-5 mr-5">
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
				<button v-if="sheetStatus != 'WAIT'" class="btn btn-primary mr-2">엑셀 내보내기</button>
				<span v-if="sheetStatus == 'PROCEEDING'">
					<button class="btn btn-danger mr-2">미응답자 메일 발송</button>
					<button class="btn btn-danger mr-2" @click="endSheet">시트 종료</button>
				</span>
				<span v-if="sheetStatus == 'WAIT'">
					<button class="btn btn-success mr-2" @click="startSheet">시트 시작</button>
				</span>
			</div>
			<span v-if="sheetStatus == 'FINISHED'">
				<h1 class="mb-5">종료된 시트</h1>
			</span>
			<table class="table table-striped" style="text-align: center">
				<thead>
					<tr style="background-color: #7d2ae8;color: white">
						<th scope="col">#</th>
						<th scope="col">이름</th>
						<th scope="col">직책</th>
						<th scope="col">팀</th>
						<th scope="col">응답상태</th>
						<th v-for="(question, index) in sheetQuestions" :key="index" scope="col">{{ question }}</th>
						<th scope="col" v-if="sheetStatus != 'WAIT'">응답날짜</th>
						<th scope="col" v-if="sheetStatus != 'WAIT'">수정날짜</th>
						<th scope="col" v-if="sheetStatus == 'PROCEEDING'">응답 수정</th>
						<th scope="col" v-if="sheetStatus == 'PROCEEDING'">메일 전송</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(line, index) in responseData" :key="index">
						<th scope="row">{{ index + 1 }}</th>
						<td>{{ line.name }}</td>
						<td>{{ line.position }}</td>
						<td>{{ line.teamName }}</td>
						<td>
							<span v-if="line.requestStatus == 'YES'" style=""><span class="dot dot-yes"></span></span>
							<span v-else
								><div class="spinner-border spinner-border-sm text-danger" role="status">
									<span class="sr-only">Loading...</span>
								</div></span
							>
						</td>
						<td v-for="(text, index2) in line.response" :key="index2">{{ text }}</td>
						<td v-if="sheetStatus != 'WAIT'">{{ line.responseDate != null ? line.responseDate.split('T').join('  ') : '' }}</td>
						<td v-if="sheetStatus != 'WAIT'">{{ line.modifiedDate != null ? line.modifiedDate.split('T').join('  ') : '' }}</td>
						<td v-if="sheetStatus == 'PROCEEDING'"><Button class="btn btn-info">Edit</Button></td>
						<td v-if="sheetStatus == 'PROCEEDING'">
							<Button v-if="line.requestStatus == 'YES'" class="btn btn-warning">수정 요청</Button>
							<Button v-if="line.requestStatus == 'NO'" class="btn btn-warning">재전송</Button>
						</td>
					</tr>
					<tr style="background-color: cornflowerblue;color: white">
						<th>예시</th>
						<td>-</td>
						<td>-</td>
						<td>-</td>
						<td>-</td>
						<td v-for="(example, index3) in sheetExamples" :key="index3">{{ example }}</td>
						<td v-if="sheetStatus != 'WAIT'">-</td>
						<td v-if="sheetStatus != 'WAIT'">-</td>
						<td v-if="sheetStatus == 'PROCEEDING'">-</td>
						<td v-if="sheetStatus == 'PROCEEDING'">-</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script>
import { getDetail } from '@/api';

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
			responseData: [],
		};
	},
	mounted() {
		this.loadSheet();
		this.loadDetail();
		console.log(this.responseData);
	},
	methods: {
		async loadSheet() {},
		async loadDetail() {
			const responseDetail = await getDetail(this.$route.params.id);
			const responseDetailData = responseDetail.data;

			console.log(responseDetailData[0]);
			this.sheetTitle = responseDetailData[0].title;
			this.sheetColNum = responseDetailData[0].colNum;
			this.sheetContent = responseDetailData[0].content;
			this.sheetQuestions = responseDetailData[0].question.split('&&&&');
			this.sheetExamples = responseDetailData[0].example.split('&&&&');
			this.sheetCreatedDate = responseDetailData[0].createdDate;
			this.sheetStatus = responseDetailData[0].status;

			for (let i = 1; i < responseDetailData.length; i++) {
				const response = responseDetailData[i].response;
				if (response != null) {
					const list = responseDetailData[i].response.split('&&&&');
					responseDetailData[i].response = list;
				} else {
					const list = [];
					for (let j = 0; j < this.sheetColNum; j++) {
						list.push('-');
					}
					responseDetailData[i].response = list;
				}
				this.responseData.push(responseDetailData[i]);
			}
			this.loading = false;
		},
		async startSheet() {
			console.log('start sheet');
		},
		async endSheet() {
			console.log('end sheet');
		},
	},
};
</script>

<style scoped>
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
.dot-no {
	background-color: red;
}
</style>
