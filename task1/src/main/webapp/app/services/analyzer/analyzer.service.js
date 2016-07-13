(function () {
    'use strict';

    angular
        .module('logAnalyzerApp')
        .factory('LogAnalyzerService', LogAnalyzerService);

    LogAnalyzerService.$inject = ['$resource'];

    function LogAnalyzerService ($resource) {

        return $resource('api/logInfo?connected=:connected&received=:received', {}, {
            'query': {method: 'GET', isArray: false},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    }
})();
