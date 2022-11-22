package pe.utp.venta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import pe.utp.venta.config.advice.VentaMessageSource;

@SpringBootApplication
public class VentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentaApplication.class, args);
	}

	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		VentaMessageSource messageSource = new VentaMessageSource("default", 60, "classpath:i18n/messages", "classpath:i18n/messages-legacy");
		return messageSource;
	}
}
