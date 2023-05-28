# Online Market

## Versions
* Tomcat 9.0.35
* Java 11
* Hibernate 5.6.5.Final
* Wicket 9.11.0

## User manual
Please use IDE so the Tomcat could be automatically configured according to the deployment configuration (the build is located in online-market/.idea/artifacts/online_market_war_exploded.xml). The app will be launched on the following address: <tomcat_configuration>/login. Make sure to use the Wicket version mentioned above to avoid any version conflicts.

## Database connection (hibernate.cfg)
* Address: jdbc:postgresql://localhost:<port>/market
* Username: postgres
* Password: root

DB dump could be found in the root folder (market.sql).

## Smoke test

You could use (```UpdateServlet```) alongside custom python script to send a request using the following endpoint ```<tomcat_configuration>/updateProduct```. The servlet will change the quantity of the specified product until it reaches zero quantity. Please make sure to change the quantity and version to their current database values so the json deserialization in servlet could work properly. The script could be launched from the terminal using the python 3 (with the ```requests``` module): ```python3 update.py```.

Script could be found in the root folder (update.py).

## App login credentials
* User: user/user
* Admin: admin/admin
