<template>
	<div id="app">
		<div class="app-contents">
			<AppVar></AppVar>
			<router-view></router-view>
		</div>
	</div>
</template>

<script>
import AppVar from './components/common/AppHeader.vue';
export default {
	name: 'App',
	components: {
		AppVar,
	},
};
</script>

<style>
@import './css/common.css';
</style>
