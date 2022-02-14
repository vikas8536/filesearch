# filesearch

# Introduction
The application exposes HTTP POST route on the / path
accept iso8601 UTC timestamps only.

Example
```
curl -XPOST localhost:9090/ -d '{"filename":"sample1.txt", "from":"2021-07-06T23:00:00Z", "to": "2020-07-06T23:00:00Z"}'
```
Output: Parsed entries within the date time range inclusively, in JSON format. Example:

```
[
    {
        "eventTime":"2000-01-01T03:05:58Z",
        "email":"test123@test.com",
        "sessionId":"97994694-ea5c-4da7-a4bb-d6423321ccd0"
    },
    {
        "eventTime":"2000-01-01T04:05:58Z",
        "email":"test456@test.com",
        "sessionId":"97994694-ea5c-4da7-a4bb-d6423321ccd1"
    }
]
```


#Architecture
Using filesystem as database and filesystem can be mounted in docker container.

# EXECUTE
export DATA_PATH=/path/to/data/filesDir

bash build.sh

bash run.sh