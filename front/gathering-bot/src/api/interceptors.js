import store from '@/store/index';

export function setInterceptors(instance) {
	instance.interceptors.request.use(
		function(config) {
			config.headers.Authorization = store.state.token;
			console.log(config);
			return config;
		},
		function(error) {
			return error;
		}
	);
	// instance.interceptors.response.use(
	// 	function(response) {},
	// 	function(error) {}
	// );
	return instance;
}
