'use strict';
app
  .factory('postulerServ', ['$resource', function ($resource) {
    return $resource('http://localhost:8080/postulers/postulers/:id', {}, {
      query: {method: "GET", isArray: true},
      create: {method: "POST"},
      get: {method: "GET"},
      remove: {method: "DELETE"},
      update: {method: "PUT"}
    });
  }])
  .controller('PostulerController', function ($scope, $http, postulerServ,sweet, $state, $stateParams, fileUploadService, $parse, $sessionStorage) {
    var postuler = {};

    $scope.postuler = {};

    $scope.setFile = function (element, name) {
      $parse(name + 'Upload').assign($scope, element.files[0]);
      $parse(name + 'Name').assign($scope, $sessionStorage.username + "_" + name + "_" + Math.round(new Date().getTime() / 1000) + "." + element.files[0].name.split('.').pop());
    };

    $scope.valider = function () {
      var fd = new FormData();
      fd.append($scope.cvName, $scope.cvUpload);
      fd.append($scope.lettreName, $scope.lettreUpload);

      fileUploadService.create({}, fd).$promise.then(function (res) {
        postulerServ.create({
          'fichierCV': $scope.cvName,
          'fichierLettreMotivation': $scope.lettreName,
          'pretentionSalarial': $scope.postuler.pretentionSalarial,
          'offre': {
            'codeOffre': $stateParams.codeOffre
          }
        }).$promise.then(function (){
        	sweet.show(' Ajout !', 'Opération effectuée avec succès.', 'success');
        	$state.go('app.postulerResult');
        },function (error){
        	sweet.show(' Ajout !', 'Opération non effectuée .', 'error');
        });
        
        console.log('sending');
      }).catch(function (err) {
        console.log('Error uploading files');
      }).finally(function () {
        console.log('finished');
      });
    }
  })

  .factory('fileUploadService', function ($resource) {
    return $resource('http://localhost:8080/fileUpload/:id', {id: "@id"}, {
      create: {
        method: "POST",
        transformRequest: angular.identity,
        headers: {'Content-Type': undefined}
      }
    });
  })

  .directive('fileModel', function ($parse) {
    return {
      restrict: 'A',
      link: function (scope, element, attrs) {
        var model = $parse(attrs.fileModel);
        var modelSetter = model.assign;

        element.bind('change', function () {
          scope.$apply(function () {
            modelSetter(scope, element[0].files[0]);
          });
        });
      }
    };
  })

  .directive('validfile', function validFile() {

    var validFormats = ['jpg', 'pdf', 'word'];
    return {
      require: 'ngModel',
      link: function (scope, elem, attrs, ctrl) {
        ctrl.$validators.validFile = function () {
          elem.on('change', function () {
            var value = elem.val(),
              ext = value.substring(value.lastIndexOf('.') + 1).toLowerCase();

            return validFormats.indexOf(ext) !== -1;
          });
        };
      }
    };
  });
