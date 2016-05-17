'use strict';

describe('Controller Tests', function() {

    describe('EuroOrder Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEuroOrder, MockEuroOrderItem, MockUser, MockEuroOrderPayment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEuroOrder = jasmine.createSpy('MockEuroOrder');
            MockEuroOrderItem = jasmine.createSpy('MockEuroOrderItem');
            MockUser = jasmine.createSpy('MockUser');
            MockEuroOrderPayment = jasmine.createSpy('MockEuroOrderPayment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'EuroOrder': MockEuroOrder,
                'EuroOrderItem': MockEuroOrderItem,
                'User': MockUser,
                'EuroOrderPayment': MockEuroOrderPayment
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
