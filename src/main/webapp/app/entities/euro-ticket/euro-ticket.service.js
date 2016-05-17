(function() {
    'use strict';
    angular
        .module('eurocupApp')
        .factory('EuroTicket', EuroTicket);

    EuroTicket.$inject = ['$resource', 'DateUtils'];

    function EuroTicket ($resource, DateUtils) {
        var resourceUrl =  'api/euro-tickets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.matchDate = DateUtils.convertLocalDateFromServer(data.matchDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.matchDate = DateUtils.convertLocalDateToServer(data.matchDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.matchDate = DateUtils.convertLocalDateToServer(data.matchDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
