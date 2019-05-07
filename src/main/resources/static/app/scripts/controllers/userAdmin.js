'use strict';
app
.factory('userService',['$resource', function ($resource) {
    return $resource('http://localhost:8080/users/allUsers', {}, {
        query: { method: "GET", isArray: true },
        create: { method: "POST"},
      //  get: { method: "GET"},
        remove: { method: "DELETE" },
        update: { method: "PUT"}
    });

}])
 .controller('UsersCtrl', function ($scope,userService,$resource,sweet,$state, $filter, ngTableParams,$sessionStorage) { 
	   if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') { 
		   
		   $scope.editingData = {};
			
			angular.forEach($scope.users, function(item){
				$scope.editingData[item.codeUser] = false;
		      });
		    $scope.modify = function(tableData){
		        $scope.editingData[tableData.codeUser] = true;
		    };
		    
		   /* $scope.update = function(tableData){
		        $scope.editingData[tableData.codeUser] = false;
		    };
		   $scope.isVisible=false;
		   $scope.showHide=function (){
			   $scope.isVisible = $scope.isVisible ? false : true;
		   }*/
		    
		   $scope.update=function (user){
			   $scope.editingData[user.codeUser] = false;
			   $resource('http://localhost:8080/users/updateAdmin').update({
		              "username" : user.username,
		              "nom" : user.nom,
		              "prenom" : user.prenom  
			   }).$promise.then(function (){
				   		sweet.show('Modification :', 'La modification a été effectué avec succès!', 'success');
			   });
		   };
		 $scope.changeEtat = function (user, $event) {
			    console.log('actived : '+ user.actived);
		        $resource('http://localhost:8080/users/allUsers/' + user.username + '/' + !user.actived, null, {
		          'update': {method: 'PUT'}
		        }).update().$promise.then(function () {
		        	user.actived = !user.actived ;
		        });
		      };
		 
		 userService.query().$promise.then(function(data){
			 $scope.result = data;
				$scope.tableParams = new ngTableParams({
					page : 1,
					count : $scope.result.length
				}, {
					counts : [],
					total : 1,
					getData : function($defer, params) {
						$scope.users = params.sorting() ? $filter(
								'orderBy')($scope.result,
								params.orderBy()) : $scope.result;
						$scope.users = params.filter() ? $filter(
								'filter')($scope.users,
								params.filter()) : $scope.users;
						$scope.users = $scope.users.slice(0, 20);
						$defer.resolve($scope.users);
					}
	
				});
	
				$scope.getMoreData = function() {
					$scope.users = $scope.result.slice(0,
							$scope.users.length + 20);
				}
				 console.log('administrateur :')
				 console.log($scope.users);
				 
		 });
		 
	 $scope.remove=function(user){
		 sweet.show({
	          title: 'Confirmer',
	          text: 'Voulez-vous vraiment supprimer cet utilisateur ?',
	          type: 'warning',
	          showCancelButton: true,
	          confirmButtonColor: '#DD6B55',
	          confirmButtonText: 'Oui, supprimez-le !',
	          closeOnConfirm: false
	        }, function () {
	          $resource('http://localhost:8080/users/allUsers/' + user.username +'/target').remove().$promise.then(function () {
	            sweet.show('Supprimé !', 'L\'utilisateur a été supprimé.', 'success');
	            angular.element(document.querySelector('#user_' + user.codeUser)).remove();
	          });
	        });
	      };
	 
	  var emailError=false;
	  
	  $scope.valider = function() {
		  console.log('paxx :'+$scope.pass);
		  console.log('username :'+$scope.newUsername);
		  console.log('nom :'+$scope.nom);
		  console.log('prenom :'+$scope.prenom);
		 	  $resource('http://localhost:8080/users/addAdmin').save({
				  "typeUser" : "UA",
	              "username" : $scope.newUsername,
	              "password" : $scope.pass,
	              "nom" : $scope.nom,
	              "prenom" : $scope.prenom,
	              "actived" : true,
	              "roles" : [
	                      {
	                    	  "codeRole": 1,
	                          "role": "ADMIN",
	                          "description": "Role d'administrateur"
	                      }
	                      ]
	            }).$promise.then(function(){	
	             
		            sweet.show('Nouveau administrateur :', 'L\'ajout a été effectué avec succès!', 'success');
					$state.reload();
	              });
	              // counter($('#counter'), 10);
	            };
		  }else {
			  $state.go('app.home');
		  }
  });
