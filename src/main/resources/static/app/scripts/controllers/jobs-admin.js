'use strict';

app

  .factory('offreService', ['$resource', function ($resource) {
    return $resource('http://localhost:8080/offre/offres', {}, {
      query: {
        method: "GET",
        isArray: true
      },
      create: {
        method: "POST"
      },
      get: {
        method: "GET"
      },
      remove: {
        method: "DELETE"
      },
      update: {
        method: "PUT"
      }
    });

  }])
  .factory('adminFatory',function(){
	  var fact={
			  missions:function (){
				  return ['1 mois','2 mois','3 mois','3 mois renouvelable','6 mois','6 mois reouvelable','12 mois'];
			  },
			  
			  experiences : function(){
				  return ['1 an','2 ans','3 ans','4 ans','5 ans','5 ans et plus'];
			  } 
			  
	  }
	  return fact;
  })
.controller('TodoListController', function($scope,$resource,sweet, $sessionStorage) {
	//if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') {
    $scope.todos = [{}];
        $scope.addTodo = function() {
        	if($scope.todoText){
        		$scope.todos.push({"titre": $scope.todoText , "activated": true}); 	
        	}
        	$scope.todoText = '';
	    };  
	    $scope.saveGroupe = function (){
	    	var j=$scope.competences.slice(-1)[0].id;
	    	angular.forEach($scope.todos, function(competence) {
	    		  j=j+1;
	    		 //console.log('val :'+competence.titre);
	    		if(competence.titre){
	    			$scope.competences.push({
		    			id : j ,
			    		titre : competence.titre ,
			    		group :  $scope.textGroup
		            });
	    		}
	    	});
	    	 $scope.todos.splice(0,1);
	    	 $resource('http://localhost:8080/group-competence').save({
	    		  "titre": $scope.textGroup ,
	    		  "competences" : $scope.todos
	   	      }).$promise.then(function (data){
	   	    	  console.log('ajout effectueé ');
	   	    	$scope.textGroup='';
	   	    	$scope.todos=[{}];
	   	    	sweet
				    .show(
						' Ajout !',
						'Opération effectuée avec succès.',
						'success');
	          });
	    };
	    
	    $scope.addevnt = function($event){
	        $event.preventDefault();
	        $scope.todos.push({
	        	"titre" : $scope.todoText,
	        	"activated": true
	        });
	      };
	//}
  })
  .controller('JobsAdminCtrl', function ($scope, $resource, $sessionStorage, $state) {
    if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') {
    	  
      $scope.Api = "http://localhost:8080/offre";
      $scope.villesApi = "http://localhost:8080/villes";
      $scope.contratsApi = "http://localhost:8080/contrats";
      $scope.groupCompetencesApi = "http://localhost:8080/group-competence";
      $scope.competencesApi = "http://localhost:8080/competences";
      $scope.offreCompetencesApi = "http://localhost:8080/offre-competence";

      $resource($scope.competencesApi).query().$promise.then(function (data) {
          $scope.competences = data;
        });
      
      $resource($scope.villesApi).query().$promise.then(function (data) {
        $scope.villes = data;
      });

      $resource($scope.contratsApi).query().$promise.then(function (data) {
        $scope.contrats = data;
      });

      $resource($scope.groupCompetencesApi).query().$promise.then(function (data) {
        $scope.competences = [];
        data.forEach(function(group){
          group.competences.forEach(function(competence){
            $scope.competences.push({
              id : competence.codeCompetence,
              titre : competence.titre,
              group : group.titre
            })
          });
        });
 // console.log($scope.competences);
      });

    } else {
      $state.go("app.home");
    }

  })
  
  .controller('JobsListAdminMyCtrl', function ($scope, $filter, ngTableParams, $resource, sweet, $state, $sessionStorage,$rootScope) {
    if ($sessionStorage.authenticated && $sessionStorage.connectedRole == 'ROLE_ADMIN') {
      $scope.sortType = 'name'; // set the default sort type
      $scope.sortReverse = false;  // set the default sort order
    
      $scope.changeEtat = function (entry, $event) {
        $resource($scope.Api + '/changeEtatOffre/' + entry.codeOffre + '/' + !entry.etat, null, {
          'update': {method: 'PUT'}
        }).update().$promise.then(function () {
          entry.etat = !entry.etat;
        });
      };

      $scope.supprimer=function(id){
    	  
    	  $resource($scope.Api + '/offres/' + id).remove().$promise.then(function () {
              console.log('suppression effectuée');
            
            },function (){
            	 console.log('suppression echouer');
            });
      };
      $scope.remove = function (id) {
        sweet.show({
          title: 'Confirmer',
          text: 'Voulez-vous vraiment supprimer cette offre ?',
          type: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#DD6B55',
          confirmButtonText: 'Oui, supprimez-le !',
          closeOnConfirm: false
        }, function () {
          $resource($scope.Api + '/offres/' + id).remove().$promise.then(function () {
            sweet.show('Supprimé !', 'L\'offre a été supprimé.', 'success');
            angular.element(document.querySelector('#entry_' + id)).remove();
          });
        });
      };

      $scope.update = function (id) {
    	  $resource("http://localhost:8080/offre-competences/" + id).query().$promise.then(function (data) {  
    		  $rootScope.comptces=data;
        	  console.log($rootScope.comptces);
          });
    	  
        $resource($scope.Api + '/offres/' + id).get().$promise.then(function (data) {
          $rootScope.villeSelected={'codeVille' : data.ville.codeVille,
        		     				'nomVille'  : data.ville.nomVille }; 
          console.log('ville entiere :');
          console.log($rootScope.villeSelected);
          $state.go('app.admin.jobs.edit', {offre: data});
        });
        //$state.go('app.admin.jobs.edit',{offre: id});
      };

      $scope.nbPostulant = function (id) {
        $resource('http://localhost:8080/candidats/candidatsPostulersByOffre/' + id).query().$promise.then(function (data) {
          $scope.candidatsByOffre = data;
          //console.log($scope.candidats);
          $state.go('app.candidatesList', {candidatsByOffre: data});
        });
        //$state.go('app.admin.jobs.edit',{offre: id});
      };

      $resource($scope.Api + '/offres/').query().$promise
        .then(function (data) {
        	console.log('loading');
            $scope.result = data;
        	// start SelectAll 
            
    	 

          $scope.tableParams = new ngTableParams({
            page: 1,
            count: $scope.result.length
          }, {
            counts: [],
            total: 1,
            getData: function ($defer, params) {
              $scope.data = params.sorting() ? $filter('orderBy')($scope.result, params.orderBy()) : $scope.result;
              $scope.data = params.filter() ? $filter('filter')($scope.data, params.filter()) : $scope.data;
              $scope.data = $scope.data.slice(0, 20);
              $defer.resolve($scope.data);
            }

          });

          $scope.getMoreData = function () {
            $scope.data = $scope.result.slice(0, $scope.data.length + 20);
          }


        });
        /*.catch(function () {
        })
        .finally(function () {
          console.log('Finished');
        });*/
    } else {
      $state.go("app.home");
    };

  })
  .controller('AddComptence',function ($scope,$resource,sweet,$state){
	  $scope.choices = [{}];
	  
	  $scope.add = function($event){
	        $event.preventDefault();
	        $scope.choices.push({});
	      };
	      
	  $scope.register=function () {
    	  console.log('ajouter encore');
    	  console.log($scope.choices);
    	  $resource('http://localhost:8080/group-competence').save({
    		  "titre": $scope.titre,
    		  "competences" : $scope.choices
   	      }).$promise.then(function (data) {
   	    	sweet
			.show(
					' Ajout !',
					'Opération effectuée avec succès.',
					'success');
   	          $state.reload();
          });
      };
  })
  .controller('NewJobAdminCtrl', function ($scope, $filter, ngTableParams, $resource, sweet, $state,
		  $sessionStorage,adminFatory) {
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
    	                icon: true,
    	                onclick: function(e) {
    	                    inp.trigger('click');
    	                }
    	            });
    	        }

    			
            };
    	
    	
    	$scope.niveauExperiences=adminFatory.experiences();
    	$scope.dureeMissions=adminFatory.missions();
      
      
      $scope.choices = [{}];
      $scope.villes =[{}];
      
       
      $resource($scope.villesApi).query().$promise.then(function (data) {
          $scope.villes = data;
        });
      
     //$scope.competence = [{}];
     //-------------------------------------------- 
      
      /*$scope.setSelected = function(selectedObj) {
         $scope.idSelectedVote = selectedObj.codeVille;
         console.log("is select :"+selectedObj.codeVille+' : '+selectedObj.nomVille);
         $scope.nameVille= $scope.savedVille.nomVille ? $scope.savedVille.nomVille : selectedObj.nomVille ;
         return selectedObj.nomVille;
      };*/
       /*$scope.addTitle=function(){
    	   console.log('tttt :'+$scope.mytitre);
       };*/
      
      $scope.idSelectedVote = null;
      $scope.setSelected = function(selectedObj) {
          $scope.idSelectedVote = selectedObj.codeVille;
          console.log("is select :"+selectedObj.codeVille +" : "+selectedObj.nomVille);
          $scope.nameVille=selectedObj.nomVille;
     
       };
       
     var objet={};
      $scope.addVille=function(str){
    	     if(str){
    	    	 $resource('http://localhost:8080/villes').save({
    			        "nomVille" : str
    		      		}).$promise.then(function (data) {
    		      			$scope.savedVille=data;
    				      		   $scope.villes.push({
    				      			  'codeVille': data.codeVille,
    				         		  'nomVille' : data.nomVille
    				         	  });
    				      		 objet=$scope.savedVille;
    		      			});
    	     }
    	  
    	     
   //--------------------------------------------   
    	  
    	 /* $resource('http://localhost:8080/villes').save({
					        "nomVille" : str
				      		}).$promise.then(function (data) {
				      		     $scope.savedVille=data;
				      		     console.log('$scope.savedVille');
				      		     console.log($scope.savedVille.codeVille);
				      		     console.log($scope.savedVille);
						      		   $scope.villes.push({
						      			  'codeVille': $scope.savedVille.codeVille ,
						         		  'nomVille' : str
						         	  });
						      		   */
						      		
						      		/* $scope.idSelectedVote=$scope.savedVille.codeVille; 
						      		 console.log("new $scope.idSelectedVote :"+$scope.idSelectedVote);
						      		 objet=$scope.savedVille;
						      		 */
						      		 /*console.log("objet");
						      		 console.log(objet);
						      		 console.log("new $scope.idSelectedVote :"+$scope.idSelectedVote);*/
						   /*   		 
				      			});
    	  $scope.nameVille='';*/	  
      };
      
      $scope.add = function($event){
        $event.preventDefault();
        $scope.choices.push({});
      };
      
      $scope.register=function () {
    	  console.log('ajouter encore');
    	  console.log($scope.choices);
    	  $resource('http://localhost:8080/group-competence').save({
    		  "titre": $scope.titre,
    		  "competences" : $scope.choices
   	      }).$promise.then(function (data) {
   	    	sweet
			.show(
					' Ajout !',
					'Opération effectuée avec succès.',
					'success');
   	    	  
   	          $state.reload();
          });;
      };
      
      $scope.save = function () {
    	  //console.log('objet.codeVille :'+objet.codeVille);
        $resource($scope.Api + '/offres/').save({
          "titre": this.offre.titre,
          "dureeMission": this.offre.dureeMission,
          "datePublication": this.datePublication,
          "dateExpiration": this.dateExpiration,
          "poste": this.offre.poste,
          "profilRecherche": this.offre.profilRecherche,
          "dateDebutMission": this.dateDebutMission,
          "etat": true,
          "niveauExperience": this.offre.niveauExperience,
          "ville": {
            "codeVille": (objet.codeVille)? objet.codeVille : $scope.idSelectedVote   // var job = $scope.job ? $scope.job : $scope.jobs //this.offre.ville.codeVille
          },
          "typeContrat": {
            "codeTypeContrat": this.offre.typeContrat.codeTypeContrat
          },
          "offreCompetences" : $scope.choices
        }).$promise.then(function (data) {
          $state.go('^.list');
        });
      };
    } else {
      $state.go('app.home');
    }
  })
  
  .controller('EditJobAdminCtrl', function ($scope,$rootScope, $filter, ngTableParams, $resource, sweet, $state, $stateParams, offreService, $sessionStorage,adminFatory) {
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
    	                text: "Image",
    	                icon: false ,
    	                onclick: function(e) {
    	                    inp.trigger('click');
    	                }
    	            });
    	        }
            };
    
    	$scope.niveauExperiences=adminFatory.experiences();
    	$scope.dureeMissions=adminFatory.missions();
    	//$scope.nameVille=$rootScope.villeSelected;
    	//console.log('name villa :'+$scope.nameVille);
    	 $scope.choices = [{}];
         //$scope.competence = [{}];
    	 $scope.nameVille=$rootScope.villeSelected.nomVille;
    	 $scope.idSelectedVote = $rootScope.villeSelected.codeVille;
         $scope.setSelected = function(selectedObj) {
        	 $scope.nameVille='';
            $scope.idSelectedVote = selectedObj.codeVille;
            console.log("is select :"+selectedObj.codeVille+" : "+selectedObj.nomVille);
            $scope.nameVille=selectedObj.nomVille;
         };
         
         var objet={};
         $scope.addVille=function(str){
       	   if(str){
       		 $resource('http://localhost:8080/villes')
       		    .save({
			          "nomVille" : str
		      		  }).$promise.then(function (data) {
		      			$scope.savedVille=data;
				      		   $scope.villes.push({
				      			  'codeVille': data.codeVille ,
				         		  'nomVille' : data.nomVille
				         	  });
				      		 objet=$scope.savedVille;
		  			}); 
       	   }
         };

      $scope.add = function($event){
        console.log('add comp');
        $event.preventDefault();
        $scope.choices.push({});
      };

      $scope.offre = $stateParams.offre;
      console.log("$scope.offre :"+$scope.offre.titre);
      
      console.log("$stateParams :"+$scope.offre.datePublication);
      console.log("$stateParams :"+$scope.offre.dateExpiration);
      console.log("$stateParams :"+$scope.offre.dateDebutMission);
      
      $scope.dateExpiration=$filter('date')($scope.offre.dateExpiration,'MM/dd/yyyy');
      $scope.datePublication =$filter('date')($scope.offre.datePublication,'MM/dd/yyyy');
      $scope.dateDebutMission =$filter('date')($scope.offre.dateDebutMission,'MM/dd/yyyy');
      
      /*if ($scope.offre != null)$scope.datePublication = new Date($scope.offre.datePublication);
      if ($scope.offre != null)$scope.dateExpiration = new Date($scope.offre.dateExpiration);
      if ($scope.offre != null)$scope.dateDebutMission = new Date($scope.offre.dateDebutMission);
      console.log("debut de la mission :"+$scope.dateDebutMission);
      */
    //$scope.choices = $scope.offre.offreCompetences;
     
      console.log('scope.choices');
     
 
      $scope.save = function () {
    	 console.log('longueur :'+$scope.choices.length);
    	 console.log($scope.choices);
    	 console.log('rootscope');
         console.log($rootScope.comptces);
    
          offreService.update({
          "codeOffre": $scope.offre.codeOffre,
          "titre": this.offre.titre,
          "dureeMission": this.offre.dureeMission,
          "datePublication": this.offre.datePublication,
          "dateExpiration": this.offre.dateExpiration,
          "poste": this.offre.poste,
          "profilRecherche": this.offre.profilRecherche,
          "dateDebutMission": this.offre.dateDebutMission,
          "nombreVue": this.offre.nombreVue,
          "etat": true,
          "niveauExperience": this.offre.niveauExperience,
          "ville": {
            "codeVille": (objet.codeVille)? objet.codeVille : $scope.idSelectedVote //this.offre.ville.codeVille
          },
          "typeContrat": {
            "codeTypeContrat": this.offre.typeContrat.codeTypeContrat
          },
          "offreCompetences" : ($scope.choices.length >0)? $scope.choices : $rootScope.comptces
          
        }).$promise.then(function () {
          $state.go('^.list');
        });
      };
    } else {
      $state.go('app.home');
    }

  });

