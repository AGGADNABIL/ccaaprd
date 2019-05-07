'use strict';

app

.filter('removeAccents',function (){
	
		 return  function (source) {
		  
		        var accent = [
		            /[\300-\306]/g, /[\340-\346]/g, // A, a
		            /[\310-\313]/g, /[\350-\353]/g, // E, e
		            /[\314-\317]/g, /[\354-\357]/g, // I, i
		            /[\322-\330]/g, /[\362-\370]/g, // O, o
		            /[\331-\334]/g, /[\371-\374]/g, // U, u
		            /[\321]/g, /[\361]/g, // N, n
		            /[\307]/g, /[\347]/g, // C, c
		        ],
		        noaccent = ['A','a','E','e','I','i','O','o','U','u','N','n','C','c'];
	
		        for (var i = 0; i < accent.length; i++){
		            source = source.replace(accent[i], noaccent[i]);
		        }
	
		        return source;
			  	};
		   
	})
		.factory(
				'priorityFactory',
				function() {
					var fact = {
						priority : function() {
							var array = [ 'Première', 'Deuxième', 'Troisième',
									'Quatrième', 'Cinquième', 'Sixième' ];
							return array;
						}
					}
					return fact;
				})
		.factory('nosInfosService', [ '$resource', function($resource) {
			return $resource('http://localhost:8080/nosInfos/:id', {}, {
				query : {
					method : "GET",
					isArray : true
				},
				create : {
					method : "POST"
				},
				get : {
					method : "GET"
				},
				remove : {
					method : "DELETE"
				},
				update : {
					method : "PUT",
					params : {
						id : '@id'
					}
				}
			});

		} ])
		.factory('nosMetiersService', [ '$resource', function($resource) {
			return $resource('http://localhost:8080/nosMetier/:id', {}, {
				query : {
					method : "GET",
					isArray : true
				},
				create : {
					method : "POST"
				},
				get : {
					method : "GET"
				},
				remove : {
					method : "DELETE"
				},
				update : {
					method : "PUT",
					params : {
						id : '@id'
					}
				}
			});
		} ])
		
		// ---------------start slide
		
