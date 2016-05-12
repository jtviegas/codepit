select * from formular_commonpt where id like ':pricecheck:' order by order_by;

select * from menu_commonpt where id='pricecheck' union select * from menu_commonpt where id='CLink2OtherDataPage';
		
select * from menu_commonpt where id like '%Cpricecheckall%';

select req_par,input_values from datatypes_commonpt where req_par like '%plunmbr%'

select * from datatypes_commonpt where req_par like '%format%'

select * from formular_commonpt where input_type like '%button%';

select min(t.oid),max(t.oid),count(t.oid) from (select oid from custom_test_link2OtherDataPage where oid > 15409702 order by oid limit 5)t