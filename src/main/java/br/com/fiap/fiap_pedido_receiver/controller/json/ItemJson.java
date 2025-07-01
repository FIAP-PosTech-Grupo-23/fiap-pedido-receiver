package br.com.fiap.fiap_pedido_receiver.controller.json;

public record ItemJson(
        String sku,
        Long quantidade
){}