//select All row
//.directive('rowSelectAll', function (){
//  return {
//	    require: '^stTable',
//	    template: '<input type="checkbox">',
//	    scope: {
//	      all: '=rowSelectAll',
//	      selected: '='
//	    },
//	    link: function (scope, element, attr) {
//
//	      scope.isAllSelected = false;
//
//	      element.bind('click', function (evt) {
//
//	        scope.$apply(function () {
//
//	          scope.all.forEach(function (val) {
//
//	            val.isSelected = scope.isAllSelected;
//
//	          });
//
//	        });
//
//	      });
//
//	      scope.$watchCollection('selected', function(newVal) {
//
//	        var s = newVal.length;
//	        var a = scope.all.length;
//
//	        if ((s == a) && s > 0 && a > 0) {
//
//	          element.find('input').attr('checked', true);
//	          scope.isAllSelected = false;
//
//	        } else {
//
//	          element.find('input').attr('checked', false);
//	          scope.isAllSelected = true;
//
//	        }
//
//	      });
//	    }
//	  };
//})
//.directive('rowSelect', function (){
//  return {
//	    require: '^stTable',
//	    template: '<input type="checkbox">',
//	    scope: {
//	        row: '=rowSelect'
//	    },
//	    link: function (scope, element, attr, ctrl) {
//
//	      element.bind('click', function (evt) {
//
//	        scope.$apply(function () {
//
//	            ctrl.select(scope.row, 'multiple');
//
//	        });
//
//	      });
//
//	      scope.$watch('row.isSelected', function (newValue) {
//
//	        if (newValue === true) {
//
//	            element.parent().addClass('st-selected');
//	            element.find('input').attr('checked', true);
//
//	        } else {
//
//	            element.parent().removeClass('st-selected');
//	            element.find('input').attr('checked', false);
//
//	        }
//	      });
//	    }
//	  };
//})
