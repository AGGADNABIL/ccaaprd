'use strict';

app
.filter('removeSpaces', [function() {
    return function(string) {
        if (!angular.isString(string)) {
            return string;
        }
        return string.replace(/[\s]/g, '');
    };
}])
.filter('replaceSpaces', [function() {
    return function(string) {
        if (!angular.isString(string)) {
            return string;
        }
        return string.replace(/[\s]/g, '-');
    };
}])
.factory('candidatService',['$resource', function ($resource) {
    return $resource('http://localhost:8080/candidats/candidats/:id', {}, {
        query: { method: "GET", isArray: true },
        create: { method: "POST"},
        get: { method: "GET"},
        remove: { method: "DELETE" },
        update: { method: "PUT"}
    });

}])

.factory('dateFactory',function(){
     var factory = {
     	days : function (){
	      	var tab=[];
		   	for (var i = 0; i <31; i++)
		   	     {
	   	    		tab[i]=i+1;
	  		 };
  		 return tab;
	   },

	   months : ['Janvier','Février','Mars','Avril','Mai','Juin','Juillet','Août','Septembre','Octobre','Novembre','Décembre'],
	   
	   years : function (){
		   	var array =[];
	        var age=1970;
	        var d=new Date();
	        var year=d.getFullYear()-1970;  
		   	for (var i = 0; i <=year+1; i++)
		   	     {
	   	    		array[i]=age++;
	  		 };
  		 return array; 
	   },

	   currentDate:function (day,month,year){
	   	    var dateInput=day+"-"+month+"-"+year;
	   	    var currDate=new Date(dateInput);
	   	    
	   	    console.log("data Enter "+ currDate);
	   	  return new Date(currDate);
	   },
	   niveau:function (){
		   var arrayNiv=['Bac','Bac + 2','Bac + 3','Bac + 4','Bac + 5','Bac + 5 et plus'];
		   return arrayNiv;
	   },
	   
	   situation :function (){
		    var arry=['Freelance','salarié'];
		    return arry;
	   },
	   experience :function (){
		    var ary=['1 an','2 ans','3 ans','4 ans','5 ans','plus de 5 ans'];;
		    return ary;
	   }
         
          
     }
  return factory;
})
//factory to download
.factory('downloadService', ['$q', '$timeout', '$window',
        function($q, $timeout, $window) {
            return {
                download: function() {

                    var defer = $q.defer();

                    $timeout(function() {
                            $window.location = '/recruteCapvalue/download';

                        }, 1000)
                        .then(function() {
                            defer.resolve('success');
                        }, function() {
                            defer.reject('error');
                        });
                    return defer.promise;
                }
            };
        }
    ])
.directive('checkImage', function($http) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            attrs.$observe('ngSrc', function(ngSrc) {
                $http.get(ngSrc).success(function(){
                    //console.log('image exist');
                    
                }).error(function(){
                    //console.log('image not exist');
                    element.attr('src', 'images/username.png'); // set default image
                });
            });
        }
    };
})
//    /findOneCandidat/{username}/candidats
												//,dateFactory,$http
.controller('ProfileCtrl',function($scope,$rootScope,candidatService, sweet,$state,$sessionStorage,$localStorage){

	if($sessionStorage.authenticated){	
		
				$rootScope.$storage = $localStorage.$default({
					candidat : $rootScope.$storage.candidat
				});
		
			$scope.updateCandidat=function(){
					
				candidatService.update({
					//$rootScope.$storage.candidat
					  id : $rootScope.$storage.candidat.username,
					  "username": $rootScope.$storage.candidat.username,
					  "nom": $rootScope.$storage.candidat.nom,
					  "prenom": $rootScope.$storage.candidat.prenom,
					  "niveauEtude": $rootScope.$storage.candidat.niveauEtude,
					  "profil": $rootScope.$storage.candidat.profil,
					  "photo" : $rootScope.$storage.candidat.photo,
					  "niveauExperience": $rootScope.$storage.candidat.niveauExperience,
					  "situationActuelle": $rootScope.$storage.candidat.situationActuelle,
					  "adresse": $rootScope.$storage.candidat.adresse,
					  "telephone": $rootScope.$storage.candidat.telephone,
					  "salaire": $rootScope.$storage.candidat.salaire, 
					  "dateDisponibilite": $rootScope.$storage.candidat.dateDisponibilite,
					  "typeSalaire": $rootScope.$storage.candidat.typeSalaire, // tarif journalier
					  "dateNaiss": $rootScope.$storage.candidat.dateNaiss,
					  "nationalite": $rootScope.$storage.candidat.nationalite,  // payer
					  "experiences": $rootScope.$storage.candidat.experiences,
					  "notification": $rootScope.$storage.candidat.notification
					  
					  
					  /*id :$scope.candidat.username,
					  "username":$scope.candidat.username,
					  "nom": $scope.candidat.nom,
					  "prenom": $scope.candidat.prenom,
					  "niveauEtude": $scope.candidat.niveauEtude,
					  "profil": $scope.candidat.profil,
					  "photo" : $scope.candidat.photo,
					  "niveauExperience": $scope.candidat.niveauExperience,
					  "situationActuelle": $scope.candidat.situationActuelle,
					  "adresse": $scope.candidat.adresse,
					  "telephone": $scope.candidat.telephone,
					  "salaire": $scope.candidat.salaire, 
					  "dateDisponibilite": dateFactory.currentDate($scope.dayDisp,$scope.monthDisp,$scope.yearDisp),
					  "typeSalaire": $scope.candidat.typeSalaire, // tarif journalier
					  "dateNaiss": dateFactory.currentDate($scope.dayNaiss,$scope.monthNaiss,$scope.yearNaiss),
					  "nationalite": $scope.candidat.nationalite,  // payer
					  "experiences": $scope.candidat.experiences,
					  "notification": $scope.candidat.notification*/
					  
				}).$promise.then(function(){
		            sweet.show(' profile Modifié !', 'Votre profile à été bien modifié.', 'success'); //success
					$state.go('app.profile');
				},function (){
					 sweet.show(' Erreur !', 'Votre profile n\'a pas été modifié.', 'error'); //success
					 $state.go('app.editprofil');
				});	
			}
	}else{
		$state.go('app.home');
	}

})

