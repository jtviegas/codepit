CREATE OR REPLACE FUNCTION custom_finalize_transactions_justheaders(_imported_data_table varchar(50), 
		_transaction_first_number integer) 
returns integer
as 	
$BODY$	
declare
	_owner varchar(20) := 'tplinux';

	_businessdate int4;
	_pos int2;

	_transaction_number integer;

	_sequence integer;
	_saletype smallint; 
	_linenumber integer;
	_rectype smallint;
	_media smallint;
	_old_termnmbr int2;
	_old_transnmbr int4;
	_old_bdate int4;

	_ok integer;
	_result integer := -1;
	_sql text;
	_sql2 text;
	_r record;
	_r2 record;
	_transaction_description varchar(100);

begin

raise log '[>>] <IN> custom_finalize_transactions_justheaders';
_transaction_number := _transaction_first_number;

/*  set pos number */	
_pos := 1;
_sql := 'select max(key) from terminal';
execute _sql into _ok;
if _ok > 0 then
	_pos := _ok + 1;
end if;
_ok := 0; /* reset variable */
raise debug '[>>] pos number is [%]', _pos;

/* set business date */
_sql := 'select bdate from businessdate where datetime_eod is null';
execute _sql into _businessdate;
raise debug '[>>] business date is [%]', _businessdate;


_sql := 'select bdate,termnmbr,transnmbr,medcurrnmbr from ' || _imported_data_table || ' order by bdate,termnmbr,transnmbr';  
raise debug '[>>] going to enter transactions loop...with sentence: [%]', _sql;

for _r IN EXECUTE _sql LOOP

	_old_transnmbr := _r.transnmbr;
	_old_termnmbr := _r.termnmbr;
	_old_bdate := _r.bdate;
	_transaction_description := 'bdate=' || _old_bdate || '/termnmbr=' || _old_termnmbr || '/transnmbr=' || _old_transnmbr;
	raise debug '[>>] going to process transaction [%]', _transaction_description;

	_media := _r.medcurrnmbr;
	raise debug '[>>] media will be [%]', _media;

	_sequence := 1;
	_saletype := 5; 
	_linenumber := 0;

	/*  ----------   header section   ----------  */

	raise debug '[>>] going to finalize the header for transaction [%]', _transaction_description;
	
	_sql2 := 'select custom_finalize_transaction_header(' ||
		_old_bdate || '::int4,' || _old_termnmbr || '::int2,' || _old_transnmbr || '::int4,'  
		|| _businessdate || '::int4, ' || _pos || '::smallint, ' || _transaction_number || '::integer, ' 
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer )';

	raise debug '[>>] going to execute sql [%]', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned [%]', _ok;
	if _ok != 0 then
		raise debug '[>>] failed on finalizing headers of transaction [%]', _transaction_description;
		exit;
	end if;
	
	raise debug '[>>] finalized the header for transaction [%]', _transaction_description;

	raise debug '[>>] going to set the new number for transaction [%]', _transaction_description;

	_sql2 := 'update ' || _imported_data_table || ' set newtransnmbr=' || _transaction_number || 
	' where bdate=' || _old_bdate || ' and termnmbr=' || _old_termnmbr || ' and transnmbr=' || _old_transnmbr;
	raise debug '[>>] going to execute sql [%]', _sql2;
	execute _sql2;
	GET DIAGNOSTICS _ok = ROW_COUNT;
	raise debug '[>>] sql execution affected [%] rows', _ok;
	
	raise debug '[>>] done with setting the new number for transaction [%]', _transaction_description;

	raise debug '[>>] finished processing transaction [%]', _transaction_description;
	_transaction_number := _transaction_number + 1;

end loop;

raise debug '[>>] ...left transactions loop';

_result := 0;
raise log '[>>] <OUT> custom_import_transactions2finalize [%]', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transactions_justheaders(_imported_data_table varchar(50), _transaction_first_number integer)   is 
'
Helper function for finalizing just the headers part of the set of transactions to finalize.
arguments:	
	_imported_data_table		varchar(50)	table holding records to finalize.
	_transaction_first_number	integer		new transaction number to perform to 
							asssign to  the finalized rercords.
returns  : 0 for sucess
';
ALTER FUNCTION custom_finalize_transactions_justheaders(varchar(50), integer)   OWNER TO tplinux;
