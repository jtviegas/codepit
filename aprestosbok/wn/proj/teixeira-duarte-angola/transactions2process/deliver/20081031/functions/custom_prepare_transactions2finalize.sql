CREATE OR REPLACE FUNCTION custom_prepare_transactions2finalize(_transactions2finalize_table varchar(50),
_transactions_being_processed_table varchar(50), _owner varchar(50), _number_of_records integer) 
returns integer
as 	
$BODY$	
declare

	_result integer := -1;
	_sql text;
	_ok integer;

begin
	raise log '[>>] <IN> custom_prepare_transactions2finalize';
	raise debug '[>>] going to setup temporary table to process part of the transactions to be finalized...';

	begin
		_sql := 'drop table ' || _transactions_being_processed_table;
		raise debug '[>>] going to execute sql -> % <-', _sql;
		execute _sql;
		GET DIAGNOSTICS _ok = ROW_COUNT;
		raise debug '[>>] sql execution affected -> % <- rows', _ok;
	exception
		when others then
		raise debug '[>>] table -> % <- does not exist.', _transactions_being_processed_table;
	end;

_ok := -1; /* reset var */

_sql := 'create table ' || _transactions_being_processed_table || ' as select * from ' || _transactions2finalize_table  
		|| ' order by bdate,termnmbr,transnmbr limit ' || _number_of_records;
raise debug '[>>] going to execute sql -> % <-', _sql;
execute _sql;
GET DIAGNOSTICS _ok = ROW_COUNT;
raise debug '[>>] sql execution affected -> % <- rows', _ok;

if _ok = 1 then
	_result := 0;
end if;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';
COMMENT ON FUNCTION custom_prepare_transactions2finalize(_transactions2finalize_table varchar(50),
_transactions_being_processed_table varchar(50), _owner varchar(50), _number_of_records integer)   is 
'
Helper function that sets up a table with a subset of data to be processed, drawed from the table that
 is holding the complete set of data to be processed.
arguments:	
	_transactions2finalize_table 		varchar(50)	the table where we can find all the data to be processed.
	_transactions_being_processed_table 	varchar(50)	the name of the table to hold the subset of dtata to be 
								processed.
	_owner 					varchar(50)	tthe owner of the table.
	_number_of_records 			integer		the number of records to be drawn from the table.
					finalized.
';
ALTER FUNCTION custom_prepare_transactions2finalize(_transactions2finalize_table varchar(50),
_transactions_being_processed_table varchar(50), _owner varchar(50), _number_of_records integer) 
 OWNER TO tplinux;