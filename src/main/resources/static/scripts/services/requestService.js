(function() {
    'use strict';

    angular.module('main').factory('requestService',requestService);

    requestService.$inject = ['$http'];
    function requestService($http) {
        function login (requestParameters, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/public/user/login',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:{
                    email:requestParameters.email,
                    password:requestParameters.password
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function reset(requestParameters, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: '/public/user/change-forgot-password/'+requestParameters.link,
                headers: {
                    'Content-Type': 'application/json'
                },
                data:{
                    password:requestParameters.password,
                    passwordConfirm: requestParameters.passwordConfirm
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function forgot(requestParameters, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/public/user/change-forgot-password',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:{
                    email:requestParameters.email
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function logout(successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/protected/user/logout',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getUser(successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: 'private/user/is-authenticated',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function signUp(requestParameters, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/private/admin/user/sign-up',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:{
                    email:requestParameters.email,
                    firstName:requestParameters.firstName,
                    lastName:requestParameters.lastName,
                    password:requestParameters.password,
                    passwordConfirm:requestParameters.passwordConfirm,
                    role:requestParameters.role
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAdmin(requestParameters, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/admin/user/'+requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getLeadInfo(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/manager/lead/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getSiteInfo(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/manager/site/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getEquipmentInfo(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/admin/audit/equipment/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getMeasureInfo(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/admin/audit/measure/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        // GET ALL
        function getAllUsers (requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/admin/user/get-all'+requestParameters.url,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {

                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAllLeads(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/manager/lead/get-all'+requestParameters.url,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAllContacts(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/protected/contact/get-all'+requestParameters.url,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAllActivities(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/sales/plan_activity/get-all'+requestParameters.url,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAllSites(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/manager/site/get-all'+requestParameters.url,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAllAudits(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/auditor/audit/get-all' + requestParameters.url,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAllQuotes(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/protected/quote/get-all' + requestParameters.url,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAllEquipments(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/admin/audit/equipment/get-all' + requestParameters.url,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAllMeasures(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/admin/audit/measure/get-all' + requestParameters.url,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAssumption(successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/auditor/audit/assumptions',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getCompanyInfo(successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/protected/company',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getCurrencyAndTaxes(successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/admin/setting/currency-taxes',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function getAllEmailTemplates(successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: '/private/admin/email/get-all',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        // ADD
        function addContact(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/protected/contact',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addLead(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/private/manager/lead',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addActivity(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/private/sales/plan_activity',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addSite(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/private/manager/site',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addAudit(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/private/auditor/audit',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addQuote(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/protected/quote',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addEquipment(requestParameters,successCallback, errorCallback){
            $http({
                method: 'POST',
                url: '/private/admin/audit/equipment',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addMeasure(requestParameters,successCallback, errorCallback){
            $http({
                method: 'POST',
                url: '/private/admin/audit/measure',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addAssumption(requestParameters,successCallback, errorCallback){
            $http({
                method: 'POST',
                url: '/private/admin/audit/assumptions',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addCompanyInfo(requestParameters,successCallback, errorCallback){
            $http({
                method: 'POST',
                url: '/private/admin/company',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addCurrencyAndTaxes(requestParameters,successCallback, errorCallback){
            $http({
                method: 'POST',
                url: '/private/admin/setting/currency-taxes',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function addEmailTemplate(requestData, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/private/admin/email',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestData
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }


        // DELETE
        function deleteLead(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: '/private/manager/lead/'+requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function deleteContact(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: ' /protected/contact/'+requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function deleteActivity(requestParameters, successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: '/private/sales/plan_activity/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function deleteSite(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: ' /private/manager/site/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function deleteAudit(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: '/private/auditor/audit/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function deleteQuote(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: '/protected/quote/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function deleteEquipment(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: '/private/admin/audit/equipment/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function deleteMeasure(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: '/private/admin/audit/measure/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function sendEmail(requestParameters,successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: '/private/sales/contact/send_email',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:requestParameters
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }

        function convertToClient(requestParameters,successCallback,errorCallback) {
            $http({
                method: 'GET',
                url: '/private/manager/lead/convert/' + requestParameters.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            success(function (result) {
                successCallback(result);
            }).
            error(function (error) {
                errorCallback(error);
            });
        }


       return{
            public:{
                login:login,
                reset:reset,
                forgot:forgot
            },
            private:{
                logout: logout,
                getUser: getUser,
                signUp: signUp,
                getAdmin: getAdmin,
                getAllUsers: getAllUsers,

                convertToClient:convertToClient,
                getLeadInfo: getLeadInfo,
                getSiteInfo: getSiteInfo,
                getEquipmentInfo: getEquipmentInfo,
                getMeasureInfo: getMeasureInfo,

                getAllLead: getAllLeads,
                getAllContacts: getAllContacts,
                getAllActivities: getAllActivities,
                getAllSites: getAllSites,
                getAllAudits: getAllAudits,
                getAllQuotes: getAllQuotes,
                getAllEquipments: getAllEquipments,
                getAllMeasures: getAllMeasures,
                getAssumption: getAssumption,
                getCompanyInfo: getCompanyInfo,
                getCurrencyAndTaxes: getCurrencyAndTaxes,
                getAllEmailTemplates: getAllEmailTemplates,

                addLead: addLead,
                addContact: addContact,
                addActivity: addActivity,
                addSite: addSite,
                addAudit: addAudit,
                addQuote: addQuote,
                addEquipment: addEquipment,
                addMeasure: addMeasure,
                addAssumption: addAssumption,
                addCompanyInfo: addCompanyInfo,
                addCurrencyAndTaxes: addCurrencyAndTaxes,
                addEmailTemplate: addEmailTemplate,

                deleteLead: deleteLead,
                deleteActivity: deleteActivity,
                deleteContact: deleteContact,
                deleteSite: deleteSite,
                deleteAudit: deleteAudit,
                deleteQuote: deleteQuote,
                deleteEquipment: deleteEquipment,
                deleteMeasure: deleteMeasure,

                sendEmail:sendEmail

            },
            admin:{

            }
        };
    }

})();