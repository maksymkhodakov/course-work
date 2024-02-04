package org.example.producermodule.rabbitmq.global;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GlobalConstants {
    public static final String ANIMAL_EXCHANGE_NAME = "animal-stream";
    public static final String ANIMAl_QUEUE_NAME = "animal-stream";
    public static final String ANIMAL_ROUTING_KEY = "animal-stream";
    public static final String DEV_EXCHANGE_NAME = "dev";
    public static final String DEV_QUEUE_NAME = "dev";
    public static final String DEV_ROUTING_KEY = "dev";
    public static final String DLX_NAME = "dlq-exchange";
    public static final String DLQ_NAME = "dlq-queue";
}
