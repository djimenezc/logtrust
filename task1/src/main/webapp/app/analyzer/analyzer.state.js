(function() {
    'use strict';

    angular
        .module('logAnalyzerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('analyzer', {
            parent: 'app',
            url: '/analyzer',
            data: {
                authorities: [],
                pageTitle: 'Log Trust Analyzer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/analyzer/analyzer.html',
                    controller: 'AnalyzerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('register');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
