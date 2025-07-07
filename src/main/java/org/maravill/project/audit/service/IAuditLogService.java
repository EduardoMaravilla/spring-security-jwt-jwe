package org.maravill.project.audit.service;

public interface IAuditLogService {

    void log(String action, String methodName, String endpoint, String httpMethod,
             String requestBody, String responseBody, Integer statusCode);
}
