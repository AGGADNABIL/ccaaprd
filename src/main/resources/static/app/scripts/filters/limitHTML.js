'use strict';

/**
 * @ngdoc directive
 * @name capRecruteApp.directive:limitHTML
 * @author MAJDOU Aimad
 * @description
 * this filter is used to limit the displayed text (html format)
 * @param text : the html to be limited
 * @param limit : the number of characters to be displayed
 */
app
  .filter('limitHtml', function () {
    return function (text, limit) {

      var changedString = String(text).replace(/<[^>]+>/gm, '');
      var length = changedString.length;

      return changedString.length > limit ? changedString.substr(0, limit - 1) : changedString;
    }
  });
