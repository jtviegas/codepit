---------------------------------------------------------------------------------
	
	



	select t.bdate, t.custnmbr,t.custname,t.plunmbr,t.pludesc,t.deptnmbr,t.deptdesc,
		sum(t.qtySold) as qtysold, sum(t.amtSold) as amtsold,
		 sum(t.sumamtdisc) as amtdisc,sum(t.netamtsold) as netamtsold, 
		sum(t.marginval) as amtmargin
		,case	when sum(t.netamtsold)<>0 then
			((sum(t.marginval)/sum(t.netamtsold))*100)::int8 
			when sum(t.netamtsold)=0 and sum(t.amtsoldcost)>0 then
				(-1*100)::int8
			else 
				(0)::int8
			end as percmargin 
	from 
	(
		select g.bdate,g.custnmbr,g.custname,g.deptnmbr,g.deptdesc,g.plunmbr,g.pludesc,g.qtySold,g.amtSold,g.sumamtdisc,
			(g.buyprice::float * g.qtySold::float)::int8 as amtsoldcost,
			(g.amtSold::float-g.sumamtdisc::float)::int8 as netamtsold,
			case when g.amtSold=0 
				then 0 
				when g.qtySold=0
				then 0
				else ((g.amtSold::float-g.sumamtdisc::float)-(g.buyprice::float * g.qtySold::float))::int8
			end as marginval
		from 
		(
				select f.bdate,f.termnmbr,f.transnmbr,f.custnmbr,f.custname,f.deptnmbr,f.deptdesc,f.plunmbr,f.pludesc, 
					case when pluext.price1 is null then 0 else pluext.price1 end as buyprice,f.qtySold,f.amtSold,f.sumamtdisc 
				from 
				(
					select e.bdate,e.termnmbr,e.transnmbr,e.custnmbr,e.custname,e.deptnmbr,dept.deptdesc,e.plunmbr,e.pludesc,e.qtySold,e.amtSold,e.sumamtdisc 
					from 
					(
						select q.bdate,q.termnmbr,q.transnmbr,q.custnmbr,customer.custname,q.deptnmbr,q.plunmbr,q.pludesc,q.qtySold,q.amtSold,q.sumamtdisc
						from 
						(
							select c.bdate,c.termnmbr,c.transnmbr,c.custnmbr,d.deptnmbr,d.plunmbr,d.pludesc,d.qtySold,d.amtSold,d.sumamtdisc
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
							(
								select bdate, termnmbr, transnmbr, plunmbr, pludesc, deptnmbr, price1, qtySold, amtSold, sumamtdisc from ejitem
							)d
							on c.bdate=d.bdate and c.termnmbr=d.termnmbr and c.transnmbr=d.transnmbr
						)q 
						inner join 
						customer on q.custnmbr=customer.custnmbr
					)e 
					inner join 
					dept 
					on e.deptnmbr=dept.deptnmbr
				)f 
				left join 
				pluext 
				on f.plunmbr=pluext.plunmbr
		)g
	)t
 --CONDITIONS
	group by		
	t.bdate, t.custnmbr, t.custname,t.plunmbr, t.pludesc, t.deptnmbr,t.deptdesc
	order by
	t.bdate, t.custnmbr, t.custname,t.plunmbr, t.pludesc, t.deptnmbr,t.deptdesc


