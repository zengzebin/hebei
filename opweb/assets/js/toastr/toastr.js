!function(e){e(["jquery"],function(e){return function(){function t(e,t,n){return m({type:O.error,iconClass:g().iconClasses.error,message:e,optionsOverride:n,title:t})}function n(t,n){return t||(t=g()),v=e("#"+t.containerId),v.length?v:(n&&(v=l(t)),v)}function i(e,t,n){return m({type:O.info,iconClass:g().iconClasses.info,message:e,optionsOverride:n,title:t})}function o(e){w=e}function s(e,t,n){return m({type:O.success,iconClass:g().iconClasses.success,message:e,optionsOverride:n,title:t})}function a(e,t,n){return m({type:O.warning,iconClass:g().iconClasses.warning,message:e,optionsOverride:n,title:t})}function r(e,t){var i=g();v||n(i),c(e,i,t)||u(i)}function d(t){var i=g();if(v||n(i),t&&0===e(":focus",t).length)return void h(t);v.children().length&&v.remove()}function u(t){for(var n=v.children(),i=n.length-1;i>=0;i--)c(e(n[i]),t)}function c(t,n,i){var o=!(!i||!i.force)&&i.force;return!(!t||!o&&0!==e(":focus",t).length)&&(t[n.hideMethod]({duration:n.hideDuration,easing:n.hideEasing,complete:function(){h(t)}}),!0)}function l(t){return v=e("<div/>").attr("id",t.containerId).addClass(t.positionClass).attr("aria-live","polite").attr("role","alert"),v.appendTo(e(t.target)),v}function f(){return{tapToDismiss:!0,toastClass:"toast",containerId:"toast-container",debug:!1,showMethod:"fadeIn",showDuration:300,showEasing:"swing",onShown:void 0,hideMethod:"fadeOut",hideDuration:1e3,hideEasing:"swing",onHidden:void 0,extendedTimeOut:1e3,iconClasses:{error:"toast-error",info:"toast-info",success:"toast-success",warning:"toast-warning"},iconClass:"toast-info",positionClass:"toast-top-right",timeOut:5e3,titleClass:"toast-title",messageClass:"toast-message",target:"body",closeHtml:'<button type="button">&times;</button>',newestOnTop:!0,preventDuplicates:!1,progressBar:!1}}function p(e){w&&w(e)}function m(t){function i(){t.iconClass&&b.addClass(m.toastClass).addClass(w)}function o(){m.newestOnTop?v.prepend(b):v.append(b)}function s(){t.title&&(D.append(t.title).addClass(m.titleClass),b.append(D))}function a(){t.message&&(x.append(t.message).addClass(m.messageClass),b.append(x))}function r(){m.closeButton&&(E.addClass("toast-close-button").attr("role","button"),b.prepend(E))}function d(){m.progressBar&&(y.addClass("toast-progress"),b.prepend(y))}function u(t){if(!e(":focus",b).length||t)return clearTimeout(H.intervalId),b[m.hideMethod]({duration:m.hideDuration,easing:m.hideEasing,complete:function(){h(b),m.onHidden&&"hidden"!==I.state&&m.onHidden(),I.state="hidden",I.endTime=new Date,p(I)}})}function c(){(m.timeOut>0||m.extendedTimeOut>0)&&(O=setTimeout(u,m.extendedTimeOut),H.maxHideTime=parseFloat(m.extendedTimeOut),H.hideEta=(new Date).getTime()+H.maxHideTime)}function l(){clearTimeout(O),H.hideEta=0,b.stop(!0,!0)[m.showMethod]({duration:m.showDuration,easing:m.showEasing})}function f(){var e=(H.hideEta-(new Date).getTime())/H.maxHideTime*100;y.width(e+"%")}var m=g(),w=t.iconClass||m.iconClass;if(void 0!==t.optionsOverride&&(m=e.extend(m,t.optionsOverride),w=t.optionsOverride.iconClass||w),!function(e,t){if(e.preventDuplicates){if(t.message===C)return!0;C=t.message}return!1}(m,t)){T++,v=n(m,!0);var O=null,b=e("<div/>"),D=e("<div/>"),x=e("<div/>"),y=e("<div/>"),E=e(m.closeHtml),H={intervalId:null,hideEta:null,maxHideTime:null},I={toastId:T,state:"visible",startTime:new Date,options:m,map:t};return function(){i(),s(),a(),r(),d(),o()}(),function(){b.hide(),b[m.showMethod]({duration:m.showDuration,easing:m.showEasing,complete:m.onShown}),m.timeOut>0&&(O=setTimeout(u,m.timeOut),H.maxHideTime=parseFloat(m.timeOut),H.hideEta=(new Date).getTime()+H.maxHideTime,m.progressBar&&(H.intervalId=setInterval(f,10)))}(),function(){b.hover(l,c),!m.onclick&&m.tapToDismiss&&b.click(u),m.closeButton&&E&&E.click(function(e){e.stopPropagation?e.stopPropagation():void 0!==e.cancelBubble&&!0!==e.cancelBubble&&(e.cancelBubble=!0),u(!0)}),m.onclick&&b.click(function(){m.onclick(),u()})}(),p(I),m.debug&&console&&console.log(I),b}}function g(){return e.extend({},f(),b.options)}function h(e){v||(v=n()),e.is(":visible")||(e.remove(),e=null,0===v.children().length&&(v.remove(),C=void 0))}var v,w,C,T=0,O={error:"error",info:"info",success:"success",warning:"warning"},b={clear:r,remove:d,error:t,getContainer:n,info:i,options:{},subscribe:o,success:s,version:"2.1.1",warning:a};return b}()})}("function"==typeof define&&define.amd?define:function(e,t){"undefined"!=typeof module&&module.exports?module.exports=t(require("jquery")):window.toastr=t(window.jQuery)});