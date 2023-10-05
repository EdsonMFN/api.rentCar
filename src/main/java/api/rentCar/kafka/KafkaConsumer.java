package api.rentCar.kafka;

import api.rentCar.domains.entity.Rent;
import api.rentCar.service.RentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    @Autowired
    private RentService rentService;

    @KafkaListener(topics = "test.kafka2",groupId = "kafka")
    public void consumer(String json, @Header(KafkaHeaders.OFFSET) Long offset,@Header(KafkaHeaders.TOPIC)String topicKafka,@Header(KafkaHeaders.GROUP_ID)String groupIdKafka){

        log.info("--> Consumindo fila. Topico: {} - Grupo: {} .", topicKafka, groupIdKafka );
        log.info("CampanhaPortadorEnvioConsumer received. Offset: {}  - payload: {} ", offset, json);

        Rent rent = null;
        try {
            rent = new ObjectMapper().readValue(json, Rent.class);
        } catch (Exception e) {
            throw new RuntimeException("erro no consumer");
        }
    }
}
