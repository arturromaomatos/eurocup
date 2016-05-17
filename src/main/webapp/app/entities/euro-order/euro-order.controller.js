(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderController', EuroOrderController);

    EuroOrderController.$inject = ['$scope', '$state', 'EuroOrder'];

    function EuroOrderController ($scope, $state, EuroOrder) {
        var vm = this;
        vm.euroOrders = [];
        vm.loadAll = function() {
            EuroOrder.query(function(result) {
                vm.euroOrders = result;
            });
        };

        vm.loadAll();
        
    }
})();
