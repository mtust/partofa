/**
 * Created by tust on 10.09.2016.
 */
(function () {
    'use strict';

    angular
        .module('app')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$location', '$http'];
    function LoginController($location, $http) {
        var vm = this;

        vm.login = login;

        (function initController() {
            // reset login status
            // AuthenticationService.ClearCredentials();
        })();

        function login() {
            // vm.dataLoading = true;
            // AuthenticationService.Login(vm.username, vm.password, function (response) {
            //     if (response.success) {
            //         AuthenticationService.SetCredentials(vm.username, vm.password);
            //         $location.path('/');
            //     } else {
            //         FlashService.Error(response.message);
            //         vm.dataLoading = false;
            //     }
            // });
        };
    }

})();