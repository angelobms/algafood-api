package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.SendEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpSendEmailService implements SendEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemakerConfiguration;
	
	@Override
	public void send(Message message) {
		try {
			MimeMessage mimeMessage = createMimeMessage(message);
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Could not send email.", e);
		}
	}

	
	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
		String body = processTemplate(message);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(emailProperties.getSender());
		helper.setTo(message.getRecipients().toArray(new String[0]));
		helper.setSubject(message.getSubject());
		helper.setText(body, true);
		return mimeMessage;
	}
	
	protected String processTemplate(Message message) {
		try {
			Template template = freemakerConfiguration.getTemplate(message.getTemplateBody());
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
		} catch (Exception e) {
			throw new EmailException("could not assemble email template.", e);
		}
	}

}
