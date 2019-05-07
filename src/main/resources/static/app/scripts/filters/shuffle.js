'use strict';

/**
 * @ngdoc directive
 * @name capRecruteApp.directive:shuffle
 * @author MAJDOU Aimad
 * @description
 * this filter is used to shuffle the order of an array of objects
 */
app
  .filter('shuffle', function () {
    var shuffledArr = [],
      shuffledLength = 0;
    return function(arr) {
      var o = arr.slice(0, arr.length);
      if (shuffledLength == arr.length) return shuffledArr;
      for(var j, x, i = o.length; i; j = parseInt(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
      shuffledArr = o;
      shuffledLength = o.length;
      return o;
    };
  });
