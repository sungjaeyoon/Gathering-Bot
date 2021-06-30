<template>
	<div>
		<div class="main-box">
			<video muted autoplay loop>
				<source src="@/assets/intro.mp4" type="video/mp4" />
				<strong>Your browser does not support the video tag.</strong>
			</video>
			<div class="main-text">
				<p>Gathering Bot</p>
				<span>취합 업무를 쉽고 빠르게 처리하세요.</span>
			</div>
		</div>
		<mdb-container class="pb-5">
			<h2 class="h1-responsive font-weight-bold text-center my-5">
				소 개
			</h2>
			<mdb-row>
				<mdb-col lg="5">
					<mdb-view class="rounded z-depth-1 mb-lg-0 mb-4" hover>
						<img class="mx-auto p-3" src="@/assets/add.png" alt="Sample image" style="height: 200px" />
					</mdb-view>
				</mdb-col>
				<mdb-col lg="7">
					<h3 class="green-text font-weight-bold mb-3 mt-4 p-0">
						1. 시트 생성
					</h3>
					<p class="sub-text">
						메뉴에서 시트 추가를 클릭하여 시트 생성 페이지로 진입하세요. <br />
						시트의 내용과 취합 항목을 입력하고 수신자를 지정하세요. <br />
						저장을 클릭하면 시트가 생성됩니다.
					</p>
				</mdb-col>
			</mdb-row>
			<hr class="my-5" />
			<mdb-row>
				<mdb-col lg="7">
					<h3 class="pink-text font-weight-bold mb-3 mt-4  p-0">
						2. 취합 시작
					</h3>
					<p class="sub-text">
						메뉴에서 시트 목록을 클릭하고 생성한 시트로 진입하세요. <br />
						시트 시작 버튼을 누르게 되면 취합 메일이 발송됩니다. <br />
						시트가 시작되면 메일발송을 취소할 수 없습니다.
					</p>
				</mdb-col>
				<mdb-col lg="5">
					<mdb-view class="rounded z-depth-1 mb-lg-0 mb-4" hover>
						<img class="mx-auto p-3" src="@/assets/email.png" alt="Sample image" style="height: 200px" />
					</mdb-view>
				</mdb-col>
			</mdb-row>
			<hr class="my-5" />
			<mdb-row>
				<mdb-col lg="5">
					<mdb-view class="rounded z-depth-1 mb-lg-0 mb-4" hover>
						<img class="mx-auto p-3" src="@/assets/result.svg" alt="Sample image" style="height: 200px" />
					</mdb-view>
				</mdb-col>
				<mdb-col lg="7">
					<h3 class="indigo-text font-weight-bold mb-3 mt-5 p-0">
						3. 응답 & 결과 확인
					</h3>
					<p class="sub-text">
						인원들은 메일에서 링크를 클릭하여 응답할 수 있습니다. <br />
						응답자, 미응답자, 통계를 확인하세요.
					</p>
				</mdb-col>
			</mdb-row>
		</mdb-container>
	</div>
</template>

<script>
import {
	mdbContainer,
	mdbRow,
	mdbCol,
	// mdbCard,
	// mdbCardBody,
	// mdbMask,
	// mdbIcon,
	mdbView
	// mdbBtn
} from 'mdbvue';
export default {
	name: 'Home',
	components: {
		mdbContainer,
		mdbRow,
		mdbCol,
		// mdbCard,
		// mdbCardBody,
		// mdbMask,
		// mdbIcon,
		mdbView
		// mdbBtn
	}
};
</script>
<style>
.main-box {
	width: 100%;
	height: 600px;
	overflow: hidden;
	margin: 0px auto;
	position: relative;
}
video {
	width: 100%;
	filter: brightness(70%);
}
.main-text {
	position: absolute;
	top: 35%;
	width: 100%;
}
.main-text p {
	text-align: center;
	font-size: 48px;
	color: #ffffff;
	font-family: KTBold;
}

.main-text span {
	text-align: center;
	font-size: 20px;
	color: #ffffff;
	font-family: KTThin;
}
.sub-text {
	font-size: 20px;
	font-family: KT;
}
</style>
