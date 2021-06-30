<template>
	<div>
		<h1 class="mt-5" style="font-family: KTBold">나의 답변</h1>
		<div v-if="loading">
			<div class="spinner-border text-center" role="status" style="width: 150px;height: 150px">
				<span class="sr-only">Loading...</span>
			</div>
		</div>
		<div v-else>
			<template v-if="responseList.length == 0">
				<h3 class="mt-5">항목이 없습니다.</h3>
			</template>
			<template v-else>
				<div class="mx-auto mt-5 z-depth-1" style="background-color: white;width: 80%;overflow: auto">
					<table class="table table-hover border">
						<thead>
							<tr style="background-color: #C9EDE4">
								<th scope="col" width="10%">번호</th>
								<th scope="col" width="30%">시트 제목</th>
								<th scope="col" width="10%">시트 상태</th>
								<th scope="col" width="10%">공유</th>
								<th scope="col" width="10%">답변상태</th>
								<th scope="col" width="10%">답변하기</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(response, index) in responseList" :key="index">
								<td>{{ index + 1 + (currentPage - 1) * 10 }}</td>
								<td class="text-left">{{ response.title }}</td>
								<td>
									<span v-if="response.sheetStatus == 'PROCEEDING'">진행중</span>
									<span v-if="response.sheetStatus == 'FINISHED'">종료</span>
								</td>
								<td>
									<span v-if="response.shareType == 'PRIVATE'">비공개</span>
									<span v-else><button class="rounded" @click="showSheet(response.sheetId, response.sheetToken)">시트보기</button></span>
								</td>
								<td>
									<span v-if="response.requestStatus == 'YES'"><span class="dot dot-yes"></span></span>
									<span v-else><span class="dot dot-no"></span></span>
								</td>
								<td>
									<span v-if="response.sheetStatus == 'PROCEEDING'">
										<button class="rounded" @click="showResponse(response.sheetId, response.responseId, response.responseToken)">답변</button>
									</span>
									<span v-else>종료</span>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="">
						<pagination v-model="currentPage" :records="parsingInt" :per-page="10" @paginate="changePage"></pagination>
					</div>
				</div>
			</template>
		</div>
	</div>
</template>

<script>
import Pagination from 'vue-pagination-2';
import { getResponseList } from '@/api';

export default {
	name: 'ResponseListPage',
	components: {
		Pagination
	},
	data() {
		return {
			loading: true,
			responseList: [],
			totalPages: 0,
			totalElements: 1,
			currentPage: 1
		};
	},
	computed: {
		parsingInt: function() {
			return Number.parseInt(this.totalElements);
		}
	},
	mounted() {
		this.loadResponseList();
	},
	methods: {
		async loadResponseList() {
			this.loading = true;
			try {
				const response = await getResponseList(this.currentPage - 1, this.$store.state.email);
				if (response.data.status != 200) {
					alert(response.data.message);
				}
				const data = response.data.data;
				this.responseList = data.responseList;
				this.totalPages = data.totalPages;
				this.totalElements = data.totalElements;
			} catch (e) {
				alert('서버에러');
			}
			this.loading = false;
		},
		changePage(page) {
			this.currentPage = page;
			this.loadResponseList();
		},
		showResponse(sheetId, responseId, token) {
			this.$router.push('/response/' + sheetId + '/' + responseId + '/' + token);
		},
		showSheet(sheetId, token) {
			this.$router.push('/sheets/' + sheetId + '/' + token);
		}
	}
};
</script>

<style scoped>
th {
	font-weight: bold;
	font-size: 18px;
}
td {
	text-overflow: ellipsis;
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
</style>
