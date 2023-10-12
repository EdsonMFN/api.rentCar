package api.rentCar.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;
@EnableKafka
@Configuration
public class KaficaConfigConsumer {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> kafkaFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> kafkaFactory = new ConcurrentKafkaListenerContainerFactory<>();

        kafkaFactory.setConsumerFactory(consumerFactory());

        return kafkaFactory;
    }

    @Bean("consumerFactory")
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumer());
    }

    @Bean("consumerConfigKafka")
    public Map<String,Object> consumer() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG,"api.rentCar.consumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"api.rentCar.group.consumer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        return props;
    }
}
