-- SQL Script for MySQL

CREATE  TABLE IF NOT EXISTS `test`.`persons` (
  `persons_id` INT NOT NULL AUTO_INCREMENT ,
  `persons_name` VARCHAR(255) NULL ,
  `persons_age` INT NULL ,
  `persons_address` VARCHAR(255) NULL ,
  PRIMARY KEY (`persons_id`)
);

CREATE  TABLE IF NOT EXISTS `test`.`persons2` (
  `persons_id` INT NOT NULL AUTO_INCREMENT ,
  `persons_name` VARCHAR(255) NULL ,
  `persons_age` INT NULL ,
  `persons_address` VARCHAR(255) NULL ,
  PRIMARY KEY (`persons_id`)
);

insert into persons(persons_id, persons_name, persons_age, persons_address) values(1, 'Ignat Marinov', 12, null);
insert into persons(persons_id, persons_name, persons_age, persons_address) values(2, 'Georgi Stanchev', 33, null);
insert into persons(persons_id, persons_name, persons_age, persons_address) values(3, 'Marin Marinov', 4, null);
insert into persons(persons_id, persons_name, persons_age, persons_address) values(4, 'Svetlozar Georgiev', 24, null);
insert into persons(persons_id, persons_name, persons_age, persons_address) values(5, 'Kostadin Ivanov', 54, null);
insert into persons(persons_id, persons_name, persons_age, persons_address) values(6, 'Milen Cvetkov', 53, null);
insert into persons(persons_id, persons_name, persons_age, persons_address) values(7, 'Nikolay Krumov', 5, null);
insert into persons(persons_id, persons_name, persons_age, persons_address) values(8, 'Rumen Victorov', 3, 'Sofia, Nezabravka 4');
insert into persons(persons_id, persons_name, persons_age, persons_address) values(9, 'Viktor Marinov', 6, null);
insert into persons(persons_id, persons_name, persons_age, persons_address) values(10, 'Pesho Stoev', 2, null);
