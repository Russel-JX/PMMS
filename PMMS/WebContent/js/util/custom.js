//My Workflows
$('#workflowtab ul.nav-tabs li a').click(function(){
	var fnnav = $(this).attr('id');
	if(fnnav=='tab01Nav'){var fnnum = 1;}
	if(fnnav=='tab02Nav'){var fnnum = 2;}
	if(fnnav=='tab03Nav'){var fnnum = 3;}
	
	$('#allWorkflow'+fnnum).hide();
	$('#myWorkflow'+fnnum).show();
	$('#workflowClosed'+fnnum).hide();
	$('#myWorkflowNav'+fnnum).addClass("active");
	$('#allWorkflowNav'+fnnum).removeClass("active");
	$('#workflowClosedNav'+fnnum).removeClass("active");

	$('#allsearch'+fnnum).hide();
	$('#search'+fnnum).show();
	$('#searchNav'+fnnum).addClass("active");
	$('#allsearchNav'+fnnum).removeClass("active");
	
	$('#genResults'+fnnum).hide();
	$('#generate'+fnnum).show();
	$('#generateNav'+fnnum).addClass("active");
	$('#genResultsNav'+fnnum).removeClass("active");
});

		var fnnum = "1";		
			$('.secondary-nav > ul li').click(function(){
				var fntext = $(this).attr('id');
				var fnlength = $(this).attr('id').length;
				var fnnum = fntext.charAt(fnlength-1);
				
				//CBM Workflows
				if(fntext=='cbmSearchNav1'){
						$('#cbmSearchResults'+fnnum).hide();
						$('#cbmSearch'+fnnum).show();
						$('#cbmSearchNav'+fnnum).addClass("active");
						$('#cbmSearchResultsNav'+fnnum).removeClass("active");
				}else{
						$('#cbmSearchResults'+fnnum).show();
						$('#cbmSearch'+fnnum).hide();
						$('#cbmSearchNav'+fnnum).removeClass("active");
						$('#cbmSearchResultsNav'+fnnum).addClass("active");
				}			
				
				
				//Reports Starts Secondary Menu
				if((fntext=='generateNav2')||(fntext=='generateNav3')){
						$('#genResults'+fnnum).hide();
						$('#generate'+fnnum).show();
						$('#generateNav'+fnnum).addClass("active");
						$('#genResultsNav'+fnnum).removeClass("active");
				}else{
						$('#genResults'+fnnum).show();
						$('#generate'+fnnum).hide();
						$('#generateNav'+fnnum).removeClass("active");
						$('#genResultsNav'+fnnum).addClass("active");
				}
				
				//My Workflows
				if((fntext=='myWorkflowNav1')||(fntext=='myWorkflowNav2')||(fntext=='myWorkflowNav3')){
						$('#myWorkflow'+fnnum).show();
						$('#allWorkflow'+fnnum).hide();
						$('#workflowClosed'+fnnum).hide();
						$('#myWorkflowNav'+fnnum).addClass("active");
						$('#allWorkflowNav'+fnnum).removeClass("active");
						$('#workflowClosedNav'+fnnum).removeClass("active");
				}else if(fntext=='workflowClosedNav3'){
						$('#allWorkflow'+fnnum).hide();
						$('#myWorkflow'+fnnum).hide();
						$('#workflowClosed'+fnnum).show();
						$('#myWorkflowNav'+fnnum).removeClass("active");
						$('#allWorkflowNav'+fnnum).removeClass("active");
						$('#workflowClosedNav'+fnnum).addClass("active");
				}else{
						$('#allWorkflow'+fnnum).show();
						$('#myWorkflow'+fnnum).hide();
						$('#workflowClosed'+fnnum).hide();
						$('#myWorkflowNav'+fnnum).removeClass("active");
						$('#allWorkflowNav'+fnnum).addClass("active");
						$('#workflowClosedNav'+fnnum).removeClass("active");
				}
				
				//My Workflows
				if((fntext=='searchNav1')||(fntext=='searchNav2')){
						$('#allsearch'+fnnum).hide();
						$('#search'+fnnum).show();
						$('#searchNav'+fnnum).addClass("active");
						$('#allsearchNav'+fnnum).removeClass("active");
				}else{
						$('#allsearch'+fnnum).show();
						$('#search'+fnnum).hide();
						$('#searchNav'+fnnum).removeClass("active");
						$('#allsearchNav'+fnnum).addClass("active");
				}
			});
			
