(function() {
    'use strict';

    angular.module('main').config(stateConfig);

    stateConfig.$inject = ['$stateProvider','$urlRouterProvider'];
    function stateConfig($stateProvider,$urlRouterProvider) {

        $stateProvider
        .state('login', {
            url: '/login',
            templateUrl: '/public/login',
            controller: landingController
        }).state('signup', {
            url: '/signup',
            templateUrl: '/public/signup',
            controller: signupController
        }).state('settings', {
            url:'/settings',
            templateUrl: '/protected/settings',
            controller: settingsController
        }).state('users', {
            url: '/users-all',
            templateUrl: '/protected/users-all',
            controller: usersController
        }).state('dashboard', {
            url: '/dashboard',
            templateUrl: '/protected/dashboard',
            controller: dashboardController
        });

        $urlRouterProvider.otherwise('/login');
    }

})();

