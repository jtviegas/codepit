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