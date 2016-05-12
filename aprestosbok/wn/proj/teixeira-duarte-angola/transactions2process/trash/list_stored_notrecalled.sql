-- DROP TABLE list_stored_notrecalled;

CREATE TABLE list_stored_notrecalled
(
  termnmbrs int2,
  transnmbrs int4,
  bdates int4,
  storedamnts int8,
  custnmbr numchar
) 
WITH OIDS;
ALTER TABLE list_stored_notrecalled OWNER TO tplinux;
