-- Cast for day_of_week_enum
CREATE CAST (VARCHAR AS day_of_week_enum)
    WITH INOUT AS IMPLICIT;

-- Cast for audit_action_enum
CREATE CAST (VARCHAR AS audit_action_enum)
    WITH INOUT AS IMPLICIT;