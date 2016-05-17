(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroTicketController', EuroTicketController);

    EuroTicketController.$inject = ['$scope', '$state', 'EuroTicket'];

    function EuroTicketController ($scope, $state, EuroTicket) {
        var vm = this;
        vm.euroTickets = [];
        vm.loadAll = function() {
            EuroTicket.query(function(result) {
                vm.euroTickets = result;
            });
        };

        vm.loadAll();
        
    }
})();