.directive(
				'slideable',
				function() {
					return {
						restrict : 'C',
						compile : function(element, attr) {
							// wrap tag
							var contents = element.html();
							element
									.html('<div class="slideable_contenu" style="margin:0 !important; padding:0 !important" >'
											+ contents + '</div>');

							return function postLink(scope, element, attrs) {
								// default properties
								attrs.duration = (!attrs.duration) ? '1s'
										: attrs.duration;
								attrs.easing = (!attrs.easing) ? 'ease-in-out'
										: attrs.easing;
								
								
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
				'slideToggle',
				function() {
					return {
						restrict : 'A',
						link : function(scope, element, attrs) {
							var target = document
									.querySelector(attrs.slideToggle);
							attrs.expanded = false;
							element
									.bind(
											'click',
											function() {
												
												var content = target.querySelector('.slideable_contenu');
												if (!attrs.expanded) {
													content.style.border = '1px solid rgba(0,0,0,0)';
													var y = content.clientHeight;
													target.style.height = y
															+ 'px';
												} else {
													target.style.height = '0px';
													
												}
												attrs.expanded = !attrs.expanded;
											});
						}
					}
				})	
				
		// ------------------end slide
	
				.directive('verifyImage', function($http) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            attrs.$observe('ngSrc', function(ngSrc) {
                $http.get(ngSrc).success(function(){
                    //console.log('image exist');
                    
                }).error(function(){
                    //console.log('image not exist');
            element.attr('src', 'images/vide.png'); // set default image
                });
            });
        }
    };
})
.directive('hiddenImage', function($http) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            attrs.$observe('ngSrc', function(ngSrc) {
                $http.get(ngSrc).success(function(){
                    //console.log('image exist');
                    
                }).error(function(){
                    //console.log('image not exist');
            //element.attr('src', 'images/vide.png'); // set default image
                	angular.element('#element').css('height', '100px');
                });
            });
        }
    };
})
.provider('$dynMetierState', function($stateProvider){
    this.$get = function($state){
        return {
            addState: function(metier) {
            	var title=metier.titre.replace(/[\s]/g, '-');
            	console.log('title' +title);
            
                $stateProvider.state('app.metier'+metier.id, {
                    url : '/' + title,
                    controller : 'MetierSup',
					templateUrl : 'views/tmpl/metier.html',
					params : {
						'metier' : metier
					}
                });
            }
        }
    }
})
.controller('referenceCtrl',function($scope,sweet,$resource,$state,ngTableParams,$filter,$sessionStorage) { // ,referenceAll
	if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') {
					$resource("http://localhost:8080/references").query().$promise.then(function(data) {
										console.log("references :");
										console.log(data);
										
										$scope.res = data;
										$scope.tableParams = new ngTableParams({
											page : 1,
											count : $scope.res.length
										}, {
											counts : [],
											total : 1,
											getData : function($defer, params) {
												$scope.refer = params.sorting() ? $filter(
														'orderBy')($scope.res,
														params.orderBy()) : $scope.result;
												$scope.refer = params.filter() ? $filter(
														'filter')($scope.refer,
														params.filter()) : $scope.refer;
												$scope.refer = $scope.refer.slice(0, 20);
												$defer.resolve($scope.refer);
											}
										});

										$scope.getMor = function() {
											$scope.refer = $scope.res.slice(0,20);
										}
									
									});
							
							$scope.validate=function(){
								sweet
								.show(
										' Ajout !',
										'Opération effectuée avec succès.',
										'success');
								$state.reload();
							};
							
							$scope.invalidate=function(){
								sweet
								.show(
										' Ajout !',
										'Opération effectuée n\'a pas été effectuée.',
										'error');
							};
							$scope.supprimer=function (id){
								sweet.show({
									title : 'Confirmer',
									text : 'Voulez-vous vraiment supprimer ?',
									type : 'warning',
									showCancelButton : true,
									confirmButtonColor : '#DD6B55',
									confirmButtonText : 'Oui, supprimez-le !',
									closeOnConfirm : false
								}, function() {
									//$scope.refer.splice(id-1,1);
									$resource('http://localhost:8080/references/'+id).remove().$promise.then(function() {
										angular.element(document.querySelector('#ent_' + id)).remove();
										sweet.show('Supprimé !','Suppression réussite.', 'success');
										//$scope.refer.
									});
								});
								//$state.reload();
							};
						  };	
						})
						.directive("showNotif", function() {
								    return {
								        restrict: "A",
								        
								        link: function(scope, elem, attrs) {
								            //On click
								            $(elem).on('change',function() {
								                console.log('changed value :'+scope.imgId);
								                $.ajax({
								        			url : "/recruteCapvalue/uploadMetier", //    /recruteCapvalue
								        			type : "POST",
								        			data : new FormData($("#upload-frm-infos-"+scope.imgId)[0]),
								        			enctype : 'multipart/form-data',
								        			processData : false,
								        			contentType : false,
								        			cache : false,
								        			success : function() {
								        				console.log("succes");
								        			},
								        			error : function() {
								        				console.log("echec");
								        			}
								        		}); 
								            });    
								        }
								    }
								})
								.directive("showNotifmetier", function() {
								    return {
								        restrict: "A",
								        
								        link: function(scope, elem, attrs) {
								            //On click
								            $(elem).on('change',function() {
								                console.log('changed value :'+scope.imgId);
								                $.ajax({
								        			url : "/recruteCapvalue/uploadMetier", //    /recruteCapvalue
								        			type : "POST",			
								        			data : new FormData($("#upload-frm-metier-"+scope.imgId)[0]),
								        			enctype : 'multipart/form-data',
								        			processData : false,
								        			contentType : false,
								        			cache : false,
								        			success : function() {
								        				console.log("succes");
								        			},
								        			error : function() {
								        				console.log("echec");
								        			}
								        		}); 
								            });    
								        }
								    }
								})
								
	.controller('PrincipalCtrl',function ($scope,$state,$sessionStorage, $filter, $resource, ngTableParams, sweet,priorityFactory,nosInfosService, nosMetiersService,$dynMetierState){
	
	 if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') {
	
		$scope.tinymceOptions = {
    			toolbar : "imageupload  | insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image jbimages",
    	        setup: function(editor) {
    	            var inp = $('<input id="tinymce-uploader" type="file" name="pic" accept="image/*" style="display:none">');
    	            $(editor.getElement()).parent().append(inp);

    	            inp.on("change",function(){
    	                var input = inp.get(0);
    	                var file = input.files[0];
    	                var fr = new FileReader();
    	                fr.onload = function() {
    	                    var img = new Image();
    	                    img.src = fr.result;
    	                    editor.insertContent('<img src="'+img.src+'"/>');
    	                    inp.val('');
    	                }
    	                fr.readAsDataURL(file);
    	            });

    	            editor.addButton( 'imageupload', {
    	                text:"Image",
    	                icon: false,
    	                onclick: function(e) {
    	                    inp.trigger('click');
    	                }
    	            });
    	        }
            };
    	
		$scope.setFile=function (file){
			$scope.imgId =file.id.split('-').pop();
			//console.log("Principal Ctrl :"+file.id);
			//console.log("Principal Ctrl :"+$scope.imgId);
			
		};
		
		/*$scope.isVisible=false;
		$scope.isNotHidden=false;
		
		$scope.showHide=function (){
			 $scope.isVisible = $scope.isVisible ? false : true;
		}
		
		$scope.showHidden=function (id){
			
			 $scope.metId=id;
			 $scope.isNotHidden = $scope.isNotHidden ? false : true;
		}*/
		
		$scope.priorities = priorityFactory.priority();
		
		$scope.enregistrer = function() {
			nosMetiersService.create({
				'titre' : $scope.NosMetier.titre,
				'contenu' : $scope.NosMetier.contenu,
				'contenuShow': $scope.NosMetier.contenuShow ,
				'priorite' : $scope.NosMetier.priorite
			}).$promise
					.then(function() {

								sweet
										.show(
												' Ajout !',
												'Opération effectuée avec succès.',
												'success');
								$state.reload();
							},
							function() {
								sweet
										.show(
												' Ajout !',
												'Opération n\'a pas été effectuée avec succés.',
												'error');
							});
		};

		nosMetiersService.query().$promise.then(function(data) {

			$scope.resul = data;
			// console.log(data);
			$scope.tableParam = new ngTableParams({
				page : 1,
				count : $scope.resul.length
			}, {
				counts : [],
				total : 1,
				getData : function($defer, params) {
					$scope.dat = params.sorting() ? $filter(
							'orderBy')($scope.resul,
							params.orderBy()) : $scope.resul;
					$scope.dat = params.filter() ? $filter(
							'filter')($scope.dat, params.filter())
							: $scope.dat;
					$scope.dat = $scope.dat.slice(0, 20);
					$defer.resolve($scope.dat);
				}

			});

			$scope.getMoreDat = function() {
				$scope.dat = $scope.resul.slice(0,
						$scope.dat.length + 20);
			}
		});

		
		   
	     //-----edit line in table nosInfos
       $scope.editingInfos = {};
		
		angular.forEach($scope.datainf, function(item,key){
			$scope.editingInfos[key] = false;
	      });
		//modifyInfos
		$scope.modifyInfos = function(tableData){
	        $scope.editingInfos[tableData.id] = true;
	    };

	    /*$scope.updateInfos = function(tableData){
	        $scope.editingInfos[tableData.id] = false;
	    };*/
	    
		//------------------
	   
     //-----edit line in table nosMetier
		$scope.editingData = {};
		
		angular.forEach($scope.dat, function(item,key){
			$scope.editingData[key] = false;
	      });
		
	    $scope.modify = function(tableData){
	        $scope.editingData[tableData.id] = true;
	    };
	    
	    /*$scope.update = function(tableData){
	        $scope.editingData[tableData.id] = false;
	    };*/
	    
		//------------------
		
		$scope.remove = function(id) {
			sweet.show({
				title : 'Confirmer',
				text : 'Voulez-vous vraiment supprimer ?',
				type : 'warning',
				showCancelButton : true,
				confirmButtonColor : '#DD6B55',
				confirmButtonText : 'Oui, supprimez-le !',
				closeOnConfirm : false
			}, function() {
				$resource('http://localhost:8080/nosMetier/' + id)
						.remove().$promise.then(function() {
					sweet.show('Supprimé !',
							'Suppression réussite.', 'success');
					angular.element(
							document.querySelector('#entry_' + id))
							.remove();
				});
			});
		};

		$scope.modifier = function(nosMetier) {
			  $scope.editingData[nosMetier.id] = false;
			nosMetiersService.update({
				id : nosMetier.id,
				"titre" : nosMetier.titre,
				"contenu" : nosMetier.contenu,
				"contenuShow" : nosMetier.contenuShow,
				"image" : nosMetier.image,
				"priorite" : nosMetier.priorite

			}).$promise.then(function() {
				sweet.show(' Modification !',
						'Opération effectuée avec succés.',
						'success'); // success
				$state.reload();
			}, function() {
				sweet.show(' Erreur !', 'Opération échouée.',
						'error'); // success
				// $state.go('app.editprofil');
			});
		};
		
		$scope.updated = function(nosInfos) {
			
			$scope.editingInfos[nosInfos.id] = false;
			nosInfosService.update({
				id : nosInfos.id,
				"titre" : nosInfos.titre,
				"contenu" : nosInfos.contenu,
				"contenuShow" : nosInfos.contenuShow,
				"image" : nosInfos.image,
				"priorite" : nosInfos.priorite
			}).$promise.then(function() {
				sweet.show(' Modification !',
						'Opération effectuée avec succés.',
						'success'); // success
				$state.reload();
			}, function() {
				sweet.show(' Erreur !', 'Opération échouée.',
						'error'); // success
				// $state.go('app.editprofil');
			});
		};
		
		$scope.priorits = priorityFactory.priority();
		// console.log("pppoooooo");
		// console.log($scope.priorits);

		$scope.valider = function() {
			nosInfosService.create({
				'titre' : $scope.nosInfos.titre,
				'contenu' : $scope.nosInfos.contenu,
				'contenuShow' : $scope.nosInfos.contenuShow,
				'priorite' : $scope.nosInfos.priorite

			}).$promise
					.then(
							function() {
								sweet
										.show(
												' Ajout !',
												'Opération effectuée avec succès.',
												'success');
											$state.reload();
							},
							function() {
								sweet
										.show(
												' Ajout !',
												'Opération n\'a pas été effectuée.',
												'error');
							});
		};

		nosInfosService.query().$promise.then(function(data) {
			// console.log('loading');
			$scope.result = data;
			// console.log(data);
			$scope.tableParams = new ngTableParams({
				page : 1,
				count : $scope.result.length
			}, {
				counts : [],
				total : 1,
				getData : function($defer, params) {
					$scope.datainf = params.sorting() ? $filter(
							'orderBy')($scope.result,
							params.orderBy()) : $scope.result;
					$scope.datainf = params.filter() ? $filter(
							'filter')($scope.datainf,
							params.filter()) : $scope.datainf;
					$scope.datainf = $scope.datainf.slice(0, 20);
					$defer.resolve($scope.datainf);
				}

			});

			$scope.getMoreData = function() {
				$scope.datainf = $scope.result.slice(0,
						$scope.datainf.length + 20);
			}

		});

		$scope.removed = function(id) {
			sweet.show({
				title : 'Confirmer',
				text : 'Voulez-vous vraiment supprimer ?',
				type : 'warning',
				showCancelButton : true,
				confirmButtonColor : '#DD6B55',
				confirmButtonText : 'Oui, supprimez-le !',
				closeOnConfirm : false
			}, function() {
				$resource('http://localhost:8080/nosInfos/' + id)
						.remove().$promise.then(function() {
					sweet.show('Supprimé !',
							'Suppression réussite.', 'success');
					angular.element(
							document.querySelector('#enty_' + id))
							.remove();
				});
			});
		};
		
	} else {
      $state.go("app.home");
    }
})
.controller('MetierSup',function($scope,$stateParams ,$resource,$dynMetierState){
	 $scope.dynMetier = function (nosMetier){
			$dynMetierState.addState(nosMetier);
	    };
	    
		var rs=$resource('http://localhost:8080/orderedMetier');
		rs.query({},function(data) {
			$scope.dataAll = data;
			console.log(data);
		});
		
	console.log('$stateParams');
	console.log($stateParams.metier);
	$scope.metiersup=$stateParams.metier;
})
.controller('NosMetierCtrl',function($scope,$state,$sessionStorage, $filter, $resource, ngTableParams, sweet,priorityFactory, nosMetiersService,$dynMetierState,nosInfosService,$dynamicState) {
				
							$scope.priorities = priorityFactory.priority();
							$scope.enregistrer = function() {
								nosMetiersService.create({
									'titre' : $scope.NosMetier.titre,
									'contenu' : $scope.NosMetier.contenu,
									'contenuShow':$scope.NosMetier.contenuShow,
									'priorite' : $scope.NosMetier.priorite
		
								}).$promise
										.then(
												function() {
		
													sweet
															.show(
																	' Ajout !',
																	'Opération effectuée avec succès.',
																	'success');
													$state.reload();
												},
												function() {
													sweet
															.show(
																	' Ajout !',
																	'Opération effectuée n\'a pas été effectuée.',
																	'error');
												});
							};
		
							nosMetiersService.query().$promise.then(function(data) {
		
								$scope.resul = data;
								$scope.tableParam = new ngTableParams({
									page : 1,
									count : $scope.resul.length
								}, {
									counts : [],
									total : 1,
									getData : function($defer, params) {
										$scope.dat = params.sorting() ? $filter(
												'orderBy')($scope.resul,
												params.orderBy()) : $scope.resul;
										$scope.dat = params.filter() ? $filter(
												'filter')($scope.dat, params.filter())
												: $scope.dat;
										$scope.dat = $scope.dat.slice(0, 20);
										$defer.resolve($scope.dat);
									}
		
								});
		
								$scope.getMoreDat = function() {
									$scope.dat = $scope.resul.slice(0,
											$scope.dat.length + 20);
								}
							});
		
							$scope.remove = function(id) {
								sweet.show({
									title : 'Confirmer',
									text : 'Voulez-vous vraiment supprimer ?',
									type : 'warning',
									showCancelButton : true,
									confirmButtonColor : '#DD6B55',
									confirmButtonText : 'Oui, supprimez-le !',
									closeOnConfirm : false
								}, function() {
									$resource('http://localhost:8080/nosMetier/' + id)
											.remove().$promise.then(function() {
										sweet.show('Supprimé !',
												'Suppression réussite.', 'success');
										angular.element(
												document.querySelector('#entry_' + id))
												.remove();
									});
								});
							};
		
							$scope.modifier = function(nosMetier) {
								// console.log("id Metier :"+nosMetier.id)
								nosMetiersService.update({
									id : nosMetier.id,
									"titre" : nosMetier.titre,
									"contenu" : nosMetier.contenu,
									"image" : nosMetier.image,
									"priorite" : nosMetier.priorite
		
								}).$promise.then(function() {
									sweet.show(' Modification !',
											'Opération effectuée avec succés.',
											'success'); // success
								}, function() {
									sweet.show(' Erreur !', 'Opération échouée.',
											'error'); // success
									// $state.go('app.editprofil');
								});
							};
							
			 		
				})
				
