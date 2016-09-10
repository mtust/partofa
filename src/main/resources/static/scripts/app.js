
    angular.module('main',
        [
            'ngRoute',
            'ui.router',
            'ngCookies',
            'ngMaterial',
            'ngMessages',
            'ngAnimate',
            'moment-picker'
        ]).run(function ($rootScope,requestService) {
            alert(1)
        $rootScope.getUser = function () {
            requestService.private.getUser(
                function (result) {
                    console.log(result);
                    $rootScope.user = result;
                    $rootScope.user.id = result.userAccess.id;
                    $rootScope.assignTo.push($rootScope.user);
                    $rootScope.$evalAsync();
                },
                function (error) {
                    console.log(error);
                });

        };

        $rootScope.assignTo = [];

        (function getUser() {
            $rootScope.getUser();
        })();


        $rootScope.autoExpand = function(e) {
            var element = typeof e === 'object' ? e.target : document.getElementById(e);
            var scrollHeight = element.scrollHeight;
            element.style.height =  scrollHeight + 1 + 'px';
        };

        $rootScope.dataReset = function(){
            $('.modal').on('hidden.bs.modal', function () {
                $(this)
                    .find("input,textarea,select")
                    .val('')
                    .end()
                    .find("input[type=checkbox], input[type=radio]")
                    .prop("checked", '')
                    .end()
                    .find("input:disabled,.manual-disable")
                    .prop("disabled",false)
                    .end();
            });
        };


    });;
/**
 * Created by tust on 10.09.2016.
 */
