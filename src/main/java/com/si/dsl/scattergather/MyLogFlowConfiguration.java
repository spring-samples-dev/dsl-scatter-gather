package com.si.dsl.scattergather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
public class MyLogFlowConfiguration {

    private final Logger LOGGER = LoggerFactory.getLogger(MyLogFlowConfiguration.class);


    @Bean
    public MessageChannel myLogQueue() {
        return MessageChannels
                .queue("my-log-queue", 10)
                .get();
    }

    @Bean
    public IntegrationFlow myLogFlow() {
        return IntegrationFlows
                .from(myLogQueue())
                .handle(new MessageHandler() {

                    @Override
                    public void handleMessage(Message<?> message) throws MessagingException {
                        System.out.println(" :: here :: ");
                        System.out.println("\n-------inside looger-------------" +
                                message.getPayload().toString()+
                        "\n-------inside looger-------------");
//                        LOGGER.error("Payload ----> ", message.getPayload().toString());
                    }
                })
                .handle(new LoggingHandler("debug"){

                })
                .get();
    }
}
