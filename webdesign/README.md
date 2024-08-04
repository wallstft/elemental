# Project structure
The angular directory contains angular project code, the nginx contains the http-server
that references the angular director for static content and the nginx server is also configured to route
specific url's to the spring-boot server.   The springboot directory contains the springboot application that 
will be used to respond to RESTful requests that are configured in the nginx configuration.
## Angular

Build the Angular application

```cd <project-directory>/angular_nginx/angular/app```

`npm install`

To create the "dist" directory needed for the nginx server you must build the application using this command.

`ng build`

Run the angular application, this is usefully for developing the application as this command will
display the angular code changes dynamically.  

`ng server`

## nginx

MacOS

install nginx on macos.  Brew must be installed prior to installing nginx.   
https://www.javatpoint.com/installing-nginx-on-mac

`brew update`

`brew install nginx`

### Starting nginx

check to see if nginx is already running

```ps -ef | grep nginx```

if there is an nginx server already running you can stop it using this command

```nginx -s stop```

Before starting nginx edit the nginx.conf and verify that the port and angular directory is correct for your environment.
Look fo the server section in nconf.conf and verify the port and hostname are correct for the environment.

```
    server {
        listen       7070;
        server_name  localhost;
```

####angular project directory

```
       location  {
            root   /Users/kevinboyle/dev/elemental/webdesign/angular_nginx/angular/app/dist/app;
            index  index.html index.htm;
        }
```

####configuring springboot
This section will redirect RESTful requests to the springboot application.  This section should be 
changed to specify the correct url phrase and the correct hostname and port number followed by url
needed to communicate with the springboot application.
```
        location /rest {
            proxy_pass http://localhost:9091/rest;
        }
```

#### Starting nginx
Once the configuration file is correct configured for your environment you can start the nginx server
using the `nginx -p <directory containing nginx.conf> -c <nginx.conf>`command, see below for an example an
nginx command.

`nginx -p /Users/kevinboyle/dev/elemental/webdesign/angular_nginx/nginx -c nginx.conf`

Open the URL using the hostname and port configured above.   In my environment I used 
localhost and port 7070.

```http://localhost:7070/```

## springboot
The springboot project is configured to produce an executable jar-file using maven.

``` mvn clean package spring-boot:repackage```

```cd target```

```java -jar springboot-1.0-SNAPSHOT.jar```

