CREATE OR REPLACE FUNCTION custom_finalize_transactions_othersThanHeaders(_temp_finalize_table varchar(50), 
		_stored_table varchar(50), _transaction_first_number integer) 
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
	_r2 record;
	_transaction_description varchar(100);

begin
raise log '[>>] <IN> custom_finalize_transactions_othersThanHeaders';

_transaction_number := _transaction_first_number;


/*  set pos number */	
_pos := 1;
_sql := 'select max(key) from terminal';
execute _sql into _ok;
if _ok > 0 then
	_pos := _ok + 1;
end if;
_ok := 0; /* reset variable */
raise debug '[>>] pos number is %', _pos;

/* set business date */
_sql := 'select bdate from businessdate where datetime_eod is null';
execute _sql into _businessdate;
raise debug '[>>] business date is %', _businessdate;

raise debug '[>>] going to enter transactions loop...';
_sql := 'select bdate,termnmbr,transnmbr,medcurrnmbr from ' || _temp_finalize_table ;  

for _r IN EXECUTE _sql LOOP


	_transaction_description := 'bdate=' || _r.bdate || '/termnmbr=' || _r.termnmbr || '/transnmbr=' || _r.transnmbr;
	raise debug '[>>] going to process transaction %', _transaction_description;
	_media := _r.medcurrnmbr;
	raise debug '[>>] media will be %', _media;

	/* get stored transaction info */
	_sql2 := 'select bdates,termnmbrs,transnmbrs from ' || _stored_table 
		|| ' where termnmbrs=' ||  _r.termnmbr || ' and transnmbrs=' || _r.transnmbr
		|| ' and bdates=' || _r.bdate ;
	raise debug '[>>] going to execute sql -> % <-', _sql2;
	execute _sql2 into _r2;
	GET DIAGNOSTICS _ok = ROW_COUNT;
	raise debug '[>>] execution of sql returned row count -> % <-', _ok;
	if _ok != 1 then
		if _ok > 1 then 
			raise warning '[>>] transaction with more than one instance stored --> ' , _transaction_description;
		end if;
		if _ok < 1 then 
			raise warning '[>>] transaction with no instance stored --> ' , _transaction_description;
		end if;
		raise warning '[>>] going to continue to next transaction';
		/* if no single row then we must fall to the next row */
		_transaction_number := _transaction_number + 1; 
		continue;
	end if;

	_ok := 0; /* reset variable */
	

	_sequence := 2;
	_saletype := 5; 
	_linenumber := 1;

	
	/*  ----------   items section   ----------  */
	
	raise debug '[>>] going to finalize items for transaction %', _transaction_description;
	
	_sql2 := 'select custom_finalize_transaction_items(' ||
		_r2.bdates || '::int4,' || _r2.termnmbrs || '::int2,' || _r2.transnmbrs || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer )';

	raise debug '[>>] going to execute sql -> % <-', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned -> % <-', _ok;
	if _ok < 1 then
		raise exception '[>>] !!!!!!!!!! failed on finalizing items - transaction with no items -> %', _transaction_description;
		exit;
	end if;

	raise debug '[>>] finalized items for transaction %', _transaction_number;

	/* do not forget to add the number of processed items to the sequence and linenumber */
	_sequence := _sequence + _ok;
	_linenumber := _linenumber + _ok;	

	/*  ----------   store recall section   ----------  */

	raise debug '[>>] going to finalize the store recall record for transaction %', _transaction_description;
	
	_rectype := 52;

	_sql2 := 'select custom_finalize_transaction_storerecall(' ||
		_r2.bdates || '::int4,' || _r2.termnmbrs || '::int2,' || _r2.transnmbrs || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer , ' || _rectype || '::smallint)';

	raise debug '[>>] going to execute sql -> % <-', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned -> % <-', _ok;
	if _ok != 0 then
		raise exception '[>>] failed on finalizing store recall record of transaction -> %', _transaction_description;
		exit;
	end if;
	
	raise debug '[>>] finalized the store recall record for transaction %', _transaction_number;

	/*  ----------   trailer section   ----------  */

	raise debug '[>>] going to finalize the trailer for transaction %', _transaction_description;
	
	_sequence := _sequence + 2;
	_linenumber := _linenumber + 2;

	_sql2 := 'select custom_finalize_transaction_trailer(' ||
		_r2.bdates || '::int4,' || _r2.termnmbrs || '::int2,' || _r2.transnmbrs || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer)';

	raise debug '[>>] going to execute sql -> % <-', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned -> % <-', _ok;
	if _ok != 0 then
		raise exception '[>>] failed on finalizing trailer of transaction -> %', _transaction_description;
		exit;
	end if;
	
	raise debug '[>>] finalized the trailer for transaction %', _transaction_number;

	/*  ----------   media section   ----------  */

	raise debug '[>>] going to finalize the media record for transaction %', _transaction_description;
	
	_sequence := _sequence - 1;
	_linenumber := _linenumber - 1;

	_sql2 := 'select custom_finalize_transaction_media(' ||
		_r2.bdates || '::int4,' || _r2.termnmbrs || '::int2,' || _r2.transnmbrs || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer, ' || _media || '::smallint)';

	raise debug '[>>] going to execute sql -> % <-', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned -> % <-', _ok;
	if _ok != 0 then
		raise exception '[>>] failed on finalizing media record of transaction -> %', _transaction_description;
		exit;
	end if;
	
	raise debug '[>>] finalized the media record for transaction %', _transaction_number;

	/*  ----------   subtotal section   ----------  */

	raise debug '[>>] going to finalize the subtotal for transaction %', _transaction_description;
	
	_sequence := _sequence + 2;
	_linenumber := _linenumber + 2;

	_sql2 := 'select custom_finalize_transaction_subtotal(' ||
		_r2.bdates || '::int4,' || _r2.termnmbrs || '::int2,' || _r2.transnmbrs || '::int4,' 
		|| _businessdate || '::int4, ' || _pos|| '::smallint, ' || _transaction_number || '::integer, '
		|| _sequence || '::integer, ' || _saletype || '::smallint, ' || _linenumber || '::integer)';

	raise debug '[>>] going to execute sql -> % <-', _sql2;
	execute _sql2 into _ok;
	raise debug '[>>] execution of sql returned -> % <-', _ok;
	if _ok != 0 then
		raise exception '[>>] failed on finalizing subtotal of transaction -> %', _transaction_description;
		exit;
	end if;
	
	raise debug '[>>] finalized the subtotal for transaction %', _transaction_number;



	raise debug '[>>] finished processing transaction %', _transaction_description;
	_transaction_number := _transaction_number + 1;

end loop;

raise debug '[>>] ...left transactions loop';

_result := 0;
raise debug '[>>] <OUT> custom_finalize_transactions_othersThanHeaders(%)', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transactions_othersThanHeaders(_temp_finalize_table varchar(50), 
		_stored_table varchar(50), _transaction_first_number integer)   is 
'
Helper function for finalizing just the headers part of the set of transactions to finalize.
arguments:	
	_temp_finalize_table		varchar(50)	table holding records to finalize.
	_stored_table			varchar(50)	table holding the stored transactions.
	_transaction_first_number	integer		new transaction number to perform to 
							asssign to  the finalized records.
returns  : 0 for sucess
';
ALTER FUNCTION custom_finalize_transactions_justheaders(varchar(50), varchar(50), integer)   OWNER TO tplinux;