/**
 * 仅适用于调入、调出、拆入、拆出、正回购、逆回购、同业存放、存放同业、代付九个模块
 */
/**
 * 根据起止日期计算期限或天数
 * @param num
 * @return
 */
function CalDays(num){
	var date1=document.getElementById("trtjDqr").value;
	var date2=document.getElementById("tradeDate").value;
	if(date1==""||date2==""){
		return;
	}
	var array1=date1.split("-");
	var array2=date2.split("-");
	var begin=new Date(array1[1]+"-"+array1[2]+"-"+array1[0]);
	var end=new Date(array2[1]+"-"+array2[2]+"-"+array2[0]);
	var days=parseInt(Math.abs(begin-end)/1000/3600/24);
	if(document.getElementById("finanDays")!=null)
		document.getElementById("finanDays").value=days;
	else
		document.getElementById("finanTerm").value=days;
	CalIntrest(num);
}
/**
 * 根据期限、利率、金额计算利息
 * @param num
 * @return
 */
function CalIntrest(num){
	var days;
	if(document.getElementById("finanDays")!=null)
		days=document.getElementById("finanDays").value;
	else
		days=document.getElementById("finanTerm").value;
	var rate=document.getElementById("finanRate").value;
	var amt=document.getElementById("finanAmt").value;
	var intr=document.getElementById("finanIntrest");
	if(days==""||rate==""||amt==""){
		return;
	}
	var result=days*amt*rate/100/num;
	var str=result.toString().replace(/^(\d+\.\d{1})\d*$/, "$1 ");
	intr.value=parseFloat(str);
}