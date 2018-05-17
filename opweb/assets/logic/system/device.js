define(["datatables","bootstrap-datetimepickerCN"],function(){var t,e={ignore:".ignore",errorPlacement:function(t,e){t.appendTo(e.parents(".form-group"))},rules:{address:{required:!0}},messages:{address:{required:"请填写RTU地址"}}},a=function(){$dict.init({name:"ownerUnits",url:$cfg.baseUrl+"/ownerUnits/qlist/"}),$dict.init({name:"addvcd",url:$cfg.baseUrl+"/addvcd/loadAll/"}),$dict.init({name:"station",url:$cfg.baseUrl+"/stinfo/loadAll/"})},n=function(){$("#c_startTime").datetimepicker({format:"yyyy-mm-dd hh:ii",autoclose:!0,todayBtn:!0,pickerPosition:"bottom-left"}),$("#c_endTime").datetimepicker({format:"yyyy-mm-dd hh:ii",autoclose:!0,todayBtn:!0,pickerPosition:"bottom-left"});var t,e=[];t=$dict.initOptions({s2s:!0,elem:"#c_addvcd",dict:"addvcd",propText:"addvnm",propId:"addvcd",append:[{id:"-1",text:"全部"}],filter:function(t,e){var a=$.userData.addvcd;return"0000"===a.slice(-4)||"00"===a.slice(-2)&&t.addvcd.slice(0,-2)===a.slice(0,-2)||a===t.addvcd}}),e.push(t),$.when.apply($,e).done(function(){i()}),$("#b_refresh,#b_search").on("click",function(){i()}),$("#b_add").on("click",function(){s()})},i=function(){t&&t.fnDestroy();var e=$.extend({},$plugin.datatable_server,{fnServerData:function(t,e,a){var n=$.extend({},$opt.paramConvert(e),$opt.paramFilter({startTime:$("#c_startTime").val(),endTime:$("#c_endTime").val(),address:$("#c_address").val(),deviceName:$("#c_deviceName").val(),serviceType:$("#c_serviceType").val(),addvcd:$("#c_addvcd").val()})),i=$api.get({url:$cfg.baseUrl+"/device/select",data:n});$.when(i).done(function(t){t.success?a($opt.dataConvert(t)):$toast.warning(t.msg)}).fail(function(t){$toast.error(t)})},aaSorting:[[0,"desc"]],columns:[{title:"RTU地址",data:"address",class:"text-center"},{title:"设备名称",data:"deviceName",class:"text-center"},{title:"设备型号",data:"deviceModel",class:"text-center"},{title:"测站编码",data:"stcd",class:"text-center"},{title:"归属测站",data:"stcd",class:"text-center",fnCreatedCell:function(t,e,a,n,i){$dict.get("station").then(function(a){$.each(a,function(a,n){n.stcd===e&&$(t).html(n.stnm)})})}},{title:"归属区域",data:"addvcd",class:"text-center",fnCreatedCell:function(t,e,a,n,i){$dict.get("addvcd").then(function(a){$.each(a,function(a,n){n.addvcd===e&&$(t).html(n.addvnm)})})}},{title:"归属单位",data:"ownerId",class:"text-center"},{title:"安装时间",data:"installTime",class:"text-center"},{title:"是否有效",data:"delFlag",class:"text-center",render:function(t,e,a){return 1===t?"是":"否"}},{title:"操作",data:"id",bSortable:!1,fnCreatedCell:function(t,e,a,n,i){var d=$('<a class="btn btn-warning btn-xs ml-5"><i class="fa fa-edit"></i>修改</a>'),c=$('<a class="btn btn-danger btn-xs ml-5"><i class="fa fa-trash-o"></i>删除</a>'),s=$('<a class="btn btn-info btn-xs ml-5"><i class="fa fa-file-o"></i>查看</a>');d.on("click",function(){o(t,e,a,n,i)}),c.on("click",function(){r(t,e,a,n,i)}),s.on("click",function(){l(t,e,a,n,i)}),$(t).html("").append(d).append(c).append(s)}}]});t=$("#t_device").dataTable(e)},d=function(t){var e,a=[];e=$dict.initOptions({s2s:!0,elem:"#p_addvcd",dict:"addvcd",propText:"addvnm",propId:"addvcd",filter:function(t,e){var a=$.userData.addvcd;return"0000"===a.slice(-4)||"00"===a.slice(-2)&&t.addvcd.slice(0,-2)===a.slice(0,-2)||a===t.addvcd}}),a.push(e),e=$dict.initOptions({elem:"#p_stcd",dict:"station",propText:function(t){return t.stcd+" "+t.stnm},propId:"stcd"}),a.push(e),$.when.apply($,a).done(function(){$.isFunction(t)&&t()}),$("#p_installTime").datetimepicker({format:"yyyy-mm-dd hh:ii",autoclose:!0,todayBtn:!0})},c={since:0},s=function(){var a=$msg.dialog("deviceAdd",function(t,e){return $(e).find("form").submit(),!1},{title:"新增站点",modalClassName:"modal-lg"});$.when(a).then(function(a){var n=a.find("form");d(function(){n.formData(c),$.updateTextFields();var i=$.extend({},e,{submitHandler:function(e){var i={},d=n.formData();$.extend(i,d);var c=$api.post({url:$cfg.baseUrl+"/device/add",data:$opt.paramFilter(i)});$.when(c).done(function(e){e.success?($toast.success(e.msg),a.modal("hide"),$dict.remove("device"),t.fnUpdate()):$toast.warning(e.msg)}).fail(function(t){$toast.error(t)})}});n.validate(i),n.formDisable(!1)})})},o=function(a,n,i,s,o){var r=$msg.dialog("deviceAdd",function(t,e){return $(e).find("form").submit(),!1},{title:"修改站点信息",modalClassName:"modal-lg"}),l=$api.get({url:$cfg.baseUrl+"/device/load/"+i.id});$.when(r,l).then(function(a,n){var i=a.find("form");d(function(){var d=$.extend({},e,{submitHandler:function(e){var n={},d=i.formData();$.extend(n,d);var c=$api.put({url:$cfg.baseUrl+"/device/update/",data:$opt.paramFilter(n)});$.when(c).done(function(e){e.success?($toast.success(e.msg),a.modal("hide"),$dict.remove("device"),t.fnUpdate()):$toast.warning(e.msg)}).fail(function(t){$toast.error(t)})}});i.validate(d),n.success?($toast.success(n.msg),i.formData($.extend({},c,n.data)),$.updateTextFields(),i.formDisable(!1)):($toast.warning(n.msg),a.modal("hide"))})})},r=function(e,a,n,i,d){$msg.confirm("是否删除该记录?",function(){var e=$api.del({url:$cfg.baseUrl+"/device/delete/"+n.id});$.when(e).done(function(e){e.success?($toast.success(e.msg),$dict.remove("device"),t.fnUpdate()):$toast.warning(e.msg)}).fail(function(t){$toast.error(t)})})},l=function(t,e,a,n,i){var s=$msg.dialog("deviceAdd",function(t,e){},{title:"站点信息",modalClassName:"modal-lg",buttons:{success:{label:"确定",className:"btn-info"}}}),o=$api.get({url:$cfg.baseUrl+"/device/load/"+a.id});$.when(s,o).then(function(t,e){var a=t.find("form");d(function(){a.formDisable(!0),e.success?($toast.success(e.msg),a.formData($.extend({},c,e.data)),$.updateTextFields(),a.formDisable(!0)):($toast.warning(e.msg),t.modal("hide"))})})};return{data:{model:[{icon:"fa fa-cog",title:"系统管理"},{icon:"",title:"站点设备配置"}]},init:function(t){console.log(t+":init"),a(),n()},dispose:function(t){console.log(t+":dispose")}}});