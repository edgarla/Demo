create table if not exists persons (
  personid bigint not null auto_increment,
  firstname text not null,
  lastname text not null,
  primary key (personid)
);

create table if not exists skills (
  skillid bigint not null auto_increment,
  skillname text not null,
  primary key (skillid)
);

create table if not exist personskills (
  personid bigint not null,
  skillid bigint not null,
  primary key (personid, skillid),
  foreign key (personid) references persons(personid),
  foreign key (skillid) references skills(skillid)
);