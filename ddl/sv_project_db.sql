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

create table sv_users (
	id integer primary key auto_increment,
	login varchar(100) not null,
	password varchar(200) not null,
	registration_date date,
	last_visit date
);

create table sv_people (
	id integer primary key auto_increment,
	last_name varchar(50) not null,
	first_name varchar(50) not null,
	middle_name varchar(50),
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
	foreign key (caught_by) references sv_inspectors(id) on delete cascade
);

insert into sv_users (id, login, password, registration_date, last_visit) values (1, 'test', md5('test'), current_date(), null);

insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (1, 'Magnificent', 'Suleiman', null, str_to_date('1494-11-04', '%Y-%m-%d'), 'SDDD@@2');
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (2, 'Isabella', 'Castile', null, str_to_date('1451-04-22', '%Y-%m-%d'),	null);
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (3, 'Curie', 'Marie', 'Sklodowska', str_to_date('1867-11-07', '%Y-%m-%d'), null);	
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (4, 'Hemingway', 'Ernest', '#333#', str_to_date('1899-07-12', '%Y-%m-%d'), '33WER12-21');
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (5, 'Einstein', 'Albert', null, str_to_date('1879-03-14', '%Y-%m-%d'), null);
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (6, 'Thatcher', 'Margaret', 'Hilda', str_to_date('1925-10-13', '%Y-%m-%d'), 'XXDHG-12-2S');
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (7, 'Newton', 'Isaac', null, str_to_date('1642-12-25', '%Y-%m-%d'), null);
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (8, 'Lavoisier', 'Antoine', null, str_to_date('1743-08-26', '%Y-%m-%d'), null);
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (9, 'Maxwell', 'James', 'Clerk', str_to_date('1831-06-13', '%Y-%m-%d'), 'YTD-123SS');
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (10, 'Bonaparte', 'Napoleon', null, str_to_date('1769-08-15', '%Y-%m-%d'), null);	
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (11, 'Shelley', 'Mary', 'Wollstonecraft', str_to_date('1797-08-30', '%Y-%m-%d'), null);
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (12, 'Mozart', 'Wolfgang', 'Amadeus', str_to_date('1756-01-27', '%Y-%m-%d'), null);
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (13, 'Montessori', 'Maria', null, str_to_date('1870-08-31', '%Y-%m-%d'), null);
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (14, 'Kirchhoff', 'Gustav', 'Robert', str_to_date('1824-03-11', '%Y-%m-%d'), '0023VFG-1');
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (15, 'Anthony', 'Susan', 'Brownell', str_to_date('1820-02-15', '%Y-%m-%d'), null);
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (16, 'Marley', 'Bob', 'Nesta', str_to_date('1945-02-05', '%Y-%m-%d'), null);
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (17, 'Washington', 'George', null, str_to_date('2021-05-16', '%Y-%m-%d'), 'BV-##233/36S');
insert into sv_people (id, last_name, first_name, middle_name, born, identification_number) values (18, 'Faltskog', 'Agnetha', 'Ase', str_to_date('1950-04-05', '%Y-%m-%d'), null);

insert into sv_inspectors (id, person_id, badge_number, rank_id, department_id) values (1, 4, 'AAA', 1, 1);
insert into sv_inspectors (id, person_id, badge_number, rank_id, department_id) values (2, 7, '1234556', 2, 1);
insert into sv_inspectors (id, person_id, badge_number, rank_id, department_id) values (3, 17, 'ZZ-123', 2, null);

insert into sv_departments (id, title, address) values (1, 'NYPD', '1 Police Plaza - New York, NY');
insert into sv_departments (id, title, address) values (2, 'Ghost Dep', 'Somwhere Place, Lost, ST');

insert into sv_ranks (id, title) values (1, 'Some rank');
insert into sv_ranks (id, title) values (2, 'Antoher Rank');

insert into sv_vehicle_marks (id, title) values (1, 'Reno');

insert into sv_vehicle_models (id, title, mark_id) values (1, 'Logan', 1);
insert into sv_vehicle_models (id, title, mark_id) values (1, 'Latitude', 1);

insert into sv_countries (id, title, iso) values (1, 'Belarus', 'BLR');
insert into sv_countries (id, title, iso) values (2, 'Brazil', null);
insert into sv_countries (id, title, iso) values (3, 'Japan', null);
insert into sv_countries (id, title, iso) values (4, 'Argentina', null);
insert into sv_countries (id, title, iso) values (5, 'USA', null);
insert into sv_countries (id, title, iso) values (6, 'Waffleland', 'WUF');
insert into sv_countries (id, title, iso) values (7, 'Thailand', null);
insert into sv_countries (id, title, iso) values (8, 'France', null);
insert into sv_countries (id, title, iso) values (9, 'Russia', null);
insert into sv_countries (id, title, iso) values (10, 'Mexico', 'MEX');
insert into sv_countries (id, title, iso) values (11, 'India', 'IND');
insert into sv_countries (id, title, iso) values (12, 'Chile', null);
insert into sv_countries (id, title, iso) values (13, 'Italy', 'ITA');
insert into sv_countries (id, title, iso) values (14, 'Paraguay', null);
insert into sv_countries (id, title, iso) values (15, 'China', null);
insert into sv_countries (id, title, iso) values (16, 'Canada', null);
insert into sv_countries (id, title, iso) values (17, 'Greece', null);

insert into sv_regions (id, title, country_id) values (1, 'Minsk Region', 1);
insert into sv_regions (id, title, country_id) values (2, 'Provincia de Catamarca', 12);
insert into sv_regions (id, title, country_id) values (3, 'Smolensk region', 9);

-- drop procedure if exists generateViolations;
/*
create procedure generateViolations(in value integer, in p_start date, in p_end date)
begin
	declare i integer default 0;
	while i < value do
		insert into sv_violations (violation_date, region_id, speed_limit, actual_speed, guilty_id, vehicle_id, caught_by, is_repaid, note)
		with rng as (
		select str_to_date(date_format(from_unixtime(a.d_start + floor(rand() * a.d_range)), '%d-%m-%Y'), '%d-%m-%Y') rand_date
		from (select unix_timestamp(p_start) d_start,
		(unix_timestamp(p_end) - unix_timestamp(p_start)) d_range) a
		), notes as (
		select null text
		union all select 'stop you there'
		union all select 'another day - another violation'
		union all select 'crazy fast'
		union all select 'pathetic note'
		union all select 'caught finally'
		union all select 'next time, buddy'
		union all select 'paid'
		union all select 'random row'
		), pre as (
		select b.rand_date dt,
		(select id from sv_regions order by rand() limit 1) rg,
		floor(50 + rand() * 30) li,
		(select id from sv_people order by rand() limit 1) gu,
		(select id from sv_vehicles order by rand() limit 1) ve,
		round(rand()) re,
		(select text from notes order by rand() limit 1) nt
		from rng b
		) select a.dt, a.rg, a.li, a.li + floor(rand() * 20) ac,
		a.gu, a.ve, (select id from sv_inspectors where id != a.gu order by rand() limit 1) ch,
		a.re, a.nt from pre a;
		set i = i + 1;
	end while;
end
/*
-- delete from sv_violations where id > 2;
-- SHOW PROCESSLIST;
-- kill 198;
-- grant all on sv_project_db.* to sys;
-- select table_name from information_schema.tables where table_type = 'BASE TABLE' and table_schema = 'sv_project_db';