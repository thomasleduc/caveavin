# Cave à vin #

## Installation ##

### Wildfly ###

Get the source
```bash
# go to your favorite directory
cd $FAVDIR/java
# get the wildfly source
wget http://download.jboss.org/wildfly/8.1.0.Final/wildfly-8.1.0.Final.zip
# unzip
unzip wildfly-8.1.0.Final.zip
# export the home variable to simpify your life
export WILDFLY_HOME=$FAVDIR/wildfly-8.1.0.Final
```

IntelliJ
On Start up wizard, go to ...
Open the project, wait for maven resolving ...
Go to "Run/Debug Configuration" and ..

### MySQL ###
(Assuming you have a mysql server already running on port 3306)

Make Mysql available for JBoss
```bash
wget http://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.0.8.zip
unzip mysql-connector-java-5.0.8.zip
mkdir -p $WILDFLY_HOME/modules/system/layers/base/com/mysql/main/
cp mysql-connector-java-5.0.8/mysql-connector-java-5.0.8-bin.jar $WILDFLY_HOME/modules/system/layers/base/com/mysql/main/
```

then in $WILDFLY_HOME/modules/system/layers/base/com/mysql/main create a file name module.xml
```xml
<?xl version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.1" name="com.mysql">
    <resources>
        <resource-root path="mysql-connector-java-5.0.8-bin.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>
```

and then enable the module with the command line admin tools ($WILDFLY_HOME/bin/jboss-cli.sh --connect)
```
 /subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql, driver-module-name=com.mysql, driver-class-name=com.mysql.jdbc.Driver)
```
The result should be : {"outcome" => "success"}

Create your mysql base :
```SQL
-- Create a new Database
CREATE DATABASE caveavin;

-- Create a MySQL User with all right on the database
GRANT ALL PRIVILEGES ON caveavin.* TO 'caveavin'@'localhost'
IDENTIFIED BY 'chatEo9dupape' WITH GRANT OPTION;
--
```

again with the command line admin tools
```
/subsystem=datasources/data-source=CavDS:add(driver-name=mysql, user-name=caveavin, password=chatEo9dupape, \
connection-url=jdbc:mysql://localhost:3306/caveavin, min-pool-size=5, \
max-pool-size=15, jndi-name=java:/jdbc/CavDS, enabled=true, validate-on-match=true, \
valid-connection-checker-class-name=org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker, \
exception-sorter-class-name=org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter)
```


## Webservices : ##

### Public : ###

/session/login              POST username password          -> auth_token
/public/stats               GET                             -> nb_cellar nb_bottle
/session/register           POST username password email    -> auth_token

## Authorized : ###
/session/logout             POST                            -> OK
/session/password           UPDATE password new_password    -> OK
/session/email              UPDATE email new_email          -> OK

/bottle/{id}                GET                             -> Bottle
/bottle                     GET                             -> Bottles
/bottle                     POST Bottle Cellar_id           -> OK
/bottle/{id}                DELETE                          -> OK
/bottle/{id}/drink          GET comment <date>              -> OK
/bottle/drunk               GET                             -> Bottles


/cellar/{id}                GET                             -> Cellar {Bottles}
/cellar                     GET                             -> Cellars
/cellar                     POST Cellar                     -> OK
/cellar                     DELETE                          -> OK
/cellar/{id}                UPDATE                          -> OK
/cellar/{id}/history        GET                             -> Cellar {Bottles drunk}
