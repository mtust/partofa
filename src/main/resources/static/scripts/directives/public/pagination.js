(function() {
    'use strict';
    angular
        .module('main')
        .directive('pagination', paginationDirective);
    paginationDirective.$inject = [];

    function paginationDirective() {
        return{
            replace:true,
            restrict:'E',
            templateUrl:'/directives/public/pagination',
            link:function () {

            }
        };
    }

})();