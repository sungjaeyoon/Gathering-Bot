<template>
	<div class="container">
		<!--로딩-->
		<div v-if="loading">
			<h1>loading...</h1>
		</div>
		<!--찾을수 없는 페이지-->
		<div v-if="!pageNotFound && !loading">
			<h1>Page Not Found</h1>
		</div>
		<div v-else style="overflow:auto; overflow-y:hidden" class=" shadow p-3 mb-5 bg-white rounded">
			<!--테이블-->
			<table class="table table-bordered text-center">
				<tbody>
					<tr class="table-active">
						<th>이름</th>
						<th>직책</th>
						<th>팀</th>
						<th v-for="(question, index) in questions" v-bind:key="index">
							{{ question }}
						</th>
					</tr>
					<tr>
						<th>{{ name }}</th>
						<th>{{ position }}</th>
						<th>{{ teamName }}</th>

						<th v-for="(question, index) of questions" :key="index">
							<div scope="col"><input type="text" placeholder="입력해주세요" v-model="inputQuestions[index]" /></div>
						</th>
					</tr>
					<tr>
						<th scope="col"></th>
						<th scope="col"></th>
						<th scope="col">예시</th>
						<th v-for="(example, index) in examples" v-bind:key="index">
							{{ example }}
						</th>
					</tr>
				</tbody>
			</table>
			<button class="btn btn-dark float-right" @click="submitInput">제출</button>
			<!--테이블-->
		</div>
	</div>
</template>

<script>
import { getSheetResponse, updateResponse } from '@/api';

export default {
	name: 'ResponseForm',
	data() {
		return {
			pageNotFound: false,
			loading: true,
			memberId: '',
			name: '',
			position: '',
			teamName: '',
			questions: [],
			examples: [],
			inputQuestions: []
		};
	},
	mounted() {
		this.loadSheet();
	},
	methods: {
		async loadSheet() {
			const response = await getSheetResponse(this.$route.params.sheetId, this.$route.params.userId, this.$route.params.token);
			if (response.data.status == 200) {
				const data = response.data;
				this.name = data.name;
				this.memberId = data.memberId;
				this.position = data.position;
				this.teamName = data.teamName;
				this.questions = data.question.split('&&&&');
				for (let i = 0; i < this.questions.length; i++) {
					this.inputQuestions.push('');
				}
				this.examples = data.example.split('&&&&');
				this.pageNotFound = true;
			} else {
				alert(response.data.message);
			}
			this.loading = false;
		},
		async submitInput() {
			const data = {
				memberId: this.memberId,
				sheetId: this.$route.params.sheetId,
				response: this.inputQuestions.join('&&&&'),
				randomToken: this.$route.params.token
			};
			const response = await updateResponse(data);
			alert('제출 완료 창이 닫힙니다.');
			close();
		}
	}
};
</script>

<style scoped></style>
