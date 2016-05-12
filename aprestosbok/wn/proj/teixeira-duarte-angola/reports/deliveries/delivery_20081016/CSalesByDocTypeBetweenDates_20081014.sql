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
