!function(i){i.fn.splitter=function(e){return e=e||{},this.each(function(){function t(e){d.outline&&(a=a||x.clone(!1).insertAfter(h)),c.css("-webkit-user-select","none"),x.addClass(d.activeClass),i('<div class="splitterMask"></div>').insertAfter(x),h._posSplit=h[0][d.pxSplit]-e[d.eventPos],i(document).bind("mousemove",s).bind("mouseup",o)}function s(i){var e=h._posSplit+i[d.eventPos];d.outline?(e=Math.max(0,Math.min(e,p._DA-x._DA)),x.css(d.origin,e)):n(e)}function o(e){i("div.splitterMask").remove(),x.removeClass(d.activeClass);var t=h._posSplit+e[d.eventPos];d.outline&&(a.remove(),a=null,n(t)),c.css("-webkit-user-select","text"),i(document).unbind("mousemove",s).unbind("mouseup",o)}function n(i){i=Math.max(h._min,p._DA-u._max,Math.min(i,h._max,p._DA-x._DA-u._min)),x._DA=x[0][d.pxSplit];var e=x.is(":visible")?x._DA-1:0;x.css(d.origin,i-e).css(d.fixed,p._DF),h.css(d.origin,0).css(d.split,i).css(d.fixed,p._DF),u.css(d.origin,i+x._DA-e).css(d.split,p._DA-x._DA-i).css(d.fixed,p._DF),IE_10_AND_BELOW||c.trigger("resize")}function r(i,e){for(var t=0,s=1;s<arguments.length;s++)t+=Math.max(parseInt(i.css(arguments[s]))||0,0);return t}var a,l=(e.splitHorizontal?"h":e.splitVertical?"v":e.type)||"v",d=i.extend({activeClass:"active",pxPerKey:8,tabIndex:0,accessKey:""},{v:{keyLeft:39,keyRight:37,cursor:"col-resize",splitbarClass:"vsplitbar",outlineClass:"voutline",type:"v",eventPos:"pageX",origin:"left",split:"width",pxSplit:"offsetWidth",side1:"Left",side2:"Right",fixed:"height",pxFixed:"offsetHeight",side3:"Top",side4:"Bottom"},h:{keyTop:40,keyBottom:38,cursor:"row-resize",splitbarClass:"hsplitbar",outlineClass:"houtline",type:"h",eventPos:"pageY",origin:"top",split:"height",pxSplit:"offsetHeight",side1:"Top",side2:"Bottom",fixed:"width",pxFixed:"offsetWidth",side3:"Left",side4:"Right"}}[l],e),p=i(this).css({position:"relative"}),c=i(">*",p[0]).css({position:"absolute","z-index":"1","-moz-outline-style":"none"}),h=i(c[0]),u=i(c[1]),_=i('<a href="javascript:void(0)"></a>').attr({accessKey:d.accessKey,tabIndex:d.tabIndex,title:d.splitbarClass}).bind(i.browser.opera?"click":"focus",function(){this.focus(),x.addClass(d.activeClass)}).bind("keydown",function(i){var e=i.which||i.keyCode,t=e==d["key"+d.side1]?1:e==d["key"+d.side2]?-1:0;t&&n(h[0][d.pxSplit]+t*d.pxPerKey)}).bind("blur",function(){x.removeClass(d.activeClass)}),x=i(c[2]||"<div></div>").insertAfter(h).css("z-index","100").append(_).attr({class:d.splitbarClass,unselectable:"on"}).css({position:"absolute","user-select":"none","-webkit-user-select":"none","-khtml-user-select":"none","-moz-user-select":"none",top:"0px"}).bind("mousedown",t);/^(auto|default|)$/.test(x.css("cursor"))&&x.css("cursor",d.cursor),x._DA=x[0][d.pxSplit],p._PBF=i.boxModel?r(p,"border"+d.side3+"Width","border"+d.side4+"Width"):0,p._PBA=i.boxModel?r(p,"border"+d.side1+"Width","border"+d.side2+"Width"):0,h._pane=d.side1,u._pane=d.side2,i.each([h,u],function(){this._min=d["min"+this._pane]||r(this,"min-"+d.split),this._max=d["max"+this._pane]||r(this,"max-"+d.split)||9999,this._init=!0===d["size"+this._pane]?parseInt(i.curCSS(this[0],d.split)):d["size"+this._pane]});var f=h._init;if(isNaN(u._init)||(f=p[0][d.pxSplit]-p._PBA-u._init-x._DA),d.cookie){i.cookie||alert("jQuery.splitter(): jQuery cookie plugin required");var m=parseInt(i.cookie(d.cookie));isNaN(m)||(f=m),i(window).bind("unload",function(){var e=String(x.css(d.origin));i.cookie(d.cookie,e,{expires:d.cookieExpires||365,path:d.cookiePath||document.location.pathname})})}isNaN(f)&&(f=Math.round((p[0][d.pxSplit]-p._PBA-x._DA)/2)),d.anchorToWindow?(p._hadjust=r(p,"borderTopWidth","borderBottomWidth","marginBottom"),p._hmin=Math.max(r(p,"minHeight"),20),i(window).bind("resize",function(){var e=p.offset().top,t=i(window).height();p.css("height",Math.max(t-e-p._hadjust,p._hmin)+"px"),IE_10_AND_BELOW||p.trigger("resize")}).trigger("resize")):d.resizeToWidth&&!IE_10_AND_BELOW&&i(window).bind("resize",function(){p.trigger("resize")}),p.bind("resize",function(i,e){i.target==this&&(p._DF=p[0][d.pxFixed]-p._PBF,p._DA=p[0][d.pxSplit]-p._PBA,p._DF<=0||p._DA<=0||n(isNaN(e)?d.sizeRight||d.sizeBottom?p._DA-u[0][d.pxSplit]-x._DA:h[0][d.pxSplit]:e))}).trigger("resize",[f])})}}(jQuery);