USE Bookstore

--^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
--------------------------------------------------------------------I.	Query the data---------------------------------------------------------------------------
--^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

--1.Avarage length of all CDs
SELECT AVG(Cdlength) AS Avarage
FROM CD$

--2.Avarage length of all CDs made by The Beatles
SELECT AVG(Cdlength) AS Avarage
FROM CD$
WHERE artist='The Beatles'

--3.Avarage lenght of all CDs that come from the supplier B
SELECT AVG(Cdlength) AS Avarage 
FROM CD$,Product$
WHERE Product$.supplier='B' AND CD$.PID=Product$.PID

--4.Find all suppliers that sell CDs that have at least 100 minutes of music
SELECT DISTINCT supplier
FROM CD$,Product$
WHERE CD$.Cdlength>100


--5.For each supplier, find the average DVD length
SELECT Product$.supplier,AVG(length)
FROM Product$,DVD$
WHERE Product$.PID=DVD$.PID
GROUP BY Product$.supplier

--6.Find the songs that can be found on more than one CD
SELECT Song$.name
FROM Song$
GROUP BY Song$.name
HAVING count(Song$.name)>1

--7.Find all books that have the word ‘hundred’ in the title   
SELECT DISTINCT Book$.title
FROM Book$
WHERE Book$.title LIKE '%hundred%'

--8.Find all suppliers that sell books but not CDs
SELECT DISTINCT supplier
FROM Product$,Book$,CD$
WHERE Product$.PID=Book$.PID AND Product$.PID!=CD$.PID

--9.Find the average price of all books and DVDs made by supplier ‘B’
SELECT AVG(price) as 'Avarage Price'
FROM Product$,Book$,DVD$
WHERE (Product$.type='Book' OR Product$.type='DVD') AND Product$.supplier='B'


--10.Find suppliers that sell at least two different products (book or DVD) that were made in 2011

SELECT DISTINCT Product$.supplier
FROM Product$,Book$,DVD$
WHERE (Product$.type='Book' AND Product$.type='DVD') 
GROUP BY Product$.supplier
HAVING count(Product$.supplier)>=2 AND (Book$.YearPub=2011 OR DVD$.yearmade=2011)



--11.Find manufacturers who sell at least three different DVDs
SELECT DISTINCT Product$.supplier
FROM Product$
WHERE Product$.type='DVD' 
GROUP BY Product$.supplier
HAVING COUNT(Product$.supplier)>=3


--12.	Find the books with the highest price
SELECT TOP 3 Product$.price 
FROM Product$
WHERE Product$.type='Book'
ORDER BY Product$.price DESC 

--13.For each author, list the number of books that are available for sale
SELECT DISTINCT Book$.author,count(Book$.title) AS  'Number Of Books'
FROM Book$,Product$
WHERE Book$.PID=Product$.PID
GROUP BY Book$.author

--14.Find all the suppliers that sell DVDs with ‘Rebel Wilson’
SELECT DISTINCT Product$.supplier,Product$.PID
FROM Product$,Actor$
WHERE Product$.PID=Actor$.Movie AND Actor$.[Name ]='Rebel Wilson'



--^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
--------------------------------------------------------------------II.	Update the data--------------------------------------------------------------------------
--^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

--1.	Add key constraints to the tables according to the data definitions
ALTER TABLE BOOK
ADD CONSTRAINT fk_book_product
FOREIGN KEY (PID) 
REFERENCES Product(PID)

ALTER TABLE CD
ADD CONSTRAINT fk_CD_product
FOREIGN KEY (PID) 
REFERENCES Product(PID)

ALTER TABLE DVD
ADD CONSTRAINT fk_DVD_product
FOREIGN KEY (PID) 
REFERENCES Product(PID)

ALTER TABLE Song
ADD CONSTRAINT fk_Song_CD
FOREIGN KEY (album) 
REFERENCES CD(PID)

ALTER TABLE Actor
ADD CONSTRAINT fk_Actor_DVD
FOREIGN KEY (movie) 
REFERENCES DVD(PID)


--2.	Add to the database the movie ‘Stardust’ from supplier C that was made by Paramount in 2007 (use two insert statements)
INSERT INTO DVD$(PID,title,studio,yearmade,length) VALUES(8125,'Stardust','Paramount',2007,128);
INSERT INTO Product$(PID,type,supplier,price) VALUES(8125,'DVD','B',90);

--3.Supplier C buys manufacturer B.  Change all the products made by B so they are listed as made by C
UPDATE Product$
SET supplier='C'
WHERE supplier='B'

--4.For each CD made by ‘Sting’, add five minutes to the length of the CD
UPDATE CD$
SET Cdlength=Cdlength+5
WHERE artist='Sting'

--5.For each book sold by supplier B, add 1 to the edition

UPDATE Book$
SET Edition=Edition+1
FROM Book$
INNER JOIN Product$
	on Product$.supplier='B' and Product$.PID=Book$.PID

--6.Delete all books that were published before 1980

DELETE FROM Book$
WHERE YearPub<1980

--7.Delete all books sold by a supplier that does not sell CDs

DELETE Book$
FROM Book$
INNER JOIN Product$
	on Product$.type='CD' AND Product$.PID=Book$.PID


