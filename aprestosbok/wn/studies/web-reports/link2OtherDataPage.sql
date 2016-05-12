drop table custom_test_link2OtherDataPage;
create table custom_test_link2OtherDataPage as select deptnmbr as dept,qtysold as qty,0 as ok from dptmove2008082800 limit 13;
alter table custom_test_link2OtherDataPage owner to tplinux;
GRANT ALL ON TABLE custom_test_link2OtherDataPage TO tplinux;
GRANT SELECT ON TABLE custom_test_link2OtherDataPage TO webapp;
select oid,dept,qty,ok from custom_test_link2OtherDataPage where oid > 11317188 order by oid limit 20;

delete from menu_commonpt where id='CLink2OtherDataPage';


insert into menu_commonpt		
(
id, title,parent_id,
pariddocu,order_by,access1,
access2,href,
form_js,html_format
--,query
--,
--common_format,count_query
)
values	
(
'CLink2OtherDataPage','Link to other data page','root'
,'root',70000,'commonpt',
'reportspt','formular.html',
'<include-js>link2OtherDataPage.js</include-js>',
'MISC[insBatch]'
--,'select selaccu,gendesc from itemizer where used = 1'
--,
--''
--,'select 0'
);	

delete from formular_commonpt where id=':CLink2OtherDataPage:';

/*
insert into formular_commonpt(
	id,req_par,show,order_by,
	input_value						
)values(
	':CLink2OtherDataPage:','number','hidden',1,
	'select count(*) from itemizer where used = 1'
);

*/

insert into formular_commonpt(		
	id,req_par,order_by,show,input_value
)values(	
	':CLink2OtherDataPage:','lower_oid',2,'text','com.+.formular'
);
insert into formular_commonpt(		
	id,req_par,order_by,input_type,input_value  --,format
)values(	
	':CLink2OtherDataPage:','higher_oid',4,'text','com.+.formular' -- ,'r0:l4'
);

insert into formular_commonpt(		
	id,req_par,order_by,show,input_value
)values(	
	':CLink2OtherDataPage:','lower_oid_displayed',5,'text','com.+.formular'
);


insert into formular_commonpt(		
	id,req_par,order_by,show,input_value
)values(	
	':CLink2OtherDataPage:','higher_oid_displayed',7,'text','com.+.formular'
);

insert into formular_commonpt(		
	id,req_par,order_by,show,input_value
)values(	
	':CLink2OtherDataPage:','num_oids_displayed',8,'text','com.+.formular'
);

insert into formular_commonpt(		
	id,req_par,order_by,show,input_value
)values(	
	':CLink2OtherDataPage:','direction',9,'text','1'
);
		
insert into formular_commonpt(
	id, req_par,  order_by, input_value					
)values(
	':CLink2OtherDataPage:','format', 10,':10:75:75:75'
);
			
insert into formular_commonpt(	
	id, req_par, order_by, input_type, input_value, format--, show						
)values(':CLink2OtherDataPage:','oid{4,$num_oids_displayed$}',15,'text','com.+.formular','l0:l1'--,'hidden'				
);

insert into formular_commonpt(
	id,req_par,order_by,input_type,input_value	,format
)values(			
	':CLink2OtherDataPage:','dept',20,'text','com.+.formular','l0:l1'			
);	

insert into formular_commonpt(
	id,req_par,order_by,input_type,input_value,format
)values
(		
	':CLink2OtherDataPage:','qty',25,'text','com.+.formular','r0:l1'			
);

insert into formular_commonpt(
	id,req_par,order_by,input_type,input_value,format
)values
(		
	':CLink2OtherDataPage:','ok',30,'CHECKBOX','com.+.formular','r0:l1'			
);	
		
insert into formular_commonpt(		
	id,req_par,order_by,input_value		
)values(	
	':CLink2OtherDataPage:','format',35,'::'
);

insert into formular_commonpt(
	id, req_par,  order_by, input_value					
)values(
	':CLink2OtherDataPage:','format', 36,':75:75'
);

insert into formular_commonpt(		
	id,req_par,order_by,input_type, input_value,format,fjs,show
)values(	
	':CLink2OtherDataPage:','previous',37,'button','<','l1', 'prev','com.+.formular' -- , '<onClick>javascript:nextPage();</onClick>'   --,'form:CLink2OtherDataPage'
);

insert into formular_commonpt(		
	id,req_par,order_by,input_type, input_value,format,fjs,show
)values(	
	':CLink2OtherDataPage:','next',40,'button','>','r1', 'next', 'com.+.formular' -- , '<onClick>javascript:nextPage();</onClick>'   --,'form:CLink2OtherDataPage'
);
insert into formular_commonpt(		
	id,req_par,order_by,input_value		
)values(	
	':CLink2OtherDataPage:','format',45,'::'
);
	

--select fjs from formular_commonpt where fjs is not null
--select selaccu,round,code,gendesc from itemizer where used = 1