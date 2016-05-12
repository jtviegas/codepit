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

