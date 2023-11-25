INSERT INTO users (email, password, is_active, name, last_name, address, phone_number, role)
VALUES ('admin@example.com', 'adminpassword', true, 'Admin', 'AdminLastName', 'AdminAddress', '1234567890', 'Admin');

INSERT INTO users (email, password, is_active, name, last_name, address, phone_number, role, average_score)
VALUES ('owner2@example.com', 'owner2password', true, 'Owner2', 'Owner2LastName', 'Owner2Address', '9876543210', 'Owner', 4.2);

INSERT INTO users (email, password, is_active, name, last_name, address, phone_number, role, cancelled_reservations)
VALUES ('guest2@example.com', 'guest2password', true, 'Guest2', 'Guest2LastName', 'Guest2Address', '5555555555', 'Guest', 2);

INSERT INTO users (email, password, is_active, name, last_name, address, phone_number, role)
VALUES ('admin2@example.com', 'admin2password', true, 'Admin2', 'Admin2LastName', 'Admin2Address', '1234567890', 'Admin');