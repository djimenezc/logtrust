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
            'values': ['received1', 'received2', 'received3']
        };
        vm.getMetrics = getMetrics;

        vm.metricEntries = [];

        vm.hostChanged = function() {
            console.log('hostChanged');
//            vm.metricEntries = [];
        }

        function getMetrics () {
            console.log('getMetrics');

            LogAnalyzerService.get({
                connected : vm.connectedHost.value,
                received : vm.receivedHost.value
            }).$promise.then(function(data) {
                console.log(data);

                vm.connectedHosts = data.connected;
                vm.receivedHosts = data.received;
                vm.hostMostConnections = data.hostMostConnections;

                vm.metricEntries.push(data);
            });
        }
    }
})();
