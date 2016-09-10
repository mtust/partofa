(function() {
    'use strict';
    angular
        .module('main')
        .directive('contactTable', contactTable);
    contactTable.$inject = ['requestService','$stateParams'];

    function contactTable(requestService,$stateParams) {
        return{
            replace:false,
            restrict:'EA',
            scope:{
                contacts:'=',
                update:'&updateInfo'
            },
            templateUrl:'/directives/private/contact-table',
            link:function (scope) {

                scope.addNewContact = function () {
                    var requestParam = {
                        note: scope.addNewContactModels.notes,
                        firstName: scope.addNewContactModels.firstName,
                        lastName: scope.addNewContactModels.lastName,
                        jobTitle: scope.addNewContactModels.title,
                        phoneNumber: scope.addNewContactModels.phone,
                        faxNumber: scope.addNewContactModels.fax,
                        email: scope.addNewContactModels.email,
                        leadId: $stateParams.id,
                        site: scope.addNewContactModels.site
                    };
                    requestService.private.addContact(requestParam,
                        function (result) {
                            $('#add-new-contact').modal('hide');
                            toastr.success(result.message,'Success');
                            scope.update();
                        },function (error) {
                            toastr.error(error.message,'Error');
                        });
                };

                scope.deleteContact = function (id) {
                    var requestParam = {
                        id:id
                    };
                    requestService.private.deleteContact(requestParam,
                        function (result) {
                            $('#add-new-contact').modal('hide');
                            toastr.success(result.message,'Success');
                            scope.update();
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