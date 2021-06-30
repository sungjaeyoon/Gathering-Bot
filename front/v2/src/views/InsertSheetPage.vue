<template>
	<div>
		<div v-if="loading" class="mt-5">
			<div class="spinner-border text-center" role="status" style="width: 150px;height: 150px">
				<span class="sr-only">Loading...</span>
			</div>
		</div>
		<div v-else>
			<h1 class="mt-5 mb-2" style="font-family: KTBold">시트 생성</h1>
			<div class=" sheet-content rounded mx-auto px-1 mt-4">
				<SaveSheetModal
					v-on:save="saveSheet"
					v-bind:content="content"
					v-bind:finished="finished"
					v-bind:title="title"
					v-bind:share-type="shareType"
					v-bind:table="table"
					v-bind:user-list="userList"
				></SaveSheetModal>
				<!--			<button class="mb-2 rounded m-0 py-2 px-4 float-right" @click="saveSheet">8. 저장</button>-->
				<!--			<button class="mb-2 mr-2 rounded m-0 py-2 px-4 float-right">불러오기</button>-->
				<!--row1-->
				<div class="row my-2 rounded border" style="background-color: white; clear: both">
					<div class="col-2 content-text border">
						<p>
							시트 제목
						</p>
					</div>
					<div class="col-5 content-input">
						<p class="w-100 border-0">
							<input v-model="title" class="pl-3 border-0" type="text" style="height: 45px; width: 100%" placeholder="메일 제목 입력" maxlength="50" />
						</p>
					</div>
					<div class="col-2 content-text">
						<p>
							완료 기한
						</p>
					</div>
					<div class="col-3 m-0 p-0">
						<p class="w-100 border-0 m-0 p-0">
							<datetime class="pl-2" v-model="finished" type="datetime" input-style="border:0;height:40px;width:100%" value-zone="Asia/Seoul"></datetime>
						</p>
					</div>
				</div>
				<!--row2-->
				<div class="row my-2 rounded border" style="background-color: white">
					<div class="col-2 content-text border">
						<p>
							시트 공유
						</p>
					</div>
					<div class="col-10">
						<table class="w-100">
							<tbody>
								<tr class="text-left">
									<td class="pl-3">
										<input type="radio" id="private" name="shareType" value="private" checked v-model="shareType" />
										<label class="mt-2 ml-2" for="private">취합자만 열람가능</label>
									</td>
									<td>
										<input type="radio" id="public" name="shareType" value="public" v-model="shareType" />
										<label class="mt-2 ml-2" for="public">수신자 모두 열람가능</label>
									</td>
									<td>
										<input type="radio" id="link" name="shareType" value="link" v-model="shareType" />
										<label class="mt-2 ml-2" for="link">링크가 있는 모든 사용자 열람 가능</label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row my-2 rounded border" style="background-color: white">
					<div class="col-2 content-text">
						<p>
							받는사람
						</p>
					</div>
					<div class="col-10">
						<ContactModal v-bind:added-user-list="userList" @addUser="addUser" @removeUser="removeUser" @addAllUser="addAllUser" @removeAllUser="removeAllUser"></ContactModal>
					</div>
				</div>
				<!--sheet 내용-->
				<div class="row">
					<div class="col-12 content-text border" style="height: 33px">
						<p>
							시트 내용
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-12 p-0" style="background-color: white">
						<wysiwyg v-model="content" />
					</div>
				</div>
			</div>
			<div class="mt-4" style="width: 92%">
				<button class="float-right rounded m-0 py-2 px-4  rounded border" @click="addColumn">항목 추가</button>
			</div>
			<div class="mt-2 mx-auto Flipped scrollbar-info" style="overflow:auto; overflow-y:auto;width: 85%;clear: both">
				<table class="table Content" style="background-color: white">
					<thead>
						<tr>
							<td colspan="2"></td>
							<td style="min-width: 150px;">취합 항목</td>
							<td v-for="(col, index) in table" :key="index" style="max-width: 500px; ;min-width: 300px;">
								<div class="input-group">
									<input type="text" v-model="col.content" class="form-control m-0" placeholder="항목 입력" style="border-right: none" maxlength="100" />
									<div class="input-group-prepend">
										<span class="input-group-text button-danger rounded-right" id="basic-addon1" @click="removeColumn(index)">삭제</span>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2"></td>
							<td>예시 답변</td>
							<th v-for="(col, index) in table" :key="index">
								<input v-model="col.example" class="w-100 pl-3 border rounded-left" type="text" style="height: 37px" placeholder="예시 입력" maxlength="100" />
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td width="10%" style="min-width: 150px;">팀</td>
							<td width="7%" style="min-width: 50px;">직책</td>
							<td width="10%">이름</td>
							<td v-for="(col, index) in table" :key="index">-</td>
						</tr>
						<tr v-for="(user, index) in userList" :key="index">
							<td>{{ user.teamName }}</td>
							<td>{{ user.position }}</td>
							<td>{{ user.userName }}</td>
							<td v-for="(col, index) in table" :key="index">-</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>

