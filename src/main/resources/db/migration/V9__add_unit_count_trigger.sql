-- Drop old trigger/function if they exist (from previous attempt)
DROP TRIGGER IF EXISTS after_room_change ON units;
DROP FUNCTION IF EXISTS update_room_count();
DROP TRIGGER IF EXISTS after_unit_change ON units;


CREATE OR REPLACE FUNCTION update_property_room_count()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        UPDATE properties 
        SET no_of_rooms = COALESCE(no_of_rooms, 0) + 1 
        WHERE id = NEW.property_id;
    
    ELSIF TG_OP = 'DELETE' THEN
        UPDATE properties 
        SET no_of_rooms = COALESCE(no_of_rooms, 0) - 1 
        WHERE id = OLD.property_id;
    
    ELSIF TG_OP = 'UPDATE' THEN
        -- If property_id changed
        IF OLD.property_id != NEW.property_id THEN
            -- Remove from old property
            UPDATE properties 
            SET no_of_rooms = COALESCE(no_of_rooms, 0) - 1 
            WHERE id = OLD.property_id;
            
            -- Add to new property
            UPDATE properties 
            SET no_of_rooms = COALESCE(no_of_rooms, 0) + 1 
            WHERE id = NEW.property_id;
        END IF;
    END IF;
    
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Attach trigger to the UNITS table (not rooms)
CREATE TRIGGER after_unit_change
AFTER INSERT OR DELETE ON units
FOR EACH ROW
EXECUTE FUNCTION update_property_room_count();