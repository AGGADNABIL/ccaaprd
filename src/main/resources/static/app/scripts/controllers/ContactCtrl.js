'use strict';

app
.controller('ContactCtrl',function($scope,$http){
/*	$scope.contacter={};
	$scope.contacter.username="";
	$scope.contacter.subject="";
	$scope.contacter.message="";	*/
	
	$scope.envoyer=function(){
		//console.log('message' +$scope.contacter.message);
		$http.get('http://localhost:8080/contacter/'+$scope.contacter.username+'/'+$scope.contacter.subject+'/'+$scope.contacter.message).success(function(data) {
	//console.log(data);
	});
	    
}
})

;