CREATE TABLE Customer (
	Customer_id VARCHAR(50),
            Name CHAR(50),
	Phone# VARCHAR(50),
	Billing_address VARCHAR(50),
	Shipping_address VARCHAR(50),
PRIMARY KEY (Customer_id)
);

INSERT INTO Customer (Customer_id, Name, Phone#, Billing_address, Shipping_address) values ('69-864-7808', 'Lucas Snyder', '(100) 4741902', '288 Westerfield Court', '288 Westerfield Court');

INSERT INTO Customer (Customer_id, Name, Phone#, Billing_address, Shipping_address) values ('09-254-7178', 'Cindy Newton', '(286) 5727517', '379 Parkside Circle', '93 Sachs Street');

INSERT INTO Customer (Customer_id, Name, Phone#, Billing_address, Shipping_address) values ('89-747-8984', 'Delbert Park', '(437) 5130996', '99431 Almo Trail', '99431 Almo Trail');

INSERT INTO Customer (Customer_id, Name, Phone#, Billing_address, Shipping_address) values ('97-952-3231', 'Becky Maxwell', '(573) 8030653', '53 Portage Crossing', '53 Portage Crossing');

INSERT INTO Customer (Customer_id, Name, Phone#, Billing_address, Shipping_address) values ('87-914-8118', 'Brian Arnold', '(195) 1966652', '76891 Ludington Street', '97 Transport Park');

CREATE TABLE VIP_1 (
	VIP_ID INT,
	VIP_Points INT,
                        PRIMARY KEY (VIP_ID)
);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (7367, 84);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (4336, 50);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (8940, 7);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (8566, 28);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (8520, 87);

CREATE TABLE VIP_2 (
	Customer_id VARCHAR(50),
	VIP_ID INT,
	Annual_fee INT,
          PRIMARY KEY (Customer_id)
);
INSERT INTO VIP_2 (Customer_id, VIP_ID, Annual_fee) values ('02-349-8190', 1721, 20);
INSERT INTO VIP_2 (Customer_id, VIP_ID, Annual_fee) values ('80-666-6892', 2997, 20);
INSERT INTO VIP_2 (Customer_id, VIP_ID, Annual_fee) values ('91-091-5395', 2148, 20);
INSERT INTO VIP_2 (Customer_id, VIP_ID, Annual_fee) values ('62-814-0066', 6724, 20);
INSERT INTO VIP_2 (Customer_id, VIP_ID, Annual_fee) values ('56-426-5754', 3817, 20);



CREATE TABLE Products (
	Product_ID INT,
	Category VARCHAR(50),
	Manufacturer VARCHAR(50),
	Name VARCHAR(50),
PRIMARY KEY(Product_ID)
);

INSERT INTO Products_1 (Product_ID, Category, Manufacturer, Name) values (77682, 'Kids', 'Cormier Group', 'Wine - Fino Tio Pepe Gonzalez');

INSERT INTO Products_1 (Product_ID, Category, Manufacturer, Name) values (95033, 'Baby', 'Nolan-Watsica', 'Macaroons - Two Bite Choc');

INSERT INTO Products_1 (Product_ID, Category, Manufacturer, Name) values (79301, 'Kids', 'Zboncak, Rice and Bins', 'Muffin Batt - Ban Dream Zero');

INSERT INTO Products_1 (Product_ID, Category, Manufacturer, Name) values (75637, 'Industrial', 'Von, Metz and Wuckert', 'Snapple - Iced Tea Peach');

INSERT INTO Products_1 (Product_ID, Category, Manufacturer, Name) values (12459, 'Books', 'Lakin Inc', 'Wasabi Paste');




CREATE TABLE Seller (
	Name VARCHAR(50),
	SIN INT,
	Seller_ID INT,
	Address VARCHAR(50),
	Phone# VARCHAR(50)
);
INSERT INTO Seller (Name, SIN, Seller_ID, Address, Phone#) values ('Ella Dureden', 650970108, 753080, '5152 Cottonwood Center', '(354) 9290200');

INSERT INTO Seller (Name, SIN, Seller_ID, Address, Phone#) values ('Clim Umpleby', 871368939, 951267, '75615 Granby Place', '(979) 4772521');

INSERT INTO Seller (Name, SIN, Seller_ID, Address, Phone#) values ('Thorin Trembley', 854479275, 300356, '72399 Warner Park', '(575) 4504131');

INSERT INTO Seller (Name, SIN, Seller_ID, Address, Phone#) values ('Casie Melville', 810896693, 155223, '8 Claremont Point', '(464) 9851443');

INSERT INTO Seller (Name, SIN, Seller_ID, Address, Phone#) values ('Janot Son', 641212698, 945803, '79 Prentice Court', '(846) 3752798');


CREATE TABLE PutOrder (
	Status VARCHAR(50),
	Payment_method VARCHAR(50),
	Data_placed DATE,
	Shipping_date DATE,
	Arrival_date DATE,
	VIP_points_used INT,
	Order_number  INT,
	Product_ID INT NOT NULL,
	Customer_id VARCHAR(50) NOT NULL,
	Seller_ID INT NOT NULL,
            PRIMARY KEY(Order_number),
            FOREIGN KEY(Product_ID) REFERENCES Products (Product_ID),
            FOREIGN KEY(Customer_id) REFERENCES Customers (Customer_id),
			FOREIGN KEY(Seller_ID) REFERENCES Seller (Seller_ID)
);
INSERT INTO PutOrder (Status, Payment_method, Data_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_id, Seller_ID) values (In_progress, 'maestro', '2/14/2017', NULL, NULL, NULL, 84601784, 45167, '07-129-9603',458715);

INSERT INTO PutOrder (Status, Payment_method, Data_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_id, Seller_ID) values (In_progress, 'mastercard', '2/15/2017', '2/18/2017', '2/21/2017', 23, 21784284, 97673, '73-247-6510',300356);

INSERT INTO PutOrder (Status, Payment_method, Data_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_id, Seller_ID) values (Completed, 'china-unionpay', '2/14/2017', '2/17/2017', '2/22/2017', 18, 93777720, 29453, '29-564-1413',897458);

INSERT INTO PutOrder (Status, Payment_method, Data_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_id, Seller_ID) values (Completed, 'switch', '2/15/2017', '2/18/2017', '2/20/2017', 6, 62089060, 13511, '11-167-0896',128754);

INSERT INTO PutOrder (Status, Payment_method, Data_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_id, Seller_ID) values (Completed, '2/14/2017', '2/19/2017', '2/20/2017', 8, 62735978, 10135, '82-469-4274',789458);

CREATE TABLE Choose (
	Product_ID INT,
	Customer_id VARCHAR(50),
	Quantity INT,
PRIMARY KEY (Product_ID, Customer_id),
FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id),
FOREIGN KEY (Product_ID) REFERENCES Product(Product_ID)
	);

INSERT INTO Choose (Product_ID, Customer_id, Quantity) values (94895, 87-914-8118, 10);

INSERT INTO Choose (Product_ID, Customer_id, Quantity) values (67889, 97-952-3231, 8);

INSERT INTO Choose (Product_ID, Customer_id, Quantity) values (94895, 89-747-8984, 16);

INSERT INTO Choose (Product_ID, Customer_id, Quantity) values (49510, 06-387-2139, 7);

INSERT INTO Choose (Product_ID, Customer_id, Quantity) values (37086, 03-155-8425, 4);

CREATE TABLE Rate (
	Rating INT,
	Customer_id VARCHAR(50),
	Seller_ID INT,
	Product_ID INT,
	Order_number INT,
            PRIMARY KEY(Customer_id, Product_ID, Seller_ID),
            FOREIGN KEY(Customer_id) REFERENCES Customer(Customer_id),
            FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID),
            FOREIGN KEY(Seller_ID) REFERENCES Seller(Seller_ID),
			FOREIGN KEY(Order_number) REFERENCES PutOrder(Order_number),
);
INSERT INTO Rate (Rating, Customer_id, Seller_ID, Product_ID, Order_number) values (9, '85-816-1228', 817540, 84895,62089060);

INSERT INTO Rate (Rating, Customer_id, Seller_ID, Product_ID, Order_number) values (7, '70-219-1393', 280040, 30304,62012360);

INSERT INTO Rate (Rating, Customer_id, Seller_ID, Product_ID, Order_number) values (7, '86-778-1860', 792888, 30874,64789060);

INSERT INTO Rate (Rating, Customer_id, Seller_ID, Product_ID, Order_number) values (8, '85-905-9179', 711351, 72571,62088100);

INSERT INTO Rate (Rating, Customer_id, Seller_ID, Product_ID, Order_number) values (8, '35-719-2187', 235278, 80786,61384060);

CREATE TABLE Has (
	Product_ID INT,
	Seller_ID INT,
	Quantity INT,
	Price VARCHAR(50),
            PRIMARY KEY(Product_ID, Seller_ID),
            FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID),
            FOREIGN KEY(Seller_ID) REFERENCES Seller(Seller_ID)
);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (21248, 886045, 124, '$196.76');

INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (55328, 748007, 25, '$72.19');

INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (62662, 989615, 129, '$29.01');

INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (13776, 448548, 126, '$112.81');

INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (86410, 601743, 156, '$169.82');


