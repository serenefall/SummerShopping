drop table Rate;
drop table HAS;
drop table PutOrder;
drop table Products;
drop table Seller;
drop table Users;
drop table Customer;
drop table VIP_1;
drop table VIP_2;


CREATE TABLE Users (
	id INT,
	password INT,
	PRIMARY KEY (id)
);

INSERT INTO Users (id, password) values (1, 1);
INSERT INTO Users (id, password) values (234, 123456);
INSERT INTO Users (id, password) values (456, 123456);
INSERT INTO Users (id, password) values (896,123456);
INSERT INTO Users (id, password) values (678, 123456);
INSERT INTO Users (id, password) values (331, 593843);
INSERT INTO Users (id, password) values (123, 123456);
INSERT INTO Users (id, password) values (437, 123456);
INSERT INTO Users (id, password) values (108, 123456);
INSERT INTO Users (id, password) values (458, 123456);
INSERT INTO Users (id, password) values (570, 123456);
INSERT INTO Users (id, password) values (1111, 123456);
INSERT INTO Users (id, password) values (2222, 341832);
INSERT INTO Users (id, password) values (7530, 123456);
INSERT INTO Users (id, password) values (3333, 123456);
INSERT INTO Users (id, password) values (4444, 123456);
INSERT INTO Users (id, password) values (8458, 123456);
INSERT INTO Users (id, password) values (8685, 123456);
INSERT INTO Users (id, password) values (8499, 123456);
INSERT INTO Users (id, password) values (9653, 123456);
INSERT INTO Users (id, password) values (8165, 123456);

CREATE TABLE Customer (
	Customer_ID INT,
    Customer_Name CHAR(50),
	Phone# VARCHAR(50),
	Billing_address VARCHAR(50),
	Shipping_address VARCHAR(50),
		PRIMARY KEY (Customer_ID)
);

INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (234, 'Lucas Snyder', '(100) 4741902', '288 Westerfield Court', '288 Westerfield Court');
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (456, 'Cindy Newton', '(286) 5727517', '379 Parkside Circle', '93 Sachs Street');
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (896, 'Delbert Park', '(437) 5130996', '99431 Almo Trail', '99431 Almo Trail');
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (678, 'Becky Maxwell', '(573) 8030653', '53 Portage Crossing', '53 Portage Crossing');
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (123, 'Brian Arnold', '(195) 1966652', '76891 Ludington Street', '97 Transport Park');
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (331, 'Raymond Paul', '(100) 4729802', '108 Westerfield Court', '108 Westerfield Court');
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (437, 'Christ Anthony', '(237) 5420996', '2525 West Mall', '2525 West Mall');
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (108, 'Damien Lillard', '(677) 8457916', '1008 No 4 Road', '1008 No 4 Road');
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (458, 'Tony Bradley', '(778) 8715416', '1874 No 4 Road', '1874 No 4 Road');
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (570, 'Bradley Bill', '(717) 9874416', '1695 No 4 Road', '1695 No 4 Road');


