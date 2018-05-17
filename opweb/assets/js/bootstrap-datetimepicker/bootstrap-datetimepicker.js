!function(e){function t(){return new Date(Date.UTC.apply(Date,arguments))}"indexOf"in Array.prototype||(Array.prototype.indexOf=function(e,t){void 0===t&&(t=0),t<0&&(t+=this.length),t<0&&(t=0);for(var i=this.length;t<i;t++)if(t in this&&this[t]===e)return t;return-1});var i=function(t,i){var s=this;this.element=e(t),this.container=i.container||"body",this.language=i.language||this.element.data("date-language")||"en",this.language=this.language in a?this.language:this.language.split("-")[0],this.language=this.language in a?this.language:"en",this.isRTL=a[this.language].rtl||!1,this.formatType=i.formatType||this.element.data("format-type")||"standard",this.format=n.parseFormat(i.format||this.element.data("date-format")||a[this.language].format||n.getDefaultFormat(this.formatType,"input"),this.formatType),this.isInline=!1,this.isVisible=!1,this.isInput=this.element.is("input"),this.fontAwesome=i.fontAwesome||this.element.data("font-awesome")||!1,this.bootcssVer=i.bootcssVer||(this.isInput?this.element.is(".form-control")?3:2:this.bootcssVer=this.element.is(".input-group")?3:2),this.component=!!this.element.is(".date")&&(3==this.bootcssVer?this.element.find(".input-group-addon .glyphicon-th, .input-group-addon .glyphicon-time, .input-group-addon .glyphicon-calendar, .input-group-addon .glyphicon-calendar, .input-group-addon .fa-calendar, .input-group-addon .fa-clock-o").parent():this.element.find(".add-on .icon-th, .add-on .icon-time, .add-on .icon-calendar, .add-on .fa-calendar, .add-on .fa-clock-o").parent()),this.componentReset=!!this.element.is(".date")&&(3==this.bootcssVer?this.element.find(".input-group-addon .glyphicon-remove, .input-group-addon .fa-times").parent():this.element.find(".add-on .icon-remove, .add-on .fa-times").parent()),this.hasInput=this.component&&this.element.find("input").length,this.component&&0===this.component.length&&(this.component=!1),this.linkField=i.linkField||this.element.data("link-field")||!1,this.linkFormat=n.parseFormat(i.linkFormat||this.element.data("link-format")||n.getDefaultFormat(this.formatType,"link"),this.formatType),this.minuteStep=i.minuteStep||this.element.data("minute-step")||5,this.pickerPosition=i.pickerPosition||this.element.data("picker-position")||"bottom-right",this.showMeridian=i.showMeridian||this.element.data("show-meridian")||!1,this.initialDate=i.initialDate||new Date,this.zIndex=i.zIndex||this.element.data("z-index")||void 0,this.title=void 0!==i.title&&i.title,this.icons={leftArrow:this.fontAwesome?"fa-arrow-left":3===this.bootcssVer?"glyphicon-arrow-left":"icon-arrow-left",rightArrow:this.fontAwesome?"fa-arrow-right":3===this.bootcssVer?"glyphicon-arrow-right":"icon-arrow-right"},this.icontype=this.fontAwesome?"fa":"glyphicon",this._attachEvents(),this.clickedOutside=function(t){0===e(t.target).closest(".datetimepicker").length&&s.hide()},this.formatViewType="datetime","formatViewType"in i?this.formatViewType=i.formatViewType:"formatViewType"in this.element.data()&&(this.formatViewType=this.element.data("formatViewType")),this.minView=0,"minView"in i?this.minView=i.minView:"minView"in this.element.data()&&(this.minView=this.element.data("min-view")),this.minView=n.convertViewMode(this.minView),this.maxView=n.modes.length-1,"maxView"in i?this.maxView=i.maxView:"maxView"in this.element.data()&&(this.maxView=this.element.data("max-view")),this.maxView=n.convertViewMode(this.maxView),this.wheelViewModeNavigation=!1,"wheelViewModeNavigation"in i?this.wheelViewModeNavigation=i.wheelViewModeNavigation:"wheelViewModeNavigation"in this.element.data()&&(this.wheelViewModeNavigation=this.element.data("view-mode-wheel-navigation")),this.wheelViewModeNavigationInverseDirection=!1,"wheelViewModeNavigationInverseDirection"in i?this.wheelViewModeNavigationInverseDirection=i.wheelViewModeNavigationInverseDirection:"wheelViewModeNavigationInverseDirection"in this.element.data()&&(this.wheelViewModeNavigationInverseDirection=this.element.data("view-mode-wheel-navigation-inverse-dir")),this.wheelViewModeNavigationDelay=100,"wheelViewModeNavigationDelay"in i?this.wheelViewModeNavigationDelay=i.wheelViewModeNavigationDelay:"wheelViewModeNavigationDelay"in this.element.data()&&(this.wheelViewModeNavigationDelay=this.element.data("view-mode-wheel-navigation-delay")),this.startViewMode=2,"startView"in i?this.startViewMode=i.startView:"startView"in this.element.data()&&(this.startViewMode=this.element.data("start-view")),this.startViewMode=n.convertViewMode(this.startViewMode),this.viewMode=this.startViewMode,this.viewSelect=this.minView,"viewSelect"in i?this.viewSelect=i.viewSelect:"viewSelect"in this.element.data()&&(this.viewSelect=this.element.data("view-select")),this.viewSelect=n.convertViewMode(this.viewSelect),this.forceParse=!0,"forceParse"in i?this.forceParse=i.forceParse:"dateForceParse"in this.element.data()&&(this.forceParse=this.element.data("date-force-parse"));for(var h=3===this.bootcssVer?n.templateV3:n.template;-1!==h.indexOf("{iconType}");)h=h.replace("{iconType}",this.icontype);for(;-1!==h.indexOf("{leftArrow}");)h=h.replace("{leftArrow}",this.icons.leftArrow);for(;-1!==h.indexOf("{rightArrow}");)h=h.replace("{rightArrow}",this.icons.rightArrow);if(this.picker=e(h).appendTo(this.isInline?this.element:this.container).on({click:e.proxy(this.click,this),mousedown:e.proxy(this.mousedown,this)}),this.wheelViewModeNavigation&&(e.fn.mousewheel?this.picker.on({mousewheel:e.proxy(this.mousewheel,this)}):console.log("Mouse Wheel event is not supported. Please include the jQuery Mouse Wheel plugin before enabling this option")),this.isInline?this.picker.addClass("datetimepicker-inline"):this.picker.addClass("datetimepicker-dropdown-"+this.pickerPosition+" dropdown-menu"),this.isRTL){this.picker.addClass("datetimepicker-rtl");var o=3===this.bootcssVer?".prev span, .next span":".prev i, .next i";this.picker.find(o).toggleClass(this.icons.leftArrow+" "+this.icons.rightArrow)}e(document).on("mousedown",this.clickedOutside),this.autoclose=!1,"autoclose"in i?this.autoclose=i.autoclose:"dateAutoclose"in this.element.data()&&(this.autoclose=this.element.data("date-autoclose")),this.keyboardNavigation=!0,"keyboardNavigation"in i?this.keyboardNavigation=i.keyboardNavigation:"dateKeyboardNavigation"in this.element.data()&&(this.keyboardNavigation=this.element.data("date-keyboard-navigation")),this.todayBtn=i.todayBtn||this.element.data("date-today-btn")||!1,this.todayHighlight=i.todayHighlight||this.element.data("date-today-highlight")||!1,this.weekStart=(i.weekStart||this.element.data("date-weekstart")||a[this.language].weekStart||0)%7,this.weekEnd=(this.weekStart+6)%7,this.startDate=-1/0,this.endDate=1/0,this.datesDisabled=[],this.daysOfWeekDisabled=[],this.setStartDate(i.startDate||this.element.data("date-startdate")),this.setEndDate(i.endDate||this.element.data("date-enddate")),this.setDatesDisabled(i.datesDisabled||this.element.data("date-dates-disabled")),this.setDaysOfWeekDisabled(i.daysOfWeekDisabled||this.element.data("date-days-of-week-disabled")),this.setMinutesDisabled(i.minutesDisabled||this.element.data("date-minute-disabled")),this.setHoursDisabled(i.hoursDisabled||this.element.data("date-hour-disabled")),this.fillDow(),this.fillMonths(),this.update(),this.showMode(),this.isInline&&this.show()};i.prototype={constructor:i,_events:[],_attachEvents:function(){this._detachEvents(),this.isInput?this._events=[[this.element,{focus:e.proxy(this.show,this),keyup:e.proxy(this.update,this),keydown:e.proxy(this.keydown,this)}]]:this.component&&this.hasInput?(this._events=[[this.element.find("input"),{focus:e.proxy(this.show,this),keyup:e.proxy(this.update,this),keydown:e.proxy(this.keydown,this)}],[this.component,{click:e.proxy(this.show,this)}]],this.componentReset&&this._events.push([this.componentReset,{click:e.proxy(this.reset,this)}])):this.element.is("div")?this.isInline=!0:this._events=[[this.element,{click:e.proxy(this.show,this)}]];for(var t,i,s=0;s<this._events.length;s++)t=this._events[s][0],i=this._events[s][1],t.on(i)},_detachEvents:function(){for(var e,t,i=0;i<this._events.length;i++)e=this._events[i][0],t=this._events[i][1],e.off(t);this._events=[]},show:function(t){this.picker.show(),this.height=this.component?this.component.outerHeight():this.element.outerHeight(),this.forceParse&&this.update(),this.place(),e(window).on("resize",e.proxy(this.place,this)),t&&(t.stopPropagation(),t.preventDefault()),this.isVisible=!0,this.element.trigger({type:"show",date:this.date})},hide:function(t){this.isVisible&&(this.isInline||(this.picker.hide(),e(window).off("resize",this.place),this.viewMode=this.startViewMode,this.showMode(),this.isInput||e(document).off("mousedown",this.hide),this.forceParse&&(this.isInput&&this.element.val()||this.hasInput&&this.element.find("input").val())&&this.setValue(),this.isVisible=!1,this.element.trigger({type:"hide",date:this.date})))},remove:function(){this._detachEvents(),e(document).off("mousedown",this.clickedOutside),this.picker.remove(),delete this.picker,delete this.element.data().datetimepicker},getDate:function(){var e=this.getUTCDate();return new Date(e.getTime()+6e4*e.getTimezoneOffset())},getUTCDate:function(){return this.date},setDate:function(e){this.setUTCDate(new Date(e.getTime()-6e4*e.getTimezoneOffset()))},setUTCDate:function(e){e>=this.startDate&&e<=this.endDate?(this.date=e,this.setValue(),this.viewDate=this.date,this.fill()):this.element.trigger({type:"outOfRange",date:e,startDate:this.startDate,endDate:this.endDate})},setFormat:function(e){this.format=n.parseFormat(e,this.formatType);var t;this.isInput?t=this.element:this.component&&(t=this.element.find("input")),t&&t.val()&&this.setValue()},setValue:function(){var t=this.getFormattedDate();this.isInput?this.element.val(t):(this.component&&this.element.find("input").val(t),this.element.data("date",t)),this.linkField&&e("#"+this.linkField).val(this.getFormattedDate(this.linkFormat))},getFormattedDate:function(e){return void 0==e&&(e=this.format),n.formatDate(this.date,e,this.language,this.formatType)},setStartDate:function(e){this.startDate=e||-1/0,this.startDate!==-1/0&&(this.startDate=n.parseDate(this.startDate,this.format,this.language,this.formatType)),this.update(),this.updateNavArrows()},setEndDate:function(e){this.endDate=e||1/0,this.endDate!==1/0&&(this.endDate=n.parseDate(this.endDate,this.format,this.language,this.formatType)),this.update(),this.updateNavArrows()},setDatesDisabled:function(t){this.datesDisabled=t||[],e.isArray(this.datesDisabled)||(this.datesDisabled=this.datesDisabled.split(/,\s*/)),this.datesDisabled=e.map(this.datesDisabled,function(e){return n.parseDate(e,this.format,this.language,this.formatType).toDateString()}),this.update(),this.updateNavArrows()},setTitle:function(e,t){return this.picker.find(e).find("th:eq(1)").text(!1===this.title?t:this.title)},setDaysOfWeekDisabled:function(t){this.daysOfWeekDisabled=t||[],e.isArray(this.daysOfWeekDisabled)||(this.daysOfWeekDisabled=this.daysOfWeekDisabled.split(/,\s*/)),this.daysOfWeekDisabled=e.map(this.daysOfWeekDisabled,function(e){return parseInt(e,10)}),this.update(),this.updateNavArrows()},setMinutesDisabled:function(t){this.minutesDisabled=t||[],e.isArray(this.minutesDisabled)||(this.minutesDisabled=this.minutesDisabled.split(/,\s*/)),this.minutesDisabled=e.map(this.minutesDisabled,function(e){return parseInt(e,10)}),this.update(),this.updateNavArrows()},setHoursDisabled:function(t){this.hoursDisabled=t||[],e.isArray(this.hoursDisabled)||(this.hoursDisabled=this.hoursDisabled.split(/,\s*/)),this.hoursDisabled=e.map(this.hoursDisabled,function(e){return parseInt(e,10)}),this.update(),this.updateNavArrows()},place:function(){if(!this.isInline){if(!this.zIndex){var t=0;e("div").each(function(){var i=parseInt(e(this).css("zIndex"),10);i>t&&(t=i)}),this.zIndex=t+10}var i,s,a;this.container instanceof e?this.container.offset():e(this.container).offset(),i=this.element.offset(),s=i.top,a=i.left,"bottom-left"!=this.pickerPosition&&"top-left"!=this.pickerPosition||(a+=this.element.outerWidth()-this.picker.outerWidth());var n=document.body.clientWidth||window.innerWidth;a+220>n&&(a=n-220),s="top-left"==this.pickerPosition||"top-right"==this.pickerPosition?s-this.picker.outerHeight()-3:s+this.height+3,this.picker.css({top:s,left:a,zIndex:this.zIndex})}},update:function(){var e,t=!1;arguments&&arguments.length&&("string"==typeof arguments[0]||arguments[0]instanceof Date)?(e=arguments[0],t=!0):("string"==typeof(e=(this.isInput?this.element.val():this.element.find("input").val())||this.element.data("date")||this.initialDate)||e instanceof String)&&(e=e.replace(/^\s+|\s+$/g,"")),e||(e=new Date,t=!1),this.date=n.parseDate(e,this.format,this.language,this.formatType),t&&this.setValue(),this.date<this.startDate?this.viewDate=new Date(this.startDate):this.date>this.endDate?this.viewDate=new Date(this.endDate):this.viewDate=new Date(this.date),this.fill()},fillDow:function(){for(var e=this.weekStart,t="<tr>";e<this.weekStart+7;)t+='<th class="dow">'+a[this.language].daysMin[e++%7]+"</th>";t+="</tr>",this.picker.find(".datetimepicker-days thead").append(t)},fillMonths:function(){for(var e="",t=0;t<12;)e+='<span class="month">'+a[this.language].monthsShort[t++]+"</span>";this.picker.find(".datetimepicker-months td").html(e)},fill:function(){if(null!=this.date&&null!=this.viewDate){var i=new Date(this.viewDate),s=i.getUTCFullYear(),h=i.getUTCMonth(),o=i.getUTCDate(),r=i.getUTCHours(),d=i.getUTCMinutes(),l=this.startDate!==-1/0?this.startDate.getUTCFullYear():-1/0,c=this.startDate!==-1/0?this.startDate.getUTCMonth()+1:-1/0,u=this.endDate!==1/0?this.endDate.getUTCFullYear():1/0,p=this.endDate!==1/0?this.endDate.getUTCMonth()+1:1/0,m=new t(this.date.getUTCFullYear(),this.date.getUTCMonth(),this.date.getUTCDate()).valueOf(),v=new Date;if(this.setTitle(".datetimepicker-days",a[this.language].months[h]+" "+s),"time"==this.formatViewType){var g=this.getFormattedDate();this.setTitle(".datetimepicker-hours",g),this.setTitle(".datetimepicker-minutes",g)}else this.setTitle(".datetimepicker-hours",o+" "+a[this.language].months[h]+" "+s),this.setTitle(".datetimepicker-minutes",o+" "+a[this.language].months[h]+" "+s);this.picker.find("tfoot th.today").text(a[this.language].today).toggle(!1!==this.todayBtn),this.updateNavArrows(),this.fillMonths();var f=t(s,h-1,28,0,0,0,0),w=n.getDaysInMonth(f.getUTCFullYear(),f.getUTCMonth());f.setUTCDate(w),f.setUTCDate(w-(f.getUTCDay()-this.weekStart+7)%7);var D=new Date(f);D.setUTCDate(D.getUTCDate()+42),D=D.valueOf();for(var y,T=[];f.valueOf()<D;)f.getUTCDay()==this.weekStart&&T.push("<tr>"),y="",f.getUTCFullYear()<s||f.getUTCFullYear()==s&&f.getUTCMonth()<h?y+=" old":(f.getUTCFullYear()>s||f.getUTCFullYear()==s&&f.getUTCMonth()>h)&&(y+=" new"),this.todayHighlight&&f.getUTCFullYear()==v.getFullYear()&&f.getUTCMonth()==v.getMonth()&&f.getUTCDate()==v.getDate()&&(y+=" today"),f.valueOf()==m&&(y+=" active"),(f.valueOf()+864e5<=this.startDate||f.valueOf()>this.endDate||-1!==e.inArray(f.getUTCDay(),this.daysOfWeekDisabled)||-1!==e.inArray(f.toDateString(),this.datesDisabled))&&(y+=" disabled"),T.push('<td class="day'+y+'">'+f.getUTCDate()+"</td>"),f.getUTCDay()==this.weekEnd&&T.push("</tr>"),f.setUTCDate(f.getUTCDate()+1);this.picker.find(".datetimepicker-days tbody").empty().append(T.join("")),T=[];for(var M="",C="",b="",k=this.hoursDisabled||[],U=0;U<24;U++)if(-1===k.indexOf(U)){var V=t(s,h,o,U);y="",V.valueOf()+36e5<=this.startDate||V.valueOf()>this.endDate?y+=" disabled":r==U&&(y+=" active"),this.showMeridian&&2==a[this.language].meridiem.length?(C=U<12?a[this.language].meridiem[0]:a[this.language].meridiem[1],C!=b&&(""!=b&&T.push("</fieldset>"),T.push('<fieldset class="hour"><legend>'+C.toUpperCase()+"</legend>")),b=C,M=U%12?U%12:12,T.push('<span class="hour'+y+" hour_"+(U<12?"am":"pm")+'">'+M+"</span>"),23==U&&T.push("</fieldset>")):(M=U+":00",T.push('<span class="hour'+y+'">'+M+"</span>"))}this.picker.find(".datetimepicker-hours td").html(T.join("")),T=[],M="",C="",b="";for(var S=this.minutesDisabled||[],U=0;U<60;U+=this.minuteStep)if(-1===S.indexOf(U)){var V=t(s,h,o,r,U,0);y="",V.valueOf()<this.startDate||V.valueOf()>this.endDate?y+=" disabled":Math.floor(d/this.minuteStep)==Math.floor(U/this.minuteStep)&&(y+=" active"),this.showMeridian&&2==a[this.language].meridiem.length?(C=r<12?a[this.language].meridiem[0]:a[this.language].meridiem[1],C!=b&&(""!=b&&T.push("</fieldset>"),T.push('<fieldset class="minute"><legend>'+C.toUpperCase()+"</legend>")),b=C,M=r%12?r%12:12,T.push('<span class="minute'+y+'">'+M+":"+(U<10?"0"+U:U)+"</span>"),59==U&&T.push("</fieldset>")):(M=U+":00",T.push('<span class="minute'+y+'">'+r+":"+(U<10?"0"+U:U)+"</span>"))}this.picker.find(".datetimepicker-minutes td").html(T.join(""));var F=this.date.getUTCFullYear(),x=this.setTitle(".datetimepicker-months",s).end().find("span").removeClass("active");if(F==s){var H=x.length-12;x.eq(this.date.getUTCMonth()+H).addClass("active")}(s<l||s>u)&&x.addClass("disabled"),s==l&&x.slice(0,c+1).addClass("disabled"),s==u&&x.slice(p).addClass("disabled"),T="",s=10*parseInt(s/10,10);var N=this.setTitle(".datetimepicker-years",s+"-"+(s+9)).end().find("td");s-=1;for(var U=-1;U<11;U++)T+='<span class="year'+(-1==U||10==U?" old":"")+(F==s?" active":"")+(s<l||s>u?" disabled":"")+'">'+s+"</span>",s+=1;N.html(T),this.place()}},updateNavArrows:function(){var e=new Date(this.viewDate),t=e.getUTCFullYear(),i=e.getUTCMonth(),s=e.getUTCDate(),a=e.getUTCHours();switch(this.viewMode){case 0:this.startDate!==-1/0&&t<=this.startDate.getUTCFullYear()&&i<=this.startDate.getUTCMonth()&&s<=this.startDate.getUTCDate()&&a<=this.startDate.getUTCHours()?this.picker.find(".prev").css({visibility:"hidden"}):this.picker.find(".prev").css({visibility:"visible"}),this.endDate!==1/0&&t>=this.endDate.getUTCFullYear()&&i>=this.endDate.getUTCMonth()&&s>=this.endDate.getUTCDate()&&a>=this.endDate.getUTCHours()?this.picker.find(".next").css({visibility:"hidden"}):this.picker.find(".next").css({visibility:"visible"});break;case 1:this.startDate!==-1/0&&t<=this.startDate.getUTCFullYear()&&i<=this.startDate.getUTCMonth()&&s<=this.startDate.getUTCDate()?this.picker.find(".prev").css({visibility:"hidden"}):this.picker.find(".prev").css({visibility:"visible"}),this.endDate!==1/0&&t>=this.endDate.getUTCFullYear()&&i>=this.endDate.getUTCMonth()&&s>=this.endDate.getUTCDate()?this.picker.find(".next").css({visibility:"hidden"}):this.picker.find(".next").css({visibility:"visible"});break;case 2:this.startDate!==-1/0&&t<=this.startDate.getUTCFullYear()&&i<=this.startDate.getUTCMonth()?this.picker.find(".prev").css({visibility:"hidden"}):this.picker.find(".prev").css({visibility:"visible"}),this.endDate!==1/0&&t>=this.endDate.getUTCFullYear()&&i>=this.endDate.getUTCMonth()?this.picker.find(".next").css({visibility:"hidden"}):this.picker.find(".next").css({visibility:"visible"});break;case 3:case 4:this.startDate!==-1/0&&t<=this.startDate.getUTCFullYear()?this.picker.find(".prev").css({visibility:"hidden"}):this.picker.find(".prev").css({visibility:"visible"}),this.endDate!==1/0&&t>=this.endDate.getUTCFullYear()?this.picker.find(".next").css({visibility:"hidden"}):this.picker.find(".next").css({visibility:"visible"})}},mousewheel:function(t){if(t.preventDefault(),t.stopPropagation(),!this.wheelPause){this.wheelPause=!0;var i=t.originalEvent,s=i.wheelDelta,a=s>0?1:0===s?0:-1;this.wheelViewModeNavigationInverseDirection&&(a=-a),this.showMode(a),setTimeout(e.proxy(function(){this.wheelPause=!1},this),this.wheelViewModeNavigationDelay)}},click:function(i){i.stopPropagation(),i.preventDefault();var s=e(i.target).closest("span, td, th, legend");if(s.is("."+this.icontype)&&(s=e(s).parent().closest("span, td, th, legend")),1==s.length){if(s.is(".disabled"))return void this.element.trigger({type:"outOfRange",date:this.viewDate,startDate:this.startDate,endDate:this.endDate});switch(s[0].nodeName.toLowerCase()){case"th":switch(s[0].className){case"switch":this.showMode(1);break;case"prev":case"next":var a=n.modes[this.viewMode].navStep*("prev"==s[0].className?-1:1);switch(this.viewMode){case 0:this.viewDate=this.moveHour(this.viewDate,a);break;case 1:this.viewDate=this.moveDate(this.viewDate,a);break;case 2:this.viewDate=this.moveMonth(this.viewDate,a);break;case 3:case 4:this.viewDate=this.moveYear(this.viewDate,a)}this.fill(),this.element.trigger({type:s[0].className+":"+this.convertViewModeText(this.viewMode),date:this.viewDate,startDate:this.startDate,endDate:this.endDate});break;case"today":var h=new Date;h=t(h.getFullYear(),h.getMonth(),h.getDate(),h.getHours(),h.getMinutes(),h.getSeconds(),0),h<this.startDate?h=this.startDate:h>this.endDate&&(h=this.endDate),this.viewMode=this.startViewMode,this.showMode(0),this._setDate(h),this.fill(),this.autoclose&&this.hide()}break;case"span":if(!s.is(".disabled")){var o=this.viewDate.getUTCFullYear(),r=this.viewDate.getUTCMonth(),d=this.viewDate.getUTCDate(),l=this.viewDate.getUTCHours(),c=this.viewDate.getUTCMinutes(),u=this.viewDate.getUTCSeconds();if(s.is(".month")?(this.viewDate.setUTCDate(1),r=s.parent().find("span").index(s),d=this.viewDate.getUTCDate(),this.viewDate.setUTCMonth(r),this.element.trigger({type:"changeMonth",date:this.viewDate}),this.viewSelect>=3&&this._setDate(t(o,r,d,l,c,u,0))):s.is(".year")?(this.viewDate.setUTCDate(1),o=parseInt(s.text(),10)||0,this.viewDate.setUTCFullYear(o),this.element.trigger({type:"changeYear",date:this.viewDate}),this.viewSelect>=4&&this._setDate(t(o,r,d,l,c,u,0))):s.is(".hour")?(l=parseInt(s.text(),10)||0,(s.hasClass("hour_am")||s.hasClass("hour_pm"))&&(12==l&&s.hasClass("hour_am")?l=0:12!=l&&s.hasClass("hour_pm")&&(l+=12)),this.viewDate.setUTCHours(l),this.element.trigger({type:"changeHour",date:this.viewDate}),this.viewSelect>=1&&this._setDate(t(o,r,d,l,c,u,0))):s.is(".minute")&&(c=parseInt(s.text().substr(s.text().indexOf(":")+1),10)||0,this.viewDate.setUTCMinutes(c),this.element.trigger({type:"changeMinute",date:this.viewDate}),this.viewSelect>=0&&this._setDate(t(o,r,d,l,c,u,0))),0!=this.viewMode){var p=this.viewMode;this.showMode(-1),this.fill(),p==this.viewMode&&this.autoclose&&this.hide()}else this.fill(),this.autoclose&&this.hide()}break;case"td":if(s.is(".day")&&!s.is(".disabled")){var d=parseInt(s.text(),10)||1,o=this.viewDate.getUTCFullYear(),r=this.viewDate.getUTCMonth(),l=this.viewDate.getUTCHours(),c=this.viewDate.getUTCMinutes(),u=this.viewDate.getUTCSeconds();s.is(".old")?0===r?(r=11,o-=1):r-=1:s.is(".new")&&(11==r?(r=0,o+=1):r+=1),this.viewDate.setUTCFullYear(o),this.viewDate.setUTCMonth(r,d),this.element.trigger({type:"changeDay",date:this.viewDate}),this.viewSelect>=2&&this._setDate(t(o,r,d,l,c,u,0))}var p=this.viewMode;this.showMode(-1),this.fill(),p==this.viewMode&&this.autoclose&&this.hide()}}},_setDate:function(e,t){t&&"date"!=t||(this.date=e),t&&"view"!=t||(this.viewDate=e),this.fill(),this.setValue();var i;this.isInput?i=this.element:this.component&&(i=this.element.find("input")),i&&(i.change(),this.autoclose),this.element.trigger({type:"changeDate",date:this.getDate()}),null==e&&(this.date=this.viewDate)},moveMinute:function(e,t){if(!t)return e;var i=new Date(e.valueOf());return i.setUTCMinutes(i.getUTCMinutes()+t*this.minuteStep),i},moveHour:function(e,t){if(!t)return e;var i=new Date(e.valueOf());return i.setUTCHours(i.getUTCHours()+t),i},moveDate:function(e,t){if(!t)return e;var i=new Date(e.valueOf());return i.setUTCDate(i.getUTCDate()+t),i},moveMonth:function(e,t){if(!t)return e;var i,s,a=new Date(e.valueOf()),n=a.getUTCDate(),h=a.getUTCMonth(),o=Math.abs(t);if(t=t>0?1:-1,1==o)s=-1==t?function(){return a.getUTCMonth()==h}:function(){return a.getUTCMonth()!=i},i=h+t,a.setUTCMonth(i),(i<0||i>11)&&(i=(i+12)%12);else{for(var r=0;r<o;r++)a=this.moveMonth(a,t);i=a.getUTCMonth(),a.setUTCDate(n),s=function(){return i!=a.getUTCMonth()}}for(;s();)a.setUTCDate(--n),a.setUTCMonth(i);return a},moveYear:function(e,t){return this.moveMonth(e,12*t)},dateWithinRange:function(e){return e>=this.startDate&&e<=this.endDate},keydown:function(e){if(this.picker.is(":not(:visible)"))return void(27==e.keyCode&&this.show());var t,i,s,a=!1;switch(e.keyCode){case 27:this.hide(),e.preventDefault();break;case 37:case 39:if(!this.keyboardNavigation)break;t=37==e.keyCode?-1:1,viewMode=this.viewMode,e.ctrlKey?viewMode+=2:e.shiftKey&&(viewMode+=1),4==viewMode?(i=this.moveYear(this.date,t),s=this.moveYear(this.viewDate,t)):3==viewMode?(i=this.moveMonth(this.date,t),s=this.moveMonth(this.viewDate,t)):2==viewMode?(i=this.moveDate(this.date,t),s=this.moveDate(this.viewDate,t)):1==viewMode?(i=this.moveHour(this.date,t),s=this.moveHour(this.viewDate,t)):0==viewMode&&(i=this.moveMinute(this.date,t),s=this.moveMinute(this.viewDate,t)),this.dateWithinRange(i)&&(this.date=i,this.viewDate=s,this.setValue(),this.update(),e.preventDefault(),a=!0);break;case 38:case 40:if(!this.keyboardNavigation)break;t=38==e.keyCode?-1:1,viewMode=this.viewMode,e.ctrlKey?viewMode+=2:e.shiftKey&&(viewMode+=1),4==viewMode?(i=this.moveYear(this.date,t),s=this.moveYear(this.viewDate,t)):3==viewMode?(i=this.moveMonth(this.date,t),s=this.moveMonth(this.viewDate,t)):2==viewMode?(i=this.moveDate(this.date,7*t),s=this.moveDate(this.viewDate,7*t)):1==viewMode?this.showMeridian?(i=this.moveHour(this.date,6*t),s=this.moveHour(this.viewDate,6*t)):(i=this.moveHour(this.date,4*t),s=this.moveHour(this.viewDate,4*t)):0==viewMode&&(i=this.moveMinute(this.date,4*t),s=this.moveMinute(this.viewDate,4*t)),this.dateWithinRange(i)&&(this.date=i,this.viewDate=s,this.setValue(),this.update(),e.preventDefault(),a=!0);break;case 13:if(0!=this.viewMode){var n=this.viewMode;this.showMode(-1),this.fill(),n==this.viewMode&&this.autoclose&&this.hide()}else this.fill(),this.autoclose&&this.hide();e.preventDefault();break;case 9:this.hide()}if(a){var h;this.isInput?h=this.element:this.component&&(h=this.element.find("input")),h&&h.change(),this.element.trigger({type:"changeDate",date:this.getDate()})}},showMode:function(e){if(e){var t=Math.max(0,Math.min(n.modes.length-1,this.viewMode+e));t>=this.minView&&t<=this.maxView&&(this.element.trigger({type:"changeMode",date:this.viewDate,oldViewMode:this.viewMode,newViewMode:t}),this.viewMode=t)}this.picker.find(">div").hide().filter(".datetimepicker-"+n.modes[this.viewMode].clsName).css("display","block"),this.updateNavArrows()},reset:function(e){this._setDate(null,"date")},convertViewModeText:function(e){switch(e){case 4:return"decade";case 3:return"year";case 2:return"month";case 1:return"day";case 0:return"hour"}}};var s=e.fn.datetimepicker;e.fn.datetimepicker=function(t){var s=Array.apply(null,arguments);s.shift();var a;return this.each(function(){var n=e(this),h=n.data("datetimepicker"),o="object"==typeof t&&t;if(h||n.data("datetimepicker",h=new i(this,e.extend({},e.fn.datetimepicker.defaults,o))),"string"==typeof t&&"function"==typeof h[t]&&void 0!==(a=h[t].apply(h,s)))return!1}),void 0!==a?a:this},e.fn.datetimepicker.defaults={},e.fn.datetimepicker.Constructor=i;var a=e.fn.datetimepicker.dates={en:{days:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"],daysShort:["Sun","Mon","Tue","Wed","Thu","Fri","Sat","Sun"],daysMin:["Su","Mo","Tu","We","Th","Fr","Sa","Su"],months:["January","February","March","April","May","June","July","August","September","October","November","December"],monthsShort:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],meridiem:["am","pm"],suffix:["st","nd","rd","th"],today:"Today"}},n={modes:[{clsName:"minutes",navFnc:"Hours",navStep:1},{clsName:"hours",navFnc:"Date",navStep:1},{clsName:"days",navFnc:"Month",navStep:1},{clsName:"months",navFnc:"FullYear",navStep:1},{clsName:"years",navFnc:"FullYear",navStep:10}],isLeapYear:function(e){return e%4==0&&e%100!=0||e%400==0},getDaysInMonth:function(e,t){return[31,n.isLeapYear(e)?29:28,31,30,31,30,31,31,30,31,30,31][t]},getDefaultFormat:function(e,t){if("standard"==e)return"input"==t?"yyyy-mm-dd hh:ii":"yyyy-mm-dd hh:ii:ss";if("php"==e)return"input"==t?"Y-m-d H:i":"Y-m-d H:i:s";throw new Error("Invalid format type.")},validParts:function(e){if("standard"==e)return/t|hh?|HH?|p|P|ii?|ss?|dd?|DD?|mm?|MM?|yy(?:yy)?/g;if("php"==e)return/[dDjlNwzFmMnStyYaABgGhHis]/g;throw new Error("Invalid format type.")},nonpunctuation:/[^ -\/:-@\[-`{-~\t\n\rTZ]+/g,parseFormat:function(e,t){var i=e.replace(this.validParts(t),"\0").split("\0"),s=e.match(this.validParts(t));if(!i||!i.length||!s||0==s.length)throw new Error("Invalid date format.");return{separators:i,parts:s}},parseDate:function(s,n,h,o){if(s instanceof Date){var r=new Date(s.valueOf()-6e4*s.getTimezoneOffset());return r.setMilliseconds(0),r}if(/^\d{4}\-\d{1,2}\-\d{1,2}$/.test(s)&&(n=this.parseFormat("yyyy-mm-dd",o)),/^\d{4}\-\d{1,2}\-\d{1,2}[T ]\d{1,2}\:\d{1,2}$/.test(s)&&(n=this.parseFormat("yyyy-mm-dd hh:ii",o)),/^\d{4}\-\d{1,2}\-\d{1,2}[T ]\d{1,2}\:\d{1,2}\:\d{1,2}[Z]{0,1}$/.test(s)&&(n=this.parseFormat("yyyy-mm-dd hh:ii:ss",o)),/^[-+]\d+[dmwy]([\s,]+[-+]\d+[dmwy])*$/.test(s)){var d,l,c=/([-+]\d+)([dmwy])/,u=s.match(/([-+]\d+)([dmwy])/g);s=new Date;for(var p=0;p<u.length;p++)switch(d=c.exec(u[p]),l=parseInt(d[1]),d[2]){case"d":s.setUTCDate(s.getUTCDate()+l);break;case"m":s=i.prototype.moveMonth.call(i.prototype,s,l);break;case"w":s.setUTCDate(s.getUTCDate()+7*l);break;case"y":s=i.prototype.moveYear.call(i.prototype,s,l)}return t(s.getUTCFullYear(),s.getUTCMonth(),s.getUTCDate(),s.getUTCHours(),s.getUTCMinutes(),s.getUTCSeconds(),0)}var m,v,d,u=s&&s.toString().match(this.nonpunctuation)||[],s=new Date(0,0,0,0,0,0,0),g={},f=["hh","h","ii","i","ss","s","yyyy","yy","M","MM","m","mm","D","DD","d","dd","H","HH","p","P"],w={hh:function(e,t){return e.setUTCHours(t)},h:function(e,t){return e.setUTCHours(t)},HH:function(e,t){return e.setUTCHours(12==t?0:t)},H:function(e,t){return e.setUTCHours(12==t?0:t)},ii:function(e,t){return e.setUTCMinutes(t)},i:function(e,t){return e.setUTCMinutes(t)},ss:function(e,t){return e.setUTCSeconds(t)},s:function(e,t){return e.setUTCSeconds(t)},yyyy:function(e,t){return e.setUTCFullYear(t)},yy:function(e,t){return e.setUTCFullYear(2e3+t)},m:function(e,t){for(t-=1;t<0;)t+=12;for(t%=12,e.setUTCMonth(t);e.getUTCMonth()!=t;){if(isNaN(e.getUTCMonth()))return e;e.setUTCDate(e.getUTCDate()-1)}return e},d:function(e,t){return e.setUTCDate(t)},p:function(e,t){return e.setUTCHours(1==t?e.getUTCHours()+12:e.getUTCHours())}};if(w.M=w.MM=w.mm=w.m,w.dd=w.d,w.P=w.p,s=t(s.getFullYear(),s.getMonth(),s.getDate(),s.getHours(),s.getMinutes(),s.getSeconds()),u.length==n.parts.length){for(var p=0,D=n.parts.length;p<D;p++){if(m=parseInt(u[p],10),d=n.parts[p],isNaN(m))switch(d){case"MM":v=e(a[h].months).filter(function(){var e=this.slice(0,u[p].length);return e==u[p].slice(0,e.length)}),m=e.inArray(v[0],a[h].months)+1;break;case"M":v=e(a[h].monthsShort).filter(function(){var e=this.slice(0,u[p].length),t=u[p].slice(0,e.length);return e.toLowerCase()==t.toLowerCase()}),m=e.inArray(v[0],a[h].monthsShort)+1;break;case"p":case"P":m=e.inArray(u[p].toLowerCase(),a[h].meridiem)}g[d]=m}for(var y,p=0;p<f.length;p++)(y=f[p])in g&&!isNaN(g[y])&&w[y](s,g[y])}return s},formatDate:function(t,i,s,h){if(null==t)return"";var o;if("standard"==h)o={t:t.getTime(),yy:t.getUTCFullYear().toString().substring(2),yyyy:t.getUTCFullYear(),m:t.getUTCMonth()+1,M:a[s].monthsShort[t.getUTCMonth()],MM:a[s].months[t.getUTCMonth()],d:t.getUTCDate(),D:a[s].daysShort[t.getUTCDay()],DD:a[s].days[t.getUTCDay()],p:2==a[s].meridiem.length?a[s].meridiem[t.getUTCHours()<12?0:1]:"",h:t.getUTCHours(),i:t.getUTCMinutes(),s:t.getUTCSeconds()},2==a[s].meridiem.length?o.H=o.h%12==0?12:o.h%12:o.H=o.h,o.HH=(o.H<10?"0":"")+o.H,o.P=o.p.toUpperCase(),o.hh=(o.h<10?"0":"")+o.h,o.ii=(o.i<10?"0":"")+o.i,o.ss=(o.s<10?"0":"")+o.s,o.dd=(o.d<10?"0":"")+o.d,o.mm=(o.m<10?"0":"")+o.m;else{if("php"!=h)throw new Error("Invalid format type.");o={y:t.getUTCFullYear().toString().substring(2),Y:t.getUTCFullYear(),F:a[s].months[t.getUTCMonth()],M:a[s].monthsShort[t.getUTCMonth()],n:t.getUTCMonth()+1,t:n.getDaysInMonth(t.getUTCFullYear(),t.getUTCMonth()),j:t.getUTCDate(),l:a[s].days[t.getUTCDay()],D:a[s].daysShort[t.getUTCDay()],w:t.getUTCDay(),N:0==t.getUTCDay()?7:t.getUTCDay(),S:t.getUTCDate()%10<=a[s].suffix.length?a[s].suffix[t.getUTCDate()%10-1]:"",a:2==a[s].meridiem.length?a[s].meridiem[t.getUTCHours()<12?0:1]:"",g:t.getUTCHours()%12==0?12:t.getUTCHours()%12,G:t.getUTCHours(),i:t.getUTCMinutes(),s:t.getUTCSeconds()},o.m=(o.n<10?"0":"")+o.n,o.d=(o.j<10?"0":"")+o.j,o.A=o.a.toString().toUpperCase(),o.h=(o.g<10?"0":"")+o.g,o.H=(o.G<10?"0":"")+o.G,o.i=(o.i<10?"0":"")+o.i,o.s=(o.s<10?"0":"")+o.s}for(var t=[],r=e.extend([],i.separators),d=0,l=i.parts.length;d<l;d++)r.length&&t.push(r.shift()),t.push(o[i.parts[d]]);return r.length&&t.push(r.shift()),t.join("")},convertViewMode:function(e){switch(e){case 4:case"decade":e=4;break;case 3:case"year":e=3;break;case 2:case"month":e=2;break;case 1:case"day":e=1;break;case 0:case"hour":e=0}return e},headTemplate:'<thead><tr><th class="prev"><i class="{iconType} {leftArrow}"/></th><th colspan="5" class="switch"></th><th class="next"><i class="{iconType} {rightArrow}"/></th></tr></thead>',headTemplateV3:'<thead><tr><th class="prev"><span class="{iconType} {leftArrow}"></span> </th><th colspan="5" class="switch"></th><th class="next"><span class="{iconType} {rightArrow}"></span> </th></tr></thead>',contTemplate:'<tbody><tr><td colspan="7"></td></tr></tbody>',footTemplate:'<tfoot><tr><th colspan="7" class="today"></th></tr></tfoot>'};n.template='<div class="datetimepicker"><div class="datetimepicker-minutes"><table class=" table-condensed">'+n.headTemplate+n.contTemplate+n.footTemplate+'</table></div><div class="datetimepicker-hours"><table class=" table-condensed">'+n.headTemplate+n.contTemplate+n.footTemplate+'</table></div><div class="datetimepicker-days"><table class=" table-condensed">'+n.headTemplate+"<tbody></tbody>"+n.footTemplate+'</table></div><div class="datetimepicker-months"><table class="table-condensed">'+n.headTemplate+n.contTemplate+n.footTemplate+'</table></div><div class="datetimepicker-years"><table class="table-condensed">'+n.headTemplate+n.contTemplate+n.footTemplate+"</table></div></div>",n.templateV3='<div class="datetimepicker"><div class="datetimepicker-minutes"><table class=" table-condensed">'+n.headTemplateV3+n.contTemplate+n.footTemplate+'</table></div><div class="datetimepicker-hours"><table class=" table-condensed">'+n.headTemplateV3+n.contTemplate+n.footTemplate+'</table></div><div class="datetimepicker-days"><table class=" table-condensed">'+n.headTemplateV3+"<tbody></tbody>"+n.footTemplate+'</table></div><div class="datetimepicker-months"><table class="table-condensed">'+n.headTemplateV3+n.contTemplate+n.footTemplate+'</table></div><div class="datetimepicker-years"><table class="table-condensed">'+n.headTemplateV3+n.contTemplate+n.footTemplate+"</table></div></div>",e.fn.datetimepicker.DPGlobal=n,e.fn.datetimepicker.noConflict=function(){return e.fn.datetimepicker=s,this},e(document).on("focus.datetimepicker.data-api click.datetimepicker.data-api",'[data-provide="datetimepicker"]',function(t){var i=e(this);i.data("datetimepicker")||(t.preventDefault(),i.datetimepicker("show"))}),e(function(){e('[data-provide="datetimepicker-inline"]').datetimepicker()})}(window.jQuery);