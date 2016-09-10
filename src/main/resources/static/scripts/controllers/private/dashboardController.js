angular
    .module('main')
    .controller('dashboardController', dashboardController);
dashboardController.$inject = ['$scope','requestService','$state','$rootScope','countries','states','$stateParams'];

function dashboardController($scope,requestService,$state,$rootScope,countries,states,$stateParams) {
    $scope.$on('$viewContentLoaded', function () {
        $scope.checkRoute();
        $scope.checkUserStatus();
        $scope.checkRole();
        $scope.countries = countries.list;
        $scope.states = states.list;
        $scope.getAllLeads();
        $scope.getAllContacts();
        $scope.getAllSites();
        $scope.getAllAudits();
        $scope.getAllQuotes();
    });

    $scope.chooseLeadContactSite = function (index) {
        //$scope.contacts.unshift({id:0,firstName:'Choose',lastName:'Contacts'});
        for (var i = 0; i < $scope.leads.length; i++) {
            if($scope.leads[i].id === index){
                $scope.contacts = $scope.leads[i].contacts;
                $scope.sites = $scope.leads[i].sites;
            }
        }
    };

    $scope.checkRoute = function () {
       if($state.current.url.indexOf('client')>-1)
       {
           $scope.client = true;
       } else {
           $scope.client = false;
       }
    };

    $scope.checkUserStatus = function () {
        if ('ROLE_ADMIN' === $rootScope.user.role) {
            $scope.getAllSales();
        }
    };

    $scope.getAllSales = function () {
        var requestParam = {
            url:'?role=ROLE_USER'
        };
        requestService.private.getAllUsers(requestParam,
            function (result) {
                for (var i = 0; i < result.content.length; i++) {
                    $rootScope.assignTo.push(result.content[i]);
                }
            },
            function (error) {
                console.log(error);
            });
    };
    //ADD LEAD

    $scope.getAllLeads = function () {
        var requestParam = {
            url:''
        };
        requestService.private.getAllLead(requestParam,
            function (result) {
                $scope.leads = result.content;
                console.log($scope.leads);
            },
            function (error) {
                console.log(error);
            });
    };

    //ADD CONTACT
    $scope.getAllContacts = function(){
        var requestParam = {
            url:''
        };
        requestService.private.getAllContacts(requestParam,
            function (result) {
                $scope.contacts = result.content;
                $scope.contacts.unshift({id:0,firstName:'Choose',lastName:'Contacts'});
            },
            function (error) {
                console.log(error);
            });
    };

    $scope.getAllSites = function () {
        var requestParam = {
            url:''
        };
        requestService.private.getAllSites(requestParam,
            function (result) {
                $scope.sites = result.content;
                console.log(result);
                $scope.sites.unshift({id:0,siteName:'Choose site'});
            },
            function (error) {
                console.log(error);
            });
    };

    $scope.getContactsInfo = function () {
        var requestParam = {
            url:'?company='+$scope.searchLeads+'&client='+$scope.client+'&status='+$scope.leadsStatus+'&page='+$scope.page +'&size='+$scope.size+'&sort='+$scope.sortField+','+$scope.orderValue
        };
        requestService.private.getAllContacts(requestParam,
            function (result) {
                console.log(result);
                $scope.contacts = result.content;
                $scope.pageCount = Math.ceil(result.totalElements/ $scope.size);
                if($scope.pageCount === 0){
                    $scope.pageCount++;
                }
                $('#add-edit-site').modal('hide');
                $scope.$evalAsync();
            },
            function (error) {
                console.log(error);
            });
    };

    $scope.addNewContact = function () {
        var requestParam = {
            id: $scope.primaryContact ? $scope.primaryContact.id : '',
            note: $scope.addNewContactModels.notes,
            firstName: $scope.addNewContactModels.firstName,
            lastName: $scope.addNewContactModels.lastName,
            jobTitle: $scope.addNewContactModels.title,
            phoneNumber: $scope.addNewContactModels.phone,
            faxNumber: $scope.addNewContactModels.fax,
            email: $scope.addNewContactModels.email,
            leadId: $scope.addNewContactModels.lead,
            site: $scope.addNewContactModels.site
        };
        requestService.private.addContact(requestParam,
            function (result) {
                $('#add-new-contact').modal('hide');
                toastr.success('Contact successfully added','Success');
            },function (error) {
                toastr.error(error.message,'Error');
            });
    };

    //ADD SITE
    $scope.isSameAddress = function (index) {
        for (var i = 0; i < $scope.leads.length; i++) {
            if ($scope.leads[i].id === index) {
                if ($scope.sameAddress) {
                    $scope.addNewSitesModels.siteStreetAddress = $scope.leads[i].address.streetAddress;
                    $scope.addNewSitesModels.suiteApt = $scope.leads[i].address.city;
                    $scope.addNewSitesModels.city = $scope.leads[i].address.suiteApt;
                    $scope.addNewSitesModels.postalCode = $scope.leads[i].address.postalCode;
                    $scope.addNewSitesModels.location = $scope.leads[i].address.location;
                    $('.manual-disable,.sameAddress input').attr('disabled', true);

                } else {
                    $scope.addNewSitesModels.siteStreetAddress = null;
                    $scope.addNewSitesModels.suiteApt = null;
                    $scope.addNewSitesModels.city = null;
                    $scope.addNewSitesModels.postalCode = null;
                    $scope.addNewSitesModels.location = null;
                    $('.manual-disable,.sameAddress input ').attr('disabled', false);
                }
            }
        }
    };

    $scope.addNewSite = function () {
        var requestParam = {
            siteName: $scope.addNewSitesModels.siteName,
            streetAddress: $scope.addNewSitesModels.siteStreetAddress,
            suiteApt: $scope.addNewSitesModels.suiteApt,
            city: $scope.addNewSitesModels.city,
            postalCode: $scope.addNewSitesModels.postalCode,
            location: $scope.addNewSitesModels.location,
            country: $scope.addNewSitesModels.siteCountry,
            siteAdditions:{
                propertyType: $scope.addNewSitesModels.propertyType,
                year: $scope.addNewSitesModels.year,
                electric: $scope.addNewSitesModels.electric,
                water: $scope.addNewSitesModels.water,
                gas: $scope.addNewSitesModels.gas,
                square: $scope.addNewSitesModels.square,
                notes: $scope.addNewSitesModels.notes
            },
            leadId: $scope.addNewSitesModels.lead,
            contactId: $scope.addNewSitesModels.siteContact
        };
        requestService.private.addSite(requestParam,
            function (result) {
            $('#add-edit-site').modal('hide');
                toastr.success('Site successfully added', 'Success');
                console.log(result);
            },
            function (error) {
                toastr.error(error.message,'Error');
                console.log(error);
            });
    };

    $scope.openNewSite = function () {
        $scope.modalHeader = 'Add new site';
        $scope.addNewSitesModels = {};
        $scope.addNewSitesModels.siteContact = 0;
        if ($scope.leads.length > 0)
        {
            $scope.addNewSitesModels.lead = $scope.leads[0].id;
            $scope.addNewSitesModels.siteCountry = $scope.countries[0].name;
            $scope.addNewSitesModels.location = $scope.states[0].abbreviation;
            $('#add-edit-site').modal('show');
            $scope.primaryContact = undefined;
        } else  {
            toastr.error("Firstly you have to add at lease one lead","Error");
        }
    };

    $scope.openNewContact = function () {
        $('#add-new-contact').modal('show');
    };

    $scope.openNewActivity = function () {
      $('#add-new-activity').modal('show');
    };

    $scope.openNewAudit = function () {
        $('#add-new-audit').modal('show');
    };

    $scope.openNewQuote = function () {
        $('#add-new-quote').modal('show');
    };

    //ACTIVITY
    $scope.isChecked = function () {
        var date = new Date();
        var d = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);
        var t = date.getHours() + ':' + date.getMinutes();
        $scope.isChecked = function () {
            if($scope.rightnow){
                $scope.datepicker = d;
                $scope.timepicker = t;
            } else{
                $scope.datepicker = null;
                $scope.timepicker = null;
            }
        };
    };

    $scope.addActivity = function () {
        var date  = $scope.datepicker + ' ' + $scope.timepicker;
        var requestParam = {
            contactId:$scope.activityContact,
            plannedTime: new Date(date).toISOString(),
            activityType: $scope.activityType,
            note: $scope.addNewActivityNotes,
            leadId: $scope.activityLead
        };
        requestService.private.addActivity(requestParam,
            function (result) {
                $('#add-new-activity').modal('hide');
                console.log(result);
                toastr.success('Activity successfully added', 'Success');
            },
            function (error) {
                console.log(error);
                toastr.error(error.message,'Error');
            });
    };

    //AUDITS

    $scope.addAudit = function () {
        var requestParam = {
            id: '',
            name: $scope.auditName,
            contactId: $scope.auditContact,
            modifiedTime: '',
            siteId: $scope.auditSite
        };
        requestService.private.addAudit(requestParam,
            function (result) {
                $('#add-new-audit').modal('hide');
                $scope.getAllAudits();
                toastr.success('Audit successfully added', 'Success');
                console.log(result);
            },
            function (error) {
                console.log(error);
                toastr.error(error.message,'Error');
            });
    };

    $scope.getAllAudits = function () {
        var requestParam = {
            url:'?leadId=' + $stateParams.id
        };
        requestService.private.getAllAudits(requestParam,
            function (result) {
                $scope.allAudits = result.content;
            },
            function (error) {
                console.log(error);
            });
    };


    // QUOTES

    $scope.getAllQuotes = function () {
        var requestParam = {
            url:'?leadId=' + $stateParams.id
        };
        requestService.private.getAllQuotes(requestParam,
            function (result) {
                $scope.allQuotes = result.content;
            },
            function (error) {
                console.log(error);
            });
    };

    $scope.addQuote = function () {
        var requestParam = {
            id: '',
            name: $scope.quoteName,
            contactId: $scope.quoteContact,
            siteId: $scope.quoteSite
        };
        requestService.private.addQuote(requestParam,
            function (result) {
                $('#add-new-quote').modal('hide');
                $scope.getAllQuotes();
                toastr.success('Quote successfully added', 'Success');
                console.log(result);
            },
            function (error) {
                console.log(error);
                toastr.error(error.message,'Error');
            });
    };

}