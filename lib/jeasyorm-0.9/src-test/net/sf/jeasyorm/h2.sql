drop table taddress;
drop table tperson;
drop table tnote;

create table tperson (
    id integer auto_increment primary key,
    first_name varchar(100),
    LAST_NAME varchar(100)
);

create table taddress (
    id integer auto_increment primary key,
    person_id integer,
    street varchar(100),
    city varchar(100)
);

create table tnote (
    id integer auto_increment primary key,
    content clob,
    attachment blob
);

alter table taddress add constraint fk_address_person 
    foreign key (person_id) references tperson (id);
