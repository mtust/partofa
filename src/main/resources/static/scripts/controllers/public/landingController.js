angular
    .module('main')
    .controller('landingController', landingController);
landingController.$inject = ['$scope','requestService','$rootScope','$state'];

function landingController($scope,requestService,$rootScope,$state) {
    $scope.$on('$viewContentLoaded', function () {
        $scope.loginPage = true;
    });

    $scope.login = function (email,password) {
        var requestParameters = {
            email:email,
            password:password
        };
        requestService.public.login(requestParameters,
            function (result) {
                console.log(result);
                $rootScope.user = result;
                $rootScope.user.id = result.id;
//                $rootScope.user.id = result.userAccess.id;
                $rootScope.getUser();
                $state.go('dashboard');

            },
            function (error) {
                console.log(error);
                toastr.error(error.message,'Error');
            });
    };

}