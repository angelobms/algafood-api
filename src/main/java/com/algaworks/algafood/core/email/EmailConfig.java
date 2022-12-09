package com.algaworks.algafood.core.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.service.SendEmailService;
import com.algaworks.algafood.infrastructure.service.email.FakeSendEmailService;
import com.algaworks.algafood.infrastructure.service.email.SandboxSendEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpSendEmailService;

@Configuration
public class EmailConfig {
	
	private EmailProperties emailProperties;

	public EmailConfig(EmailProperties emailProperties) {
		this.emailProperties = emailProperties;
	}

    @Bean
    SendEmailService sendEmailService() {
        switch (emailProperties.getType()) {
            case FAKE:
                return new FakeSendEmailService();
            case SMTP:
                return new SmtpSendEmailService();
            case SANDBOX:
                return new SandboxSendEmailService();
            default:
                return null;
        }
    }
}
