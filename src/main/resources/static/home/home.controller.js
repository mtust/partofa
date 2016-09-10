/**
 * Created by tust on 10.09.2016.
 */
(function () {
    'use strict';
alert(199)
    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$http', '$rootScope'];
    function HomeController($http, $rootScope) {
        var vm = this;
        alert(123);
        vm.user = null;
        vm.allUsers = [];
        vm.deleteUser = deleteUser;

        initController();

        function initController() {
            loadCurrentUser();
            loadAllUsers();
        }

        function loadCurrentUser() {
            // UserService.GetByUsername($rootScope.globals.currentUser.username)
            //     .then(function (user) {
            //         vm.user = user;
            //     });
        }

        function loadAllUsers() {
            // UserService.GetAll()
            //     .then(function (users) {
            //         vm.allUsers = users;
            //     });
        }

        function deleteUser(id) {
            // UserService.Delete(id)
            //     .then(function () {
            //         loadAllUsers();
            //     });
        }
    }

})();