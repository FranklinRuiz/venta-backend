package pe.utp.venta.config.advice;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

public abstract class VentaResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (request.getURI().getPath().startsWith("/api/") && !(body instanceof VentaResponseEntity) && !(body instanceof InputStreamResource)) {
            @SuppressWarnings("rawtypes")
            VentaResponseEntity icaaResponseEntity = new VentaResponseEntity();
            icaaResponseEntity.setData(body);
            icaaResponseEntity.setTraceId(getTraceId());
            return icaaResponseEntity;
        }
        return body;
    }

    protected abstract String getTraceId();
}