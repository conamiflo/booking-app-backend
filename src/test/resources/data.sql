

INSERT INTO users (role, email, is_active, name, last_name, notifications, password, phone_number, average_score)
VALUES ('Owner', 'owner@example.com', true, 'Owner', 'OwnerLastName', true, 'password', '+123456789', 4.5);


INSERT INTO users (role, email, is_active, name, last_name, notifications, password, phone_number, average_score)
VALUES ('Guest', 'guest@example.com', true, 'Guest', 'GuestLastName', true, 'password', '+987654321', 4.2);


INSERT INTO accommodations (active, automatic_approval, cancelation_days, created, default_price, description, location, max_guests, min_guests, name, price_type, status, type, owner_email)
VALUES (true, true, 7, 1642972800000, 100.0, 'Description of Accommodation', 'Location of Accommodation', 5, 2, 'AccommodationName', 0, 1, 'Apartment', 'owner@example.com');

INSERT INTO reservations (end_date, number_of_guests, price, start_date, status, accommodation_id, guest_email)
VALUES (1643673600000, 2, 150.0, 1642972800000, 0, 1, 'guest@example.com');
