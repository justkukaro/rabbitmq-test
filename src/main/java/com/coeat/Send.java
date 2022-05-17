package com.coeat;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(35672);
        factory.setUsername("uguest");
        factory.setPassword("pguest");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            for (int i = 0; i <= 1000; i++) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                String message = "Hello World!" + (int) (Math.random() * 100);
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Set '" + message + "'");
                Thread.sleep(10);
            }
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
