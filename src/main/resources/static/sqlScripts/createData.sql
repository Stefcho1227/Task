
CREATE TABLE Tasks (

                       id INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
                       title VARCHAR(50) NOT NULL,
                       description VARCHAR(225),
                       due_date DATE,
                       is_completed BOOLEAN,
                       priority VARCHAR(50),
                       is_critical BOOLEAN
);