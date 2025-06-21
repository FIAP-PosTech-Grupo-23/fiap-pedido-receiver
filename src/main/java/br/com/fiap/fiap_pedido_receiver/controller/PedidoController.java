package br.com.fiap.fiap_pedido_receiver.controller;

import br.com.fiap.fiap_pedido_receiver.controller.json.ReceivePedidoJson;
import br.com.fiap.fiap_pedido_receiver.usecase.ReceivePedidoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final ReceivePedidoUseCase receivePedidoUseCase;

    @PostMapping
    public ResponseEntity<Void> receivePedido(@RequestBody ReceivePedidoJson receivePedidoJson) {
        receivePedidoUseCase.receive(receivePedidoJson);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
