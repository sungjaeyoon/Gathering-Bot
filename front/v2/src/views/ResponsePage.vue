<template>
	<div>
		<h1 class="my-5" style="font-family: KTBold">{{ title }} - 답 변</h1>

		<div v-if="loading" class="mt-5">
			<div class="spinner-border text-center" role="status" style="width: 150px;height: 150px">
				<span class="sr-only">Loading...</span>
			</div>
		</div>
		<div v-else>
			<div class="mt-3 mx-auto scrollbar-info" style="overflow:auto; overflow-y:auto;width: 85%">
				<table class="table table-bordered" style="background-color: white">
					<thead>
						<tr>
							<td></td>
							<td></td>
							<td style="min-width: 150px;">항목</td>
							<td v-for="(question, index) in questions" :key="index" style="max-width: 500px; ;min-width: 300px;">
								{{ question }}
							</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td width="10%" style="min-width: 150px;">{{ teamName }}</td>
							<td width="10%">{{ userName }}</td>
							<td width="5%">{{ position }}</td>
							<td class="p-0" v-for="(question, index) in questions" :key="index">
								<textarea class="form-control m-0 p-0 w-100 pl-3 rounded-left scrollbar-info" v-model="response[index]" type="text" style="height: 70px" placeholder="답변 입력" />
							</td>
						</tr>
						<tr style="background-color: #c9ede4">
							<td></td>
							<td></td>
							<td>예시</td>
							<td v-for="(example, index) in examples" :key="index">
								{{ example }}
							</td>
						</tr>
						<!--					<tr v-for="index in colNum" :key="index">-->
						<!--						<td width="10%" style="min-width: 150px;"></td>-->
						<!--						<td width="10%"></td>-->
						<!--						<td width="5%"></td>-->
						<!--						<td class="p-0" v-for="(question, index2) in questions" :key="index2">-->
						<!--							<textarea class="form-control m-0 p-0 w-100 pl-3" v-model="addResponse[index - 1][index2]" type="text" style="height: 70px" placeholder="답변 입력" />-->
						<!--						</td>-->
						<!--						<td>-->
						<!--							<button class="m-0 px-2 rounded" style="background-color: #d25b49" @click="removeRow(index - 1)">삭제</button>-->
						<!--						</td>-->
						<!--					</tr>-->
					</tbody>
				</table>
			</div>
			<div style="width: 92%" class="mt-4">
				<button class="ml-3 float-right py-2 px-3 rounded" @click="submitResponse">제출</button>
				<!--				<button class="float-right py-2 px-3 rounded" @click="addRow">줄 추가</button>-->
			</div>
		</div>
	</div>
</template>

<script>
import { loadResponse, saveResponse } from '@/api';

export default {
	name: 'ResponsePage',
	data() {
		return {
			responseId: '',
			email: '',
			teamName: '',
			position: '',
			userName: '',
			questions: [],
			examples: [],
			colNum: 0,
			response: [],
			title: '',
			loading: true
		};
	},
	mounted() {
		this.loadingResponse();
	},
	methods: {
		// addRow() {
		// 	if (this.colNum >= 9) {
		// 		alert('10개 이상 추가할 수 없습니다.');
		// 		return;
		// 	}
		// 	this.colNum++;
		// 	this.addResponse.push([]);
		// },
		// removeRow(index) {
		// 	this.colNum--;
		// 	this.addResponse.splice(index, 1);
		// },
		async loadingResponse() {
			this.loading = true;
			try {
				const response = await loadResponse(this.$route.params.sheetId, this.$route.params.responseId, this.$route.params.token);
				if (response.data.status != 200) {
					alert(response.data.message);
					this.$router.push('/notfound');
				} else {
					const data = response.data.data;
					// console.log(data);
					this.colNum = data.colNum - 1;
					this.title = data.title;
					this.examples = data.example.split('&&&&');
					this.questions = data.question.split('&&&&');

					const responseList = data.responseList;
					const firstLine = responseList[0];
					this.position = firstLine.position;
					this.teamName = firstLine.teamName;
					this.userName = firstLine.userName;
					this.email = firstLine.email;
					this.responseId = firstLine.id;
					if (firstLine.response != null) {
						this.response = firstLine.response.split('&&&&');
					} else {
						let arr = [];
						for (let j = 0; j < this.questions.length; j++) {
							arr.push('');
						}
						this.response = arr;
					}
					// //이후 줄
					// if (this.colNum > 1) {
					// 	for (let i = 1; i < responseList.length; i++) {
					// 		const line = responseList[i];
					// 		this.responseId.push(line.id);
					// 		if (line.response != null) {
					// 			this.addResponse.push(line.response.split('&&&&'));
					// 		}
					// 	}
					// }
				}
			} catch (e) {
				alert('서버에러');
			}
			this.loading = false;
		},
		async submitResponse() {
			for (let i = 0; i < this.questions.length; i++) {
				if (this.response[i] == '') {
					alert(i + 1 + '번째 항목이 빈칸입니다.');
					return;
				}
			}
			const responseString = this.response.join('&&&&');
			if (responseString.length > 30000) {
				alert('답변이 너무 길어요...');
				return;
			}
			const data = {
				email: this.email,
				firstResponseId: this.responseId,
				position: this.position,
				sheetId: this.$route.params.sheetId,
				teamName: this.teamName,
				token: this.$route.params.token,
				userName: this.userName,
				responseData: [
					{
						response: responseString,
						responseId: this.responseId
					}
				]
			};
			try {
				this.loading = true;
				const res = await saveResponse(data);
				if (res.data.status != 200) {
					alert(res.data.message);
				} else {
					alert('제출 완료 되었습니다. 창이 닫힙니다.');
					close();
				}
			} catch (e) {
				alert('서버 에러');
			}
			this.loading = false;
		}
	}
};
</script>

<style scoped>
thead {
	background-color: #c9ede4;
}

thead tr td {
	font-size: 18px;
}
tbody tr td {
	font-size: 18px;
}

td,
th {
	min-width: 100px;
	max-width: 600px;
	word-wrap: break-word;
}
</style>
