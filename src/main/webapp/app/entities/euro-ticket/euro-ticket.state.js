(function() {
    'use strict';

    angular
        .module('eurocupApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('euro-ticket', {
            parent: 'entity',
            url: '/euro-ticket',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eurocupApp.euroTicket.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/euro-ticket/euro-tickets.html',
                    controller: 'EuroTicketController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('euroTicket');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('euro-ticket-detail', {
            parent: 'entity',
            url: '/euro-ticket/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eurocupApp.euroTicket.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/euro-ticket/euro-ticket-detail.html',
                    controller: 'EuroTicketDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('euroTicket');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EuroTicket', function($stateParams, EuroTicket) {
                    return EuroTicket.get({id : $stateParams.id});
                }]
            }
        })
        .state('euro-ticket.new', {
            parent: 'euro-ticket',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-ticket/euro-ticket-dialog.html',
                    controller: 'EuroTicketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                match: null,
                                location: null,
                                phase: null,
                                matchgroup: null,
                                matchDate: null,
                                matchHour: null,
                                price: null,
                                image: null,
                                totalTickets: null,
                                nrOfTickets: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('euro-ticket', null, { reload: true });
                }, function() {
                    $state.go('euro-ticket');
                });
            }]
        })
        .state('euro-ticket.edit', {
            parent: 'euro-ticket',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-ticket/euro-ticket-dialog.html',
                    controller: 'EuroTicketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EuroTicket', function(EuroTicket) {
                            return EuroTicket.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('euro-ticket', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('euro-ticket.delete', {
            parent: 'euro-ticket',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/euro-ticket/euro-ticket-delete-dialog.html',
                    controller: 'EuroTicketDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EuroTicket', function(EuroTicket) {
                            return EuroTicket.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('euro-ticket', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
