!function(e){"function"==typeof define&&define.amd?define(["jquery","fuelux/loader"],e):"object"==typeof exports?module.exports=e(require("jquery"),require("./loader")):e(jQuery)}(function(e){var t=e.fn.infinitescroll,n=function(t,n){this.$element=e(t),this.$element.addClass("infinitescroll"),this.options=e.extend({},e.fn.infinitescroll.defaults,n),this.curScrollTop=this.$element.scrollTop(),this.curPercentage=this.getPercentage(),this.fetchingData=!1,this.$element.on("scroll.fu.infinitescroll",e.proxy(this.onScroll,this)),this.onScroll()};n.prototype={constructor:n,destroy:function(){return this.$element.remove(),this.$element.empty(),this.$element[0].outerHTML},disable:function(){this.$element.off("scroll.fu.infinitescroll")},enable:function(){this.$element.on("scroll.fu.infinitescroll",e.proxy(this.onScroll,this))},end:function(t){var n=e('<div class="infinitescroll-end"></div>');t?n.append(t):n.append("---------"),this.$element.append(n),this.disable()},getPercentage:function(){var e="border-box"===this.$element.css("box-sizing")?this.$element.outerHeight():this.$element.height(),t=this.$element.get(0).scrollHeight;return t>e?e/(t-this.curScrollTop)*100:0},fetchData:function(t){var n,i=e('<div class="infinitescroll-load"></div>'),o=this,l=function(){var t={percentage:o.curPercentage,scrollTop:o.curScrollTop},n=e('<div class="loader"></div>');i.append(n),n.loader(),o.options.dataSource&&o.options.dataSource(t,function(e){var t;i.remove(),e.content&&o.$element.append(e.content),e.end&&(t=!0!==e.end?e.end:void 0,o.end(t)),o.fetchingData=!1})};this.fetchingData=!0,this.$element.append(i),this.options.hybrid&&!0!==t?(n=e('<button type="button" class="btn btn-primary"></button>'),"object"==typeof this.options.hybrid?n.append(this.options.hybrid.label):n.append('<span class="glyphicon glyphicon-repeat"></span>'),n.on("click.fu.infinitescroll",function(){n.remove(),l()}),i.append(n)):l()},onScroll:function(e){this.curScrollTop=this.$element.scrollTop(),this.curPercentage=this.getPercentage(),!this.fetchingData&&this.curPercentage>=this.options.percentage&&this.fetchData()}},e.fn.infinitescroll=function(t){var i,o=Array.prototype.slice.call(arguments,1),l=this.each(function(){var l=e(this),r=l.data("fu.infinitescroll"),s="object"==typeof t&&t;r||l.data("fu.infinitescroll",r=new n(this,s)),"string"==typeof t&&(i=r[t].apply(r,o))});return void 0===i?l:i},e.fn.infinitescroll.defaults={dataSource:null,hybrid:!1,percentage:95},e.fn.infinitescroll.Constructor=n,e.fn.infinitescroll.noConflict=function(){return e.fn.infinitescroll=t,this}});