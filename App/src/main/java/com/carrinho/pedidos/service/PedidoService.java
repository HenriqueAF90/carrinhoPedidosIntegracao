package com.carrinho.pedidos.service;

import com.carrinho.pedidos.dto.PedidoDTO;
import com.carrinho.pedidos.entity.Pedido;
import com.carrinho.pedidos.repository.PedidoRepository;
import com.carrinho.pedidos.sqs.PedidoSqsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Service
public class PedidoService {
    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoSqsSender sqsSender;

    @Value("${pedidos.useSqs:false}")
    private boolean useSqs;

    public Pedido processarPedido(PedidoDTO dto) {

        if (dto.getIdPedido() == null || dto.getProdutos() == null || dto.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("Pedido inválido");
        }
        
        String status = "EM_PROCESSAMENTO";
        if (dto.getValorTotal() != null && dto.getValorTotal() > 0) {
            status = "PROCESSADO";
        }
        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        pedido.setProdutos(dto.getProdutos());
        pedido.setIdCliente(dto.getIdCliente());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setStatus(status);
        logger.info("Processando pedido: {}", dto.getIdPedido());
        Pedido salvo = pedidoRepository.save(pedido);
        // Mock: envia status para SQS se configurado, senão só loga
        if (useSqs) {
            sqsSender.enviarStatus(dto.getIdPedido(), status);
        } else {
            logger.info("[MOCK] Status do pedido '{}' enviado para SQS: {}", dto.getIdPedido(), status);
        }
        return salvo;
    }

    public Optional<Pedido> buscarPorIdPedido(String idPedido) {
        return pedidoRepository.findByIdPedido(idPedido);
    }
}
