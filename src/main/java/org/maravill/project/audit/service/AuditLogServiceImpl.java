package org.maravill.project.audit.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.maravill.project.audit.models.AuditLog;
import org.maravill.project.audit.repository.IAuditLogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements IAuditLogService {

    private final IAuditLogRepository auditLogRepository;
    private final HttpServletRequest request;

    @Override
    public void log(String action, String methodName, String endpoint, String httpMethod,
                    String requestBody, String responseBody, Integer statusCode) {

        String username = extractUsername();
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        AuditLog auditLog = AuditLog.builder()
                .username(username)
                .action(action)
                .method(methodName)
                .endpoint(endpoint)
                .httpMethod(httpMethod)
                .requestBody(requestBody)
                .responseBody(responseBody)
                .statusCode(statusCode)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .createdAt(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
    }

    private String extractUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            return "admin";
        }
    }
}
