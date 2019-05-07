app.factory('candidatService', [ '$resource', function($resource) {
	return $resource('http://localhost:8080/candidats/candidats', {}, {
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

}])    
.directive('exportTable',function(){
	return {
		restrict: 'C',
		  link: function($scope, elm, attr){
			  $scope.$on('export-pdf', function(e, d){
			      elm.tableExport({type:'pdf', escape:'false'});
			 });
			$scope.$on('export-excel', function(e, d){
			       elm.tableExport({type:'excel', escape:false});
			 });
			$scope.$on('export-doc', function(e, d){
			     elm.tableExport({type: 'doc', escape:false});
			 });
		}
	}
})
.controller('CandidatCtrl', function(candidatService, $scope, $http, $filter,
		ngTableParams, $resource, sweet,$stateParams,$state,$sessionStorage) {
	if($sessionStorage.authenticated && $sessionStorage.connectedRole =='ROLE_ADMIN'){
			var Api = "http://localhost:8080/candidats/candidats/";
			if($stateParams.candidatsByOffre !=null){
				$scope.candidats = $stateParams.candidatsByOffre;
			}else{
				$scope.candidats = candidatService.query();
			}
			
			
			$scope.remove = function(id,codeUser) {
				sweet.show({
					title : 'Confirmer',
					text : 'Voulez-vous vraiment supprimer ce candidat ?',
					type : 'warning',
					showCancelButton : true,
					confirmButtonColor : '#DD6B55',
					confirmButtonText : 'Oui, supprimez-le !',
					closeOnConfirm : false
				}, function() {
					$resource(Api + id+"/candidats").remove().$promise.then(function() {
						sweet.show('Supprimé !', 'Le candidat a été supprimé.',
								'success');
						angular.element(
								document.querySelector('#candidat_'+ codeUser))
								.remove();
					});
				});
			};
		   
			$scope.exportAction = function(){ 
			      switch($scope.export_action){ 
			          case 'pdf': $scope.$broadcast('export-pdf', {}); 
			                      break; 
			          case 'excel': $scope.$broadcast('export-excel', {}); 
			                      break; 
			          case 'doc': $scope.$broadcast('export-doc', {});
			                      break; 
			          default: console.log('no event caught'); 
			       }
			};
			
			$resource(Api).query().$promise.then(function(data) {
				if($stateParams.candidatsByOffre !=null){
					$scope.result = $stateParams.candidatsByOffre;
				}else{
					$scope.result = data;
				}
				
		
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
				});
				$scope.getMoreData = function() {
					$scope.data = $scope.result.slice(0,$scope.data.length + 20);
				}
			});
	}else{
		$state.go('app.home');
	}
	

})
//CandidatConsulterCtrl

.controller('CandidatConsulterCtrl',function($scope, $http ,$stateParams,$state,$sessionStorage) {

	if($sessionStorage.authenticated && $sessionStorage.connectedRole =='ROLE_ADMIN'){

		$http.get('http://localhost:8080/candidats/findOneCandidat/'+$stateParams.id+'/candidats').then(function(data) {
			$scope.candidat = data;
			console.log($scope.candid);
			});
	}else{
		$state.go('app.home');
	};		
		});
