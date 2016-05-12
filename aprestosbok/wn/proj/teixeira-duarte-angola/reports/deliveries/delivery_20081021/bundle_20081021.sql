
/**
*****************************************************************************************************************************************
	configuration values
**/

delete from configtpl where
subapp='commonpt' and configkey='TablesWithBusinessDates';

insert into configtpl(configkey,configvalue,subapp) 
values(
'TablesWithBusinessDates',
'ejheader,ejtrailer,ejitem,ejsubtotal,plumove,dptmove,invoices,ejmedia',
'commonpt'
);


/**
*****************************************************************************************************************************************
	report CSalesPerPaymentMediaBetweenDates
**/

delete from menu_commonpt where id='CSalesPerPaymentMediaBetweenDates';

insert into menu_commonpt
(
id,title,parent_id,pariddocu,order_by,access1,access2,href,query,user_methods,html_format,common_format,count_query
)
values
(
	'CSalesPerPaymentMediaBetweenDates','i18n:Sales per payment media between dates','salesReports','salesReports',60060,'commonpt','reportspt','formular.html',
'

select media_recs.bdate, media_recs.mdesc, media_recs.medcurrnmbr,
	media_recs.currsym,sum(media_recs.amount) as mediaamnt
from
(
select ejm.*, coalesce(allowable_change_deductions.numOfChangeDeduction,0) as numOfAllowableChangeDeductions, 
	coalesce(changes.mediaamnt,0) as change, mca.changeallowed, mca.medcurrnmbr,mca.currsym,
	case 
	when ejm.mediaamnt>0 then 
		case
		when coalesce(changes.mediaamnt,0)>0 then	
			case 
			when changeallowed=1 then
				ejm.mediaamnt-(coalesce(changes.mediaamnt,0)/coalesce(allowable_change_deductions.numOfChangeDeduction,0))
			else
				ejm.mediaamnt
			end
			
		else
			ejm.mediaamnt
		end
	else
		ejm.mediaamnt
	end as amount
from
(
	select *
	from ejmedia
	where saletype in (0,2,3,5) and flag=''00000000''
)ejm
left join
(
	select distinct m.bdate,m.termnmbr,m.transnmbr, count(n.medcurrnmbr) as numOfChangeDeduction
	from
	(
		select * from ejmedia where saletype in (0,2,3,5) and flag=''00000000''
	)m
	inner join
	(
		select medcurrnmbr from media where changeallowed=1
	)n
	on m.mediatype=n.medcurrnmbr
	group by m.bdate,m.termnmbr,m.transnmbr
	order by m.bdate,m.termnmbr,m.transnmbr
)allowable_change_deductions
on ejm.bdate=allowable_change_deductions.bdate and 
ejm.termnmbr=allowable_change_deductions.termnmbr and 
ejm.transnmbr=allowable_change_deductions.transnmbr
left join
(
	select h.bdate,h.termnmbr,h.transnmbr,m.mediaamnt 
	from 
	(select * from ejheader )h
	 inner join 
	(select * from ejmedia where flag=''00100000'')m
	on h.bdate=m.bdate and h.termnmbr=m.termnmbr and h.transnmbr=m.transnmbr
)changes
on ejm.bdate=changes.bdate and 
ejm.termnmbr=changes.termnmbr and 
ejm.transnmbr=changes.transnmbr
inner join
(
	select medcurrnmbr,changeallowed,currsym from media
)mca
on ejm.mediatype=mca.medcurrnmbr
)media_recs
$conditions$
group by media_recs.bdate, media_recs.mdesc,media_recs.medcurrnmbr, 
	media_recs.currsym
order by media_recs.bdate, media_recs.mdesc, media_recs.medcurrnmbr,
	media_recs.currsym
',
'com.+.ReportTool.doTablesBusinessDatesResolution_HandleQuery','MISC[insBatch]',
'MISC[columnTotal=mediaamnt,location=footer;],' ||
'ROWS[bdate:title=i18n:bdate,showUp=1,showDown=1,group-on=1;medcurrnmbr:hidden=1,group-on=1,no-footer=1;'
|| 'mdesc:title=i18n:Payment method,group-on=1,showUp=1,showDown=1;currsym:title=i18n:currency,showUp=1,showDown=1,bold=1,group-on=1,no-footer=1;'
|| 'mediaamnt:title=i18n:value,SUM=1,align=right,df=num3,fd=2;]'
|| ',FOOTER[mediaamnt:bold=1,df=num3,fd=2,align=right;]',
'SELECT 0'
);	

delete from formular_commonpt where id=':CSalesPerPaymentMediaBetweenDates:';


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
		':CSalesPerPaymentMediaBetweenDates:','media_recs.medcurrnmbr','eq',
		'i18n:Payment method',10,
		'SELECT','select medcurrnmbr,medcurrdesc from media where medcurrnmbr<10 order by medcurrnmbr'
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
		':CSalesPerPaymentMediaBetweenDates:','media_recs.bdate','ge',
		'i18n:From business date',20,
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
		':CSalesPerPaymentMediaBetweenDates:','media_recs.bdate','le',
		'i18n:To business date',30,
		'SELECT','select bdate from businessdate where deleted is null and datetime_eod is not null order by bdate'
		);

