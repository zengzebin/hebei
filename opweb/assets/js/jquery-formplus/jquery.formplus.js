!function(t){t.fn.formData=function(e){if(void 0===e){var i={},a=this.serializeArray();this.serialize();return t(a).each(function(){i[this.name]?t.isArray(i[this.name])?i[this.name].push(this.value):i[this.name]=[i[this.name],this.value]:i[this.name]=this.value}),i}var r,s,n,d,l;for(r in e)s=e[r],t(this).find("[name='"+r+"'],[name='"+r+"[]']").each(function(){if(n=t(this)[0].tagName,d=t(this).attr("type"),"INPUT"==n)if("radio"==d)t(this).attr("checked",t(this).val()==s);else if("checkbox"==d){l=s.split(",");for(var e=0;e<l.length;e++)if(t(this).val()==l[e]){t(this).attr("checked",!0);break}}else t(this).val(s);else"SELECT"==n?t(this).val(s).trigger("change"):"TEXTAREA"==n&&t(this).val(s)})},t.fn.formDisable=function(e){var i=t(this);if(e){var a="disable";i.data("formStatus",a),i.find(":text").attr("disabled","true"),i.find("textarea").attr("disabled","true"),i.find("select").attr("disabled","true").trigger("attr.update"),i.find(":radio").attr("disabled","true"),i.find(":checkbox").attr("disabled","true")}else a="enable",i.data("formStatus",a),i.find(":text").removeAttr("disabled"),i.find("textarea").removeAttr("disabled"),i.find("select").removeAttr("disabled").trigger("attr.update"),i.find(":radio").removeAttr("disabled"),i.find(":checkbox").removeAttr("disabled")},t.fn.selectValue=function(){return t(this).find("option:selected").attr("value")},t.fn.selectText=function(){var e=t(this).find("option:selected");return""==(e.attr("value")||"")?"":e.text()}}(jQuery);