(function() {
    'use strict';
    angular
        .module('main')
        .directive('addEditNewLead', addEditNewLead);
    addEditNewLead.$inject = ['requestService','countries','states'];

    function addEditNewLead(requestService,countries,states) {
        return{
            replace:true,
            restrict:'EA',
            scope:{
                open:'=',
                users:'=',
                clientstate:'=',
                header:'=',
                update:'&updateInfo',
                check: '&checkRoute'
            },
            templateUrl:'/directives/private/add-edit-new-lead',
            link:function (scope) {
                scope.addNewOrEditLead = {};
                scope.countries = countries.list;
                scope.states = states.list;
                scope.addNewOrEditLead.country  = scope.countries[0];
                scope.addNewOrEditLead.state = scope.states[0];
                scope.addNewOrEditLead.id = '';

                scope.addNewOrEditLeadSubmit = function () {
                    var requestParam = {
                        status:scope.addNewOrEditLead.status,
                        temp:scope.addNewOrEditLead.temp,
                        source:scope.addNewOrEditLead.source,
                        notes:scope.addNewOrEditLead.notes,
                        address:{
                            streetAddress:scope.addNewOrEditLead.address,
                            suiteApt:scope.addNewOrEditLead.suit,
                            city:scope.addNewOrEditLead.city,
                            location:scope.addNewOrEditLead.state,
                            postalCode:scope.addNewOrEditLead.postal,
                            country:scope.addNewOrEditLead.country
                        },
                        id:scope.addNewOrEditLead.id,
                        primaryContact:{
                            companyName:scope.addNewOrEditLead.companyName,
                            firstName: scope.addNewOrEditLead.firstName,
                            lastName: scope.addNewOrEditLead.lastName,
                            jobTitle: scope.addNewOrEditLead.jobTitle,
                            phoneNumber: scope.addNewOrEditLead.phone,
                            faxNumber: scope.addNewOrEditLead.fax,
                            email: scope.addNewOrEditLead.email
                        },
                        userId: scope.addNewOrEditLead.userId,
                        client:scope.clientstate
                    };
                    console.log(requestParam);

                    requestService.private.addLead(requestParam,
                        function (result) {
                            $('#add-edit-lead').modal('hide');
                            /*if(scope.clientstate === true){
                                toastr.success('Client successfully added','Success');
                            } else{
                                toastr.success(result.message,'Success');
                            }*/
                            scope.update();
                        },
                        function (error) {
                            toastr.error(error.message,'Error');
                            console.log(error);
                    });
                };

                scope.open = function () {
                    if(scope.clientstate){
                        scope.modalHeader = 'Add new client';
                    }
                    else {
                        scope.modalHeader = 'Add new lead';
                    }
                    scope.addNewOrEditLead = {};
                    scope.addNewOrEditLead.status = 'ACTIVE';
                    scope.addNewOrEditLead.state = 'STATE';
                    scope.addNewOrEditLead.temp = 'WARM';
                    scope.addNewOrEditLead.country  = scope.countries[0].name;
                    scope.addNewOrEditLead.state  = scope.states[0].abbreviation;
                    scope.assignTo = scope.users;
                    scope.addNewOrEditLead.userId = scope.users[0].id;
                    $('#add-edit-lead').modal('show');
                };

                scope.$on('changeText',function(event, data){
                    scope.getLeadInfo(data.id);
                });

                scope.getLeadInfo = function (id) {
                    var requestParam = {
                        id: id
                    };
                    requestService.private.getLeadInfo(requestParam,
                    function(result){
                        scope.getLeadInfoSuccess(result,id);
                    },function (error) {
                        console.log(error);
                    });
                };

                scope.getLeadInfoSuccess = function (result,id) {
                    scope.client = result.client;
                    scope.modalHeader = scope.header;
                    scope.addNewOrEditLead.status = result.status;
                    scope.addNewOrEditLead.source = result.source;
                    scope.addNewOrEditLead.temp = result.temp;
                    scope.addNewOrEditLead.companyName = result.primaryContact.companyName;
                    scope.addNewOrEditLead.jobTitle = result.primaryContact.jobTitle;
                    scope.addNewOrEditLead.firstName = result.primaryContact.firstName;
                    scope.addNewOrEditLead.phone = result.primaryContact.phoneNumber;
                    scope.addNewOrEditLead.lastName = result.primaryContact.lastName;
                    scope.addNewOrEditLead.fax = result.primaryContact.faxNumber;
                    scope.addNewOrEditLead.email = result.primaryContact.email;
                    scope.addNewOrEditLead.address = result.address.streetAddress;
                    scope.addNewOrEditLead.suit = result.address.suiteApt;
                    scope.addNewOrEditLead.city = result.address.city;
                    scope.addNewOrEditLead.state = result.address.location;
                    scope.addNewOrEditLead.postal = result.address.postalCode;
                    scope.addNewOrEditLead.country = result.address.country;
                    scope.addNewOrEditLead.notes = result.notes;
                    scope.assignTo = scope.users;
                    scope.addNewOrEditLead.userId = result.user.id;
                    scope.addNewOrEditLead.id = id;
                    $('#add-edit-lead').modal('show');
                };
            }
        };
    }
})();