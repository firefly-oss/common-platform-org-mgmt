-- Create enum for days of the week
CREATE TYPE day_of_week_enum AS ENUM (
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
    'SUNDAY'
);

-- Create enum for audit action types
CREATE TYPE audit_action_enum AS ENUM (
    'CREATED',
    'UPDATED',
    'DELETED',
    'ACTIVATED',
    'DEACTIVATED'
);