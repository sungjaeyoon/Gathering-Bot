<template>
	<div class="container">
		<div class="row" style="margin: auto;text-align: center">
			<div class="col"></div>
			<div class="col center">
				<h1 class="page-header" style="text-align: center">로그인</h1>
			</div>
			<div class="col"></div>
		</div>
		<div class="row" style="height: 600px">
			<div class="col"></div>
			<div class="col center">
				<LoginForm></LoginForm>
			</div>
			<div class="col"></div>
		</div>
	</div>
</template>

<script>
import LoginForm from '@/components/LoginForm.vue';
export default {
	name: 'LoginPage',
	components: {
		LoginForm,
	},
};
</script>

<style scoped>
.center {
	margin: auto;
	text-align: center;
}
.login-form {
	color: white;
	border-radius: 0.5em;
	background-color: #7d2ae8;
}
</style>
