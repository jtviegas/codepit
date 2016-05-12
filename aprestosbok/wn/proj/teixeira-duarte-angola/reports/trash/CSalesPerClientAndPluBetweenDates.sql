
		---### DELETE ###

		delete from menu_commonpt where id='CSalesPerClientAndPluBetweenDates';

		---### INSERT ###		

		insert into menu_commonpt
		(
		id, title,parent_id,
		pariddocu,order_by,access1,
		access2,href,
		query,html_format,
		common_format,count_query
		)
		values
		(
		'CSalesPerClientAndPluBetweenDates','i18n:Sales per Client and Article between dates','salesReports'
		,'salesReports',60020,'commonpt',			
		'reportspt','formular.html',
		'select * from custom_get_reportdata_salesperclientandplubetweendates($conditions$)',	
		'MISC[insBatch]'		
		,			
		'MISC[columnTotal=amtsold,location=footer;],' ||	
		'ROWS[bdate:group-on=1,title=i18n:bdate;custnmbr:group-on=1,no-footer=1,hidden=0,title=i18n:Customer Number;custname:group-on=1,title=i18n:customer;'
			|| 'plunmbr:group-on=1,no-footer=1,hidden=0,title=i18n:Article Number;pludesc:group-on=1,title=i18n:Items;'
			|| 'deptnmbr:group-on=1,no-footer=1,title=i18n:Dept Number;deptdesc:group-on=1,no-footer=1,title=i18n:Dept Name;'
			|| 'qtysold:SUM=1,title=i18n:qtysold,align=right,df=number;amtsold:SUM=1,title=i18n:amtsold,align=right,df=number;'
			|| 'amtdisc:SUM=1,title=i18n:amtdisc,align=right,df=number;' --amtsoldcost:SUM=1,title=i18n:amtsoldcost,align=right,df=number;'
			|| 'netamtsold:SUM=1,title=i18n:netamtsold,align=right,df=number;'
			|| 'amtmargin:SUM=1,title=i18n:amtmargin,align=right,df=number;percmargin:title=i18n:percmargin,align=right,df=number;],'
		|| 'FOOTER[bdate:bold=1,align=right;custname:bold=1,align=right;pludesc:bold=1,align=right;'
			|| 'qtysold:bold=1,df=number;'
			|| 'amtsold:bold=1,df=number;'
			|| 'amtdisc:bold=1,df=number;'
			-- 'amtsoldcost:bold=1,df=number;'
			|| 'netamtsold:bold=1,df=number;'
			|| 'amtmargin:bold=1,df=number;'
		|| ']'	
		,'select 0'
		);


		---### DELETE ###

		delete from formular_commonpt where id=':CSalesPerClientAndPluBetweenDates:';

		---### INSERT ###
		insert into formular_commonpt
		(
		id,req_par,rp_ext,
		title,order_by
		--, input_type,	
		--input_value,input_values,format	
		)
			values
			(
		':CSalesPerClientAndPluBetweenDates:','d.custnmbr','eq',
		'i18n:Customer Number',10
		);

		insert into formular_commonpt
		(
		id,req_par,rp_ext,
		title,order_by
		--, input_type,
		--input_value,input_values,format	
		)
		values
		(
		':CSalesPerClientAndPluBetweenDates:','d.plunmbr','eq',
		'i18n:Article Number',20

		);
			
			
		insert into formular_commonpt
		(
		id,req_par,rp_ext,
		title,order_by, 
		input_type,
		--input_value,
		input_values
		--format	
		)
		values
		(
		':CSalesPerClientAndPluBetweenDates:','d.bdate','ge',
		'i18n:From business date',30,
		'SELECT','select bdate from businessdate where deleted is null and datetime_eod is not null order by bdate'
		);
		insert into formular_commonpt
		(
		id,req_par, 
		rp_ext,
		title,
			order_by
		, input_type,
		--input_value,
		input_values
		--format	
		)
		values
		(
		':CSalesPerClientAndPluBetweenDates:','d.bdate','le',
		'i18n:To business date',40,
		'SELECT','select bdate from businessdate where deleted is null and datetime_eod is not null order by bdate'
		);
		insert into formular_commonpt
		(
		id,req_par, 
		--rp_ext,
		--title,
			order_by
		, input_type,input_value,
		--input_values,
		format	
		)
		values
		(
		':CSalesPerClientAndPluBetweenDates:','button',50,
		'button:submit','i18n:createReport','c2'	
		);