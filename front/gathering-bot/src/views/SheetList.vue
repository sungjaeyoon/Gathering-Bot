<template>
	<div class="container">
		<template v-if="sheetList.length == 0">
			<div class="mx-auto mt-5 pt-5 pb-5" style="width: 700px;text-align: center">
				<h1 style="font-size: 50px">생성된 시트가 없어요!</h1>
				<router-link to="/sheet/new"><h2 class="mt-5">시트 추가하러 가기</h2></router-link>
			</div>
		</template>
		<template v-else>
			<table class="table" style="text-align: center">
				<thead>
					<tr style="font-size: 25px;background-color: #7d2ae8;color: white">
						<th scope="col">제목</th>
						<th scope="col">내용</th>
						<th scope="col">생성일</th>
						<th scope="col">상태</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(sheet, index) in sheetList" :key="index" v-on:click="showList(sheet.id)" class="table-line">
						<th scope="row" class="table-text">{{ sheet.title }}</th>
						<td class="table-text">{{ sheet.content }}</td>
						<td>{{ sheet.createdDate.split('T').join('-') }}</td>
						<td>
							<div v-if="sheet.sheetStatus == 'WAIT'">
								<span class="spinner-grow text-secondary mr-3 pt-2" role="status">
									<span class="sr-only">Loading...</span>
								</span>
								<span>대기중</span>
							</div>
							<div v-if="sheet.sheetStatus == 'PROCEEDING'">
								<span class="spinner-border text-success mr-3 pt-2" role="status">
									<span class="sr-only">Loading...</span>
								</span>
								<span>진행중</span>
							</div>
							<span v-if="sheet.sheetStatus == ' FINISHED'"> 종료</span>
						</td>
					</tr>
				</tbody>
			</table>
		</template>
	</div>
</template>

<script>
import { getSheet } from '@/api';

export default {
	name: 'SheetList',
	data() {
		return {
			sheetList: [],
			// id: '',
			// title: '',
			// content: '',
			// question: '',
			// createdDate: '',
			// finishedDate: '',
			// sheetStatus: '',
		};
	},
	mounted() {
		this.getSheetList();
	},
	methods: {
		//내 시트리스트 가져오기
		async getSheetList() {
			const response = await getSheet(this.$store.state.id);

			this.sheetList = response.data;
			console.log(response.data);
		},
		showList(id) {
			this.$router.push('/sheet/' + id);
		},
	},
};
</script>

<style scoped>
.table-text {
	text-align: left;
	max-width: 100px;
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
}

tr {
	line-height: 45px;
	min-height: 25px;
	height: 25px;
	border-radius: 1em;
	font-weight: bold;
}

.table-line {
	border-radius: 2em;
	cursor: pointer;
}
.table-line:hover {
	background-color: #00c4cc;
	color: white;
}
</style>
