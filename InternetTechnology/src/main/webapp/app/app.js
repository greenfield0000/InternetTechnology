'use strict';
var myApp = angular.module('myApp', ['ngCookies',
    'ngRoute',
    'lab1Page',
    'Main',
    'lab2Page',
    'lab3Page',
    'authorization',
    'registry']);
myApp.controller("dataManagerCtrl", function ($scope) {

    // заголовки панели контента
    $scope.labsListContent = [
        {name: "Лабораторная 1", note: "Для первой лабораторной работы необходимо найти бесплатный хостинг, \n\ (или сделать свой сайт). После этого нужно разметить заглавную страницу.\n\ Заглавная страница включает в себя:\n\ 1) Ссылки на другие страницы\n\ 2) Сделать якоря"},
        {name: "Лабораторная 2", note: "Задание второй лабораторной работы включает в себя:\n\ 1) Создание фреймов\n\ 2) Создание всевозможных таблиц\n\ 3) Вставить на главную страницу видео двумя способами. Первый способ\n\ с помощью фреймов, а второй способ с помощью тега <object>"},
        {name: "Лабораторная 3", note: "Третья лабораторная работа включает в себя: 1)Выполнение скрипов на стороне клиента. Стандартно предлагается написать калькулятор на JavaScript (можно и другое)"},
        {name: "Лабораторная 4", note: "Последняя лабораторная работа включает в себя: 1) Выполнение скрипта на стороне сервера. 2) Создание механизма авторизации (Java + БД) 3) Хранение пользователей в базе. 4) Поддержка сессии через cookie"}
    ];
    //заголовки навигационной панели
    $scope.labsListHeader = [
        {nameHeader: "На главную", link: "#main"},
        {nameHeader: "Лабораторная 1", link: "#lab1"},
        {nameHeader: "Лабораторная 2", link: "#lab2"},
        {nameHeader: "Лабораторная 3", link: "#lab3"}
    ];
})
        //контроллер для работы с "печеньками"
        .controller('cookiesCtrl', ['$cookieStore', '$rootScope', '$location',
            function ($scope, $rootScope, $cookieStore, $location) {
                // Функции 
                $scope.Exit = function () {
                    $scope.$root.user = {};
                    $scope.$root.userIsActive = false;
                };
                $scope.$root.user = {};
                $scope.$root.userIsActive = false;
                // Get cookie
                var sesIDCookie = $cookieStore.get('sesID');
                if (sesIDCookie) {
                    $cookieStore.set('sesID', sesIDCookie);
                    var sesNameCookie = $cookieStore.get('sesName');
                    if (sesNameCookie) {
                        $cookieStore.set('sesName', sesNameCookie);
                        $scope.$root.user = {"name": sesNameCookie};
                        $scope.$root.userIsActive = false;
                    }
                } else {
                    userExit();
                }
            }])

        // конфиг для переключения страниц между собой
        .config(["$routeProvider", function ($routeProvider) {
                $routeProvider
                        .when("/", {
                            templateUrl: "index.html",
                            controller: "dataManagerCtrl",
                            activetab: 'Main',
                            tittle: "Главная страница"
                        });
            }]);
