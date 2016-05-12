
select media_recs.bdate, media_recs.mdesc, 
	media_recs.currsym,sum(media_recs.amount) as mediaamnt
from
(
select ejm.*, coalesce(allowable_change_deductions.numOfChangeDeduction,0) as numOfAllowableChangeDeductions, 
	coalesce(changes.mediaamnt,0) as change, mca.changeallowed, mca.currsym,
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
	where saletype in (0,2,3,5) and flag='00000000'
)ejm
left join
(
	select distinct m.bdate,m.termnmbr,m.transnmbr, count(n.medcurrnmbr) as numOfChangeDeduction
	from
	(
		select * from ejmedia where saletype in (0,2,3,5) and flag='00000000'
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
	from ejheader h 
	inner join 
	(select * from ejmedia where flag='00100000')m
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
group by media_recs.bdate, media_recs.mdesc, 
	media_recs.currsym
order by media_recs.bdate, media_recs.mdesc, 
	media_recs.currsym




select media_recs.bdate, media_recs.mdesc, 
	media_recs.currsym,sum(media_recs.amount) as mediaamnt
from
(
select ejm.*, coalesce(allowable_change_deductions.numOfChangeDeduction,0) as numOfAllowableChangeDeductions, 
	coalesce(changes.mediaamnt,0) as change, mca.changeallowed, mca.currsym,
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
	from ejmedia2008090300
	where saletype in (0,2,3,5) and flag='00000000'
)ejm
left join
(
	select distinct m.bdate,m.termnmbr,m.transnmbr, count(n.medcurrnmbr) as numOfChangeDeduction
	from
	(
		select * from ejmedia2008090300 where saletype in (0,2,3,5) and flag='00000000'
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
	from ejheader2008090300 h 
	inner join 
	(select * from ejmedia2008090300 where flag='00100000')m
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
group by media_recs.bdate, media_recs.mdesc, 
	media_recs.currsym
order by media_recs.bdate, media_recs.mdesc, 
	media_recs.currsym






