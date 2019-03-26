/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

// Add the additional 'advanced' VTypes

Ext.onReady(function(){

    Ext.QuickTips.init();



		/*
		 * ================  Date Range  =======================
		 */
    // ‰÷»æ»’¿˙øÚ
    var df = new Ext.form.DateField({
        applyTo: 'dr',
        format:'Ymd',
        timePicker: true
    });
});