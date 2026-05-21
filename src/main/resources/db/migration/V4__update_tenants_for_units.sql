-- Update tenants table for property and unit relationships
BEGIN;

ALTER TABLE tenants DROP COLUMN IF EXISTS password;

ALTER TABLE tenants ADD COLUMN IF NOT EXISTS phone VARCHAR(15);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS property_id BIGINT;
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS unit_id BIGINT;

ALTER TABLE tenants
    ADD CONSTRAINT fk_tenants_property
        FOREIGN KEY (property_id)
        REFERENCES properties(id)
        ON DELETE CASCADE;

ALTER TABLE tenants
    ADD CONSTRAINT fk_tenants_unit
        FOREIGN KEY (unit_id)
        REFERENCES units(id)
        ON DELETE CASCADE;

ALTER TABLE tenants
    ADD CONSTRAINT uk_tenants_unit
        UNIQUE (unit_id);

CREATE INDEX idx_tenants_property_id ON tenants(property_id);
CREATE INDEX idx_tenants_unit_id ON tenants(unit_id);

COMMIT;