# Название проекта

Дипломный проект для курса Нетологии "Тестировщик ПО"

## Описание

Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

Тестируемое приложение — это веб-сервис, который предлагает купить тур по определённой цене двумя способами:
1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.

Поддерживается работа с двумя БД: MySQL и PostgreSQL. Postgres работает на нестандартном порту 3300.

## Инструкция по запуску

1. Сначала склонируйте репозиторий:

   ```bash
   git clone https://github.com/sibiryakov27/diploma.git
   ```

2. Из корневой папки проекта выполните команду:

   ```bash
   docker-compose up
   ```

3. Затем запустите тестируемое приложение с помощью команды:

    ```bash
    java -jar artifacts/aqa-shop.jar
    ```
   По умолчанию приложение взаимодействует с MySQL. Для работы с PostgreSQL нужно изменить параметр ```spring.datasource.url```. Сделать это можно прямо из командной строки:
   ```bash
    java -jar artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:3300/app
   ```

4. Соберите проект и запустите автотесты командой:

   ```bash
   ./gradlew test
   ```
   Для работы с PostgreSQL нужно задать значение одной переменной окружения:
   ```bash
   ./gradlew test -Ddatasource.url=jdbc:postgresql://localhost:3300/app
   ```

5. Для просмотра allure-отчёта выполните команду:

   ```bash
   ./gradlew allureServe
   ```

## Дополнительная информация

В корне проекта имеется файл docker-compose.reportPortal.yml, предназначенный для развертывания локально Report Portal.
