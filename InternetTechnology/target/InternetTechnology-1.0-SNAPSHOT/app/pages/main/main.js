'use strict';

angular.module('Main', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/main', {
                    templateUrl: 'pages/main/main.html',
                    controller: 'dataManagerCtrl'
                });
            }])

        .controller('dataManagerCtrl', ['$scope', '$rootScope', '$http', '$filter',
            function ($scope, $rootScope, $http, $filter) {
            }]);