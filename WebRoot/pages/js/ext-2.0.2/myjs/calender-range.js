Ext.onReady(function(){
	 Ext.QuickTips.init();
	    // 渲染日历框
	    var startDate = new Ext.form.DateField({
	        name: 'startdt',
	        id: 'startdt',
	        applyTo: 'startDate',
	        width: 150,
	        format:'Ymd',
	        vtype: 'daterange',
	        timePicker: true,
	        endDateField: 'enddt' 
	    });
	    var endDate = new Ext.form.DateField({
	        name: 'enddt',
	        id: 'enddt',
	        applyTo: 'endDate',
	        width: 150,
	        format:'Ymd',
	        vtype: 'daterange',
	        timePicker: true,
	        startDateField: 'startdt'
	    });
});
//日期区间面板 开始日期和结束日期范围的控制
	Ext.apply(Ext.form.VTypes, {
  daterange: function(val, field) {
    var date = field.parseDate(val);
    
    // We need to force the picker to update values to recaluate the disabled dates display
    var dispUpd = function(picker) {
      var ad = picker.activeDate;
      picker.activeDate = null;
      picker.update(ad);
    };
    if (field.startDateField) {
      var sd = Ext.getCmp(field.startDateField);
      sd.maxValue = date;
      if (sd.menu && sd.menu.picker) {
        sd.menu.picker.maxDate = date;
        dispUpd(sd.menu.picker);
      }
    } else if (field.endDateField) {
      var ed = Ext.getCmp(field.endDateField);
      ed.minValue = date;
      if (ed.menu && ed.menu.picker) {
        ed.menu.picker.minDate = date;
        dispUpd(ed.menu.picker);
      }
    }
    return true;
  }
});

	
