package com.carrinho.pedidos.controller;

import com.carrinho.pedidos.entity.Pedido;
import com.carrinho.pedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Buscar pedido por idPedido")
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedido(@PathVariable("id") String idPedido) {
        return pedidoService.buscarPorIdPedido(idPedido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
