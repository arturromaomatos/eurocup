(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroTicketDialogController', EuroTicketDialogController);

    EuroTicketDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EuroTicket', 'EuroOrderItem'];

    function EuroTicketDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EuroTicket, EuroOrderItem) {
        var vm = this;
        vm.euroTicket = entity;
        vm.euroorderitems = EuroOrderItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('eurocupApp:euroTicketUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.euroTicket.id !== null) {
                EuroTicket.update(vm.euroTicket, onSaveSuccess, onSaveError);
            } else {
                EuroTicket.save(vm.euroTicket, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.matchDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
