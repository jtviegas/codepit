CREATE OR REPLACE FUNCTION custom_create_type(_type varchar(50), _type_definition varchar(200), _owner varchar(20)) 
returns integer
as 	
$BODY$
declare
	_rows integer;
	_result bit := 1;
begin

select count(pg_catalog.pg_type.typname) into _rows from pg_catalog.pg_type where pg_catalog.pg_type.typname=_type;

if _rows > 0 then
	execute 'drop type ' || _type || ' cascade';
end if;

execute 'create type ' || _type || ' as (' || _type_definition || ')' ;
execute 'alter type ' || _type || ' owner to ' || _owner;

_result := 0;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';