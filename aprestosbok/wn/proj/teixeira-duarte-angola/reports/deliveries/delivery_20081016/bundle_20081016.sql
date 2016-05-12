
/**
*****************************************************************************************************************************************
	report CSalesByItemAndDate
**/
delete from menu_commonpt where id='CSalesByItemAndDate';
insert into menu_commonpt
(
id,title,parent_id,pariddocu,order_by,access1,access2,href,query,user_methods,html_format,common_format,count_query
)
values
(
	'CSalesByItemAndDate','i18n:Sales by item and dates','salesReports','salesReports',60011,'commonpt','reportspt','formular.html',
'
select t1.date,t1.plunmbr, t4.internalcode, t1.pludesc, t1.deptnmbr, t2.deptdesc,'''' as void, 
	sum(t1.qtysold) as qtysold, sum(t1.brutamtsold) as brutamtsold, 
	sum(t1.liqamtsold) as liqamtsold, sum(t1.amtdisc) as amtdisc, 
	((t3.price1 - t4.price1) * (sum(t1.qtysold) / 1000)) as margenvalue, 
	case when t3.price1=0  then -100::float else (1::float - (t4.price1::float / t3.price1::float)) end as margenperc 
from 
(select bdate as date,plunmbr, pludesc, deptnmbr, (sum(amtsold) - sum(amtrfnd)) as brutamtsold, 
		(sum(amtsold) - sum(amtdptdisc) - sum(amtrfnd)) as liqamtsold, 
		(sum(qtysold) - sum(qtyrfnd)) as qtysold, sum(amtdptdisc) as amtdisc from plumove 
	group by bdate,plunmbr, pludesc, deptnmbr 
	having sum(amtsold) != 0 or sum(qtysold) != 0 or sum(amtrfnd) != 0 or sum(qtyrfnd) != 0 or 
	sum(amtdptdisc) != 0 or sum(qtydptdisc) != 0 
)t1 
inner join 
(select * from dept)
t2 
on t1.deptnmbr = t2.deptnmbr 
inner join 
(select * from plu)t3 
on t1.plunmbr = t3.plunmbr 
inner join 
(
	select pluext.*, case when pluext.pluextdesc is null then 0 else pluext.pluextdesc::integer end as internalcode 
	from pluext
)t4 
on t1.plunmbr = t4.plunmbr 
$conditions$  
group by t1.date,t1.plunmbr, t4.internalcode, t1.pludesc, t1.deptnmbr, t2.deptdesc, t3.price1, t4.price1 order by t1.date,t1.plunmbr
',
'com.+.ReportTool.doTablesBusinessDatesResolution_HandleQuery','MISC[insBatch]',
'ROWS[date:title=i18n:bdate,showUp=1,showDown=1;plunmbr:title=i18n:Article Number;internalcode:title=i18n:Item Internal Code;pludesc:title=i18n:Article desc,showUp=1,showDown=1,bold=1;deptnmbr:title=i18n:Dept Number;deptdesc:title=i18n:Dept Name;brutamtsold:title=i18n:amtsold;liqamtsold:title=i18n:netamtsold;amtdisc:title=i18n:amtdisc;qtysold:title=i18n:qtysold;void:title=,w=0.5;margenvalue:title=i18n:amtmargin;margenperc:title=i18n:percmargin;],HEADER[brutamtsold:replace=sold_head,df=i18n,span=6,bold=1,align=center;liqamtsold:hidden=1;amtdisc:hidden=1;qtysold:hidden=1;margenvalue:hidden=1;margenperc:hidden=1;],FOOTER[brutamtsold:;liqamtsold:;amtdisc:;qtysold:;margenvalue:;margenperc:;]',
'SELECT 0'
);


insert into formular_commonpt 		
(id,req_par,rp_ext,title,order_by)		
values
(
':CSalesByItemAndDate:','t4.internalcode','ge','i18n:From Item Internal Code',18
);
insert into formular_commonpt 
(id,req_par,rp_ext,title,order_by)		
values
(
':CSalesByItemAndDate:','t4.internalcode','le','i18n:To Item Internal Code',20
);

/**
*****************************************************************************************************************************************
	report CSalesByDocTypeBetweenDates
**/
--### DELETE ###

delete from menu_commonpt where id='CSalesByDocTypeBetweenDates';

--### INSERT ###
			
insert into menu_commonpt
(
	id, title,parent_id,
	pariddocu,order_by,access1,
access2,href,
query,user_methods,html_format,
common_format,count_query
)
values
(
'CSalesByDocTypeBetweenDates','i18n:Sales By Doc Type Between Dates','salesReports'
,'salesReports',60050,'commonpt',
'reportspt','formular.html',
'select g.empname,g.bdate,g.invnmbr,g.doctype,g.docdesc,h.amnt,g.custnmbr,g.custname from '
||'( select e.empname,e.bdate,e.termnmbr,e.transnmbr,f.invnmbr,f.doctype,f.docdesc,e.custnmbr,e.custname '
||'from(select d.cshrnmbr,d.empname,d.bdate,d.termnmbr,d.transnmbr,d.custnmbr,customer.custname	from '
||'(select c.cshrnmbr,employee.empname,c.bdate,c.termnmbr,c.transnmbr,c.custnmbr from (	'
|| ' select a.cshrnmbr,a.bdate,a.termnmbr,a.transnmbr,a.custnmbr from( '
|| ' select bdate, termnmbr, transnmbr,cshrnmbr, custnmbr from ejheader where functype=0 and saletype in (0,5) '
|| ' )a inner join ( select bdate, termnmbr, transnmbr from ejtrailer )b on a.bdate=b.bdate and a.termnmbr=b.termnmbr '
|| ' and a.transnmbr=b.transnmbr )c inner join employee	on c.cshrnmbr=employee.empnmbr )d inner join customer '
|| ' on d.custnmbr=customer.custnmbr )e inner join ( select inv.bdate,inv.termnmbr,inv.transnmbr,inv.invnmbr,inv.doctype,doctypes.docdesc '
|| ' from(select * from invoices)inv inner join (select 1 as doctype,''Vendas a Dinheiro'' as docdesc union '
|| ' select 2 as doctype,''Factura'' as docdesc union select 3 as doctype,''Devolução de Venda a Dinheiro'' as docdesc union '
|| ' select 4 as doctype,''Nota de crédito'' as docdesc) doctypes on inv.doctype=doctypes.doctype )f on '
|| ' e.bdate=f.bdate and e.termnmbr=f.termnmbr and e.transnmbr=f.transnmbr )g inner join ( select bdate,termnmbr,transnmbr,amnt from ejsubtotal)h '
|| ' on g.bdate=h.bdate and g.termnmbr=h.termnmbr and g.transnmbr=h.transnmbr $conditions$ order by g.empname,g.bdate,g.invnmbr,g.doctype,g.docdesc,h.amnt,g.custnmbr,g.custname '
,
'com.+.ReportTool.doTablesBusinessDatesResolution_HandleQuery',
'MISC[insBatch]',	
'ROWS[empname:title=i18n:Employee,showUp=1,showDown=1;bdate:title=i18n:bdate,showUp=1,showDown=1;'
|| 'invnmbr:title=i18n:Doc number,showUp=1,showDown=1;docdesc:title=i18n:Doc type,showUp=1,showDown=1;doctype:hidden=1;'
|| 'amnt:title=i18n:Doc value,df=price,w=20,align=right,unit=1,showUp=1,showDown=1;'
|| 'custnmbr:title=i18n:Customer Number,showUp=1,showDown=1;'
|| 'custname:title=i18n:customer,showUp=1,showDown=1;'
|| ']',
'select 0'	
);		



---### DELETE ###
	
delete from formular_commonpt where id=':CSalesByDocTypeBetweenDates:';

---### INSERT ###

insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesByDocTypeBetweenDates:','g.doctype','eq',
'i18n:Doc type',10,'SELECT',
null,'select t.doctype,t.docdesc from(select 1 as doctype,''Vendas a Dinheiro'' as docdesc union '
||  'select 2 as doctype,''Factura'' as docdesc union '
|| ' select 3 as doctype,''Devolução de Venda a Dinheiro'' as docdesc union  '
|| ' select 4 as doctype,''Nota de crédito'' as docdesc)t order by t.doctype',null
);



insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesByDocTypeBetweenDates:','g.bdate','ge',
'i18n:From business date',20,'SELECT',
null,'select bdate from businessdate where deleted is null and datetime_eod is not null order by bdate',null
);
insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesByDocTypeBetweenDates:','g.bdate','le',
'i18n:To business date',30,'SELECT',
null,'select bdate from businessdate where deleted is null and datetime_eod is not null order by bdate',null
);
insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesByDocTypeBetweenDates:','button',null,
null,80,'button:submit',
'i18n:createReport',null,'c2'
)
;
insert into formular_commonpt
		(
		id,req_par, order_by
		)
		values
		(
		':CSalesByDocTypeBetweenDates:','<hr>',55
		);
insert into formular_commonpt
		(
		id,req_par,title, order_by,input_value
		
		)
		values
		(
		':CSalesByDocTypeBetweenDates:','rf','i18n:reportformat', 60,'html'
		);
insert into formular_commonpt
		(
		id,req_par, order_by,input_value
		
		)
		values
		(
		':CSalesByDocTypeBetweenDates:','limit', 70,'200'
		);

/**
*****************************************************************************************************************************************
	report CSalesReceiptsPerClientBetweenDates
**/

--### DELETE ###

delete from menu_commonpt where id='CSalesReceiptsPerClientBetweenDates';

--### INSERT ###
			
insert into menu_commonpt
(
	id, title,parent_id,
	pariddocu,order_by,access1,
access2,href,
query,user_methods,html_format,
common_format,count_query
)
values
(
'CSalesReceiptsPerClientBetweenDates','i18n:Sales Receipts by client between dates','salesReports'
,'salesReports',60016,'commonpt',
'reportspt','formular.html',
'select d.bdate,d.custnmbr,d.custname,sum(e.amnt)as sales from '
||'(select c.bdate,c.termnmbr,c.transnmbr,c.custnmbr,customer.custname '
|| 'from( select a.bdate,a.termnmbr,a.transnmbr,a.custnmbr from	(select bdate, termnmbr, transnmbr, custnmbr from ejheader $conditions$ and functype=0 and saletype in (0,5))a ' 
|| 'inner join (select bdate, termnmbr, transnmbr from ejtrailer)b on a.bdate=b.bdate and a.termnmbr=b.termnmbr and a.transnmbr=b.transnmbr )c '
|| 'inner join customer on c.custnmbr=customer.custnmbr )d inner join(select bdate,termnmbr,transnmbr,amnt from ejsubtotal)e '
|| 'on d.bdate=e.bdate and d.termnmbr=e.termnmbr and d.transnmbr=e.transnmbr  '
|| ' group by d.bdate,d.custname,d.custnmbr order by d.bdate,d.custname'
,
'com.+.ReportTool.doTablesBusinessDatesResolution_HandleQuery',
'MISC[insBatch]',
'MISC[columnTotal=sales,location=footer;],ROWS[bdate:group-on=1,title=i18n:bdate,showUp=1,showDown=1;custnmbr:hidden=0,title=i18n:Customer Number,showUp=1,showDown=1;'
|| 'custname:title=i18n:customer,showUp=1,showDown=1;sales:SUM=1,title=i18n:grossamnt,df=price,w=20,align=right,unit=1;],'
|| 'FOOTER[bdate:align=center,bold=1;custnmbr:align=center,hidden=0,bold=1;custname:align=right,bold=1;sales:align=right,bold=1,df=price;]',
'select 0'	
);		



---### DELETE ###
	
delete from formular_commonpt where id=':CSalesReceiptsPerClientBetweenDates:';

---### INSERT ###
insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesReceiptsPerClientBetweenDates:','custnmbr','ge',
'i18n:From customer',10,null,
null,null,null
);

insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesReceiptsPerClientBetweenDates:','custnmbr','le',
'i18n:To customer',15,null,
null,null,null
);

insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesReceiptsPerClientBetweenDates:','bdate','ge',
'i18n:From business date',30,'SELECT',
null,'select bdate from businessdate where deleted is null and datetime_eod is not null order by bdate',null
);
insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesReceiptsPerClientBetweenDates:','bdate','le',
'i18n:To business date',40,'SELECT',
null,'select bdate from businessdate where deleted is null and datetime_eod is not null order by bdate',null
);
insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesReceiptsPerClientBetweenDates:','button',null,
null,80,'button:submit',
'i18n:createReport',null,'c2'
)
;

insert into formular_commonpt
		(
		id,req_par, order_by
		)
		values
		(
		':CSalesReceiptsPerClientBetweenDates:','<hr>',55);
insert into formular_commonpt
		(
		id,req_par,title, order_by,input_value
		
		)
		values
		(
		':CSalesReceiptsPerClientBetweenDates:','rf','i18n:reportformat', 60,'html'
		);
insert into formular_commonpt
		(
		id,req_par, order_by,input_value
		
		)
		values
		(
		':CSalesReceiptsPerClientBetweenDates:','limit', 70,'200'
		);

/**
*****************************************************************************************************************************************
	report CSalesPerClientAndPluBetweenDates
**/
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
