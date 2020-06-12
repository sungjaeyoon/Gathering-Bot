import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		id: '',
		email: '',
		username: '',
		teamName: '',
		position: '',
		token: ''
	},
	getters: {
		isLogin(state) {
			return state.username != '';
		}
	},
	mutations: {
		setUserdata(state, data) {
			state.id = data.id;
			state.email = data.username;
			state.username = data.username;
			state.teamName = data.teamName;
			state.position = data.position;
			state.token = data.token;
		},
		clearUsername(state) {
			state.id = '';
			state.email = '';
			state.username = '';
			state.teamName = '';
			state.position = '';
			state.token = '';
		}
	}
});
