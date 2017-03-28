
'use strict';

angular.module('lab3Page', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/lab3', {
                    templateUrl: 'pages/lab3/lab3.html',
                    controller: 'dataManagerLab3Ctrl'
                });
            }])

        .controller('dataManagerLab3Ctrl', ['$scope', '$rootScope', '$http', '$filter',
            function ($scope, $rootScope, $http, $filter) {
            }
        ]);
