'use strict';

/**
 * @ngdoc directive
 * @name capRecruteApp.directive:checkimage
 * @author MAJDOU Aimad
 * @description
 *
 */
app
  .filter('unsafe', function ($sce) {
    return function (val) {
      return $sce.trustAsHtml(val);
    };
  });
