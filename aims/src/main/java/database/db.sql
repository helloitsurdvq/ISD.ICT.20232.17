--
-- File generated with SQLiteStudio v3.4.4 on Sun Jun 16 16:38:09 2024
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Book
CREATE TABLE IF NOT EXISTS Book (
    id           INTEGER      PRIMARY KEY AUTOINCREMENT
                              NOT NULL,
    author       VARCHAR (45) NOT NULL,
    coverType    VARCHAR (45) NOT NULL,
    publisher    VARCHAR (45) NOT NULL,
    publishDate  DATETIME     NOT NULL,
    numOfPages   INTEGER      NOT NULL,
    language     VARCHAR (45) NOT NULL,
    bookCategory VARCHAR (45) NOT NULL,
    CONSTRAINT fk_book_media FOREIGN KEY (
        id
    )
    REFERENCES Media (id) 
);

INSERT INTO Book (
                     id,
                     author,
                     coverType,
                     publisher,
                     publishDate,
                     numOfPages,
                     language,
                     bookCategory
                 )
                 VALUES (
                     97,
                     'testAuthor',
                     'testCover',
                     'testPublisher',
                     '2022-06-16',
                     100,
                     'English',
                     'testtt'
                 );

INSERT INTO Book (
                     id,
                     author,
                     coverType,
                     publisher,
                     publishDate,
                     numOfPages,
                     language,
                     bookCategory
                 )
                 VALUES (
                     98,
                     'me',
                     'nothing',
                     'nha nam',
                     '2022-06-09',
                     34,
                     'Vietnamese',
                     'novel'
                 );

INSERT INTO Book (
                     id,
                     author,
                     coverType,
                     publisher,
                     publishDate,
                     numOfPages,
                     language,
                     bookCategory
                 )
                 VALUES (
                     99,
                     'Nguyen Nhat Anh',
                     'aaa',
                     'Kim Dong',
                     '2023-06-13',
                     434,
                     'Vietnamese',
                     'Novel'
                 );


-- Table: Card
CREATE TABLE IF NOT EXISTS Card (
    id             INTEGER      NOT NULL
                                PRIMARY KEY,
    cardNumber     VARCHAR (45) NOT NULL,
    holderName     VARCHAR (45) NOT NULL,
    expirationDate DATE         NOT NULL,
    securityCode   VARCHAR (45) NOT NULL
);


-- Table: CD
CREATE TABLE IF NOT EXISTS CD (
    id           INTEGER      PRIMARY KEY
                              NOT NULL,
    artist       VARCHAR (45) NOT NULL,
    recordLabel  VARCHAR (45) NOT NULL,
    musicType    VARCHAR (45) NOT NULL,
    releasedDate DATE,
    CONSTRAINT fk_cd_media FOREIGN KEY (
        id
    )
    REFERENCES Media (id) 
);


-- Table: DVD
CREATE TABLE IF NOT EXISTS DVD (
    id           INTEGER      PRIMARY KEY
                              NOT NULL,
    discType     VARCHAR (45) NOT NULL,
    director     VARCHAR (45) NOT NULL,
    runtime      INTEGER      NOT NULL,
    studio       VARCHAR (45) NOT NULL,
    subtitle     VARCHAR (45) NOT NULL,
    releasedDate DATETIME,
    filmType     VARCHAR (45) NOT NULL,
    CONSTRAINT fk_dvd_media FOREIGN KEY (
        id
    )
    REFERENCES Media (id) 
);


-- Table: Media
CREATE TABLE IF NOT EXISTS Media (
    id       INTEGER      PRIMARY KEY AUTOINCREMENT
                          NOT NULL,
    type     VARCHAR (45) NOT NULL,
    category VARCHAR (45) NOT NULL,
    price    INTEGER      NOT NULL,
    quantity INTEGER      NOT NULL,
    title    VARCHAR (45) NOT NULL,
    value    INTEGER      NOT NULL,
    imageUrl VARCHAR (45) NOT NULL
);

INSERT INTO Media (
                      id,
                      type,
                      category,
                      price,
                      quantity,
                      title,
                      value,
                      imageUrl
                  )
                  VALUES (
                      97,
                      'book',
                      'testCate',
                      100,
                      250,
                      'testTitle',
                      0,
                      'aims/src/main/resources/assets/uploads/1717580607619_book2.jpg'
                  );

INSERT INTO Media (
                      id,
                      type,
                      category,
                      price,
                      quantity,
                      title,
                      value,
                      imageUrl
                  )
                  VALUES (
                      98,
                      'book',
                      'romance',
                      44,
                      12,
                      'book2',
                      0,
                      'aims/src/main/resources/assets/uploads/1718451718797_book2.jpg'
                  );

INSERT INTO Media (
                      id,
                      type,
                      category,
                      price,
                      quantity,
                      title,
                      value,
                      imageUrl
                  )
                  VALUES (
                      99,
                      'book',
                      'ddd',
                      55,
                      20,
                      'hello',
                      0,
                      'aims/src/main/resources/assets/uploads/1718451797593_book5.jpg'
                  );


