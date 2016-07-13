(function() {
    'use strict';

    angular
        .module('logAnalyzerApp')
        .controller('AnalyzerController', AnalyzerController);


    AnalyzerController.$inject = [ 'LogAnalyzerService'];

    function AnalyzerController (LogAnalyzerService) {
        var vm = this;

        vm.connectedHost = {
            'type': 'select',
            'name': 'connectedHost',
            'value': 'connected1',
            'values': ['connected1', 'connected2', 'connected3']
        };
        vm.receivedHost =  {
            'type': 'select',
            'name': 'receivedHost',
            'value': 'received2',
            'values': ['received2', 'received2', 'received2']
        };
        vm.getMetrics = getMetrics;

        function getMetrics () {
            console.log('getMetrics');

            LogAnalyzerService.get({
                connected : vm.connectedHost.value,
                received : vm.receivedHost.value
            });
        }
    }
})();
