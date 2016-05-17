(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderPaymentDetailController', EuroOrderPaymentDetailController);

    EuroOrderPaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'EuroOrderPayment'];

    function EuroOrderPaymentDetailController($scope, $rootScope, $stateParams, entity, EuroOrderPayment) {
        var vm = this;
        vm.euroOrderPayment = entity;
        
        var unsubscribe = $rootScope.$on('eurocupApp:euroOrderPaymentUpdate', function(event, result) {
            vm.euroOrderPayment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
