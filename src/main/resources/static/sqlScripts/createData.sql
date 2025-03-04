
CREATE TABLE Tasks (

                       id INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
                       title VARCHAR(50) NOT NULL,
                       description VARCHAR(225) NOT NULL ,
                       due_date DATE NOT NULL ,
                       is_completed BOOLEAN ,
                       priority enum ('HIGH','MEDIUM', 'LOW') NOT NULL,
                       is_critical BOOLEAN
);