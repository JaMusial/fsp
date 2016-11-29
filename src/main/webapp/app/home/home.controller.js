(function() {
    'use strict';

    angular
        .module('foodtruckApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'NgMap', 'Home'];

    function HomeController ($scope, Principal, LoginService, $state, NgMap, Home) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        loadAll();

        vm.foodtrucks = [];
        NgMap.getMap().then(function(map) {
              vm.map = map;
        });

        vm.getCurrentLoc = function(param) {
            vm.map.getCenter();
        }

        function loadAll () {
            Home.query(function(result) {
                vm.foodtrucks = result;
                console.log(result);
            });
        }
    }
})();
