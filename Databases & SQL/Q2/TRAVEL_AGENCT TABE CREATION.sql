CREATE DATABASE TRAVEL_AGENCY

CREATE TABLE hotel(
hotel_id bigint NOT NULL,
name varchar(25) NOT NULL,
city varchar(255)  NOT NULL,
adress varchar(255)  NOT NULL,
country varchar(255)  NOT NULL,
star_rating int NOT NULL,
room_price int NOT NULL,
beds int NOT NULL,
PRIMARY KEY(hotel_id)
)


CREATE TABLE  traveler(
traveler_id bigint NOT NULL,
firstname varchar(255)  NOT NULL,
lastname varchar(255)  NOT NULL,
Dob DATE NOT NULL,
email  varchar(100)  ,
phone varchar(14) NOT  NULL,
gender char(1) ,
trip_id int ,
PRIMARY KEY(traveler_id)
 
) 

CREATE TABLE  agent(
agent_id bigint   NOT NULL ,
Fname varchar(255)  NOT NULL,
Lname varchar(255)  NOT NULL,
salary int NOT NULL,
PRIMARY KEY(agent_id)

)


CREATE TABLE flight(
price float NOT NULL,
duration float NOT NULL,
company varchar(255) NOT NULL,
flightNumber varchar(255) NOT NULL,
d_City varchar(255) NOT NULL,
d_country varchar(255) NOT NULL,
d_date date NOT NULL,
d_airport varchar(255) NOT NULL,
d_landTime time NOT NULL,
r_City varchar(255) NOT NULL,
r_country varchar(255) NOT NULL,
r_date date NOT NULL,
r_airport varchar(255) NOT NULL,
r_landTime time NOT NULL
PRIMARY KEY(flightNumber)
)

CREATE TABLE vacation_package(
id bigint   NOT NULL ,
hotel_id bigint   NOT NULL ,
flight_id varchar(255)   NOT NULL ,
city varchar(255) NOT NULL,
country varchar(255) NOT NULL,
departure_date date NOT NULL,
return_date date NOT NULL,
final_price int not null, 
PRIMARY KEY(id),


CONSTRAINT fk_VP_hotel_id
FOREIGN KEY (hotel_id) 
REFERENCES hotel(hotel_id),

CONSTRAINT fk_VP_flight_id
FOREIGN KEY (flight_id) 
REFERENCES flight(flightNumber),



)

CREATE TABLE trip(
trip_id bigint NOT NULL,
T_id bigint NOT NULL,
agent_id bigint NOT NULL,
hotel_id bigint ,
flight_id varchar(255) ,
VP_id bigint,
destenation_country varchar(255) ,
destenation_city varchar(255) ,
total_price int ,
end_date date ,
PRIMARY KEY(trip_id),

CONSTRAINT fk_trip_traveler_id
FOREIGN KEY (T_id) 
REFERENCES traveler(traveler_id),

CONSTRAINT fk_trip_agent_id
FOREIGN KEY (agent_id) 
REFERENCES agent(agent_id),

CONSTRAINT fk_trip_flight_id
FOREIGN KEY (flight_id) 
REFERENCES flight(flightNumber),

CONSTRAINT fk_trip_hotel_id
FOREIGN KEY (hotel_id) 
REFERENCES hotel(hotel_id),

CONSTRAINT fk_trip_VP_id
FOREIGN KEY (VP_id) 
REFERENCES vacation_package(id),


)
insert into flight
(flightNumber,company,d_airport ,d_country,d_City,d_date,d_landTime,r_airport,r_country,r_City,r_date,r_landTime,duration,price) 
values
('123', 'Alaska Airlines', 'Gawtick', 'U.S.A','Detroit','2020-12-01','06:00:00','Sheremetyevo','Russia','Moscow','2021-01-01','06:00:00',6,500),
('456', 'Allegiant Air', 'Luton', 'Russia','Saint Petersburg','2020-10-01','06:00:00','Barajas','Spain','Madrid','2021-11-01','07:00:00',3,500),
('789', 'JetBlu', 'Malmo', 'Israel','Tel-aviv','2020-09-01','06:00:00','DCA','U.S.A','Washington','2021-10-01','08:00:00',12,1000),
('159', 'Qatar Airway', 'Laguardia', 'Brazil',' Brasilia','2020-08-01','06:00:00','Ben Gurion ','Israel','Tel-Aviv','2020-09-01','08:00:00',15,1000)

insert into hotel
(hotel_id, name,city,adress,country,star_rating,room_price,beds) 
values
(1, 'Plaza', 'Madrid', 'Extramuros 74','Spain',5,1000,3),
(2, 'Hilton', 'Tel-aviv', '4068  Nixon Avenue','Israel',4,600,2),
(3, 'Rotana', 'Moscow', '3136  Walkers Ridge Way','Russia',3,440,2),
(4, 'venu', 'Washington', '394  Crummit Lane','U.S.A',3,300,1)


insert into vacation_package
(id,country ,city ,departure_date ,return_date ,flight_id ,hotel_id ,final_price) 
values
(1, 'Spain', 'Madrid', '2020-01-01','2020-02-03','456',1,5000),
(2, 'Russia', 'Moscow', '2022-01-01','2022-02-03','123',3,4000),
(3, 'U.S.A', 'Washimgton', '2019-01-01','2020-02-03','789',4,3000),
(4, 'Israel', 'Tel-aviv', '2020-01-01','2020-02-03','159',2,4000),
(5, 'Spain', 'Madrid', '2020-01-01','2020-02-03','456',1,400),
(6, 'Russia', 'Moscow', '2022-01-01','2022-02-03','123',3,300),
(7, 'U.S.A', 'Washimgton', '2019-01-01','2020-02-03','789',4,200),
(8, 'Israel', 'Tel-aviv', '2020-01-01','2020-02-03','159',2,300)

insert into agent
(agent_id,Fname,Lname,salary) 
values
(11,'Yossi','Hen',10000),
(12,'Shlomo','Malka',5000),
(13,'Will ','smith',11000),
(14,'Tory','Lanez',5000)

insert into traveler 
(traveler_id, firstname,lastname,Dob,trip_id,gender,phone,email) 
values
(1, 'avi', 'moshe', '1900-11-01',1,'M','054-3216548','isotopian@msn.com'),
(2, 'daniel', 'kacher', '1903-12-21',1,'M','052-3852548','pmint@yahoo.com'),
(3, 'alex', 'gringberg', '1985-06-06',2,'M','050-3295148','dpitts@yahoo.com'),
(4, 'lidia', 'azraiev', '1980-04-15',3,'F','05s-3216137','damian@att.net')

insert into trip 
(trip_id, destenation_country,destenation_city,end_date,total_price,agent_id,hotel_id,flight_id,VP_id,T_id) 
values
(1, 'Spain', 'Madrid', '2020-11-01',4000,11,1,'456',1,1),
(2, 'Israel', 'Tel-aviv', '2020-12-21',3000,12,2,'789',2,1),
(3, 'Russia', 'Moscow', '2020-06-06',2000,12,3,'456',3,2),
(4, 'Spain', 'Madrid', '2020-04-15',1500,13,4,'159',4,3)



