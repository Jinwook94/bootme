CREATE TRIGGER post_after_insert AFTER INSERT ON post
    FOR EACH ROW
BEGIN
    DECLARE currentCount BIGINT;

    -- Check the current count for the new status
    SELECT count INTO currentCount FROM data_count WHERE table_name = 'post' AND status = NEW.status;

    IF currentCount IS NOT NULL THEN
        -- If an entry exists, increase the count
    UPDATE data_count
    SET count = count + 1
    WHERE table_name = 'post' AND status = NEW.status;
    ELSE
        -- If no entry exists, create a new one with count 1
        INSERT INTO data_count (table_name, status, count)
        VALUES ('post', NEW.status, 1);
END IF;
END;

CREATE TRIGGER post_after_delete AFTER DELETE ON post
    FOR EACH ROW
BEGIN
    -- Decrease the count for the deleted status
    UPDATE data_count
    SET count = count - 1
    WHERE table_name = 'post' AND status = OLD.status;
END;


CREATE TRIGGER post_after_status_update AFTER UPDATE ON post
    FOR EACH ROW
BEGIN
    DECLARE currentCount BIGINT;

    IF NEW.status != OLD.status THEN
        -- Decrease the old status count
    UPDATE data_count
    SET count = count - 1
    WHERE table_name = 'post' AND status = OLD.status;

    -- Check if there is an existing count for the new status
    SELECT count INTO currentCount FROM data_count WHERE table_name = 'post' AND status = NEW.status;

    IF currentCount IS NOT NULL THEN
            -- Increase the new status count
    UPDATE data_count
    SET count = count + 1
    WHERE table_name = 'post' AND status = NEW.status;
    ELSE
            INSERT INTO data_count (table_name, status, count)
            VALUES ('post', NEW.status, 1);
END IF;
END IF;
END;
