'use strict';

/**
 * @ngdoc directive
 * @name capRecruteApp.directive:trim
 * @author MAJDOU Aimad
 * @description
 * This filter is used to trim a text
 * @param value : the text to be [trimed]
 */
app
  .filter('trim', function () {
    return function (value) {
      if (!angular.isString(value)) {
        return value;
      }
      return value.replace(/^\s+|\s+$/g, ''); // we can use .trim, but it's not going to work in IE<9
    };
  });
