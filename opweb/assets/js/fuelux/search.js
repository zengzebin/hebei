!function(e){"function"==typeof define&&define.amd?define(["jquery"],e):"object"==typeof exports?module.exports=e(require("jquery")):e(jQuery)}(function(e){var t=e.fn.search,s=function(t,s){this.$element=e(t),this.$repeater=e(t).closest(".repeater"),this.options=e.extend({},e.fn.search.defaults,s),"true"===this.$element.attr("data-searchOnKeyPress")&&(this.options.searchOnKeyPress=!0),this.$button=this.$element.find("button"),this.$input=this.$element.find("input"),this.$icon=this.$element.find(".glyphicon, .fuelux-icon"),this.$button.on("click.fu.search",e.proxy(this.buttonclicked,this)),this.$input.on("keyup.fu.search",e.proxy(this.keypress,this)),this.$repeater.length>0&&this.$repeater.on("rendered.fu.repeater",e.proxy(this.clearPending,this)),this.activeSearch=""};s.prototype={constructor:s,destroy:function(){return this.$element.remove(),this.$element.find("input").each(function(){e(this).attr("value",e(this).val())}),this.$element[0].outerHTML},search:function(e){this.$icon.hasClass("glyphicon")&&this.$icon.removeClass("glyphicon-search").addClass("glyphicon-remove"),this.$icon.hasClass("fuelux-icon")&&this.$icon.removeClass("fuelux-icon-search").addClass("fuelux-icon-remove"),this.activeSearch=e,this.$element.addClass("searched pending"),this.$element.trigger("searched.fu.search",e)},clear:function(){this.$icon.hasClass("glyphicon")&&this.$icon.removeClass("glyphicon-remove").addClass("glyphicon-search"),this.$icon.hasClass("fuelux-icon")&&this.$icon.removeClass("fuelux-icon-remove").addClass("fuelux-icon-search"),this.$element.hasClass("pending")&&this.$element.trigger("canceled.fu.search"),this.activeSearch="",this.$input.val(""),this.$element.trigger("cleared.fu.search"),this.$element.removeClass("searched pending")},clearPending:function(){this.$element.removeClass("pending")},action:function(){var e=this.$input.val();e&&e.length>0?this.search(e):this.clear()},buttonclicked:function(t){t.preventDefault(),e(t.currentTarget).is(".disabled, :disabled")||(this.$element.hasClass("pending")||this.$element.hasClass("searched")?this.clear():this.action())},keypress:function(e){13===e.which?(e.preventDefault(),this.action()):9===e.which?e.preventDefault():27===e.which?(e.preventDefault(),this.clear()):this.options.searchOnKeyPress&&this.action()},disable:function(){this.$element.addClass("disabled"),this.$input.attr("disabled","disabled"),this.options.allowCancel||this.$button.addClass("disabled")},enable:function(){this.$element.removeClass("disabled"),this.$input.removeAttr("disabled"),this.$button.removeClass("disabled")}},e.fn.search=function(t){var i,n=Array.prototype.slice.call(arguments,1),a=this.each(function(){var a=e(this),h=a.data("fu.search"),r="object"==typeof t&&t;h||a.data("fu.search",h=new s(this,r)),"string"==typeof t&&(i=h[t].apply(h,n))});return void 0===i?a:i},e.fn.search.defaults={clearOnEmpty:!1,searchOnKeyPress:!1,allowCancel:!1},e.fn.search.Constructor=s,e.fn.search.noConflict=function(){return e.fn.search=t,this},e(document).on("mousedown.fu.search.data-api","[data-initialize=search]",function(t){var s=e(t.target).closest(".search");s.data("fu.search")||s.search(s.data())}),e(function(){e("[data-initialize=search]").each(function(){var t=e(this);t.data("fu.search")||t.search(t.data())})})});