//openworkflow Table Navigator starts
		$(document).ready(function(){
			$(window).load(function () {
					doLayout2();
				});
				
				var spottercount2="0";
				
				$(document).delegate('#genResults2 ul#spotter li', "click",function () {
					var eacVal = $(this).attr("id");
					var spottercount2 = parseInt(eacVal);
					
					var leftPos = $('#genResults2inner').scrollLeft();
					$("#genResults2inner").animate({scrollLeft: (1140*spottercount2)}, 200);
					
					$('#genResults2 ul#spotter li').removeClass("active");
					$(this).addClass("active");
					spottercount2 = spottercount2+1;
				});
				
			$('#genResults2 #rt').click(function(){
			
						var findCount = $('#genResults2 #spotter li').length;
						for(i=0;i<findCount;i++){
							var counter = i+1;
							var foundClass = $('#genResults2 #spotter li').eq(i).attr("class");
							if((foundClass == 'active')&&(i!=findCount-1) ){
							
								$('#genResults2 ul#spotter li').removeClass("active");
								$('#genResults2 #spotter li').eq(counter).addClass("active");	
								var leftPos = $('#genResults2inner').scrollLeft();
								
								$("#genResults2inner").animate({scrollLeft: (1140*counter)}, 200);
								
								return false;
							}
						}
				});
				
				$('#genResults2 #lft').click(function(){
					var findCount = $('#genResults2 #spotter li').length;
						for(i=0;i<findCount;i++){
							var counter = i-1;
							var foundClass = $('#genResults2 #spotter li').eq(i).attr("class");
							if((foundClass == 'active')&&(i!=0) ){
								$('#genResults2 ul#spotter li').removeClass("active");
								$('#genResults2 #spotter li').eq(counter).addClass("active");	
								var leftPos = $('#genResults2inner').scrollLeft();
								$("#genResults2inner").animate({scrollLeft: (1140*counter)}, 200);
								return false
							}
						}						
				});	
		});
		
	
		function doLayout2(totwidth){
		//alert(dolayout);
		totwidth = $('#genResults2table').width();
		$("#genResults2inner").scrollLeft(0);

			//alert(totwidth);
			var eachwidth = (totwidth)/1140+'';		
			var modval = eachwidth % 1;
			var licount = "";
			
			//alert(modval);
			if(modval != 0){
				//alert("if");
				var remdec = eachwidth.charAt(0);
				var outputis = eachwidth[0];
				licount = parseInt(outputis)+1;
			}else if(modval == 0){
				//alert("else");
				licount = eachwidth;
			}
			//alert("licount="+licount);
			var i ="";
			$('#genResults2 ul#spotter').empty();
			for(i=0;i<licount;i++){
				$('#genResults2 ul#spotter').append("<li id="+i+"></li>");
			}
			$('#genResults2 ul#spotter li').eq(0).addClass("active");
		}		
//openworkflow Table Navigator ends


//closedworkflow Table Navigator starts
		$(document).ready(function(){
			$(window).load(function () {
					doLayout3();
				});
				
				var spottercount3="0";
				
				$(document).delegate('#genResults3 ul#spotter li', "click",function () {
					var eacVal = $(this).attr("id");
					var spottercount3 = parseInt(eacVal);
					
					var leftPos = $('#genResults3inner').scrollLeft();
					$("#genResults3inner").animate({scrollLeft: (1140*spottercount3)}, 200);
					
					$('#genResults3 ul#spotter li').removeClass("active");
					$(this).addClass("active");
					spottercount3 = spottercount3+1;
				});
				
			$('#genResults3 #rt').click(function(){
			
						var findCount = $('#genResults3 #spotter li').length;
						for(i=0;i<findCount;i++){
							var counter = i+1;
							var foundClass = $('#genResults3 #spotter li').eq(i).attr("class");
							if((foundClass == 'active')&&(i!=findCount-1) ){
							
								$('#genResults3 ul#spotter li').removeClass("active");
								$('#genResults3 #spotter li').eq(counter).addClass("active");	
								var leftPos = $('#genResults3inner').scrollLeft();
								
								$("#genResults3inner").animate({scrollLeft: (1140*counter)}, 200);
								
								return false;
							}
						}
				});
				
				$('#genResults3 #lft').click(function(){
					var findCount = $('#genResults3 #spotter li').length;
						for(i=0;i<findCount;i++){
							var counter = i-1;
							var foundClass = $('#genResults3 #spotter li').eq(i).attr("class");
							if((foundClass == 'active')&&(i!=0) ){
								$('#genResults3 ul#spotter li').removeClass("active");
								$('#genResults3 #spotter li').eq(counter).addClass("active");	
								var leftPos = $('#genResults3inner').scrollLeft();
								$("#genResults3inner").animate({scrollLeft: (1140*counter)}, 200);
								return false
							}
						}						
				});	
		});
		
	
		function doLayout3(totwidth){
		//alert(dolayout);
		totwidth = $('#genResults3table').width();
		$("#genResults3inner").scrollLeft(0);

			//alert(totwidth);
			var eachwidth = (totwidth)/1140+'';		
			var modval = eachwidth % 1;
			var licount = "";
			
			//alert(modval);
			if(modval != 0){
				//alert("if");
				var remdec = eachwidth.charAt(0);
				var outputis = eachwidth[0];
				licount = parseInt(outputis)+1;
			}else if(modval == 0){
				//alert("else");
				licount = eachwidth;
			}
			//alert("licount="+licount);
			var i ="";
			$('#genResults3 ul#spotter').empty();
			for(i=0;i<licount;i++){
				$('#genResults3 ul#spotter').append("<li id="+i+"></li>");
			}
			$('#genResults3 ul#spotter li').eq(0).addClass("active");
		}		
