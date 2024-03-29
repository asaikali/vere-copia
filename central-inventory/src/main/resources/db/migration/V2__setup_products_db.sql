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
       (303, 'hockey helmet'),

       (400, 'tennis ball'),
       (401, 'tennis shoes'),

       (500, 'golf ball'),
       (501, 'golf shoes'),
       (502, 'golf club')
       ;
