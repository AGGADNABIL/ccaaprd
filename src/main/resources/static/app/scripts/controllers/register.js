'use strict';
   
/**
 * # RegisterCtrl
 */
app
.factory('registerService',['$resource', function ($resource) {
    return $resource('http://localhost:8080/candidats/candidats/:id',  {}, {
        query: { method: "GET", isArray: true },
        create: { method: "POST"},
        get: { method: "GET"},
        remove: { method: "DELETE" },
        update: { method: "PUT"}
    });

    
   
}])
 .controller('RegisterCtrl', function ($scope, $http,registerService,sweet,$state) { 
	 var emailError=false;
	 $scope.username='';
	  $scope.registerMe = function() {
		  var errors=true;
		  var response="";
		  var username=$scope.username;
		  var password=$scope.password;
		  var passwordConfirm=$scope.passwordConfirm;
		  if(password==passwordConfirm)errors=false;
		  else errors=true;
		  $scope.errors=errors;
		  if(!errors){
			  registerService.create({
	              username: $scope.username,
	              password: $scope.password,
	              nom:$scope.nom,
	              prenom:$scope.prenom
	              
	            }).$promise.then(function(){
	            	
	               $scope.register = true;
	               $scope.username='';
	               $scope.password=null;
	               $scope.nom=null;
		           $scope.prenom=null;
	               $scope.passwordConfirm=null;
	               $scope.verification=false;
		            sweet.show(' votre inscription a été enregistrée avec succès!', 'un message d\'activation à été envoyé à votre adresse e-mail.', 'success');
					$state.go('app.login');

	              function counter($el, n) {
	                (function loop() {
	                  $el.html(n);
	                  if (n--) {
	                    setTimeout(loop, 1000);
	                  }else{
	                    // $mdDialog.hide();
	                  }
	                })();
	              }
	              // counter($('#counter'), 10);
	            }).catch(function(error){
	              if(error.data == "USERNAME_CONFLICT"){
	            	  emailError=true;
	            	  $scope.username='';
	            	  $scope.emailError=emailError;
	            	  $scope.usernameConflict ="Ce compte ou cette adresse email existe déjà";
	              }
	            }).finally(function(){
	              // $scope.signingup = false;
	              // $scope.loading = false;
	            });
		  }

		  
		  
		  
		  
		};


  });
