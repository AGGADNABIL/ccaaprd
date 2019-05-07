'use strict';
app.controller('LoginCtrl', function($rootScope,$resource, $http, $state,$scope,$sessionStorage,$location,$window,$localStorage) {
			$rootScope.loginErrors = false;
			$rootScope.connectedRole=$sessionStorage.connectedRole;
			$rootScope.authenticated=$sessionStorage.authenticated;
			console.log($rootScope.connectedRole);
			
			$scope.changePass= function (){
					 
				      var errors=true;
					  var response='';
					  var username=$rootScope.$storage.username;
					  var password=$scope.newPassword;
					  var passwordConfirm=$scope.confirmPassword;
			       if(password==passwordConfirm)
				      errors=false;
					  else errors=true;
					  $scope.errors=errors;
					  if(!errors){
						  $resource('http://localhost:8080/users/currentUser/'+$rootScope.$storage.username+'/',null, {
					            'update': {method: 'PUT'}
						  }).update({
				              username: $rootScope.$storage.username,
				              password: $scope.newPassword
				            }).$promise.then(function(){
				               //$scope.register = true;
				               //$scope.username='';
				               // $scope.verification=false;
				               $scope.newPassword=null;
				               $scope.passwordConfirm=null;
					            sweet.show(' votre inscription a été enregistrée avec succès!', 'un message d\'activation à été envoyé à votre adresse e-mail.', 'success');
								$state.go('app.login');

				             /* function counter($el, n) {
				                (function loop() {
				                  $el.html(n);
				                  if (n--) {
				                    setTimeout(loop, 1000);
				                  }else{
				                    // $mdDialog.hide();
				                  }
				                })();
				              }*/
				              // counter($('#counter'), 10);
				            }).catch(function(error){
				              if(error.data == "USERNAME_CONFLICT"){
				            	  emailError=true;
				            	  $scope.username='';
				            	  $scope.emailError=emailError;
				              }
				            }).finally(function(){
				              // $scope.signingup = false;
				              // $scope.loading = false;
				            });
					  } 
					  
					};
				
			

			/*self.tab = function(route) {
				return $state.current && route === $state.current.controller;
			};*/
			
			$rootScope.$storage = $localStorage.$default({
		          username: $rootScope.username,
			      name : "", 
			      lastname: ""
		        });
			
			var authenticate = function(credentials, callback) {
				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};
				$http.get('http://localhost:8080/user', {
					headers : headers
				}).then(function(response) {
					if (response.data.name) {
						console.log("response.data");
						console.log(response.data);
						$rootScope.authenticated = true;
                        $sessionStorage.username = response.data.name;
						$rootScope.username=response.data.name;
						$rootScope.roles=response.data.authorities;
						$rootScope.sessionid=response.data.details.sessionId;
						
						
					} else {
						$rootScope.authenticated = false;
					}
					callback && callback($rootScope.authenticated);
				}, function() {
					$rootScope.authenticated = false;
					callback && callback(false);
				});

			}
			$rootScope.adminstrateur=false;
			$scope.credentials = {};
			$scope.login = function() {
				authenticate($scope.credentials, function(authenticated) {
					$sessionStorage.authenticated=authenticated;
					if (authenticated) {
						 console.log('adminstrateur :'+$rootScope.adminstrateur);
						//if($rootScope.roles[0].authority =='ROLE_ADMIN'){
						$sessionStorage.connectedRole = $rootScope.roles[0].authority;
						$rootScope.connectedRole=$rootScope.roles[0].authority;
						
						console.log($rootScope.connectedRole);
						
						$http.get('http://localhost:8080/users/currentUser').then(function(response){
							 //$scope.$storage=response.data; 
							 console.log("data User Current");
							 $rootScope.$storage.username=response.data.username;
							 $rootScope.$storage.name=response.data.nom;
							 $rootScope.$storage.lastname=response.data.prenom;
							 $rootScope.adminstrateur= response.data.username=='admin@gmail.com' ? true :false; 
							
							 // console.log('adminstrateur :'+$rootScope.adminstrateur);
							 /*var us =response.data
							 $window.sessionStorage.setItem(us, JSON.stringify(us));
							 $rootScope.u= $window.sessionStorage.getItem(us);
							 console.log('useru');
							 console.log($rootScope.u);*/
							
							 /*$scope.u=response.data.username;
							 $scope.$storage=$localStorage.u;
							 console.log('local storage ');
							 console.log($scope.$storage);
							 console.log('scope user ');
							 console.log( $scope.u);*/
							 
							
							// console.log($scope.$storage.nom);
							// console.log($scope.$storage.prenom);
							 
							//$rootScope.pictureProfile=response.data.photo;
							 
						});
						if($rootScope.url){
							 console.log($rootScope.url);
							//$location.url($rootScope.url);
							$window.location.href = $rootScope.url;
						}else{
							
							$state.go("app.home");
						}
							
						
						$scope.error = false;
						$rootScope.authenticated = true;
						$rootScope.loginErrors = false;
						$rootScope.url='';
					} else {
						$state.go("app.login");
						$scope.error = true;
						$rootScope.authenticated = false;
						$rootScope.loginErrors = true;
					}
				})
			
			};
			$scope.forgotPassword=function(){
				$state.go('app.formGetPassword');
			}
		});



