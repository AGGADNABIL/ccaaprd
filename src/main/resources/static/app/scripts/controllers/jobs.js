
'use strict';
app

.factory('offresFactory', function ($http, $q) {
  var factory = {
		  getList: function () {
		      return $http.get('http://localhost:8080/offre/offres')
		        .then(function (response) {

		          return response;

		        }, function (error) {
		          return 'There was an error getting data';
		        });
		    },

		    getOffresDetail: function (id) {
		      return $http.get('http://localhost:8080/offre/offres/' + id)
		        .then(function (response) {
		          return response;

		        }, function (error) {
		          return 'There was an error getting data';
		        });
		    },
		    getVillesList: function() {
		        return $http.get('http://localhost:8080/villes')
		          .then(function(response) {

		            return response;

		          }, function(error) {
		            return 'There was an error getting data';
		          });
		      },
		      getContratsList: function() {
		        return $http.get('http://localhost:8080/contrats')
		          .then(function(response) {

		            return response;

		          }, function(error) {
		            return 'There was an error getting data';
		          });
		      },

		    searchOffres: function (typeContrat, ville, competence) {
		        return $http.get('http://localhost:8080/offresSearch?typeContrat='+typeContrat+'&ville='+ville+'&competence='+competence)

		          .then(function (response) {
		            return response;
		          }, function (error) {
		            return 'There was an error getting data';
		          });
		      }
  };

	return factory;
  })
.controller('OffresController', function ($scope, $sce, offresFactory, $stateParams,$resource,$state,$location) {

  $scope.limit = 3;

  $scope.loadMore = function(){
    /*console.log("limit : " + $scope.limit + ", size : " + $scope.size)*/

    if ($scope.limit < $scope.size) {
      $scope.limit = ($scope.limit + 3 > $scope.size) ? $scope.size : $scope.limit + 3;
    }

    if($scope.limit == $scope.size){
      $scope.showLoadMoreButton = false;
    }
  };
			  $scope.comptenceTags = $resource('http://localhost:8080/offre-competence-not').query().$promise.then(function(data) {
						console.log('offre-competences :');	
						 console.log(data);
						 $scope.comptenceTags=data;
						 console.log(data[0].offre.codeOffre)
				    	});
			  
				 /*return $resource('http://localhost:8080/offre-competences/'+id).query().$promise.then(function(data) {	 
					console.log('offre-competences :'+id);	
					 console.log(data);
			    		return data;
			    	});*/
			 
	$scope.nbVueIncrement=function(titreOffre,codeOffre){
		$resource('http://localhost:8080/offre/offres/incrementNbVueOffre/'+codeOffre).get().$promise.then(function(data) {
			titreOffre=titreOffre.replace(/ /g,"-");
    		$state.go('app.job',{titreOffre:titreOffre,codeOffre: codeOffre});
    	});
	};

  if($location.search().typeContrat || $location.search().ville || $location.search().competence){
    offresFactory.searchOffres($location.search().typeContrat,$location.search().ville,$location.search().competence).then(function (list) {
      $scope.offres = list.data;
      $scope.size = list.data.length;
      $scope.showLoadMoreButton =  $scope.size > 3 ? true : false;
    }, function (msg) {
    });
  }else{
    offresFactory.getList().then(function (list) {
      $scope.offres = list.data;
      $scope.size = list.data.length;
      $scope.showLoadMoreButton =  $scope.size > 3 ? true : false;
    }, function (msg) {
    });
  }

	})

	.controller('offresDetailController', function ($scope,$resource, $state, offresFactory, $stateParams,$sessionStorage,$location,$rootScope) {
		
		$scope.comptenceTags = $resource('http://localhost:8080/offre-competence-not').query().$promise.then(function(data) {
			console.log('offre-competences :');	
			 console.log(data);
			 $scope.comptenceTags=data;
			 //console.log(data[0].offre.codeOffre)
	    	});
		
		offresFactory.getOffresDetail($stateParams.codeOffre).then(function (offre) {
	    $scope.offre = offre.data;
	    
	    $scope.apply=function(){
	    	if($sessionStorage.authenticated && $sessionStorage.connectedRole=='ROLE_CANDIDAT'){
	    		$state.go('app.postuler', {'codeOffre':$scope.offre.codeOffre});
	    	}else{
	    		console.log($location.absUrl());
	    		$rootScope.url=$location.absUrl();
	    		$state.go('app.login');
	    			}
	    		};
	    //alert($scope.offre.codeOffre);
	  }, function (msg) {
	    alert(msg);
	  });

	});

