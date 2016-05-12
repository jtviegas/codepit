select d.bdate,d.custnmbr,d.custname,sum(e.amnt)as sales	
from
(
	select c.bdate,c.termnmbr,c.transnmbr,c.custnmbr,customer.custname 
	from
	(
		select a.bdate,a.termnmbr,a.transnmbr,a.custnmbr 
		from 
		(
			select bdate, termnmbr, transnmbr, custnmbr from ejheader where functype=0 and saletype in (0,5)
		)a 
		inner join 
		(
			select bdate, termnmbr, transnmbr from ejtrailer
		)b
		on a.bdate=b.bdate and a.termnmbr=b.termnmbr and a.transnmbr=b.transnmbr
	)c
	inner join
	customer
	on c.custnmbr=customer.custnmbr
)d
inner join
(
	select bdate,termnmbr,transnmbr,amnt from ejsubtotal 
)e
on d.bdate=e.bdate and d.termnmbr=e.termnmbr and d.transnmbr=e.transnmbr
$conditions$	
group by d.bdate,d.custname,d.custnmbr
order by d.bdate,d.custname


