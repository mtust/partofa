/**
 * Created by tust on 10.09.2016.
 */
(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['$http', '$location', '$rootScope'];
    function RegisterController($http, $location, $rootScope) {
        var vm = this;

        vm.register = register;

        function register() {
            vm.dataLoading = true;
            // UserService.Create(vm.user)
            //     .then(function (response) {
            //         if (response.success) {
            //             FlashService.Success('Registration successful', true);
            //             $location.path('/login');
            //         } else {
            //             FlashService.Error(response.message);
            //             vm.dataLoading = false;
            //         }
            //     });
        }
    }

})();
