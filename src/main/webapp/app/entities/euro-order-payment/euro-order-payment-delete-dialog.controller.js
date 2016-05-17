(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderPaymentDeleteController',EuroOrderPaymentDeleteController);

    EuroOrderPaymentDeleteController.$inject = ['$uibModalInstance', 'entity', 'EuroOrderPayment'];

    function EuroOrderPaymentDeleteController($uibModalInstance, entity, EuroOrderPayment) {
        var vm = this;
        vm.euroOrderPayment = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            EuroOrderPayment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
