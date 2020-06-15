<template>
	<div class="row mt-5">
		<!--시트 작성-->
		<div class="left-content" v-bind:class="[{ 'col-8': !previewFlag }, { 'col-12': previewFlag }]">
			<h1 class="mail-preview-text mb-4">새로운 시트</h1>
			<!--오른쪽 버튼-->
			<span v-if="previewFlag">
				<button v-on:click="changePreview" class="float-right my-3 btn btn-lg btn-warning">Open Preview</button>
			</span>
			<span v-else>
				<button v-on:click="changePreview" class="float-right my-3 btn btn-lg btn-warning">Close Preview</button>
			</span>
			<span>
				<button v-on:click="saveSheet" class="float-right my-3 mx-3 btn btn-lg save-button">저장하기</button>
			</span>
			<!--오른쪽 버튼-->
			<!--폼 내용-->
			<div class="form mx-5">
				<div class="input-group input-group-lg">
					<div class="input-group-prepend">
						<span class="input-group-text" style="font-size: 20px">제목</span>
					</div>
					<input type="text" class="form-control" aria-label="Sizing example input" v-model="sheetTitle" />
					<div class="input-group-prepend">
						<span class="input-group-text ml-4" style="font-size: 20px">완료 기한</span>
					</div>
					<input type="datetime-local" class="form-control" aria-label="Sizing example input" v-model="finishedDate" />
				</div>
				<div class="input-group mt-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="font-size: 20px">시트 내용</span>
					</div>
					<textarea class="form-control" aria-label="With textarea" v-model="sheetContent" placeholder="메일에 표시될 내용 작성"></textarea>
				</div>
				<div class="input-group mt-3">
					<div class="input-group-prepend">
						<button class="btn btn-outline-secondary" type="button" v-on:click="getUsers" id="button-addon1" data-toggle="modal" data-target="#personListModal">수신자 지정</button>
					</div>
					<input type="text" disabled class="form-control" placeholder="" aria-label="Example text with button addon" aria-describedby="button-addon1" v-bind:value="userNameList()" />
				</div>
			</div>
			<!--폼 내용-->

			<!--Modal-->
			<div class="modal fade" id="personListModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">수신자 지정</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true"></span>
							</button>
						</div>
						<div class="modal-body">
							<!--search bar-->
							<div class="md-form mt-0">
								<input class="form-control" type="text" placeholder="Search" aria-label="Search" v-model="searchWord" on />
							</div>
							<div class="my-2">
								<input
									type="text"
									disabled
									class="form-control"
									placeholder="수신자 목록"
									aria-label="Example text with button addon"
									aria-describedby="button-addon1"
									v-bind:value="userNameList()"
								/>
							</div>
							<table class="table table-striped">
								<thead>
									<tr>
										<th scope="col">Check</th>
										<th scope="col">이름</th>
										<th scope="col">직책</th>
										<th scope="col">이메일</th>
										<th scope="col">팀 이름</th>
									</tr>
								</thead>
								<tbody v-if="searchWord == ''">
									<tr v-for="(user, index) in userList" :key="index">
										<td>
											<input type="checkbox" v-bind:value="user" v-model="selectedPersonList" />
										</td>
										<td>
											<label>{{ user.name }}</label>
										</td>
										<td>
											<label>{{ user.position }}</label>
										</td>
										<td>
											<label>{{ user.email }}</label>
										</td>
										<td>
											<label>{{ user.teamName }}</label>
										</td>
									</tr>
								</tbody>
								<tbody v-else>
									<tr v-for="(user, index) in searchedPersonList" :key="index">
										<td>
											<input type="checkbox" v-bind:value="user" v-model="selectedPersonList" />
										</td>
										<td>
											<label>{{ user.name }}</label>
										</td>
										<td>
											<label>{{ user.position }}</label>
										</td>
										<td>
											<label>{{ user.email }}</label>
										</td>
										<td>
											<label>{{ user.teamName }}</label>
										</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
						</div>
					</div>
				</div>
			</div>
			<!--Modal-->

			<!--테이블-->
			<div style="overflow:auto; overflow-y:hidden" class="mt-5">
				<table class="table mt-2 ">
					<thead class="thead-dark">
						<tr>
							<th scope="col"></th>
							<th v-for="(tableHead, index) in tableHeads" v-bind:key="index" style="border-left: thick solid lightgray;max-width: 500px;white-space: normal">
								<div scope="col">
									<span v-if="tableHead.edit">
										<input type="text" class="edit-text" v-model="tableHeads[index].content" />
									</span>
									<span v-else class="non-edit-text">
										{{ tableHeads[index].content }}
									</span>
									<!--수정, 삭제 버튼-->
									<div class="ml-3 btn-group form-btn-group" role="group">
										<button class="btn btn-info float-right my-2" v-on:click="editText(index)">E</button>
										<button class="btn btn-danger float-right my-2" v-on:click="removeColumn(index)">-</button>
									</div>
								</div>
							</th>
						</tr>
						<tr>
							<th scope="col">예시</th>
							<th v-for="(tableHead, index) in tableHeads" v-bind:key="index" style="border-left: thick solid lightgray;max-width: 500px;white-space: normal">
								<div scope="col">
									<span v-if="tableHead.edit">
										<input type="text" class="edit-text" v-model="tableHeads[index].example" />
									</span>
									<span v-else class="non-edit-text">
										{{ tableHeads[index].example }}
									</span>
								</div>
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="col">이름</th>
							<th v-for="tableHead of tableHeads" v-bind:key="tableHead.key" style="border-left: thick solid lightgray">
								<div scope="col">·</div>
							</th>
						</tr>
					</tbody>
				</table>
				<button class="btn btn-success mt-1 float-right" v-on:click="addColumn">항목 추가</button>
			</div>
			<!--테이블-->
		</div>
		<!--시트 작성-->

		<!--메일 미리보기-->
		<div class="col-4 right-content" v-bind:class="{ hide: previewFlag }">
			<h1 class="mail-preview-text mb-4">메일 미리보기</h1>
			<!--메일 상단-->
			<div class="row mb-2">
				<div class="col-3 ">
					<input type="text" class="form-control" disabled value="받는 사람" style="text-align: center" />
				</div>
				<div class="col-9">
					<input
						type="text"
						style="background-color: white"
						disabled
						class="form-control"
						placeholder=""
						aria-label="Example text with button addon"
						aria-describedby="button-addon1"
						v-bind:value="userNameList()"
					/>
				</div>
			</div>
			<div class="row">
				<div class="col-3">
					<input type="text" class="form-control" disabled value="제  목" style="text-align: center" />
				</div>
				<div class="col-9">
					<p type="text" class="form-control" disabled>[Gathering-bot] {{ sheetTitle }}</p>
				</div>
			</div>
			<!--메일 상단-->
			<!--메일 하단-->
			<div class="row">
				<div type="text" class="form-control mail-preview-content" disabled style="white-space: pre;">
					<div class="finished-date-text">Gathering-bot에서 발송한 메일입니다.</div>
					<br />{{ sheetContent }}
					<div class="finished-date-text"><br />- 완료 기한 : {{ finishedDate.split('T').join(' : ') }}</div>
					<div class="row mt-5">
						<div style="overflow:auto; overflow-y:hidden">
							<!--테이블-->
							<table class="table table-bordered">
								<thead>
									<tr>
										<th scope="col"></th>
										<th v-for="tableHead of tableHeads" v-bind:key="tableHead.key" style="max-width: 500px;white-space: normal">
											<div scope="col">{{ tableHead.content }}</div>
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th scope="col">예시</th>
										<th v-for="(tableHead, index) in tableHeads" v-bind:key="index">
											{{ tableHeads[index].example }}
										</th>
									</tr>
									<tr>
										<th scope="col">이름</th>
										<th v-for="tableHead of tableHeads" v-bind:key="tableHead.key">
											<div scope="col"><input type="text" placeholder="입력해주세요" disabled /></div>
										</th>
									</tr>
								</tbody>
							</table>
							<!--테이블-->
						</div>
					</div>
					<button class="btn btn-outline-secondary float-right mt-4">제출</button>
				</div>
			</div>
			<!--메일 하단-->
		</div>
	</div>
