package br.com.fiap.fiap_pedido_receiver.core.usecase;

import br.com.fiap.fiap_pedido_receiver.config.RabbitMQConfig;
import br.com.fiap.fiap_pedido_receiver.core.domain.message.PedidoMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivePedidoUseCase {

    private final RabbitTemplate rabbitTemplate;

    public void receive(PedidoMessage pedidoMessage) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, pedidoMessage);
    }
}
