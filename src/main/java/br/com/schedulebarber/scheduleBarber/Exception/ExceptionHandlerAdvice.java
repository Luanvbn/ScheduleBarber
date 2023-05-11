package br.com.schedulebarber.scheduleBarber.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex){
        String mensagem = "Erro interno do servidor.";
        int codigo = HttpStatus.INTERNAL_SERVER_ERROR.value();
        Map<String, Object> error = new HashMap<>();
        error.put("mensagem", mensagem);
        error.put("codigo", codigo);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @ExceptionHandler(ClientNotExistsException.class)
    public ResponseEntity<Map<String, Object>> handleClientNotExistsException(ClientNotExistsException ex) {
        String mensagem = "O Cliente não foi encontrado";
        int codigo = HttpStatus.NOT_FOUND.value();
        String detalhes = ex.getMessage();
        Map<String, Object> error = new HashMap<>();
        error.put("mensagem", mensagem);
        error.put("codigo", codigo);
        error.put("detalhes", detalhes);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AccessAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAccessAlreadyExistsException(AccessAlreadyExistsException ex) {
        String mensagem = "Já existe um acesso com esse email.";
        int codigo = HttpStatus.CONFLICT.value();
        String detalhes = ex.getMessage();
        Map<String, Object> error = new HashMap<>();
        error.put("mensagem", mensagem);
        error.put("codigo", codigo);
        error.put("detalhes", detalhes);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(BarberNotExistsException.class)
    public ResponseEntity<Map<String, Object>> handleBarberNotExistsException(BarberNotExistsException ex) {
        String mensagem = "O Barbeiro não foi encontrado";
        int codigo = HttpStatus.NOT_FOUND.value();
        String detalhes = ex.getMessage();
        Map<String, Object> error = new HashMap<>();
        error.put("mensagem", mensagem);
        error.put("codigo", codigo);
        error.put("detalhes", detalhes);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ServicoNotExistsException.class)
    public ResponseEntity<Map<String, Object>> handleBarberNotExistsException(ServicoNotExistsException ex) {
        String mensagem = "O Servico não foi encontrado";
        int codigo = HttpStatus.NOT_FOUND.value();
        String detalhes = ex.getMessage();
        Map<String, Object> error = new HashMap<>();
        error.put("mensagem", mensagem);
        error.put("codigo", codigo);
        error.put("detalhes", detalhes);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ServicoAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> ServicoAlreadyExistsException(ServicoAlreadyExistsException ex) {
        String mensagem = "O Servico já existe!";
        int codigo = HttpStatus.NOT_FOUND.value();
        String detalhes = ex.getMessage();
        Map<String, Object> error = new HashMap<>();
        error.put("mensagem", mensagem);
        error.put("codigo", codigo);
        error.put("detalhes", detalhes);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }




}
