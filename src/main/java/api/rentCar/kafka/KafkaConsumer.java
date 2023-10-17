package api.rentCar.kafka;

import api.rentCar.domains.model.RentDto;
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

    @KafkaListener(topics = "test.kafka2",groupId = "api.rentCar.group.consumer")
    public void consumer(String json, @Header(KafkaHeaders.OFFSET) Long offset){

//        log.info("--> Consumindo fila. Topico: {} - Grupo: {} .", topicKafka, groupIdKafka );
        log.info("kafkaConsumer received. Offset: {}  - payload: {} ", offset, json);

        RentDto rent = null;
        try {
            rent = new ObjectMapper().readValue(json, RentDto.class);
        } catch (Exception e) {
            throw new RuntimeException("erro no consumer", e);
        }
    }
}
