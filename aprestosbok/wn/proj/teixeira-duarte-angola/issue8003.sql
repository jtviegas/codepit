
--select query from menu_reports where id='CashierMediaD'

select t1.typenumber as nOperador, t2.medcurrdesc as formaPagamento, t2.currsym as simboloMoeda, t1.estimated_counter as counterTeorico, 
t1.estimated_base_amount as valorTeoricoLocal, t1.estimated_base_amount + t1.counted_base_amount as valorTeorico, 
t1.counted_base_amount as recolha, t1.float_counter as counterReforço, t1.float_base_amount as reforcoLocal, 
t1.pick_up_counter as counterSangria, t1.pick_up_base_amount as sangriaLocal, t1.pay_in_counter as counterDeposito, 
t1.pay_in_base_amount 	as depositoLocal, t1.pay_out_counter as counterLevantamento, t1.pay_out_base_amount as levantamentoLocal,
t1.start_float_base_amount as reforcoInicialLocal , t1.diff_base_amount as diferencaLocal, coalesce(c.open_base_amt, 0) as aDeclararLocal,   
0 - t1.estimated_base_amount - coalesce(c.open_base_amt, 0) - t1.start_float_amount - t1.diff_base_amount as diferencas
-- 0 - valorTeoricoLocal - aDeclarar - reforcoInicialLocal - diferencaDeclaracao
from 
	payments2008091600 t1 
	left join 
	employee b 
		on t1.typenumber = b.empnmbr 
	left join 
	paymentsremain2008091600 c 
		on t1.typenumber = c.typenumber and t1.medcurrnmbr = c.medcurrnmbr, 
	media t2 
where 
	t1.medcurrflag=t2.medcurrflag and 
	t1.medcurrnmbr=t2.medcurrnmbr and 
	constype='C' and 
	(t1.estimated_amount != 0 or t1.counted_amount != 0 or t1.float_amount != 0 or 
		t1.pick_up_amount != 0 or t1.pay_in_amount != 0 or t1.pay_out_amount != 0
		or t1.start_float_amount != 0 or (c.open_base_amt is not null and c.open_base_amt > 0)) 
	and t1.typenumber=11
order by t1.typenumber, t1.medcurrnmbr;

--select * from payments2008091500 where constype ='C' order by typenumber