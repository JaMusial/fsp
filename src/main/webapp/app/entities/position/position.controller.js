(function() {
    'use strict';

    angular
        .module('foodtruckApp')
        .controller('PositionController', PositionController);

    PositionController.$inject = ['$scope', '$state', 'Position'];

    function PositionController ($scope, $state, Position) {
        var vm = this;

        vm.positions = [];

        loadAll();

        function loadAll() {
            Position.query(function(result) {
                vm.positions = result;
            });
        }
    }
})();
