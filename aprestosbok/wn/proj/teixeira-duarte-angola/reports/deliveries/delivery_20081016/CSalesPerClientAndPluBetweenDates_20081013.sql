
		---### DELETE ###

		delete from menu_commonpt where id='CSalesPerClientAndPluBetweenDates';

		---### INSERT ###		

		insert into menu_commonpt
		(
		id, title,parent_id,
		pariddocu,order_by,access1,
		access2,href,
		query,html_format,
		common_format,count_query,user_methods
		)
		values
		(
		'CSalesPerClientAndPluBetweenDates','i18n:Sales per Client and Article between dates','salesReports'
		,'salesReports',60020,'commonpt',			
		'reportspt','formular.html',
		'select t.bdate, t.custnmbr,t.custname,t.deptnmbr,t.deptdesc,t.plunmbr,t.pludesc,sum(t.qtySold) as qtysold, sum(t.amtSold) as amtsold,sum(t.sumamtdisc) as amtdisc,'
			|| ' sum(t.netamtsold) as netamtsold,sum(t.marginval) as amtmargin,'
			|| ' case when sum(t.netamtsold)<>0 then ((sum(t.marginval)/sum(t.netamtsold))*100)::int8 when sum(t.netamtsold)=0 and sum(t.amtsoldcost)>0 then(-1*100)::int8 else (0)::int8 end as percmargin ' 
		||' from '
		||'( '
			||'select g.bdate,g.custnmbr,g.custname,g.deptnmbr,g.deptdesc,g.plunmbr,g.pludesc,g.qtySold,g.amtSold,g.sumamtdisc,(g.buyprice::float * g.qtySold::float)::int8 as amtsoldcost,'
			||' 	(g.amtSold::float-g.sumamtdisc::float)::int8 as netamtsold,case when g.amtSold=0 then 0 when g.qtySold=0 then 0 else ((g.amtSold::float-g.sumamtdisc::float)-(g.buyprice::float * g.qtySold::float))::int8 end as marginval '
			||' from( '
				||'select f.bdate,f.termnmbr,f.transnmbr,f.custnmbr,f.custname,f.deptnmbr,f.deptdesc,f.plunmbr,f.pludesc, '
					||' case when pluext.price1 is null then 0 else pluext.price1 end as buyprice,f.qtySold,f.amtSold,f.sumamtdisc '
				||'from(' 
				|| 	'select e.bdate,e.termnmbr,e.transnmbr,e.custnmbr,e.custname,e.deptnmbr,dept.deptdesc,e.plunmbr,e.pludesc,e.qtySold,e.amtSold,e.sumamtdisc '
				|| 	'from ('
				|| 		'select q.bdate,q.termnmbr,q.transnmbr,q.custnmbr,customer.custname,q.deptnmbr,q.plunmbr,q.pludesc,q.qtySold,q.amtSold,q.sumamtdisc '
				|| 		' from(select c.bdate,c.termnmbr,c.transnmbr,c.custnmbr,d.deptnmbr,d.plunmbr,d.pludesc,d.qtySold,d.amtSold,d.sumamtdisc '
				|| 'from('
					|| ' select a.bdate,a.termnmbr,a.transnmbr,a.custnmbr '
					|| ' from '
					|| ' 	(select bdate, termnmbr, transnmbr, custnmbr from ejheader where functype=0 and saletype in (0,5))a '
					|| ' inner join '
					|| ' 	(select bdate, termnmbr, transnmbr from ejtrailer)b '
					|| ' on a.bdate=b.bdate and a.termnmbr=b.termnmbr and a.transnmbr=b.transnmbr '
				|| ' )c '
				|| ' inner join '
				|| ' (select bdate, termnmbr, transnmbr, plunmbr, pludesc, deptnmbr, price1, qtySold, amtSold, sumamtdisc from ejitem )d '
				|| ' on c.bdate=d.bdate and c.termnmbr=d.termnmbr and c.transnmbr=d.transnmbr '
		|| ')q inner join customer on q.custnmbr=customer.custnmbr $conditions$)e ' 
				|| 'inner join dept on e.deptnmbr=dept.deptnmbr)f left join pluext on f.plunmbr=pluext.plunmbr )g )t '
				|| '  ' 
				|| ' group by t.bdate, t.custnmbr, t.custname, t.deptnmbr,t.deptdesc,t.plunmbr, t.pludesc '
				|| ' order by t.bdate, t.custnmbr, t.custname, t.deptnmbr,t.deptdesc,t.plunmbr, t.pludesc',	
		'MISC[insBatch]'		
		,			
		'MISC[columnTotal=amtsold,location=footer;],' ||	
		'ROWS[bdate:group-on=1,title=i18n:bdate;custnmbr:group-on=1,no-footer=1,hidden=0,title=i18n:Customer Number;custname:group-on=1,title=i18n:customer;'
			|| 'plunmbr:group-on=1,no-footer=1,hidden=0,title=i18n:Article Number;pludesc:group-on=1,no-footer=1,title=i18n:Items;'
			|| 'deptnmbr:group-on=1,no-footer=1,title=i18n:Dept Number;deptdesc:group-on=1,title=i18n:Dept Name;'
			|| 'qtysold:SUM=1,title=i18n:qtysold,align=right,df=number;amtsold:SUM=1,title=i18n:amtsold,align=right,df=number;'
			|| 'amtdisc:SUM=1,title=i18n:amtdisc,align=right,df=number;' 
			|| 'netamtsold:SUM=1,title=i18n:netamtsold,align=right,df=number;'
			|| 'amtmargin:SUM=1,title=i18n:amtmargin,align=right,df=number;percmargin:title=i18n:percmargin,align=right,df=number;],'
		|| 'FOOTER[bdate:bold=1,align=right;custname:bold=1,align=right;pludesc:bold=1,align=right;'
			|| 'qtysold:bold=1,df=number;'
			|| 'amtsold:bold=1,df=number;'
			|| 'amtdisc:bold=1,df=number;'
			|| 'netamtsold:bold=1,df=number;'
			|| 'amtmargin:bold=1,df=number;'
		|| ']'	
		,'select 0','com.+.ReportTool.doTablesBusinessDatesResolution_HandleQuery'
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
		':CSalesPerClientAndPluBetweenDates:','q.custnmbr','ge',
		'i18n:From customer',10
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
		':CSalesPerClientAndPluBetweenDates:','q.custnmbr','le',
		'i18n:To customer',15
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
		':CSalesPerClientAndPluBetweenDates:','q.plunmbr','ge',
		'i18n:From item number',20

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
		':CSalesPerClientAndPluBetweenDates:','q.plunmbr','le',
		'i18n:To item number',25

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
		':CSalesPerClientAndPluBetweenDates:','q.bdate','ge',
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
		':CSalesPerClientAndPluBetweenDates:','q.bdate','le',
		'i18n:To business date',35,
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
		':CSalesPerClientAndPluBetweenDates:','button',80,
		'button:submit','i18n:createReport','c2'	
		);
insert into formular_commonpt
		(
		id,req_par, order_by
		)
		values
		(
		':CSalesPerClientAndPluBetweenDates:','<hr>',50
		);
insert into formular_commonpt
		(
		id,req_par,title, order_by,input_value
		
		)
		values
		(
		':CSalesPerClientAndPluBetweenDates:','rf','i18n:reportformat', 60,'html'
		);
insert into formular_commonpt
		(
		id,req_par, order_by,input_value
		
		)
		values
		(
		':CSalesPerClientAndPluBetweenDates:','limit', 70,'200'
		);