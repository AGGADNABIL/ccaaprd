'use strict';

/**
 * @ngdoc function
 * @name capRecruteApp.controller:jobsListAdminCtrl
 * @author MAJDOU Aimad
 * @description
 * # CompetencesAdminCtrl
 *
 */
app


  .controller('CompetencesAdminCtrl', function ($scope, $resource, $sessionStorage, $state) {
    if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') {
      $scope.Api = "http://localhost:8080/group-competence/";
      $scope.ApiCompetence = "http://localhost:8080/competences/";
      //$scope.ApiOffre = "http://localhost:8080/offres/";

    } else {
      $state.go("app.home");
    }

    $scope.availableList = ['Java','PHP','AngularJS','C#','.Net','HTML5','Jenkins','Node.js','Socket.io'];

    $scope.selectedList = [];

  })
  //CompetencesListAdminCtrl
  .controller('CompetencesListAdminCtrl', function ($scope, $filter, ngTableParams, $resource, sweet, $state, $sessionStorage) {
	  if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') {

      $scope.sortType = 'name'; // set the default sort type
      $scope.sortReverse = false;  // set the default sort order

      $scope.changeEtat = function (entry, $event, etat) {
        $resource($scope.ApiCompetence + 'changeEtat/' + entry.codeCompetence + '/' + etat, null, {
          'update': {method: 'PUT'}
        }).update().$promise.then(function () {
          entry.activated = !entry.activated;
        });
      };

      $scope.remove = function (id) {
        sweet.show({
          title: 'Confirmer',
          text: 'Voulez-vous vraiment supprimer cette groupe ?',
          type: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#DD6B55',
          confirmButtonText: 'Oui, supprimez-le !',
          closeOnConfirm: false
        }, function () {
          $resource($scope.Api + id).remove().$promise.then(function () {
            sweet.show('Supprimé !', 'L\'enregistrement a été supprimé.', 'success');
            angular.element(document.querySelector('#entry_' + id)).remove();
          });
        });
      };

      $scope.removeCompetence = function (id) {
        sweet.show({
          title: 'Confirmer',
          text: 'Voulez-vous vraiment supprimer cette compétence ?',
          type: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#DD6B55',
          confirmButtonText: 'Oui, supprimez-le !',
          closeOnConfirm: false
        }, function () {
          $resource($scope.ApiCompetence + id).remove().$promise.then(function () {
            sweet.show('Supprimé !', 'L\'enregistrement a été supprimé.', 'success');
            angular.element(document.querySelector('#competence_' + id)).remove();
          });
        });
      };

      $scope.addCompetToGrp = function (code) {
          sweet.show({
            title: 'Nouvelle compétence :',
            text: 'Titre :',
            type: 'input',
            showCancelButton: true,
            closeOnConfirm: false,
            animation: 'slide-from-top',
            showLoaderOnConfirm: true
          }, function(inputValue){
            if (inputValue === false){
              return false;
            }
            if (inputValue === '') {
              sweet.showInputError('Vous devez écrire quelque chose!');
              return false;
            }

            $resource($scope.ApiCompetence + code).save(
              {
                "titre": inputValue,
                "activated": true
              } 
            ).$promise.then(function () {
            	$state.reload();
            })
              .finally(function(){
               // entry.titre = inputValue;
                sweet.show('Titre modifié!', '', 'success');
              });

          });
        };
     
      $scope.update = function (entry) {
        sweet.show({
          title: 'Modification',
          text: 'Nouveau titre :',
          type: 'input',
          showCancelButton: true,
          closeOnConfirm: false,
          animation: 'slide-from-top',
          showLoaderOnConfirm: true
        }, function(inputValue){
          if (inputValue === false){
            return false;
          }
          if (inputValue === '') {
            sweet.showInputError('Vous devez écrire quelque chose!');
            return false;
          }

          $resource($scope.Api + entry.codeGroupCompetence, null, {
            'update': {method: 'PUT'}
          }).update(
            {
              "titre": inputValue
            }
          ).$promise.then(function () {})
            .finally(function(){
              entry.titre = inputValue;
              sweet.show('Titre modifié!', '', 'success');
            });

        });


      };


      $scope.updateCompetence = function (competence) {
        sweet.show({
          title: 'Modification',
          text: 'Nouveau titre :',
          type: 'input',
          showCancelButton: true,
          closeOnConfirm: false,
          animation: 'slide-from-top',
          showLoaderOnConfirm: true
        }, function(inputValue){
          if (inputValue === false){
            return false;
          }
          if (inputValue === '') {
            sweet.showInputError('Vous devez écrire quelque chose!');
            return false;
          }

          $resource($scope.ApiCompetence + competence.codeCompetence, null, {
            'update': {method: 'PUT'}
          }).update(
            {
              "titre": inputValue
            }
          ).$promise.then(function () {})
            .finally(function(){
              competence.titre = inputValue;
              sweet.show('Titre modifié!', '', 'success');
          });

        });


      };

      $scope.nbPostulant = function (id) {
        $resource('http://localhost:8080/candidats/candidatsPostulersByOffre/' + id).query().$promise.then(function (data) {
          $scope.candidatsByOffre = data;
          //console.log($scope.candidats);
          $state.go('app.candidatesList', {candidatsByOffre: data});
        });
        //$state.go('app.admin.jobs.edit',{offre: id});
      };

      $resource($scope.Api).query().$promise
         .then(function (data) {
        
          console.log('loading');
          $scope.result = data;
          console.log(data);

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

  });
