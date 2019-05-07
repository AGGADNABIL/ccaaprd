'use strict';
app

.controller('ForgotPasswordCtrl',function($http, $state,$scope,$location,$resource) {
	if($location.search().email ==null)$state.go('app.home');
	$scope.passwordNotIdentique=false;
	$scope.valider=function(){
		if($scope.password!=$scope.confirmPassword){
			$scope.passwordNotIdentique=true;
		}else{
			$scope.passwordNotIdentique=false;
			$scope.changePasswordMessageState=false;
			$scope.response=$resource('http://localhost:8080/users/recoverPassword/'+$location.search().email+'/'+$location.search().verificationKey,  {}, {
		        query: { method: "GET", isArray: true },
		        create: { method: "POST"},
		        get: { method: "GET"},
		        remove: { method: "DELETE" },
		        update: { method: "PUT"}
		    }).update({
				"password":$scope.password
			}).$promise.then(function(){
				$scope.changePasswordMessageState=true;
				$scope.changePasswordMessage="Votre mot de passe est changé";
				setTimeout(function(){
			        $state.go('app.login');
			    },5000);
			});
			
		}
		
	}
	
 })
 
.controller('FormGetPasswordCtrl',function($http, $state,$scope,sweet) {
	$scope.valider=function(){
		$http.get('http://localhost:8080/users/forgotPassword?email='+$scope.email).success(function(data){
			if(data=='PASSWORD_REQUESTED_SUCCESSFULY'){
				$scope.username=$scope.email;
				 sweet.show('vous receverez dans quelques instants un email de reinitialisation de votre mot de passe!'
						 	, 'Opération réussite.', 'success');
				 $state.go('app.home');
			};
			
		});
	}
	
		});



