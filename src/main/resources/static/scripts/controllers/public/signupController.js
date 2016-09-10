angular
    .module('main')
    .controller('signupController', signupController);
signupController.$inject = ['$scope','requestService','$timeout'];

function signupController($scope,requestService,$timeout) {
    $scope.$on('$viewContentLoaded', function () {


    });
}