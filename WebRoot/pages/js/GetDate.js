document.write("<DOWNLOAD ID=\"Calendardwn\" STYLE=\"behavior:url(#default#download)\" />");
function onCalendarDone(src)
{
	oCalendarPopup.document.write(src);
}

function showCalendarPopup(theDataObj,showTime)
{
	var iHeight=190;
	if (showTime)
	{
		iHeight=215;
		oCalendarPopup.document.getElementById("timePanel").style.display="block";
		oCalendarPopup.document.getElementById("oTime").checked=true;
	}

	oCalendarPopup.show(0, 20, 177, iHeight-3, theDataObj);
}

var oCalendarPopup = window.createPopup();
var CalendarIsDown=false;
var theDataObj;
//原来的查找目录路径的,jsp用的
function getDate0(DateType)
{
	var url,top,left,style,DataValue,temp;
	top=window.event.screenY+12;
	left=window.event.screenX-100;
	temp="";

	theDataObj=window.document.getElementById(DateType);
	if (theDataObj==null)
		theDataObj=window.document.getElementsByName(DateType)[0];
	if (theDataObj==null)
		return;
	if (theDataObj.className=="ReadOnly")
		return;
	Url="/ftp/pages/Calendar.html";
	
	if (!CalendarIsDown)
	{
		Calendardwn.startDownload(Url,onCalendarDone);
		CalendarIsDown=true;
	}
	showCalendarPopup(theDataObj,arguments[2]==null?false:arguments[2]);
}

//修改成servlet用的时间获取
function getDate(DateType,iRelatePub)
{
	var url,top,left,style,DataValue,temp;
	top=window.event.screenY+12;
	left=window.event.screenX-100;

	theDataObj=window.document.getElementById(DateType);
	if (theDataObj==null)
		theDataObj=window.document.getElementsByName(DateType)[0];
	if (theDataObj==null)
		return;
	if (theDataObj.className=="ReadOnly")
		return;
	Url=iRelatePub+"/script/Calendar.html";
	if (!CalendarIsDown)
	{
		Calendardwn.startDownload(Url,onCalendarDone);
		CalendarIsDown=true;
	}
	showCalendarPopup(theDataObj,arguments[2]==null?false:arguments[2]);
}
