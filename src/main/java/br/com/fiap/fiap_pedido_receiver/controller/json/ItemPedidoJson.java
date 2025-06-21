package br.com.fiap.fiap_pedido_receiver.controller.json;

public record ItemPedidoJson(
        String sku,
        Long quantidade
) {
}
