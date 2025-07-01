package com.carrinho.pedidos.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoKafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(PedidoKafkaProducer.class);
    private static final String TOPICO_STATUS = "pedidos-status";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void enviarStatus(String idPedido, String status) {
        String mensagem = String.format("{\"idPedido\":\"%s\",\"status\":\"%s\"}", idPedido, status);
        logger.info("Enviando status para Kafka: {}", mensagem);
        kafkaTemplate.send(new ProducerRecord<>(TOPICO_STATUS, idPedido, mensagem));
    }
}
