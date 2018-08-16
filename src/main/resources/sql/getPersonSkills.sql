select s.skillid, skillname
from schema_name.persons p
inner join schema_name.personskills ps on p.personid = ps.personid
inner join schema_name.skills s on ps.skillid = s.skillid
where p.personId = :personId