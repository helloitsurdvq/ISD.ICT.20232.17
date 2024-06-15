CREATE TABLE Media (
    id INT NOT NULL,
    title VARCHAR(100),
    categoryType VARCHAR(50),
    value INT NOT NULL,
    price INT NOT NULL,
    quantity INT,
    imageURL VARCHAR(200)
);
ALTER TABLE Media
ADD CONSTRAINT PK_Media PRIMARY KEY (id);

CREATE TABLE PaymentTransaction (
    id INT NOT NULL,
    content VARCHAR(200) NOT NULL,
    bankName VARCHAR(50) NOT NULL,
    method VARCHAR(200) NOT NULL,
    status VARCHAR(50) NOT NULL,
    time DATETIME NOT NULL,
);
ALTER TABLE PaymentTransaction
ADD CONSTRAINT PK_PaymentTransaction PRIMARY KEY (id);

CREATE TABLE Invoice (
    id INT NOT NULL,
    order_id INT NOT NULL,
    totalAmount INT NOT NULL,
    vat INT NOT NULL,
    paymentTransaction_id INT NOT NULL
);
ALTER TABLE Invoice
ADD CONSTRAINT PK_Invoice PRIMARY KEY (id);

CREATE TABLE Order (
    id INT NOT NULL,
    shippingFees FLOAT NOT NULL,
    delivery_id INT NOT NULL
);
ALTER TABLE Order
ADD CONSTRAINT PK_Order PRIMARY KEY (id);

CREATE TABLE DeliveryInformation (
    id INT NOT NULL,
    name VARCHAR(50),
    phone VARCHAR(50) NOT NULL,
    province VARCHAR(2000) NOT NULL,
    address VARCHAR(200) NOT NULL,
);
ALTER TABLE DeliveryInformation
ADD CONSTRAINT PK_DeliveryInformation PRIMARY KEY (id);

CREATE TABLE ShippingInfomation (
    id INT NOT NULL,
    shippingMethod_id INT NOT NULL,
    orderMedia_id INT NOT NULL,
    shippingFee INT NOT NULL
);
ALTER TABLE shippingInfomation
ADD CONSTRAINT PK_shippingInfomation PRIMARY KEY (id);

CREATE TABLE OrderMedia (
    id INT NOT NULL,
    order_id INT NOT NULL
    media_id INT NOT NULL,
    quantity INT NOT NULL,
    price FLOAT NOT NULL
);
ALTER TABLE OrderMedia
ADD CONSTRAINT PK_OrderMedia PRIMARY KEY (id);

CREATE TABLE RushOrder (
    id INT NOT NULL,
    instruction VARCHAR(2000) NOT NULL,
    deliveryTime DATETIME NOT NULL,
    shippingInfo_id INT NOT NULL,
    shippingMethod_id INT NOT NULL,
)
ALTER TABLE RushOrder
ADD CONSTRAINT PK_RushOrder PRIMARY KEY (id);

CREATE TABLE ShippingMethod (
    id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(2000) NOT NULL,
);
ALTER TABLE ShippingMethod
ADD CONSTRAINT PK_ShippingMethod PRIMARY KEY (id);

CREATE TABLE MediaShippingMethod (
    id INT NOT NULL,
    shippingMethod_id INT NOT NULL,
    media_id INT NOT NULL
);
ALTER TABLE MediaShippingMethod
ADD CONSTRAINT PK_MediaShippingMethod PRIMARY KEY (id);

ALTER TABLE MediaShippingMethod
ADD CONSTRAINT FK_MediaShippingMethod_0 FOREIGN KEY (media_id) REFERENCES Media (id);

ALTER TABLE MediaShippingMethod
ADD CONSTRAINT FK_MediaShippingMethod_1 FOREIGN KEY (shippingMethod_id) REFERENCES ShippingMethod (id);

ALTER TABLE Order
ADD CONSTRAINT FK_Order_0 FOREIGN KEY (deliveryAddress_id) REFERENCES DeliveryAddress (id);

ALTER TABLE OrderMedia
ADD CONSTRAINT FK_OrderMedia_0 FOREIGN KEY (media_id) REFERENCES Media (id);
ALTER TABLE OrderMedia
ADD CONSTRAINT FK_OrderMedia_1 FOREIGN KEY (order_id) REFERENCES Order (id);

ALTER TABLE ShippingInfomation
ADD CONSTRAINT FK_ShippingInfomation_0 FOREIGN KEY (orderMedia_id) REFERENCES OrderMedia (id);
ALTER TABLE ShippingInfomation
ADD CONSTRAINT FK_ShippingInfomation_1 FOREIGN KEY (shippingMethod_id) REFERENCES ShippingMethod (id);

ALTER TABLE RushOrder
ADD CONSTRAINT FK_RushOrder_0 FOREIGN KEY (shippingInfo_id) REFERENCES ShippingInfomation (id);

ALTER TABLE RushOrder
ADD CONSTRAINT FK_RushOrder_1 FOREIGN KEY (shippingMethod_id) REFERENCES ShippingMethod (id);

ALTER TABLE Invoice
ADD CONSTRAINT FK_Invoice_0 FOREIGN KEY (order_id) REFERENCES Order (id);
ALTER TABLE Invoice
ADD CONSTRAINT FK_Invoice_1 FOREIGN KEY (paymentTransaction_id) REFERENCES PaymentTransaction (id);