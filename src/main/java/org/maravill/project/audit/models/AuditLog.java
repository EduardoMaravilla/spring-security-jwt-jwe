package org.maravill.project.audit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_log_seq")
    @SequenceGenerator(name = "audit_log_seq", sequenceName = "audit_log_seq", allocationSize = 1)
    private Long id;

    private String username;
    private String action;
    private String method;
    private String endpoint;
    private String httpMethod;

    @Lob
    private String requestBody;

    @Lob
    private String responseBody;

    private Integer statusCode;
    private String ipAddress;

    @Lob
    private String userAgent;

    private LocalDateTime createdAt;
}

