(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroTicketDeleteController',EuroTicketDeleteController);

    EuroTicketDeleteController.$inject = ['$uibModalInstance', 'entity', 'EuroTicket'];

    function EuroTicketDeleteController($uibModalInstance, entity, EuroTicket) {
        var vm = this;
        vm.euroTicket = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            EuroTicket.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
