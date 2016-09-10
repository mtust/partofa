(function() {
    'use strict';
    angular
        .module('main')
        .directive('emailTemplateModal', emailTemplateModal);
    emailTemplateModal.$inject = ['requestService'];

    function emailTemplateModal(requestService) {
        return{
            replace:true,
            restrict:'EA',
            scope:{
                send:'=',
                update:'&updateInfo'
            },
            templateUrl:'/directives/private/email-template-modal',
            link:function (scope) {
              
                scope.sendEmail = function () {
                    var requestParam = {
                        message:scope.text,
                        subject:scope.subject,
                        contactId:scope.send
                    };
                    requestService.private.sendEmail(requestParam,
                    function (result) {
                        $('#email-template').modal('hide');
                        toastr.success(result.message,'Success');
                        console.log(result);
                    },
                    function (error) {
                        toastr.success(error.message,'Error');
                        console.log(error);
                    });

                };
            } 
        };
    }
})();