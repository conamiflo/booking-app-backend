-- Table: public.users

CREATE TABLE IF NOT EXISTS public.users (
                                            role VARCHAR(31) NOT NULL,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    last_name VARCHAR(255),
    name VARCHAR(255),
    notifications BOOLEAN NOT NULL,
    password VARCHAR(255),
    phone_number VARCHAR(255),
    photo VARCHAR(255),
    average_score DOUBLE,
    CONSTRAINT users_pkey PRIMARY KEY (email)
    );

-- Insert owner
INSERT INTO public.users (role, email, is_active, name, last_name, notifications, password, phone_number, average_score)
VALUES ('owner', 'owner@example.com', TRUE, 'Owner', 'OwnerLastName', TRUE, 'password', '+123456789', 4.5);

-- Insert guest
INSERT INTO public.users (role, email, is_active, name, last_name, notifications, password, phone_number, average_score)
VALUES ('guest', 'guest@example.com', TRUE, 'Guest', 'GuestLastName', TRUE, 'password', '+987654321', 4.2);

-- Table: public.accommodations

CREATE TABLE IF NOT EXISTS public.accommodations (
                                                     id BIGINT NOT NULL AUTO_INCREMENT,
                                                     active BOOLEAN NOT NULL,
                                                     automatic_approval BOOLEAN NOT NULL,
                                                     cancelation_days INTEGER NOT NULL,
                                                     created BIGINT,
                                                     default_price DOUBLE,
                                                     description VARCHAR(255),
    location VARCHAR(255),
    max_guests INTEGER NOT NULL,
    min_guests INTEGER NOT NULL,
    name VARCHAR(255),
    price_type SMALLINT,
    status SMALLINT,
    type VARCHAR(255),
    owner_email VARCHAR(255),
    CONSTRAINT accommodations_pkey PRIMARY KEY (id),
    CONSTRAINT fk6ag6bpden88mlnn50j5sv916p FOREIGN KEY (owner_email)
    REFERENCES public.users (email) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT accommodations_price_type_check CHECK (price_type >= 0 AND price_type <= 1),
    CONSTRAINT accommodations_status_check CHECK (status >= 0 AND status <= 2)
    );

-- Insert accommodation
INSERT INTO public.accommodations (active, automatic_approval, cancelation_days, created, default_price, description, location, max_guests, min_guests, name, price_type, status, type, owner_email)
VALUES (TRUE, TRUE, 7, 1642972800000, 100.0, 'Description of Accommodation', 'Location of Accommodation', 5, 2, 'AccommodationName', 0, 1, 'Apartment', 'owner@example.com');

-- Table: public.reservations

CREATE TABLE IF NOT EXISTS public.reservations (
                                                   id BIGINT NOT NULL AUTO_INCREMENT,
                                                   end_date BIGINT,
                                                   number_of_guests INTEGER NOT NULL,
                                                   price DOUBLE,
                                                   start_date BIGINT,
                                                   status SMALLINT,
                                                   accommodation_id BIGINT,
                                                   guest_email VARCHAR(255),
    CONSTRAINT reservations_pkey PRIMARY KEY (id),
    CONSTRAINT fk68ypsfp82ocwo4lfws91vj3wk FOREIGN KEY (guest_email)
    REFERENCES public.users (email) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fkb1rm1anuor9vcj2vxer1j5cfr FOREIGN KEY (accommodation_id)
    REFERENCES public.accommodations (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT reservations_status_check CHECK (status >= 0 AND status <= 4)
    );

-- Insert reservation
INSERT INTO public.reservations (end_date, number_of_guests, price, start_date, status, accommodation_id, guest_email)
VALUES (1643673600000, 2, 150.0, 1642972800000, 0, 1, 'guest@example.com');
