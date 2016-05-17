(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderPaymentDialogController', EuroOrderPaymentDialogController);

    EuroOrderPaymentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EuroOrderPayment'];

    function EuroOrderPaymentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EuroOrderPayment) {
        var vm = this;
        vm.euroOrderPayment = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('eurocupApp:euroOrderPaymentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.euroOrderPayment.id !== null) {
                EuroOrderPayment.update(vm.euroOrderPayment, onSaveSuccess, onSaveError);
            } else {
                EuroOrderPayment.save(vm.euroOrderPayment, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.paymentDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
