'use strict';

/**
 * @ngdoc function
 * @name capRecruteApp.controller:mainCtrl

 * @description
 * # mainCtrl
 *
 */
app
.filter('unique', function() {
	   return function(collection, keyname) {
	      var output = [], 
	          keys = [];

	      angular.forEach(collection, function(item) {
	          var key = item[keyname];
	          if(keys.indexOf(key) === -1) {
	              keys.push(key);
	              output.push(item);
	          }
	      });
	      return output;
	   };
	})
	.filter('uniqueVille', function() {
	   return function(collection, keyname) {
	      var output = [], 
	          keys = [];

	      angular.forEach(collection, function(item) {
	          var key = item[keyname];
	          if(keys.indexOf(key) === -1) {
	              keys.push(key);
	              output.push(item);
	          }
	      });
	      return output;
	   };
	})
 .factory('contratsFactory',function ($http){
	 var  factory ={
			 getcontrats: function() {
			        return $http.get('http://localhost:8080/offre/contrats')
			          .then(function(response) {
			        	    console.log(response);
			            return response;
					          }, function(error) {
					            return 'There was an error getting data';
					          });
			      }
	 }
	 
	 return factory;
 })
  .controller('mainCtrl', function ($scope, $resource, $state,sweet) {
	 // $scope.listvilles=[];
	  //$scope.offres =[];
    $resource("http://localhost:8080/villes").query().$promise.then(function(data) {
      $scope.villes = shuffleArray(data);
    });
    
   $resource("http://localhost:8080/contrats").query().$promise.then(function(data) {
         $scope.contrats = shuffleArray(data);
    });
    
    $resource("http://localhost:8080/competencelst",{}).query().$promise.then(function(data) {
    	console.log("data");
    	console.log(data);
      $scope.competences =data;
      
      
    });

    $resource("http://localhost:8080/offre-competence-not").query().$promise.then(function(data) {
    	//console.log("offre list");
	   console.log("offre logger :");
    	$scope.listCompetences =data;
    	
    	//console.log($scope.offres);
    	
      });  
     
   $resource("http://localhost:8080/offre/offres/contrat").query().$promise.then(function(data) {
    	//console.log("offre list");
	   console.log("offre logger :")
    	$scope.offres =data;
    	
    	//console.log($scope.offres);
    	
      });  
     
   $resource("http://localhost:8080/offre/offres/ville").query().$promise.then(function(data) {
   	console.log("ville list");
   
   	$scope.listvilles = data;
   	
   	
     }); 
   
  /* $resource("http://localhost:8080/offre-competence").query().$promise.then(function(data) {
   	$scope.offreCompetences = data;
   	console.log('offreCompetences');
   	console.log($scope.offreCompetences);
   	
     });
   */
   
   /* $resource("http://localhost:8080/offre/offres").query().$promise.then(function(data) {
    	$scope.listvilles = data;
    	console.log('listvilles');
    	console.log($scope.listvilles);
    	
    	
      });*/
     
    $scope.search = function(typeContrat, ville, competence){
    	console.log('search :');
       	console.log(typeContrat);
       	console.log(ville);
       	console.log(competence);
      $state.go('app.jobsList',{typeContrat:typeContrat,ville:ville,competence:competence});
    };

    var shuffleArray = function(array) {
      var m = array.length, t, i;

      // While there remain elements to shuffle
      while (m) {
        // Pick a remaining element…
        i = Math.floor(Math.random() * m--);

        // And swap it with the current element.
        t = array[m];
        array[m] = array[i];
        array[i] = t;
      }

      return array;
    }

    /** subscribe ****/
    $scope.subscribeError=false;
    $scope.subscribeMessage="";
    $scope.subscribe=function(){
    	 $resource('http://localhost:8080/subscribes/subscribes').save({
    		 "email":this.email,
    		 "nom":this.name

    	 }).$promise.then(function() {
    		 
    		 $scope.subscribeError=false;
    		 $scope.subscribeMessage ="Bien enregistrer";

	        sweet.show(' votre abonnement a été enregistrée avec succès!', 'Nos Alertes d\'emplois seront envoyés à votre adresse e-mail.', 'success');
            $state.reload();

	      }).catch(function(error){
              if(error.data == "USERNAME_CONFLICT"){
    
            	  $scope.subscribeError=true;
            	  $scope.subscribeMessage ="Cette adresse email existe déjà";
            	  sweet.show(' Inscription !', 'Cette adresse email existe déjà.', 'error');
              }
            }).finally(function(){

            });
    }

  });
