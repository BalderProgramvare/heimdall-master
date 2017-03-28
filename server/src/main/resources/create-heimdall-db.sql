drop database if exists heimdall;

create database heimdall;
drop user 'heimdall'@'localhost';

create user 'heimdall'@'localhost' identified by 'apabase';
grant all privileges on heimdall.* to 'heimdall'@'localhost';

use heimdall;

create table checkIn (

  id bigint not null AUTO_INCREMENT,
  uuid varchar(40),     -- Unique id of the device
  lat decimal(9,6),     -- Latitude in decimal degrees
  lon decimal(9,6),     -- Longitude in decimal degrees
  tstamp datetime,
  tzone varchar(32),
  primary key (id)
) ;

