!function(e){"function"==typeof define&&define.amd?define(["jquery","datatables.net-bs4","datatables.net-responsive"],function(d){return e(d,window,document)}):"object"==typeof exports?module.exports=function(d,a){return d||(d=window),a&&a.fn.dataTable||(a=require("datatables.net-bs4")(d,a).$),a.fn.dataTable.Responsive||require("datatables.net-responsive")(d,a),e(a,d,d.document)}:e(jQuery,window,document)}(function(e,d,a,n){"use strict";var t=e.fn.dataTable,o=t.Responsive.display,i=o.modal,s=e('<div class="modal fade dtr-bs-modal" role="dialog"><div class="modal-dialog" role="document"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"/></div></div></div>');return o.modal=function(d){return function(a,n,t){e.fn.modal?n||(d&&d.header&&s.find("div.modal-header").empty().append('<h4 class="modal-title">'+d.header(a)+"</h4>"),s.find("div.modal-body").empty().append(t()),s.appendTo("body").modal()):i(a,n,t)}},t.Responsive});