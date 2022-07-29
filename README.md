# Online Market

## Тестовый стенд:
* Tomcat 9.0.35
* Wicket 9.11.0
* Java 11
* Postgresql 42.4.0
* Hibernate 5.6.5.Final

## Как запускал:
Запускал из IDE, поэтому Tomcat автоматически в соответствии с настройками деплоил созданный war и прилож запускался на https://localhost:8087/Market_war_exploded/login. Важно использовать именно 9 Tomcat, из-за какой-то странной ошибки текущая версия Wicket'а с 10-м конфликтует (вроде бы распространенная ошибка, судя по стаку и тикетам ни гитхабе).

## Подключение к БД (по настройкам в hibernate.cfg):
* Адрес: jdbc:postgresql://localhost:5432/market
* Юзернейм: postgres
* Пароль: root

Дамп БД лежит в корневой папке проекта (market.sql).

## Как проверял нагрузку: 

Создал сервлет (```UpdateServlet```) и отдельный скрипт на python, который запросом на эндпоинт ```http://localhost:8087/Market_war_exploded/updateProduct``` в цикле изменяет количество определенного продукта, пока оно не станет 0. Сам скрипт прикладываю на всякий случай, для корректной работы нужно изменить значение переменных quantity и version на актуальные значения из БД для нужного продукта (нужно для корректной десериализации json в сервлете). Запускается из консоли с помощью python 3 (с установленным модулем ```requests```): ```python3 update.py```. Тем не менее осталась проблема, что в какой-то момент подключение к БД иногда заваливаться. Но судя по всей информации, которую удалось найти, проблема именно в драйвере, а не в кол-во совершаемых операций.

Скрипт лежит в корневой папке проекта (update.py).

## Что сделал для обеспечения параллельного доступа: 

Реализовал оптимистичный лок записей у product через добавление тега Version. Метод ```decreaseQuantity()``` сделал synchronized в ```ProductController```, он отвечает за изменение кол-ва товара на складе.

## Креды для логина:
* Юзер: user/user
* Админ: admin/admin

## Из того, что не успел:

Оставил на последний момент логгер, т.к. казалось это займет совсем недолго, но что-то идет не так и логи log4j писать не хочет. Кажется, что проблема в расположении xml-файла с пропертями, т.к. делал все четко по доке. Тем не менее ни в WEB-INF, ни в resources он его как будто не видит. 
