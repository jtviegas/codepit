


---------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION custom_finalize_transaction(varchar(50),varchar(50)) 
returns void
as 	
$BODY$
declare
	_transactions2finalizeTable alias for $1;
	_storedTransactionsTable alias for $2;
	
	_row record;
	_result integer;

	_transaction_description text;
	_transaction custom_transaction2finalize;
	_sql_transactions2finalize text;
	_sql_storedTransaction text;
	_rowcount integer;
	_sql text;

	_bdate int4;
	_terminal int2;
	_transaction_number int4;
	_sequence integer;
	_saletype smallint; 
	_linenumber integer;
begin

raise log '<<IN>> custom_finalize_transaction';
/* get transactions to finalize */
_sql_transactions2finalize := 'select bdate,termnmbr,transnmbr,medcurrnmbr from ' || _transactions2finalizeTable ;

	/*  set terminal */	
	_terminal := 1;
	_sql := 'select max(key) from terminal';
	execute _sql into _rowcount;
	if _rowcount > 0 then
		_terminal := _rowcount + 1;
	end if;
	_rowcount=0; /* reset variable */

	/* set business date */
	_sql := 'select bdate from businessdate where datetime_eod is null';
	execute _sql into _bdate;
	raise debug 'business date is %', _bdate;


for _row IN EXECUTE _sql_transactions2finalize LOOP

	_transaction_description := 'bdate=' || _row.bdate || '/termnmbr=' || _row.termnmbr || 
			'/transnmbr=' || _row.transnmbr;

	raise debug 'going to process transaction %', _transaction_description;

	/* get stored transaction info */
	_sql_storedTransaction := 'select * from ' || _storedTransactionsTable 
		|| 'where termnmbrs=' ||  _row.termnmbr || ' and transnmbrs =' || _row.transnmbr
		|| ' and bdates=' || _row.bdate ;

	execute _sql_storedTransaction into _transaction;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	
	if _rowcount != 1 then
		if _rowcount > 1 then 
			raise warning 'transaction with more than one instance stored --> ' , _transaction_description;
		end if;
		if _rowcount < 1 then 
			raise warning 'transaction with no instance stored --> ' , _transaction_description;
		end if;
		/* if no single row then we must fall to the next row */
		continue;
	end if;

	_rowcount=0; /* reset variable */
	
	/* supply the desired media info to the transaction */
	_transaction.medcurrnmbr := _row.medcurrnmbr;

	raise debug 'media is %', _row.medcurrnmbr;

	_rowcount=0; /* reset variable */
	
	/* set other vars */
	_transaction_number := 1;
	_sequence := 1;
	_saletype := 5; 
	_linenumber := 0;

	raise debug 'going to finalize the header for transaction %', _transaction_number;
	_result := -1;
	select custom_finalize_transaction_header(_transaction,_bdate,_terminal,_transaction_number,_sequence,_saletype,_linenumber) into _result;
	if _result != 0 then
		raise exception 'failed on finalizing headers';
		exit;
	end if;
	raise debug 'finalized the header for transaction %', _transaction_number;

	_sequence := _sequence + 1;
	_linenumber := _linenumber + 1;

	raise debug 'going to finalize items for transaction %', _transaction_number;
	_result := -1;
	select custom_finalize_transaction_items(_transaction,_bdate,_terminal,_transaction_number,_sequence,_saletype,_linenumber) into _result;
	if _result < 1 then
		raise exception 'failed on finalizing items';
		exit;
	end if;
	raise debug 'finalized % items for transaction', _result;

	_sequence := _sequence + _result;
	_linenumber := _linenumber + _result;

	raise debug 'going to finalize store recall for transaction %', _transaction_number;
	_result := -1;
	select custom_finalize_transaction_storerecall(_transaction,_bdate,_terminal,_transaction_number,_sequence,_saletype,_linenumber) into _result;
	if _result < 1 then
		raise exception 'failed on finalizing store recall';
		exit;
	end if;
	raise debug 'finalized store recall for transaction % ', _transaction_number;

	_sequence := _sequence + 2;
	_linenumber := _linenumber + 2;

	raise debug 'going to finalize trailer for transaction %', _transaction_number;
	_result := -1;
	select custom_finalize_transaction_trailer(_transaction,_bdate,_terminal,_transaction_number,_sequence,_saletype,_linenumber) into _result;
	if _result < 1 then
		raise exception 'failed on finalizing trailer';
		exit;
	end if;
	raise debug 'finalized trailer for transaction % ', _transaction_number;

	_sequence := _sequence - 1;
	_linenumber := _linenumber - 1;

	raise debug 'going to finalize media for transaction %', _transaction_number;
	_result := -1;
	select custom_finalize_transaction_media(_transaction,_bdate,_terminal,_transaction_number,_sequence,_saletype,_linenumber) into _result;
	if _result < 1 then
		raise exception 'failed on finalizing media';
		exit;
	end if;
	raise debug 'finalized media for transaction % ', _transaction_number;

	_sequence := _sequence + 2;
	_linenumber := _linenumber + 2;

	raise debug 'going to finalize subtotal for transaction %', _transaction_number;
	_result := -1;
	select custom_finalize_transaction_subtotal(_transaction,_bdate,_terminal,_transaction_number,_sequence,_saletype,_linenumber) into _result;
	if _result < 1 then
		raise exception 'failed on finalizing subtotal';
		exit;
	end if;
	raise debug 'finalized subtotal for transaction % ', _transaction_number;


	raise debug 'processed transaction %', _transaction_description;


