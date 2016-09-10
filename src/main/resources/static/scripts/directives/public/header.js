(function() {
    'use strict';
    angular
        .module('main')
        .directive('header', headerDirective);
    headerDirective.$inject = [];

    function headerDirective() {
        return{
            replace:true,
            restrict:'E',
            templateUrl:'/directives/public/header',
            link:function () {
            }
        };
    }

})();