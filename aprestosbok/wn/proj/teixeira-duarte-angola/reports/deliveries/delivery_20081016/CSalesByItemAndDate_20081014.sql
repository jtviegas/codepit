
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
