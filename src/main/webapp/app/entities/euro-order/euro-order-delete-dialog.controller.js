(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderDeleteController',EuroOrderDeleteController);

    EuroOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'EuroOrder'];

    function EuroOrderDeleteController($uibModalInstance, entity, EuroOrder) {
        var vm = this;
        vm.euroOrder = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            EuroOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
