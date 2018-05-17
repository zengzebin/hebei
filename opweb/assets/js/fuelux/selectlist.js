!function(e){"function"==typeof define&&define.amd?define(["jquery"],e):"object"==typeof exports?module.exports=e(require("jquery")):e(jQuery)}(function(e){var t=e.fn.selectlist,i=function(t,i){this.$element=e(t),this.options=e.extend({},e.fn.selectlist.defaults,i),this.$button=this.$element.find(".btn.dropdown-toggle"),this.$hiddenField=this.$element.find(".hidden-field"),this.$label=this.$element.find(".selected-label"),this.$dropdownMenu=this.$element.find(".dropdown-menu"),this.$element.on("click.fu.selectlist",".dropdown-menu a",e.proxy(this.itemClicked,this)),this.setDefaultSelection(),"auto"!==i.resize&&"auto"!==this.$element.attr("data-resize")||this.resize(),0===this.$dropdownMenu.children("li").length&&(this.disable(),this.doSelect(e(this.options.emptyLabelHTML))),this.$element.on("shown.bs.dropdown",function(){var t=e(this);e(document).on("keypress.fu.selectlist",function(i){var s=String.fromCharCode(i.which);t.find("li").each(function(t,i){if(e(i).text().charAt(0).toLowerCase()===s)return e(i).children("a").focus(),!1})})}),this.$element.on("hide.bs.dropdown",function(){e(document).off("keypress.fu.selectlist")})};i.prototype={constructor:i,destroy:function(){return this.$element.remove(),this.$element[0].outerHTML},doSelect:function(t){var i;this.$selectedItem=i=t,this.$hiddenField.val(this.$selectedItem.attr("data-value")),this.$label.html(e(this.$selectedItem.children()[0]).html()),this.$element.find("li").each(function(){i.is(e(this))?e(this).attr("data-selected",!0):e(this).removeData("selected").removeAttr("data-selected")})},itemClicked:function(t){this.$element.trigger("clicked.fu.selectlist",this.$selectedItem),t.preventDefault(),e(t.currentTarget).parent("li").is(".disabled, :disabled")||(e(t.target).parent().is(this.$selectedItem)||this.itemChanged(t),this.$element.find(".dropdown-toggle").focus())},itemChanged:function(t){this.doSelect(e(t.target).closest("li"));var i=this.selectedItem();this.$element.trigger("changed.fu.selectlist",i)},resize:function(){var t=0,i=0,s=e("<div/>").addClass("selectlist-sizer");Boolean(e(document).find("html").hasClass("fuelux"))?e(document.body).append(s):e(".fuelux:first").append(s),s.append(this.$element.clone()),this.$element.find("a").each(function(){s.find(".selected-label").text(e(this).text()),i=s.find(".selectlist").outerWidth(),(i+=s.find(".sr-only").outerWidth())>t&&(t=i)}),t<=1||(this.$button.css("width",t),this.$dropdownMenu.css("width",t),s.remove())},selectedItem:function(){var t=this.$selectedItem.text();return e.extend({text:t},this.$selectedItem.data())},selectByText:function(t){var i=e([]);this.$element.find("li").each(function(){if((this.textContent||this.innerText||e(this).text()||"").toLowerCase()===(t||"").toLowerCase())return i=e(this),!1}),this.doSelect(i)},selectByValue:function(e){var t='li[data-value="'+e+'"]';this.selectBySelector(t)},selectByIndex:function(e){var t="li:eq("+e+")";this.selectBySelector(t)},selectBySelector:function(e){var t=this.$element.find(e);this.doSelect(t)},setDefaultSelection:function(){var e=this.$element.find("li[data-selected=true]").eq(0);0===e.length&&(e=this.$element.find("li").has("a").eq(0)),this.doSelect(e)},enable:function(){this.$element.removeClass("disabled"),this.$button.removeClass("disabled")},disable:function(){this.$element.addClass("disabled"),this.$button.addClass("disabled")}},i.prototype.getValue=i.prototype.selectedItem,e.fn.selectlist=function(t){var s,n=Array.prototype.slice.call(arguments,1),l=this.each(function(){var l=e(this),d=l.data("fu.selectlist"),o="object"==typeof t&&t;d||l.data("fu.selectlist",d=new i(this,o)),"string"==typeof t&&(s=d[t].apply(d,n))});return void 0===s?l:s},e.fn.selectlist.defaults={emptyLabelHTML:'<li data-value=""><a href="#">No items</a></li>'},e.fn.selectlist.Constructor=i,e.fn.selectlist.noConflict=function(){return e.fn.selectlist=t,this},e(document).on("mousedown.fu.selectlist.data-api","[data-initialize=selectlist]",function(t){var i=e(t.target).closest(".selectlist");i.data("fu.selectlist")||i.selectlist(i.data())}),e(function(){e("[data-initialize=selectlist]").each(function(){var t=e(this);t.data("fu.selectlist")||t.selectlist(t.data())})})});