-- Create properties table
BEGIN;

CREATE TABLE IF NOT EXISTS properties (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    property_name VARCHAR(100) NOT NULL,
    no_of_rooms INTEGER,
    location VARCHAR(255) NOT NULL,
    landlord_id UUID NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP,
    deleted_date TIMESTAMP
    );

CREATE INDEX idx_properties_landlord_id ON properties(landlord_id);
CREATE INDEX idx_properties_created_date ON properties(created_date);
CREATE INDEX idx_properties_deleted_date ON properties(deleted_date);
CREATE INDEX idx_properties_location ON properties(location);

COMMIT;