<script>
import { Datetime } from 'vue-datetime';
import 'vue-datetime/dist/vue-datetime.css';
import SaveSheetModal from '@/components/modal/SaveSheetModal.vue';
import ContactModal from '@/components/modal/ContactModal.vue';
import { insertSheet } from '@/api';
export default {
	name: 'InsertSheetPage',
	components: {
		datetime: Datetime,
		SaveSheetModal,
		ContactModal
	},
	data() {
		return {
			title: '',
			finished: '',
			shareType: 'private',
			content: '<div style="text-align: left;">입력해주세요</div>',
			table: [
				{ content: '', example: '' },
				{ content: '', example: '' },
				{ content: '', example: '' }
			],
			userList: [],
			loading: false
		};
	},
	watch: {
		finished: function() {
			this.finished = this.finished.split('.')[0];
		}
	},
	methods: {
		addColumn() {
			if (this.table.length >= 15) {
				alert('항목은 15개가 최대입니다.');
				return;
			}
			this.table.push({ content: '', example: '' });
		},
		removeColumn(index) {
			this.table.splice(index, 1);
		},
		async saveSheet() {
			const listContent = [];
			const listExample = [];
			for (let i = 0; i < this.table.length; i++) {
				listContent.push(this.table[i].content);
				listExample.push(this.table[i].example);
			}

			const questions = listContent.join('&&&&');
			const examples = listExample.join('&&&&');

			const sheet = {
				colNum: this.table.length,
				content: this.content,
				createdMemberId: this.$store.state.id,
				example: examples,
				finishedDate: this.finished,
				question: questions,
				shareType: this.shareType,
				title: this.title,
				userList: this.userList
			};

			try {
				this.loading = true;
				const response = await insertSheet(sheet);
				this.loading = false;
				alert('생성되었습니다. 시트에서 시작을 눌러주세요.');
				this.$router.push('/list');
			} catch (e) {
				alert(e);
			}
		},
		addUser(user) {
			if (!this.userList.includes(user)) {
				this.userList.push(user);
			}
		},
		removeUser(index) {
			this.userList.splice(index, 1);
		},
		addAllUser(userList) {
			for (let i = 0; i < userList.length; i++) {
				this.addUser(userList[i]);
			}
		},
		removeAllUser() {
			this.userList = [];
		}
	}
};
</script>

<style scoped>
.sheet-content {
	width: 84%;
}
.content-text {
	background-color: #c9ede4;
	font-weight: bold;
	font-size: 20px;
	height: 45px;
	position: relative;
}

.content-text p {
	margin: 0;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	min-width: 100px;
	text-overflow: ellipsis;
}

.content-input {
	height: 45px;
	position: relative;
}

.content-input p {
	margin: 0;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	border-color: white;
}

thead {
	background-color: #c9ede4;
}

thead tr td {
	font-weight: bold;
	font-size: 20px;
}

.button-danger {
	color: white;
	background-color: #d25b49;
}

.Flipped,
.Flipped .Content {
	transform: rotateX(180deg);
	-ms-transform: rotateX(180deg);
	-webkit-transform: rotateX(180deg);
}
@import '~vue-wysiwyg/dist/vueWysiwyg.css';
</style>
