export function saveEmailToCookie(value) {
	document.cookie = `email=${value}`;
}

export function saveIdToCookie(value) {
	document.cookie = `id=${value}`;
}

export function savePositionToCookie(value) {
	document.cookie = `position=${value}`;
}

export function saveTeamNameToCookie(value) {
	document.cookie = `teamname=${value}`;
}

export function saveTokenToCookie(value) {
	document.cookie = `token=${value}`;
}

export function saveUserToCookie(value) {
	document.cookie = `username=${value}`;
}

export function getEmailFromCookie() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)email\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

export function getIdFromCookie() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)id\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

export function getPositionFromCookie() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)position\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

export function getTeamNameFromCookie() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)teamname\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

export function getTokenFromCookie() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)token\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

export function getUserFromCookie() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)username\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

export function deleteCookie(value) {
	document.cookie = `${value}=; expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
}
