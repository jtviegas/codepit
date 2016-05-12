select ta.plunmbr, t4.internalcode, ta.pludesc, ta.deptnmbr, t2.deptdesc,'''' as void, 
	sum(ta.qtysold) as qtysold, sum(ta.brutamtsold) as brutamtsold, 
	sum(ta.liqamtsold) as liqamtsold, sum(ta.amtdisc) as amtdisc, 
	((t3.price1 - t4.price1) * (sum(ta.qtysold) / 1000)) as margenvalue, 
	case when t3.price1=0  then -100::float else (1::float - (t4.price1::float / t3.price1::float))*100::float end as margenperc 
from 
(select plunmbr, pludesc, deptnmbr, (sum(amtsold) - sum(amtrfnd)) as brutamtsold, 
		(sum(amtsold) - sum(amtdptdisc) - sum(amtrfnd)) as liqamtsold, 
		(sum(qtysold) - sum(qtyrfnd)) as qtysold, sum(amtdptdisc) as amtdisc 
	from plumove#allbd# 
	group by plunmbr, pludesc, deptnmbr 
	having sum(amtsold) != 0 or sum(qtysold) != 0 or sum(amtrfnd) != 0 or sum(qtyrfnd) != 0 or 
	sum(amtdptdisc) != 0 or sum(qtydptdisc) != 0 
)ta 
inner join 
(select * from dept)
t2 
on ta.deptnmbr = t2.deptnmbr 
inner join 
(select * from plu)t3 
on ta.plunmbr = t3.plunmbr 
inner join 
(
	select pluext.*, case when pluext.pluextdesc is null then null else pluext.pluextdesc::integer end as internalcode 
	from pluext
)t4 
on ta.plunmbr = t4.plunmbr 
$conditions$  
group by ta.plunmbr, t4.internalcode, ta.pludesc, ta.deptnmbr, t2.deptdesc, t3.price1, t4.price1 order by ta.plunmbr