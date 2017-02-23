/******************自定义校验规则********************/

Ext.apply(Ext.form.field.VTypes, {
	mobile : function(v) {    // 手机号
		var mobileReg = /^1\d{10}$/;
		return mobileReg.test(v);
	},
	mobileText : '手机号格式不正确',
	
	mobile : function(v) {    // 固话
		var phoneReg = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
		return phoneReg.test(v);
	},
	mobileText : '固话格式不正确',
	
	mobileOrPhone : function(v) {    // 手机号或固话
		var mobileReg = /^1\d{10}$/;
		var phoneReg = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
		
		return mobileReg.test(v) || phoneReg.test(v);
	},
	mobileOrPhoneText : '手机号或固话格式不正确'
});

