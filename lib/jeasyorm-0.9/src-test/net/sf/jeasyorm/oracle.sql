drop table taddress;
drop table tperson;
drop table tnote;
drop sequence jeasyorm_sequence;

create table tperson (
    id integer primary key,
    first_name varchar2(100 char),
    LAST_NAME varchar2(100 char)
);

create table taddress (
    id integer primary key,
    person_id integer,
    street varchar2(100 char),
    city varchar2(100 char)
);

create table tnote (
    id integer primary key,
    content clob,
    attachment blob
);

alter table taddress add constraint fk_address_person 
    foreign key (person_id) references tperson (id);

create sequence jeasyorm_sequence;
