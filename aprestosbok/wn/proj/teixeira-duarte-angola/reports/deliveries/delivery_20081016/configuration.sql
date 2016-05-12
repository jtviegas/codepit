--select
select * from configtpl where subapp='commonpt';

-- delete
delete from configtpl where subapp='commonpt' and configkey in ('TablesWithBusinessDates');

--insert
insert into configtpl(configkey,configvalue,subapp) 
values
('TablesWithBusinessDates','ejheader,ejtrailer,ejitem,ejsubtotal,invoices','commonpt');

