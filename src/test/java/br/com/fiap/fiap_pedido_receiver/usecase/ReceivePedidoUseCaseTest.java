package br.com.fiap.fiap_pedido_receiver.usecase;

import br.com.fiap.fiap_pedido_receiver.config.rabbitmq.RabbitMQConfig;
import br.com.fiap.fiap_pedido_receiver.domain.message.ItemMessage;
import br.com.fiap.fiap_pedido_receiver.domain.message.PagamentoMessage;
import br.com.fiap.fiap_pedido_receiver.domain.message.PedidoMessage;
import br.com.fiap.fiap_pedido_receiver.exception.ExternalResourceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ReceivePedidoUseCaseTest {

    AutoCloseable mock;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ReceivePedidoUseCase receivePedidoUseCase;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    private final PedidoMessage pedidoMessage = new PedidoMessage(UUID.randomUUID(),
            List.of(new ItemMessage("XYZ1234", 10L)),
            new PagamentoMessage("5261 7196 7059 1831", "131", "08/2026"));

    @Test
    void shouldSendParameterMessageToQueue() {

        // Arrange
        doNothing().when(rabbitTemplate).convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, pedidoMessage);

        // Act
        receivePedidoUseCase.receive(pedidoMessage);

        // Assert
        ArgumentCaptor<PedidoMessage> captor = ArgumentCaptor.forClass(PedidoMessage.class);
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), captor.capture());
        PedidoMessage sentMessage = captor.getValue();
        assertEquals(pedidoMessage.pagamento().numeroCartao(), sentMessage.pagamento().numeroCartao());

    }

    @Test
    void shouldThrowExternalResourceException() {

        // Arrange
        doThrow(new AmqpException("")).when(rabbitTemplate).convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, pedidoMessage);

        // Act and assert
        assertThrows(ExternalResourceException.class, () -> receivePedidoUseCase.receive(pedidoMessage));

    }
}