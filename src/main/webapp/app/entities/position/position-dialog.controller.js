(function() {
    'use strict';

    angular
        .module('foodtruckApp')
        .controller('PositionDialogController', PositionDialogController);

    PositionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Position'];

    function PositionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Position) {
        var vm = this;

        vm.position = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.position.id !== null) {
                Position.update(vm.position, onSaveSuccess, onSaveError);
            } else {
                Position.save(vm.position, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('foodtruckApp:positionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
