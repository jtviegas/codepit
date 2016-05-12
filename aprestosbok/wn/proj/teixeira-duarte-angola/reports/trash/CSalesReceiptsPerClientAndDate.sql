--### DELETE ###

delete from menu_commonpt where id='CSalesReceiptsPerClientAndDate';

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
'CSalesReceiptsPerClientAndDate','i18n:Sales Receipts by client between dates','salesReports'
,'salesReports',60016,'commonpt',
'reportspt','formular.html',
'
select trx_subtotals.bdate as bdate,sale_trxs_by_customer.customer_id as customer_id,
sale_trxs_by_customer.customer as customer,sum(trx_subtotals.value)as sales
from
	(
		select bdate,termnmbr as pos,transnmbr as trx_id, amnt as value 
		from ejsubtotal 
	)trx_subtotals

	inner join
	(
		select sale_trxs.bdate,sale_trxs.pos,sale_trxs.trx_id,
		sale_trxs.customer_id,customer.custname as customer
		from
			(
				select ejheaders.bdate as bdate,
						ejheaders.pos as pos,ejheaders.trx_id as trx_id	
						, ejheaders.customer_id as customer_id
				from
				(
					select termnmbr as pos,transnmbr as trx_id,
					bdate as bdate, custnmbr as customer_id
					from ejheader 
					where functype=0 
					and saletype in (0,5) 
				)ejheaders

			inner join

				(
					select bdate as bdate, termnmbr as pos, transnmbr as trx_id,
					netsale as netsale,grosssale as grosssale
					from ejtrailer 
				)ejtrailers

			on ejheaders.pos=ejtrailers.pos 
			and ejheaders.trx_id=ejtrailers.trx_id
			and ejheaders.bdate=ejtrailers.bdate
		)sale_trxs
	inner join
		customer
	on
		sale_trxs.customer_id=customer.custnmbr

)sale_trxs_by_customer
on trx_subtotals.bdate=sale_trxs_by_customer.bdate
and trx_subtotals.pos=sale_trxs_by_customer.pos
and trx_subtotals.trx_id=sale_trxs_by_customer.trx_id
$conditions$	
group by trx_subtotals.bdate,sale_trxs_by_customer.customer,sale_trxs_by_customer.customer_id
order by trx_subtotals.bdate,sale_trxs_by_customer.customer'
,
'com.+.ReportTool.businessDateTablesHandleQuery',
'MISC[insBatch]',
'MISC[columnTotal=sales,location=footer;],ROWS[bdate:group-on=1,title=i18n:bdate,showUp=1,showDown=1;customer_id:hidden=0,title=i18n:Customer Number,showUp=1,showDown=1;customer:title=i18n:customer,showUp=1,showDown=1;sales:SUM=1,title=i18n:grossamnt,df=price,w=20,align=right,unit=1;],FOOTER[bdate:align=center,bold=1;customer_id:align=center,hidden=0,bold=1;customer:align=right,bold=1;sales:align=right,bold=1,df=price;]',
'select 0'	
);

--'MISC[columnTotal=sales,location=footer;],ROWS[bdate:group-on=1,title=i18n:bdate;
--customer_id:hidden=0,title=i18n:Customer Number;customer:title=i18n:customer;			
--sales:SUM=1,title=i18n:grossamnt;],
--FOOTER[bdate:align=right,bold=1;customer_id:align=right,hidden=0,;
--customer:align=right,bold=1;sales:align=right,bold=1;]'


---### DELETE ###
	
delete from formular_commonpt where id=':CSalesReceiptsPerClientAndDate:';

---### INSERT ###
insert into formular_commonpt
(
id,req_par,rp_ext,
title,order_by, input_type,	
input_value,input_values,format	
)
values
(
':CSalesReceiptsPerClientAndDate:','sale_trxs_by_customer.customer_id','eq',
'i18n:Customer Number',10,null,
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
':CSalesReceiptsPerClientAndDate:','trx_subtotals.bdate','ge',
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
':CSalesReceiptsPerClientAndDate:','trx_subtotals.bdate','le',
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
':CSalesReceiptsPerClientAndDate:','button',null,
null,50,'button:submit',
'i18n:createReport',null,'c2'
)
;

