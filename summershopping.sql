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

INSERT INTO Users (id, password) values (234, 123456);
INSERT INTO Users (id, password) values (456, 123456);
INSERT INTO Users (id, password) values (896,123456);
INSERT INTO Users (id, password) values (678, 123456);
INSERT INTO Users (id, password) values (1111, 123456);
INSERT INTO Users (id, password) values (331, 593843);
INSERT INTO Users (id, password) values (2222, 341832);
INSERT INTO Users (id, password) values (1, 1);
INSERT INTO Users (id, password) values (7530, 123456);
INSERT INTO Users (id, password) values (123, 123456);
INSERT INTO Users (id, password) values (3333, 123456);

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

CREATE TABLE VIP_1 (
	VIP_ID INT,
	VIP_Points INT,
    	PRIMARY KEY (VIP_ID)
);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (73, 84);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (46, 50);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (21, 7);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (43, 28);
INSERT INTO VIP_1 (VIP_ID, VIP_Points) values (37, 87);

CREATE TABLE VIP_2 (
	Customer_ID INT,
	VIP_ID INT,
	Annual_fee INT,
        PRIMARY KEY (Customer_ID)
);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (234, 73, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (896, 46, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (678, 21, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (123, 43, 20);
INSERT INTO VIP_2 (Customer_ID, VIP_ID, Annual_fee) values (331, 37, 20);



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

SELECT p.Product_ID, Product_name, Manufacturer, Price, Seller_name, s.Seller_ID
                        FROM Products p , Seller s, Has h
                        WHERE p.Product_Name LIKE '%Lego%' AND p.Product_ID = h.Product_ID AND s.Seller_ID = h.Seller_ID
                        AND Price > ? AND Price < ?;

SELECT p.Product_Name, AvgPrice FROM Products p,
                        (SELECT Product_ID as PID, AvgPrice FROM
                            (SELECT Product_ID, AVG(Price) as AvgPrice FROM Has GROUP by Product_ID)
                            WHERE AvgPrice = (SELECT  MIN(AvgPrice)
                                                FROM (SELECT Product_ID, AVG(Price) as AvgPrice
                                                        FROM Has GROUP BY Product_ID)
                                                )
                        ) WHERE p.Product_ID = PID;


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

SELECT Seller_ID, Seller_Name FROM Seller WHERE Seller_ID = ANY
                        (
                        SELECT s.Seller_ID FROM Has h, Seller s
                        WHERE h.Seller_ID = s.Seller_ID AND h.Product_ID IN (SELECT Product_ID FROM Products)
                        GROUP BY s.Seller_ID HAVING COUNT(*) = (SELECT COUNT(*) FROM Products)
                        );

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
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('In Progress', 'alipay', '2017-2-6', '2017-2-7', '2017-2-11', 3, 45138574, 13776, 123, 3333, 3);
INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number , Product_ID, Customer_ID, Seller_ID, Quantity) values ('In Progress', 'bit-coin', '2017-6-6', '2017-6-7', '2017-6-11', 10, 41945574, 30874, 331, 3333, 5);

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
INSERT INTO Rate (Rating, Order_number) values (4, 41945574);

INSERT INTO Rate (Rating, Order_number) VALUES (3, 41945574)  where not 41945574 in (SELECT Order_number from Rate);

insert into rate (rating, order_number)
                            select 3, 41945574  from dual
                            where not exists (select order_number from rate where order_number = 41945574);


CREATE TABLE Has (
	Product_ID INT,
	Seller_ID INT,
	Quantity INT,
	Price INT,
        PRIMARY KEY(Product_ID, Seller_ID),
        FOREIGN KEY(Product_ID) REFERENCES Products(Product_ID),
        FOREIGN KEY(Seller_ID) REFERENCES Seller(Seller_ID)
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


commit;



commit;

