package no.bekk.spark;

import scala.Tuple2;
import twitter4j.HashtagEntity;

import java.io.Serializable;

public class Result implements Serializable {
    public final Long count;
    public final String hashTag;

    public Result(Tuple2<Long, HashtagEntity> t) {
        this.count = t._1;
        this.hashTag = t._2.getText();
    }
}
