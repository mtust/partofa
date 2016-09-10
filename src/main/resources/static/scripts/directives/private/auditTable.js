(function() {
    'use strict';
    angular
        .module('main')
        .directive('auditTable', auditTable);
    auditTable.$inject = ['requestService','$stateParams'];

    function auditTable(requestService,$stateParams) {
        return{
            replace:true,
            restrict:'EA',
            scope:{
                audits:'=',
                update:'&updateInfo'
            },
            templateUrl:'/directives/private/audit-table',
            link:function (scope) {

                scope.addAudit = function () {
                    var requestParam = {
                        id: '',
                        name: scope.auditName,
                        modifiedTime: '',
                        siteId: scope.auditSite,
                        contactId: scope.auditContact
                    };
                    requestService.private.addAudit(requestParam,
                        function (result) {
                            $('#add-new-audit').modal('hide');
                            scope.getAllAudits();
                            toastr.success('Audit successfully added', 'Success');
                            console.log(result);
                        },
                        function (error) {
                            console.log(error);
                            toastr.error(error.message,'Error');
                        });
                };

                scope.getAllAudits = function () {
                    var requestParam = {
                        url:'?leadId=' + $stateParams.id
                    };
                    requestService.private.getAllAudits(requestParam,
                        function (result) {
                            scope.allAudits = result.content;
                            console.log(scope.allAudits);
                        },
                        function (error) {
                            console.log(error);
                        });
                };

                scope.deleteAudit = function (id) {
                    var requestParam = {
                        id:id
                    };
                    requestService.private.deleteAudit(requestParam,
                        function (result) {
                            scope.getAllAudits();
                            toastr.success('Audit successfully deleted','Success');
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