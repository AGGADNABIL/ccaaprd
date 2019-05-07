'use strict';

app
.factory('offreService',['$resource', function ($resource) {
    return $resource('http://localhost:8080/offre/offres/:id',  {}, {
        query: { method: "GET", isArray: true },
        create: { method: "POST"},
        get: { method: "GET"},
        remove: { method: "DELETE" },
       update: { method: "PUT"}
    });

}])


				.directive(
				'slider',
				function() {
					return {
						restrict : 'C',
						compile : function(element, attr) {
							// wrap tag
							var contents = element.html();
							element
									.html('<div class="slideable_contenu" style="margin:0 !important; padding:0 !important;" >'
											+ contents + '</div>');
									
							return function postLink(scope, element, attrs) {
								// default properties
								attrs.duration = (!attrs.duration) ? '0.1s'
										: attrs.duration;
								attrs.easing = (!attrs.easing) ? 'ease-in-out'
										: attrs.easing;
								// elem.css('height', winHeight - headerHeight + 'px');
								
								element.css({
    								'overflow' : 'hidden',
									'height' : '0px',
									'transitionProperty' : 'height',
									'transitionDuration' : attrs.duration,
									'transitionTimingFunction' : attrs.easing
								});
							};
						}
					};
				})
				.directive(
				'slideToggler',
				function() {
					return {
						restrict : 'A',
						link : function(scope, element, attrs) {
							var target = document
									.querySelector(attrs.slideToggler);
							attrs.expanded = false;
							element
									.bind(
											'click',
											function() {
												
												var content = target.querySelector('.slideable_contenu');
												if (!attrs.expanded) {
													content.style.border = '1px solid rgba(0,0,0,0)';
													var y = content.clientHeight;
													content.style.border = 0;
													document.querySelector('.slider').style.overflow='visible';
													target.style.height = y
															+ 'px';
												} else {
													target.style.height = '0px';
													//document.querySelector('.slider').style.overflow='hidden';
													//document.querySelector('#boxer').style.height= '0px';
													
												}
												attrs.expanded = !attrs.expanded;
											});
							
								
						}
					}
				})	
.controller('offresDetailCtrl',function(offresFactory,$stateParams,$scope,$http){
	
	offresFactory.getOffresDetail($stateParams.id).then(function (offre) {
	    $scope.offre = offre.data;
	    
	  }, function (msg) {
	    alert(msg);
	  });
	  
})

.controller('ViewAllCondidatures',function($scope,$http,$state,$sessionStorage,$resource){
	if($sessionStorage.authenticated && $sessionStorage.connectedRole =='ROLE_CANDIDAT'){
			  $http.get('http://localhost:8080/user', {
				}).then(function(response) {
						$scope.username=response.data.name;
				});
			  
			$http.get('http://localhost:8080/offre/offres/'+ $scope.username +"/offres").success(function (data) { 
				$scope.offres = data; 
				
		});
	}else{
		$state.go('app.home');
	}
	$scope.nbVueIncrement=function(titreOffre,codeOffre){
		$resource('http://localhost:8080/offre/offres/incrementNbVueOffre/'+codeOffre).get().$promise.then(function(data) {
			titreOffre=titreOffre.replace(/ /g,"-");
    		$state.go('app.job',{titreOffre:titreOffre,codeOffre: codeOffre});
    	});
	};

});