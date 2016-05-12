delete from configtpl where
subapp='commonpt' and configkey='TablesWithBusinessDates';

insert into configtpl(configkey,configvalue,subapp) 
values(
'TablesWithBusinessDates',
'ejheader,ejtrailer,ejitem,ejsubtotal,plumove,invoices,ejmedia',
'commonpt'
);