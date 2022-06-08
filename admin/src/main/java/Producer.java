//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//
//import java.util.Properties;
//
//public class Producer {
//    public static final String brokerlist="123.56.91.14:9092";
//    public static final String topic = "test";
//
//    public static void main(String[] args) {
//        Properties properties = new Properties();
//        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
//        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
//        properties.put("bootstrap.servers",brokerlist);
//        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);
//        ProducerRecord<String,String> record = new ProducerRecord<>(topic,"hello,kafka");
//        producer.send(record);
//        producer.close();
//    }
//}
