

CREATE OR REPLACE FUNCTION custom_finalize_transaction_trailer(_original_bdate int4,_original_pos int2, 
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
	raise log '[>>] <IN> custom_finalize_transaction_trailer';

	_transaction_description := 'bdate=' || _original_bdate || '/termnmbr=' || _original_pos || 
			'/transnmbr=' || _original_transaction;

	_sql := 'insert into ejtrailer' || _final_bdate || ' select * from ejtrailer' || _original_bdate || ' where termnmbr=' || _original_pos 
		|| ' and transnmbr=' || _original_transaction;
	raise debug '[>>] going to execute sql [%]', _sql;
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	GET DIAGNOSTICS _id = RESULT_OID;
	raise debug '[>>] execution of sql returned row count [%]', _rowcount;
	raise debug '[>>] sql was an insert and returned an oid of [%]', _id;
	if _rowcount != 1 then
		
		if _rowcount > 1 then 
			raise debug '[>>] !!! transaction with more than one trailer [%] !!!' , _transaction_description;
		end if;
		if _rowcount < 1 then 
			raise debug '[>>] !!! transaction with no trailer [%] !!!' , _transaction_description;
		end if;
		
		RETURN _result;
	end if;

	_sql := 'update ejtrailer' || _final_bdate || ' set bdate=' || _final_bdate || ',termnmbr=' || _final_pos ||
		',transnmbr=' || _final_transaction || ',linenmbr=' || _line_number || ',saletype=' || _saletype || ',seq=' || _sequence
		|| ' where oid=' || _id;
	raise debug '[>>] going to execute sql [%]', _sql; 
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	raise debug '[>>] execution of sql returned row count [%]', _rowcount;

	if _rowcount = 1 then
		_result := 0;
	end if;
	raise log '[>>] <OUT> custom_finalize_transaction_header[%]', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transaction_trailer(_original_bdate int4,_original_pos int2, _original_transaction int4, 
	_final_bdate int4, _final_pos smallint, _final_transaction integer, _sequence integer, _saletype smallint, _line_number integer)  is 
'
Helper function that finalizes the trailer for a stored transaction.
It queries the database for the original ejtrailer record and if it finds it inserts a record
in the table ejtrailer<BDATE>(where BDATE is the second argument) based on the ejtrailer retrieved
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
ALTER FUNCTION custom_finalize_transaction_trailer(_original_bdate int4,_original_pos int2, _original_transaction int4, 
	_final_bdate int4, _final_pos smallint, _final_transaction integer, _sequence integer, _saletype smallint, _line_number integer)
 OWNER TO tplinux;


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
	raise debug '[>>] going to execute sql [%]', _sql;
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	GET DIAGNOSTICS _id = RESULT_OID;
	raise debug '[>>] execution of sql returned row count [%]', _rowcount;
	raise debug '[>>] sql was an insert and returned an oid of [%]', _id;
	if _rowcount < 1 then
		raise debug '[>>] !!! transaction with no store recall [%] !!!' , _transaction_description;
		RETURN _result;
	end if;

	_sql := 'update ejstorerecall' || _final_bdate || ' set bdate=' || _final_bdate || ',termnmbr=' || _final_pos ||
		',transnmbr=' || _final_transaction || ',linenmbr=' || _line_number || ',saletype=' 
		|| _saletype || ',seq=' || _sequence || ',rectype=' || _rectype
		|| ' where oid=' || _id;
	raise debug '[>>] going to execute sql [%]', _sql;
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	raise debug '[>>] execution of sql returned row count [%]', _rowcount;

	if _rowcount = 1 then
		_result := 0;
	end if;
	raise log '[>>] <OUT> custom_finalize_transaction_storerecall[%]', _result;

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


CREATE OR REPLACE FUNCTION custom_finalize_transaction_media(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, _final_pos smallint, 
	_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer, _media smallint) 
returns integer
as 	
$BODY$
declare
	_rowcount integer;
	_id integer;
	_result integer := -1;
	_sql text;
	_transaction_description text;
	
	_rectype smallint := 40;
	_rsvdhdr smallint := 0;
	_media_desc varchar(21);
	_media_number smallint;	
	_media_grp_used smallint;
	MEDIA_TYPE_CREDITO smallint := 8;
	MEDIA_TYPE_NUMERARIO smallint := 1;
	MEDIA_NUMBER_CREDITO smallint := 222;
	MEDIA_NUMBER_NUMERARIO smallint := 1;
	MEDIA_GROUP_CREDITO smallint := 7;
	MEDIA_GROUP_NUMERARIO smallint := 1;
	MEDIA_DESC_CREDITO varchar(21) := 'CREDITO';
	MEDIA_DESC_NUMERARIO varchar(21) := 'NUMERARIO';
begin
	raise log '[>>] <IN> custom_finalize_transaction_media';

	_transaction_description := 'bdate=' || _original_bdate || '/termnmbr=' || _original_pos || 
			'/transnmbr=' || _original_transaction;
		
	raise log '[>>] going to insert a media for the transaction [%]', _transaction_description;

	if _media = MEDIA_TYPE_NUMERARIO then
		_media_number := MEDIA_NUMBER_NUMERARIO;
		_media_desc := MEDIA_DESC_NUMERARIO;
		_media_grp_used := MEDIA_GROUP_NUMERARIO;
	else
		_media_number := MEDIA_NUMBER_CREDITO;
		_media_desc := MEDIA_DESC_CREDITO;
		_media_grp_used := MEDIA_GROUP_NUMERARIO;
	end if;


	_sql := 'insert into ejmedia' || _final_bdate || '('
		|| 'termnmbr,transnmbr,linenmbr,rectype,saletype,rsvdhdr,'
		|| 'bdate,seq,'
		|| 'mngrovnmbr,funcsetovr,mediakeybits,mdesc,'
		|| 'mediatype,changefrom,accountnmbr,accountstat,accountaprovl,'
		|| 'rejmngrnmbr,rejfuncsetovr,creditflags,entrytype,medianmbr,' 
		|| 'expdate,storemedia,medgrpused,decpos,drateexchg,'
		|| 'medcurrflag,discountgrpident,flag,meddisclinenmbr,dcf,'
		|| 'posgrpnmbr,functype,date,time,cshrnmbr,'
		|| 'datetime,altcurrtend,mediaamnt'
		|| ') values('
		|| _final_pos || ',' || _final_transaction || ',' || _line_number || ',' || _rectype|| ',' 
		|| _saletype || ',' || _rsvdhdr || ','
		||   _final_bdate || ',' 
		|| _sequence || ',' || '0,0,''00000000'',''' || _media_desc || ''','
		|| _media || ',1,0,'''','''',0,0,''00000000'',2,' || _media_number || ','
		|| '0,49,' || _media_grp_used || ',2,1,1,' || '''0'',''00000000'',0' 
		|| ',' || '(select dcf from ejheader' || 
			_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
			|| ' and transnmbr=' || _final_transaction || ')'
		|| ',' || '(select posgrpnmbr from ejheader' || 
			_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
			|| ' and transnmbr=' || _final_transaction || ')'
		|| ',' || '(select functype from ejheader' || 
			_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
			|| ' and transnmbr=' || _final_transaction || ')'
		|| ',' || '(select date from ejheader' || 
			_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
			|| ' and transnmbr=' || _final_transaction || ')'
		|| ',' || '(select time from ejheader' || 
			_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
			|| ' and transnmbr=' || _final_transaction || ')'
		|| ',' || '(select cshrnmbr from ejheader' || 
			_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
			|| ' and transnmbr=' || _final_transaction || ')'
		|| ',' || '(select datetime from ejheader' || 
			_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
			|| ' and transnmbr=' || _final_transaction || ')'
		|| ',' || '(select netsale from ejtrailer' || 
			_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
			|| ' and transnmbr=' || _final_transaction || ')'
		|| ',' || '(select netsale from ejtrailer' || 
			_final_bdate || ' where bdate=' || _final_bdate || ' and termnmbr=' || _final_pos 
			|| ' and transnmbr=' || _final_transaction || ')'

		|| ')';

	raise debug '[>>] going to execute sql [%]', _sql;
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	raise debug '[>>] sql execution row count was [%]', _rowcount;
	raise log '[>>] inserted a media for the transaction [%]', _transaction_description;
	if _rowcount = 1 then
		_result := 0;
	end if;

	raise log '[>>] <OUT> custom_finalize_transaction_media[%]', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transaction_media(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, _final_pos smallint, 
	_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer, _media smallint)   is 
'
Helper function that finalizes the media record for a stored transaction.
It inserts a record in the table ejmedia<BDATE>(where BDATE is the second argument) 
based on the data supplied in the arguments.
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
	_media smallint			media used for payment, money(1) or credit(8).
';
ALTER FUNCTION custom_finalize_transaction_media(_original_bdate int4,_original_pos int2, 
	_original_transaction int4, _final_bdate int4, _final_pos smallint, 
	_final_transaction integer, _sequence integer, _saletype smallint, _line_number integer, _media smallint) 
 OWNER TO tplinux;



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
		
		raise debug '[>>] going to finalize transaction item [%]', _local_line_number;

		_sql2 := 'insert into ejitem' || _final_bdate || ' select * from ejitem' || _original_bdate || ' where termnmbr=' || _original_pos 
		|| ' and transnmbr=' || _original_transaction || ' and linenmbr=' || _r.linenmbr;
		raise debug '[>>] going to execute sql [%]', _sql2;
		execute _sql2;
		GET DIAGNOSTICS _rowcount = ROW_COUNT;
		GET DIAGNOSTICS _id = RESULT_OID;
		raise debug '[>>] execution of sql returned row count [%]', _rowcount;
		raise debug '[>>] sql was an insert and returned an oid of [%]', _id;
		if _rowcount != 1 then
			if _rowcount > 1 then 
				raise debug '[>>] !!! transaction items with the same line number [%] !!!' , _transaction_description;
			end if;
			if _rowcount < 1 then 
				raise debug '[>>] !!! could not find transaction by its line number [%] !!!' , _transaction_description;
			end if;
			RETURN _result;
		end if;
		
		_sql2 := 'update ejitem' || _final_bdate || ' set bdate=' || _final_bdate || ',termnmbr=' || _final_pos ||
			',transnmbr=' || _final_transaction || ',linenmbr=' || _local_line_number || ',saletype=' || _saletype || ',seq=' || _local_sequence
			|| ' where oid=' || _id;
		raise debug '[>>] going to execute sql [%]', _sql2;
		execute _sql2;
		GET DIAGNOSTICS _rowcount = ROW_COUNT;
		raise debug '[>>] execution of sql returned row count [%]', _rowcount;

		if _rowcount != 1 then
			_result:=-1;
			raise debug '[>>] !!! DID NOT finalized transaction item [%] !!!', _local_line_number;
			RETURN _result;	
		else
			raise debug '[>>] finalized transaction item [%]', _local_line_number;
			_result := _result + 1;
		end if;
		
		_rowcount:=0;/* reset var */

		_local_sequence := _local_sequence + 1;
		_local_line_number := _local_line_number + 1;

	end loop;
	raise log '[>>] <OUT> custom_finalize_transaction_items[%]', _result;

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



CREATE OR REPLACE FUNCTION custom_finalize_transaction_header(_original_bdate int4,_original_pos int2, 
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
	raise log '[>>] <IN> custom_finalize_transaction_header';

	_transaction_description := 'bdate=' || _original_bdate || '/termnmbr=' || _original_pos || 
			'/transnmbr=' || _original_transaction;

	_sql := 'insert into ejheader' || _final_bdate || ' select * from ejheader' || _original_bdate || ' where termnmbr=' || _original_pos 
		|| ' and transnmbr=' || _original_transaction;
	raise debug '[>>] going to execute sql [%]', _sql;
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	GET DIAGNOSTICS _id = RESULT_OID;
	raise debug '[>>] execution of sql returned row count [%]', _rowcount;
	raise debug '[>>] sql was an insert and returned an oid of [%]', _id;
	if _rowcount != 1 then
		if _rowcount > 1 then 
			raise debug '[>>] !!! transaction with more than one header [%] !!!' , _transaction_description;
		end if;
		if _rowcount < 1 then 
			raise debug '[>>] !!! transaction with no header [%] !!!' , _transaction_description;
		end if;
		RETURN _result;
	end if;

	_sql := 'update ejheader' || _final_bdate || ' set bdate=' || _final_bdate || ',termnmbr=' || _final_pos ||
		',transnmbr=' || _final_transaction || ',linenmbr=' || _line_number || ',saletype=' || _saletype || ',seq=' || _sequence
		|| ' where oid=' || _id;
	raise debug '[>>] going to execute sql [%]', _sql;
	execute _sql;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	raise debug '[>>] execution of sql returned row count [%]', _rowcount;

	if _rowcount = 1 then
		_result := 0;
	end if;
	raise log '[>>] <OUT> custom_finalize_transaction_header[%]', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transaction_header(_original_bdate int4,_original_pos int2, _original_transaction int4, 
	_final_bdate int4, _final_pos smallint, _final_transaction integer, _sequence integer, _saletype smallint, _line_number integer)  is 
'
Helper function that finalizes the header for a stored transaction.
It queries the database for the original ejheader record and if it finds it inserts a record
in the table ejheader<BDATE>(where BDATE is the second argument) based on the ejheader retrieved
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
ALTER FUNCTION custom_finalize_transaction_header(_original_bdate int4,_original_pos int2, _original_transaction int4, 
	_final_bdate int4, _final_pos smallint, _final_transaction integer, _sequence integer, _saletype smallint, _line_number integer)
 OWNER TO tplinux;

/*..................................................................................................
....................................................................................................
......................................................................................................
.................................................................................................
....................................................................................................
......................................................................................................*/

CREATE OR REPLACE FUNCTION custom_import_transactions2finalize( _cvs_data_file varchar(100), _imported_data_table varchar(50), 
	_stored_data_table varchar(50)) 
returns integer
as 	
$BODY$
declare

	_rows integer;
	_result integer := -1;
	_owner varchar(20) := 'tplinux';
	_sql text;
	_dummy_table varchar(50) := 'transactions2finalize_dummy_table';

begin
	raise log '[>>] <IN> custom_import_transactions2finalize';

	raise debug '[>>] removing dummy table if it exists...';
		begin
			_sql := 'drop table ' || _dummy_table;
			raise debug '[>>] going to execute sql [%]', _sql;
			execute _sql;
			GET DIAGNOSTICS _rows = ROW_COUNT;
			raise debug '[>>] sql execution affected [%] rows', _rows;
		exception
			when others then
			raise debug '[>>] table [%] does not exist.We will create it.', _dummy_table;
		end;
	raise debug '[>>] removing dummy table if it exists...OK!';

	raise debug '[>>] creating dummy table...';

		_sql := 'create temporary table ' || _dummy_table || 
			'(termnmbr int2, transnmbr int4, bdate int4,medcurrnmbr int2)';
		raise debug '[>>] going to execute sql [%]', _sql;
		execute _sql;
		GET DIAGNOSTICS _rows = ROW_COUNT;
		raise debug '[>>] sql execution row count was [%]', _rows;
	
		_sql := 'alter table ' || _dummy_table || ' owner to ' || _owner;
		raise debug '[>>] going to execute sql [%]', _sql;
		execute _sql;
		GET DIAGNOSTICS _rows = ROW_COUNT;
		raise debug '[>>] sql execution row count was [%]', _rows;

	raise debug '[>>] creating dummy table...OK!';

	raise debug '[>>] copying data from csv into dummy table...';

		_sql := 'copy ' || _dummy_table || ' from ''' || _cvs_data_file || ''' delimiter '';''' ;
		raise debug '[>>] going to execute sql [%]', _sql;
		execute _sql;
		GET DIAGNOSTICS _rows = ROW_COUNT;
		raise debug '[>>] sql execution row count was [%]', _rows;
	
	raise debug '[>>] copying data from csv into dummy table...OK!';

	raise debug '[>>] removing imported data table if it exists...';
		begin
			_sql := 'drop table ' || _imported_data_table;
			raise debug '[>>] going to execute sql [%]', _sql;
			execute _sql;
			GET DIAGNOSTICS _rows = ROW_COUNT;
			raise debug '[>>] sql execution affected [%] rows', _rows;
		exception
			when others then
			raise debug '[>>] table [%] does not exist.We will create it.', _imported_data_table;
		end;
	raise debug '[>>] removing imported data table if it exists...OK!';

	raise debug '[>>] getting stored data based on the csv file into table  %...', _imported_data_table;

		_sql := 'create table ' || _imported_data_table || ' as ' ||
		'select t.*, 0::int4 as newtransnmbr  from (select termnmbrs, transnmbrs, bdates ' ||
		'from list_stored_notrecalled)s inner join ' ||
		'(select distinct termnmbr, transnmbr, bdate,medcurrnmbr ' ||
		'from transactions2finalize_dummy_table)t ' ||
		'on t.termnmbr=s.termnmbrs and t.transnmbr=s.transnmbrs and t.bdate=s.bdates ' ||
		' order by t.bdate,t.termnmbr,t.transnmbr';
		raise debug '[>>] going to execute sql [%]', _sql;
		execute _sql;
		GET DIAGNOSTICS _rows = ROW_COUNT;
		raise debug '[>>] sql execution row count was [%]', _rows;

	raise debug '[>>] getting stored data based on the csv file into table  %...OK!', _imported_data_table;

	if _rows = 1 then
		_result := 0;
	end if;

	_result := 0;
	raise log '[>>] <OUT> custom_import_transactions2finalize [%]', _result;

	RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_import_transactions2finalize( _cvs_data_file varchar(100), _imported_data_table varchar(50), 
	_stored_data_table varchar(50))  is 
'
Imports the csv file with the list of transactions to finalize into a table.
Be noted that this function is expecting a csv file with 4 columns in the order 
<termnmbr>;<transnmbr>;<bdate>;<medcurrnmbr>
.
arguments:	
	_cvs_data_file		varchar(100)		csv file with data to import
	_imported_data_table	varchar(50)		table where to import data to be processed
	_stored_data_table	varchar(50)		table with data stored to be recalled
returns  : 0 for sucess
';
ALTER FUNCTION custom_import_transactions2finalize( _cvs_data_file varchar(100), _imported_data_table varchar(50), 
	_stored_data_table varchar(50)) OWNER TO tplinux;


/*..................................................................................................
....................................................................................................
......................................................................................................*/

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



/*..................................................................................................
....................................................................................................
......................................................................................................*/

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



