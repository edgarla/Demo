create table if not exists persons(
  personid bigint not null auto_increment,
  firstname text not null,
  lastname text not null,
  primary key (personid)
);