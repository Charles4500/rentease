-- Create the trigger function
CREATE OR REPLACE FUNCTION update_room_count()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        UPDATE properties 
        SET no_of_rooms = no_of_rooms + 1 
        WHERE id = NEW.property_id;
    
    ELSIF TG_OP = 'DELETE' THEN
        UPDATE properties 
        SET no_of_rooms = no_of_rooms - 1 
        WHERE id = OLD.property_id;
    END IF;
    
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;