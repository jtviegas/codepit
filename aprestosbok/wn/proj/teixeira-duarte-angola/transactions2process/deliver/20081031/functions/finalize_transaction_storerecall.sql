CREATE OR REPLACE FUNCTION custom_finalize_transaction_storerecall(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, _final_pos smallint, 
	_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer, _rectype smallint) 
returns integer
as 	
$BODY$
declare
	_rowcount integer;
	_id integer;
	_result integer := -1;
	_sql text;
	_transaction_description text;
begin
	raise log '[>>] <IN> custom_finalize_transaction_storerecall';

	_transaction_description := 'bdate=' || _original_bdate || '/termnmbr=' || _original_pos || 
			'/transnmbr=' || _original_transaction;

	_sql := 'insert into ejstorerecall' || _final_bdate || ' select * from ejstorerecall' || _original_bdate 
		|| ' where termnmbr=' || _original_pos 
		|| ' and transnmbr=' || _original_transaction;
	raise debug '[>>] going to execute sql -> % <-', _sql;
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	GET DIAGNOSTICS _id = RESULT_OID;
	raise debug '[>>] execution of sql returned row count -> % <-', _rowcount;
	raise debug '[>>] sql was an insert and returned an oid of -> % <-', _id;
	if _rowcount != 1 then
		if _rowcount > 1 then 
			raise exception 'transaction with more than one store recall --> ' , _transaction_description;
		end if;
		if _rowcount < 1 then 
			raise exception 'transaction with no store recall --> ' , _transaction_description;
		end if;
		RETURN _result;
	end if;

	_sql := 'update ejstorerecall' || _final_bdate || ' set bdate=' || _final_bdate || ',termnmbr=' || _final_pos ||
		',transnmbr=' || _final_transaction || ',linenmbr=' || _line_number || ',saletype=' 
		|| _saletype || ',seq=' || _sequence || ',rectype=' || _rectype
		|| ' where oid=' || _id;
	raise debug '[>>] going to execute sql -> % <-', _sql;
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	raise debug '[>>] execution of sql returned row count -> % <-', _rowcount;

	if _rowcount = 1 then
		_result := 0;
	end if;
	raise log '[>>] <OUT> custom_finalize_transaction_storerecall(%)', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transaction_storerecall(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, _final_pos smallint, 
	_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer, _rectype smallint)  is 
'
Helper function that finalizes the storerecall record for a stored transaction.
It queries the database for the original storerecall record and if it finds it inserts a record
in the table ejstorerecall<BDATE>(where BDATE is the second argument) based on the ejstorerecall retrieved
and on the data supplied in the arguments.
arguments:	
	_original_bdate int4		business date of the original transaction record.
	_original_pos int2		terminal number of the original transaction record.
	_original_transaction int4	transaction number of the original transaction record.
	_final_bdate int4		the business date on which the records must be now
					finalized.
	_termnmbr smallint		terminal number for which we want to generate the finalized transaction.
	_transnmbr integer		generated transaction number to set on the transaction to be finalized.
	_seq integer			generated sequence for the ej record.
	_saletype smallint		saletype to set on the finalized transaction.
	_linenmbr integer		generated line number for the ej record.
	_rectype  smallint		the new rectype stating that this record is recalled;
';
ALTER FUNCTION custom_finalize_transaction_storerecall(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, _final_pos smallint, 
	_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer, _rectype smallint)
 OWNER TO tplinux;
