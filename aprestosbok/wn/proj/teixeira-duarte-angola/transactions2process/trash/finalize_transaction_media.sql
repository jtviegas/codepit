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
