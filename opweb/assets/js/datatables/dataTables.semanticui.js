!function(e){"function"==typeof define&&define.amd?define(["jquery","datatables.net"],function(a){return e(a,window,document)}):"object"==typeof exports?module.exports=function(a,t){return a||(a=window),t&&t.fn.dataTable||(t=require("datatables.net")(a,t).$),e(t,a,a.document)}:e(jQuery,window,document)}(function(e,a,t,n){"use strict";var i=e.fn.dataTable;return e.extend(!0,i.defaults,{dom:"<'ui grid'<'row'<'eight wide column'l><'right aligned eight wide column'f>><'row dt-table'<'sixteen wide column'tr>><'row'<'seven wide column'i><'right aligned nine wide column'p>>>",renderer:"semanticUI"}),e.extend(i.ext.classes,{sWrapper:"dataTables_wrapper dt-semanticUI",sFilter:"dataTables_filter ui input",sProcessing:"dataTables_processing ui segment",sPageButton:"paginate_button item"}),i.ext.renderer.pageButton.semanticUI=function(a,n,d,s,r,o){var l,c,u,f=new i.Api(a),p=a.oClasses,b=a.oLanguage.oPaginate,g=a.oLanguage.oAria.paginate||{},m=0,w=function(t,n){var i,s,u,x,v=function(a){a.preventDefault(),e(a.currentTarget).hasClass("disabled")||f.page()==a.data.action||f.page(a.data.action).draw("page")};for(i=0,s=n.length;i<s;i++)if(x=n[i],e.isArray(x))w(t,x);else{switch(l="",c="",x){case"ellipsis":l="&#x2026;",c="disabled";break;case"first":l=b.sFirst,c=x+(r>0?"":" disabled");break;case"previous":l=b.sPrevious,c=x+(r>0?"":" disabled");break;case"next":l=b.sNext,c=x+(r<o-1?"":" disabled");break;case"last":l=b.sLast,c=x+(r<o-1?"":" disabled");break;default:l=x+1,c=r===x?"active":""}var h=-1===c.indexOf("disabled")?"a":"div";l&&(u=e("<"+h+">",{class:p.sPageButton+" "+c,id:0===d&&"string"==typeof x?a.sTableId+"_"+x:null,href:"#","aria-controls":a.sTableId,"aria-label":g[x],"data-dt-idx":m,tabindex:a.iTabIndex}).html(l).appendTo(t),a.oApi._fnBindAction(u,{action:x},v),m++)}};try{u=e(n).find(t.activeElement).data("dt-idx")}catch(e){}w(e(n).empty().html('<div class="ui pagination menu"/>').children(),s),u&&e(n).find("[data-dt-idx="+u+"]").focus()},e(t).on("init.dt",function(a,t){if("dt"===a.namespace&&e.fn.dropdown){var n=new e.fn.dataTable.Api(t);e("div.dataTables_length select",n.table().container()).dropdown()}}),i});