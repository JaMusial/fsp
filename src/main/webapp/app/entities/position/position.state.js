(function() {
    'use strict';

    angular
        .module('foodtruckApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('position', {
            parent: 'entity',
            url: '/position',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Positions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/position/positions.html',
                    controller: 'PositionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('position-detail', {
            parent: 'entity',
            url: '/position/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Position'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/position/position-detail.html',
                    controller: 'PositionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Position', function($stateParams, Position) {
                    return Position.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'position',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('position-detail.edit', {
            parent: 'position-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/position/position-dialog.html',
                    controller: 'PositionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Position', function(Position) {
                            return Position.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('position.new', {
            parent: 'position',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/position/position-dialog.html',
                    controller: 'PositionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lat: null,
                                lng: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('position', null, { reload: 'position' });
                }, function() {
                    $state.go('position');
                });
            }]
        })
        .state('position.edit', {
            parent: 'position',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/position/position-dialog.html',
                    controller: 'PositionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Position', function(Position) {
                            return Position.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('position', null, { reload: 'position' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('position.delete', {
            parent: 'position',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/position/position-delete-dialog.html',
                    controller: 'PositionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Position', function(Position) {
                            return Position.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('position', null, { reload: 'position' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
