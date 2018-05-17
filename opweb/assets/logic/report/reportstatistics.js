define(["ol","handlebars","html!!mapPopup3","highcharts","datatables","bootstrap-datetimepickerCN"],function(t,e,a){var n,i,r,o,d=function(){$dict.init({name:"priority",url:$cfg.baseUrl+"/OptionSelect/com-ssxt-op-priority/"}),$dict.init({name:"serviceType",url:$cfg.baseUrl+"/OptionSelect/com-ssxt-op-serviceType/"}),$dict.init({name:"addvcd",url:$cfg.baseUrl+"/addvcd/loadAll/"}),$dict.init({name:"device",url:$cfg.baseUrl+"/device/loadAll/"}),$dict.init({name:"station",url:$cfg.baseUrl+"/stinfo/loadAll/"}),$dict.init({name:"stcdType",url:$cfg.baseUrl+"/OptionSelect/com-ssxt-op-stcdType/"}),$dict.init({name:"maintainer",url:$cfg.baseUrl+"/users/loadAll/?isOperation=1"}),$dict.init({name:"statusCode",url:$cfg.baseUrl+"/OptionSelect/statusCode"})},c=function(t){if(!this.attach){var e=t.find(".chart-condition");e.html('<option value="0">近六个月</option>\n<option value="1">近一年</option>\n<option value="2">今年</option>\n<option value="3">汛期</option>').select2().next(".select2").css("position","absolute").css("zIndex","1");var a=this;e.on("change",function(){a.initFunc.apply(a,[t])}),this.attach=!0}var n=$api.get({loading:!1,url:$cfg.baseUrl+"/report/analysis"}),i=$api.get({loading:!1,url:$cfg.baseUrl+"/statistics/maintenanceInfo"});$.when(n,i).done(function(e,a){if(t.trigger("loading.hide"),e.success&&a.success){var n=a.data;t.find("#p_total").html(n.total),t.find("#p_sign").html(n.clockNum),t.find("#p_wait").html(n.freeNum),t.find("#p_vacation").html(n.vacationNum);var i=e.data,r=$.map(i,function(t){return t.addvnm}),o=$.map(i,function(t){return t.bad}),d=$.map(i,function(t){return t.normal}),c=$.map(i,function(t){return t.maintNum}),l=($.map(i,function(t){return t.taskNum}),function(e){var a=t.find(".chart2");a.removeClass("hide"),a.highcharts({chart:{type:"column",height:400,width:600,marginTop:220},title:{text:""},xAxis:{categories:r,tickLength:0,labels:{enabled:!1},gridLineWidth:0},yAxis:{min:0,minorTickInterval:10,allowDecimals:!1,reversed:!0,title:{text:""},stackLabels:{enabled:!0,style:{fontWeight:"bold",color:Highcharts.theme&&Highcharts.theme.textColor||"gray"}}},legend:{align:"left",x:0,verticalAlign:"bottom",y:0},plotOptions:{column:{stacking:"normal",dataLabels:{formatter:function(){return 0===this.y?"":this.y},enabled:!0,color:Highcharts.theme&&Highcharts.theme.dataLabelsColor||"white",style:{textShadow:"0 0 9px black"}}}},series:[{maxPointWidth:30,name:"维修人数",color:"#00b0f0",data:c}]},function(t){e&&e(t),a.addClass("hide")})});l(function(e){t.find(".chart1").highcharts({chart:{type:"column",height:400,marginBottom:230,events:{beforePrint:function(e){var a=e.target,n=a.options.exporting.printMaxWidth,i=n&&a.chartWidth>n,r=t.find(".chart2"),o=Highcharts.getChart(r);i&&(r.removeClass("hide"),o.setSize(n,void 0,!1),r.addClass("hide"),t.find(".chart1 .add-chart").remove(),t.find(".chart2").find("svg > *").addClass("add-chart").appendTo(t.find(".chart1").find("svg")))},afterPrint:function(e){e.target;t.trigger("panel:resize")}}},title:{text:""},xAxis:{categories:r,gridLineWidth:0,tickInterval:1,labels:{style:{fontSize:"11px"}}},yAxis:{min:0,minorTickInterval:10,allowDecimals:!1,title:{text:""},stackLabels:{enabled:!0,style:{fontWeight:"bold",color:Highcharts.theme&&Highcharts.theme.textColor||"gray"}}},legend:{align:"left",x:0,verticalAlign:"top",y:0},plotOptions:{column:{stacking:"normal",dataLabels:{formatter:function(){return 0===this.y?"":this.y},enabled:!0,color:Highcharts.theme&&Highcharts.theme.dataLabelsColor||"white",style:{textShadow:"0 0 9px black"}}}},series:[{maxPointWidth:30,name:"故障站点",color:"#ff0000",data:o},{maxPointWidth:30,name:"正常站点",color:"#ffc000",data:d}],exporting:{enabled:!0,filename:"各市维修任务及维修人数分析表（"+moment().format("YYYY年MM月DD日")+"）"}},function(a){e.setSize(t.width()-30,void 0,!1),t.find(".chart2").find("svg > *").addClass("add-chart").appendTo($(a.container).find("svg")),setTimeout(function(){t.find(".chart2").replaceWith('<div class="chart2 hide"></div>'),l()},100)})}),t.on("panel:resize",function(){var e=t.find(".chart2"),a=Highcharts.getChart(e);e.removeClass("hide"),a.setSize(t.width()-30,void 0,!1),e.addClass("hide"),t.find(".chart1 .add-chart").remove(),t.find(".chart2").find("svg > *").addClass("add-chart").appendTo(t.find(".chart1").find("svg")),setTimeout(function(){t.find(".chart2").replaceWith('<div class="chart2 hide"></div>'),l()},100)})}else $toast.warning(e.msg||a.msg)}).fail(function(e){t.trigger("loading.hide"),$toast.error(e)})},l=function(t){if(!this.attach){t.find("#c_startTime").datetimepicker({minView:2,format:"yyyy-mm-dd",autoclose:!0,todayBtn:!0,pickerPosition:"bottom-left"}),t.find("#c_endTime").datetimepicker({minView:2,format:"yyyy-mm-dd",autoclose:!0,todayBtn:!0,pickerPosition:"bottom-left"});var e,a=[];e=$dict.initOptions({s2s:!0,elem:"#c_addvcd",dict:"addvcd",propText:"addvnm",propId:"addvcd",append:[{id:"-1",text:"全部"}],filter:function(t,e){var a=$.userData.addvcd;return"0000"===a.slice(-4)||"00"===a.slice(-2)&&t.addvcd.slice(0,-2)===a.slice(0,-2)||a===t.addvcd}}),a.push(e);var d=this;return t.find("#b_refresh").on("click",function(){t.trigger("loading.show"),d.initFunc.apply(d,[t])}),$.when.apply($,a).done(function(){t.find("#c_startTime").attr("disabled","true"),t.find("#c_endTime").attr("disabled","true"),t.find("#c_startTime").val(moment().format("YYYY-MM-DD")).change(),t.find("#c_endTime").val(moment().format("YYYY-MM-DD")).change(),d.attach=!0,d.initFunc.apply(d,[t])}),t.find("#c_time").on("change",function(){var e=t.find(this).val().toString();t.find("#c_startTime").attr("disabled","true"),t.find("#c_endTime").attr("disabled","true"),"1"===e?(t.find("#c_startTime").val(moment().format("YYYY-MM-DD")).change(),t.find("#c_endTime").val(moment().format("YYYY-MM-DD")).change()):"2"===e?(t.find("#c_startTime").val(moment().add(-6,"d").format("YYYY-MM-DD")).change(),t.find("#c_endTime").val(moment().format("YYYY-MM-DD")).change()):"3"===e?(t.find("#c_startTime").val(moment().add(1,"d").add(-1,"M").format("YYYY-MM-DD")).change(),t.find("#c_endTime").val(moment().format("YYYY-MM-DD")).change()):"4"===e&&(t.find("#c_startTime").val("").change(),t.find("#c_endTime").val("").change(),t.find("#c_startTime").removeAttr("disabled").trigger("attr.update"),t.find("#c_endTime").removeAttr("disabled").trigger("attr.update"))}),void(this.attach=!0)}var c=t.find("#c_startTime").val(),l=t.find("#c_endTime").val();if(!c||!l)return $toast.warning("开始时间或结束时间不能为空"),void t.trigger("loading.hide");i=c,r=l,o=$("#c_addvcd").find("option:selected").text();var s=$.extend({},$opt.paramFilter({startTime:c,endTime:l,addvcd:$("#c_addvcd").val()})),m=$api.get({loading:!1,url:$cfg.baseUrl+"/report/assessment",data:s});$.when(m).done(function(e){if(t.trigger("loading.hide"),e.success){n&&n.fnDestroy();var a=$.extend({},$plugin.datatable_local,{buttons:[{extend:"print",text:"打印",title:"各市维修人员考勤表("+c+"-"+l+"-"+o+")",className:"btn btn-primary mr-5",exportOptions:{stripHtml:!1,format:{header:function(t,e){return 9===e?"":t},body:function(t,e,a,n){return 9===a?"":2===a?$dict.getItem("addvcd","addvcd",t).addvnm:4===a||5===a||7===a?t||"0":t}}}},{extend:"excelHtml5",text:"导出",title:"各市维修人员考勤表("+c+"-"+l+"-"+o+")",className:"btn btn-primary",exportOptions:{format:{header:function(t,e){return 9===e?"":t},body:function(t,e,a,n){return 9===a?"":2===a?$dict.getItem("addvcd","addvcd",t).addvnm:4===a||5===a||7===a?t||"0":t}}}}],data:e.data,aaSorting:[[0,"desc"]],columns:[{title:"姓名",data:"name",class:"text-center"},{title:"排名",data:"ranking",class:"text-center"},{title:"所属区域",data:"addvcd",class:"text-center",fnCreatedCell:function(t,e,a,n,i){$dict.get("addvcd").then(function(a){$.each(a,function(a,n){n.addvcd===e&&$(t).html(n.addvnm)})})}},{title:"职位",data:"position",class:"text-center"},{title:"维修数量",data:"dealNum",class:"text-center"},{title:"请假次数",data:"vacation",class:"text-center"},{title:"维修质量",data:"maintenanceRate",class:"text-center",render:function(t,e,a){return(100*parseFloat(t)).toFixed(0)+"%"}},{title:"维修工作时长",data:"wordTime",class:"text-center"},{title:"操作",data:"userId",bSortable:!1,fnCreatedCell:function(t,e,a,n,i){var r=$('<a class="btn btn-info btn-xs ml-5"><i class="fa fa-file-o mr-5"></i>人员业绩查询</a>');r.on("click",function(){f(t,e,a,n,i)}),$(t).html("").append(r)}}]});n=t.find(".table").dataTable(a),n.api().buttons().container().appendTo(t.find("#t_button"))}else $toast.warning(e.msg)}).fail(function(e){t.trigger("loading.hide"),$toast.error(e)})},s=function(t){if(!this.attach){t.find("#c_startTime").datetimepicker({minView:2,format:"yyyy-mm-dd",autoclose:!0,todayBtn:!0,pickerPosition:"bottom-left"}),t.find("#c_endTime").datetimepicker({minView:2,format:"yyyy-mm-dd",autoclose:!0,todayBtn:!0,pickerPosition:"bottom-left"}),t.find("#c_startTime").val(moment().add(-1,"d").format("YYYY-MM-DD")).change(),t.find("#c_endTime").val(moment().add(-1,"d").format("YYYY-MM-DD")).change();var e=this;t.find("#b_refresh").on("click",function(){t.trigger("loading.show"),e.initFunc.apply(e,[t])}),this.attach=!0}var a=t.find("#c_startTime").val(),n=t.find("#c_endTime").val();if(!a||!n)return $toast.warning("开始时间或结束时间不能为空"),void t.trigger("loading.hide");var i=$api.get({loading:!1,url:$cfg.baseUrl+"/report/cityError",data:$.extend({},$opt.paramFilter({startTime:a,endTime:n}))});$.when(i).done(function(e){if(t.trigger("loading.hide"),e.success){var a=$.map(e.data,function(t){return t.addvnm}),n=$.map(e.data,function(t){return t.bad}),i=$.map(e.data,function(t){return t.normal});t.find(".chart").highcharts({chart:{type:"bar",alignTicks:!1},title:{text:""},xAxis:{categories:a},yAxis:{min:0,gridLineWidth:0,title:{text:""}},legend:{align:"left",verticalAlign:"top",x:20,y:10,reversed:!0},plotOptions:{series:{stacking:"normal",dataLabels:{formatter:function(){return 0===this.y?"":this.y},enabled:!0,style:{color:"white",fontSize:10,textShadow:"none"}}}},series:[{maxPointWidth:20,name:"故障",color:"#ed7d31",data:n},{maxPointWidth:20,name:"正常",color:"#92d050",data:i}],exporting:{enabled:!0,filename:"各市故障站点分析"}})}else $toast.warning(e.msg)}).fail(function(e){t.trigger("loading.hide"),$toast.error(e)})},m=function(){var t=[{elem:$("#p_panel_1").get(0),target:$(".panel-content .panel-left").get(0),data:{title:"各市维修任务及维修人数分析表（"+moment().format("YYYY年MM月DD日")+"）"},initFunc:c},{elem:$("#p_panel_2").get(0),target:$(".panel-content .panel-right").get(0),data:{title:"各市维修人员考勤表"},initFunc:l},{elem:$("#p_panel_3").get(0),target:$(".panel-content .panel-left").get(0),data:{title:"各市故障站点分析"},initFunc:s}],a=$(".panel-content");$.map(t,function(t){t.target&&(a=$(t.target));var n=t.html||t.elem.innerHTML.replace(/\[\[/g,"{{").replace(/]]/g,"}}"),i=e.compile(n)(t.data||{}),r=$(i),o=r.hasClass("panel")?r:r.find(".panel");r.on("refresh",function(){$.isFunction(t.initFunc)&&(o.trigger("loading.show"),t.initFunc.apply(t,[o]))}),a.append(r),$.isFunction(t.initFunc)&&(t.attach=!1,t.initFunc.apply(t,[o]))})},f=function(t,e,a,n,o){var d=$msg.dialog("peopleratingAdd",function(t,e){},{title:"人员业绩查询 ("+i+" ~ "+r+")",modalClassName:"modal-lg",buttons:{success:{label:"确定",className:"btn-info"}}}),c=$api.get({loading:!1,url:$cfg.baseUrl+"/maintGps/RepairMileage/",data:{startTime:i,endTime:r,repairUserId:e}});$.when(d,c).then(function(t,n){var o=t.find("form.info-left"),d=t.find("form.info-right");o.formData($.extend({},a,{attendance:(100*parseFloat(a.attendance)).toFixed(0)+"%",maintenanceRate:(100*parseFloat(a.maintenanceRate)).toFixed(0)+"%",repairMileage:n.data+"公里"})),o.formDisable(!0);var c=$.extend({},$plugin.datatable_server,{fnServerData:function(t,a,n){var o=$.extend({},$opt.paramConvert(a),$opt.paramFilter({startTime:i,endTime:r,maintUserId:e,taskStatus:60})),d=$api.get({url:$cfg.baseUrl+"/task/history",data:o});$.when(d).done(function(t){t.success?n($opt.dataConvert(t)):$toast.warning(t.msg)}).fail(function(t){$toast.error(t)})},aaSorting:[[0,"desc"]],columns:[{title:"维修单单编号",data:"taskNo",class:"text-center"},{title:"测站名称",data:"stionId",class:"text-center",fnCreatedCell:function(t,e,a,n,i){$dict.get("station").then(function(a){$.each(a,function(a,n){n.stionId===e&&$(t).html(n.stnm)})})}},{title:"业务类型",data:"stionId",class:"text-center",fnCreatedCell:function(t,e,a,n,i){$.when($dict.get("station"),$dict.get("serviceType")).then(function(a){$.each(a,function(a,n){if(n.stionId===e){var i=$dict.getItem("serviceType","trueValue",n.serviceType);$(t).html(i.showValue)}})})}},{title:"维修区域",data:"addvcd",class:"text-center",fnCreatedCell:function(t,e,a,n,i){$dict.get("addvcd").then(function(a){$.each(a,function(a,n){n.addvcd===e&&$(t).html(n.addvnm)})})}},{title:"优先级",data:"priorityStatus",class:"text-center",fnCreatedCell:function(t,e,a,n,i){var r={1:"mt-5 badge bg-dutch",2:"mt-5 badge bg-orange",3:"mt-5 badge bg-red"};$dict.get("priority").then(function(a){$.each(a,function(a,n){n.trueValue===(e||"").toString()&&$(t).html('<div class="'+r[n.trueValue]+'">'+n.showValue+"</div>")})})}},{title:"创建时间",data:"createTime",class:"text-center"},{title:"操作",data:"maintUserId",bSortable:!1,fnCreatedCell:function(t,e,a,n,i){var r=$('<a class="btn btn-info btn-xs ml-5"><i class="fa fa-file-o mr-5"></i>路线查看</a>');r.on("click",function(){p(t,e,a,n,i)}),$(t).html("").append(r)}}]});d.find(".table").dataTable(c)})},p=function(n,i,r,o,d){var c=r.reciveTime,l=r.finishTime,s=i,m=$msg.dialog("mapLocation",function(t,e){},{title:"路线查看 ("+c+" ~ "+l+")",modalClassName:"modal-lg",buttons:{success:{label:"确定",className:"btn-info"}}}),f=$api.get({url:$cfg.baseUrl+"/maintGps/historyRoute/",data:{startTime:c,endTime:l,repairUserId:s}});$.when(m,f).then(function(n,i){if(i.success&&i.data.length>0){var r,o=t.proj.fromLonLat([parseFloat(116.39739),parseFloat(39.40886)]),d=new t.View({center:o,zoom:7,minZoom:5}),c=new t.Map({controls:t.control.defaults({attributionOptions:{collapsible:!1}}),target:"p_map",logo:!1,view:d}),l=new t.layer.Tile({zIndex:1,source:$opt.getBmapOptions()});c&&c.addLayer(l);var s=[],m=$.map(i.data,function(e,a){return s.push(t.proj.fromLonLat([e.longitude,e.latitude])),{type:"Feature",geometry:{type:"Point",coordinates:t.proj.fromLonLat([e.longitude,e.latitude])},properties:$.extend({},e,{pointType:"Point",isStart:0===a,isEnd:a===i.data.length-1})}}),f=$.map(i.data,function(e,a){if(0===a)return null;var n=i.data[a-1];return{type:"Feature",geometry:{type:"LineString",coordinates:[t.proj.fromLonLat([n.longitude,n.latitude]),t.proj.fromLonLat([e.longitude,e.latitude])]},properties:$.extend({},e,{pointType:"LineString"})}}),p={type:"FeatureCollection",crs:{type:"name",properties:{name:"EPSG:3857"}},features:m.concat(f).concat([{type:"Feature",geometry:{type:"Polygon",coordinates:[s]},properties:{pointType:"setView"}}])},u={StartPoint:new t.style.Style({image:new t.style.Icon({anchor:[.5,1],scale:.7,src:"assets/img/map/p_start.png"})}),endPoint:new t.style.Style({image:new t.style.Icon({anchor:[.5,1],scale:.7,src:"assets/img/map/p_end.png"})}),Point:new t.style.Style({image:new t.style.Circle({radius:5,fill:new t.style.Fill({color:"rgba(255,0,0,0.3)"}),stroke:new t.style.Stroke({color:"red",width:1})})}),LineString:new t.style.Style({stroke:new t.style.Stroke({color:"blue",lineDash:[10,10],width:2})})},g=function(t){var e=t.getGeometry().getType();if("Point"===e){var a=t.getProperties();if(a.isStart)return u.StartPoint;if(a.isEnd)return u.endPoint}return u[e]},h=new t.source.Vector({features:(new t.format.GeoJSON).readFeatures(p)}),v=new t.layer.Vector({zIndex:2,source:h,style:g});c&&c.addLayer(v);var y=function(){var t=h.getFeatures();$.map(t,function(t){"setView"===t.get("pointType")&&d.fit(t.getGeometry(),{padding:[50,50,50,50],constrainResolution:!1})})};y(),c.on("pointermove",function(n){var i=c.forEachFeatureAtPixel(n.pixel,function(t,e){return t});if(i){var o=i.getProperties();if("Point"===o.pointType){var d=n.coordinate;r&&(r.remove(),r=void 0);var l={};l=$.extend(l,o),r=$(e.compile(a)(l));var s=new t.Overlay({element:r.get(0),autoPan:!0,autoPanAnimation:{duration:250}});s.setPosition(d),c.addOverlay(s)}else r&&(r.remove(),r=void 0)}else r&&(r.remove(),r=void 0)}),c.on("click",function(t){c.forEachFeatureAtPixel(t.pixel,function(t,e){return t})&&y()})}else i.success?($msg.info("该任务单处理时间段没有位置记录"),n.modal("hide")):($toast.warning(i.msg),n.modal("hide"))})};return{data:{model:[{icon:"fa fa-bar-chart",title:"报表统计"}]},init:function(t){console.log(t+":init"),d(),m()},dispose:function(t){console.log(t+":dispose")}}});