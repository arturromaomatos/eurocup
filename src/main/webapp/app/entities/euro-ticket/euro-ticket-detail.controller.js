(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroTicketDetailController', EuroTicketDetailController);

    EuroTicketDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'EuroTicket', 'EuroOrderItem'];

    function EuroTicketDetailController($scope, $rootScope, $stateParams, entity, EuroTicket, EuroOrderItem) {
        var vm = this;
        vm.euroTicket = entity;
        
        var unsubscribe = $rootScope.$on('eurocupApp:euroTicketUpdate', function(event, result) {
            vm.euroTicket = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
