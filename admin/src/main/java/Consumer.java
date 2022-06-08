//import lombok.extern.log4j.Log4j2;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//
//import java.time.Duration;
//import java.util.Collections;
//import java.util.Properties;
//@Log4j2
//public class Consumer {

//    public static final String brokerList = "123.56.91.14:9092";
//    public static final String topic = "test";
//    public static final String group = "group.demo";
//
//    public static void main(String[] args) {
//        Properties properties = new Properties();
//        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
//        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
//        properties.put("bootstrap.servers",brokerList);
//        properties.put("group.id",group);
//        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
//        consumer.subscribe(Collections.singleton(topic));
//        log.error("adsdsadasd");
//            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
//            for(ConsumerRecord record:records){
//                log.info(String.valueOf(record));
//            }
//
//    }
//}
