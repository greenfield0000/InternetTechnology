
'use strict';

angular.module('lab2Page', ['ngRoute', ])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/lab2', {
                    templateUrl: 'pages/lab2/lab2.html',
                    controller: 'dataManagerLab2Ctrl'
                });
            }])

        .controller('dataManagerLab2Ctrl', ['$scope', '$rootScope', '$http', '$filter',
            function ($scope, $rootScope, $http, $filter) {

            }]);

        