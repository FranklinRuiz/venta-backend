package pe.utp.venta.config.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

public class VentaMessageSource extends ReloadableResourceBundleMessageSource {
    private static final String SHORT_MESSAGE_SUFIX = ".short";
    private static final String DEFAULT_MESSAGE = "Error";
    private static final String DEFAULT_MESSAGE_SHORT = "ERROR";
    private final String defaultMessage;
    private final String defaultShortMessage;

    public VentaMessageSource(String defaultCode, long cacheMillis, String... baseNames) {
        this.setBasenames(baseNames);
        this.setCacheMillis(cacheMillis);
        this.setDefaultEncoding(StandardCharsets.UTF_8.name());
        defaultMessage = getMessage(defaultCode, null, DEFAULT_MESSAGE, LocaleContextHolder.getLocale());
        defaultShortMessage = getMessage(defaultCode + SHORT_MESSAGE_SUFIX, null, DEFAULT_MESSAGE_SHORT, LocaleContextHolder.getLocale());
    }

    public Message getMessage(String code) {
        String message = getMessage(code, null, defaultMessage, LocaleContextHolder.getLocale());
        String shortMessage;
        if (isDefaultMessage(message)) {
            shortMessage = defaultShortMessage;
        } else {
            shortMessage = getMessage(code + SHORT_MESSAGE_SUFIX, null, defaultShortMessage, LocaleContextHolder.getLocale());
        }
        return new Message(message, shortMessage);
    }

    @Override
    protected String getDefaultMessage(String code) {
        return defaultMessage;
    }

    private boolean isDefaultMessage(String msg) {
        return msg != null && msg.equals(defaultMessage);
    }

    @Getter
    @RequiredArgsConstructor
    public static class Message {
        private final String message;
        private final String shortMessage;
    }
}

