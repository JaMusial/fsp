(function() {
    'use strict';

    angular
        .module('foodtruckApp')
        .controller('TruckDialogController', TruckDialogController);

    TruckDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Truck', 'User', 'Position', 'NgMap'];

    function TruckDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Truck, User, Position, NgMap) {
        var vm = this;

        vm.truck = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.positions = Position.query({filter: 'truck-is-null'});
        $q.all([vm.truck.$promise, vm.positions.$promise]).then(function() {
            if (!vm.truck.positionId) {
                return $q.reject();
            }
            return Position.get({id : vm.truck.positionId}).$promise;
        }).then(function(position) {
            vm.positions.push(position);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        // maps
        vm.address = 'current-position';
        NgMap.getMap().then(function(map) {
              vm.map = map;
        });

        vm.getCurrentLoc = function(param) {
            vm.map.getCenter();
        }

        vm.addMarker = function(event) {
            var ll = event.latLng;
            entity.lat = ll.lat();
            entity.lng = ll.lng();
        }


        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.truck.id !== null) {
                Truck.update(vm.truck, onSaveSuccess, onSaveError);
            } else {
                Truck.save(vm.truck, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('foodtruckApp:truckUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
