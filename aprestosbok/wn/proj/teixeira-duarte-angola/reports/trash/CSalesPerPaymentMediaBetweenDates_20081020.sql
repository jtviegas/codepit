

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