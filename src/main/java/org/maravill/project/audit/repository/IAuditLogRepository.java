package org.maravill.project.audit.repository;

import org.maravill.project.audit.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuditLogRepository extends JpaRepository<AuditLog,Long> {
}
