INSERT INTO users (id, archive, email, name, password, role)
VALUES (1, false, 'mail@mail.pl', 'admin', '$2a$10$GQT1j496jZU3.jAbh80gUebp6kicugxjbIpBBYChRn6saC7bnR1k2', 'ADMIN');

ALTER SEQUENCE user_seq RESTART WITH 2;

