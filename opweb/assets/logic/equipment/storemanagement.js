define(["handlebars","datatables"],function(e){var t,n,a,i,d,r={ignore:".ignore",errorPlacement:function(e,t){e.appendTo(t.parents(".form-group"))},rules:{stcd:{required:!0}},messages:{stcd:{required:"请填写测站编码"}}},o=function(){$dict.init({name:"stcdType",url:$cfg.baseUrl+"/OptionSelect/com-ssxt-op-stcdType/"}),$dict.init({name:"addvcd",url:$cfg.baseUrl+"/addvcd/loadAll/"}),$dict.init({name:"maintainer",url:$cfg.baseUrl+"/users/loadAll/?isOperation=1"}),$dict.init({name:"user",url:$cfg.baseUrl+"/users/loadAll/"}),$dict.init({name:"serviceType",url:$cfg.baseUrl+"/OptionSelect/com-ssxt-op-serviceType/"}),$dict.init({name:"deviceType",url:$cfg.baseUrl+"/OptionSelect/deviceType/"}),$dict.init({name:"store",url:$cfg.baseUrl+"/WareHouse/loadAll/"})},s=function(){var e=[];k(),$.when.apply($,e).done(function(){A()}),$("#b_refresh,#b_search").on("click",function(){A()}),$("#b_add").on("click",function(){W()}),$("#b_setting").on("click",function(){c()})},c=function(){var e=$msg.dialog("deviceTypeConfAdd",function(e,t){return!0},{title:"设备类型配置",buttons:{success:{label:"确定",className:"btn-info"}}});$.when(e).then(function(e){$(e).find("#b_add").on("click",function(){p()}),n&&n.fnDestroy();var t=$.extend({},$plugin.datatable_server,{fnServerData:function(e,t,n){var a=$api.get({loading:!1,url:$cfg.baseUrl+"/warehouseDevice/deviceType/select"});$.when(a).done(function(e){e.success?n($opt.dataConvert(e)):$toast.warning(e.msg)}).fail(function(e){$toast.error(e)})},bPaginate:!1,ordering:!1,autoWidth:!1,info:"",columns:[{title:"设备类型名称",data:"name",class:"text-center"},{title:"操作",data:"id",bSortable:!1,width:120,fnCreatedCell:function(e,t,n,a,i){var d=$('<a class="btn btn-warning btn-xs ml-5"><i class="fa fa-edit mr-5"></i>修改</a>'),r=$('<a class="btn btn-danger btn-xs ml-5"><i class="fa fa-trash-o mr-5"></i>删除</a>');d.on("click",function(){m(e,t,n,a,i)}),r.on("click",function(){g(e,t,n,a,i)}),$(e).html("").append(d).append(r)}}]});n=$(e).find(".table").dataTable(t)})},l={ignore:".ignore",errorPlacement:function(e,t){e.appendTo(t.parents(".form-group"))},rules:{name:{required:!0}},messages:{name:{required:"请填写设备类型名称"}}},u=function(e){var t=[];$.when.apply($,t).done(function(){$.isFunction(e)&&e()})},f={},p=function(){var e=$msg.dialog("deviceTypeAdd",function(e,t){return $(t).find("form").submit(),!1},{title:"新增设备类型"});$.when(e).then(function(e){var t=e.find("form");u(function(){t.formData(f),$.updateTextFields();var a=$.extend({},l,{submitHandler:function(a){var i={},d=t.formData();$.extend(i,d);var r=$api.post({url:$cfg.baseUrl+"/warehouseDevice/deviceType/add",data:$opt.paramFilter(i)});$.when(r).done(function(t){t.success?($toast.success(t.msg),$dict.remove("deviceType"),e.modal("hide"),n.fnUpdate()):$toast.warning(t.msg)}).fail(function(e){$toast.error(e)})}});t.validate(a),t.formDisable(!1)})})},m=function(e,t,a,i,d){var r=$msg.dialog("deviceTypeAdd",function(e,t){return $(t).find("form").submit(),!1},{title:"修改设备类型信息"});$.when(r).then(function(e){var t=e.find("form");u(function(){var i=$.extend({},l,{submitHandler:function(a){var i={},d=t.formData();$.extend(i,d);var r=$api.put({url:$cfg.baseUrl+"/warehouseDevice/deviceType/update",data:$opt.paramFilter(i)});$.when(r).done(function(t){t.success?($toast.success(t.msg),$dict.remove("deviceType"),e.modal("hide"),n.fnUpdate()):$toast.warning(t.msg)}).fail(function(e){$toast.error(e)})}});t.validate(i),t.formData($.extend({},f,a)),$.updateTextFields(),t.formDisable(!1)})})},g=function(e,t,a,i,d){$msg.confirm("是否删除该记录?",function(){var e=$api.del({url:$cfg.baseUrl+"/warehouseDevice/deviceType/delete/"+a.id});$.when(e).done(function(e){e.success?($toast.success(e.msg),$dict.remove("deviceType"),n.fnUpdate()):$toast.warning(e.msg)}).fail(function(e){$toast.error(e)})})},v=function(e){if(this.attach){a&&a.fnDestroy();var t=$.extend({},$plugin.datatable_server,{iDisplayLength:5,fnServerData:function(t,n,a){var i=$.extend({},$opt.paramConvert(n),$opt.paramFilter({page:!0,wareHouseId:e.find("#c_store1").val()})),d=$api.get({loading:!1,url:$cfg.baseUrl+"/warehouseDevice/select",data:i});$.when(d).done(function(t){e.trigger("loading.hide"),t.success?a($opt.dataConvert(t)):$toast.warning(t.msg)}).fail(function(t){e.trigger("loading.hide"),$toast.error(t)})},aaSorting:[[0,"desc"]],autoWidth:!1,info:"",columns:[{title:"入库时间",data:"addTime",class:"text-center",render:function(e,t,n){return moment(e).format("YYYY-MM-DD")}},{title:"设备类型",data:"deviceTypeId",class:"text-center",fnCreatedCell:function(e,t,n,a,i){$dict.get("deviceType").then(function(n){$.each(n,function(n,a){a.id===t&&$(e).html(a.name)})})}},{title:"型号",data:"model",class:"text-center"},{title:"状态",data:"state",class:"text-center",render:function(e,t,n){return{10:"全新",20:"良好",30:"损坏",40:"报废"}[e]}},{title:"数量",data:"num",class:"text-center"},{title:"操作",data:"id",bSortable:!1,fnCreatedCell:function(e,t,n,a,i){var d=$('<a class="btn btn-info btn-xs ml-5"><i class="fa fa-file-o"></i>查看</a>');d.on("click",function(){w(e,t,n,a,i)}),$(e).html("").append(d)}}]});a=e.find(".table").dataTable(t)}else{var n=$dict.initOptions({elem:"#c_store1",dict:"store",propText:"name",propId:"id"}),i=this;$.when(n).done(function(){i.attach=!0,i.initFunc.apply(i,[e])}),e.on("click","#btn_refresh1",function(){i.initFunc.apply(i,[e])}),e.on("click","#btn_add1",function(){var t=e.find("#c_store1").val();T(t)})}},h={state:10},b={ignore:".ignore",errorPlacement:function(e,t){e.appendTo(t.parents(".form-group"))},rules:{num:{required:!0},model:{required:!0}},messages:{num:{required:"数量不能为空"},model:{required:"型号不能为空"}}},x=function(e){var t,n=[];t=$dict.initOptions({elem:"#p_wareHouseId",dict:"store",propText:"name",propId:"id"}),n.push(t),t=$dict.initOptions({elem:"#p_deviceTypeId",dict:"deviceType",propText:"name",propId:"id"}),n.push(t),t=$dict.initOptions({elem:"#p_addUserId",dict:"user",propText:"name",propId:"id"}),n.push(t),$.when.apply($,n).done(function(){$.isFunction(e)&&e()})},T=function(e){var t=$msg.dialog("storeInAdd",function(e,t){return $(t).find("form").submit(),!1},{title:"新增入库"});$.when(t).then(function(t){var n=t.find("form");x(function(){n.formData($.extend(h,{wareHouseId:e})),$.updateTextFields();var i=$.extend({},b,{submitHandler:function(e){var i={},d=n.formData();$.extend(i,d);var r=$api.post({url:$cfg.baseUrl+"/warehouseDevice/add",data:$opt.paramFilter(i)});$.when(r).done(function(e){e.success?($toast.success(e.msg),t.modal("hide"),a.fnUpdate()):$toast.warning(e.msg)}).fail(function(e){$toast.error(e)})}});n.validate(i),n.formDisable(!1),$("#p_addUserId").parents(".col-md-6").addClass("hide"),$("#p_addTime").parents(".col-md-6").addClass("hide")})})},w=function(e,t,n,a,i){var d=$msg.dialog("storeInAdd",function(e,t){},{title:"入库信息",buttons:{success:{label:"确定",className:"btn-info"}}});$.when(d).then(function(e){var t=e.find("form");x(function(){t.formData($.extend({},h,n)),$.updateTextFields(),t.formDisable(!0)})})},y=function(e){if(this.attach){i&&i.fnDestroy();var t=$.extend({},$plugin.datatable_server,{iDisplayLength:5,fnServerData:function(t,n,a){var i=$.extend({},$opt.paramConvert(n),$opt.paramFilter({page:!0,wareHouseId:e.find("#c_store2").val()})),d=$api.get({loading:!1,url:$cfg.baseUrl+"/applyDevcie/select",data:i});$.when(d).done(function(t){e.trigger("loading.hide"),t.success?a($opt.dataConvert(t)):$toast.warning(t.msg)}).fail(function(t){e.trigger("loading.hide"),$toast.error(t)})},aaSorting:[[0,"desc"]],autoWidth:!1,info:"",columns:[{title:"申请时间",data:"applyTime",class:"text-center",render:function(e,t,n){return moment(e).format("YYYY-MM-DD")}},{title:"申请人",data:"applyUserId",class:"text-center",fnCreatedCell:function(e,t,n,a,i){$dict.get("user").then(function(n){$.each(n,function(n,a){a.id===t&&$(e).html(a.name)})})}},{title:"申请设备",data:"orderId",class:"text-center",fnCreatedCell:function(e,t,n,a,i){$(e).html("");var d=$api.get({loading:!1,url:$cfg.baseUrl+"/applyDevcie/load/"+t});$.when(d,$dict.get("deviceType")).done(function(t){if(t.success){var n=$.map(t.data.BasDeviceOrderDetails,function(e){return $dict.getItem("deviceType","id",e.deviceTypeId).name+" x "+e.num}).join(";");$(e).html(n)}else $toast.warning(t.msg)}).fail(function(e){$toast.error(e)})}},{title:"内容",data:"content",class:"text-center"},{title:"状态",data:"examine",class:"text-center",render:function(e,t,n){return 10===e?"待处理":"已出库"}},{title:"操作",data:"orderId",bSortable:!1,fnCreatedCell:function(e,t,n,a,i){if(10===n.examine){var d=$('<a class="btn btn-orange btn-xs ml-5"><i class="fa fa-mail-forward"></i>出库</a>');d.on("click",function(){H(e,t,n,a,i)}),$(e).html("").append(d)}else if(20===n.examine){var d=$('<a class="btn btn-info btn-xs ml-5"><i class="fa fa-file-o"></i>查看</a>');d.on("click",function(){S(e,t,n,a,i)}),$(e).html("").append(d)}else $(e).html("")}}]});i=e.find(".table1").dataTable(t),d&&d.fnDestroy(),t=$.extend({},$plugin.datatable_server,{iDisplayLength:5,fnServerData:function(t,n,a){var i=$.extend({},$opt.paramConvert(n),$opt.paramFilter({page:!0,wareHouseId:e.find("#c_store2").val()})),d=$api.get({loading:!1,url:$cfg.baseUrl+"/warehouseDevice/out",data:i});$.when(d).done(function(t){e.trigger("loading.hide"),t.success?a($opt.dataConvert(t)):$toast.warning(t.msg)}).fail(function(t){e.trigger("loading.hide"),$toast.error(t)})},aaSorting:[[0,"desc"]],autoWidth:!1,info:"",columns:[{title:"出库时间",data:"createTime",class:"text-center",render:function(e,t,n){return moment(e).format("YYYY-MM-DD")}},{title:"设备类型",data:"deviceTypeId",class:"text-center",fnCreatedCell:function(e,t,n,a,i){$dict.get("deviceType").then(function(n){$.each(n,function(n,a){a.id===t&&$(e).html(a.name)})})}},{title:"型号",data:"model",class:"text-center"},{title:"状态",data:"state",class:"text-center",render:function(e,t,n){return{10:"全新",20:"良好",30:"损坏",40:"报废"}[e]}},{title:"数量",data:"num",class:"text-center"},{title:"操作",data:"orderId",bSortable:!1,fnCreatedCell:function(e,t,n,a,i){var d=$('<a class="btn btn-info btn-xs ml-5"><i class="fa fa-file-o"></i>查看</a>');d.on("click",function(){S(e,t,n,a,i)}),$(e).html("").append(d)}}]}),d=e.find(".table2").dataTable(t)}else{var n=$dict.initOptions({elem:"#c_store2",dict:"store",propText:"name",propId:"id"}),a=this;$.when(n).done(function(){a.attach=!0,a.initFunc.apply(a,[e])}),e.on("click","#btn_refresh2",function(){a.initFunc.apply(a,[e])}),e.on("click","#btn_add2",function(){U()})}},D={},_={ignore:".ignore",errorPlacement:function(e,t){e.appendTo(t.parents(".form-group"))},rules:{},messages:{}},I=function(e){var t,n=[];t=$dict.initOptions({elem:"#p_deviceTypeId",dict:"deviceType",propText:"name",propId:"id"}),n.push(t),t=$dict.initOptions({elem:"#p_applyUserId",dict:"maintainer",propText:"name",propId:"id",filter:function(e,t){var n=$.userData.addvcd;return"0000"===n.slice(-4)||"00"===n.slice(-2)&&e.addvcd.slice(0,-2)===n.slice(0,-2)||n===e.addvcd}}),n.push(t),$.when.apply($,n).done(function(){$.isFunction(e)&&e()})},U=function(){var e=$msg.dialog("storeApplyAdd",function(e,t){return $(t).find("form").submit(),!1},{title:"新增出库申请"});$.when(e).then(function(e){var t=e.find("form");I(function(){t.formData($.extend(D)),$.updateTextFields();var n=$.extend({},_,{submitHandler:function(n){var a=t.find("#p_list"),r=a.data("device")||{};if(0===Object.keys(r).length)return void $toast.warning("申请设备不能为空");var o=t.formData(),s=new FormData;s.append("applyUserId",o.applyUserId),s.append("content",o.content),s.append("examine","10");var c=0;$.each(r,function(e,t){s.append("detailsList["+c+"].deviceTypeId",t.id),s.append("detailsList["+c+"].num",t.num),c++});var l=$api.post({contentType:!1,processData:!1,url:$cfg.baseUrl+"/applyDevcie/add",data:s});$.when(l).done(function(t){t.success?($toast.success(t.msg),e.modal("hide"),i.fnUpdate(),d.fnUpdate()):$toast.warning(t.msg)}).fail(function(e){$toast.error(e)})}});t.validate(n),t.formDisable(!1),t.find("#btn_add").on("click",function(){var n=t.find("#p_list"),a=n.data("device")||{},i=t.find("#p_deviceTypeId").val(),d=t.find("#p_deviceTypeId option:selected").text(),r=t.find("#p_num").val();if(!(r>0&&isNum(r)))return $toast.warning("数量必须大于0"),"";a[i]={id:i,name:d,num:r};var o="<ul>";$.each(a,function(e,t){o+="<li>"+t.name+" x "+t.num+'<a class="btn btn-danger btn-xs ml-5" data-id="'+t.id+'">删除</a></li>'}),o+="</ul>",n.html(o),e.resize(),n.data("device",a)}),t.find("#p_list").on("click",".btn",function(){var e=$(this).data("id"),n=t.find("#p_list"),a=n.data("device")||{};delete a[e],$(this).parents("li").remove(),n.data("device",a)})})})},C={state:10},F={ignore:".ignore",errorPlacement:function(e,t){e.appendTo(t.parents(".form-group"))},rules:{},messages:{}},O=function(e){var t,n=[];t=$dict.initOptions({elem:"#p_wareHouseId",dict:"store",propText:"name",propId:"id"}),n.push(t),t=$dict.initOptions({elem:"#p_deviceTypeId",dict:"deviceType",propText:"name",propId:"id"}),n.push(t),t=$dict.initOptions({elem:"#p_applyUserId",dict:"user",propText:"name",propId:"id"}),n.push(t),$.when.apply($,n).done(function(){$.isFunction(e)&&e()})},H=function(e,t,n,a,r){var o=$api.get({loading:!1,url:$cfg.baseUrl+"/applyDevcie/load/"+t});$.when(o,$dict.get("deviceType")).done(function(e){if(e.success){var a=$msg.dialog("storeExamineAdd",function(e,t){return $(t).find("form.info-right").submit(),!1},{title:"新增出库",modalClassName:"modal-lg",data:e.data});$.when(a).then(function(a){var r=a.find("form.info-left"),o=a.find("form.info-right"),s=0;O(function(){r.formData($.extend(C,n,{details:$.map(e.data.BasDeviceOrderDetails,function(e){return $dict.getItem("deviceType","id",e.deviceTypeId).name+" x "+e.num}).join(";")})),o.formData({}),$.updateTextFields(),s=r.height(),r.formDisable(!1),$("#p_orderId").attr("disabled","true"),$("#p_examine").attr("disabled","true"),$("#p_applyTime").attr("disabled","true"),$("#p_content").attr("disabled","true"),$("#p_applyUserId").attr("disabled","true");var c,l=[],u=function(e){var t=o.find("#p_wareHouseId").val(),n=$api.get({loading:!1,url:$cfg.baseUrl+"/WareHouse/surplus",data:{wareHouseId:t}});$.when(n).done(function(t){t.success?(l=t.data||[],$.isFunction(e)&&e.apply(e)):$toast.warning(t.msg)}).fail(function(e){$toast.error(e)})},f=function(){var e={},t=$.map(l,function(t){if(1!==e[t.model])return e[t.model]=1,'<option value="'+t.model+'">'+t.model+"</option>"});o.find("#p_model").css("width","100%").removeClass("select2-hidden-accessible"),c&&clearTimeout(c),c=setTimeout(function(){o.find("#p_model").html(t).select2(),p()},100)},p=function(){var e=(o.find("#p_deviceTypeId").val()||"").toString(),t=(o.find("#p_state").val()||"").toString(),n=(o.find("#p_model").val()||"").toString(),a=0;$.each(l,function(i,d){e===d.deviceTypeId.toString()&&t===d.state.toString()&&n===d.model.toString()&&(a=d.num)}),o.find("#p_count").html(a)};o.find("#p_deviceTypeId").on("change",function(){p()}),o.find("#p_state").on("change",function(){p()}),o.find("#p_model").on("change",function(){p()}),o.find("#p_wareHouseId").on("change",function(){u(function(){f();var e=o.find("#p_list");e.data("device",{}),e.html(""),a.resize(),r.height(Math.max(s,o.height()))})}),u(function(){f()}),o.find("#btn_add").on("click",function(){var e=o.find("#p_list"),n=e.data("device")||{},i=o.find("#p_deviceTypeId").val(),d=o.find("#p_deviceTypeId option:selected").text(),c=o.find("#p_state").val(),l=o.find("#p_state option:selected").text(),u=o.find("#p_model").val(),f=o.find("#p_num").val(),p=o.find("#p_count").text(),m=o.find("#p_wareHouseId").val(),g=t;if(!(f>0&&isNum(f)))return $toast.warning("数量必须大于0"),"";if(parseInt(f,10)>parseInt(p,10))return $toast.warning("数量不能大于大于库存"),"";n[i+"-"+u+"-"+c]={orderId:g,storeId:m,id:i,name:d,state:c,stateText:l,model:u,num:f};var v="<ul>";$.each(n,function(e,t){v+="<li>"+t.name+"【"+t.model+"】【"+t.stateText+"】 x "+t.num+'<a class="btn btn-danger btn-xs ml-5" data-id="'+t.id+'">删除</a></li>'}),v+="</ul>",e.html(v),e.data("device",n),a.resize(),r.height(Math.max(s,o.height()))}),o.find("#p_list").on("click",".btn",function(){var e=$(this).data("id"),t=o.find("#p_list"),n=t.data("device")||{};delete n[e],$(this).parents("li").remove(),t.data("device",n),r.height(Math.max(s,o.height()))});var m=$.extend({},F,{submitHandler:function(e){var n=o.find("#p_list"),r=n.data("device")||{};if(0===Object.keys(r).length)return void $toast.warning("出库设备不能为空");var s=(o.formData(),new FormData);s.append("orderId",t),s.append("examine","20");var c=0;$.each(r,function(e,t){s.append("deviceOutDetails["+c+"].orderId",t.orderId),s.append("deviceOutDetails["+c+"].wareHouseId",t.storeId),s.append("deviceOutDetails["+c+"].deviceTypeId",t.id),s.append("deviceOutDetails["+c+"].model",t.model),s.append("deviceOutDetails["+c+"].state",t.state),s.append("deviceOutDetails["+c+"].num",t.num),c++});var l=$api.post({contentType:!1,processData:!1,url:$cfg.baseUrl+"/applyDevcie/examine",data:s});$.when(l).done(function(e){e.success?($toast.success(e.msg),a.modal("hide"),i.fnUpdate(),d.fnUpdate()):$toast.warning(e.msg)}).fail(function(e){$toast.error(e)})}});o.validate(m)})})}else $toast.warning(e.msg)}).fail(function(e){$toast.error(e)})},S=function(e,t,n,a,i){var d=$api.get({loading:!1,url:$cfg.baseUrl+"/applyDevcie/load/"+t});$.when(d,$dict.get("deviceType")).done(function(e){if(e.success){var a=$msg.dialog("storeOutAdd",function(e,t){return!1},{title:"出库信息",modalClassName:"modal-lg",buttons:{success:{label:"确定",className:"btn-info"}}});$.when(a).then(function(a){var i=a.find("form.info-left"),d=a.find("form.info-right"),r=0;O(function(){i.formData($.extend(C,n,{details:$.map(e.data.BasDeviceOrderDetails,function(e){return $dict.getItem("deviceType","id",e.deviceTypeId).name+" x "+e.num}).join(";")})),d.formData({}),$.updateTextFields(),r=i.height(),i.formDisable(!1),$("#p_orderId").attr("disabled","true"),$("#p_examine").attr("disabled","true"),$("#p_applyTime").attr("disabled","true"),$("#p_content").attr("disabled","true"),$("#p_applyUserId").attr("disabled","true");var o=$.extend({},$plugin.datatable_server,{fnServerData:function(e,n,o){var s=$.extend({},$opt.paramConvert(n),$opt.paramFilter({page:!0,orderId:t})),c=$api.get({loading:!1,url:$cfg.baseUrl+"/warehouseDevice/out",data:s});$.when(c).done(function(e){e.success?(o($opt.dataConvert(e)),a.resize(),i.height(Math.max(r,d.height()))):$toast.warning(e.msg)}).fail(function(e){$toast.error(e)})},aaSorting:[[0,"desc"]],autoWidth:!1,info:"",columns:[{title:"出库时间",data:"createTime",class:"text-center",render:function(e,t,n){return moment(e).format("YYYY-MM-DD")}},{title:"设备类型",data:"deviceTypeId",class:"text-center",fnCreatedCell:function(e,t,n,a,i){$dict.get("deviceType").then(function(n){$.each(n,function(n,a){a.id===t&&$(e).html(a.name)})})}},{title:"型号",data:"model",class:"text-center"},{title:"状态",data:"state",class:"text-center",render:function(e,t,n){return{10:"全新",20:"良好",30:"损坏",40:"报废"}[e]}},{title:"数量",data:"num",class:"text-center"}]});d.find(".table").dataTable(o)})})}else $toast.warning(e.msg)}).fail(function(e){$toast.error(e)})},k=function(){var t=[{elem:$("#p_panel_1").get(0),data:{title:"入库管理"},initFunc:v},{elem:$("#p_panel_2").get(0),data:{title:"出库管理"},initFunc:y}],n=$(".panel-list");n.html(""),$.map(t,function(t){var a=t.html||t.elem.innerHTML.replace(/\[\[/g,"{{").replace(/]]/g,"}}"),i=e.compile(a)(t.data||{}),d=$(i);d.on("refresh",function(){$.isFunction(t.initFunc)&&(d.trigger("loading.show"),t.initFunc.apply(t,[d]))}),n.append(d),$.isFunction(t.initFunc)&&(t.attach=!1,t.initFunc.apply(t,[d]))})},A=function(){t&&t.fnDestroy();var e=$.extend({},$plugin.datatable_server,{fnServerData:function(e,t,n){var a=$.extend({},$opt.paramConvert(t),$opt.paramFilter({})),i=$api.get({url:$cfg.baseUrl+"/WareHouse/select",data:a});$.when(i).done(function(e){e.success?n($opt.dataConvert(e)):$toast.warning(e.msg)}).fail(function(e){$toast.error(e)})},aaSorting:[[0,"desc"]],columns:[{title:"仓库名称",data:"name",class:"text-center"},{title:"所属政区",data:"addvcd",class:"text-center",fnCreatedCell:function(e,t,n,a,i){$dict.get("addvcd").then(function(n){$.each(n,function(n,a){a.addvcd===t&&$(e).html(a.addvnm)})})}},{title:"所在地址",data:"address",class:"text-center"},{title:"负责人",data:"responsibilityId",class:"text-center",fnCreatedCell:function(e,t,n,a,i){$dict.get("user").then(function(n){$.each(n,function(n,a){a.id===t&&$(e).html(a.name)})})}},{title:"联系电话",data:"responsibilityId",class:"text-center",fnCreatedCell:function(e,t,n,a,i){$dict.get("user").then(function(n){var a="";$.each(n,function(e,n){n.id===t&&n.phone&&(a='<a><i class="fa fa-phone-square text-success ml-5"></i> '+n.phone+"</a>")}),$(e).html(a)})}},{title:"操作",data:"id",bSortable:!1,fnCreatedCell:function(e,t,n,a,i){var d=$('<a class="btn btn-warning btn-xs ml-5"><i class="fa fa-edit"></i>修改</a>'),r=$('<a class="btn btn-danger btn-xs ml-5"><i class="fa fa-trash-o"></i>删除</a>'),o=$('<a class="btn btn-info btn-xs ml-5"><i class="fa fa-file-o"></i>查看</a>');d.on("click",function(){N(e,t,n,a,i)}),r.on("click",function(){q(e,t,n,a,i)}),o.on("click",function(){L(e,t,n,a,i)}),$(e).html("").append(d).append(r).append(o)}}]});t=$("#t_store").dataTable(e)},Y=function(e){var t,n=[];t=$dict.initOptions({s2s:!0,elem:"#p_addvcd",dict:"addvcd",propText:"addvnm",propId:"addvcd",filter:function(e,t){var n=$.userData.addvcd;return"0000"===n.slice(-4)||"00"===n.slice(-2)&&e.addvcd.slice(0,-2)===n.slice(0,-2)||n===e.addvcd}}),n.push(t),t=$dict.initOptions({elem:"#p_responsibilityId",dict:"user",propText:"name",propId:"id"}),n.push(t),$.when.apply($,n).done(function(){$.isFunction(e)&&e()})},M={},W=function(){var e=$msg.dialog("storeAdd",function(e,t){return $(t).find("form").submit(),!1},{title:"新增仓库"});$.when(e).then(function(e){var n=e.find("form");Y(function(){n.formData(M),$.updateTextFields();var a=$.extend({},r,{submitHandler:function(a){var i={},d=n.formData();$.extend(i,d);var r=$api.post({url:$cfg.baseUrl+"/WareHouse/add",data:$opt.paramFilter(i)});$.when(r).done(function(n){n.success?($toast.success(n.msg),e.modal("hide"),$dict.remove("store"),t.fnUpdate()):$toast.warning(n.msg)}).fail(function(e){$toast.error(e)})}});n.validate(a),n.formDisable(!1)})})},N=function(e,n,a,i,d){var o=$msg.dialog("storeAdd",function(e,t){return $(t).find("form").submit(),!1},{title:"修改仓库信息",modalClassName:"modal-lg"}),s=$api.get({url:$cfg.baseUrl+"/WareHouse/load/"+a.id});$.when(o,s).then(function(e,n){var i=e.find("form");Y(function(){var d=$.extend({},r,{submitHandler:function(n){var d={},r=i.formData();$.extend(d,r);var o=$api.put({url:$cfg.baseUrl+"/WareHouse/update/"+a.id,data:$opt.paramFilter(d)});$.when(o).done(function(n){n.success?($toast.success(n.msg),e.modal("hide"),$dict.remove("store"),t.fnUpdate()):$toast.warning(n.msg)}).fail(function(e){$toast.error(e)})}});i.validate(d),n.success?($toast.success(n.msg),i.formData($.extend({},M,n.data)),$.updateTextFields(),i.formDisable(!1)):($toast.warning(n.msg),e.modal("hide"))})})},q=function(e,n,a,i,d){$msg.confirm("是否删除该记录?",function(){var e=$api.del({url:$cfg.baseUrl+"/WareHouse/delete/"+a.id});$.when(e).done(function(e){e.success?($toast.success(e.msg),$dict.remove("store"),t.fnUpdate()):$toast.warning(e.msg)}).fail(function(e){$toast.error(e)})})},L=function(e,t,n,a,i){var d=$msg.dialog("storeAdd",function(e,t){},{title:"仓库信息",modalClassName:"modal-lg",buttons:{success:{label:"确定",className:"btn-info"}}}),r=$api.get({url:$cfg.baseUrl+"/WareHouse/load/"+n.id});$.when(d,r).then(function(e,t){var n=e.find("form");Y(function(){n.formDisable(!0),t.success?($toast.success(t.msg),n.formData($.extend({},M,t.data)),$.updateTextFields(),n.formDisable(!0)):($toast.warning(t.msg),e.modal("hide"))})})};return{data:{model:[{icon:"fa fa-cube",title:"备件管理"},{icon:"",title:"仓库管理"}]},init:function(e){console.log(e+":init"),o(),s()},dispose:function(e){console.log(e+":dispose")}}});