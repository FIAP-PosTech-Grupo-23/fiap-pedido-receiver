package br.com.fiap.fiap_pedido_receiver.domain.message;

import lombok.Builder;

@Builder
public record ItemMessage(
        String sku,
        Long quantidade
){}