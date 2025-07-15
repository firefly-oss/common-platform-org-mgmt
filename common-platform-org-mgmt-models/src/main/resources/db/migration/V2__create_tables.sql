-- Create bank table
CREATE TABLE IF NOT EXISTS bank (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    logo_url VARCHAR(255),
    primary_color VARCHAR(7),
    secondary_color VARCHAR(7),
    contact_email VARCHAR(100),
    contact_phone VARCHAR(50),
    website_url VARCHAR(255),
    address_line VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(100),
    country_id BIGINT,
    time_zone_id BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    established_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT
);

-- Create bank_division table
CREATE TABLE IF NOT EXISTS bank_division (
    id BIGSERIAL PRIMARY KEY,
    bank_id BIGINT NOT NULL REFERENCES bank(id),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    UNIQUE(bank_id, code)
);

-- Create bank_region table
CREATE TABLE IF NOT EXISTS bank_region (
    id BIGSERIAL PRIMARY KEY,
    division_id BIGINT NOT NULL REFERENCES bank_division(id),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    UNIQUE(division_id, code)
);

-- Create branch table
CREATE TABLE IF NOT EXISTS branch (
    id BIGSERIAL PRIMARY KEY,
    bank_id BIGINT NOT NULL REFERENCES bank(id),
    region_id BIGINT NOT NULL REFERENCES bank_region(id),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    phone_number VARCHAR(50),
    email VARCHAR(100),
    address_line VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(100),
    country_id BIGINT,
    time_zone_id BIGINT,
    latitude FLOAT,
    longitude FLOAT,
    is_active BOOLEAN DEFAULT TRUE,
    opened_at TIMESTAMP,
    closed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    UNIQUE(bank_id, code)
);

-- Create working_calendar table
CREATE TABLE IF NOT EXISTS working_calendar (
    id BIGSERIAL PRIMARY KEY,
    bank_id BIGINT NOT NULL REFERENCES bank(id),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_default BOOLEAN DEFAULT FALSE,
    time_zone_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT
);

-- Create branch_department table
CREATE TABLE IF NOT EXISTS branch_department (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL REFERENCES branch(id),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT
);

-- Create branch_position table
CREATE TABLE IF NOT EXISTS branch_position (
    id BIGSERIAL PRIMARY KEY,
    department_id BIGINT NOT NULL REFERENCES branch_department(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT
);

-- Create calendar_assignment table
CREATE TABLE IF NOT EXISTS calendar_assignment (
    id BIGSERIAL PRIMARY KEY,
    calendar_id BIGINT NOT NULL REFERENCES working_calendar(id),
    branch_id BIGINT REFERENCES branch(id),
    department_id BIGINT REFERENCES branch_department(id),
    position_id BIGINT REFERENCES branch_position(id),
    effective_from TIMESTAMP NOT NULL,
    effective_to TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    CHECK (
        (branch_id IS NOT NULL AND department_id IS NULL AND position_id IS NULL) OR
        (branch_id IS NULL AND department_id IS NOT NULL AND position_id IS NULL) OR
        (branch_id IS NULL AND department_id IS NULL AND position_id IS NOT NULL)
    )
);

-- Create bank_holiday table
CREATE TABLE IF NOT EXISTS bank_holiday (
    id BIGSERIAL PRIMARY KEY,
    bank_id BIGINT NOT NULL REFERENCES bank(id),
    branch_id BIGINT REFERENCES branch(id),
    country_id BIGINT,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    is_recurring BOOLEAN DEFAULT FALSE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT
);

-- Create branch_hours table
CREATE TABLE IF NOT EXISTS branch_hours (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL REFERENCES branch(id),
    day_of_week day_of_week_enum NOT NULL,
    open_time TIME,
    close_time TIME,
    is_closed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    UNIQUE(branch_id, day_of_week)
);

-- Create bank_audit_log table
CREATE TABLE IF NOT EXISTS bank_audit_log (
    id BIGSERIAL PRIMARY KEY,
    bank_id BIGINT NOT NULL REFERENCES bank(id),
    action audit_action_enum NOT NULL,
    entity VARCHAR(100) NOT NULL,
    entity_id VARCHAR(50) NOT NULL,
    metadata JSONB,
    ip_address VARCHAR(45),
    user_id BIGINT,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create branch_audit_log table
CREATE TABLE IF NOT EXISTS branch_audit_log (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL REFERENCES branch(id),
    action audit_action_enum NOT NULL,
    entity VARCHAR(100) NOT NULL,
    entity_id VARCHAR(50) NOT NULL,
    metadata JSONB,
    ip_address VARCHAR(45),
    user_id BIGINT,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add indexes for foreign keys and frequently queried columns
CREATE INDEX idx_bank_division_bank_id ON bank_division(bank_id);
CREATE INDEX idx_bank_region_division_id ON bank_region(division_id);
CREATE INDEX idx_branch_bank_id ON branch(bank_id);
CREATE INDEX idx_branch_region_id ON branch(region_id);
CREATE INDEX idx_working_calendar_bank_id ON working_calendar(bank_id);
CREATE INDEX idx_branch_department_branch_id ON branch_department(branch_id);
CREATE INDEX idx_branch_position_department_id ON branch_position(department_id);
CREATE INDEX idx_calendar_assignment_calendar_id ON calendar_assignment(calendar_id);
CREATE INDEX idx_calendar_assignment_branch_id ON calendar_assignment(branch_id);
CREATE INDEX idx_calendar_assignment_department_id ON calendar_assignment(department_id);
CREATE INDEX idx_calendar_assignment_position_id ON calendar_assignment(position_id);
CREATE INDEX idx_bank_holiday_bank_id ON bank_holiday(bank_id);
CREATE INDEX idx_bank_holiday_branch_id ON bank_holiday(branch_id);
CREATE INDEX idx_branch_hours_branch_id ON branch_hours(branch_id);
CREATE INDEX idx_bank_audit_log_bank_id ON bank_audit_log(bank_id);
CREATE INDEX idx_branch_audit_log_branch_id ON branch_audit_log(branch_id);