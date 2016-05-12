

--iterate

---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_create_type(_type varchar(50), _type_definition varchar(200), _owner varchar(20)) 
returns integer
as 	
$BODY$
declare
	_rows integer;
	_result bit := 1;
begin

select count(pg_catalog.pg_type.typname) into _rows from pg_catalog.pg_type where pg_catalog.pg_type.typname=_type;

if _rows > 0 then
	execute 'drop type ' || _type || ' cascade';
end if;

execute 'create type ' || _type || ' as (' || _type_definition || ')' ;
execute 'alter type ' || _type || ' owner to ' || _owner;

_result := 0;

RETURN _result;	
end;
$BODY$
 language 'plpgsql';

COMMENT ON FUNCTION custom_create_type(_type varchar(50), _type_definition varchar(200), _owner varchar(20)) is 
'
Helper function for type creation, checking the existence of the desired type and deleting it,
cascading the delete onto the associated functions.
arguments:	
	_type			varchar(50)		type name
	_type_definition	varchar(200)		system types comma separated list
	_owner			varchar(20)		user to own the type
';
ALTER FUNCTION custom_create_type(_type varchar(50), _type_definition varchar(200), _owner varchar(20)) OWNER TO tplinux;





---------------------------------------------------------------------------------
/*
*	types creation
*/
select * from custom_create_type('custom_transaction_subtotal','bdate int4,termnmbr int2, transnmbr int4, saletype int2, amnt int8','tplinux');
select * from custom_create_type('custom_transaction_header','bdate int4,termnmbr int2, transnmbr int4, custnmbr numchar, saletype int2, functype int2','tplinux');
select * from custom_create_type('custom_transaction_trailer','bdate int4, termnmbr int2, transnmbr int4, netsale int8, grosssale int8','tplinux');
select * from custom_create_type('custom_transaction_item','bdate int4, termnmbr int2, transnmbr int4, plunmbr numchar, pludesc varchar, deptnmbr numchar, 
		price1 int8, qtySold int8, amtSold int8, sumamtdisc int8, itmz bit(32), buyprice int8, amtsoldcost int8, netamtsold int8, marginval int8, marginperc int8','tplinux');
select * from custom_create_type('custom_transaction_itemizer','bdate int4, termnmbr int2, transnmbr int4, itmznmbr int2, itmzamnt int4, itmz bit(32)','tplinux');

	select * from custom_create_type('custom_transaction_date_customer_dept_item','bdate int4, custnmbr numchar, custname varchar(40), 
			deptnmbr numchar, deptdesc varchar(21),plunmbr numchar, 
			pludesc varchar, qtysold int8, amtsold int8, amtdisc 
			int8, amtsoldcost int8, netamtsold int8, amtmargin int8, percmarginperc int8','tplinux');

---------------------------------------------------------------------------------
/*
*	functions creation
*/

---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_get_available_businessdates()
  RETURNS SETOF int4 AS
$BODY$
declare 
	record RECORD;	
	query varchar(100)='select bdate from businessdate where deleted is null';
	businessdate integer;
begin
		
for record IN EXECUTE query LOOP
	businessdate=record.bdate;
	return next businessdate;
end loop;
RETURN;
end;
$BODY$
  LANGUAGE 'plpgsql';
ALTER FUNCTION custom_get_available_businessdates() OWNER TO tplinux;
COMMENT ON FUNCTION custom_get_available_businessdates() is 
'
Helper function which resolves all available business dates still associated with
electronic journal and other data tables in the database (like ej_____2008090300 table).
arguments:	
returns:
	a set of integers reflecting each date available.
';
---------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION custom_get_available_transactionitemizers() 
returns setof custom_transaction_itemizer
as 	
$BODY$
declare
	r record;
	result custom_transaction_itemizer;
	business_date_query varchar(100):='select * from custom_get_available_businessdates()';
	source_query text:='select bdate, termnmbr, transnmbr, itmznmbr, itmzamnt, itmz from ejitemizer';
	query text:='';
begin

for r IN EXECUTE business_date_query LOOP
	if query = '' then	
		query := source_query || r.custom_get_available_businessdates;
	else
		query := query || ' union ' || source_query || r.custom_get_available_businessdates;
	end if;
end loop;

for r IN EXECUTE query LOOP
	result.bdate=r.bdate;
	result.termnmbr=r.termnmbr;
	result.transnmbr=r.transnmbr;
	result.itmznmbr=r.itmznmbr;
	result.itmzamnt=r.itmzamnt;
	result.itmz=r.itmz;
	return next result;
end loop;

RETURN;
end;
$BODY$
 language 'plpgsql';

ALTER FUNCTION custom_get_available_transactionitemizers() OWNER TO tplinux;
COMMENT ON FUNCTION custom_get_available_transactionitemizers() is 
'
Helper function which builds a set of records reflecting the itemizers
for all the items in the transactions refering to all the available dates
in the database.
arguments:	
returns:
	a set of transaction itemizer types.
';

---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_get_available_transactionitems() 
	returns setof custom_transaction_item
	as 	
	$BODY$
	declare
		r record;
		result custom_transaction_item;
		business_date_query varchar(100):='select * from custom_get_available_businessdates()';
		source_query text:='select bdate, termnmbr, transnmbr, plunmbr, pludesc, deptnmbr, price1, qtySold, amtSold, sumamtdisc,itmz from ejitem';
		query text:='';
	begin

	for r IN EXECUTE business_date_query LOOP
		if query = '' then
			query := source_query || r.custom_get_available_businessdates;
		else
			query := query || ' union ' || source_query || r.custom_get_available_businessdates;
		end if;
	end loop;

	query := 'select items.*,case when pluext.price1 is null then 0 else pluext.price1 end as buyprice,
		(items.amtSold::float-items.sumamtdisc::float)::int8 as netamtsold,
		((case when pluext.price1 is null then 0 else pluext.price1::float end) * items.qtySold::float)::int8 as amtsoldcost,
		case when items.amtSold=0 
		then 0 
		when items.qtySold=0
		then 0
		else ((items.amtSold::float-items.sumamtdisc::float)-((case when pluext.price1 is null then 0 else pluext.price1::float end) * items.qtySold::float))::int8
		end as marginval,
		case when items.amtSold=0
		then 0
		when items.qtySold=0
		then 0
		when pluext.price1=0
		then 100
		when pluext.price1 is null
		then 100
		else
		((((items.amtSold::float-items.sumamtdisc::float)-(pluext.price1::float  * items.qtySold::float))/(pluext.price1::float * items.qtySold::float))*100)::int8
		end as marginperc
		 from (' || query || 
		')items left join pluext on items.plunmbr=pluext.plunmbr';

	for r IN EXECUTE query LOOP
		result.bdate=r.bdate;
		result.termnmbr=r.termnmbr;
		result.transnmbr=r.transnmbr;
		result.plunmbr=r.plunmbr;
		result.pludesc=r.pludesc;
		result.deptnmbr=r.deptnmbr;
		result.price1=r.price1;
		result.qtySold=r.qtySold;
		result.amtSold=r.amtSold;
		result.sumamtdisc=r.sumamtdisc;
		result.itmz=r.itmz;
		result.buyprice=r.buyprice;
		result.netamtsold=r.netamtsold;
		result.amtsoldcost=r.amtsoldcost;
		result.marginval=r.marginval;
		result.marginperc=r.marginperc;
		return next result;
	end loop;

	RETURN;
	end;
	$BODY$
	 language 'plpgsql';

ALTER FUNCTION custom_get_available_transactionitems() OWNER TO tplinux;
COMMENT ON FUNCTION custom_get_available_transactionitems() is 
'
Helper function which builds a set of records reflecting all the items 
from the transactions refering to all the available dates in the database.
arguments:	
returns:
	a set of transaction item types.
';
---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_get_available_transactiontrailers() 
returns setof custom_transaction_trailer
as 	
$BODY$
declare
	r record;
	result custom_transaction_trailer;
	business_date_query varchar(100):='select * from custom_get_available_businessdates()';
	query text:='';
begin

for r IN EXECUTE business_date_query LOOP
	if query = '' then
		query := 'select bdate, termnmbr, transnmbr, netsale, grosssale from ejtrailer' || r.custom_get_available_businessdates;
	else
		query := query || ' union ' || 'select bdate, termnmbr, transnmbr, netsale, grosssale from ejtrailer' || r.custom_get_available_businessdates;
	end if;
end loop;

for r IN EXECUTE query LOOP
	result.bdate=r.bdate;
	result.termnmbr=r.termnmbr;
	result.transnmbr=r.transnmbr;
	result.netsale=r.netsale;
	result.grosssale=r.grosssale;
	return next result;
end loop;

RETURN;
end;
$BODY$
 language 'plpgsql';
ALTER FUNCTION custom_get_available_transactiontrailers() OWNER TO tplinux;
COMMENT ON FUNCTION custom_get_available_transactiontrailers() is 
'
Helper function which builds a set of records reflecting all the trailers 
from the transactions refering to all the available dates in the database.
arguments:	
returns:
	a set of transaction trailer types.
';

---------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION custom_get_available_transactionheaders() 
returns setof custom_transaction_header
as 	
$BODY$
declare
	r record;
	result custom_transaction_header;
	business_date_query varchar(100):='select * from custom_get_available_businessdates()';
	query text:='';
begin
		
for r IN EXECUTE business_date_query LOOP
	if query = '' then
		query := 'select bdate, termnmbr, transnmbr, custnmbr,saletype, functype from ejheader' || r.custom_get_available_businessdates;
	else
		query := query || ' union ' || 'select bdate, termnmbr, transnmbr, custnmbr,saletype, functype from ejheader' || r.custom_get_available_businessdates;	
	end if;
end loop;

for r IN EXECUTE query LOOP
	result.bdate=r.bdate;
	result.termnmbr=r.termnmbr;
	result.transnmbr=r.transnmbr;
	result.custnmbr=r.custnmbr;
	result.saletype=r.saletype;
	result.functype=r.functype;
	return next result;
end loop;

RETURN;
end;
$BODY$
 language 'plpgsql';
ALTER FUNCTION custom_get_available_transactionheaders() OWNER TO tplinux;
COMMENT ON FUNCTION custom_get_available_transactionheaders() is 
'
Helper function which builds a set of records reflecting all the headers 
from the transactions refering to all the available dates in the database.
arguments:	
returns:
	a set of transaction header types.
';
---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_get_available_salestransactionheaders() 
returns setof custom_transaction_header
as 	
$BODY$
declare
	th custom_transaction_header;
	-- functype must be sales_mode
	-- saletypes standard sale and recalled sale
	query text:='select * from custom_get_available_transactionheaders() where functype=0 and saletype in (0,5) ';
begin

for th IN EXECUTE query LOOP
	return next th;
end loop;

RETURN;
end;
$BODY$
 language 'plpgsql';
ALTER FUNCTION custom_get_available_salestransactionheaders() OWNER TO tplinux;
COMMENT ON FUNCTION custom_get_available_salestransactionheaders() is 
'
Helper function which builds a set of records reflecting all the headers 
from the sales transactions refering to all the available dates in the database.
Note the constraints on the functype(=0, e.g., must be sales_mode) and on 
saletype(in(0,5), e.g., standard sale and recalled sale).
arguments:	
returns:
	a set of transaction header types.
';
---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_get_available_transactionsubtotals() 
returns setof custom_transaction_subtotal
as 	
$BODY$
declareheaders_trailers_items_query text:='';
	business_date int4;
	r record;
	subtotal custom_transaction_subtotal;
	business_date_query varchar(100):='select * from custom_get_available_businessdates()';
	subtotals_query text:='';
begin
		
for r IN EXECUTE business_date_query LOOP
	business_date := r.custom_get_available_businessdates;
	if subtotals_query = '' then
		subtotals_query := 'select bdate,termnmbr,transnmbr, saletype, amnt from ejsubtotal' || business_date;
	else
		subtotals_query := subtotals_query || ' union ' || 'select bdate,termnmbr,transnmbr, saletype, amnt from ejsubtotal' || business_date;
	end if;
end loop;

for r IN EXECUTE subtotals_query LOOP
	subtotal.bdate=r.bdate;
	subtotal.termnmbr=r.termnmbr;
	subtotal.transnmbr=r.transnmbr;
	subtotal.saletype=r.saletype;
	subtotal.amnt=r.amnt;
	return next subtotal;
end loop;

RETURN;
end;
$BODY$
 language 'plpgsql';
ALTER FUNCTION custom_get_available_transactionsubtotals() OWNER TO tplinux;
COMMENT ON FUNCTION custom_get_available_transactionsubtotals() is 
'
Helper function which builds a set of records reflecting all the subtotals 
from the transactions refering to all the available dates in the database.
arguments:	
returns:
	a set of transaction subtotal types.
';
---------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION custom_get_reportdata_salesperclientandplubetweendates() 
returns setof custom_transaction_date_customer_dept_item
as 	
$BODY$
declare
	business_date int4;
	_record record;
	_row custom_transaction_date_customer_dept_item;
	business_date_query varchar(100):='select * from custom_get_available_businessdates()';
	subtotals_query text:='';
begin
		
for r IN EXECUTE business_date_query LOOP
	business_date := r.custom_get_available_businessdates;
	if subtotals_query = '' then
		subtotals_query := 'select bdate,termnmbr,transnmbr, saletype, amnt from ejsubtotal' || business_date;
	else
		subtotals_query := subtotals_query || ' union ' || 'select bdate,termnmbr,transnmbr, saletype, amnt from ejsubtotal' || business_date;
	end if;
end loop;

for r IN EXECUTE subtotals_query LOOP
	subtotal.bdate=r.bdate;
	subtotal.termnmbr=r.termnmbr;
	subtotal.transnmbr=r.transnmbr;
	subtotal.saletype=r.saletype;
	subtotal.amnt=r.amnt;
	return next subtotal;
end loop;

RETURN;
end;
$BODY$
 language 'plpgsql';
ALTER FUNCTION custom_get_available_transactionsubtotals() OWNER TO tplinux;
COMMENT ON FUNCTION custom_get_available_transactionsubtotals() is 
'
Helper function which builds a set of records reflecting all the subtotals 
from the transactions refering to all the available dates in the database.
arguments:	
returns:
	a set of transaction subtotal types.
';

---------------------------------------------------------------------------------



---------------------------------------------------------------------------------


---------------------------------------------------------------------------------


---------------------------------------------------------------------------------


---------------------------------------------------------------------------------
