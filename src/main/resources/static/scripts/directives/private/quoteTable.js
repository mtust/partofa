(function() {
    'use strict';
    angular
        .module('main')
        .directive('quoteTable', quoteTable);
    quoteTable.$inject = ['requestService','$stateParams'];

    function quoteTable(requestService,$stateParams) {
        return{
            replace:true,
            restrict:'EA',
            scope:{
                quotes:'=',
                update:'&updateInfo'
            },
            templateUrl:'/directives/private/quote-table',
            link:function (scope) {

                scope.addQuote = function () {
                    var requestParam = {
                        id: '',
                        name: scope.quoteName,
                        contactId: scope.quoteContact,
                        siteId: scope.quoteSite
                    };
                    requestService.private.addQuote(requestParam,
                        function (result) {
                            $('#add-new-quote').modal('hide');
                            scope.getAllQuotes();
                            toastr.success('Quote successfully added', 'Success');
                            console.log(result);
                        },
                        function (error) {
                            console.log(error);
                            toastr.error(error.message,'Error');
                        });
                };

                scope.getAllQuotes = function () {
                    var requestParam = {
                        url:'?leadId=' + $stateParams.id
                    };
                    requestService.private.getAllQuotes(requestParam,
                        function (result) {
                            scope.allQuotes = result.content;
                        },
                        function (error) {
                            console.log(error);
                        });
                };

                scope.deleteQuote = function (id) {
                    var requestParam = {
                        id:id
                    };
                    requestService.private.deleteQuote(requestParam,
                        function (result) {
                            scope.getAllQuotes();
                            toastr.success('Quote successfully deleted','Success');
                            scope.update();
                            console.log(result);
                        },
                        function (error) {
                            toastr.error(error.message,'Error');
                            console.log(error);
                        });
                };

            }
        };
    }
})();