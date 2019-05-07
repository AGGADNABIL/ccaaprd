'use strict';

/**
 * @ngdoc function
 * @name capRecruteApp.controller:jobsListAdminCtrl
 * @author MAJDOU Aimad
 * @description
 * # jobsListAdminCtrl
 *
 */
app

  .factory('offrePostulerService', ['$resource', function ($resource) {
    return $resource('http://localhost:8080/postulers/getAllOffres/:codeOffre', {}, {
      query: {
        method: "GET",
        isArray: true
       
      },
      create: {
        method: "POST"
      },
      get: {
        method: "GET",
        	 params : {
             	codeOffre : '@codeOffre'
             }
      },
      getReslt: {
          method: "GET",
          isArray: true,
          	 params : {
               	codeOffre : '@codeOffre'
               }
        },
      remove: {
        method: "DELETE"
      },
      update: {
        method: "PUT"
      }
    });

  }])

  .controller('JobsAdminOffreMyCtrl', function ($scope, $resource, $sessionStorage, $state) {
    if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') {
      $scope.Api = "http://localhost:8080/offre";
      $scope.villesApi = "http://localhost:8080/villes";
      $scope.contratsApi = "http://localhost:8080/contrats";

      $resource($scope.villesApi).query().$promise.then(function (data) {
        $scope.villes = data;
      });

      $resource($scope.contratsApi).query().$promise.then(function (data) {
        $scope.contrats = data;
      });
    } else {
      $state.go("app.home");
    }

    $scope.availableList = ['Java','PHP','AngularJS','C#','.Net','HTML5','Jenkins','Node.js','Socket.io'];

    $scope.selectedList = [];

  })

  .controller('JobsListAdminCtrl', function ($rootScope,$scope, $filter, ngTableParams, $resource, sweet, $state, $sessionStorage,$localStorage) {
    if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') {
       
      $scope.sortType = 'name'; // set the default sort type
      $scope.sortReverse = false;  // set the default sort order
      
             //console.log("code Offre :"+$stateParams.id);
            $scope.consulter =  function (id) {
            	
            	var ressource= $resource('http://localhost:8080/postulers/getAllOffres/'+ id,{},
            			{'get' : 
            			  {  method: 'GET',
                             transformResponse: function (data) {return angular.fromJson(data).list},
                             isArray: true //since your list property is an array
            			  }
                    });       	
/*
 ressource.get().$promise.then(function (data){
            		console.log(data);
            		$localStorage.candidatsPostuler=data;
    	            $state.go('app.candidatsPostuler', {candidatsPostuler: data});
            	});
 * */            	
            	 $resource('http://localhost:8080/postulers/getAllOffres/'+ id).query().$promise.then(function (data) {
     	        	$localStorage.candidatsPostuler=data;

     	            $state.go('app.candidatsPostuler', {candidatsPostuler: data});
     	        });
             	
            		
            	} ;
    	        //$state.go('app.admin.jobs.edit',{offre: id});
      $resource($scope.Api + '/offres/').query().$promise
        .then(function (data) {

          console.log('loading');
          $scope.result = data;

          $scope.tableParams = new ngTableParams({
            page: 1,
            count: $scope.result.length
          }, {
            counts: [],
            total: 1,
            getData: function ($defer, params) {
              $scope.data = params.sorting() ? $filter('orderBy')($scope.result, params.orderBy()) : $scope.result;
              $scope.data = params.filter() ? $filter('filter')($scope.data, params.filter()) : $scope.data;
              $scope.data = $scope.data.slice(0, 20);
              $defer.resolve($scope.data);
            }

          });

          $scope.getMoreData = function () {
            $scope.data = $scope.result.slice(0, $scope.data.length + 20);
          };


        })
        .catch(function () {
        })
        .finally(function () {
          console.log('Finished');
        });
    } else {
      $state.go("app.home");
    }

  })



