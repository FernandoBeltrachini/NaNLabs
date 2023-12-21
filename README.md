# NaNLabs
Space-x Challenge

To start the application with the default board.

```
    mvn spring-boot:run
```
There are some properties that can be overriden, but if you do, you need to change the 3 of them:
- trello.board = the id of the board that we want to use.
- trello.key = api key from trello
- trello.token = authorization token generated by trello (https://trello.com/1/authorize?expiration=never&name=MyPersonalToken&scope=read,write&response_type=token&key=KEY_ID)

```
mvn spring-boot:run -Dspring-boot.run.arguments="--trello.board=boardId --trello.key=keyId --trello.token=tokenId"
```
### Example with default board.
```
mvn spring-boot:run -Dspring-boot.run.arguments="--trello.board=ukk8ZSed --trello.key=f859ef739004493fe7e4f5f2d18992bd --trello.token=ATTA78b5fe45ca8b689b2bd4f9dc14116395e4644e95c5409d6c1bb929dc259077ac3153E41E"
```

### Trello URL <https://trello.com/b/ukk8ZSed/space-x-board>

### Exercise description:

The space-x team is designing their next launch to the international space station, they are recluting a group of the elite devs around the world and thought that you are gonna be a good fit. 

Preparations are needed and they want to start organizing their tasks management so they’ve encoment you with your first task. The developer team uses Trello as their task manager app, but their management team (the one that creates the tasks) don’t want to use it, it’s too complicated for them. Is your job to create a bridge between these two teams.

The management team wants an endpoint that they can use to create the tasks, there are 3 flavors this day, but this could change in the future. A task may be:

An issue: This represents a business feature that needs implementation, they will provide a short title and a description. All issues gets added to the “To Do” list as unassigned
```
curl --location 'localhost:8080/' \
--header 'Content-Type: application/json' \
--data '{
    "type": "issue",
    "tittle" : "Tittle",
    "description": "Description"
}'
```

A bug: This represents a problem that needs fixing. They will only provide a description, the title needs to be randomized with the following pattern: bug-{word}-{number}. It doesn't matter that they repeat internally. The bugs should be assigned to a random member of the board and have the “Bug” label.
```
curl --location 'localhost:8080/' \
--header 'Content-Type: application/json' \
--data '{
    "type": "bug",
    "description": "Description"
}'
```
A task: This represents some manual work that needs to be done. It will count with just a title and a category (Maintenance, Research, or Test) each corresponding to a label in trello. 
```
curl --location 'localhost:8080/' \
--header 'Content-Type: application/json' \
--data '{
    "type": "task",
    "tittle" : "Tittle",
    "category": "Research" 
}'
```
You need to create a post endpoint that will receive the tasks definition form the management team and create the corresponding cards in Trello API Introduction for the team to work with. Here are some examples: