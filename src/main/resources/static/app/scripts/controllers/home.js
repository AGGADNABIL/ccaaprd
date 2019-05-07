'use strict';

/**
 * @ngdoc function
 * @name minovateApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the minovateApp
 */
app
  .controller('home', function ($scope,sweet, $http,referenceAll) {

	     $scope.references=referenceAll;
		 console.log($scope.references);
		 
/*
 var valider=fonction (){
			 sweet.show(' profile Modifié!', 'Votre profile a été bien modifié.', 'success');
		};
		
		var invalider=fonction (){
			 sweet.show(' profile Modifié!', 'Opération non réussite.', 'rejetée');
		};
 * */		
  });
