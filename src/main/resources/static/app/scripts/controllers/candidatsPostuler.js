app.factory('postulerService', [
		'$resource',
		function($resource) {
			return $resource(
					'http://localhost:8080/postulers/getAllOffre/:codeOffre',
					{}, {
						query : {
							method : "GET",
							isArray : true
						},
						create : {
							method : "POST"
						},
						get : {
							method : "GET"
						},
						remove : {
							method : "DELETE"
						},
						update : {
							method : "PUT"
						}
					});

		} ])
app.factory('candidatService', [ '$resource', function($resource) {
	return $resource('http://localhost:8080/candidats/candidats/', {}, {
		query : {
			method : "GET",
			isArray : true
		},
		create : {
			method : "POST"
		},
		get : {
			method : "GET"
		},
		remove : {
			method : "DELETE"
		},
		update : {
			method : "PUT"
		}
	});

} ]).factory(
		'downloadServic',
		[
				'$q',
				'$timeout',
				'$window',
				function($q, $timeout, $window) {
					return {
						download : function(name) {

							var defer = $q.defer();

							$timeout(
									function() {
										$window.location = '/recruteCapvalue/downloadPostuler/'
												+ name + '/f';
									}, 1000).then(function() {
								defer.resolve('success');

							}, function() {
								defer.reject('error');
							});
							return defer.promise;
						}
					};
				} ])

.controller(
		'CandidatPostulerCtrl',
		function($scope, $http, $filter, downloadServic, $localStorage,
				ngTableParams, $resource, sweet, $state, $sessionStorage) {
			if ($sessionStorage.authenticated
					&& $sessionStorage.connectedRole == 'ROLE_ADMIN') {
				/*
				 * var Api =
				 * "http://localhost:8080/postulers/getAllOffre/:codeOffre";
				 * $scope.postulers=$stateParams.candidatsPostuler;
				 * if($stateParams.candidatsPostuler ==null){ $scope.postulers =
				 * candidatService.query(); }
				 */

				$scope.downloadPost = function(name) {
					downloadServic.download(name).then(function(success) {
						console.log('success : ' + success);
					}, function(error) {
						console.log('error : ' + error);
					});
				};

				$scope.loadpage = function() {
					$state.reload();
				};

				var Api = "http://localhost:8080/candidats/candidats/";
				$scope.postulers = $localStorage.candidatsPostuler;
				$scope.result = $localStorage.candidatsPostuler;

				$scope.tableParams = new ngTableParams({
					page : 1,
					count : $scope.result.length
				}, {
					counts : [],
					total : 1,
					getData : function($defer, params) {

						$scope.data = params.sorting() ? $filter('orderBy')(
								$scope.result, params.orderBy())
								: $scope.result;
						$scope.data = params.filter() ? $filter('filter')(
								$scope.data, params.filter()) : $scope.data;
						$scope.data = $scope.data.slice(0, 20);
						$defer.resolve($scope.data);
					}
				})

				$scope.getMoreData = function() {
					$scope.data = $scope.result.slice(0,
							$scope.data.length + 20);
				}

			} else {
				$state.go('app.home');
			}

		})

.controller(
		'CandidatConsulterCtrl',
		function(postulerService, $scope, $http, $stateParams, $state,
				$sessionStorage) {
			if ($sessionStorage.authenticated
					&& $sessionStorage.connectedRole == 'ROLE_ADMIN') {
				$http.get(
						'http://localhost:8080/candidats/findOneCandidat/'
								+ $stateParams.id + '/candidats').success(
						function(data) {

							$scope.candidat = data;
						    console.log($scope.candidat);
						});
			} else {
				$state.go('app.home');
			}

		});