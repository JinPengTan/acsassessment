-- Create the database
CREATE DATABASE IF NOT EXISTS acs;
USE acs;

-- Create a user named 'acs' with the password 'acs'
CREATE USER 'acs'@'%' IDENTIFIED BY 'acs';
GRANT ALL PRIVILEGES ON acs.* TO 'acs'@'%';
FLUSH PRIVILEGES;
