'use strict';

app.factory('NosMetierService',['$resource', function($resource) {
  return $resource('http://localhost:8080/nosMetier/:id',{},{
	  query: {method: "GET", isArray: true},
      create: {method: "POST"},
      get: {method: "GET"},
      remove: {method: "DELETE"},
      update: {method: "PUT"}
  });
  
 
	 
}])
 .controller('NosMetierCtr',function($scope,NosMetierService,sweet){
	 
	 $scope.Enregister=function (){
		 NosMetierService.create({
			 'titre':$scope.NosMetier.titre,
			 'contenu':$scope.NosMetier.contenu,
			 'contenuShow':$scope.NosMetier.contenuShow,
			 'priorite':$scope.NosMetier.priorite 
		 }).$promise.then(function (){
			 sweet.show(' Ajout !', 'Opération effectuée avec succès.', 'succès');
		 })
	 };
	  
  });
	  
