!function(e){"function"==typeof define&&define.amd?define(["jquery","datatables.net"],function(a){return e(a,window,document)}):"object"==typeof exports?module.exports=function(a,t){return a||(a=window),t&&t.fn.dataTable||(t=require("datatables.net")(a,t).$),e(t,a,a.document)}:e(jQuery,window,document)}(function(e,a,t,l){"use strict";var d=e.fn.dataTable;return e.extend(!0,d.defaults,{dom:"<'mdl-grid'<'mdl-cell mdl-cell--6-col'l><'mdl-cell mdl-cell--6-col'f>><'mdl-grid dt-table'<'mdl-cell mdl-cell--12-col'tr>><'mdl-grid'<'mdl-cell mdl-cell--4-col'i><'mdl-cell mdl-cell--8-col'p>>",renderer:"material"}),e.extend(d.ext.classes,{sWrapper:"dataTables_wrapper form-inline dt-material",sFilterInput:"form-control input-sm",sLengthSelect:"form-control input-sm",sProcessing:"dataTables_processing panel panel-default"}),d.ext.renderer.pageButton.material=function(a,l,n,i,r,s){var o,c,u,m=new d.Api(a),f=(a.oClasses,a.oLanguage.oPaginate),b=a.oLanguage.oAria.paginate||{},p=0,g=function(t,l){var d,i,u,x,w,T=function(a){a.preventDefault(),e(a.currentTarget).hasClass("disabled")||m.page()==a.data.action||m.page(a.data.action).draw("page")};for(d=0,i=l.length;d<i;d++)if(x=l[d],e.isArray(x))g(t,x);else{switch(o="",w=!1,x){case"ellipsis":o="&#x2026;",c="disabled";break;case"first":o=f.sFirst,c=x+(r>0?"":" disabled");break;case"previous":o=f.sPrevious,c=x+(r>0?"":" disabled");break;case"next":o=f.sNext,c=x+(r<s-1?"":" disabled");break;case"last":o=f.sLast,c=x+(r<s-1?"":" disabled");break;default:o=x+1,c="",w=r===x}w&&(c+=" mdl-button--raised mdl-button--colored"),o&&(u=e("<button>",{class:"mdl-button "+c,id:0===n&&"string"==typeof x?a.sTableId+"_"+x:null,"aria-controls":a.sTableId,"aria-label":b[x],"data-dt-idx":p,tabindex:a.iTabIndex,disabled:-1!==c.indexOf("disabled")}).html(o).appendTo(t),a.oApi._fnBindAction(u,{action:x},T),p++)}};try{u=e(l).find(t.activeElement).data("dt-idx")}catch(e){}g(e(l).empty().html('<div class="pagination"/>').children(),i),u&&e(l).find("[data-dt-idx="+u+"]").focus()},d});