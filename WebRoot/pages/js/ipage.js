var MAX_INT = Number.MAX_VALUE;
var DEFAULT_PAGESIZE = 10;
var gIpageMaxCount = 0;			//ȫ�ֱ���, �������ֵڼ���table

//���ȴ���div, Ȼ������ҳ�ӳ���
function ipage(table) {
	var ipageCount = table.ipageCount;
	if (!ipageCount) {
		gIpageMaxCount++;
		ipageCount = table.ipageCount = gIpageMaxCount;
		var pagediv = document.createElement('<div style=margin-top:5px>');			//��table��5�����صļ��
		pagediv.id = 'ipage_div_' + gIpageMaxCount;
		pagediv.pageSize = DEFAULT_PAGESIZE;
		pagediv.table = table;
		insertAfter(pagediv, table);		//��pagediv��Ϊtable��nextSibling;
		_ipage(gIpageMaxCount, 1, DEFAULT_PAGESIZE);
	} else {
		_ipage(ipageCount);
	}
}

//����ipageʱ, �贫��table, ipageCountֵӦΪ��, ֮���ҳʱ, ipageCountָ�ڼ���table, pageΪ�ڼ�ҳ, table����
//�����ipageCount��ָ�ڼ�����ҳ, ����һ��ҳ�����ж����ҳ���
function _ipage(ipageCount, page, pageSize) {			
	var pagediv = document.getElementById('ipage_div_' + ipageCount);
	var pageSize = pagediv.pageSize = pageSize || pagediv.pageSize;
	var pagenum = parseInt(page) || page || 1;
	var begin = (pagenum-1) * pageSize;				//ҳ����1��ʼ
	var end = (pagenum) * pageSize;
	//alert(begin + '\t'+ end);

	var table = pagediv.table;
	var rows = table.rows;
	var recordtotal = 0;
	var length = rows.length;
	for(var i = 1; i < length; i++) {				//��ͷ�������ҳ
		if (rows[i].isDisabled) {
			rows[i].style.display = 'none';
		} else {
			recordtotal++;
			rows[i].style.display = (begin < recordtotal && recordtotal <= end) ? 'block' : 'none';
		}
	}
	var pagetotal = Math.ceil(recordtotal / pageSize);
	
	var html = '';			//�� += ʱ���� 15ms ����, ��array.joinʱ����...
	if (recordtotal == 0) {
		html += '<table class=page><tr><td class="nodata">û��������ѯ�ļ�¼</td></tr></table>';
	} else if (false && recordtotal < 10) {
		//����ʾ...
	} else {
		html += '<table class=page><tr>';
		html += '<td align=left>';
		html += '&nbsp;ÿҳ��ʾ ';
		html += '<select onchange=javascript:_ipage('+ipageCount+',1,this.value)>';
		var maxs = [10, 15, 20, 25, 50, 100];
		for(var i = 0; i < maxs.length; i++) {
			if (maxs[i] == pageSize) {
				html += '<option value=' + maxs[i] + ' selected>' + maxs[i] + '</option>';
			} else {
				html += '<option value=' + maxs[i] + '>' + maxs[i] + '</option>';
			}
		}
		html += '<option value='+MAX_INT+(MAX_INT==pageSize?' selected':'')+'>ȫ��</option>';
		html += '</select>';
		html += ' ����¼'
		html += '</td>';
		
		html += '<td align=right>';
		html += '��' + pagenum + 'ҳ/��'+pagetotal+'ҳ ��'+recordtotal+'����¼' + ' ';
		html += '<a href=javascript:_ipage('+ipageCount+',1)>��ҳ</a>' + ' ';
		if (pagenum > 1) {
			html += '<a href=javascript:_ipage('+ipageCount+','+(pagenum-1)+')>��һҳ</a>' + ' ';
		}
		if (pagenum < pagetotal) {
			html += '<a href=javascript:_ipage('+ipageCount+','+(pagenum+1)+')>��һҳ</a>' + ' ';
		}
		html += '<a href=javascript:_ipage('+ipageCount+','+(pagetotal)+')>βҳ</a>' + ' ';
		html += '��ת��:';
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
	//table�ͻ��˷�ҳ		Ҳ����дΪ $("table.ipage").each(ipage);
	ipage(document.getElementById('tbColor'));
});

