package br.com.fiap.fiap_pedido_receiver.usecase;

import br.com.fiap.fiap_pedido_receiver.config.rabbitmq.RabbitMQConfig;
import br.com.fiap.fiap_pedido_receiver.domain.message.PedidoMessage;
import br.com.fiap.fiap_pedido_receiver.exception.ExternalResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivePedidoUseCase {

    private final RabbitTemplate rabbitTemplate;

    public void receive(PedidoMessage pedidoMessage) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, pedidoMessage);
        } catch (AmqpException e) {
            throw new ExternalResourceException("Erro ao adicionar pedido à queue do RabbitMQ.");
        }
    }
}
