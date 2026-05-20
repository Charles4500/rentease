-- Create tenants table

BEGIN;

CREATE TABLE IF NOT EXISTS tenants (

    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP,
    deleted_date TIMESTAMP

    );

CREATE INDEX idx_tenants_email ON tenants(email);
CREATE INDEX idx_tenants_created_date ON tenants(created_date);
CREATE INDEX idx_tenants_deleted_date ON tenants(deleted_date);

COMMIT;