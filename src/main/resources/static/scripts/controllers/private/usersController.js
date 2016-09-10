angular
    .module('main')
    .controller('usersController', usersController);
usersController.$inject = ['$scope','requestService','$rootScope','$state'];

function usersController($scope,requestService,$rootScope,$state) {
    $scope.$on('$viewContentLoaded', function () {
        $scope.roles = ['ADMIN','SALES','AUDITOR','PROJECT_MANAGER'];
        $scope.role = $scope.roles[0];
        $scope.sizes = ['10','25','50','100'];
        $scope.size = $scope.sizes[0];
        $scope.page = 0;
        $scope.order = false;
        $scope.sortField = 'id';
        $scope.searchLeads = '';
        $scope.orderValue = 'asc';
        $scope.leadsStatus = 'ACTIVE';
        $scope.getAllUser();
    });
    
    $scope.getAllUser = function () {
        var requestParam = {
            url:'?search='+$scope.searchLeads+'&status='+$scope.leadsStatus+'&page='+$scope.page +'&size='+$scope.size+'&sort='+$scope.sortField+','+$scope.orderValue
        };
        requestService.private.getAllUsers(requestParam,
            function (result) {
                $scope.users = result.content;
                $scope.pageCount = Math.ceil(result.totalElements/ $scope.size);
                if($scope.pageCount === 0){
                    $scope.pageCount++;
                }
                console.log(result);
            },
            function (error) {
                console.log(error);
            });
    };

    $scope.rangeRows = function () {
        $scope.page = 0;
        $scope.getAllUser();
    };

    $scope.pagination = function (index) {
        $scope.page = index;
        $scope.getAllUser();
    };

    $scope.sort = function (filed) {
        $scope.page = 0;
        $scope.order = $scope.sortField === filed ? !$scope.order : true;
        $scope.sortField = filed;
        $scope.orderValue = ($scope.order === true) ? 'asc':'desc';

        $scope.getAllUser();
    };

    $scope.$watch('searchLeads',function () {
        $scope.page = 0;
        $scope.getAllUser();
    });

    $scope.changeStatus = function () {
        $scope.page = 0;
        $scope.getAllUser();
    };

    $scope.signUp = function (email,firstName,lastName,password,confirmPassword,role) {
        var requestParam = {
            email:email,
            firstName:firstName,
            lastName:lastName,
            password:password,
            passwordConfirm:confirmPassword,
            role:'ROLE_'+role
        };
        requestService.private.signUp(requestParam,
            function (result) {
                $scope.getAllUser();
                toastr.success(result.message,'Success');
                console.log(result);

            },
            function (error) {
                toastr.error(error.message,'Error');
                console.log(error);
            });
    };


}