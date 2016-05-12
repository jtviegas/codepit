CREATE OR REPLACE FUNCTION custom_finalize_transaction_subtotal(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, _final_pos smallint, 
	_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer) 
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
	raise log '[>>] <IN> custom_finalize_transaction_subtotal';

	_transaction_description := 'bdate=' || _original_bdate || '/termnmbr=' || _original_pos || 
			'/transnmbr=' || _original_transaction;

	/*raise log '[>>] going to create the subtotal record for transaction -> % <-', _transaction_description;*/

	_sql := 'insert into ejsubtotal' || _final_bdate || '('  || 
			'termnmbr,transnmbr,linenmbr,rectype,dcf,' ||
			'posgrpnmbr,functype,saletype,rsvdhdr,datetime,' ||
			'date,time,bdate,cshrnmbr,seq,' ||
			'amnt,print1contxt,print1text,print2contxt,print2text' ||
			')values(' ||
			_final_pos || ',' || _final_transaction || ',' ||  _line_number || ',55,' || 
				'(select dcf from ejheader' || _final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos || ' and transnmbr=' || _final_transaction || '),' ||  
			'(select posgrpnmbr from ejheader' || 
				_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
				|| ' and transnmbr=' || _final_transaction || '),' || '(select functype from ejheader' || 
				_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
				|| ' and transnmbr=' || _final_transaction || '),' || _saletype || ',0,' || '(select datetime from ejheader' || 
				_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
				|| ' and transnmbr=' || _final_transaction || '),' ||
			'(select date from ejheader' || 
				_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
				|| ' and transnmbr=' || _final_transaction || '),'
				|| '(select time from ejheader' || 
				_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
				|| ' and transnmbr=' || _final_transaction || '),' || _final_bdate || ',(select cshrnmbr from ejheader' || 
				_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
				|| ' and transnmbr=' || _final_transaction || '),' || _sequence || ',' ||
			'(select netsale from ejtrailer' || 
				_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
				|| ' and transnmbr=' || _final_transaction || '),7,''SUBTOTAL'',0,''''' ||
			')';

	raise debug '[>>] going to execute sql [%] ', _sql; 
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	raise debug '[>>] execution of sql returned row count [%]', _rowcount;

	if _rowcount = 1 then
		_result := 0;
		raise log '[>>] created the subtotal record for transaction [%]', _transaction_description;
	end if;
	raise log '[>>] <OUT> custom_finalize_transaction_subtotal[%]', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transaction_subtotal(_original_bdate int4,_original_pos int2, _original_transaction int4, 
	_final_bdate int4, _final_pos smallint, _final_transaction integer, _sequence integer, _saletype smallint, _line_number integer)  is 
'
Helper function that creates the subtotal record for a stored transaction.
It  inserts a record in the table ejsubtotal<BDATE>(where BDATE is the second argument) based on the data supplied in the arguments.
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
ALTER FUNCTION custom_finalize_transaction_subtotal(_original_bdate int4,_original_pos int2, _original_transaction int4, 
	_final_bdate int4, _final_pos smallint, _final_transaction integer, _sequence integer, _saletype smallint, _line_number integer)
 OWNER TO tplinux;
