require([ 'jquery','datagrids','ge-bootstrap','datepicker'], function() {
	$(function() {
		require(['sp.baseInfo'],function(baseInfo){
			baseInfo.init();
		});
	});
	//点击备件信息(tab006Nav)
	$("#tab006Nav").bind('click',function(){
		require(['sp.baseInfo'],function(baseInfo){
			baseInfo.init();
		});
	});
	//小于安全库存的备件(tab007Nav)
	$("#tab007Nav").bind('click',function(){
		require(['sp.safetyInfo'],function(safetyInfo){
			safetyInfo.init();
		});
	});
	//小于安全库存的备件(tab008Nav)
	$("#tab008Nav").bind('click',function(){
		require(['sp.record'],function(record){
			record.init();
		});
	});
});
