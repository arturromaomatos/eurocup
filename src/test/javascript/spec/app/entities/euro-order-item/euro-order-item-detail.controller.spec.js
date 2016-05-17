'use strict';

describe('Controller Tests', function() {

    describe('EuroOrderItem Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEuroOrderItem, MockEuroOrder, MockEuroTicket;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEuroOrderItem = jasmine.createSpy('MockEuroOrderItem');
            MockEuroOrder = jasmine.createSpy('MockEuroOrder');
            MockEuroTicket = jasmine.createSpy('MockEuroTicket');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'EuroOrderItem': MockEuroOrderItem,
                'EuroOrder': MockEuroOrder,
                'EuroTicket': MockEuroTicket
            };
            createController = function() {
                $injector.get('$controller')("EuroOrderItemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eurocupApp:euroOrderItemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
