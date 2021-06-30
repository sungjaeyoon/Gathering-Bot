<template>
	<div id="app">
		<AppBar></AppBar>
		<router-view />
		<div class="footer-copyright text-center py-3 mt-5">&copy; 2020 Copyright: <a href="#">sungjae.yoon@kt.com </a></div>
	</div>
</template>
<script>
import AppBar from '@/components/common/AppBar';
export default {
	components: {
		AppBar
	}
};
</script>

<style>
@font-face {
	font-family: 'KT';
	src: local('KT'), url('./fonts/KTfontMedium.woff') format('woff'), url('./fonts/KTfontMedium.ttf') format('truetype');
}
@font-face {
	font-family: 'KTBold';
	src: local('KTBold'), url('./fonts/KTfontBold.woff') format('woff'), url('./fonts/KTfontBold.ttf') format('truetype');
}
@font-face {
	font-family: 'KTLight';
	src: local('KTLight'), url('./fonts/KTfontLight.woff') format('woff'), url('./fonts/KTfontLight.ttf') format('truetype');
}
@font-face {
	font-family: 'KTThin';
	src: local('KTThin'), url('./fonts/KTfontThin.woff') format('woff'), url('./fonts/KTfontThin.ttf') format('truetype');
}
#app {
	font-family: 'KT', 'KTBold', 'KTLight', 'KTThin', Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
}
body {
	background-color: #f4f4f4;
	width: 100%;
	height: 100%;
	min-width: 1200px;
	overflow: auto;
}
button {
	background-color: #2cbbb6;
	color: white;
	border: 0;
}

button:hover {
	background-color: #1a9691;
}

button:focus {
	outline: none !important;
}

input:focus {
	outline-color: #2cbbb6 !important;
}
li > .active {
	background-color: #2cbbb6 !important;
}

.button-blue {
	background-color: white;
	color: #33b5e5;
	border: 1px solid #33b5e5;
}

.button-blue:hover {
	background-color: #33b5e5;
	color: white;
}

.button-danger {
	background-color: white;
	color: #dc3f13;
	border: 1px solid #ecb48b;
}

.button-danger:hover {
	background-color: #ecb48b;
	color: white;
}

.scrollbar-info::-webkit-scrollbar-track {
	-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
	background-color: #f5f5f5;
	border-radius: 10px;
}

.scrollbar-info::-webkit-scrollbar {
	width: 12px;
	height: 12px;
	background-color: #f5f5f5;
}

.scrollbar-info::-webkit-scrollbar-thumb {
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
	background-color: #2cbbb6;
}
</style>
