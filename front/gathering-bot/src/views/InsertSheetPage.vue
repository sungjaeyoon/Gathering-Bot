<template>
	<div class="row mt-5">
		<div class="col-8 left-content">
			<h1 class="mail-preview-text mb-4" style="color: gray">새로운 시트</h1>
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
						<button class="btn btn-outline-secondary" type="button" id="button-addon1">수신자 지정</button>
					</div>
					<input type="text" class="form-control" placeholder="" aria-label="Example text with button addon" aria-describedby="button-addon1" />
				</div>
			</div>
			<!--테이블-->

			<div style="overflow:auto; overflow-y:hidden" class="mt-5">
				<table class="table mt-2 ">
					<thead class="thead-dark">
						<tr>
							<th scope="col">성함</th>
							<th v-for="(tableHead, index) in tableHeads" v-bind:key="index" style="border-left: thick solid lightgray">
								<div scope="col">
									<span v-if="tableHead.edit">
										<input type="text" class="edit-text" v-model="tableHeads[index].content" />
									</span>
									<span v-else>
										<input type="text" :value="tableHead.content" disabled class="non-edit-text" />
									</span>
									<div class="btn-group" role="group">
										<button style="border: #2c3e50;height: 40px;" class="btn btn-info float-right my-2" v-on:click="editText(index)">E</button>
										<button style="border: #2c3e50;height: 40px;" class="btn btn-danger float-right my-2" v-on:click="removeColumn(index)">-</button>
									</div>
								</div>
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="col">이름</th>
							<th v-for="tableHead of tableHeads" v-bind:key="tableHead.key" style="border-left: thick solid lightgray">
								<div scope="col">#</div>
							</th>
						</tr>
					</tbody>
				</table>
				<button class="btn btn-success mt-1 float-right" v-on:click="addColumn">항목 추가</button>
			</div>
		</div>
		<div class="col-4 right-content">
			<h1 class="mail-preview-text mb-4" style="color: gray">메일 미리보기</h1>
			<div class="row">
				<div class="col-3">
					<input type="text" class="form-control" disabled value="받는 사람" style="text-align: center" />
				</div>
				<div class="col-9">
					<p type="text" class="form-control" disabled>{{ personList }}</p>
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
			<div class="row">
				<div type="text" class="form-control mail-preview-content" disabled style="white-space: pre;">
					<div class="finished-date-text">Gathering-bot에서 발송한 메일입니다.</div>
					<br />{{ sheetContent }}
					<div class="finished-date-text"><br />- 완료 기한 : {{ finishedDate.split('T').join(' : ') }}</div>
					<div class="row mt-5">
						<div style="overflow:auto; overflow-y:hidden">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th scope="col"></th>
										<th v-for="tableHead of tableHeads" v-bind:key="tableHead.key">
											<div scope="col">{{ tableHead.content }}</div>
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th scope="col">이름</th>
										<th v-for="tableHead of tableHeads" v-bind:key="tableHead.key">
											<div scope="col"><input type="text" placeholder="입력해주세요" disabled /></div>
										</th>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<button class="btn btn-outline-secondary float-right mt-4">제출</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'InsertSheetPage',
	data() {
		return {
			sheetTitle: '',
			finishedDate: '',
			sheetContent: '',
			personList: '',
			tableHeads: [
				{ content: '몇살인가요?', edit: false },
				{ content: '주말에 어디가시나요?', edit: false },
				{ content: '배고프나요?', edit: false },
			],
		};
	},
	methods: {
		addColumn() {
			this.tableHeads.push({ content: '새로운 항목', edit: false });
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
	},
};
</script>

<style scoped>
.row {
	padding-left: 2%;
	padding-right: 2%;
}
.left-content {
	border-right: thick solid lightgray;
}
.mail-preview-content {
	height: 500px;
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
</style>
