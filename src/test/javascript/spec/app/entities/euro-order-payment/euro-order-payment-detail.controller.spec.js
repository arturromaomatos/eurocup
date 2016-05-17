'use strict';

describe('Controller Tests', function() {

    describe('EuroOrderPayment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEuroOrderPayment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEuroOrderPayment = jasmine.createSpy('MockEuroOrderPayment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'EuroOrderPayment': MockEuroOrderPayment
            };
            createController = function() {
                $injector.get('$controller')("EuroOrderPaymentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eurocupApp:euroOrderPaymentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
