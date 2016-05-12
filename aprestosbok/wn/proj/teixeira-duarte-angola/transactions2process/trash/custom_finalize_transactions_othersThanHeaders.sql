CREATE OR REPLACE FUNCTION custom_finalize_transactions_othersThanHeaders(_imported_data_table varchar(50)) 
returns integer
as 	
$BODY$	
declare
	_owner varchar(20) := 'tplinux';

	_businessdate int4;
	_pos int2;

	_transaction_number int4;
	_sequence integer;
	_saletype smallint; 
	_linenumber integer;
	_rectype smallint;
	_media smallint;

	_ok integer;
	_result integer := -1;
	_sql text;
	_sql2 text;
	_r record;

	_transaction_description varchar(100);

begin
raise log '[>>] <IN> custom_finalize_transactions_othersThanHeaders';




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

_sql := 'select bdate,termnmbr,transnmbr,medcurrnmbr,newtransnmbr from ' || _imported_data_table || ' order by bdate,termnmbr,transnmbr' ;  
raise debug '[>>] going to enter transactions loop...with sentence: [%]', _sql;

for _r IN EXECUTE _sql LOOP

	_transaction_number := _r.newtransnmbr;
	_transaction_description := 'bdate=' || _r.bdate || '/termnmbr=' || _r.termnmbr || '/transnmbr=' || _r.transnmbr;

	raise debug '[>>] going to process transaction [%]', _transaction_description;

	_media := _r.medcurrnmbr;
	raise debug '[>>] media will be [%]', _media;

	_sequence := 2;
	_saletype := 5; 
	_linenumber := 1;
	
	/*  ----------   items section   ----------  */
	
	raise debug '[>>] going to finalize items for transaction [%]', _transaction_description;
	
	_sql2 := 'select custom_finalize_transaction_items(' ||
		_r.bdate || '::int4,' || _r.termnmbr || '::int2,' || _r.transnmbr || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer )';

	raise debug '[>>] going to execute sql [%]', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned [%]', _ok;
	if _ok < 1 then
		raise debug '[>>] !!!!!!!!!! failed on finalizing items - transaction with no items [%]', _transaction_description;
		exit;
	end if;

	raise debug '[>>] finalized items for transaction [%]', _transaction_description;

	/* do not forget to add the number of processed items to the sequence and linenumber */
	_sequence := _sequence + _ok;
	_linenumber := _linenumber + _ok;	

	/*  ----------   store recall section   ----------  */

	raise debug '[>>] going to finalize the store recall record for transaction [%]', _transaction_description;
	
	_rectype := 52;

	_sql2 := 'select custom_finalize_transaction_storerecall(' ||
		_r.bdate || '::int4,' || _r.termnmbr || '::int2,' || _r.transnmbr || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer , ' || _rectype || '::smallint)';

	raise debug '[>>] going to execute sql [%]', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned [%]', _ok;
	if _ok != 0 then
		raise debug '[>>] !!!!!!!!!!!!!!!!!!!!! failed on finalizing store recall record of transaction [%]', _transaction_description;
		exit;
	end if;
	
	raise debug '[>>] finalized the store recall record for transaction [%]', _transaction_number;

	/*  ----------   trailer section   ----------  */

	raise debug '[>>] going to finalize the trailer for transaction [%]', _transaction_description;
	
	_sequence := _sequence + 2;
	_linenumber := _linenumber + 2;

	_sql2 := 'select custom_finalize_transaction_trailer(' ||
		_r.bdate || '::int4,' || _r.termnmbr || '::int2,' || _r.transnmbr || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer)';

	raise debug '[>>] going to execute sql [%]', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned [%]', _ok;
	if _ok != 0 then
		raise debug '[>>] !!!!!!!!!!!!!!!!!!!!! failed on finalizing trailer of transaction [%]', _transaction_description;
		exit;
	end if;
	
	raise debug '[>>] finalized the trailer for transaction [%]', _transaction_description;

	/*  ----------   media section   ----------  */

	raise debug '[>>] going to finalize the media record for transaction[%]', _transaction_description;
	
	_sequence := _sequence - 1;
	_linenumber := _linenumber - 1;

	_sql2 := 'select custom_finalize_transaction_media(' ||
		_r.bdate || '::int4,' || _r.termnmbr || '::int2,' || _r.transnmbr || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer, ' || _media || '::smallint)';

		raise debug '[>>] going to execute sql [%]', _sql2; 
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned [%]', _ok;
	if _ok != 0 then
		raise debug '[>>] !!!!!!!!!!!!!!!!!!! failed on finalizing media record of transaction [%]', _transaction_description;
		exit;
	end if;
	
	raise debug '[>>] finalized the media record for transaction [%]', _transaction_description;

	/*  ----------   subtotal section   ----------  */

	raise debug '[>>] going to finalize the subtotal for transaction [%]', _transaction_description;
	
	_sequence := _sequence + 2;
	_linenumber := _linenumber + 2;

	_sql2 := 'select custom_finalize_transaction_subtotal(' ||
		_r.bdate || '::int4,' || _r.termnmbr || '::int2,' || _r.transnmbr || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer)';

	raise debug '[>>] going to execute sql [%]', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned [%]', _ok; 
	if _ok != 0 then
		raise debug '[>>] !!!!!!!!!!!!!!!!! failed on finalizing subtotal of transaction [%]', _transaction_description;
		exit;
	end if;
	
	raise debug '[>>] finalized the subtotal for transaction [%]', _transaction_description;

	raise debug '[>>] finished processing transaction [%]', _transaction_description;

end loop;

raise debug '[>>] ...left transactions loop';

_result := 0;
raise debug '[>>] <OUT> custom_finalize_transactions_othersThanHeaders[%]', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transactions_othersThanHeaders(_imported_data_table varchar(50))   is 
'
Helper function for finalizing just the headers part of the set of transactions to finalize.
arguments:	
	_imported_data_table		varchar(50)	table holding records to finalize.
returns  : 0 for sucess
';
ALTER FUNCTION custom_finalize_transactions_othersThanHeaders(varchar(50)) OWNER TO tplinux;
