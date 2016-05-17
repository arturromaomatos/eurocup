'use strict';

describe('Controller Tests', function() {

    describe('EuroOrder Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEuroOrder, MockEuroOrderItem, MockEuroOrderPayment, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEuroOrder = jasmine.createSpy('MockEuroOrder');
            MockEuroOrderItem = jasmine.createSpy('MockEuroOrderItem');
            MockEuroOrderPayment = jasmine.createSpy('MockEuroOrderPayment');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'EuroOrder': MockEuroOrder,
                'EuroOrderItem': MockEuroOrderItem,
                'EuroOrderPayment': MockEuroOrderPayment,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("EuroOrderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eurocupApp:euroOrderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
