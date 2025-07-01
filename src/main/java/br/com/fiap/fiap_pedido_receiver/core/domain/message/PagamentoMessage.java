package br.com.fiap.fiap_pedido_receiver.core.domain.message;

import lombok.Builder;

@Builder
public record PagamentoMessage(
        String numeroCartao,
        String cvv,
        String dataVencimento
){}