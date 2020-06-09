# murder-backend

A backend application for organizing [assassin games](http://www.games-wiki.org/wiki/Assassin_game/). Look [here](https://github.com/elite-se/elite-se.murder-app) for the corresponding frontend app.

## Usage

### How to get it running

A JDBC enabled database is required, as well as JDK 13.

Set at least the following environment variables, pass them later on when executing the JAR or add them to a Spring config file:
* `spring.datasource.url`: where to find the database
* `spring.datasource.username`: database user
* `spring.datasource.password`: password for the database user
* `murder.jwt.secret`: secret used to sign the JWT tokens
 
Run
```$xslt
cd backend
./mvnw package
java -jar target/*.jar
``` 

### Communicating with the server

There is no API documentation yet. You find the endpoints in the [webservice package](./backend/src/main/java/de/marvinbrieger/toothbrushgame/webservice).
Data is provided and required as JSON. You may face errors if you do not add a `Content-Type` header with value `application/json`.

### How to pass the security layer

Register a device by sending a POST request to the `/user/sign-up` endpoint with something like following JSON as content:
```$json
{
 "deviceId": "⟨put whatever you want⟩",
 "password": "⟨put whatever you want⟩"
}
```

Once you registered a device, you can retrieve a JWT by putting the same content to the `/user/login` endpoint. The token will be located in the `Authorization` header. Just repeat this header whenever you send requests. If it expired, just log in again to retrieve a new one.


## Contributing

We manage the current tasks in the following JIRA project: https://murder-game.atlassian.net/browse/MUR

### Board usage

If you like to implement a story that lies in the backlog set its status to "Selected for development". In the board you can add subtasks that contain the actual work for the story. Please pay attention that the sub tasks must be set to "Selected for development" manually.

### Branching and commit naming

Please create a new branch for each user story you like to implement. Name the branch as follows: If the story has the issue number 1 and the title "Create game" then the branch should be called `mur_1_create_game`.

Please name your commits with the issue number preciding. For example a commit to the issue _MUR-1_ could be named:
`MUR-1: Create game code service`
