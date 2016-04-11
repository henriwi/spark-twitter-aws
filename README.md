# Spark Streaming

To run this project locally the following steps needs to be executed:

- Download spark from http://spark.apache.org/downloads.html
- Go to https://apps.twitter.com
- Select "Create new app". Use a random name and description, and "http://example.org" as url
- Navigate to "Keys and Access Tokens" and copy the `Consumer Key (API Key)`, `Consumer Secret (API Secret)`, `Access Token` and `Access Token Secret`
- Create a file called `twitter.properties` in the folder `src/main/resources`
- The file must contain the following:

```
oauth.consumerKey=<your-key>
oauth.consumerSecret=<your-secret>
oauth.accessToken=<your-token>
oauth.accessTokenSecret<your-secret>
```

- Build the application with `mvn clean install`
- Submit the application to spark by running: `<spark-location>/bin/spark-submit --class no.bekk.spark.SparkApplication <path-to-target-folder>/spark-twitter-1.0.jar`
- The application runs on `http://localhost:8888`
- The Spark monitoring dashboard should be available on `http://localhost:8080`
