package com.carrinho.pedidos.repository;

import com.carrinho.pedidos.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByIdPedido(String idPedido);
}
