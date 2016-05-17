(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('euro-order-item', {
            parent: 'entity',
            url: '/euro-order-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eurocupApp.euroOrderItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/euro-order-item/euro-order-items.html',
                    controller: 'EuroOrderItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('euroOrderItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('euro-order-item-detail', {
            parent: 'entity',
            url: '/euro-order-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eurocupApp.euroOrderItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/euro-order-item/euro-order-item-detail.html',
                    controller: 'EuroOrderItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('euroOrderItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EuroOrderItem', function($stateParams, EuroOrderItem) {
                    return EuroOrderItem.get({id : $stateParams.id});
                }]
            }
        })
        .state('euro-order-item.new', {
            parent: 'euro-order-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-order-item/euro-order-item-dialog.html',
                    controller: 'EuroOrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quantity: null,
                                totalPrice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('euro-order-item', null, { reload: true });
                }, function() {
                    $state.go('euro-order-item');
                });
            }]
        })
        .state('euro-order-item.edit', {
            parent: 'euro-order-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-order-item/euro-order-item-dialog.html',
                    controller: 'EuroOrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EuroOrderItem', function(EuroOrderItem) {
                            return EuroOrderItem.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('euro-order-item', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('euro-order-item.delete', {
            parent: 'euro-order-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-order-item/euro-order-item-delete-dialog.html',
                    controller: 'EuroOrderItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EuroOrderItem', function(EuroOrderItem) {
                            return EuroOrderItem.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('euro-order-item', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
