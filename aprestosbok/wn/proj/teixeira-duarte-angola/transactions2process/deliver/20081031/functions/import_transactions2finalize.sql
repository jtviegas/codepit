CREATE OR REPLACE FUNCTION custom_import_transactions2finalize( _file varchar(100), _table varchar(50), _owner varchar(50)) 
returns integer
as 	
$BODY$
declare
	_rows integer;
	_result integer := -1;
	_sql text;
begin
	raise log '[>>]<IN> custom_import_transactions2finalize';

	begin
		_sql := 'drop table ' || _table;
		raise debug '[>>] going to execute sql -> % <-', _sql;
		execute _sql;
		GET DIAGNOSTICS _rows = ROW_COUNT;
		raise debug '[>>] sql execution affected -> % <- rows', _rows;
	exception
		when others then
		raise debug '[>>] table -> % <- does not exist.', _table;
	end;

	_sql := 'create table ' || _table || '(termnmbr int2, transnmbr int4, bdate int4,medcurrnmbr int2)';
	raise debug '[>>]going to execute sql -> % <-', _sql;
	execute _sql;
	GET DIAGNOSTICS _rows = ROW_COUNT;
	raise debug '[>>]sql execution row count was -> % <-', _rows;
	
	_sql := 'alter table ' || _table || ' owner to ' || _owner;
	raise debug '[>>]going to execute sql -> % <-', _sql;
	execute _sql;
	GET DIAGNOSTICS _rows = ROW_COUNT;
	raise debug '[>>]sql execution row count was -> % <-', _rows;

	_sql := 'copy ' || _table || ' from ''' || _file || ''' delimiter '';''' ;
	raise debug '[>>]going to execute sql -> % <-', _sql;
	execute _sql;
	GET DIAGNOSTICS _rows = ROW_COUNT;
	raise debug '[>>]sql execution row count was -> % <-', _rows;

_result := _rows;

raise log '[>>]<OUT> custom_import_transactions2finalize(%)', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_import_transactions2finalize( _file varchar(100), _table varchar(50), _owner varchar(50))  is 
'
Imports the csv file with the list of transactions to finalize into a table.
Be noted that this function is expecting a csv file with 4 columns in the order 
<termnmbr>;<transnmbr>;<bdate>;<medcurrnmbr>
.
arguments:	
	_file			varchar(100)		csv file with data to import
	_table			varchar(50)		table to import data into
	_owner			varchar(50)		user to own the table
returns  : 0 for sucess
';
ALTER FUNCTION custom_import_transactions2finalize( _file varchar(100), _table varchar(50), _owner varchar(50)) OWNER TO tplinux;

