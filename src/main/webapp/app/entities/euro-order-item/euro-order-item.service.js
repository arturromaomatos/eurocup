(function() {
    'use strict';
    angular
        .module('eurocupApp')
        .factory('EuroOrderItem', EuroOrderItem);

    EuroOrderItem.$inject = ['$resource'];

    function EuroOrderItem ($resource) {
        var resourceUrl =  'api/euro-order-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
