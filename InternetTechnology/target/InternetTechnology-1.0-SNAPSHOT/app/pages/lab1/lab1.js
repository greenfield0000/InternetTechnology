'use strict';
angular.module('lab1Page', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/lab1', {
                    templateUrl: 'pages/lab1/lab1.html',
                    controller: 'dataManagerLab1Ctrl'
                });
            }])

        .controller('dataManagerLab1Ctrl',
                ['$scope', '$rootScope', '$location', '$http', '$filter', '$window', '$anchorScroll',
                    function ($scope, $rootScope, $http, $filter, $location, $window, $anchorScroll) {

                        $scope.gotoAnchor = function (x) {
                            var newHash = 'anchor_' + x;
                            if ($window.location.hash != newHash) {
                                $window.location.hash = 'lab1#anchor_' + x;
                            } else {
                                $anchorScroll();
                            }
                        };
                    }]);