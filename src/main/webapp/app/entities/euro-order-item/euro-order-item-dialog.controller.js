(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderItemDialogController', EuroOrderItemDialogController);

    EuroOrderItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'EuroOrderItem', 'EuroOrder', 'EuroTicket'];

    function EuroOrderItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, EuroOrderItem, EuroOrder, EuroTicket) {
        var vm = this;
        vm.euroOrderItem = entity;
        vm.euroorders = EuroOrder.query();
        vm.tickets = EuroTicket.query({filter: 'name-is-null'});
        $q.all([vm.euroOrderItem.$promise, vm.tickets.$promise]).then(function() {
            if (!vm.euroOrderItem.ticketId) {
                return $q.reject();
            }
            return EuroTicket.get({id : vm.euroOrderItem.ticketId}).$promise;
        }).then(function(ticket) {
            vm.tickets.push(ticket);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('eurocupApp:euroOrderItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.euroOrderItem.id !== null) {
                EuroOrderItem.update(vm.euroOrderItem, onSaveSuccess, onSaveError);
            } else {
                EuroOrderItem.save(vm.euroOrderItem, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
