
select g.empname,g.bdate,g.invnmbr,g.doctype,g.docdesc,h.amnt,g.custnmbr,g.custname
from
(
		select e.empname,e.bdate,e.termnmbr,e.transnmbr,f.invnmbr,f.doctype,f.docdesc,e.custnmbr,e.custname
		from
		(	
			select d.cshrnmbr,d.empname,d.bdate,d.termnmbr,d.transnmbr,d.custnmbr,customer.custname
			from
			(					
				select c.cshrnmbr,employee.empname,c.bdate,c.termnmbr,c.transnmbr,c.custnmbr
				from
				(	
					select a.cshrnmbr,a.bdate,a.termnmbr,a.transnmbr,a.custnmbr
					from 
					(
					-- functype must be sales_mode
					-- saletypes standard sale and recalled sale
						select bdate, termnmbr, transnmbr,cshrnmbr, custnmbr from ejheader where functype=0 and saletype in (0,5)
					)a 
					inner join 
					(
						select bdate, termnmbr, transnmbr from ejtrailer
					)b
					on a.bdate=b.bdate and a.termnmbr=b.termnmbr and a.transnmbr=b.transnmbr
				)c
				inner join
				employee
				on c.cshrnmbr=employee.empnmbr
			)d
			inner join
			customer
			on d.custnmbr=customer.custnmbr
		)e
		inner join
		(
			select inv.bdate,inv.termnmbr,inv.transnmbr,inv.invnmbr,inv.doctype,doctypes.docdesc
			from
			(
				select * from invoices
			)inv
			inner join
			(
				select 1 as doctype,'Vendas a Dinheiro' as docdesc union 
				select 2 as doctype,'Factura' as docdesc union 
				select 3 as doctype,'Devolução de Venda a Dinheiro' as docdesc union 
				select 4 as doctype,'Nota de crédito' as docdesc
			) doctypes
			on inv.doctype=doctypes.doctype
		)f	
		on
		e.bdate=f.bdate and e.termnmbr=f.termnmbr and e.transnmbr=f.transnmbr
)g
inner join
(
	select bdate,termnmbr,transnmbr,amnt from ejsubtotal 
)h
on g.bdate=h.bdate and g.termnmbr=h.termnmbr and g.transnmbr=h.transnmbr
order by g.empname,g.bdate,g.invnmbr,g.doctype,g.docdesc,h.amnt,g.custnmbr,g.custname



