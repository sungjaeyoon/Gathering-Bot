<template>
	<div id="app">
		<div class="app-contents">
			<AppVar></AppVar>
			<router-view></router-view>
		</div>
		<!-- Footer -->
		<footer class="page-footer font-small blue pt-4">
			<div class="footer-copyright text-center py-3">
				© 2020 Copyright:
				<a href="#"> sungjae.yoon@kt.com</a>
			</div>
		</footer>
	</div>
</template>

<script>
import AppVar from './components/common/AppHeader.vue';

export default {
	name: 'App',
	components: {
		AppVar
	}
};
</script>

<style>
@import './css/common.css';
</style>
