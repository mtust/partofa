(function() {
    'use strict';
    angular
        .module('main')
        .directive('addEditNewSite', addEditNewSite);
    addEditNewSite.$inject = ['requestService','countries','states', '$stateParams'];

    function addEditNewSite(requestService,countries,states,$stateParams) {
        return{
            replace:false,
            restrict:'EA',
            scope:{
                open:'=',
                lead:'=',
                users:'=',
                update:'&updateInfo'
            },
            templateUrl:'/directives/private/add-edit-new-site',
            link:function (scope) {
                scope.countries = countries.list;
                scope.states = states.list;
                scope.siteCountry  = scope.countries[0].name;
                scope.siteState = scope.states[0].abbreviation;
                scope.id = '';


                scope.isSameAddress = function () {
                    if(scope.sameAddress){
                        scope.siteStreetAddress =  scope.lead.address.streetAddress;
                        scope.siteCity =  scope.lead.address.city;
                        scope.siteSuit =  scope.lead.address.suiteApt;
                        scope.sitePostal =  scope.lead.address.postalCode;
                        scope.siteState =   scope.lead.address.location;
                        $('.manual-disable,.sameAddress input').attr('disabled',true);
                    } else{
                        scope.siteStreetAddress =  null;
                        scope.siteCity =  null;
                        scope.siteSuit =  null;
                        scope.sitePostal =  null;
                        scope.siteState =   null;
                        $('.manual-disable,.sameAddress input').attr('disabled',false);
                    }
                };

                scope.addSite = function () {
                    var requestParam = {
                        //id: scope.id,
                        siteName: scope.siteName,
                        streetAddress: scope.siteStreetAddress,
                        suiteApt: scope.siteSuit,
                        city: scope.siteCity,
                        postalCode: scope.sitePostal,
                        location: scope.siteState,
                        country: scope.siteCountry,
                        siteAdditions:{
                            propertyType: scope.sitePropertyType,
                            year: scope.siteYear,
                            electric: scope.siteElectric,
                            water: scope.siteWater,
                            gas: scope.siteGas,
                            square: scope.siteSquare,
                            notes: scope.siteNotes
                        },
                        leadId: $stateParams.id,
                        contactId: (scope.siteContact === null || scope.siteContact === 0) ? null : scope.siteContact
                    };
                    requestService.private.addSite(requestParam,
                        function (result) {
                            console.log(result);
                            $('#add-new-site').modal('hide');
                            var message = 'Site successfully ' + (scope.id === null ? 'added' : 'updated');
                            toastr.success(message, 'Success');
                            scope.update();
                        },
                        function (error) {
                            console.log(error);
                        });
                };

                scope.open = function () {
                    if(scope.lead.contacts.length > 0){
                     scope.contacts = scope.lead.contacts;
                     $('#add-new-site').modal('show');
                 } else {
                     toastr.error('Firstly you have to create at least one contact','Error');
                 }

                };

                /*scope.$on('editSite',function(event, data){
                    scope.getSiteInfo(data.id);
                });*/

                scope.getSiteInfo = function () {
                    var requestParam = {
                        id: $stateParams.id
                    };
                    requestService.private.getSiteInfo(requestParam,
                        function(result){
                            scope.getSiteInfoSuccess(result,id);
                        },function (error) {
                            console.log(error);
                        });
                };

                scope.getSiteInfoSuccess = function (result) {
                    scope.siteName = result.siteName;
                    scope.siteStreetAddress = result.streetAddress;
                    scope.siteSuit = result.suiteApt;
                    scope.siteCity = result.city;
                    scope.siteState = result.location;
                    scope.sitePostal=  result.postalCode;
                    scope.siteCountry= result.country;
                    scope.sitePropertyType = result.siteAdditions.propertyType;
                    scope.siteElectric = result.siteAdditions.electric;
                    scope.siteYear =  result.siteAdditions.year;
                    scope.siteGas = result.siteAdditions.gas;
                    scope.siteSquare = result.siteAdditions.square;
                    scope.siteWater = result.siteAdditions.water;
                    scope.siteNotes = result.notes;
                    scope.leadId = result.lead.id;
                    scope.contacts = [result.contact];
                    $('#add-new-site').modal('show');
                };


            }
        };
    }
})();