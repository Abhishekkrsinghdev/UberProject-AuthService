-- 1. Clean up the old relationship in the booking table
ALTER TABLE booking
    DROP FOREIGN KEY FKh1stionm0jgsyfg7fv98trhjj,
    DROP INDEX UK2c57floc70nhp4ehcsn9ctr71,
    DROP COLUMN review_id;

-- 2. Create the column AND the constraint in the review table
ALTER TABLE booking_review
    ADD COLUMN booking_id BIGINT NOT NULL,
    ADD CONSTRAINT UK_booking_review_booking_id UNIQUE (booking_id),
    ADD CONSTRAINT FK_booking_review_booking
        FOREIGN KEY (booking_id) REFERENCES booking (id);