//closedworkflow Table Navigator ends


//cbmr Table Navigator starts
		$(document).ready(function(){
			$(window).load(function () {
					doLayout4();
				});
				
				var spottercount4="0";
				
				$(document).delegate('#genResults4 ul#spotter li', "click",function () {
					var eacVal = $(this).attr("id");
					var spottercount4 = parseInt(eacVal);
					
					var leftPos = $('#genResults4inner').scrollLeft();
					$("#genResults4inner").animate({scrollLeft: (1140*spottercount4)}, 200);
					
					$('#genResults4 ul#spotter li').removeClass("active");
					$(this).addClass("active");
					spottercount4 = spottercount4+1;
				});
				
			$('#genResults4 #rt').click(function(){
			
						var findCount = $('#genResults4 #spotter li').length;
						for(i=0;i<findCount;i++){
							var counter = i+1;
							var foundClass = $('#genResults4 #spotter li').eq(i).attr("class");
							if((foundClass == 'active')&&(i!=findCount-1) ){
							
								$('#genResults4 ul#spotter li').removeClass("active");
								$('#genResults4 #spotter li').eq(counter).addClass("active");	
								var leftPos = $('#genResults4inner').scrollLeft();
								
								$("#genResults4inner").animate({scrollLeft: (1140*counter)}, 200);
								
								return false;
							}
						}
				});
				
				$('#genResults4 #lft').click(function(){
					var findCount = $('#genResults4 #spotter li').length;
						for(i=0;i<findCount;i++){
							var counter = i-1;
							var foundClass = $('#genResults4 #spotter li').eq(i).attr("class");
							if((foundClass == 'active')&&(i!=0) ){
								$('#genResults4 ul#spotter li').removeClass("active");
								$('#genResults4 #spotter li').eq(counter).addClass("active");	
								var leftPos = $('#genResults4inner').scrollLeft();
								$("#genResults4inner").animate({scrollLeft: (1140*counter)}, 200);
								return false
							}
						}						
				});	
		});
		
	
		function doLayout4(totwidth){
		
		totwidth = $('#genResults4table').width();
		$("#genResults4inner").scrollLeft(0);
		
			var eachwidth = (totwidth)/1140+'';		
			var modval = eachwidth % 1;
			var licount = "";
			
			if(modval != 0){
				//alert("if");
				var remdec = eachwidth.charAt(0);
				var outputis = eachwidth[0];
				licount = parseInt(outputis)+1;
			}else if(modval == 0){
				//alert("else");
				licount = eachwidth;
			}
			//alert("eachwidth="+eachwidth);
			var i ="";
			$('#genResults4 ul#spotter').empty();
			for(i=0;i<licount;i++){
				$('#genResults4 ul#spotter').append("<li id="+i+"></li>");
			}
			$('#genResults4 ul#spotter li').eq(0).addClass("active");
		}
//cbmr Table Navigator ends

			$(".navbar ul[class='nav'] li").click(function(){

$(".navbar ul[class='nav'] li").removeClass("active");
		$(this).addClass("active");

});

$("#editPassword").keyup(function(){
var value=$("#editPassword").val();
if(value=="123"){
$("#editButton").show();
}else{
	$("#editButton").hide();
}

});
$("#select").chosen();

$("#addButton").click(function(){
$("#equipmentInfo").modal("show");
});

$("#editButton").click(function(){
$("#equipmentUpdate").modal("show");
});

$("#datepicker1 input").datepicker();
$("#datepicker2 input").datepicker();

