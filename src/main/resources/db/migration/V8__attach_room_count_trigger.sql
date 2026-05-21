-- Attach trigger to rooms table
CREATE TRIGGER after_room_change
AFTER INSERT OR DELETE ON units
FOR EACH ROW
EXECUTE FUNCTION update_room_count();