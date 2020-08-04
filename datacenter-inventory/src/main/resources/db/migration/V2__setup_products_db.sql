CREATE TABLE products
(
    sku         INTEGER PRIMARY KEY,
    version     INTEGER DEFAULT 0,
    description TEXT
);

INSERT INTO products (sku, description)
VALUES (100, 'base ball'),
       (101, 'baseball glove'),
       (102, 'baseball hat'),
       (103, 'baseball bat'),
       (104, 'baseball shoes'),

       (200, 'football'),
       (201, 'football cleats'),
       (202, 'football helmet'),

       (300, 'hockey puck'),
       (301, 'hockey stick'),
       (302, 'hockey skates'),
       (303, 'hockey helmet');
