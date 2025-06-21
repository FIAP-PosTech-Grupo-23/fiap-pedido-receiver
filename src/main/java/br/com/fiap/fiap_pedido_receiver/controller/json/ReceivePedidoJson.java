package br.com.fiap.fiap_pedido_receiver.controller.json;

import java.util.List;
import java.util.UUID;

public record ReceivePedidoJson(
        UUID cliente,
        List<ItemPedidoJson> itens,
        String numeroCartao
) {
}
