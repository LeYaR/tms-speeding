set sql_notes = 0;

create schema if not exists sv_project_db;
use sv_project_db;

set @tbl = null;
set foreign_key_checks = 0;
select group_concat(table_schema, '.', table_name) into @tbl
from information_schema.tables 
where table_schema = 'sv_project_db' and lower(table_name) like 'sv\_%';
select if(@tbl is null, 'select 1', concat('drop table if exists ', @tbl)) into @tbl;
prepare stm from @tbl;
execute stm;
deallocate prepare stm;
set foreign_key_checks = 1;

create table sv_people (
	id integer primary key auto_increment,
	last_name varchar(50) not null,
	first_name varchar(50) not null,
	middle_name varchar(50) not null,
	born date,
	identification_number varchar(14)
);

create table sv_ranks (
	id integer primary key auto_increment,
	title varchar(70) not null
);

create table sv_departments (
	id integer primary key auto_increment,
	title varchar(100) not null,
	address varchar(100)
);

create table sv_inspectors (
	id integer primary key auto_increment,
	person_id integer not null,
	badge_number varchar(20),
	rank_id integer,
	department_id integer,
	foreign key (person_id) references sv_people(id) on delete cascade,
	foreign key (rank_id) references sv_ranks(id) on delete set null,
	foreign key (department_id) references sv_departments(id) on delete set null
);

create table sv_drivers_licenses (
	id integer primary key auto_increment,
	person_id integer not null unique,
	license_number varchar(30),
	issued date not null,
	expires date not null,
	is_revoked boolean,
	last_revocation date,
	foreign key (person_id) references sv_people(id) on delete cascade
);

create table sv_vehicle_marks (
	id integer primary key auto_increment,
	title varchar(50) not null);

create table sv_vehicle_models (
	id integer primary key auto_increment,
	title varchar(50) not null,
	mark_id integer not null,
	foreign key (mark_id) references sv_vehicle_marks(id) on delete cascade
);

create table sv_countries (
	id integer primary key auto_increment,
	title varchar(70) not null,
	iso varchar(3)
);

create table sv_regions (
	id integer primary key auto_increment,
	title varchar(100) not null,
	country_id integer not null,
	foreign key (country_id) references sv_countries(id) on delete cascade
);

create table sv_vehicles (
	id integer primary key auto_increment,
	vin varchar(17),
	reg_number varchar(10) not null,
	model_id integer,
	region_id integer,
	foreign key (model_id) references sv_vehicle_models(id) on delete set null,
	foreign key (region_id) references sv_regions(id) on delete set null
);

create table sv_violations (
	id integer primary key auto_increment,
	violation_date timestamp not null,
	region_id integer,
	speed_limit smallint not null,
	actual_speed smallint not null,
	guilty_id integer not null,
	vehicle_id integer not null,
	caught_by integer not null,
	is_repaid boolean,
	note varchar(500),
	foreign key (region_id) references sv_regions(id) on delete set null,
	foreign key (guilty_id) references sv_people(id) on delete cascade,
	foreign key (vehicle_id) references sv_vehicles(id) on delete cascade,
	foreign key (caught_by) references sv_people(id) on delete cascade
);

-- grant all on sv_project_db.* to sys;
-- select table_name from information_schema.tables where table_type = 'BASE TABLE' and table_schema = 'sv_project_db';
-- insert into sv_ranks (title) values ('some_rank');
-- select * from sv_ranks;