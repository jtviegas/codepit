CREATE OR REPLACE FUNCTION custom_finalize_transaction_items(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, 
		_final_pos smallint,_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer) 
returns integer
as 	
$BODY$
declare
	_local_sequence integer;
	_local_line_number integer;
	_rowcount integer;
	_result integer := 0;
	_id integer;
	_r record;
	_sql2 text;
	_sql text;
	_transaction_description text;
begin
	raise log '[>>] <IN> custom_finalize_transaction_items';
	
	_local_sequence := _sequence;
	_local_line_number := _line_number;
	
	_transaction_description := 'bdate=' || _original_bdate || '/termnmbr=' ||_original_pos || 
			'/transnmbr=' || _original_transaction;
	
	_sql := 'select linenmbr from ejitem' || _original_bdate || ' where termnmbr=' || _original_pos 
		|| ' and transnmbr=' || _original_transaction;
	
	for _r in execute _sql loop
		
		raise debug '[>>] going to finalize transaction item --> %', _local_line_number;

		_sql2 := 'insert into ejitem' || _final_bdate || ' select * from ejitem' || _original_bdate || ' where termnmbr=' || _original_pos 
		|| ' and transnmbr=' || _original_transaction || ' and linenmbr=' || _r.linenmbr;
		raise debug '[>>] going to execute sql -> % <-', _sql2;
		execute _sql2;
		GET DIAGNOSTICS _rowcount = ROW_COUNT;
		GET DIAGNOSTICS _id = RESULT_OID;
		raise debug '[>>] execution of sql returned row count -> % <-', _rowcount;
		raise debug '[>>] sql was an insert and returned an oid of -> % <-', _id;
		if _rowcount != 1 then
			if _rowcount > 1 then 
				raise exception 'transaction items with the same line number --> ' , _transaction_description;
			end if;
			if _rowcount < 1 then 
				raise exception 'could not find transaction by its line number --> ' , _transaction_description;
			end if;
			RETURN _result;
		end if;
		
		_sql2 := 'update ejitem' || _final_bdate || ' set bdate=' || _final_bdate || ',termnmbr=' || _final_pos ||
			',transnmbr=' || _final_transaction || ',linenmbr=' || _local_line_number || ',saletype=' || _saletype || ',seq=' || _local_sequence
			|| ' where oid=' || _id;
		raise debug '[>>] going to execute sql -> % <-', _sql2;
		execute _sql2;
		GET DIAGNOSTICS _rowcount = ROW_COUNT;
		raise debug '[>>] execution of sql returned row count -> % <-', _rowcount;

		if _rowcount != 1 then
			_result:=-1;
			raise debug '[>>] !!!!!!!!!! DID NOT finalized transaction item --> %', _local_line_number;
			RETURN _result;	
		else
			raise debug '[>>] finalized transaction item --> %', _local_line_number;
			_result := _result + 1;
		end if;
		
		_rowcount:=0;/* reset var */

		_local_sequence := _local_sequence + 1;
		_local_line_number := _local_line_number + 1;

	end loop;
	raise log '[>>] <OUT> custom_finalize_transaction_items(%)', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transaction_items(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, 
		_final_pos smallint,_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer) is 
'
Helper function that finalizes the items for a stored transaction.
It queries the database for the original ejitems records and if it finds it inserts a record
in the table ejitem<BDATE>(where BDATE is the second argument) based on the ejitem retrieved
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
';
ALTER FUNCTION custom_finalize_transaction_items(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, 
		_final_pos smallint,_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer) OWNER TO tplinux;

