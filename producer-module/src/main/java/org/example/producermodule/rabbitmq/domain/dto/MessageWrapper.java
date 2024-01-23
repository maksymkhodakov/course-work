package org.example.producermodule.rabbitmq.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageWrapper<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;

    private Collection<T> data;
    private String message;
}
