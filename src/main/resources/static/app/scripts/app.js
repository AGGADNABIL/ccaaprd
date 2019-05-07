'use strict';

/**
 * @ngdoc overview
 * @name capRecruteApp
 * @description # capRecruteApp
 *
 * Main module of the application.
 */

/* jshint -W079 */

var app = angular.module(
		'capRecruteApp',
		[ 'ngAnimate', 'ngCookies', 'ngResource', 'ngSanitize', 'ngTouch',
				'ngMessages', 'ui.router', 'angular-loading-bar', 'lazyModel',
				'oc.lazyLoad', 'ui.select', 'infinite-scroll', 'hSweetAlert','simditor',
				'ceibo.components.table.export','ngTable', 'ngStorage', 'ui.bootstrap', 'textAngular', 'ui.tinymce', 'ngBootstrap','angularFileUpload','HiggidyCarousel','angular.filter'])
	
		.run(['$rootScope',
				'$state',
				'$stateParams',
				function($rootScope, $state, $stateParams) { //,$location,$sessionStorage
					
					$rootScope.$state = $state;
					$rootScope.$stateParams = $stateParams;
					
					/*var postLogInRoute;
				    $rootScope.$on('$routeChangeStart', function (event, nextRoute, currentRoute) {
				    //if login required and you're logged out, capture the current path
				        if (nextRoute.loginRequired && !$sessionStorage.authenticated) {
				          postLogInRoute = $location.path();
				          $location.path('/login').replace();
				        } else if (postLogInRoute && $sessionStorage.authenticated) {
				    //once logged in, redirect to the last route and reset it
				          $location.path(postLogInRoute).replace();
				          postLogInRoute = null;
				        }
				    });*/
				    
					$rootScope.$on('$stateChangeSuccess', function(event,
							toState) {

						event.targetScope.$watch('$viewContentLoaded',
								function() {

									angular.element('html, body, #content')
											.animate({
												scrollTop : 0
											}, 200);

									setTimeout(function() {
										angular.element('#wrap').css(
												'visibility', 'visible');

										if (!angular.element('.dropdown')
												.hasClass('open')) {
											angular.element('.dropdown').find(
													'>ul').slideUp();
										}
									}, 200);
								});
						$rootScope.containerClass = toState.containerClass;
					});
				}])

.config([ 'uiSelectConfig', function(uiSelectConfig) {
	uiSelectConfig.theme = 'bootstrap';
} ])