-- Table: Order
CREATE TABLE IF NOT EXISTS [Order] (
    id           INTEGER       NOT NULL
                               PRIMARY KEY AUTOINCREMENT,
    name         VARCHAR (45)  NOT NULL,
    address      VARCHAR (45)  NOT NULL,
    phone        VARCHAR (45)  NOT NULL,
    shipping_fee INTEGER       NOT NULL,
    instruction  VARCHAR (255),
    province     VARCHAR (255),
    status       VARCHAR (45)  NOT NULL
                               DEFAULT 'Pending'
);


-- Table: Order_dg_tmp
CREATE TABLE IF NOT EXISTS Order_dg_tmp (
    id           INTEGER       NOT NULL
                               PRIMARY KEY AUTOINCREMENT,
    name         VARCHAR (45)  NOT NULL,
    address      VARCHAR (45)  NOT NULL,
    phone        VARCHAR (45)  NOT NULL,
    shipping_fee INTEGER       NOT NULL,
    instruction  VARCHAR (255),
    province     VARCHAR (255) 
);


-- Table: OrderMedia
CREATE TABLE IF NOT EXISTS OrderMedia (
    mediaID  INTEGER NOT NULL,
    orderID  INTEGER NOT NULL,
    price    INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (
        mediaID,
        orderID
    ),
    CONSTRAINT fk_ordermedia_media FOREIGN KEY (
        mediaID
    )
    REFERENCES Media (id),
    CONSTRAINT fk_ordermedia_order FOREIGN KEY (
        orderID
    )
    REFERENCES [Order] (id) 
);

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           73,
                           43,
                           28,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           67,
                           43,
                           25,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           73,
                           44,
                           28,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           69,
                           44,
                           97,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           65,
                           44,
                           37,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           63,
                           44,
                           99,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           70,
                           44,
                           47,
                           2
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           73,
                           45,
                           28,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           70,
                           45,
                           47,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           67,
                           45,
                           25,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           63,
                           45,
                           99,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           73,
                           46,
                           28,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           67,
                           46,
                           25,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           69,
                           46,
                           97,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           47,
                           47,
                           82,
                           2
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           57,
                           48,
                           38,
                           2
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           67,
                           49,
                           25,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           70,
                           49,
                           47,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           70,
                           50,
                           47,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           67,
                           50,
                           25,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           92,
                           51,
                           30,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           73,
                           51,
                           28,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           92,
                           52,
                           30,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           94,
                           53,
                           23,
                           1
                       );

INSERT INTO OrderMedia (
                           mediaID,
                           orderID,
                           price,
                           quantity
                       )
                       VALUES (
                           97,
                           54,
                           100,
                           2
                       );


-- Table: PaymentTransaction
CREATE TABLE IF NOT EXISTS PaymentTransaction (
    id       INTEGER      PRIMARY KEY AUTOINCREMENT
                          NOT NULL,
    orderID  INTEGER      NOT NULL,
    createAt DATETIME     NOT NULL,
    content  VARCHAR (45) NOT NULL,
    CONSTRAINT fk_transaction_order FOREIGN KEY (
        orderID
    )
    REFERENCES [Order] (id) 
);


-- Table: Shipment
CREATE TABLE IF NOT EXISTS Shipment (
    id                  INTEGER       NOT NULL
                                      PRIMARY KEY AUTOINCREMENT,
    shipType            INTEGER       NOT NULL,
    deliveryInstruction VARCHAR (255) NOT NULL,
    dateTime            VARCHAR (255) NOT NULL,
    deliverySub         VARCHAR (255) NOT NULL,
    orderId             INTEGER       CONSTRAINT Shipment_Order_id_fk REFERENCES [Order]
);


-- Table: User
CREATE TABLE IF NOT EXISTS User (
    id                 INTEGER      NOT NULL
                                    PRIMARY KEY AUTOINCREMENT,
    name               VARCHAR (45) NOT NULL,
    email              VARCHAR (45) NOT NULL,
    address            VARCHAR (45) NOT NULL,
    phone              VARCHAR (45) NOT NULL,
    encrypted_password VARCHAR (45) NOT NULL
);

INSERT INTO User (
                     id,
                     name,
                     email,
                     address,
                     phone,
                     encrypted_password
                 )
                 VALUES (
                     1,
                     'Admin',
                     'admin@gmail.com',
                     'Giai Phong',
                     '0123456789',
                     'e10adc3949ba59abbe56e057f20f883e'
                 );


-- Index: OrderMedia.fk_ordermedia_order_idx
CREATE INDEX IF NOT EXISTS [OrderMedia.fk_ordermedia_order_idx] ON OrderMedia (
    "orderID"
);


-- Index: Transaction.fk_transaction_order_idx
CREATE INDEX IF NOT EXISTS [Transaction.fk_transaction_order_idx] ON PaymentTransaction (
    "orderID"
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
