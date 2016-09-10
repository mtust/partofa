(function() {
    'use strict';
    angular
        .module('main')
        .directive('addEditNewEquipment', addEditNewEquipment);
    addEditNewEquipment.$inject = ['requestService'];

    function addEditNewEquipment(requestService) {
        return{
            replace:true,
            restrict:'EA',
            scope:{
                open:'=',
                update:'&updateInfo',
                edit:'&editInfo'
            },
            templateUrl:'/directives/private/add-edit-new-equipment',
            link:function (scope) {

                scope.open = function () {
                    scope.refreshData();
                    $('#add-new-equipment').modal('show');
                };

                scope.addEquipment = function () {
                    var requestParam = {
                        id: scope.id,
                        fixtureCode: scope.fixtureCode,
                        description: scope.fixtureDescription,
                        inputWattage: scope.inputWattage,
                        coast: scope.equipmentCost
                };
                    requestService.private.addEquipment(requestParam,
                        function () {
                            $('#add-new-equipment').modal('hide');
                            var message = 'Equipment successfully ' + (scope.id === null ? 'added' : 'updated');
                            toastr.success(message, 'Success');
                            scope.update();
                        },
                        function (error) {
                            toastr.error(error.message,'Error');
                        });
                };

                scope.$on('changeEquipment',function(event, data){
                    scope.getEquipmentInfo(data.id);
                });

                scope.editInfo = function () {
                    getEquipmentInfo(true);
                };

                scope.getEquipmentInfo = function (id,update) {
                    var requestParam = {
                        id: id
                    };
                    requestService.private.getEquipmentInfo(requestParam,
                        function(result){
                            scope.getEquipmentInfoSuccess(result);
                            if(update){
                                toastr.success('Equipment successfully updated', 'Success');
                            }
                        },function (error) {
                            console.log(error);
                        });
                };

                scope.getEquipmentInfoSuccess = function (result) {
                    scope.id = result.id;
                    scope.fixtureCode = result.fixtureCode;
                    scope.fixtureDescription = result.description;
                    scope.inputWattage = result.inputWattage;
                    scope.equipmentCost = result.coast;
                    $('#add-new-equipment').modal('show');
                };

                scope.refreshData = function () {
                    scope.id = null;
                    scope.fixtureCode = null;
                    scope.fixtureDescription = null;
                    scope.inputWattage = null;
                    scope.equipmentCost = null;
                };

            }
        };
    }
})();