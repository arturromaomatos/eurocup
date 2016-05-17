(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderPaymentController', EuroOrderPaymentController);

    EuroOrderPaymentController.$inject = ['$scope', '$state', 'EuroOrderPayment'];

    function EuroOrderPaymentController ($scope, $state, EuroOrderPayment) {
        var vm = this;
        vm.euroOrderPayments = [];
        vm.loadAll = function() {
            EuroOrderPayment.query(function(result) {
                vm.euroOrderPayments = result;
            });
        };

        vm.loadAll();
        
    }
})();
