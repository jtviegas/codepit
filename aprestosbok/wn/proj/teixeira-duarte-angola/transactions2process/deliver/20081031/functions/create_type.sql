CREATE OR REPLACE FUNCTION custom_create_type(_type varchar(50), _type_definition varchar(200), _owner varchar(20)) 
returns integer
as 	
$BODY$
declare
	_rows integer;
	_result bit := 1;
	_sql text;
begin
raise log '[>>]<IN> custom_create_type';
_sql := 'select count(pg_catalog.pg_type.typname) from pg_catalog.pg_type where pg_catalog.pg_type.typname=''' || _type || '''';
raise debug '[>>]going to execute sql -> % <-', _sql;
execute _sql into _rows;
raise debug '[>>]sql execution returned-> % <-', _rows;

if _rows > 0 then
	_sql := 'drop type ' || _type || ' cascade';
	raise debug '[>>]going to execute sql -> % <-', _sql;
	execute _sql;
	GET DIAGNOSTICS _rows = ROW_COUNT;
	raise debug '[>>]sql execution returned-> % <-', _rows;
end if;
	
_sql := 'create type ' || _type || ' as (' || _type_definition || ')' ;
raise debug '[>>]going to execute sql -> % <-', _sql;
execute _sql;
GET DIAGNOSTICS _rows = ROW_COUNT;
raise debug '[>>]sql execution returned-> % <-', _rows;

_sql := 'alter type ' || _type || ' owner to ' || _owner;
raise debug '[>>]going to execute sql -> % <-', _sql;
execute _sql;
GET DIAGNOSTICS _rows = ROW_COUNT;
raise debug '[>>]sql execution returned-> % <-', _rows;

_result := 0;
raise log '[>>]<OUT> custom_create_type(%)', _result;
RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_create_type(_type varchar(50), _type_definition varchar(200), _owner varchar(20)) is 
'
Helper function for type creation, checking the existence of the desired type and deleting it,
cascading the delete onto the associated functions.
arguments:	
	_type			varchar(50)		type name
	_type_definition	varchar(200)		system types comma separated list
	_owner			varchar(20)		user to own the type
returns  : 0 for sucess
';
ALTER FUNCTION custom_create_type(_type varchar(50), _type_definition varchar(200), _owner varchar(20)) OWNER TO tplinux;



