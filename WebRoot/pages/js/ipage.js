var MAX_INT = Number.MAX_VALUE;
var DEFAULT_PAGESIZE = 10;
var gIpageMaxCount = 0;			//全局变量, 用于区分第几个table

//首先创建div, 然后进入分页子程序
function ipage(table) {
	var ipageCount = table.ipageCount;
	if (!ipageCount) {
		gIpageMaxCount++;
		ipageCount = table.ipageCount = gIpageMaxCount;
		var pagediv = document.createElement('<div style=margin-top:5px>');			//与table有5上像素的间隔
		pagediv.id = 'ipage_div_' + gIpageMaxCount;
		pagediv.pageSize = DEFAULT_PAGESIZE;
		pagediv.table = table;
		insertAfter(pagediv, table);		//把pagediv作为table的nextSibling;
		_ipage(gIpageMaxCount, 1, DEFAULT_PAGESIZE);
	} else {
		_ipage(ipageCount);
	}
}

//创建ipage时, 需传入table, ipageCount值应为空, 之后分页时, ipageCount指第几个table, page为第几页, table忽略
//这里的ipageCount是指第几个分页, 用于一张页面里有多个分页表格
function _ipage(ipageCount, page, pageSize) {			
	var pagediv = document.getElementById('ipage_div_' + ipageCount);
	var pageSize = pagediv.pageSize = pageSize || pagediv.pageSize;
	var pagenum = parseInt(page) || page || 1;
	var begin = (pagenum-1) * pageSize;				//页数从1开始
	var end = (pagenum) * pageSize;
	//alert(begin + '\t'+ end);

	var table = pagediv.table;
	var rows = table.rows;
	var recordtotal = 0;
	var length = rows.length;
	for(var i = 1; i < length; i++) {				//表头不计入分页
		if (rows[i].isDisabled) {
			rows[i].style.display = 'none';
		} else {
			recordtotal++;
			rows[i].style.display = (begin < recordtotal && recordtotal <= end) ? 'block' : 'none';
		}
	}
	var pagetotal = Math.ceil(recordtotal / pageSize);
	
	var html = '';			//用 += 时间在 15ms 左右, 与array.join时间差不多...
	if (recordtotal == 0) {
		html += '<table class=page><tr><td class="nodata">没有您所查询的记录</td></tr></table>';
	} else if (false && recordtotal < 10) {
		//不显示...
	} else {
		html += '<table class=page><tr>';
		html += '<td align=left>';
		html += '&nbsp;每页显示 ';
		html += '<select onchange=javascript:_ipage('+ipageCount+',1,this.value)>';
		var maxs = [10, 15, 20, 25, 50, 100];
		for(var i = 0; i < maxs.length; i++) {
			if (maxs[i] == pageSize) {
				html += '<option value=' + maxs[i] + ' selected>' + maxs[i] + '</option>';
			} else {
				html += '<option value=' + maxs[i] + '>' + maxs[i] + '</option>';
			}
		}
		html += '<option value='+MAX_INT+(MAX_INT==pageSize?' selected':'')+'>全部</option>';
		html += '</select>';
		html += ' 条记录'
		html += '</td>';
		
		html += '<td align=right>';
		html += '第' + pagenum + '页/共'+pagetotal+'页 共'+recordtotal+'条记录' + ' ';
		html += '<a href=javascript:_ipage('+ipageCount+',1)>首页</a>' + ' ';
		if (pagenum > 1) {
			html += '<a href=javascript:_ipage('+ipageCount+','+(pagenum-1)+')>上一页</a>' + ' ';
		}
		if (pagenum < pagetotal) {
			html += '<a href=javascript:_ipage('+ipageCount+','+(pagenum+1)+')>下一页</a>' + ' ';
		}
		html += '<a href=javascript:_ipage('+ipageCount+','+(pagetotal)+')>尾页</a>' + ' ';
		html += '跳转到:';
		html += '<select name=pageNo onchange=_ipage('+ipageCount+',this.value)>' + ' ';
		for(var i = 1; i <= pagetotal; i++) {
			if (i == pagenum) {
				html += '<option value=' + i + ' selected>' + i + '</option>';
			} else {
				html += '<option value=' + i + '>' + i + '</option>';
			}
		}
		html += '</select>';
		html += '</td>';
		html += '</tr></table>';
	}
	
	//alert(html);
	if (false && pageSize == MAX_INT) {
		pagediv.innerHTML = '';
	} else {
		pagediv.innerHTML = html;
	}
}

function insertAfter(newElement,targetElement) {
	var parent = targetElement.parentNode;
	var nextSibling = targetElement.nextSibling;
	if (nextSibling == null) {
		parent.appendChild(newElement);
	} else {
		parent.insertBefore(newElement,nextSibling);
	}
}

window.attachEvent("onload", function() {
	//table客户端分页		也可以写为 $("table.ipage").each(ipage);
	ipage(document.getElementById('tbColor'));
});

