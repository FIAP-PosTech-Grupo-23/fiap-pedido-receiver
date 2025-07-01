package br.com.fiap.fiap_pedido_receiver.adapter;

import br.com.fiap.fiap_pedido_receiver.adapter.json.PedidoJson;
import br.com.fiap.fiap_pedido_receiver.core.domain.message.ItemMessage;
import br.com.fiap.fiap_pedido_receiver.core.domain.message.PagamentoMessage;
import br.com.fiap.fiap_pedido_receiver.core.domain.message.PedidoMessage;
import br.com.fiap.fiap_pedido_receiver.core.usecase.ReceivePedidoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final ReceivePedidoUseCase receivePedidoUseCase;

    @PostMapping
    public ResponseEntity<Void> receivePedido(@RequestBody PedidoJson pedidoJson) {
        PedidoMessage pedidoMessage = PedidoMessage.builder()
                        .idCliente(pedidoJson.idCliente())
                        .produtos(pedidoJson.produtos().stream()
                                .map(ij -> ItemMessage.builder()
                                                .sku(ij.sku())
                                                .quantidade(ij.quantidade())
                                                .build())
                                .collect(Collectors.toList()))
                        .pagamento(PagamentoMessage.builder()
                                .numeroCartao(pedidoJson.pagamento().numeroCartao())
                                .cvv(pedidoJson.pagamento().cvv())
                                .dataVencimento(pedidoJson.pagamento().dataVencimento())
                                .build())
                        .build();
        receivePedidoUseCase.receive(pedidoMessage);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
