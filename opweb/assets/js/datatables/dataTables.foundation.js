!function(a){"function"==typeof define&&define.amd?define(["jquery","datatables.net"],function(e){return a(e,window,document)}):"object"==typeof exports?module.exports=function(e,n){return e||(e=window),n&&n.fn.dataTable||(n=require("datatables.net")(e,n).$),a(n,e,e.document)}:a(jQuery,window,document)}(function(a,e,n,l){"use strict";var t=a.fn.dataTable,s=a('<meta class="foundation-mq"/>').appendTo("head");return t.ext.foundationVersion=s.css("font-family").match(/small|medium|large/)?6:5,s.remove(),a.extend(t.ext.classes,{sWrapper:"dataTables_wrapper dt-foundation",sProcessing:"dataTables_processing panel"}),a.extend(!0,t.defaults,{dom:"<'row'<'small-6 columns'l><'small-6 columns'f>r>t<'row'<'small-6 columns'i><'small-6 columns'p>>",renderer:"foundation"}),t.ext.renderer.pageButton.foundation=function(e,n,l,s,i,o){var r,u,d,c=new t.Api(e),f=e.oClasses,p=e.oLanguage.oPaginate,b=e.oLanguage.oAria.paginate||{},m=5===t.ext.foundationVersion,g=function(n,t){var s,v,x,w,T=function(e){e.preventDefault(),a(e.currentTarget).hasClass("unavailable")||c.page()==e.data.action||c.page(e.data.action).draw("page")};for(s=0,v=t.length;s<v;s++)if(w=t[s],a.isArray(w))g(n,w);else{switch(r="",u="",d=null,w){case"ellipsis":r="&#x2026;",u="unavailable disabled",d=null;break;case"first":r=p.sFirst,u=w+(i>0?"":" unavailable disabled"),d=i>0?"a":null;break;case"previous":r=p.sPrevious,u=w+(i>0?"":" unavailable disabled"),d=i>0?"a":null;break;case"next":r=p.sNext,u=w+(i<o-1?"":" unavailable disabled"),d=i<o-1?"a":null;break;case"last":r=p.sLast,u=w+(i<o-1?"":" unavailable disabled"),d=i<o-1?"a":null;break;default:r=w+1,u=i===w?"current":"",d=i===w?null:"a"}m&&(d="a"),r&&(x=a("<li>",{class:f.sPageButton+" "+u,"aria-controls":e.sTableId,"aria-label":b[w],tabindex:e.iTabIndex,id:0===l&&"string"==typeof w?e.sTableId+"_"+w:null}).append(d?a("<"+d+"/>",{href:"#"}).html(r):r).appendTo(n),e.oApi._fnBindAction(x,{action:w},T))}};g(a(n).empty().html('<ul class="pagination"/>').children("ul"),s)},t});