.controller('ViewProfileCtrl',function($scope,$resource,$state,downloadService,$sessionStorage,$rootScope,$localStorage){ //getCandidat,
	/*if($sessionStorage.authenticated && $sessionStorage.connectedRole =='ROLE_CANDIDAT'){*/
	$rootScope.$storage = $localStorage.$default({
		candidat : {}
	});	
	
			$scope.download = function() {
			        downloadService.download()
			            .then(function(success) {
			                console.log('success : ' + success);
			            }, function(error) {
			                console.log('error : ' + error);
			            });
			    };
			
			$scope.loadpage=function (){
				         $state.reload();
			   			};	
			  	console.log('$rootScope.$storage.username :'+$rootScope.$storage.username)
			   			
		   $resource("http://localhost:8080/candidats/findOneCandidat/"+$rootScope.$storage.username+"/candidats",{username : $rootScope.$storage.username}).get()
				    .$promise.then(function(data){
				    	console.log('candt');
				    	console.log(data);
				    	$rootScope.$storage.candidat=data;
				    	
				    });
				    
		    
		     // $scope.profilImg='/uploadFile/'+$scope.candidat.photo ? '/uploadFile/'+$scope.candidat.photo : 'images/username.png';
		    //console.log('image profile :'+$scope.profilImg);
		    //$scope.networkIcon = network.actual ? 'assets/img/networkicons/' + network.actual + '.png' : 'assets/img/networkicons/default.png';
				
	/*}else{
		$state.go('app.home');
	};*/
	
})
										// ,$scope,$resource,$http,$sessionStorage,
.controller('EditProfileCtrl',function($rootScope,$state,dateFactory,$localStorage){ 
	  
		//console.log("hhhhhhhhhhhhhhh");
		//console.log($rootScope.$storage.candidat);
			/*$resource("http://localhost:8080/candidats/findOneCandidat/"+$rootScope.$storage.username+"/candidats",{username : $rootScope.$storage.username}).get()
			    .$promise.then(function(data){
			    	console.log('candt');
			    	$rootScope.$storage.candidat=data;
			    });*/
			
			$rootScope.$storage = $localStorage.$default({
				//cand : $rootScope.$storage.candidat ,
			   // candidat : $rootScope.$storage.candidat ,
				nivs : dateFactory.niveau(),
				situations : dateFactory.situation(),
		        experiences : dateFactory.experience()
			
			});
	
				/*$rootScope.nivs =dateFactory.niveau();
				$rootScope.situations=dateFactory.situation();
				$rootScope.experiences=dateFactory.experience();*/
	
	/*if($sessionStorage.authenticated && $sessionStorage.connectedRole =='ROLE_CANDIDAT'){

		              $scope.candidat=getCandidat.candidat;	               

		              $scope.dayNaiss=getCandidat.dayNaiss;
		              $scope.monthNaiss=getCandidat.monthNaiss;
		              $scope.yearNaiss= getCandidat.yearNaiss ;
		              
		              $scope.dayDisp= getCandidat.dayDisp;
					  $scope.monthDisp= getCandidat.monthDisp;
					  $scope.yearDisp= getCandidat.yearDisp;
				  
					  
					  $scope.naissDays=dateFactory.days();
			          $scope.naissMonths=dateFactory.months;
			          $scope.naissYears=dateFactory.years();
		          
			          $scope.dispDays=dateFactory.days();
			          $scope.dispMonths=dateFactory.months;
			          $scope.dispYears=dateFactory.years();
			          $scope.nivs =dateFactory.niveau();
			          $scope.situations=dateFactory.situation();
			          $scope.experiences=dateFactory.experience();
				
	}else{
		$state.go('app.home');
	};
	*/
});


