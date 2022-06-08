//package kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.producer.*;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import java.util.Properties;
//import java.util.concurrent.ExecutionException;
//
//@Slf4j
//public class Producers {
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Properties properties = new Properties();
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"123.56.91.14:9092");
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
//        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);
//        for (int i = 0; i < 5; i++) {
//            //同步
//            producer.send(new ProducerRecord<>("test2","hello"+i)).get();
//
//            //producer.send(new ProducerRecord<>("test2","hello"+i));
////            producer.send(new ProducerRecord<>("test2", "hello" + i), new Callback() {
////                @Override
////                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
////                    log.info("{}",recordMetadata.partition());
////                    log.info("{}",recordMetadata.topic());
////
////                }
////            });
//
//        }
//        producer.close();
//    }
//}
