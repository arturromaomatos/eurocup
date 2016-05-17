(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderItemDeleteController',EuroOrderItemDeleteController);

    EuroOrderItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'EuroOrderItem'];

    function EuroOrderItemDeleteController($uibModalInstance, entity, EuroOrderItem) {
        var vm = this;
        vm.euroOrderItem = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            EuroOrderItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
