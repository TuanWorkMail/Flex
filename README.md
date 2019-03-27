**1st level:** Java core that watch a folder for changes and update media library with media infos

**2nd level:** Spring app that get data from 2nd level and communicate with UI

**3rd level:** Angular app that interact with end user

## Prototype 1 (Deadline 13/3)
**1st level:**
- Watch directories for changes
- On change look up file title
- Print result to console
- Save to DB (text)

## Features
**1st level:**
- Watch directories for changes
- If new file created, get from filename these informations: movie/TV name, episode number and create that in DB
- If file is deleted, if it is the last episode, delete from DB

## Flow:
**1st level:**
- get all watch directories
- for each changed files
  - extract video name from filename
  - look up video type from the video name from the internet
  - if it is a series
    - extract episode from filename
  - if it is a file creation
    - if it is a movie
      - create a new movie with that video
    - else
      - if series does not exists
        - create a new series with that video
      - else
        - add the video to the existing series
  - else
    - if it is a movie
      - delete movie
    - else
      - if it is the only/last episode
        - delete series
      - else
        - delete episode
                
## pseudocode:
**1nd level:**
```java
public static void main(String[] args)
    Path[] paths = getPathsFromDatabase()

    paths.forEach ->
        onCreateOrDeleteEvent ->

```
