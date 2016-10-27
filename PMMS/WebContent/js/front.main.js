require([ 'jquery','datagrids','ge-bootstrap'], function() {
	//初始化-加载提交故障维修工单界面数据
	$(function() {
		require(['front.tab1'],function(tab1){
			tab1.init();
		});
	});
	//点击tab1，重新加载提交故障维修工单界面数据
	$("#tab01Nav").bind('click',function(){
		//console.log("click event tab1");
		require(['front.tab1'],function(tab1){
			tab1.init();
		});
	});
	//点击tab2,加载故障维修工单处理界面数据
	$("#tab02Nav").bind("click",function(){
		//console.log("click event tab2");
		require(['front.tab2'],function(tab2){
			tab2.init();
		});
	});
	//点击tab3,加载计划保养工单处理界面数据
	$("#tab03Nav").bind("click",function(){
		//console.log("click event tab3");
		require(['front.tab3'],function(tab3){
		tab3.init();
		});
	});
});
