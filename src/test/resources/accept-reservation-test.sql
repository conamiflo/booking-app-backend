INSERT INTO users(
    role, email, address, is_active, last_name, name, notifications, password, phone_number, photo, average_score)
VALUES ('Owner', 'o@gmail.com', 'asd', true, 'Test', 'Test', false, '$2a$10$CvHlPJxIP5nleqIY7Pbdh.iXKI.UMFaUaBt1bUai.D0G/C1yw4e6O', '12345678', '', 0);

INSERT INTO users(
    role, email, address, is_active, last_name, name, notifications, password, phone_number, photo, average_score)
VALUES ('Guest', 'g@gmail.com', 'asd', true, 'Test', 'Test', false, '$2a$10$nLrAt9ekO4v.fn7gXufR6OBbBWQlDCsTfEkl0bY6qbs6.MneqBYs2', '12345678', '', null);

INSERT INTO accommodations(
    id, active, automatic_approval, cancelation_days, created, default_price, description, location, max_guests, min_guests, name, price_type, status, type, owner_email)
VALUES (1, true, false, 3, 1705708800, 100, 'fesgf', 'asdfe', 4, 1, 'Acc', 1, 0, 'Studio', 'o@gmail.com');

INSERT INTO availabilities(
    id, end_epoch_time, start_epoch_time)
VALUES (1, 1706486400, 1706227200);

INSERT INTO accommodations_availability(
    accommodation_id, availability_id)
VALUES (1, 1);

INSERT INTO reservations(
    id, end_date, number_of_guests, price, start_date, status, accommodation_id, guest_email)
VALUES (1, 1706486400, 2, 200, 1706227200, 0, 1, 'g@gmail.com');