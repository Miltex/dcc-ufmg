package br.com.consultor.msg.consumer;

import br.com.consultor.msg.dto.NotificacaoRecord;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import java.util.logging.Logger;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/NotificacaoQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class NotificacaoMDB implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(NotificacaoMDB.class.getName());

    @Override
    public void onMessage(Message message) {
        try {
            NotificacaoRecord notificacao = message.getBody(NotificacaoRecord.class);
            LOGGER.info(() -> "Nova notificação recebida para: " + notificacao.destinatario());
            LOGGER.info(() -> "Conteúdo: " + notificacao.mensagem());
        } catch (JMSException e) {
            LOGGER.severe(() -> "Erro ao processar mensagem: " + e.getMessage());
        }
    }
}
