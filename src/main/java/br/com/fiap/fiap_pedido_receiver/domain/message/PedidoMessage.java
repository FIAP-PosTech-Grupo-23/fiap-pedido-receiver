package br.com.fiap.fiap_pedido_receiver.domain.message;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record PedidoMessage(
        UUID idCliente,
        List<ItemMessage> produtos,
        PagamentoMessage pagamento
) {
}