<template>
	<div>
		<h1 class="mt-5" style="font-family: KTBold">시트 목록</h1>
		<div v-if="loading">
			<div class="spinner-border text-center" role="status" style="width: 150px;height: 150px">
				<span class="sr-only">Loading...</span>
			</div>
		</div>
		<div v-else>
			<div class="mx-auto mt-3 text-left font-weight-bold" style="width: 80%;">
				<!--필터링-->
				<span>
					<mdb-dropdown style="background-color: white">
						<mdb-dropdown-toggle slot="toggle" class="px-4 py-2 z-depth-0 border">{{ type }}</mdb-dropdown-toggle>
						<mdb-dropdown-menu>
							<mdb-dropdown-item @click="changeFilter('전체보기', '')">전체보기</mdb-dropdown-item>
							<mdb-dropdown-item @click="changeFilter('대기중', 'WAIT')">대기중</mdb-dropdown-item>
							<mdb-dropdown-item @click="changeFilter('진행중', 'PROCEEDING')">진행중</mdb-dropdown-item>
							<mdb-dropdown-item @click="changeFilter('종료', 'FINISHED')">종료</mdb-dropdown-item>
						</mdb-dropdown-menu>
					</mdb-dropdown>
				</span>
				<!--검색-->
				<span class="float-right">
					<mdbIcon icon="search" />
					<input type="text" class="py-1 rounded border" v-model="searchWord" />
					<button class="ml-1 py-1 px-2 rounded border" @click="search">검색</button>
				</span>
			</div>
			<!--table-->
			<template v-if="sheetList.length == 0">
				<h3 class="mt-5">시트가 없습니다.</h3>
				<router-link to="/insert"><h4 class="mt-5">시트 추가</h4></router-link>
			</template>
			<template v-else>
				<div class="mx-auto mt-3 z-depth-1" style="background-color: white;width: 80%;overflow: auto">
					<table class="table table-hover border">
						<thead>
							<tr style="background-color: #C9EDE4">
								<th scope="col" width="10%">번호</th>
								<th scope="col" width="30%">제목</th>
								<th scope="col" width="20%">생성일</th>
								<th scope="col" width="20%">공유</th>
								<th scope="col" width="20%">상태</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(sheet, index) in sheetList" :key="index" @click="showDetail(index)">
								<td>{{ index + 1 + (currentPage - 1) * 10 }}</td>
								<td class="text-left">{{ sheet.title }}</td>
								<td>{{ sheet.createdDate }}</td>
								<td>
									<span v-if="sheet.shareType == 'PRIVATE'">비공개</span>
									<span v-if="sheet.shareType == 'PUBLIC'">인원공개</span>
									<span v-if="sheet.shareType == 'LINK'">링크공개</span>
								</td>
								<td>
									<span v-if="sheet.sheetStatus == 'WAIT'">대기중</span>
									<span v-if="sheet.sheetStatus == 'PROCEEDING'">진행중</span>
									<span v-if="sheet.sheetStatus == 'FINISHED'">종료</span>
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
import 'bootstrap';
import Pagination from 'vue-pagination-2';
import { mdbIcon, mdbDropdown, mdbDropdownItem, mdbDropdownMenu, mdbDropdownToggle } from 'mdbvue';
import { getSheetList } from '@/api';
export default {
	name: 'SheetListPage',
	data() {
		return {
			loading: true,
			type: '전체보기',
			typeValue: '',
			searchWord: '',
			totalPages: 0,
			currentPage: 1,
			totalElements: 1,
			sheetList: []
		};
	},
	components: {
		Pagination,
		mdbIcon,
		mdbDropdown,
		mdbDropdownItem,
		mdbDropdownMenu,
		mdbDropdownToggle
	},
	computed: {
		parsingInt: function() {
			return Number.parseInt(this.totalElements);
		}
	},
	mounted() {
		this.callSheetListApi();
	},
	methods: {
		changeFilter(type, typeValue) {
			this.type = type;
			this.typeValue = typeValue;
			this.callSheetListApi();
		},
		async callSheetListApi() {
			this.loading = true;
			const params = {
				index: this.currentPage - 1,
				display: 10,
				word: this.searchWord,
				type: this.typeValue
			};
			try {
				const response = await getSheetList(this.$store.state.id, params);
				// console.log(response);
				this.totalElements = response.data.data.totalElements;
				this.totalPages = response.data.data.totalPages;
				this.sheetList = response.data.data.sheetList;
			} catch (e) {
				alert(e);
			}
			this.loading = false;
		},
		changePage(page) {
			this.currentPage = page;
			this.callSheetListApi();
		},
		search() {
			this.changePage(1);
		},
		showDetail(index) {
			const sheet = this.sheetList[index];
			this.$router.push('/sheets/' + sheet.id + '/' + sheet.token);
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
button:hover {
	background-color: #bbbbbb;
}
</style>
