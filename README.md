# Online Market

## Versions:
* Tomcat 9.0.35
* Wicket 9.11.0
* Java 11
* Hibernate 5.6.5.Final

## User manual:
Please use IDE so the Tomcat could be automatically configured according to the deployment configuration. The app will be launched on the following address: https://localhost:8087/Market_war_exploded/login. Make sure to use the Wicket version mentioned above to avoid any version conflicts.

## Database connection (hibernate.cfg):
* Address: jdbc:postgresql://localhost:5432/market
* Username: postgres
* Password: root

DB dump could be found in the root folder (market.sql).

## Stress test: 

Создан сервлет (```UpdateServlet```) и отдельный скрипт на python, который запросом на эндпоинт ```http://localhost:8087/Market_war_exploded/updateProduct``` в цикле изменяет количество определенного продукта, пока оно не станет 0. Сам скрипт прикладываю на всякий случай, для корректной работы нужно изменить значение переменных quantity и version на актуальные значения из БД для нужного продукта (нужно для корректной десериализации json в сервлете). Запускается из консоли с помощью python 3 (с установленным модулем ```requests```): ```python3 update.py```.

Script could be found in the root folder (update.py).

## Concurrency: 

Product table has an optimistic lock (using the @Version tag). ```decreaseQuantity()``` method in ```ProductController``` marked as synschornized due to its relation to the products quantity.

## Креды для логина:
* Юзер: user/user
* Админ: admin/admin
