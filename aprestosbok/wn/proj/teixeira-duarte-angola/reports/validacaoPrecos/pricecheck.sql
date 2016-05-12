

select min(t.oid),max(t.oid),count(t.oid) from(
select t1.oid, t1.plunmbr , t1.pludesc,	(case when t3.translation is NULL then '' else t3.translation end) as qtyunitdesc,
	(case when t2.price1 is NULL then 0.0 else t2.price1 end) as current_price, t1.price1 as new_price, 
	t1.pricechecked, t1.priceignored, (case when t2.pluflags is NULL then t1.pluflags else t2.pluflags end) as pluflags 
from pricecheck t1 left join plu t2 on (t1.plunmbr=t2.plunmbr)
	left join translations t3 on t1.qtyunit=t3.id and t3.file='store.clt.QTYUNIT,A' and t3.locale='pt'
where pricechecked=0 and priceignored=0 order by t1.oid)t 



select t1.oid, t1.plunmbr , t1.pludesc,	(case when t3.translation is NULL then '' else t3.translation end) as qtyunitdesc,
	(case when t2.price1 is NULL then 0.0 else t2.price1 end) as current_price, t1.price1 as new_price, 
	t1.pricechecked, t1.priceignored, (case when t2.pluflags is NULL then t1.pluflags else t2.pluflags end) as pluflags 
from pricecheck t1 left join plu t2 on (t1.plunmbr=t2.plunmbr)
	left join translations t3 on t1.qtyunit=t3.id and t3.file='store.clt.QTYUNIT,A' and t3.locale='pt'
where pricechecked=0 and priceignored=0 order by t1.oid limit 20


select t1.oid, t1.plunmbr, t1.pludesc, 
(case when t3.translation is NULL then '' else t3.translation end) as qtyunitdesc,
(case when t2.price1 is NULL then 0.0 else t2.price1 end) as current_price, t1.price1 as new_price,
t1.pricechecked, t1.priceignored, (case when t2.pluflags is NULL then t1.pluflags else t2.pluflags end) as pluflags  
from pricecheck t1 left join plu t2 on (t1.plunmbr=t2.plunmbr)  left join translations t3 on t1.qtyunit=t3.id and 
t3.file='store.clt.QTYUNIT,A' and t3.locale='pt' where pricechecked=0 and priceignored=0 order by t1.oid limit 20