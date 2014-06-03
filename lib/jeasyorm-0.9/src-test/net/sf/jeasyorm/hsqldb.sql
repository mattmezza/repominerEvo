create table tperson (
    id integer primary key,
    first_name varchar(100),
    LAST_NAME varchar(100)
);

create table taddress (
    id integer primary key,
    person_id integer,
    street varchar(100),
    city varchar(100)
);

create table tnote (
    id integer primary key,
    content longvarchar,
    attachment longvarbinary
);

alter table taddress add constraint fk_address_person 
    foreign key (person_id) references tperson (id);

create sequence JEASYORM_SEQUENCE;
