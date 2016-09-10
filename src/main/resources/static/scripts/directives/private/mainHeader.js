(function() {
    'use strict';
    angular
        .module('main')
        .directive('mainHeader', mainHeader);
    mainHeader.$inject = ['$location','requestService','$state'];

    function mainHeader($location,requestService,$state) {
        return{
            replace:false,
            restrict:'E',
            templateUrl:'/directives/private/main-header',
            link:function (scope) {
                scope.getClass = function (path) {
                    if ($location.$$path.indexOf(path) > -1) {
                        return "selected-menu";
                    } else {
                        return "not-selected-menu";
                    }
                };
                scope.logout = function () {
                    requestService.private.logout(
                        function (result) {
                            $state.go('login');
                            console.log(result);
                        },
                        function (error) {
                            toastr.error(error.message,'Error');
                            console.log(error);
                    });

                };
                scope.leedsPage = function () {
                    $state.go('leeds');
                };
                scope.clientsPage = function () {
                    $state.go('clients');
                };
            }
        };
    }
})();