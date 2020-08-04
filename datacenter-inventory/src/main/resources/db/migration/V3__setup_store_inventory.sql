CREATE TABLE store_stock
(
    store_number INTEGER REFERENCES stores (num),
    sku          INTEGER REFERENCES products (sku),
    quantity     INTEGER NOT NULL check ( quantity >= 0 ),
    version      INTEGER DEFAULT 0,
    PRIMARY KEY (store_number, sku)
);

INSERT INTO store_stock(store_number, sku, quantity)
VALUES -- baseball
       (1, 100, 1),
       (2, 100, 2),
       (3, 100, 0),
       -- baseball glove
       (1, 101, 10),
       (2, 101, 20),
       (3, 101, 5),
       -- base ball hat
       (1, 102, 1),
       (2, 102, 2),
       (3, 102, 0),
       -- baseball bat
       (1, 103, 1),
       (2, 103, 7),
       (3, 103, 0),
       -- baseball shoes
       (1, 104, 1),
       (2, 104, 9),
       (3, 104, 0),

       -- football
       (1, 200, 1),
       (2, 200, 2),
       (3, 200, 0),
       -- football cleats
       (1, 201, 1),
       (2, 201, 2),
       (3, 201, 0),
       -- football helmet
       (1, 202, 3),
       (2, 202, 2),
       (3, 202, 6),

       -- hockey puck
       (1, 300, 1),
       (2, 300, 2),
       (3, 300, 0),
       -- hockey stick
       (1, 301, 1),
       (2, 301, 8),
       (3, 301, 0),
       -- hockey skates
       (1, 302, 1),
       (2, 302, 6),
       (3, 302, 0),
       -- hockey helmet
       (1, 303, 9),
       (2, 303, 2),
       (3, 303, 0)
;


