CREATE TABLE stores
(
    num        INTEGER PRIMARY KEY,
    version    INTEGER DEFAULT 0,
    name       TEXT,
    street     TEXT,
    city       TEXT,
    province   TEXT,
    postal_code TEXT
);

INSERT INTO stores (num, name, street, city, province, postal_code)
VALUES (1, 'Ajax', '123 Pine Street', 'Ajax', 'Ontario', 'L1T 4X9'),
       (2, 'Pickering', '456 Apple Cres', 'Pickering', 'Ontario', 'L1Q 4M9'),
       (3, 'Toronto', '789 Queen Street', 'Toronto', 'Ontario', 'D8K 4M9');

