/*
select custom_import_transactions2finalize
	('/tmp/CSV', 'custom_transactions2finalize', 'tplinux') ;

select custom_prepare_transactions2finalize
	('custom_transactions2finalize','transactions_being_finalized', 'tplinux', 501);
*/
select custom_finalize_transactions_justheaders
	('transactions_being_finalized','list_stored_notrecalled', 1);
 

select custom_finalize_transactions_othersThanHeaders
	('transactions_being_finalized','list_stored_notrecalled', 1) ;