</template>

<script>
import { getUserList, insertSheet } from '@/api/index';

export default {
	name: 'InsertSheetPage',
	data() {
		return {
			sheetTitle: '',
			finishedDate: '',
			sheetContent: '',
			userList: [], // 전체 유저 목록
			selectedPersonList: [], // 수신자로 지정된 목록
			searchWord: '',
			searchedPersonList: [], // 검색할 사람 목록
			previewFlag: false,
			tableHeads: [
				{ content: '분당에 방문한 적이 있나요?', edit: false, example: '네/아니오' },
				{ content: '주말에 어디가시나요?', edit: false, example: '네/아니오' },
				{ content: '배고프나요?', edit: false, example: '네/아니오' }
			]
		};
	},
	watch: {
		searchWord: function() {
			this.searchedPersonList = this.userList.filter(
				obj => obj.name.indexOf(this.searchWord) != -1 || obj.teamName.indexOf(this.searchWord) != -1 || obj.email.indexOf(this.searchWord) != -1 || obj.position.indexOf(this.searchWord) != -1
			);
		}
	},
	methods: {
		addColumn() {
			this.tableHeads.push({ content: '새로운 항목', edit: false, example: '새로운 예시' });
		},
		editText(index) {
			if (this.tableHeads[index].edit) {
				this.tableHeads[index].edit = false;
			} else {
				this.tableHeads[index].edit = true;
			}
		},
		removeColumn(index) {
			this.tableHeads.splice(index, 1);
		},
		changePreview() {
			this.previewFlag = !this.previewFlag;
		},
		// 전체 유저 가져오기
		async getUsers() {
			const response = await getUserList();
			if (response.data.status == 200) {
				this.userList = response.data.users;
			} else {
				alert(response.data.message);
			}
		},
		// 유저 이름(팀이름) 으로 반환
		userNameList() {
			const list = [];
			for (let i = 0; i < this.selectedPersonList.length; i++) {
				list.push(this.selectedPersonList[i].name + '(' + this.selectedPersonList[i].teamName + ') ');
			}
			return list;
		},
		validateSheet() {
			if (this.sheetTitle == '') {
				alert('제목은 필수입니다.');
				return false;
			}
			if (this.finishedDate == '') {
				alert('완료기한은 필수입니다.');
				return false;
			}
			if (this.sheetContent == '') {
				alert('내용은 필수입니다.');
				return false;
			}
			if (this.selectedPersonList.length == 0) {
				alert('수신자는 필수입니다.');
				return false;
			}
			if (this.tableHeads.length == 0) {
				alert('최소 하나의 응답항목을 작성해주세요.');
				return false;
			}
			return true;
		},
		//서버에 전송
		async saveSheet() {
			if (!this.validateSheet()) {
				return;
			}

			const listContent = [];
			const listExample = [];
			for (let i = 0; i < this.tableHeads.length; i++) {
				listContent.push(this.tableHeads[i].content);
				listExample.push(this.tableHeads[i].example);
			}

			const question = listContent.join('&&&&');
			const example = listExample.join('&&&&');

			const colNum = this.tableHeads.length;
			const data = {
				createdMemberId: this.$store.state.id,
				title: this.sheetTitle,
				content: this.sheetContent,
				question: question,
				colNum: colNum,
				finishedDate: this.finishedDate,
				memberList: this.selectedPersonList,
				example: example
			};

			const response = await insertSheet(data);

			if (response.data.status != 200) {
				alert(response.data.message);
				return;
			}
			alert('생성 되었습니다.');
			this.$router.push('/sheets');
		}
	}
};
</script>

<style scoped>
.row {
	padding-left: 2%;
	padding-right: 2%;
}

.save-button {
	background-color: #00c4cc;
	color: white;
}
.left-content {
	border-right: thick solid lightgray;
}

.mail-preview-text {
	color: black;
}

.mail-preview-content {
	height: 400px;
}
.finished-date-text {
	font-weight: bold;
}
.table {
	text-align: center;
	white-space: nowrap;
}
.non-edit-text {
	height: 40px;
	text-align: center;
	background-color: #343a40;
	border: none;
	color: white;
}
.edit-text {
	height: 40px;
	text-align: center;
	background-color: white;
	color: black;
}
.hide {
	display: none;
}
</style>
