(function() {
    'use strict';

    angular
        .module('foodtruckApp')
        .controller('PositionDetailController', PositionDetailController);

    PositionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Position'];

    function PositionDetailController($scope, $rootScope, $stateParams, previousState, entity, Position) {
        var vm = this;

        vm.position = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('foodtruckApp:positionUpdate', function(event, result) {
            vm.position = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
