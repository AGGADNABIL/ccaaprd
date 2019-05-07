'use strict';

app
.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          
          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
 }])

 .service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
       var fd = new FormData();
       fd.append('file', file);
    
       $http.post(uploadUrl, fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined}
       })
    
       .success(function(){
    	   console.log('reussite');
       })
    
       .error(function(){
    	   console.log('echec');
       });
    }
 }])
 
 .controller('AppController', ['$scope','$http', function($scope,$http){
	     
	      $http.get('http://localhost:8080/candidats/findOneCandidat/rachid@leeching.net/candidats').then(function(response){
	    	  console.log(response.data);
	    	  $scope.candidat=response.data;
	          $scope.pic="fileUpload/"+$scope.candidat.photo;
	      });
 }]);
    
    
//.factory('candidatureService',['$resource',
//  function ($resource) {
//    return $resource('http://localhost:8080/candidature/candidats/:id', {}, {
//      update: { method: "PUT"},
//      query: {method: "GET",  isArray: true},
//      get: {method: "GET", isArray:false}
//    });
//
//  }])
//.factory('fileUploadService', function ($resource) {
//    return $resource('http://localhost:8080/fileUpload/:id', { id: "@id" }, {
//      create: {
//        method: "POST",
//        transformRequest: angular.identity,
//        headers: { 'Content-Type': undefined }
//      }
//    });
//  })
//  
//.controller('AppController', function ($scope, $filter, $window, $state, $parse, fileUploadService) {
//
//
////  if ($window.sessionStorage.getItem('username') == null)
////    $state.go("candidature.pre");
////  else {
////    $scope.currentYear = new Date().getFullYear();
////    candidatureService.get({id: $window.sessionStorage.getItem('username')}).$promise.then(function (result) {
////      if (result.etat != "validee")
////        $state.go("candidature.pre");
////    });
////  }
//
//  $scope.submitscholarshipForm = function () {
//    //Create a new FormData which will be used to send files to the server
//    var formData = new FormData();
//    //append files to be uploaded to the FormData
//   // formData.append($scope.scholarshipContractName, $scope.scholarshipContractUpload);
//    formData.append($scope.registrationCertificateName, $scope.registrationCertificateUpload);
//    //formData.append($scope.languageCertificateName, $scope.languageCertificateUpload);
//  };
//
//  /**
//   * Preview Image before uploading
//   * @param element : Image
//   * @param name : scope variable as a string which will be assigned to selected image
//   */
//  $scope.setFile = function (element, name) {
//    //Converts Angular expression into a function, in our case we convert
//    //the string we passed as a variable
//    //and then assign a value to the parsed string
//    $parse(name + 'Upload').assign($scope, element.files[0]);
//    $parse(name + 'Name').assign($scope, $window.sessionStorage.getItem('username') + "_" + name + "." + element.files[0].name.split('.').pop());
//    var reader = new FileReader();
//
//    reader.onload = function (event) {
//      $parse(name).assign($scope, event.target.result);
//      $scope.$apply();
//    };
//    try {
//      reader.readAsDataURL(element.files[0]);
//    } catch (err) {
//      $parse(name).assign($scope, null);
//      $scope.$apply();
//    }
//  };
//
//});

//angular
//
//
//    .module('app', ['angularFileUpload'])


//    app.controller('AppController', ['$scope', 'FileUploader', function($scope, FileUploader) {
//        var uploader = $scope.uploader = new FileUploader({
//            url: 'upload.php'
//        });
//
//        // FILTERS
//
//        uploader.filters.push({
//            name: 'customFilter',
//            fn: function(item /*{File|FileLikeObject}*/, options) {
//                return this.queue.length < 10;
//            }
//        });
//
//        // CALLBACKS
//
//        uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
//            console.info('onWhenAddingFileFailed', item, filter, options);
//        };
//        uploader.onAfterAddingFile = function(fileItem) {
//            console.info('onAfterAddingFile', fileItem);
//        };
//        uploader.onAfterAddingAll = function(addedFileItems) {
//            console.info('onAfterAddingAll', addedFileItems);
//        };
//        uploader.onBeforeUploadItem = function(item) {
//            console.info('onBeforeUploadItem', item);
//        };
//        uploader.onProgressItem = function(fileItem, progress) {
//            console.info('onProgressItem', fileItem, progress);
//        };
//        uploader.onProgressAll = function(progress) {
//            console.info('onProgressAll', progress);
//        };
//        uploader.onSuccessItem = function(fileItem, response, status, headers) {
//            console.info('onSuccessItem', fileItem, response, status, headers);
//        };
//        uploader.onErrorItem = function(fileItem, response, status, headers) {
//            console.info('onErrorItem', fileItem, response, status, headers);
//        };
//        uploader.onCancelItem = function(fileItem, response, status, headers) {
//            console.info('onCancelItem', fileItem, response, status, headers);
//        };
//        uploader.onCompleteItem = function(fileItem, response, status, headers) {
//            console.info('onCompleteItem', fileItem, response, status, headers);
//        };
//        uploader.onCompleteAll = function() {
//            console.info('onCompleteAll');
//        };
//
//        console.info('uploader', uploader);
//    }]);