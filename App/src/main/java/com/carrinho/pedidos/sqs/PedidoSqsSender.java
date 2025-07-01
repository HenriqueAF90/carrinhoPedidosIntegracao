package com.carrinho.pedidos.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoSqsSender {
    private static final Logger logger = LoggerFactory.getLogger(PedidoSqsSender.class);
    private final String queueName;

    public PedidoSqsSender(@Value("${aws.sqs.queue:pedidos-status-queue}") String queueName) {
        this.queueName = queueName;
    }

    public void enviarStatus(String idPedido, String status) {
        String mensagem = String.format("{\"idPedido\":\"%s\",\"status\":\"%s\"}", idPedido, status);
        logger.info("[MOCK] Enviando status para SQS: {} (fila: {})", mensagem, queueName);
        // Aqui seria feita a chamada real para o SQS
    }
}
