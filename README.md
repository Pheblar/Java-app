## Описание проекта

Демо приложение отображает хранимые посты и имеет возможность добавления поста. Конфигурация определена на хранение в СУБД PostgreSQL с поддержкой кеширования в Redis. Имеется возможность
демо-выгрузки логов при добавлении поста в FLuentD, а также подключен Spring Actuator для сбора метрик Prometheus.

Присутствует фронт(ReactJS) и бэк(Java, Spring) компоненты соответственно.

В компоненте бэка все переменные определяются в корневом файле проекта `.env`(`/back-end/.env`)

## Запуск проекта

- front-end side
  
  - Переходим в корневую директорию фронта:\
  `cd front-end/`
  - Сборка(при условии установленных node.js&npm):\
  `npm run build`
    *создается директория build*

- back-end side

  - Переходим в корневую директорию бэка:\
    `cd back-end/`
  - Собираем прилагу(при условии установленного gradle):\
    `gradle bootJar`\
    *собранная jar-ка лежит в `/back-end/build/libs/`*
  - Запуск прилаги:\
    `java -jar build/libs/ci-back-end-0.0.1-SNAPSHOT.jar`

## Back-end Endpoints

  ### `GET /api/v1/posts`
  ### `POST /api/v1/create-post`
  ### `GET /manage/health`
      Content-Type: application/json

    {
      "title": "",
      "content": "",
      "author": ""
    }
  
