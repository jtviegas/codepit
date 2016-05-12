--select * from menu_commonpt where id='pricecheck';
--select * from formular_commonpt where id like ':pricecheck:' order by order_by;
	
--update pricecheck set pricechecked='0' where oid='15483232';
--update pricecheck set priceignored='0'
--select pludesc,pluflags from pricecheck where priceignored=0 and pricechecked=0;
--update pricecheck set pricechecked=0,priceignored=0;
/*************************just testing*/
--select min(t.oid),max(t.oid),count(t.oid) from(select oid from pricecheck)t;

--select * from translations;
--select oid,plunmbr,pludesc,qtyunit,price1 from pricecheck order by oid limit 20;

/**************************************************************************************/



/**************************************************************************
			configurations
***************************************************************************/
delete from configtpl where configkey='pricecheckPageRows' and subapp='commonpt';
insert into configtpl(configkey,configvalue,subapp)values('pricecheckPageRows','20','commonpt');



/**************************************************************************
			formular
***************************************************************************/
delete from formular_commonpt where id=':pricecheck:';

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','format',null,null,501,
null,':50:50::',null,null
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','records','i18n:Records',null,503,
'text','com.+.formular',null,'l1:l1'
);
insert into formular_commonpt
(
id,req_par,rp_ext,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','checkAll',null,null,'com.+.formular',505,
'button','check all','<onClick>checkAllPrices();</onClick>','r1'
);
insert into formular_commonpt
(
id,req_par,rp_ext,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','ignoreAll',null,null,'com.+.formular',507,
'button','ignore all','<onClick>ignoreAllPrices();</onClick>','r1'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','<hr>',null,null,508,
null,null,null,null
);



insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','format',null,null,509,
null,':50:50:50:50:50:50',null,null
);


insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','maxOid','max_oid','hidden',511,
'text','com.+.formular',null,'l1:l1'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','minOid','min_oid','hidden',515,
'text','com.+.formular',null,'l1:l1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','pageRecords','page_records','hidden',520,
'text','com.+.formular',null,'l1:l1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','pageMaxOid','page_max_oid','hidden',525,
'text','com.+.formular',null,'l1:l1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','pageMinOid','page_min_oid','hidden',530,
'text','com.+.formular',null,'l1:l1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','direction',null,'hidden',532,
'text','com.+.formular',null,'l0:l0'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','format',null,null,535,
null,':50:100:150:100:60:60:60:60:60',null,null
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','oidtitle',null,null,537,
'text','oid',null,'c1'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','plunmbrtitle',null,null,540,
'text','i18n:Item Number',null,'c1'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','pludesctitle',null,null,545,
'text','i18n:Item Description',null,'c1'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','qtyunittitle',null,null,550,
'text','i18n:Qty Unit',null,'c1'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','actualpricetitle',null,null,555,
'text','i18n:Actual Price',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','newpricetitle',null,null,560,
'text','i18n:New Price',null,'c1'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','pricecheckedtitle',null,null,565,
'text','i18n:Validated Price',null,'c1'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','priceignoredtitle',null,null,570,
'text','i18n:Ignored Price',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','shelftitle',null,null,575,
'text','i18n:pluflag27',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','format',null,null,580,
null,':',null,null
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','<hr>',null,null,580,
null,null,null,null
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','format',null,null,581,
null,':50:100:150:100:60:60:60:60:60',null,null
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','oid{9,$pageRecords$}',null,null,597,
'text','com.+.formular',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','plunmbr',null,null,598,
'text','com.+.formular',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','pludesc',null,null,599,
'text:maxlength=25','com.+.formular',null,'c1'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','unity',null,null,600,
'text','com.+.formular',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','actualprice',null,null,605,
'text','com.+.formular',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','newprice',null,null,610,
'text','com.+.formular',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','checked',null,null,615,
'CHECKBOX','com.+.formular',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','ignored',null,null,616,
'CHECKBOX','com.+.formular',null,'c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','printlabel',null,null,617,
'CHECKBOX','com.+.formular',null,'c1'
);




insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','void3',null,null,618,
'text',null,null,'c4'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','<hr>',null,null,619,
null,null,null,'c5'
);




insert into formular_commonpt
(
id,req_par,title,show,order_by,		
input_type,input_value,fjs,format
)
values
(
':pricecheck:','void',null,null,620,
'text',null,null,'r4'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,		
input_type,input_value,fjs,format
)
values
(
':pricecheck:','void2',null,null,621,
'text','seleção de coluna->',null,'r2'
);

insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','allpricecheck',null,null,622,
'CHECKBOX',null,'<onClick>selectAllCheck();</onClick>','c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','allpriceignore',null,null,623,
'CHECKBOX',null,'<onClick>selectAllIgnore();</onClick>','c1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','allshelftitle',null,null,624,
'CHECKBOX',null,'<onClick>selectAllShelfTitle();</onClick>','c1'
);

















insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','format',null,null,630,
null,':',null,null
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','<hr>',null,null,635,
null,null,null,null
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','format',null,null,640,
null,':150:::150',null,null
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','previous',null,'com.+.formular',645,
'button','<','prev','l1'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','save',null,'com.+.formular',647,
'button','save','form:pricecheck','c2'
);
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck:','next',null,'com.+.formular',650,
'button','>','next','r1'
);







/*
delete from menu_commonpt where id='pricecheck_validate_all';
insert into menu_commonpt(
id,title,parent_id,pariddocu,
order_by,hidden,access1,access2,
href
)values(
'pricecheck_validate_all','price global validation','articles','articles',
'1.3','Y','commonpt','pricecheck',
'formular.html'
);

delete from formular_commonpt where id=':pricecheck_validate_all:';
insert into formular_commonpt
(
id,req_par,title,show,order_by,
input_type,input_value,fjs,format
)
values
(
':pricecheck_validate_all:','redirectClass',null,null,10,
'text','com.+.formular',null,null
);


*/