.config(
		['$urlMatcherFactoryProvider', '$stateProvider', '$urlRouterProvider','$locationProvider',
				function($urlMatcherFactory,$stateProvider, $urlRouterProvider,$locationProvider) {		
			     
				//,$locationProvider
		        //	$locationProvider.html5Mode(true);
			    //$urlMatcherFactoryProvider.caseInsensitive(true);
			$urlMatcherFactory.caseInsensitive(true);
		    $urlMatcherFactory.strictMode(false);
			        
					$urlRouterProvider.otherwise('/app/home');
					$stateProvider
					 
					.state('app', {
						abstract : true,
						url : '/app',
                        controller : 'mainCtrl',
						templateUrl : 'views/tmpl/app.html'
					})
					// dashboard
					.state('app.home', {
						url : '/home',
						controller : 'home',
						controllerAs : 'controller',
						templateUrl : 'views/tmpl/home.html',
						resolve : {
							plugins : [ '$ocLazyLoad', function($ocLazyLoad) {
								return $ocLazyLoad.load([]);
							} ],
							referenceAll:function($http){
								return $http.get("http://localhost:8080/references").then(function(response){
									return response.data;
								});
							}
						}
					})
					// Login Page
					.state('app.login', {
						url : '/login',
						controller : 'LoginCtrl',
						controllerAs : 'controller',
						templateUrl : 'views/tmpl/login.html'
					})
					// change password Page
					.state('app.pass', {
						url : '/changPassword',
						controller : 'LoginCtrl',
						templateUrl : 'views/tmpl/password.html'
					})
					//Forgot Password
					.state('app.forgotPassword', {
						url : '/forgotPassword',
						controller : 'ForgotPasswordCtrl',
						templateUrl : 'views/tmpl/getPassword.html'
					})

					//Forgot Password
					.state('app.formGetPassword', {
						url : '/formGetPassword',
						controller : 'FormGetPasswordCtrl',
						templateUrl : 'views/tmpl/formGetPassword.html'
					})
					// Register Page
					.state('app.register', {
						url : '/register',
						controller : 'RegisterCtrl',
						templateUrl : 'views/tmpl/register.html'
					})
					// Resume Page
					.state('app.resume', {
						url : '/resume',
						templateUrl : 'views/tmpl/resume.html'
					})
					// Privacy Policy Page
					.state('app.privacy-policy', {
						url : '/privacy-policy',
						templateUrl : 'views/tmpl/privacy-policy.html'
					})
					// Our Terms Page
					.state('app.our-terms', {
						url : '/our-terms',
						templateUrl : 'views/tmpl/our-terms.html'
					})
					// Profile Page
					.state('app.profile', {
						url : '/profile',
						templateUrl : 'views/tmpl/profile.html',
						controller: 'ViewProfileCtrl'
						/*	,
						resolve :{
							getCandidat : function ($http){
								console.log("storage :"+$rootScope.$storage.username);
							  return $http.get("http://localhost:8080/candidats/findOneCandidat/"+$rootScope.$storage.username+"/candidats")
							  .then(function (response) {
								  console.log("ladata"); 
								  console.log(response.data)
								  return  response.data;
					                });
							}
						}*/
					})
					// Applies Page
					.state('app.applies', {
						url : '/applies',
						templateUrl : 'views/tmpl/applies.html'
					})

					//app.dtailsOffre
					.state('app.dtailsOffre', {
						url : '/details/:id',
						templateUrl : 'views/tmpl/offre-details.html',
						//controller : 'postulerController'
					})


					.state('app.postuler', {
						url : '/postuler',
						templateUrl : 'views/tmpl/Postuler.html',
						controller : 'PostulerController',
						params : {
							'codeOffre' : null
						}
					})
					.state('app.postulerResult', {
						url : '/postulerResult',
						templateUrl : 'views/tmpl/PostulerResult.html',

					})

					// Jobs Page
					.state('app.jobsList', {
						url : '/jobs-list?typeContrat&competence&ville',
						controller : 'OffresController',
						templateUrl : 'views/tmpl/jobs.html'
					})
					// Job details Page
					.state('app.job', {
						url : '/job/:titreOffre/:codeOffre',
						params : {
							'codeOffre' : null
						},
						templateUrl : 'views/tmpl/job-details.html'
					})

					// Edit profile Page
					.state('app.editprofil', {
						url : '/editProfil',
						templateUrl : 'views/tmpl/Editprofil.html',
						controller: 'EditProfileCtrl'
						/*	,
						resolve : {
							getCandidat : function ($http,$filter,$rootScope,$localStorage){
								
								console.log("storage2 :"+$rootScope.$storage.username);
								console.log("http://localhost:8080/candidats/findOneCandidat/"+$rootScope.$storage.username+"/candidats");
							  return $http.get("http://localhost:8080/candidats/findOneCandidat/"+$rootScope.$storage.username+"/candidats").then(function (response) {
								  return  {
									         candidat:   response.data,
									         
									         dayNaiss:   $filter('date')(response.data.dateNaiss,'d'),
								             monthNaiss: $filter('date')(response.data.dateNaiss,'M'),
								             yearNaiss:  $filter('date')(response.data.dateNaiss,'yyyy'),

											  dayDisp:   $filter('date')(response.data.dateDisponibilite,'d'),
											  monthDisp: $filter('date')(response.data.dateDisponibilite,'M'),
											  yearDisp:  $filter('date')(response.data.dateDisponibilite,'yyyy')
											  //$scope.dayDisp,$scope.monthDisp,$scope.yearDisp
								          };
					                });
							}
						}*/

					})

          //Admin Pages
            .state('app.admin', {
              url: '/admin',
              template: '<div ui-view></div>'
            })
            //Admin Jobs Pages
            .state('app.admin.jobs', {
              url: '/jobs',
              template: '<div ui-view></div>'
            })
            //Admin Jobs List app.admin.competences.list
            .state('app.admin.jobs.list', {
              url: '/list',
              controller: 'JobsAdminCtrl',
              templateUrl: 'views/tmpl/admin/jobs/list.html'
            })
             //Admin Jobs List
            .state('app.admin.jobs.JobsAdminOffreCtrl', {
              url: '/listOffrePostuler',
              params : {
					'candidatsPostuler' : null
				},
              controller: 'JobsAdminOffreMyCtrl',
              templateUrl: 'views/tmpl/admin/jobs/listOffrePostuler.html'
            })
            //Admin Jobs New
            .state('app.admin.jobs.new', {
              url: '/new',
              controller: 'JobsAdminCtrl',
              templateUrl: 'views/tmpl/admin/jobs/new.html'
            })
            //app.admin.users
            .state('app.admin.users', {
              url: '/users',
              controller: 'UsersCtrl',
              templateUrl: 'views/tmpl/admin/user/profiles.html'
            })
            //Admin Jobs Edit
            .state('app.admin.jobs.edit', {
              url: '/edit',
              controller: 'JobsAdminCtrl',
              params: {
                'offre':{}
              },
              templateUrl: 'views/tmpl/admin/jobs/edit.html'
            })
            //Admin Competences Pages
            .state('app.admin.competences', {
              url: '/competences',
              template: '<div ui-view></div>'
            })
            //Admin Competences List
            .state('app.admin.competences.list', {
              url: '/list',
              controller: 'CompetencesAdminCtrl',
              templateUrl: 'views/tmpl/admin/competences/list.html'
            })
            .state('app.admin.reference',{
						url : '/reference',
						controller : 'PrincipalCtrl',
						templateUrl:'views/tmpl/admin/references.html'
						
					})

					// Candidates Page

					// Candidate details Page
					.state('app.candidate', {
						url : '/candidate/:id',
						templateUrl : 'views/tmpl/candidate-details.html'
					})
					.state('app.candidatesList', {
						url : '/candidates',
						controller : 'CandidatCtrl',


					params : {
								'candidatsByOffre' : null
							},
						templateUrl : 'views/tmpl/candidates.html'
					})
					.state('app.candidatsPostuler', {
						url : '/candidatsPostuler',
						controller : 'CandidatPostulerCtrl',
					params : {
								'candidatsPostuler' : null
							},
						templateUrl : 'views/tmpl/candidatsPostuler.html'
					})
					.state('app.candidatesConsulter', {
						url : '/candidat/consulter/:id/',
						controller : 'CandidatConsulterCtrl',
						templateUrl : 'views/tmpl/candidate-details.html'
					})
					.state('app.result', {
						controller : 'resultController',
						url : '/result',
						templateUrl : 'views/tmpl/result.html',
						params : {
							'offres' : {}
						}
					})
					.state('app.prodETInfras',{
						url: '/Production-et-Infrastructure',
						templateUrl : 'views/tmpl/prodETInfras.html'
					})
					.state('app.ConseilExpertise',{
						url: '/Conseil-et-Expertise',
						templateUrl : 'views/tmpl/ConseilExpertise.html'
					})
					.state('app.quiSommesNous', {
						url: '/Qui-Sommes-Nous?',
						templateUrl : 'views/tmpl/quiSommeNous.html'
					})
					// Contact Page
					.state('app.contact', {
						url : '/contact',
						templateUrl : 'views/tmpl/contact.html'
					});
					//$locationProvider.html5Mode(true);
				} ]);
app.filter('unsafe', function($sce) {
	return function(val) {
		return $sce.trustAsHtml(val);
	};
});
app.filter('limitHtml', function() {
	return function(text, limit) {

		var changedString = String(text).replace(/<[^>]+>/gm, '');
		var length = changedString.length;

		return changedString.length > limit ? changedString
				.substr(0, limit - 1) : changedString;
	}
});
