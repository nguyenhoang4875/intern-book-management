# Book Management

     The goal of this exercise is to write a small web application using Spring Boot Framework.
     The application allows you to handle books in a library.
     It should contain five pages, namely Login, Register, Books List (search, order, pagination), Book Details, Book Creation and Book Edition


## Build

### [PostgresSQL on Docker](https://hub.docker.com/_/postgres)

#### Pull the image

```
 docker pull postgres:{tag}
```

*    **\{tag\}**: [The image tag/version](https://hub.docker.com/_/postgres#supported-tags-and-respective-dockerfile-links). Eg: *latest*, *11*,...

#### Start the container

```
docker run --name {container_name} -e POSTGRES_USER={POSTGRES_USER} -e POSTGRES_PASSWORD={POSTGRES_PASSWORD} -e POSTGRES_DB={POSTGRES_DB} -p {port}:5432 -d postgres
```

*    **\{container_name\}**: The name for the container you want to create.
*    **\{port\}**: Your local port you wish to use for Postgres.
*    **{POSTGRES_USER}**: Set the name of the default PostgreSQL superuser
*    **{POSTGRES_PASSWORD}**: Set default password for the user above
*    **{POSTGRES_DB}**: Set the name of the default PostgreSQL database

>  This project: ```docker run --name intern -e POSTGRES_USER=springintern -e POSTGRES_PASSWORD=springintern -e POSTGRES_DB=book -p 5432:5432 -d postgres ```

### build and run

```
gradle build
gradle bootRun 
```