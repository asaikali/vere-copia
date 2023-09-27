CREATE TABLE store_stock
(
    sku          INTEGER REFERENCES products (sku),
    quantity     INTEGER NOT NULL check ( quantity >= 0 ),
    version      INTEGER DEFAULT 0,
    PRIMARY KEY (sku)
);

INSERT INTO store_stock(sku, quantity)
VALUES -- baseball
       (100, 1),
       -- baseball glove
       (101, 5),
       -- base ball hat
       (102, 0),
       -- baseball bat
       (103, 7),
       -- baseball shoes
       (104, 9),
       -- football
       (200, 2),
       -- football cleats
       (201, 1),
       -- football helmet
       (202, 3),
       -- hockey puck
       (300, 1),
       -- hockey stick
       (301, 1),
       -- hockey skates
       (302, 6),
       -- hockey helmet
       (303, 0),

       -- tennis ball
       (400, 0),
       -- tennis shoes
       (401, 0),

       -- golf ball
       (500, 0),
       -- golf shoes
       (501, 0),
       -- golf club
       (502, 6)
;


