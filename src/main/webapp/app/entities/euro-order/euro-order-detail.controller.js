(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderDetailController', EuroOrderDetailController);

    EuroOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'EuroOrder', 'EuroOrderItem', 'EuroOrderPayment', 'User'];

    function EuroOrderDetailController($scope, $rootScope, $stateParams, entity, EuroOrder, EuroOrderItem, EuroOrderPayment, User) {
        var vm = this;
        vm.euroOrder = entity;
        
        var unsubscribe = $rootScope.$on('eurocupApp:euroOrderUpdate', function(event, result) {
            vm.euroOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
