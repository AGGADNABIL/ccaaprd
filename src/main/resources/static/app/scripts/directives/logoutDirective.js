'use strict';

/**
 * @ngdoc directive
 * @name minovateApp.directive:activateButton
 * @description
 * # activateButton
 */
app
.directive('logout',function($http,$state,$sessionStorage,$rootScope,$localStorage) {   
	//alert('test'+$sessionStorage.username);
	return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('click', function() {
            	scope.$state.go('app.login');
            	$sessionStorage.authenticated = false;
            	$sessionStorage.username=null;
				$sessionStorage.sessionid=null;
				$sessionStorage.connectedRole='ANONYMOS';
				$sessionStorage.connectedRole='ANONYMOS';
				$sessionStorage.authenticated=false;
				$rootScope.authenticated = false;
				$localStorage.$reset();
				scope.$state.reload();
				//$sessionStorage.empty();
				
				//$localStorage.clearAll();
				//$window.location.replace($window.location.toString().split('#')[0]);
				
				
				/*$http.post('http://localhost:8080/logout', {}).finally(function() {
            		$rootScope.authenticated = false;
            		$sessionStorage.authenticated=false;
            		
				});*/
           
            });
        }
    };
})