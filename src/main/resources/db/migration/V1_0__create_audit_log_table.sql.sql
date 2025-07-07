-- Crear secuencia explícita
CREATE SEQUENCE audit_log_seq START WITH 1 INCREMENT BY 1;

-- Crear tabla con uso de secuencia en la columna ID
CREATE TABLE audit_log (
    id BIGINT PRIMARY KEY DEFAULT nextval('audit_log_seq'),
    username VARCHAR(100),
    action VARCHAR(255),
    method VARCHAR(255),
    endpoint VARCHAR(255),
    http_method VARCHAR(10),
    request_body TEXT,
    response_body TEXT,
    status_code INTEGER,
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
