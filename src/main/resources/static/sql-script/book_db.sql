CREATE DATABASE IF NOT EXISTS book;

CREATE TABLE IF NOT EXISTS `book`.`user` (
  `id` INT NOT NULL,
  `username` NVARCHAR(45) NULL,
  `password` NVARCHAR(70) NULL,
  `first_name` NVARCHAR(45) NULL,
  `last_name` NVARCHAR(45) NULL,
  `email` NVARCHAR(100) NULL,
  `avatar` NVARCHAR(100) NULL,
  `enabled` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book`.`role` (
  `id` INT NOT NULL,
  `name` NVARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book`.`user_role` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  CONSTRAINT `fk_user_role_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `book`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `book`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book`.`book` (
  `id` INT NOT NULL,
  `title` NVARCHAR(200) NULL,
  `description` NVARCHAR(1000) NULL,
  `author` NVARCHAR(100) NULL,
  `image` NVARCHAR(100) NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `enabled` INT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_book_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `book`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book`.`comment` (
  `id` INT NOT NULL,
  `message` NVARCHAR(1000) NULL,
  `user_id` INT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `book_id` INT NOT NULL,
  PRIMARY KEY (`id`, `book_id`),
  CONSTRAINT `fk_comment_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `book`.`book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;