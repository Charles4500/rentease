-- Create units table
BEGIN;

CREATE TABLE IF NOT EXISTS units (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    room_no VARCHAR(50) NOT NULL,
    room_type VARCHAR(255) NOT NULL,
    rent_amount NUMERIC(12, 2) NOT NULL,
    occupied BOOLEAN NOT NULL DEFAULT FALSE,
    property_id BIGINT NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    CONSTRAINT fk_units_property
        FOREIGN KEY (property_id)
        REFERENCES properties(id)
        ON DELETE CASCADE,
    CONSTRAINT uk_units_property_room
        UNIQUE (property_id, room_no)
);

CREATE INDEX idx_units_property_id ON units(property_id);
CREATE INDEX idx_units_created_date ON units(created_date);
CREATE INDEX idx_units_deleted_date ON units(deleted_date);

COMMIT;