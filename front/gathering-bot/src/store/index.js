import Vue from 'vue';
import Vuex from 'vuex';
import { getEmailFromCookie, getIdFromCookie, getPositionFromCookie, getTeamNameFromCookie, getTokenFromCookie, getUserFromCookie } from '@/utils/cookie';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		id: getIdFromCookie() || '',
		email: getEmailFromCookie() || '',
		username: getUserFromCookie() || '',
		teamName: getTeamNameFromCookie() || '',
		position: getPositionFromCookie() || '',
		token: getTokenFromCookie() || ''
	},
	getters: {
		isLogin(state) {
			return state.username != '';
		}
	},
	mutations: {
		setUserdata(state, data) {
			state.id = data.id;
			state.email = data.email;
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
	},
	actions: {
		LOGIN({}) {}
	}
});
