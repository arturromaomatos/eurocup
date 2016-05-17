(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('euro-order', {
            parent: 'entity',
            url: '/euro-order',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eurocupApp.euroOrder.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/euro-order/euro-orders.html',
                    controller: 'EuroOrderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('euroOrder');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('euro-order-detail', {
            parent: 'entity',
            url: '/euro-order/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eurocupApp.euroOrder.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/euro-order/euro-order-detail.html',
                    controller: 'EuroOrderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('euroOrder');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EuroOrder', function($stateParams, EuroOrder) {
                    return EuroOrder.get({id : $stateParams.id});
                }]
            }
        })
        .state('euro-order.new', {
            parent: 'euro-order',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-order/euro-order-dialog.html',
                    controller: 'EuroOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orderDate: null,
                                totalPrice: null,
                                paymentStatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('euro-order', null, { reload: true });
                }, function() {
                    $state.go('euro-order');
                });
            }]
        })
        .state('euro-order.edit', {
            parent: 'euro-order',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-order/euro-order-dialog.html',
                    controller: 'EuroOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EuroOrder', function(EuroOrder) {
                            return EuroOrder.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('euro-order', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('euro-order.delete', {
            parent: 'euro-order',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-order/euro-order-delete-dialog.html',
                    controller: 'EuroOrderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EuroOrder', function(EuroOrder) {
                            return EuroOrder.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('euro-order', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
