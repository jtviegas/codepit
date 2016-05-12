---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION schlepp() 
returns void
as 	
$BODY$
declare
	_r RECORD;
	_query text := '';
begin

for _r IN EXECUTE _query LOOP
	
	--with pos and transaction
	
	--update do saletype para 5 no header,items, trailer

	--inserir o media

	--inserir ou update no media

	businessdate=record.bdate;
	return next businessdate;
end loop;

RETURN;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION schlepp() is 
'
Shakira, Beyoncé, all the works...
';
ALTER FUNCTION schlepp() OWNER TO tplinux;

