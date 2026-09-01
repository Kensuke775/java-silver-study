-- BOOKテーブルが存在する場合は削除
DROP TABLE BOOK;

-- BOOK テーブルの作成
CREATE TABLE BOOK (
    ID INTEGER PRIMARY KEY,
    TITLE VARCHAR(30),
    AUTHOR VARCHAR(20),
    PRICE INTEGER
);
-- データの挿入
INSERT INTO BOOK VALUES(1, 'Java for Beginners', 'Duke', 4500);
INSERT INTO BOOK VALUES(2, 'History of Japan', 'Johnson', 5000);
INSERT INTO BOOK VALUES(3, 'English Grammar', 'Murphy', 4000);
INSERT INTO BOOK VALUES(4, 'Basic Music Theory', 'Smith', 3500);