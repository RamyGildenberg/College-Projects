USE TRAVEL_AGENCY


--show all holtels in a certain city  , show the highest star rating and lowest price for the travelers maximum room budget of 2000

SELECT * 
FROM hotel
WHERE hotel.star_rating=(SELECT MAX(hotel.star_rating) FROM hotel)
AND hotel.room_price < 2000 
ORDER BY hotel.room_price DESC




--show all the flights to a certain destination sorted by price , show the cheapest first

SELECT *
FROM flight
where d_city='Detroit' AND d_country='U.S.A'
ORDER BY price DESC

--show all the flights that are departuring this day and return a certain other day
SELECT * 
FROM flight 
WHERE d_date='2020-12-01' AND r_date='2021-01-01'


--show all the trips that a certain agant booked 

SELECT *
FROM TRIP
WHERE agent_id=11

--show all the trips that a certain traveler booked
select * 
from trip
where T_id=1

--queries show best deal vacation package to each destination that exists 
SELECT  id,vacation_package.hotel_id,flight_id,vacation_package.city,vacation_package.country,vacation_package.final_price FROM vacation_package,flight,hotel WHERE 
hotel.hotel_id=vacation_package.hotel_ID AND flight.flightNumber=vacation_package.flight_id AND
hotel.star_rating=(SELECT MAX(hotel.star_rating)FROM hotel WHERE hotel.hotel_id=vacation_package.hotel_ID AND flight.flightNumber=vacation_package.flight_id ) 
AND vacation_package.final_price=(SELECT  MIN(vacation_package.final_price )FROM vacation_package WHERE hotel.hotel_id=vacation_package.hotel_ID AND flight.flightNumber=vacation_package.flight_id) 

--show all the flights in specific date
SELECT flightNumber,d_landTime,d_city, d_landTime,d_date 
FROM flight 
WHERE r_date='2021-01-01'

--show matches of all hotel and their address to flight destination
SELECT city,adress,star_rating FROM hotel,flight 
WHERE hotel.city= flight.d_City

--show hotels sorted by star rating in Madrid
SELECT name,star_rating,adress,room_price 
FROM hotel
WHERE star_rating BETWEEN 2 AND 5 AND city='Madrid' 
ORDER BY star_rating DESC

--display travelers identification info sorted by last name
SELECT firstname,lastname,traveler_id,gender,dob FROM traveler ORDER BY lastname ASC;

--display the second highest payed agent
SELECT TOP  1  MAX(agent.salary) AS Salary,agent.Lname,agent.Fname FROM agent 
WHERE salary < (SELECT MAX(salary) FROM agent)
GROUP BY agent.Lname,agent.Fname
ORDER BY salary DESC

--show vacation package for family of 3 and orgenize by lowest price frist
SELECT vacation_package. * 
FROM vacation_package,hotel 
WHERE vacation_package.hotel_ID =hotel.hotel_id AND hotel.beds>1 AND hotel.beds<4
ORDER BY final_price ASC



