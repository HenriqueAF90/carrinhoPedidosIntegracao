package com.carrinho.pedidos.kafka;

import com.carrinho.pedidos.dto.PedidoDTO;
import com.carrinho.pedidos.entity.Pedido;
import com.carrinho.pedidos.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(PedidoKafkaConsumer.class);

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoKafkaProducer producer;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "pedidos", groupId = "pedidos-group")
    public void consumir(ConsumerRecord<String, String> record) {
        try {
            logger.info("Mensagem recebida do Kafka: {}", record.value());
            PedidoDTO pedidoDTO = objectMapper.readValue(record.value(), PedidoDTO.class);
            Pedido pedido = pedidoService.processarPedido(pedidoDTO);
            // Após processar, envia para o tópico de status atualizado
            producer.enviarStatus(pedidoDTO.getIdPedido(), pedido.getStatus());
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem do Kafka", e);
        }
    }
}
