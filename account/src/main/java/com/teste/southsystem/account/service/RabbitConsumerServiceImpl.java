package com.teste.southsystem.account.service;

import com.google.gson.Gson;
import com.teste.southsystem.account.model.DTO.PersonDTO;
import com.teste.southsystem.account.rabbitmq.config.RabbitMQConfig;
import com.teste.southsystem.account.repository.PersonRepository;
import org.slf4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpAdmin;

import javax.annotation.PostConstruct;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class RabbitConsumerServiceImpl implements RabbitConsumerService {

    private static Logger LOG = getLogger(RabbitConsumerServiceImpl.class);

    private final AmqpAdmin amqpAdmin;
    private final RabbitMQConfig rabbitConfig;
    private final AccountService accountService;

    @Autowired
    public RabbitConsumerServiceImpl(AmqpAdmin amqpAdmin, RabbitMQConfig rabbitConfig, AccountService accountService) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitConfig = rabbitConfig;
        this.accountService = accountService;
    }

    @PostConstruct
    public void queueSetup() {
        amqpAdmin.declareExchange(rabbitConfig.exchange());
        amqpAdmin.declareQueue(rabbitConfig.pushQueue());
        amqpAdmin.declareBinding(rabbitConfig.binding());
    }

    @Override
    @RabbitListener(queues = "${config.rabbit.account.queue}")
    public void consume(String personJSON) {
        try {
            LOG.info("Got a new message from queue: [{}]", personJSON);
            PersonDTO person = new Gson().fromJson(personJSON, PersonDTO.class);
            accountService.createAccount(person);
        } catch (AmqpException ex) {
            LOG.error("An error occurred while listening to RabbitMQ");
            throw ex;
        }
    }
}
