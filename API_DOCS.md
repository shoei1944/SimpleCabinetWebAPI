### Документация по SimpleCabinet 2 WebAPI

#### Авторизация
Для выполнения запросов от имени пользователя вам необходимо получить accessToken и refreshToken методом authorize
- `/auth/authorize` авторизация по логину и паролю
```javascript
await ( await fetch("https://АДРЕС ЛК/auth/authorize", {
  "method": "POST",
  "body": JSON.stringify({
    "username": "Gravita",
    "password": "1111"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
Если авторизация прошла успешно вам будет выдан cookie 'session' с accessToken внутри. Вы можете вручную указывать accessToken передав header `Authorization: Bearer ACCESS_TOKEN`, либо использовать установленную cookie  
  - Авторизация с использованием 2FA
```javascript
await ( await fetch("https://АДРЕС ЛК/auth/authorize", {
  "method": "POST",
  "body": JSON.stringify({
    "username": "Gravita",
    "password": "1111",
    "totpPassword": "123456"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/auth/refresh` - получение новой пары accessToken/refreshToken по refreshToken
```javascript
await ( await fetch("https://АДРЕС ЛК/auth/refresh", {
  "method": "POST",
  "body": JSON.stringify({
    "refreshToken": "REFRESH TOKEN"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/auth/register` регистрация пользователя
```javascript
await ( await fetch("https://АДРЕС ЛК/auth/register", {
  "method": "POST",
  "body": JSON.stringify({
    "username": "Gravita",
    "email": "gravita@gravita.local",
    "password": "1111"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/auth/userinfo` информация о текущем пользователе
```javascript
await ( await fetch("https://АДРЕС ЛК/auth/userinfo", {
  "method": "GET"
}) ).json()
```
#### Личный кабинет: Управление скинами
- Загрузка скина (SLIM)
```
curl -H "Authorization: Bearer TOKEN" -F variant=slim -F file="@/path/to/file.png"  https://АДРЕС ЛК/cabinet/upload/skin
```
- Загрузка скина (DEFAULT)
```
curl -H "Authorization: Bearer TOKEN" -F variant=default -F file="@/path/to/file.png"  https://АДРЕС ЛК/cabinet/upload/skin
```
- Загрузка плаща
```
curl -H "Authorization: Bearer TOKEN" -F variant=default -F file="@/path/to/file.png"  https://АДРЕС ЛК/cabinet/upload/cloak
```
#### Личный кабинет: Безопасность
- `/cabinet/changepassword` смена пароля
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/changepassword", {
  "method": "POST",
  "body": JSON.stringify({
    "oldPassword": "1111",
    "newPassword": "2222"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/cabinet/prepare2fa` подключение 2FA: получение секрета
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/prepare2fa", {
  "method": "POST",
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/cabinet/enable2fa` подключение 2FA: активация
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/enable2fa", {
  "method": "POST",
  "body": JSON.stringify({
    "secret": "SECRET",
    "code": "123456"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/cabinet/disable2fa` отключение 2FA
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/disable2fa", {
  "method": "POST",
  "body": JSON.stringify({
    "code": "123456"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
#### Личный кабинет: экономика
- `/cabinet/money/balance/page/{pageId}` получить список счетов по страницам
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/money/balance/page/0", {
  "method": "GET"
}) ).json()
```
- `/cabinet/money/balance/id/{id}/transactions/page/{pageId}` получить список транзакций по страницам
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/money/balance/id/1/transactions/page/0", {
  "method": "GET"
}) ).json()
```
- `/cabinet/money/balance/currency/{currency}` получить счет по его валюте
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/money/balance/currency/DONATE", {
  "method": "GET"
}) ).json()
```
- `/cabinet/money/balance/id/{id}` получить ваш счет по id
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/money/balance/id/1", {
  "method": "GET"
}) ).json()
```
- `/cabinet/money/transfer`
```
TODO
```
#### Личный кабинет: Пополнение баланса
- `/cabinet/payment/create` создает новый платеж с заданной суммой и выдает ссылку на оплату
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/payment/create", {
  "method": "POST",
  "body": JSON.stringify({
    "system": "Yoo",
    "sum": 50.0
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
#### Бан-лист
- `/banlist/userId/{userId}` просмотр информации о бане пользователя
```javascript
await ( await fetch("https://АДРЕС ЛК/banlist/userid/1", {
  "method": "GET"
}) ).json()
```
- `/banlist/page/{pageId}` просмотр информации о банах по страницам
```javascript
await ( await fetch("https://АДРЕС ЛК/banlist/page/0", {
  "method": "GET"
}) ).json()
```
#### Пользователи
- `/users/id/{id}` просмотр информации о пользователе по ID
```javascript
await ( await fetch("https://АДРЕС ЛК/users/id/1", {
  "method": "GET"
}) ).json()
```
- `/users/uuid/{uuid}` просмотр информации о пользователе по UUID
```javascript
await ( await fetch("https://АДРЕС ЛК/users/uuid/UUID", {
  "method": "GET"
}) ).json()
```
- `/users/name/{name}` просмотр информации о пользователе по username
```javascript
await ( await fetch("https://АДРЕС ЛК/users/name/Gravita", {
  "method": "GET"
}) ).json()
```
- `/users/name/{name}` просмотр информации о пользователях по страницам
```javascript
await ( await fetch("https://АДРЕС ЛК/users/page/0", {
  "method": "GET"
}) ).json()
```
- DELETE `/users/id/{id}` удаление пользователя по ID  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/users/id/1", {
  "method": "DELETE"
}) ).json()
```
- DELETE `/users/id/{id}/status` удаление статуса пользователя по ID  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/users/id/1/status", {
  "method": "DELETE"
}) ).json()
```
#### Новости
- `/news/page/{pageId}` просмотр новостей по страницам
```javascript
await ( await fetch("https://АДРЕС ЛК/news/page/0", {
  "method": "GET"
}) ).json()
```
- `/news/id/{id}` просмотр новости по ID
```javascript
await ( await fetch("https://АДРЕС ЛК/news/id/1", {
  "method": "GET"
}) ).json()
```
- `/news/id/{id}/newcomment` написать комментарий к новости
```javascript
await ( await fetch("https://АДРЕС ЛК/news/id/1/newcomment", {
  "method": "POST",
  "body": JSON.stringify({
    "text": "LOL"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/news/new` написать новую новость  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/news/new", {
  "method": "PUT",
  "body": JSON.stringify({
    "header": "HEADER",
    "miniText": "MINI TEXT",
    "text": "TEXT"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/news/id/{id}/update` обновить новость  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/news/id/1/update", {
  "method": "PUT",
  "body": JSON.stringify({
    "header": "HEADER",
    "miniText": "MINI TEXT",
    "text": "TEXT"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
#### Курсы валют
