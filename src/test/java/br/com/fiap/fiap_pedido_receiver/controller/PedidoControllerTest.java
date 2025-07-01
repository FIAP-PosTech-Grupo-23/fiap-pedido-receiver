package br.com.fiap.fiap_pedido_receiver.controller;

import br.com.fiap.fiap_pedido_receiver.controller.json.ItemJson;
import br.com.fiap.fiap_pedido_receiver.controller.json.PagamentoJson;
import br.com.fiap.fiap_pedido_receiver.controller.json.PedidoJson;
import br.com.fiap.fiap_pedido_receiver.domain.message.PedidoMessage;
import br.com.fiap.fiap_pedido_receiver.usecase.ReceivePedidoUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class PedidoControllerTest {

    AutoCloseable mock;

    @Mock
    private ReceivePedidoUseCase receivePedidoUseCase;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldCreatePedidoMessageAndCallUseCase() {

        // Arrange
        PedidoJson pedidoJson = new PedidoJson(UUID.randomUUID(),
                List.of(new ItemJson("XYZ1234", 10L)),
                new PagamentoJson("5261 7196 7059 1831", "131", "08/2026"));

        doNothing().when(receivePedidoUseCase).receive(any(PedidoMessage.class));

        // Act
        pedidoController.receivePedido(pedidoJson);

        // Assert
        ArgumentCaptor<PedidoMessage> captor = ArgumentCaptor.forClass(PedidoMessage.class);
        verify(receivePedidoUseCase).receive(captor.capture());
        PedidoMessage createdMessage = captor.getValue();
        assertEquals("5261 7196 7059 1831", createdMessage.pagamento().numeroCartao());

    }

}