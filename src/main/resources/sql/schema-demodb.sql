create table if not exists persons (
  personId bigint not null auto_increment,
  firstName text not null,
  lastName text not null,
  primary key (personId)
);

create table if not exists skills (
  skillId bigint not null auto_increment,
  skillName text not null,
  primary key (skillId)
);

create table if not exist personskills (
  personId bigint not null,
  skillId bigint not null,
  primary key (personId, skillId),
  foreign key (personId) references persons(personId),
  foreign key (skillId) references skills(skillId)
);