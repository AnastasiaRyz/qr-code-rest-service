## QR Code REST Service

**QR Code REST Service** — это RESTful веб-сервис на Java с использованием **Spring Boot**, который генерирует QR-коды по переданному тексту.  
Проект разработан в рамках обучения на платформе [Hyperskill (JetBrains Academy)](https://hyperskill.org).

---

## Краткое описание

Приложение предоставляет API для создания QR-кодов.  
Пользователь может отправить GET-запрос с параметром `contents`, а сервис вернёт изображение QR-кода.  
В GET-запросе можно передать рамер будущего QR-code, форматр картинки (png, jpeg, gif), а также выбрать уровень коррекции ошибок. 

---

## Используемые технологии

- **Java 17**
- **Spring Boot**
- **Gradle**
- **ZXing** — библиотека для генерации QR-кодов

---

## Как запустить

1. Клонировать репозиторий:
   ```bash
   git clone https://github.com/AnastasiaRyz/cinema-room-rest-service.git
   cd qr-code-rest-service
   ```
2. Собрать и запустить приложение:
    ````
    ./gradlew bootRun
    ````

3. Открыть в браузере:
    ````
    http://localhost:8080/api/qrcode?contents=text&size=250&type=png
   ````