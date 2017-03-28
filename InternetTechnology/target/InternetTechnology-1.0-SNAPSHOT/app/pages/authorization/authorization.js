'use strict';

angular.module("authorization", ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/authorization', {
                    templateUrl: 'pages/authorization/authorization.html',
                    controller: 'authorizationPageCtrl'
                });
            }])

        .controller('authorizationPageCtrl',
                function ($window, $scope, $rootScope, $http, $cookieStore) {

                    $scope.user = $scope.$root.user = {};

                    //авторизация
                    $scope.authorization = function () {

                        var user = {};

                        user.login = "";
                        user.password = "";

                        $scope.user = user;

                        // Читаем с полей введенный логин и пароль
                        $scope.user.login = $scope.autorizationLogin;
                        $scope.user.password = $scope.autorizationPassword;

                        // Вызовем рест сервис с введенными параметрами
                        $http({
                            method: "POST",
                            url: $window.location.pathname +
                                    "../webTest/rest/getAutorization?user=" + encodeURI(angular.toJson($scope.user))
                        }).then(function mySucces(response) {

                            // Получаем ответ от сервера
                            var userDataResponce = JSON.stringify(response.data);

                            // Если ответ успешно получен
                            if (userDataResponce) {
                                // Тогда парсим data данные
                                var parseJSONUserUserAutorization = JSON.parse(userDataResponce);
                                if (parseJSONUserUserAutorization) {
                                    // Возвращаем в глобальные переменные пользователя, 
                                    // Чтобы потом можно было вернуть его имя
                                    $scope.$root.user = response.data;
                                    console.log($scope.$root.user);
                                    if ($scope.$root.user.sesID) {
                                        $cookieStore.put('sesID', $scope.$root.user.sesID);
                                    }
                                    if ($scope.$root.user.sesName) {
                                        $cookieStore.put('sesName', $scope.$root.user.sesName);
                                    }
                                    // Возводим флаг, что пользователь авторизован
                                    $scope.$root.userIsActive = true;
                                }
                            }

                        }, function myError(request) {
                        });
                    };

                    //релинк на страницу регистрации
                    $scope.registry = function () {
                        $window.location.hash = 'registry';
                    }
                });