.provider('$dynamicState', function($stateProvider){
    this.$get = function($state){
        return {
            addState: function(obj) {
            	var title=obj.titre.replace(/[\s]/g, '-');
            	console.log('title' +title);
            
                $stateProvider.state('app.information'+obj.id, {
                    url : '/' + title,
                    controller : 'InfosSup',
					templateUrl : 'views/tmpl/target.html',
					params : {
						'obj' : obj
					}
                });
            }
        }
    }
})
.controller('InfosSup',function($scope,$stateParams ,$resource,$dynamicState){
	 $scope.dynamic = function (nosInfos){
  	   $dynamicState.addState(nosInfos);
  	  
     };
      var rs=$resource('http://localhost:8080/ordered');
			rs.query({},function(data) {
				$scope.donnees = data;
				
			});
			console.log('$stateParams');
			console.log($scope.u);
			console.log($stateParams.obj);
			$scope.infosup=$stateParams.obj;
			
			
			
	
})
.controller('NosInfosCtrl',function($scope, $filter,$sessionStorage, $resource, ngTableParams, sweet,priorityFactory, nosInfosService,$state ,$dynamicState) {	//,$dynamicState			
	           			
					$scope.updated = function(nosInfos) {
						nosInfosService.update({
							id : nosInfos.id,
							"titre" : nosInfos.titre,
							"contenu" : nosInfos.contenu,
							"contenuShow" : nosInfos.contenuShow,
							"image" : nosInfos.image,
							"priorite" : nosInfos.priorite

						}).$promise.then(function() {
							sweet.show(' Modification !',
									'Opération effectuée avec succés.',
									'success'); // success
							$state.reload();
						}, function() {
							sweet.show(' Erreur !', 'Opération échouée.',
									'error'); // success
							// $state.go('app.editprofil');
						});
					};
					
					$scope.priorits = priorityFactory.priority();
					// console.log("pppoooooo");
					// console.log($scope.priorits);

					$scope.valider = function() {
						nosInfosService.create({
							'titre' : $scope.nosInfos.titre,
							'contenu' : $scope.nosInfos.contenu,
							'contenuShow' : $scope.nosInfos.contenuShow,
							'priorite' : $scope.nosInfos.priorite

						}).$promise
								.then(
										function() {
											sweet
													.show(
															' Ajout !',
															'Opération effectuée avec succès.',
															'success');
														$state.reload();
										},
										function() {
											sweet
													.show(
															' Ajout !',
															'Opération effectuée n\'a pas été effectuée.',
															'error');
										});
					};

					nosInfosService.query().$promise.then(function(data) {
						// console.log('loading');
						$scope.result = data;
						// console.log(data);
						$scope.tableParams = new ngTableParams({
							page : 1,
							count : $scope.result.length
						}, {
							counts : [],
							total : 1,
							getData : function($defer, params) {
								$scope.datainf = params.sorting() ? $filter(
										'orderBy')($scope.result,
										params.orderBy()) : $scope.result;
								$scope.datainf = params.filter() ? $filter(
										'filter')($scope.datainf,
										params.filter()) : $scope.datainf;
								$scope.datainf = $scope.datainf.slice(0, 20);
								$defer.resolve($scope.datainf);
							}

						});

						$scope.getMoreData = function() {
							$scope.datainf = $scope.result.slice(0,
									$scope.datainf.length + 20);
						}

					});

					$scope.removed = function(id) {
						sweet.show({
							title : 'Confirmer',
							text : 'Voulez-vous vraiment supprimer ?',
							type : 'warning',
							showCancelButton : true,
							confirmButtonColor : '#DD6B55',
							confirmButtonText : 'Oui, supprimez-le !',
							closeOnConfirm : false
						}, function() {
							$resource('http://localhost:8080/nosInfos/' + id)
									.remove().$promise.then(function() {
								sweet.show('Supprimé !',
										'Suppression réussite.', 'success');
								angular.element(
										document.querySelector('#enty_' + id))
										.remove();
							});
						});
					};
				});