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


