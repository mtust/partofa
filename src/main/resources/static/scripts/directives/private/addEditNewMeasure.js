(function() {
    'use strict';
    angular
        .module('main')
        .directive('addEditNewMeasure', addEditNewMeasure);
    addEditNewMeasure.$inject = ['requestService'];

    function addEditNewMeasure(requestService) {
        return{
            replace:true,
            restrict:'EA',
            scope:{
                open:'=',
                update:'&updateInfo',
                edit:'&editInfo'
            },
            templateUrl:'/directives/private/add-edit-new-measure',
            link:function (scope) {

                scope.open = function () {
                    scope.refreshData();
                    scope.upgradeType = 'FIXTURE_REPLACEMENT';
                    $('#add-new-measure').modal('show');
                };

                scope.addMeasure = function () {
                    var requestParam = {
                        id: scope.id,
                        upgradeType: scope.upgradeType,
                        upgradeDescription: scope.upgradeDescription,
                        inputWattage: scope.inputWattage,
                        coast: scope.measureCost
                    };
                    requestService.private.addMeasure(requestParam,
                        function (result) {
                            console.log(result);
                            $('#add-new-measure').modal('hide');
                            var message = 'Measure successfully ' + (scope.id === null ? 'added' : 'updated');
                            toastr.success(message, 'Success');
                            scope.update();
                        },
                        function (error) {
                            toastr.error(error.message,'Error');
                            console.log(error);
                        });
                };

                scope.$on('changeMeasure',function(event, data){
                    scope.getMeasureInfo(data.id);
                });

                scope.editInfo = function () {
                    getMeasureInfo(true);
                };

                scope.getMeasureInfo = function (id,update) {
                    var requestParam = {
                        id: id
                    };
                    requestService.private.getMeasureInfo(requestParam,
                        function(result){
                            scope.getMeasureInfoSuccess(result);
                            if(update){
                                toastr.success('Measure successfully updated','Success');
                            }
                        },function (error) {
                            console.log(error);
                        });
                };

                scope.getMeasureInfoSuccess = function (result) {
                    scope.id = result.id;
                    scope.upgradeType = result.upgradeType;
                    scope.upgradeDescription = result.upgradeDescription;
                    scope.inputWattage = result.inputWattage;
                    scope.measureCost = result.coast;
                    $('#add-new-measure').modal('show');
                };

                scope.refreshData = function () {
                    scope.id = null;
                    scope.upgradeType = null;
                    scope.upgradeDescription = null;
                    scope.inputWattage = null;
                    scope.measureCost = null;
                };

            }
        };
    }
})();