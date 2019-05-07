'use strict';

/**
 * @ngdoc directive
 * @name capRecruteApp.directive:checkimage
 * @author MAJDOU Aimad
 * @description
 * This directive is used to check if the current image is a valid one, if not or if it's missing
 * a default image is injected instead
 * the param defaultImage in the scope is readonly param
 * and it's used to specify which image to be injected
 */
app
  .directive('checkImage', function ($http) {
    return {
      restrict: 'A',
      scope: {
        defaultImage: '@'
      },
      link: function (scope, element, attrs) {
        attrs.$observe('ngSrc', function (ngSrc) {
          $http.get(ngSrc).success(function () {
          }).error(function () {
            element.attr('src', '../../images/default/60_60.jpg'); // set default image
          });
        });
      }
    };
  });

