select * from formular_commonpt where id like '%CSalesByItemAndDate%'
select * from menu_commonpt where id like '%CSalesByItemAndDate%'


select * from formular_reports
where id like '%CashierMediaDeR%'





select * from menu_reports
where id like '%CashierMediaDeR%'


id like '%CashierMediaDeR%'

select * from datatypes_reports where req_par like '%Self%'

select distinct targetnmbr as cshrnmbr1, a.bdate1 as business_date, termnmbr, transnmbr, posgrpnmbr, 
case rectype::varchar 
	when '12' then 
		case banktype::varchar 
			when '-1' then 'sales' 
			when '0' then 'pos_pickup' 
			when '1' then 'pos_float' 
			when '2' then 'pos_payout' 
			when '3' then 'pos_payin' 
			when '4' then 'pos_declr' 
			when '5' then 'pos_declr_nototal' 
			else banktype::varchar 
		end 
	when '81' then 
		case banktype::varchar 
			when '17' then 'batch_declr_corr_bal' 
			when '26' then 'batch_declr' 
			when '30' then 'batch_start_float' 
			when '31' then 'batch_final_diff' 
			else banktype::varchar 
		end 
	when '80' then 
		case banktype::varchar 
			when '7' then 'batch_declr_nototal' 
			when '25' then 'batch_declr_corr' 
			when '30' then 'batch_start_float' 
			when '31' then 'batch_diff' 
			else banktype::varchar 
		end 
	else 
		case banktype::varchar 
			when '-1' then 'sales' 
			when '0' then 'srv_pickup' 
			when '1' then 'srv_float' 
			when '7' then 'srv_declr_nototal'
			when '25' then 'srv_declr_corr' 
			when '17' then 'srv_declr_corr_bal'  
			when '26' then 'srv_declr' 
			when '30' then 'srv_start_float' 
			when '31' then 
				case rectype 
					when 85 then 'srv_diff' 
				else 'srv_final_diff' end 
			else banktype::varchar 
		end 
end as transtype,
medcurrdesc as mdesc, baseamt as mediaamnt, accountnmbr, expdate, mediaamt as altcurrtend, decpos, mediatype, 
case banktype 
	when 0 then '00100000'::bit(8) 
	when 1 then '00000000'::bit(8) 
	when 2 then '00100000'::bit(8) 
	when 3 then '00000000'::bit(8) 
	when 4 then '00100000' 
	when 5 then '00100000' 
	when 7 then '00100000' 
	when 25 then '00100000' 
	when 17 then '00100000' 
	when 26 then '00100000' 
	else flag 
end as flag, 
targetnmbr as thecol, mediatype as medianmbr, seq as datetime, 
case banktype 
	when 5 then (select count(*) from paymentsdetail$bd$ c where c.bdate1 >= $bdate1_ge$ and c.bdate1<= $bdate1_le$ and a.targetnmbr = c.targetnmbr and a.mediatype = c.mediatype and a.period = c.period and c.banktype in (17, 25, 26, 7)) 
	when 30 then (select count(*) from paymentsdetail$bd$ c where c.bdate1 >= $bdate1_ge$ and c.bdate1<= $bdate1_le$ and a.targetnmbr = c.targetnmbr and a.mediatype = c.mediatype and a.seq > c.seq) 
	when 31 then 
		case rectype 
			when 86 then 0 
			when 81 then 0 
			else (select count(*) from paymentsdetail$bd$ c where c.bdate1 >= $bdate1_ge$ and c.bdate1<= $bdate1_le$ and a.targetnmbr = c.targetnmbr and a.mediatype = c.mediatype and a.period = c.period and c.banktype in (17, 25, 26) and a.seq < c.seq) 
		end 
	when 7 then (select count(*) from paymentsdetail$bd$ c where c.bdate1 >= $bdate1_ge$ and c.bdate1<= $bdate1_le$ and a.targetnmbr = c.targetnmbr and a.mediatype = c.mediatype and a.period = c.period and c.banktype in (17, 25, 26)) 
	when 25 then (select count(*) from paymentsdetail$bd$ c where c.bdate1 >= $bdate1_ge$ and c.bdate1<= $bdate1_le$ and a.targetnmbr = c.targetnmbr and a.mediatype = c.mediatype and a.period = c.period and c.banktype in (17, 25, 26) and a.seq < c.seq) 
	ELSE 0 
END
as showflag, d.thegrp, a.period, a.banktype    
from paymentsdetail$bd$ a left join employee b on a.targetnmbr = b.empnmbr 
left join (
	select -1 as banktype2, 0 as thegrp union  
	select 0, 0 union 
	select 1, 0 union 
	select 2, 0 union 
	select 3, 0 union 
	select 30, 0
) as d 
on a.banktype = d.banktype2, 
(
	select distinct period as theperiod from bdatemapperiod
) as tt 
$conditions$ and a.period = tt.theperiod 
UNION
	select distinct g.targetnmbr as cshrnmbr1, g.bdate1 as business_date, -1 as termnmbr, -1 as transnmbr, -1 as posgrpnmbr, 
	'carry_forward' as transtype, 
	(select medcurrdesc from paymentsdetail$bd$ d where g.mediatype = d.mediatype limit 1) as mdesc, c.open_base_amt as mediaamnt, ''::numchar as accountnmbr, 0 as expdate, 
c.open_amt as altcurrtend, g.decpos, g.mediatype, 
B'00000000' as flag, g.targetnmbr as thecol, g.mediatype as medianmbr, -1 as datetime, 0 as showflag, 0 as thegrp, -1 as period, 
a.banktype     
from paymentsremai	n$bd$ c, paymentsdetail$bd$ g left join employee b on g.targetnmbr = b.empnmbr left join (
select -1 as banktype2, -2 as banktype union  
select 0, -2 union 
select 1, -2 union 
select 2, -2 union 
select 3, -2 union 
select 4, -2 union
select 5, -2 union
select 7, -2 union 
select 25, -2 union 
select 17, -2 union 
select 26, -2 union 
select 30, -2 union 
select 31, -2
) as a on g.banktype = a.banktype2, (select -1 as theperiod) as tt, (select 0 as thegrp) as d   
$conditions$ and theperiod = -1 and d.thegrp = 0 and c.typenumber = g.targetnmbr and c.medcurrnmbr = g.mediatype 
order by thecol, medianmbr, datetime