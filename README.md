# Online Market

## Versions
* Tomcat 9.0.35
* Java 11
* Hibernate 5.6.5.Final
* Wicket 9.11.0

## User manual
Please use IDE so the Tomcat could be automatically configured according to the deployment configuration. The app will be launched on the following address: https://localhost:8087/Market_war_exploded/login. Make sure to use the Wicket version mentioned above to avoid any version conflicts.

## Database connection (hibernate.cfg)
* Address: jdbc:postgresql://localhost:5432/market
* Username: postgres
* Password: root

DB dump could be found in the root folder (market.sql).

## Smoke test

You could use (```UpdateServlet```) alongside custom python script to send a request using the following endpoint ```http://localhost:8087/Market_war_exploded/updateProduct```. The servlet will change the quantity of the specified product until it reaches zero quantity. Please make sure to change the quantity and version to their current database values so the json deserialization in servlet could work properly. The script could be launched from the terminal using the python 3 (with the ```requests``` module): ```python3 update.py```.

Script could be found in the root folder (update.py).

## Concurrency

Product table has an optimistic lock (using the @Version tag). ```decreaseQuantity()``` method in ```ProductController``` marked as synschornized due to its relation to the products quantity.

## App login credentials
* User: user/user
* Admin: admin/admin
