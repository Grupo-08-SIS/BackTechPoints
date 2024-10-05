package org.techjobs.techforall.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun queue(): Queue {
        // Defina o nome da fila para comunicação
        return Queue("moodleSyncQueue", true)
    }
}
