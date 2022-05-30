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

- `/auth/logout` выход из аккаунта

```javascript
await fetch("https://АДРЕС ЛК/auth/logout", {
  "method": "POST"
})
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
- `/cabinet/security/changepassword` смена пароля
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
- `/cabinet/security/prepare2fa` подключение 2FA: получение секрета
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/prepare2fa", {
  "method": "POST",
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/cabinet/security/enable2fa` подключение 2FA: активация
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
- `/cabinet/security/disable2fa` отключение 2FA
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
- `/cabinet/money/transfer/transfer/{fromCurrency}/to/{userId}/{toCurrency}` перевод средств другому игроку/в другую валюту  
*При переводе между любыми валютами(в том числе одинаковыми) должен существовать курс валют*
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/money/transfer/transfer/ECO/to/1/ECO", {
  "method": "POST",
  "body": JSON.stringify({
    "count": 50.0,
    "comment": "COMMENT"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
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
- `/cabinet/payment/page/{pageId}` просмотр информации о платежах
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/payment/page/0", {
  "method": "GET"
}) ).json()
```
#### Личный кабинет: Сессии
- `/cabinet/sessions/page/{pageId}` просмотр информации о сессиях
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/sessions/page/0", {
  "method": "GET"
}) ).json()
```
- `/cabinet/sessions/current` просмотр информации о текущей сессии
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/sessions/current", {
  "method": "GET"
}) ).json()
```
- `/cabinet/sessions/id/{id}` просмотр информации о своей сессии по ID
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/sessions/current", {
  "method": "GET"
}) ).json()
```
- DELETE `/cabinet/sessions/id/{id}` удаление своей сессии по ID  
*Текущую сессию нельзя удалить*
```javascript
await ( await fetch("https://АДРЕС ЛК/cabinet/sessions/id/1", {
  "method": "DELETE"
}) ).json()
```
#### Магазин блоков
- `/shop/item/page/{pageId}` просмотр товаров в магазине
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/item/page/0", {
  "method": "GET"
}) ).json()
```
- `/shop/item/id/{id}` просмотр товара в магазине по ID
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/item/id/1", {
  "method": "GET"
}) ).json()
```
- `/shop/item/buy` купить товар
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/item/buy", {
  "method": "POST",
  "body": JSON.stringify({
    "id": 1,
    "quantity": 2
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/item/new` создание нового товара  
*Товар по умолчанию добавляется с available false и не показывается в списке товаров*  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/item/new", {
  "method": "PUT",
  "body": JSON.stringify({
        "displayName": "Product 1",
        "description": "Description 1",
        "price": 1.0,
        "currency": "DONATE",
        "itemName": "minecraft:stone",
        "itemExtra": null,
        "itemNbt": null,
        "itemCustom": null,
        "itemQuantity": 2,
        "server": null
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/item/id/{id}/setlimitations` изменяет органичения у товара  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/item/id/1/setlimitations", {
  "method": "POST",
  "body": JSON.stringify({
    "endDate": "2021-08-11T21:20:13.384Z",
    "count": 100,
    "groupName": null
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/item/id/{id}/update` изменяет название у товара  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/item/id/1/setlimitations", {
  "method": "POST",
  "body": JSON.stringify({
    "displayName": "The Best Product",
    "description": "The Best Description"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/item/id/{id}/setprice` изменяет цену у товара  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/item/id/1/setprice", {
  "method": "POST",
  "body": JSON.stringify({
    "price": 50.0
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/item/id/{id}/setavailable` изменяет доступность у товара  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/item/id/1/setavailable", {
  "method": "POST",
  "body": JSON.stringify({
    "available": true
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
#### Магазин привилегий
- `/shop/group/page/{pageId}` просмотр товаров в магазине
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/group/page/0", {
  "method": "GET"
}) ).json()
```
- `/shop/group/id/{id}` просмотр товара в магазине по ID
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/group/id/1", {
  "method": "GET"
}) ).json()
```
- `/shop/group/buy` купить товар
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/group/buy", {
  "method": "POST",
  "body": JSON.stringify({
    "id": 1,
    "quantity": 2
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/group/new` создание нового товара  
*Товар по умолчанию добавляется с available false и не показывается в списке товаров*  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/group/new", {
  "method": "PUT",
  "body": JSON.stringify({
        "displayName": "Product 1",
        "description": "Description 1",
        "name": "HD",
        "server": "global",
        "world": "global",
        "context": "{}",
        "expireDays": 30,
        "local": true,
        "price": 1.0,
        "currency": "DONATE",
        "stackable": true,
        "localName": "HD"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/group/id/{id}/setlimitations` изменяет органичения у товара  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/group/id/1/setlimitations", {
  "method": "POST",
  "body": JSON.stringify({
    "endDate": "2021-08-11T21:20:13.384Z",
    "count": 100,
    "groupName": null
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/group/id/{id}/update` изменяет название у товара  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/group/id/1/setlimitations", {
  "method": "POST",
  "body": JSON.stringify({
    "displayName": "The Best Product",
    "description": "The Best Description"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/group/id/{id}/setprice` изменяет цену у товара  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/group/id/1/setprice", {
  "method": "POST",
  "body": JSON.stringify({
    "price": 50.0
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/shop/group/id/{id}/setavailable` изменяет доступность у товара  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/shop/group/id/1/setavailable", {
  "method": "POST",
  "body": JSON.stringify({
    "available": true
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

- DELETE `/users/id/{id}/asset/{assetName}` удаление ассета пользователя ID и названию ассета(SKIN/CAPE)  
  *Требуются права администратора*

```javascript
await ( await fetch("https://АДРЕС ЛК/users/id/1/asset/SKIN", {
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
- `/exchangerate/page/{pageId}` просмотр курсов валют по страницам
```javascript
await ( await fetch("https://АДРЕС ЛК/exchangerate/page/0", {
  "method": "GET"
}) ).json()
```
- `/exchangerate/id/{id}` просмотр курса валют по ID
```javascript
await ( await fetch("https://АДРЕС ЛК/exchangerate/page/0", {
  "method": "GET"
}) ).json()
```
- `/exchangerate/get/{fromCurrency}/{toCurrency}` просмотр курса валют по начальной и конечной валюте
```javascript
await ( await fetch("https://АДРЕС ЛК/exchangerate/get/DONATE/ECO", {
  "method": "GET"
}) ).json()
```
- `/new` добавление нового курса валют  
*Требуются права администратора*
```javascript
await ( await fetch("https://АДРЕС ЛК/news/id/1/update", {
  "method": "PUT",
  "body": JSON.stringify({
    "fromCurrency": "DONATE",
    "toCurrency": "ECO",
    "value": 2.0,
    "unsafe": false
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
#### Администрирование: модерация
- `/admin/moderation/ban/{userId}` бан пользователя
```javascript
await ( await fetch("https://АДРЕС ЛК/admin/moderation/ban/1", {
  "method": "POST",
  "body": JSON.stringify({
    "reason": "2.4",
    "expireMinutes": "30",
    "isHardware": false
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/admin/moderation/unban/{userId}` разбан пользователя
```javascript
await ( await fetch("https://АДРЕС ЛК/admin/moderation/unban/1", {
  "method": "POST",
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
#### Администрирование: Экономика
Методы экономики имеют несколько вариантов в зависимости от имеющейся у пользователя API информации.   
Методы, помеченные как unchecked не гарантируют читаемой ошибки при пошибке в аргументах, однако они самые быстрые и выполняют меньше всего запросов к БД
- `/admin/money/userbalance/id/{id}` получение баланса пользователя по ID баланса
- `/admin/money/userbalance/userid/{userId}/{currency}` получение баланса пользователя по ID пользователя и валюте  
  *При отсутствии баланс будет создан автоматически*
- `/admin/money/userbalance/all/userid/{userId}/page/{pageId}` получение всех балансов пользователя по ID пользователя
- `/admin/money/userbalance/uuid/{userUuid}/{currency}` получение баланса пользователя по UUID пользователя и валюте  
  *При отсутствии баланс будет создан автоматически*
```javascript
await ( await fetch("https://АДРЕС ЛК/admin/money/userbalance/id/1", {
  "method": "GET"
}) ).json()
```
- `/admin/money/addmoney/unchecked/{balanceId}` Добавить монеты на баланс по его ID
- `/admin/money/addmoney/byuuid/{userUUID}/{currency}` Добавить монеты на баланс по UUID пользователя и валюте
- `/admin/money/addmoney/byid/{userId}/{currency}` Добавить монеты на баланс по ID пользователя и валюте
- `/admin/money/removemoney/unchecked/{balanceId}` Снять монеты с баланса по его ID
- `/admin/money/removemoney/byuuid/{userUUID}/{currency}` Снять монеты с баланса по UUID пользователя и валюте
- `/admin/money/removemoney/byid/{userId}/{currency}` Снять монеты с баланса по ID пользователя и валюте
```javascript
await ( await fetch("https://АДРЕС ЛК/admin/money/addmoney/unchecked/1", {
  "method": "POST",
  "body": JSON.stringify({
    "count": 50.0,
    "comment": "COMMENT"
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```
- `/admin/money/transfer/byuuid/{fromUUID}/{fromCurrency}/to/{toUUID}/{toCurrency}` перевод от пользователя к пользователю по их UUID и валюте
- `/admin/money/transfer/byid/{fromUserId}/{fromCurrency}/to/{toUserId}/{toCurrency}` перевод от пользователя к пользователю по их ID и валюте
- `/admin/money/transfer/unchecked/multicurrency/{userId}/from/{fromId}/{fromCurrency}/to/{toId}/{toCurrency}` перевод от пользователя к пользователю по ID баланса и валюте   
*Этот метод не проверяет соответствие валюты и ID баланса*  
*Если параметр selfUser false то userId игнорируется (может принимать любые значения)*
- `/admin/money/transfer/unchecked/nocurrency/{userId}/from/{fromId}/to/{toId}` перевод от пользователя к пользователю по ID баланса без учета валюты  
*Этот метод не проверяет совпадение валют и игнорирует параметр strictRate*  
*Если параметр selfUser false то userId игнорируется (может принимать любые значения)*
```javascript
await ( await fetch("https://АДРЕС ЛК/admin/money/transfer/byid/1/ECO/to/2/ECO", {
  "method": "POST",
  "body": JSON.stringify({
    "count": 50.0,
    "selfUser": true,
    "comment": "COMMENT",
    "strictRate": true
  }),
  "headers": {
    "Content-Type": "application/json"
  }
}) ).json()
```  
Если опция `strictRate` `true` то при совпадении валют перевода требуется наличие соответствующего курса  
Если опция `selfUser` `true` то инициатор платежа - владелец, иначе - администратор.
#### Администрирование: Управление HWID
#### Администрирование: Выдача предметов
#### Администрирование: Вход на сервера
#### Администрирование: Аудит
