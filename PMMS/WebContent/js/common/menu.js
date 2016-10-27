$('document').ready(function()
{
	toggleDropdownMenu();
	/*Code to handle if null being passed to document.getElementById*/
	/*if(navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
*/
        document._oldGetElementById = document.getElementById;
        document.getElementById = function(id) {

            if(id === undefined || id === null || id.trim() === ''||id === 'undefined') 
            {
            	return undefined;
}
            return document._oldGetElementById(id);
        };
    }
/*
}*/
);

function toggleDropdownMenu()
{
	$('.dropdown').hover(
							function(){
										$(this).find('.dropdown-menu').first().stop(true, true).delay(20).slideDown();
										$(this).toggleClass('active');
									  },
							function() {
										 $(this).find('.dropdown-menu').first().stop(true, true).delay(20).slideUp();
										 $(this).children().find('.dropdown-menu').slideUp();
										 $(this).toggleClass('active');
										 $(this).removeClass('open');
									  }
						);
}
