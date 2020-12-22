package com.teste.southsystem.account.service;

public interface RabbitConsumerService {
    void consume(String personJSON);
}
