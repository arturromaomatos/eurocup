(function() {
    'use strict';
    angular
        .module('eurocupApp')
        .factory('EuroOrder', EuroOrder);

    EuroOrder.$inject = ['$resource', 'DateUtils'];

    function EuroOrder ($resource, DateUtils) {
        var resourceUrl =  'api/euro-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.orderDate = DateUtils.convertDateTimeFromServer(data.orderDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
