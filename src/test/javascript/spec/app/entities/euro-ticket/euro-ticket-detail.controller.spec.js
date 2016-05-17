'use strict';

describe('Controller Tests', function() {

    describe('EuroTicket Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEuroTicket, MockEuroOrderItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEuroTicket = jasmine.createSpy('MockEuroTicket');
            MockEuroOrderItem = jasmine.createSpy('MockEuroOrderItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'EuroTicket': MockEuroTicket,
                'EuroOrderItem': MockEuroOrderItem
            };
            createController = function() {
                $injector.get('$controller')("EuroTicketDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eurocupApp:euroTicketUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
