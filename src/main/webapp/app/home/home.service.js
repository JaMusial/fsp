(function() {
    'use strict';
    angular
        .module('foodtruckApp')
        .factory('Home', Home);

    Home.$inject = ['$resource'];

    function Home ($resource) {
        var resourceUrl =  'api/trucks/all';

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
            }
        });
    }
})();
