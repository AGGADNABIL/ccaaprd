'use strict';

/**
 * @ngdoc function
 * @name minovateApp.controller:TablesDatatablesCtrl
 * @description
 * # TablesDatatablesCtrl
 * Controller of the minovateApp
 */
app

  .controller('fileUploadCtrl', function () {
    let vm = this;
    vm.uploadFile = function(file){
      let fd = new FormData();
      fd.append('file', file);
      $http.post('http://localhost:8080/fileUpload', fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined}
        })
        .success(function(){
        })
        .error(function(){
        });
    }

  });


