package no.bekk.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import scala.Tuple2;
import twitter4j.HashtagEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import static java.util.Arrays.asList;

public class TwitterApp {

    public static void run() throws IOException, URISyntaxException {
        setTwitterConfig();

        SparkConf conf = new SparkConf().setAppName("twitterApp");
        JavaStreamingContext streamingContext =
                new JavaStreamingContext(conf, Durations.seconds(3));

        TwitterUtils.createStream(streamingContext)
                .window(Durations.seconds(180), Durations.seconds(6))
                .filter(status -> "en".equals(status.getLang()))
                .flatMap(status -> asList(status.getHashtagEntities()))
                .countByValue()
                .foreachRDD(rdd -> {
                    List<Result> data = getTopTen(rdd);
                    CustomWebSocketServlet.broadcastMessage(data);
                });

        streamingContext.start();
        streamingContext.awaitTermination();
    }

    private static List<Result> getTopTen(JavaPairRDD<HashtagEntity, Long> rdd) {
        return rdd.map(Tuple2::swap)
                .sortBy(tuple -> tuple._1, false, 1)
                .map(Result::new)
                .take(10);
    }

    private static void setTwitterConfig() throws IOException {
        Properties twitterProperties = new Properties();
        InputStream twitterConfig = TwitterApp.class.getResourceAsStream("/twitter.properties");

        if (twitterConfig != null) {
            twitterProperties.load(twitterConfig);
            System.getProperties().putAll(twitterProperties);
        }
    }
}
