(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderItemDetailController', EuroOrderItemDetailController);

    EuroOrderItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'EuroOrderItem', 'EuroOrder', 'EuroTicket'];

    function EuroOrderItemDetailController($scope, $rootScope, $stateParams, entity, EuroOrderItem, EuroOrder, EuroTicket) {
        var vm = this;
        vm.euroOrderItem = entity;
        
        var unsubscribe = $rootScope.$on('eurocupApp:euroOrderItemUpdate', function(event, result) {
            vm.euroOrderItem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
