package br.com.fiap.fiap_pedido_receiver.controller.json;

public record PagamentoJson(
        String numeroCartao,
        String cvv,
        String dataVencimento
){}