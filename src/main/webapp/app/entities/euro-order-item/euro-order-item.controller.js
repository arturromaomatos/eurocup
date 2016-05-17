(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderItemController', EuroOrderItemController);

    EuroOrderItemController.$inject = ['$scope', '$state', 'EuroOrderItem'];

    function EuroOrderItemController ($scope, $state, EuroOrderItem) {
        var vm = this;
        vm.euroOrderItems = [];
        vm.loadAll = function() {
            EuroOrderItem.query(function(result) {
                vm.euroOrderItems = result;
            });
        };

        vm.loadAll();
        
    }
})();
