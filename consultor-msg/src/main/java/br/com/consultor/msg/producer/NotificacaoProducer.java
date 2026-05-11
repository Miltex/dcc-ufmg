package br.com.consultor.msg.producer;

import br.com.consultor.msg.dto.NotificacaoRecord;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.Queue;

@JMSDestinationDefinition(
    name = "java:/jms/queue/NotificacaoQueue",
    interfaceName = "jakarta.jms.Queue",
    destinationName = "NotificacaoQueue"
)
@Stateless
public class NotificacaoProducer {

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/NotificacaoQueue")
    private Queue queue;

    public void enviarNotificacao(NotificacaoRecord notificacao) {
        context.createProducer().send(queue, notificacao);
    }
}
