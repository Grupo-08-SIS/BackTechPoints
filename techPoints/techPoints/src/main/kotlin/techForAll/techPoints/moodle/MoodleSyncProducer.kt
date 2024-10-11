package techForAll.techPoints.moodle

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MoodleSyncProducer(
    private val rabbitTemplate: RabbitTemplate
) {

    // Agendamento para meia-noite
    //@Scheduled(cron = "*/10 * * * * *") // Testar funcionamento
    @Scheduled(cron = "0 0 0 * * *")
    fun enviarMensagemDeSincronizacao() {
        val mensagem = "Iniciar sincronização do Moodle"
        rabbitTemplate.convertAndSend("moodleSyncQueue", mensagem)
        println("Mensagem enviada para sincronização: $mensagem")
    }
}