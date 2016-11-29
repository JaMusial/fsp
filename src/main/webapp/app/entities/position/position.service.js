(function() {
    'use strict';
    angular
        .module('foodtruckApp')
        .factory('Position', Position);

    Position.$inject = ['$resource'];

    function Position ($resource) {
        var resourceUrl =  'api/positions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
