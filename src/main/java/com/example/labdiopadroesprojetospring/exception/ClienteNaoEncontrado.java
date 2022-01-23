package com.example.labdiopadroesprojetospring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClienteNaoEncontrado extends RuntimeException {

    public ClienteNaoEncontrado(Long id) {
        super(String.format("Cliente %d não encontrado!", id));
    }
}
