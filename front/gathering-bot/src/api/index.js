import axios from 'axios';

const instance = axios.create({
	baseURL: 'http://localhost:8080'
});

export function registerUser(userData) {
	return instance.post('signup', userData);
}

export function loginUser(userData) {
	console.log(userData);
	return instance.post('login', userData);
}

export function checkDuplicateEmail(email) {
	return instance.get('/check/' + email);
}

export function getUserList() {
	return instance.get('/users');
}

export function insertSheet(data) {
	return instance.post('/sheets/new', data);
}

export function getSheet(userId, filter) {
	return instance.get('/sheets/users/' + userId, { params: filter });
}

export function getSheetResponse(sheetId, userId) {
	return instance.get('/response/' + sheetId + '/' + userId);
}

export function getDetail(sheetId) {
	return instance.get('/sheets/' + sheetId);
}

export function updateResponse(data) {
	return instance.post('/response/result', data);
}

export function startSheet(sheetId) {
	return instance.get('/sheets/start/' + sheetId);
}
export function endSheet(sheetId) {
	return instance.get('/sheets/end/' + sheetId);
}
