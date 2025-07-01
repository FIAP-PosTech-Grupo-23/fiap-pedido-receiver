package br.com.fiap.fiap_pedido_receiver.config.handler;

import br.com.fiap.fiap_pedido_receiver.exception.ExternalResourceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReceiverExceptionHandler {

    @ExceptionHandler(ExternalResourceException.class)
    public ResponseEntity<Object> handlerExternalResourceException(ExternalResourceException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
