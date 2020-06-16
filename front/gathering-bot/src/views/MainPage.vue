<template>
	<div>
		<img src="@/assets/robot.png" class="rounded mx-auto d-block" alt="..." style="height: 350px" />
		<h1>Gathering-bot</h1>
		<h4>Gathering-bot은 취합,설문,조사 등의 업무에</h4>
		<h4>최적화 되어있습니다.</h4>
		<div class="row mt-5 mx-5 mb-5">
			<div class="col-3">
				<div class="card mx-auto shadow-lg p-3 mb-5 bg-white rounded" style="width: 15rem;">
					<img src="@/assets/add.png" class="card-img-top center" alt="..." style="width: 6rem" />
					<div class="card-body">
						<p>조사할 항목을 추가하고 <br />수신자를 지정하세요.</p>
					</div>
				</div>
			</div>
			<div class="col-3">
				<div class="card mx-auto shadow-lg p-3 mb-5 bg-white rounded" style="width: 15rem;">
					<img src="@/assets/email.png" class="card-img-top center" alt="..." style="width: 6rem" />
					<div class="card-body">
						<p>자동으로 메일이 발신되며<br />바로 응답할 수 있습니다.</p>
					</div>
				</div>
			</div>
			<div class="col-3">
				<div class="card mx-auto shadow-lg p-3 mb-5 bg-white rounded" style="width: 15rem;">
					<img src="@/assets/card-robot.png" class="card-img-top center" alt="..." style="width: 6rem" />
					<div class="card-body">
						<p>Gathering-bot이<br />응답 결과를 취합합니다.</p>
					</div>
				</div>
			</div>
			<div class="col-3">
				<div class="card mx-auto shadow-lg p-3 mb-5 bg-white rounded" style="width: 15rem;">
					<img src="@/assets/microsoft-excel.png" class="card-img-top center" alt="..." style="width: 6rem" />
					<div class="card-body">
						<p>결과를 확인하고<br />엑셀로 다운받으세요.</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {};
</script>

<style>
.center {
	display: block;
	margin-left: auto;
	margin-right: auto;
	width: 50%;
}
.card {
	text-align: center;
	border: none;
}
h1 {
	margin-top: 3%;
	margin-bottom: 3%;
}
h4 {
	margin-top: 2%;
	margin-bottom: 2%;
	text-align: center;
}
</style>
