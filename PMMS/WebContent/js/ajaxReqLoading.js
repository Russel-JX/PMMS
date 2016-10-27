require(['jquery','ge-bootstrap'], function() {
    $(function($){
        if(isEmpty($("#modalPanel"))){
			//alert("generate...........");
			generateModalPanel();
		}
		$(document).ajaxStart(function(){
			//alert("ajax request start........");
			showInProgressPanel();
		}).ajaxStop(function(){
			//alert("ajax request stop.........");
			hideInProgressPanel();
		});
    });
	
	function generateModalPanel(){	
	   var modalPanelHtml = '<div id="modalPanel" class="spin">'
	   							+ '<span class="spinner" style="height: 18px;"></span>'
			   			    	+ '<div>Please wait while we process your request...</div>' 
	  					    +'</div>'
	 if (isEmpty($("#modalPanel"))) {
		$("body").append(modalPanelHtml);
	 }
}
	
    function showInProgressPanel(){
        $("#modalPanel").show();
    }
    
    function hideInProgressPanel(){
        $("#modalPanel").hide();
    }
	
    function isEmpty(obj){
        if (obj == "" || obj == null || obj.length == 0) {
            return true;
        }
        if (typeof obj == "undefined") {
            return true;
        }
        return false;
    }
});