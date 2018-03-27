drop table Users;
drop table Customer;
drop table VIP_1;
drop table VIP_2;
drop table Products;
drop table Seller;
drop table PutOrder;
drop table Rate;
drop table HAS;

CREATE TABLE Users (
	id INT,
	password INT,
	PRIMARY KEY (id)
);

INSERT INTO Users (id, password) values (234, 123456);
INSERT INTO Users (id, password) values (456, 123456);
INSERT INTO Users (id, password) values (896,123456);
INSERT INTO Users (id, password) values (1111, 123456);
INSERT INTO Users (id, password) values (331, 593843);
INSERT INTO Users (id, password) values (2222, 341832);
INSERT INTO Users (id, password) values (1, 1);
INSERT INTO Users (id, password) values (678, 123456);
INSERT INTO Users (id, password) values (7530, 123456);

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
INSERT INTO Customer (Customer_ID, Customer_Name, Phone#, Billing_address, Shipping_address) values (879, 'Brian Arnold', '(195) 1966652', '76891 Ludington Street', '97 Transport Park');

CREATE TABLE VIP_1 (
	VIP_ID INT,
	VIP_Points INT,
    PRIMARY KEY (VIP_ID)
);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (73, 84);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (46, 50);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (21, 7);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (85, 28);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (80, 87);

CREATE TABLE VIP_2 (
	Customer_ID INT,
	VIP_ID INT,
	Annual_fee INT,
          PRIMARY KEY (Customer_ID)
);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (234, 73, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (896, 46, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (678, 21, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (496, 43, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (384, 37, 20);



CREATE TABLE Products (
	Product_ID INT,
	Category VARCHAR(50),
	Manufacturer VARCHAR(50),
	Product_Name VARCHAR(50),
PRIMARY KEY(Product_ID)
);

INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (77682, 'Kids', 'Cormier Group', 'Lego toys');
INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (95033, 'Food', 'Nolan-Watsica', 'Macaroons - Two Bite Choc');
INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (30874, 'Kids', 'Zboncak, Rice and Bins', 'Muffin Batt - Ban Dream Zero');
INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (13776, 'Industrial', 'Von, Metz and Wuckert', 'Snapple - Iced Tea Peach');
INSERT INTO Products (Product_ID, Category, Manufacturer, Product_Name) values (12459, 'Books', 'Lakin Inc', 'Wasabi Paste');




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
INSERT INTO Seller (Seller_Name, SIN, Seller_ID, Address, Phone#) values ('Janot Son', 641212698, 9403, '79 Prentice Court', '(846) 3752798');


CREATE TABLE PutOrder (
	Status VARCHAR(50),
	Payment_method VARCHAR(50),
	Date_placed DATE,
	Shipping_date DATE,
	Arrival_date DATE,
	VIP_points_used INT,
	Order_number INT,
	Product_ID INT NOT NULL,
	Customer_ID INT NOT NULL,
	Seller_ID INT NOT NULL,
	Quantity INT,
            PRIMARY KEY(Order_number),
            FOREIGN KEY(Product_ID) REFERENCES Products (Product_ID),
            FOREIGN KEY(Customer_ID) REFERENCES Customer (Customer_ID),
						FOREIGN KEY(Seller_ID) REFERENCES Seller (Seller_ID)
);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'maestro', '2017-2-14', '2017-2-15', '2017-2-19', 10, 84601784, 77682, 234, 7530, 11);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'china-unionpay', '2017-4-05', '2017-4-06', '2017-4-10', 0, 93777720, 95033, 456, 8458, 6);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'switch', '2017-5-13', '2017-5-14', '2017-5-18', 6, 62089060, 30874, 896, 1111, 1);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('Completed', 'paypal', '2017-2-11', '2017-2-12', '2017-2-16', 8, 62088100, 13776, 678, 2222, 2);


CREATE TABLE Rate (
	Rating INT,
	Customer_ID INT,
	Seller_ID INT,
	Product_ID INT,
	Order_number INT,
            PRIMARY KEY(Customer_ID, Product_ID, Seller_ID),
            FOREIGN KEY(Customer_ID) REFERENCES Customer(Customer_ID),
            FOREIGN KEY(Product_ID) REFERENCES Products(Product_ID),
            FOREIGN KEY(Seller_ID) REFERENCES Seller(Seller_ID),
			FOREIGN KEY(Order_number) REFERENCES PutOrder(Order_number)
);
INSERT INTO Rate (Rating, Customer_ID, Seller_ID, Product_ID, Order_number) values (5, 234, 7530, 77682,84601784);
INSERT INTO Rate (Rating, Customer_ID, Seller_ID, Product_ID, Order_number) values (4, 456, 8458, 95033,93777720);
INSERT INTO Rate (Rating, Customer_ID, Seller_ID, Product_ID, Order_number) values (3, 896, 1111, 30874,62089060);
INSERT INTO Rate (Rating, Customer_ID, Seller_ID, Product_ID, Order_number) values (5, 678, 2222, 13776,62088100);
INSERT INTO Rate (Rating, Customer_ID, Seller_ID, Product_ID, Order_number) values (4, 187, 2378, 80786,61384060);

CREATE TABLE Has (
	Product_ID INT,
	Seller_ID INT,
	Quantity INT,
	Price VARCHAR(50),
            PRIMARY KEY(Product_ID, Seller_ID),
            FOREIGN KEY(Product_ID) REFERENCES Products(Product_ID),
            FOREIGN KEY(Seller_ID) REFERENCES Seller(Seller_ID)
);

INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (30874, 1111, 124, '$196.76');
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (95033, 8458, 25, '$2.19');
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (77682, 7530, 129, '$29.01');
INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) values (13776, 2222, 126, '$112.81');


commit;



commit;


