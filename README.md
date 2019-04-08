# SimpleNotes:

#### Prerequisites:
  - Gradle v5.3.1
  - Mysql instance with database
  - Docker v18.06-CE
#### Installation(Centos 7):

  - Clone the repository and run the following commands
 ```
 $ git clone https://github.com/Karthikeyan298/SimpleNotes.git
 $ cd SimpleNotes/
 $ gradle build
 $ docker build -t simplenotes:latest .
 ```
  - Create env file(eg: notes.env)
  ```
  MYSQL_IP=<mysql_instance_ip>
MYSQL_PORT=<mysql_port>
MYSQL_DB=<db_name>
MYSQL_USERNAME=<mysql_username>
MYSQL_PASSWORD=<mysql_password>
SPRING_LOG_LEVEL=<spring_log_level>
APPLICATION_LOG_LEVEL=<application_log_level>
```
  - Run the following command
 ``` sh
 $ docker run --name simplenotes -d --env-file <env_file_location> --restart always -p 8080:8080 simplenotes:latest
 ```
  - Now the application started to listen on http://hostname:8080, Change hostname with your IP or hostname.
