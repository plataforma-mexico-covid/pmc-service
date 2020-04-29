package mx.mexicocovid19.plataforma.service;

import java.io.StringWriter;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Profile("mailon")
public class VelocityMailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    VelocityEngine velocityEngine;

    @Autowired
    SimpleMailMessage templateMessage;

    @Override
    public void send(String to, String cc, Map<String, Object> variables, TipoEmailEnum tipoEmailEnum) throws MessagingException {
        templateMessage.setTo(to);
        if (cc != null) {
            templateMessage.setCc(cc);
        }
        templateMessage.setSubject(tipoEmailEnum.getSubject());
        send(templateMessage, convertToVelocityContext(variables), tipoEmailEnum.getTemplate());
    }

    private VelocityContext convertToVelocityContext(final Map<String, Object> hTemplateVariables){
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> row : hTemplateVariables.entrySet()) {
            context.put(row.getKey(), row.getValue());
        }
        return context;
    }

    private void send(final SimpleMailMessage msg, final VelocityContext context, String templateLocation) {
        Template template = velocityEngine.getTemplate(templateLocation);
        StringWriter writer = new StringWriter();
        template.merge( context, writer );

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(msg.getTo());
            if (msg.getCc() != null) {
                message.setCc(msg.getCc());
            }
            message.setFrom(msg.getFrom());
            message.setSubject(msg.getSubject());

            String body = writer.toString();

            message.setText(body, true);
        };

        mailSender.send(preparator);
    }
}
