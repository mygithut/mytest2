
/*起始日期-结束日期，日期面板生产js*/
j(function() {
		j("#startDate").datepicker(
			{
				changeMonth: true,
				changeYear: true,
				dateFormat: 'yymmdd',
				showOn: 'button', 
				buttonImage: 'pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
				buttonImageOnly: true,
			    onSelect: function(dateText, inst) {
			    dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
			    j('#endDate').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
		});

		j("#endDate").datepicker(
			{
				changeMonth: true,
				changeYear: true,
				dateFormat: 'yymmdd',
				showOn: 'button', 
				buttonImage: 'pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
				buttonImageOnly: true,
			    onSelect: function(dateText, inst) {
				dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
			    j('#startDate').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
		});
	});