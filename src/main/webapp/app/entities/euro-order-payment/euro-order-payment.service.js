(function() {
    'use strict';
    angular
        .module('eurocupApp')
        .factory('EuroOrderPayment', EuroOrderPayment);

    EuroOrderPayment.$inject = ['$resource', 'DateUtils'];

    function EuroOrderPayment ($resource, DateUtils) {
        var resourceUrl =  'api/euro-order-payments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.paymentDate = DateUtils.convertDateTimeFromServer(data.paymentDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
