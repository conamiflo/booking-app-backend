INSERT INTO users (email, password, is_active, name, last_name, address, phone_number, notifications, photo, role)
VALUES ('owner@gmail.com', '$2a$10$ZCBpC0hvmcBUjS1L9DMx.eOlhdAxb0eb/FoiPFp634DwGIoytaS3.', true, 'John', 'Doe', 'Owner Street', '123456789', true, '', 'Owner');

INSERT INTO accommodations(
    id, active, automatic_approval, cancelation_days, created, default_price, description, location, max_guests, min_guests, name, price_type, status, type, owner_email)
VALUES (15, true, false, 3, 1705708800, 100, 'fesgf', 'asdfe', 4, 1, 'Acc', 1, 0, 'Studio', 'owner@gmail.com');

INSERT INTO reservations(
    id, end_date, number_of_guests, price, start_date, status, accommodation_id, guest_email)
VALUES (411, 1708373124, 2, 200, 1708200324, 1, 15, 'guest@gmail.com');

INSERT INTO availabilities(id,end_epoch_time, start_epoch_time)
VALUES (123, 1706731524,1706472324);
                //28.1.2024. //31.1.2024.

INSERT INTO accommodations_availability(
    accommodation_id, availability_id)
VALUES (15, 123);

INSERT INTO prices(id,price,end_epoch_time, start_epoch_time)
VALUES (123, 1709237124,1708977924);
            //29.2.2024 //26.2.2024
INSERT INTO price_list(accommodation_id, price_list_id)
VALUES (15, 123);