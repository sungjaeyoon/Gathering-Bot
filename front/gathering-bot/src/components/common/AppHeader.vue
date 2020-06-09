<template>
	<div class="container">
		<div class="row no-gutters">
			<div class="col-sm-6 col-md-8">
				<router-link to="/" style="color: white; text-decoration: none">
					<img src="@/assets/logo.png" style="width: 15%" alt="" />
				</router-link>
			</div>
			<div class="col-6 col-md-4 left-bar">
				<span class="login">
					<router-link to="/login" style="color: gray; text-decoration: none">로그인</router-link>
				</span>
				<span class="signUp">
					<router-link to="/signup" style="color: black; text-decoration: none">회원가입</router-link>
				</span>
				<span class="signUp">
					<router-link to="/sheet/new" style="color: black; text-decoration: none">A(test)</router-link>
				</span>
			</div>
		</div>
	</div>
</template>

<script>
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
export default {
	name: 'AppVar',
};
</script>

<style scoped>
.row {
	margin-top: 3%;
}

.left-bar {
	margin-top: 2%;
}

.login {
	font-size: 25px;
	font-weight: bold;
	margin-right: 7%;
	cursor: pointer;
}

.signUp {
	font-size: 25px;
	font-weight: bold;
	margin-left: 7%;
	padding: 3%;
	border-radius: 0.5em;
	border: 2px solid black;
	cursor: pointer;
}
</style>
