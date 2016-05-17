(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('euro-order-payment', {
            parent: 'entity',
            url: '/euro-order-payment',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eurocupApp.euroOrderPayment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/euro-order-payment/euro-order-payments.html',
                    controller: 'EuroOrderPaymentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('euroOrderPayment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('euro-order-payment-detail', {
            parent: 'entity',
            url: '/euro-order-payment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eurocupApp.euroOrderPayment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/euro-order-payment/euro-order-payment-detail.html',
                    controller: 'EuroOrderPaymentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('euroOrderPayment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EuroOrderPayment', function($stateParams, EuroOrderPayment) {
                    return EuroOrderPayment.get({id : $stateParams.id});
                }]
            }
        })
        .state('euro-order-payment.new', {
            parent: 'euro-order-payment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-order-payment/euro-order-payment-dialog.html',
                    controller: 'EuroOrderPaymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                method: null,
                                cardNumber: null,
                                month: null,
                                year: null,
                                cvc: null,
                                paymentEntity: null,
                                paymentReference: null,
                                totalPrice: null,
                                cardCustName: null,
                                paymentDate: null,
                                iban: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('euro-order-payment', null, { reload: true });
                }, function() {
                    $state.go('euro-order-payment');
                });
            }]
        })
        .state('euro-order-payment.edit', {
            parent: 'euro-order-payment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-order-payment/euro-order-payment-dialog.html',
                    controller: 'EuroOrderPaymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EuroOrderPayment', function(EuroOrderPayment) {
                            return EuroOrderPayment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('euro-order-payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('euro-order-payment.delete', {
            parent: 'euro-order-payment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-order-payment/euro-order-payment-delete-dialog.html',
                    controller: 'EuroOrderPaymentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EuroOrderPayment', function(EuroOrderPayment) {
                            return EuroOrderPayment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('euro-order-payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
