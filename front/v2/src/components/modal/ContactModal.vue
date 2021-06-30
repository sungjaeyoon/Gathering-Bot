<template>
	<div>
		<button @click="modal = true" style="height: 45px; width: 100%;background-color: white;color: black">Click!</button>
		<!--		<mdb-btn class="m-0 p-0" @click.native="modal = true" style="height: 45px; width: 100%;background-color: white">Small modal</mdb-btn>-->
		<mdb-modal size="xl" :show="modal" @close="modal = false">
			<mdb-modal-header style="background-color: #c9ede4">
				<mdb-modal-title>수신자 추가</mdb-modal-title>
			</mdb-modal-header>
			<mdb-modal-body>
				<div class="row mx-4">
					<div class="col-4 border" style="height: 500px;overflow:auto;">
						<div>
							<Teatree :roots="teamList">
								<template slot="node-toggle" slot-scope="{ node }">
									<NodeToggle :node="node" />
								</template>
								<template slot="node-name" slot-scope="{ node }">
									<NodeName
										:node="node"
										:handleNodeLeftClick="
											event => {
												showMember(event);
											}
										"
										:handleNodeRightClick="
											event => {
												showMember(event);
											}
										"
									/>
								</template>
							</Teatree>
						</div>
					</div>
					<div class="col-1 text-center content-parent">
						<p>
							>>
						</p>
					</div>
					<div class="col-7 border p-0" style="overflow:auto; height: 500px">
						<table class="table table-bordered w-100">
							<thead>
								<tr>
									<td width="20%">
										<button class="m-0 px-2" @click="addAllUser">전체 추가</button>
									</td>
									<td width="20%">이름</td>
									<td width="20%">직책</td>
									<td width="40%">이메일</td>
								</tr>
							</thead>
							<tbody>
								<tr v-if="loading" class="text-center">
									<td colspan="4">
										<div class="mt-5 text-center">
											<div class="spinner-border text-center" role="status" style="width: 150px;height: 150px">
												<span class="sr-only">Loading...</span>
											</div>
										</div>
									</td>
								</tr>
								<tr v-else v-for="(user, index) in userList" :key="index">
									<td class="m-0 px-0 py-2">
										<button class="m-0 px-2 rounded-circle" @click="addUser(user)">+</button>
									</td>
									<td class="m-0 px-0 py-2">{{ user.userName }}</td>
									<td class="m-0 px-0 py-2">{{ user.position }}</td>
									<td class="m-0 px-0 py-2">{{ user.email }}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row mt-3 mx-4 border">
					<table class="table table-bordered w-100">
						<thead>
							<tr>
								<td width="15%">
									<button class="m-0 px-3 py-1" style="background-color: #d25b49" @click="removeAllUser">전체 삭제</button>
								</td>
								<td width="15%">이름</td>
								<td width="15%">직책</td>
								<td width="15%">팀이름</td>
								<td width="40%">이메일</td>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(user, index) in addedUserList" :key="index">
								<td class="m-0 px-0 py-2">
									<button class="m-0 px-2 rounded-circle" style="background-color: #d25b49" @click="removeUser(index)">-</button>
								</td>
								<td class="m-0 px-0 py-2">{{ user.userName }}</td>
								<td class="m-0 px-0 py-2">{{ user.position }}</td>
								<td class="m-0 px-0 py-2">{{ user.teamName }}</td>
								<td class="m-0 px-0 py-2">{{ user.email }}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</mdb-modal-body>
			<!--			<mdb-modal-footer>-->
			<!--				<button class="px-3 py-2 rounded" @click="saveUser">저장하기</button>-->
			<!--			</mdb-modal-footer>-->
		</mdb-modal>
	</div>
</template>

<script>
import '@/css/Tree.css';
import { Teatree, NodeType, NodeName, NodeToggle } from 'vue-teatree';
import { mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody } from 'mdbvue';
import { getTeamUsers } from '@/api';
export default {
	name: 'ContactModal',
	components: {
		mdbModal,
		mdbModalHeader,
		mdbModalTitle,
		mdbModalBody,
		Teatree,
		NodeName,
		NodeToggle
	},
	data() {
		return {
			modal: false,
			teamList: [
				{
					id: '1',
					name: '플랫폼서비스본부',
					show: true,
					showChildren: true,
					selected: false,
					children: [
						{
							id: '2',
							name: '플랫폼5G&AI팀',
							show: true,
							showChildren: true,
							selected: false,
							children: []
						},
						{
							id: '3',
							name: '미디어플랫폼팀',
							show: true,
							showChildren: true,
							selected: false,
							children: []
						},
						{
							id: '4',
							name: '기가지니AI서비스팀',
							show: true,
							showChildren: true,
							selected: false,
							children: []
						},
						{
							id: '5',
							name: 'SmartX플랫폼팀',
							show: true,
							showChildren: true,
							selected: false,
							children: []
						},
						{
							id: '6',
							name: '플랫폼서비스담당',
							show: true,
							showChildren: true,
							selected: false,
							children: [
								{
									id: '9',
									name: '인증플랫폼팀',
									show: true,
									showChildren: true,
									selected: false,
									children: []
								},
								{
									id: '8',
									name: '결제플랫폼팀',
									show: true,
									showChildren: true,
									selected: false,
									children: []
								},
								{
									id: '7',
									name: '서비스플랫폼팀',
									show: true,
									showChildren: true,
									selected: false,
									children: []
								},
								{
									id: '10',
									name: '플랫폼품질혁신TF',
									show: true,
									showChildren: true,
									selected: false,
									children: []
								}
							]
						}
					]
				},
				{
					id: '11',
					name: '플랫폼/채널인프라팀',
					show: true,
					showChildren: true,
					selected: false,
					children: []
				}
				// {
				// 	id: '12',
				// 	name: '고객서비스본부',
				// 	show: true,
				// 	showChildren: true,
				// 	selected: false,
				// 	children: [
				// 		{
				// 			id: '13',
				// 			name: '고객5G&AI팀',
				// 			show: true,
				// 			showChildren: true,
				// 			selected: false,
				// 			children: []
				// 		},
				// 		{
				// 			id: '14',
				// 			name: '고객DX솔루션팀',
				// 			show: true,
				// 			showChildren: true,
				// 			selected: false,
				// 			children: []
				// 		},
				// 		{
				// 			id: '15',
				// 			name: '빌링담당',
				// 			show: true,
				// 			showChildren: true,
				// 			selected: false,
				// 			children: []
				// 		}
				// 	]
				// }
			],
			userList: [],
			loading: false
		};
	},
	props: {
		addedUserList: Array
	},
	methods: {
		async showMember(node) {
			this.loading = true;
			try {
				const response = await getTeamUsers(node.id);
				// console.log(response.data.data.users);
				this.userList = response.data.data.users;
			} catch (e) {
				alert(e);
			}
			this.loading = false;
		},
		addUser(user) {
			this.$emit('addUser', user);
		},
		addAllUser() {
			this.$emit('addAllUser', this.userList);
		},
		removeUser(index) {
			this.$emit('removeUser', index);
		},
		removeAllUser() {
			this.$emit('removeAllUser');
		}
	}
};
</script>

<style scoped>
.content-parent {
	font-size: 20px;
	height: 500px;
	position: relative;
}

.content-parent p {
	margin: 0;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	min-width: 100px;
	text-overflow: ellipsis;
}

.person-list {
	height: 200px;
}
</style>
