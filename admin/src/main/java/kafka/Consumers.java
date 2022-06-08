//package kafka;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.Properties;
//
//public class Consumers {
//    public static void main(String[] args) {
//        Properties properties = new Properties();
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"123.56.91.14:9092");
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
//        //配置group——id
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test");
//        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
//        ArrayList<String> topic = new ArrayList<>();
//        topic.add("test2");
//        consumer.subscribe(topic);//订阅主题
//        while (true){
//            ConsumerRecords<String, String> poll =
//                    consumer.poll(Duration.ofSeconds(1));
//            poll.forEach(v->{
//                System.out.println(v);
//            });
//        }
//    }
//}
