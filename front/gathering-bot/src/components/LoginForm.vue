<template>
	<div class="loginForm">
		<form @submit.prevent="submitForm" class="form">
			<div>
				<label for="username">id:</label>
				<input id="username" type="text" v-model="username" />
				<p class="validation-text">
					<span class="warning" v-if="!isUsernameValid && username">
						Please enter an email address
					</span>
				</p>
			</div>
			<div>
				<label for="password">pw:</label>
				<input id="password" type="text" v-model="password" />
			</div>
			<button :disabled="!isUsernameValid || !password" type="submit" class="btn">
				로그인
			</button>
		</form>
	</div>
</template>

<script>
export default {
	name: 'LoginForm',
};
</script>

<style scoped>
.loginForm {
	color: white;
	background-color: #7d2ae8;
	border-radius: 2em;
	height: 100%;
}
</style>
