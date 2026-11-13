# Отчет по лабораторной работе №1
**Тема:** Введение в Spring Boot
**Выполнил:** Алматов
**Номер зачетной книжки:** 12002408
**Направление:** 09.03.02 Информационные системы и технологии

---

## 1. Цель работы
Получить практические навыки работы с Spring Framework.

## 2. Задание
1. Изучить теоретический материал.
2. Написать приложение следуя примеру.
3. Выполнить дополнительное задание.
4. Сделать отчет.

## 3. Выполнение базовой части
Spring — это фреймворк для разработки приложений на языке Java, который обеспечивает комплексный набор инструментов и библиотек для упрощения создания веб-приложений. Одним из его главных механизмов является внедрение зависимостей (Dependency Injection), позволяющее управлять зависимостями между компонентами приложения.

С помощью сервиса Spring Initializr был сгенерирован базовый проект со следующими параметрами:
* Сборщик: Maven.
* Язык: Java.
* Упаковка: Jar.

Для превращения приложения в веб-сервер в конфигурационный файл `pom.xml` была добавлена зависимость `spring-boot-starter-web`. Точкой входа в приложение является класс `DemoApplication`, помеченный аннотацией `@SpringBootApplication`, которая говорит Spring Boot автоматически настраивать приложение.

В ходе работы был разработан класс `MessageController`, помеченный аннотацией `@RestController` (объявляет класс как контроллер RESTful). Контроллер обрабатывает HTTP-запросы и выполняет операции CRUD (Create, Read, Update, Delete) над сообщениями пользователя, которые хранятся в динамическом массиве `ArrayList`.

## 4. Выполнение дополнительных заданий
В рамках лабораторной работы были реализованы следующие дополнительные функции (варианты 1, 2, 3, 4, 6):
1. Метод `clearAllMessages()` для очистки списка всех сообщений.
2. Проверка на пустое сообщение при публикации нового сообщения.
3. Метод `getMessageByIndex(int index)` для возврата сообщения по индексу.
4. Ограничение количества сообщений (при превышении предела старые сообщения удаляются).
6. Метод `countMessages()` для подсчета общего количества сообщений в списке.

## 5. Результаты тестирования
API было протестировано с использованием терминала Windows PowerShell и веб-браузера.

1. Успешный запуск сервера Tomcat:
В консоли выведено сообщение о старте сервера и приложения (Started DemoApplication).

2. Публикация сообщения (POST-запрос):
Через команду Invoke-RestMethod отправлен POST-запрос с сообщением, сервер вернул статус успешной публикации.

3. Проверка списка сообщений в браузере (GET-запрос):
При переходе по адресу http://localhost:8080/messages в браузере отображается корректный список добавленных сообщений в формате JSON.

### Исходный код контроллера (`MessageController.java`)
```java
package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private final List<String> userMessages = new ArrayList<>();
    private static final int MAX_MESSAGES = 5;

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/messages")
    public List<String> getAllMessages() {
        return userMessages;
    }

    @PostMapping("/messages")
    public String publishMessage(@RequestBody(required = false) String message) {
        if (message == null || message.trim().isEmpty()) {
            return "Error: Message cannot be empty!";
        }
        if (userMessages.size() >= MAX_MESSAGES) {
            userMessages.remove(0);
        }
        userMessages.add(message);
        return "Message published successfully!";
    }

    @PutMapping("/messages/{index}")
    public String updateMessage(@PathVariable int index, @RequestBody String message) {
        if (index >= 0 && index < userMessages.size()) {
            userMessages.set(index, message);
            return "Message updated successfully!";
        }
        return "Message not found at index " + index;
    }

    @DeleteMapping("/messages/{index}")
    public String deleteMessage(@PathVariable int index) {
        if (index >= 0 && index < userMessages.size()) {
            userMessages.remove(index);
            return "Message deleted successfully!";
        }
        return "Message not found at index " + index;
    }

    @DeleteMapping("/messages/clear")
    public String clearAllMessages() {
        userMessages.clear();
        return "All messages cleared successfully!";
    }

    @GetMapping("/messages/{index}")
    public String getMessageByIndex(@PathVariable int index) {
        if (index >= 0 && index < userMessages.size()) {
            return userMessages.get(index);
        }
        return "Message not found at index " + index;
    }

    @GetMapping("/messages/count")
    public int countMessages() {
        return userMessages.size();
    }
}