end loop;
raise log '<<OUT>> custom_finalize_transaction';

RETURN;
end;
$BODY$
 language 'plpgsql';

ALTER FUNCTION custom_finalize_transaction(varchar(50),varchar(50)) OWNER TO tplinux;
COMMENT ON FUNCTION custom_finalize_transaction(varchar(50),varchar(50)) is 
'
Helper function which builds a set of records reflecting the itemizers
for all the items in the transactions refering to all the available dates
in the database.
arguments:	
returns:
	a set of transaction itemizer types.
';





---------------------------------------------------------------------------------


---------------------------------------------------------------------------------


---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_finalize_transaction_trailer(_transaction custom_transaction2finalize, _bdate int4, 
		_termnmbr smallint,_transnmbr integer, _seq integer, _saletype smallint, _linenmbr integer) 
returns integer
as 	
$BODY$
declare
	_rowcount integer;
	_result bit := 1;
	_ejtrailer ejtrailer;
	_sql text;
	_transaction_description text;
begin
	raise log '<<IN>> custom_finalize_transaction_trailer';

	_transaction_description := 'bdate=' || _transaction.bdate || '/termnmbr=' || _transaction.termnmbr || 
			'/transnmbr=' || _transaction.transnmbr;

	execute 'select * from ejtrailer' || _transaction.bdate || ' where termnmbr=' || _transaction.termnmbr 
		|| ' and transnmbr=' || _transaction.transnmbr into _ejtrailer;
	
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	if _rowcount != 1 then
		if _rowcount > 1 then 
			raise exception 'transaction with more than one trailer --> ' , _transaction_description;
		end if;
		if _rowcount < 1 then 
			raise exception 'transaction with no trailer --> ' , _transaction_description;
		end if;
		
		RETURN _result;
	end if;
	
	/* reset var */
	_rowcount:=0;

	_sql := 'insert into ejtrailer' || _bdate || '('
		|| 'termnmbr,transnmbr,linenmbr,rectype,dcf,'
		|| 'posgrpnmbr,functype,saletype,rsvdhdr,datetime,'
		|| 'date,time,bdate,cshrnmbr,seq,'
		|| 'netsale,grosssale,nmbrofitems,totalrounding,cashiervalueseq,'
		|| 'userchar,szuserchar,salemod,headerdatetime,totalvat,'
		|| 'totalaos,totaldirectdiscamnt,totalindirectdiscamnt,totalpoints,totaldirectissued,'
		|| 'totalindirectissued,totaldirectredeemed,totalindirectredeemed,totalmkdn,totalmanualdisc'
		|| ') values('
		|| _termnmbr || ',' || _transnmbr || ',' || _linenmbr || ',' || _ejtrailer.rectype || ',''' || _ejtrailer.dcf
		|| ''',' || _ejtrailer.posgrpnmbr || ',' || _ejtrailer.functype || ',' || _saletype || ',' || _ejtrailer.rsvdhdr || ',' || _ejtrailer.datetime
		|| ',''' || _ejtrailer.date || ''',''' || _ejtrailer.time || ''',' || _bdate || ',' || _ejtrailer.cshrnmbr || ',' || _seq
		|| ',' || _ejtrailer.netsale || ',' || _ejtrailer.grosssale || ',' || _ejtrailer.nmbrofitems || ',' || _ejtrailer.totalrounding || ',' || _ejtrailer.cashiervalueseq
		|| ',' || _ejtrailer.userchar || ',''' || _ejtrailer.szuserchar || ''',''' || _ejtrailer.salemod || ''',' || _ejtrailer.headerdatetime || ',' || _ejtrailer.totalvat
		|| ',' || _ejtrailer.totalaos || ',' || _ejtrailer.totaldirectdiscamnt || ',' || _ejtrailer.totalindirectdiscamnt || ',' || _ejtrailer.totalpoints || ',' || _ejtrailer.totaldirectissued
		|| ',' || _ejtrailer.totalindirectissued || ',' || _ejtrailer.totaldirectredeemed || ',' || _ejtrailer.totalindirectredeemed || ',' || _ejtrailer.totalmkdn || ',' || _ejtrailer.totalmanualdisc
		|| ')';
	execute _sql;

	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	if _rowcount = 1 then
		_result:=0;
	end if;
	raise log '<<OUT>> custom_finalize_transaction_trailer(%)', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transaction_trailer(_transaction custom_transaction2finalize, _bdate int4, 
		_termnmbr smallint,_transnmbr integer, _seq integer, _saletype smallint, _linenmbr integer) is 
'
Helper function that finalizes the header for a stored transaction.
It queries the database for the original ejheader record and if it finds it inserts a record
in the table ejheader<BDATE>, where BDATE is the second argument, based on the ejheader retrieved
and on the data in the first argument.
arguments:	
	_transaction custom_transaction2finalize		transaction custom type that holds the 
						data of the original transaction records.
	_bdate int4					the business date on which the records must be now
						finalized.
';
ALTER FUNCTION custom_finalize_transaction_trailer(_transaction custom_transaction2finalize, _bdate int4, 
		_termnmbr smallint,_transnmbr integer, _seq integer, _saletype smallint, _linenmbr integer) OWNER TO tplinux;



---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_finalize_transaction_media(_transaction custom_transaction2finalize, _bdate int4, 
		_termnmbr smallint,_transnmbr integer, _seq integer, _saletype smallint, _linenmbr integer) 
returns integer
as 	
$BODY$
declare
	_rowcount integer := 0;
	_result bit := 1;
	_ejmedia ejmedia;
	_ejtrailer ejtrailer;
	_ejheader ejheader;
	_sql text;
	_transaction_description text;
	_rectype smallint := 40;
	_rsvdhdr smallint := 0;
	MEDIA_TYPE_CREDITO smallint := 8;
	MEDIA_TYPE_NUMERARIO smallint := 1;
	MEDIA_NUMBER_CREDITO smallint := 222;
	MEDIA_NUMBER_NUMERARIO smallint := 1;
	MEDIA_GROUP_CREDITO smallint := 7;
	MEDIA_GROUP_NUMERARIO smallint := 1;
	MEDIA_DESC_CREDITO varchar(21) := 'CREDITO';
	MEDIA_DESC_NUMERARIO varchar(21) := 'NUMERARIO';
	
begin
	raise log '<<IN>> custom_finalize_transaction_media';

	_transaction_description := 'bdate=' || _transaction.bdate || '/termnmbr=' || _transaction.termnmbr || 
			'/transnmbr=' || _transaction.transnmbr;

	_ejmedia.termnmbr := _termnmbr;
	_ejmedia.transnmbr := _transnmbr;
	_ejmedia.linenmbr := _linenmbr;
	_ejmedia.rectype := _rectype;
	_ejmedia.saletype := _saletype;
	_ejmedia.rsvdhdr := _rsvdhdr;
	_ejmedia.bdate := _bdate;
	_ejmedia.seq := _seq;
	_ejmedia.mngrovnmbr := 0;
	_ejmedia.funcsetovr := 0;
	_ejmedia.mediakeybits := '00000000';
	_ejmedia.mediatype := _transaction.medcurrnmbr;
	if _ejmedia.mediatype = MEDIA_TYPE_NUMERARIO then
		_ejmedia.medianmbr := MEDIA_NUMBER_NUMERARIO;
		_ejmedia.mdesc := MEDIA_DESC_NUMERARIO;
		_ejmedia.medgrpused := MEDIA_GROUP_NUMERARIO;
	else
		_ejmedia.medianmbr := MEDIA_NUMBER_CREDITO;
		_ejmedia.mdesc := MEDIA_DESC_CREDITO;
		_ejmedia.medgrpused := MEDIA_GROUP_NUMERARIOz;
	end if;
	_ejmedia.changefrom := 1;
	_ejmedia.accountnmbr := 0;
	_ejmedia.accountstat := '';
	_ejmedia.accountaprovl := ''; 
	_ejmedia.rejmngrnmbr := 0;
	_ejmedia.rejfuncsetovr := 0;
	_ejmedia.creditflags := '00000000';
	_ejmedia.entrytype := 2;
	_ejmedia.expdate := 0;
	_ejmedia.storemedia := 49;
	_ejmedia.decpos := 2;
	_ejmedia.drateexchg := 1;
	_ejmedia.medcurrflag := 1;
	_ejmedia.discountgrpident := '0';
	_ejmedia.flag := '00000000';
	_ejmedia.meddisclinenmbr := 0;

	_sql := 'select * from ejtrailer' || _transaction.bdate || ' where termnmbr=' || _transaction.termnmbr 
		|| ' and transnmbr=' || _transaction.transnmbr;
	execute  _sql into _ejtrailer;

	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	if _rowcount != 1 then
		if _rowcount > 1 then 
			raise exception 'transaction with more than one trailer --> ' , _transaction_description;
		end if;
		if _rowcount < 1 then 
			raise exception 'transaction with no trailer --> ' , _transaction_description;
		end if;
		
		RETURN _result;
	end if;

	_rowcount:=0; /* reset var */

	_ejmedia.altcurrtend := _ejtrailer.netsale;
	_ejmedia.mediaamnt := _ejtrailer.netsale;

	_sql := 'select * from ejheader' || _transaction.bdate || ' where termnmbr=' || _transaction.termnmbr 
		|| ' and transnmbr=' || _transaction.transnmbr;
	execute  _sql into _ejheader;

	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	if _rowcount != 1 then
		if _rowcount > 1 then 
			raise exception 'transaction with more than one header --> ' , _transaction_description;
		end if;
		if _rowcount < 1 then 
			raise exception 'transaction with no header --> ' , _transaction_description;
		end if;
		
		RETURN _result;
	end if;

	_ejmedia.dcf := _ejheader.dcf;
	_ejmedia.posgrpnmbr := _ejheader.posgrpnmbr;
	_ejmedia.functype := _ejheader.functype;
	_ejmedia.datetime := _ejheader.datetime;
	_ejmedia.date := _ejheader.date;
	_ejmedia.time := _ejheader.time;
	_ejmedia.cshrnmbr := _ejheader.cshrnmbr;

	_rowcount := 0; /* reset var */

	_sql := 'insert into ejmedia' || _bdate || '('
		|| 'termnmbr,transnmbr,linenmbr,rectype,dcf,'
		|| 'posgrpnmbr,functype,saletype,rsvdhdr,datetime,'
		|| 'date,time,bdate,cshrnmbr,seq,'
		|| 'mngrovnmbr,funcsetovr,mediakeybits,mdesc,mediaamnt,'
		|| 'mediatype,changefrom,accountnmbr,accountstat,accountaprovl,'
		|| 'rejmngrnmbr,rejfuncsetovr,creditflags,entrytype,medianmbr,' 
		|| 'expdate,storemedia,medgrpused,decpos,drateexchg,'
		|| 'medcurrflag,altcurrtend,discountgrpident,flag,meddisclinenmbr'
		|| ') values('
		|| _ejmedia.termnmbr || ',' || _ejmedia.transnmbr || ',' || _ejmedia.linenmbr || ',' || _ejmedia.rectype || ',''' || _ejmedia.dcf || ''','
		|| _ejmedia.posgrpnmbr || ',' || _ejmedia.functype || ',' || _ejmedia.saletype || ',' || _ejmedia.rsvdhdr || ',' || _ejmedia.datetime || ','
		||  || ',' || _ejmedia.date || ',''' || _ejmedia.time || ''',''' || _ejmedia.bdate || ''',' || _ejmedia.cshrnmbr || ',' || _ejmedia.seq || ','
		|| _ejmedia.mngrovnmbr || ',' || _ejmedia.funcsetovr || ',''' || _ejmedia.mediakeybits || ''',''' || _ejmedia.mdesc || ''',' || _ejmedia.mediaamnt || ','
		|| _ejmedia.mediatype || ',' || _ejmedia.changefrom || ',' || _ejmedia.accountnmbr || ',''' || _ejmedia.accountstat || ''',''' || _ejmedia.accountaprovl || ''','
		|| _ejmedia.rejmngrnmbr || ',' || _ejmedia.rejfuncsetovr || ',''' || _ejmedia.creditflags || ''',' || _ejmedia.entrytype || ',' || _ejmedia.medianmbr || ','
		|| _ejmedia.expdate || ',' || _ejmedia.storemedia || ',' || _ejmedia.medgrpused || ',' || _ejmedia.decpos || ',' || _ejmedia.drateexchg || ','
		|| _ejmedia.medcurrflag || ',' || _ejmedia.altcurrtend || ',''' || _ejmedia.discountgrpident || ''',''' || _ejmedia.flag || ''',' || _ejmedia.meddisclinenmbr
		|| ')';
	execute _sql;

	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	if _rowcount = 1 then
		_result:=0;
	end if;
	raise log '<<OUT>> custom_finalize_transaction_media(%)', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transaction_media(_transaction custom_transaction2finalize, _bdate int4, 
		_termnmbr smallint,_transnmbr integer, _seq integer, _saletype smallint, _linenmbr integer) is 
'
Helper function that finalizes the header for a stored transaction.
It queries the database for the original ejheader record and if it finds it inserts a record
in the table ejheader<BDATE>, where BDATE is the second argument, based on the ejheader retrieved
and on the data in the first argument.
arguments:	
	_transaction custom_transaction2finalize		transaction custom type that holds the 
						data of the original transaction records.
	_bdate int4					the business date on which the records must be now
						finalized.
';
ALTER FUNCTION custom_finalize_transaction_media(_transaction custom_transaction2finalize, _bdate int4, 
		_termnmbr smallint,_transnmbr integer, _seq integer, _saletype smallint, _linenmbr integer) OWNER TO tplinux;


---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_finalize_transaction_subtotal(_transaction custom_transaction2finalize, _bdate int4, 
		_termnmbr smallint,_transnmbr integer, _seq integer, _saletype smallint, _linenmbr integer) 
returns integer
as 	
$BODY$
declare
	_rowcount integer := 0;
	_result bit := 1;
	_ejsubtotal ejsubtotal;
	_ejtrailer ejtrailer;
	_ejheader ejheader;
	_sql text;
	_transaction_description text;

	
begin
	raise log '<<IN>> custom_finalize_transaction_subtotal';

	_transaction_description := 'bdate=' || _transaction.bdate || '/termnmbr=' || _transaction.termnmbr || 
			'/transnmbr=' || _transaction.transnmbr;

	_sql := 'select * from ejsubtotal' || _transaction.bdate || ' where termnmbr=' || _transaction.termnmbr 
		|| ' and transnmbr=' || _transaction.transnmbr;
	execute _sql into _ejsubtotal;
	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	if _rowcount > 0 then

	else


	end if;

	_ejsubtotal.termnmbr := _termnmbr;
	_ejsubtotal.transnmbr := _transnmbr;
	_ejsubtotal.linenmbr := _linenmbr;
	_ejsubtotal.rectype := 55;
	_ejsubtotal.saletype := 5;
	_ejsubtotal.rsvdhdr := 0;
	_ejsubtotal.bdate := _bdate;
	_ejsubtotal.seq := _seq;
	_ejsubtotal.print1contxt := 7;
	_ejsubtotal.print1text := 'SUBTOTAL';
	_ejsubtotal.print2contxt := 0;
	_ejsubtotal.print2text := '';

	_sql := 'select * from ejtrailer' || _transaction.bdate || ' where termnmbr=' || _transaction.termnmbr 
		|| ' and transnmbr=' || _transaction.transnmbr;
	execute _sql  into _ejtrailer;

	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	if _rowcount != 1 then
		if _rowcount > 1 then 
			raise exception 'transaction with more than one trailer --> ' , _transaction_description;
		end if;
		if _rowcount < 1 then 
			raise exception 'transaction with no trailer --> ' , _transaction_description;
		end if;
		
		RETURN _result;
	end if;

	_rowcount:=0; /* reset var */

	_ejsubtotal.amnt := _ejtrailer.netsale;

	_sql := 'select * from ejheader' || _transaction.bdate || ' where termnmbr=' || _transaction.termnmbr 
		|| ' and transnmbr=' || _transaction.transnmbr;
	execute  _sql into _ejheader;

	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	if _rowcount != 1 then
		if _rowcount > 1 then 
			raise exception 'transaction with more than one header --> ' , _transaction_description;
		end if;
		if _rowcount < 1 then 
			raise exception 'transaction with no header --> ' , _transaction_description;
		end if;
		
		RETURN _result;
	end if;

	_ejsubtotal.dcf := _ejheader.dcf;
	_ejsubtotal.posgrpnmbr := _ejheader.posgrpnmbr;
	_ejsubtotal.functype := _ejheader.functype;
	_ejsubtotal.datetime := _ejheader.datetime;
	_ejsubtotal.date := _ejheader.date;
	_ejsubtotal.time := _ejheader.time;
	_ejsubtotal.cshrnmbr := _ejheader.cshrnmbr;

	_rowcount := 0; /* reset var */

	_sql := 'insert into ejsubtotal' || _bdate || '('
		|| 'termnmbr,transnmbr,linenmbr,rectype,dcf,'
		|| 'posgrpnmbr,functype,saletype,rsvdhdr,datetime,'
		|| 'date,time,bdate,cshrnmbr,seq,'
		|| 'amnt,print1contxt,print1text,print2contxt,print2text'
		|| ') values('
		|| _ejsubtotal.termnmbr || ',' || _ejsubtotal.transnmbr || ',' || _ejsubtotal.linenmbr || ',' || _ejsubtotal.rectype || ',''' || _ejsubtotal.dcf || ''','
		|| _ejsubtotal.posgrpnmbr || ',' || _ejsubtotal.functype || ',' || _ejsubtotal.saletype || ',' || _ejsubtotal.rsvdhdr || ',' || _ejsubtotal.datetime || ','''
		|| _ejsubtotal.date || ''',''' || _ejsubtotal.time || ''',' || _ejsubtotal.bdate || ',' || _ejsubtotal.cshrnmbr || ',' || _ejsubtotal.seq || ','
		|| _ejsubtotal.amnt || ',' || _ejsubtotal.print1contxt || ',''' || _ejsubtotal.print1text || ''',' || _ejsubtotal.print2contxt || ',''' || _ejsubtotal.print2text
		|| ''')';
	execute _sql;

	GET DIAGNOSTICS _rowcount = ROW_COUNT;
	if _rowcount = 1 then
		_result:=0;
	end if;
	raise log '<<OUT>> custom_finalize_transaction_subtotal(%)', _result;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_finalize_transaction_subtotal(_transaction custom_transaction2finalize, _bdate int4, 
		_termnmbr smallint,_transnmbr integer, _seq integer, _saletype smallint, _linenmbr integer) is 
'
Helper function that finalizes the header for a stored transaction.
It queries the database for the original ejheader record and if it finds it inserts a record
in the table ejheader<BDATE>, where BDATE is the second argument, based on the ejheader retrieved
and on the data in the first argument.
arguments:	
	_transaction custom_transaction2finalize		transaction custom type that holds the 
						data of the original transaction records.
	_bdate int4					the business date on which the records must be now
						finalized.
';
ALTER FUNCTION custom_finalize_transaction_subtotal(_transaction custom_transaction2finalize, _bdate int4, 
		_termnmbr smallint,_transnmbr integer, _seq integer, _saletype smallint, _linenmbr integer) OWNER TO tplinux;