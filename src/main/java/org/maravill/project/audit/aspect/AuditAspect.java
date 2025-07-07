package org.maravill.project.audit.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.maravill.project.audit.annotation.Auditable;
import org.maravill.project.audit.service.IAuditLogService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final IAuditLogService auditLogService;
    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;

    @Around("@annotation(org.maravill.project.audit.annotation.Auditable)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Auditable auditable = method.getAnnotation(Auditable.class);

        assert auditable != null;
        String action = auditable.action();
        String methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        String endpoint = request.getRequestURI();
        String httpMethod = request.getMethod();

        String requestBody = extractRequestBody(joinPoint);
        Object result = null;
        String responseBody;
        int statusCode = 200;

        try {
            result = joinPoint.proceed();
            responseBody = objectMapper.writeValueAsString(result);
        } catch (Exception ex) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseBody = "{\"error\":\"" + ex.getMessage() + "\"}";
        }

        auditLogService.log(
                action,
                methodName,
                endpoint,
                httpMethod,
                requestBody,
                responseBody,
                statusCode
        );

        return result;
    }

    private String extractRequestBody(ProceedingJoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            return objectMapper.writeValueAsString(args);
        } catch (Exception e) {
            log.warn("No se pudo serializar el cuerpo de la petición", e);
            return null;
        }
    }
}