CREATE TABLE VIP_2 (
	Customer_ID INT,
	VIP_ID INT,
	Annual_fee INT,
        PRIMARY KEY (VIP_ID),
				FOREIGN KEY (Customer_ID) REFERENCES CUSTOMER(Customer_ID) ON DELETE CASCADE
);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (234, 73, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (896, 46, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (678, 21, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (123, 43, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (331, 37, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (108, 88, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (458, 91, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (570, 10, 20);

CREATE TABLE VIP_1 (
	VIP_ID INT,
	VIP_Points INT,
    	PRIMARY KEY (VIP_ID),
			FOREIGN KEY (VIP_ID) REFERENCES VIP_2(VIP_ID) ON DELETE CASCADE
);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (73, 84);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (46, 50);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (21, 7);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (43, 28);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (37, 87);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (88, 15);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (10, 128);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (91, 78);



CREATE TABLE Products (
	Product_ID INT,
	Category VARCHAR(50),
	Manufacturer VARCHAR(50),
	Product_Name VARCHAR(50),
		PRIMARY KEY(Product_ID)
);

INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (77682, 'Kids', 'Cormier Group', 'Lego toys');
INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (95033, 'Food', 'Nolan-Watsica', 'Macaroons');
INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (30874, 'Food', 'Zboncak, Rice and Bins', 'Muffin');
INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (13776, 'Industrial', 'Von, Metz and Wuckert', 'Snapple');
INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (12459, 'Books', 'Lakin Inc', 'Paste');
INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (31259, 'Pens', 'Pilot Inc', 'Pilot Pen');
insert into Products (Product_ID, Category, Manufacturer, Product_Name) values (47653, 'Clothing', 'Graham-Abernathy', 'Pie Shell - 5');
insert into Products (Product_ID, Category, Manufacturer, Product_Name) values (41643, 'Food', 'Monahan, Ullrich and Runte', 'Lobster - Tail 6 Oz');
insert into Products (Product_ID, Category, Manufacturer, Product_Name) values (17742, 'Book', 'Bergstrom-Tromp', 'Alize Gold Passion');
insert into Products (Product_ID, Category, Manufacturer, Product_Name) values (56096, 'Movies', 'Yundt, Hahn and Rowe', 'Soap - Pine Sol Floor Cleaner');



CREATE TABLE Seller (
	Seller_Name VARCHAR(50),
	SIN INT,
	Seller_ID INT,
	Address VARCHAR(50),
	Phone# VARCHAR(50),
		PRIMARY KEY (Seller_ID)
);
INSERT INTO Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Ella Dureden', 650970108, 7530, '5152 Cottonwood Center', '(354) 9290200');
INSERT INTO Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Clim Umpleby', 871368939, 8458, '75615 Granby Place', '(979) 4772521');
INSERT INTO Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Thorin Trembley', 854479275, 1111, '72399 Warner Park', '(575) 4504131');
INSERT INTO Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Casie Melville', 810896693, 2222, '8 Claremont Point', '(464) 9851443');
INSERT INTO Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Lorcan Francis', 810898493, 3333, '2106 Upper Mall', '(464) 9874843');
INSERT INTO Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Chris Dunn', 87598743, 4444, '2106 Upper Mall', '(464) 9874843');
insert into Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Rae Phillput', 461249844, 8685, '57 Linden Park', '(540) 8755930');
insert into Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Krissy MacCarrane', 424796946, 8499, '5 Columbus Junction', '(954) 2332802');
insert into Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Ingamar Voelker', 340955963, 9653, '696 Artisan Avenue', '(725) 3385391');
insert into Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Brendan Hardwich', 492704359, 8165, '2 Pearson Center', '(597) 5782161');

CREATE TABLE PutOrder (
	Status VARCHAR(50),
	Payment_method VARCHAR(50),
	Date_placed VARCHAR(50),
	Shipping_date VARCHAR(50),
	Arrival_date VARCHAR(50),
	VIP_points_used INT,
	Order_number INT,
	Product_ID INT,
	Customer_ID INT,
	Seller_ID INT,
	Quantity INT,
            PRIMARY KEY(Order_number),
            FOREIGN KEY(Product_ID) REFERENCES Products (Product_ID) ON DELETE SET NULL,
            FOREIGN KEY(Customer_ID) REFERENCES Customer (Customer_ID) ON DELETE SET NULL,
			FOREIGN KEY(Seller_ID) REFERENCES Seller (Seller_ID) ON DELETE SET NULL
);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'maestro', '2017-2-14', '2017-2-15', '2017-2-19', 10, 84601784, 77682, 234, 7530, 11);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'china-unionpay', '2017-4-05', '2017-4-06', '2017-4-10', 0, 93777720, 95033, 456, 8458, 6);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'switch', '2017-5-13', '2017-5-14', '2017-5-18', 6, 62089060, 30874, 896, 1111, 1);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'paypal', '2017-2-11', '2017-2-12', '2017-2-16', 8, 62088100, 13776, 678, 2222, 2);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('In Progress', 'alipay', '2017-2-6', '2017-2-7', '2017-2-11', 3, 45138574, 13776, 123, 3333, 3);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('In Progress', 'bit-coin', '2017-6-6', '2017-6-7', '2017-6-11', 10, 41945574, 30874, 331, 3333, 5);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('In Progress', 'credit-card', '2017-9-11', '2017-9-12', '2017-9-16', 0, 84752074, 31259, 437, 3333, 1);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'paypal', '2017-2-11', '2017-2-12', '2017-2-16', 8, 58452100, 47653, 458, 8685, 2);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'alipay', '2017-2-6', '2017-2-7', '2017-2-11', 3, 49813474, 41643, 570, 8499, 3);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'bit-coin', '2017-6-6', '2017-6-7', '2017-6-11', 10, 25876574, 17742, 458, 9653, 5);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'credit-card', '2017-9-11', '2017-9-12', '2017-9-16', 0, 59972074, 56096, 437, 8165, 1);


CREATE TABLE Rate (
	Rating INT,
	Order_number INT,
        PRIMARY KEY(Order_number),
		FOREIGN KEY(Order_number) REFERENCES PutOrder(Order_number)
);
INSERT INTO Rate (Rating, Order_number) values (5, 84601784);
INSERT INTO Rate (Rating, Order_number) values (4, 93777720);
INSERT INTO Rate (Rating, Order_number) values (3, 62089060);
INSERT INTO Rate (Rating, Order_number) values (5, 62088100);
INSERT INTO Rate (Rating, Order_number) values (2, 45138574);
INSERT INTO Rate (Rating, Order_number) values (4, 58452100);
INSERT INTO Rate (Rating, Order_number) values (5, 49813474);
INSERT INTO Rate (Rating, Order_number) values (3, 25876574);
INSERT INTO Rate (Rating, Order_number) values (5, 59972074);




CREATE TABLE Has (
	Product_ID INT,
	Seller_ID INT,
	Quantity INT,
	Price INT,
        PRIMARY KEY(Product_ID, Seller_ID),
        FOREIGN KEY(Product_ID) REFERENCES Products(Product_ID) ON DELETE CASCADE,
        FOREIGN KEY(Seller_ID) REFERENCES Seller(Seller_ID) ON DELETE CASCADE
);

INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (30874, 1111, 124, 26);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (95033, 8458, 25, 2);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (77682, 7530, 129, 29);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (13776, 2222, 126, 112);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (13776, 3333, 11, 102);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (30874, 3333, 99, 27);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (12459, 3333, 77, 30);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (95033, 3333, 29, 3);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (77682, 3333, 189, 28);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (31259, 3333, 19, 6);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (47653, 3333, 87, 15);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (41643, 3333, 89, 48);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (17742, 3333, 488, 42);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (56096, 3333, 487, 17);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (13776, 4444, 18, 99);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (30874, 4444, 58, 26);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (12459, 4444, 71, 33);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (95033, 4444, 24, 2);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (77682, 4444, 94, 25);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (31259, 4444, 9, 2);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (47653, 4444, 48, 16);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (41643, 4444, 485, 49);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (17742, 4444, 148, 41);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (56096, 4444, 158, 18);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (47653, 8685, 157, 12);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (41643, 8499, 45, 44);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (17742, 9653, 48, 45);
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (56096, 8165, 18, 19);


commit;



commit;
