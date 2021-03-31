import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            SparkConf sparkConf = new SparkConf();
            sparkConf.setAppName("ReadKafkaTopic");
            sparkConf.setMaster("local");

            JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(1));

            Map<String, Object> kafkaParams = new HashMap<>();
            kafkaParams.put("bootstrap.servers", "localhost:9092");
            kafkaParams.put("key.deserializer", IntegerDeserializer.class.getName()); //
            kafkaParams.put("value.deserializer", ClassXDeserializer.class.getName()); //
            kafkaParams.put("group.id", "0");
            kafkaParams.put("auto.offset.reset", "latest");
            kafkaParams.put("enable.auto.commit", false);
            Set<String> topics = new HashSet<>();
            topics.add("TutorialTopic");

            JavaInputDStream<ConsumerRecord<Integer, ClassX>> messages =
                    KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent(),
                            ConsumerStrategies.<Integer, ClassX> Subscribe(topics, kafkaParams));

            messages.mapToPair(record -> new Tuple2<>(record.key(), record.value())).print();

            /*JavaDStream<ConsumerRecord<Integer, ClassX>> filter = messages.filter(x -> x.value().getValue() < 50);
            JavaDStream<ClassX> filteredRes = filter.map(ConsumerRecord::value);
            filteredRes.print();*/

            streamingContext.start();
            streamingContext.awaitTermination();
            System.out.println("Bitti");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
