function fnHome()
{
	document.getElementById("homepageform").action = "/PMMS/restful/toHome";
	document.getElementById("homepageform").submit();
}

function fnLogout()
{
	document.getElementById("homepageform").action = "/PMMS/restful/toLogout";
	document.getElementById("homepageform").submit();
}