function fPopUpCalendarDlg(ctrlobj)
{
	showx = event.screenX - event.offsetX - 4 - 210 ; // + deltaX;
	showy = event.screenY - event.offsetY + 18; // + deltaY;
	newWINwidth = 210 + 4 + 18;
  retval = window.showModalDialog("/personnel/personnel/CalendarDlg.jsp", "", "dialogWidth:220px; dialogHeight:200px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;");    
	if( retval != null ){
		ctrlobj.value = retval;
	}else{
		//alert("canceled");
	}
}
function fPopUpCalendarTimeDlg(ctrlobj)
{
	showx = event.screenX - event.offsetX - 4 - 210 ; // + deltaX;
	showy = event.screenY - event.offsetY + 18; // + deltaY;
	newWINwidth = 210 + 4 + 18;
	
    retval = window.showModalDialog("/js/CalendarTimeDlg.jsp", "", "dialogWidth:220px; dialogHeight:200px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;");    
	if( retval != null ){
		ctrlobj.value = retval;
	}else{
		//alert("canceled");
	}
}
function fPopUpCalendarDlgDoc(ctrlobj,j)
{
	showx = event.screenX - event.offsetX - 4 - 210 ; // + deltaX;
	showy = event.screenY - event.offsetY + 18; // + deltaY;
	newWINwidth = 210 + 4 + 18;
  retval = window.showModalDialog("/personnel/personnel/CalendarDlg.jsp", "", "dialogWidth:220px; dialogHeight:200px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;");    
	if( retval != null ){
		alert(j);
		var ctrlobjdoc=document.getElementsByName(ctrlobj);
		ctrlobjdoc[j].value = retval;
	}else{
		//alert("canceled");
	}
}
