USE  TRAVEL_AGENCY

--Weather condition forces flight from and to a certain airport to be canceled
DELETE FROM flight WHERE 
flight.d_date=getDate() AND  flight.r_date=getDate() 
AND flight.r_airport= 'Gawtick' AND flight.d_airport= 'Gawtick'


--incrase the salary of all the lowest payed employees by 500
UPDATE agent
SET salary =salary+500
WHERE agent.salary = MIN(salary);

--when an agent leaves the company , his record must be deleted
DELETE FROM agent WHERE agent_id='12';

--when a new traveler decides to book a new trip , he is first being registered
insert into traveler 
(traveler_id, firstname,lastname,Dob,trip_id,gender,phone,email) 
values
(5, 'Shaq', 'Oniel', '1956-12-06',4,'M','054-3214548','shaqtus@gmail.com')


