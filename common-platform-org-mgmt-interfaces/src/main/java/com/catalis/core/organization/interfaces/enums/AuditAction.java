package com.catalis.core.organization.interfaces.enums;

/**
 * Enum representing audit action types.
 * Corresponds to the audit_action_enum in the database.
 */
public enum AuditAction {
    CREATED,
    UPDATED,
    DELETED,
    ACTIVATED,
    DEACTIVATED
}