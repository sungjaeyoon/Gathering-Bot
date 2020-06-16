<template>
	<div class="container">
		<div class="text-center">
			<span v-if="loading" class="spinner-border text-center" role="status" style="width: 200px;height: 200px">
				<span class="sr-only">Loading...</span>
			</span>
			<div v-if="loading" class="mt-5"><h1>로딩중...</h1></div>
		</div>
		<template v-if="sheetList.length == 0 && !loading">
			<div class="mx-auto mt-5 pt-5 pb-5" style="width: 700px;text-align: center">
				<h1 style="font-size: 50px">생성된 시트가 없어요!</h1>
				<router-link to="/sheets/new"><h2 class="mt-5">시트 추가하러 가기</h2></router-link>
			</div>
		</template>
		<template v-else>
			<div class="dropdown show">
				<a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					시트 상태 : {{ sheetFilter }}
				</a>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
					<a class="dropdown-item" @click="filterSheet('전체보기', '')">전체보기</a>
					<a class="dropdown-item" @click="filterSheet('대기중', 'WAIT')">대기중</a>
					<a class="dropdown-item" @click="filterSheet('진행중', 'PROCEEDING')">진행중</a>
					<a class="dropdown-item" @click="filterSheet('종료', 'FINISHED')">종료</a>
				</div>
			</div>
			<table class="table" style="text-align: center">
				<thead>
					<tr style="font-size: 25px;background-color: #bbbbbb;color: white">
						<th scope="col" style="width:300px">제목</th>
						<th scope="col">내용</th>
						<th scope="col">생성일</th>
						<th scope="col">상태</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(sheet, index) in sheetList" :key="index" v-on:click="showList(sheet.id)" class="table-line">
						<td scope="row" class="table-text" style="color: cornflowerblue;font-size: 25px;text-align: left">
							{{ sheet.title }}
						</td>
						<td class="table-text">{{ sheet.content }}</td>
						<td>{{ sheet.createdDate.split('T').join(' / ') }}</td>
						<td>
							<div v-if="sheet.sheetStatus == 'WAIT'">
								<span class="spinner-grow spinner-grow-sm text-secondary mr-3 pt-2" role="status">
									<span class="sr-only">Loading...</span>
								</span>
								<span>대기중</span>
							</div>
							<div v-if="sheet.sheetStatus == 'PROCEEDING'">
								<span class="spinner-border spinner-border-sm text-success mr-3 pt-2" role="status">
									<span class="sr-only">Loading...</span>
								</span>
								<span>진행중</span>
							</div>
							<div v-if="sheet.sheetStatus == 'FINISHED'">
								<span>종 료</span>
							</div>
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
			loading: true,
			sheetList: [],
			sheetFilter: '전체보기',
			type: ''
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
		const filter = {
			offset: 0,
			limit: 10,
			type: this.type
		};
		this.getSheetList(filter);
	},
	methods: {
		//내 시트리스트 가져오기
		async getSheetList(filter) {
			const response = await getSheet(this.$store.state.id, filter);
			if (response.data.status != 200) {
				alert(response.data.message);
				return;
			}
			this.sheetList = response.data.sheets;
			this.loading = false;
		},
		showList(id) {
			this.$router.push('/sheets/' + id);
		},
		async filterSheet(sheetFilter, type) {
			this.sheetFilter = sheetFilter;
			this.type = type;
			const filter = {
				offset: 0,
				limit: 10,
				type: this.type
			};
			this.getSheetList(filter);
		}
	}
};
</script>

<style scoped>
.table-text {
	text-align: center;
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
