'use strict';

describe('Controller Tests', function() {

    describe('Truck Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTruck, MockUser, MockPosition;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTruck = jasmine.createSpy('MockTruck');
            MockUser = jasmine.createSpy('MockUser');
            MockPosition = jasmine.createSpy('MockPosition');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Truck': MockTruck,
                'User': MockUser,
                'Position': MockPosition
            };
            createController = function() {
                $injector.get('$controller')("TruckDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'foodtruckApp:truckUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
