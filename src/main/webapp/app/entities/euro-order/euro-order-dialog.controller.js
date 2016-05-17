(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .controller('EuroOrderDialogController', EuroOrderDialogController);

    EuroOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'EuroOrder', 'EuroOrderItem', 'User', 'EuroOrderPayment'];

    function EuroOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, EuroOrder, EuroOrderItem, User, EuroOrderPayment) {
        var vm = this;
        vm.euroOrder = entity;
        vm.euroorderitems = EuroOrderItem.query();
        vm.users = User.query();
        vm.payments = EuroOrderPayment.query({filter: 'euroorder-is-null'});
        $q.all([vm.euroOrder.$promise, vm.payments.$promise]).then(function() {
            if (!vm.euroOrder.paymentId) {
                return $q.reject();
            }
            return EuroOrderPayment.get({id : vm.euroOrder.paymentId}).$promise;
        }).then(function(payment) {
            vm.payments.push(payment);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('eurocupApp:euroOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.euroOrder.id !== null) {
                EuroOrder.update(vm.euroOrder, onSaveSuccess, onSaveError);
            } else {
                EuroOrder.save(vm.euroOrder, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.orderDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
