(function() {
    'use strict';

    angular
        .module('foodtruckApp')
        .controller('TruckDetailController', TruckDetailController);

    TruckDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Truck', 'User', 'Position'];

    function TruckDetailController($scope, $rootScope, $stateParams, previousState, entity, Truck, User, Position) {
        var vm = this;

        vm.truck = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('foodtruckApp:truckUpdate', function(event, result) {
            vm.truck = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
