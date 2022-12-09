package com.appstracta.kafka;

import com.appstracta.movement.IMovementService;
import com.appstracta.movement.Movement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

	private final IMovementService service;

	@KafkaListener(topics = "tbank", containerFactory = "bankListener")
	public void consumer(String registro) throws JsonProcessingException {
		log.info("Consumendo del topic tbank: " + registro);

		ObjectMapper objectMapper = new ObjectMapper();
		Movement movement = objectMapper.readValue(registro.trim(), Movement.class);

		service.save(movement);
	}

}