insert into formular_commonpt
		(
		id,req_par, order_by
		)
		values
		(
		':CSalesPerPaymentMediaBetweenDates:','<hr>',50
		);
insert into formular_commonpt
		(
		id,req_par,title, order_by,input_value
		
		)
		values
		(
		':CSalesPerPaymentMediaBetweenDates:','rf','i18n:reportformat', 60,'html'
		);
insert into formular_commonpt
		(
		id,req_par, order_by,input_value
		
		)
		values
		(
		':CSalesPerPaymentMediaBetweenDates:','limit', 70,'200'
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
		':CSalesPerPaymentMediaBetweenDates:','button',80,
		'button:submit','i18n:createReport','c2'	
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
			|| ' case when sum(t.netamtsold)<>0 then ((sum(t.marginval)/sum(t.netamtsold)))::int8 when sum(t.netamtsold)=0 and sum(t.amtsoldcost)>0 then(-1)::int8 else (0)::int8 end as percmargin ' 
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
			|| 'qtysold:SUM=1,title=i18n:qtysold,align=right,df=num3;amtsold:SUM=1,title=i18n:amtsold,align=right,df=num3,fd=2;'
			|| 'amtdisc:SUM=1,title=i18n:amtdisc,align=right,df=num3,fd=2;' 
			|| 'netamtsold:SUM=1,title=i18n:netamtsold,align=right,df=num3,fd=2;'
			|| 'amtmargin:SUM=1,title=i18n:amtmargin,align=right,df=num3,fd=2;percmargin:title=i18n:percmargin,align=right,df=percent;],'
		|| 'FOOTER[bdate:bold=1,align=right;custname:bold=1,align=right;pludesc:bold=1,align=right;'
			|| 'qtysold:bold=1,df=num3;'
			|| 'amtsold:bold=1,df=num3,fd=2;'
			|| 'amtdisc:bold=1,df=num3,fd=2;'
			|| 'netamtsold:bold=1,df=num3,fd=2;'
			|| 'amtmargin:bold=1,df=num3,fd=2;'
		|| ']'	
		,'select 0','com.+.ReportTool.doTablesBusinessDatesResolution_HandleQuery'
		);


		
/**
*****************************************************************************************************************************************
	report CSalesByDeptAndDate
**/

update menu_commonpt
set query=
'
select t1.date, selpludeptnmbr, deptdesc, sum(qtysold) as qtysold, sum(brutamtsold) as brutamtsold, 
	sum(amtdisc) as amtdisc ,sum(liqamtsold) as liqamtsold
from 
( 
	select bdate as date,deptnmbr as selpludeptnmbr, deptdesc,  sum(amtsold+amtsolddirect-amtrfnd-amtrfnddirect) as brutamtsold, 
	sum(amtsold+amtsolddirect-amtrfnd-amtrfnddirect-amtdptdisc-amtdptdiscdirect) as liqamtsold, 
	sum(qtysold+qtysolddirect-qtyrfnd-qtyrfnddirect) as qtysold, sum(amtdptdisc-amtdptdiscdirect) as amtdisc 
	from dptmove
	group by bdate,deptnmbr, deptdesc 
	having sum(amtsold+amtsolddirect) != 0 or sum(qtysold+qtysolddirect) != 0 or sum(amtrfnd+amtrfnddirect) != 0 
		or sum(qtyrfnd+qtyrfnddirect) != 0 or sum(amtdptdisc+amtdptdiscdirect) != 0 or sum(qtydptdisc+qtydptdiscdirect) != 0 
) as t1 	
$conditions$ group by t1.date,selpludeptnmbr, deptdesc order by t1.date,deptdesc
'
where id='CSalesByDeptAndDate';

update menu_commonpt 
set common_format='ROWS[date:title=i18n:bdate,bold=0,group-on=1,showUp=1,showDown=1;selpludeptnmbr:hidden=1;deptdesc:title=i18n:Dept Name,bold=0;qtysold:title=i18n:qtysold,bold=0,SUM=1,df=num3;'
|| 'brutamtsold:title=i18n:amtsold,bold=0,SUM=1,df=num3,fd=2;amtdisc:title=i18n:amtdisc,bold=0,SUM=1,df=num3,fd=2;liqamtsold:title=i18n:netamtsold,bold=0,SUM=1,df=num3,fd=2;]'
|| ',FOOTER[qtysold:bold=1,df=num3;brutamtsold:bold=1,df=num3,fd=2;amtdisc:bold=1,df=num3,fd=2;liqamtsold:bold=1,df=num3,fd=2;]'
where id='CSalesByDeptAndDate';

update menu_commonpt 
set user_methods='com.+.ReportTool.doTablesBusinessDatesResolution_HandleQuery' 
where id='CSalesByDeptAndDate';