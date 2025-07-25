package br.com.fiap.fiap_pedido_receiver.controller.json;

import java.util.List;
import java.util.UUID;

public record PedidoJson(
        UUID idCliente,
        List<ItemJson> produtos,
        PagamentoJson pagamento
) {
}