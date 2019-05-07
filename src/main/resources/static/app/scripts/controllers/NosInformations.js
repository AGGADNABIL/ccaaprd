'use strict';

app.factory('NosInformationService',['$resource', function($resource) {
  return $resource('http://localhost:8080/nosInfos/:id',{},{
	  query: {method: "GET", isArray: true},
      create: {method: "POST"},
      get: {method: "GET"},
      remove: {method: "DELETE"},
      update: {method: "PUT"}
  });
 
}])
 .controller('NosInfossCtrl',function($scope,NosMetierService,sweet){ 
	 $scope.valider=function (){
		 NosMetierService.create({
			 'titre':$scope.NosInfos.titre,
			 'image':$scope.NosInfos.image,
			 'contenu':$scope.NosInfos.contenu,
			 'contenuShow':$scope.NosInfos.contenuShow,
			 'priorite':$scope.NosInfos.priorite 
		 }).$promise.then(function (){
			 sweet.show(' Ajout !', 'Opération effectuée avec succès.', 'success');
		 },function (){
			 sweet.show(' Ajout !', 'Opération effectuée n\'a pas été effectuée.', 'error');
		 });
	 };
	  
  });
	  
