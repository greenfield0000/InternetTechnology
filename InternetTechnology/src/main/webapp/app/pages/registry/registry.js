'use strict';

angular.module("registry", ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/registry', {
                    templateUrl: 'pages/registry/registry.html',
                    controller: 'registryPageCtrl'
                });
            }])

        .controller('registryPageCtrl', function ($window, $scope, $http, $rootScope) {

            $scope.soult = "romanroman";

            $scope.registry = function () {

                var newUser = {};

                newUser.name = "";
                newUser.login = "";
                newUser.password = "";

                $scope.user = newUser;
                $scope.user.name = $scope.registryName;
                $scope.user.login = $scope.registryLogin;

                $scope.user.password = $scope.registryPassword;

                console.log($window.location.pathname +
                        "../webTest/rest/getRegistry?user=" + angular.toJson($scope.user));
                $http({
                    method: "POST",
                    url: $window.location.pathname +
                            "../webTest/rest/getRegistry?user=" + encodeURI(angular.toJson($scope.user))
                }).then(function mySucces(response) {

                    var userIsRegistry = JSON.stringify(response.data);
                    var parsJSONUserRegistry = JSON.parse(userIsRegistry);

                    if (parsJSONUserRegistry) {
                        $window.location.hash = 'authorization';
                    }
                }, function myError(request) {
                });
            };

            $scope.goToAutorization = function () {
                $window.location.hash = 'authorization';
            }

        });


