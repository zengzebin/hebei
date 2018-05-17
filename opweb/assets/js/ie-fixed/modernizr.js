!function(e,t,n){function r(e){return e.replace(/([A-Z])/g,function(e,t){return"-"+t.toLowerCase()}).replace(/^ms-/,"-ms-")}function o(e,t){return typeof e===t}function i(e){return e.replace(/([a-z])-([a-z])/g,function(e,t,n){return t+n.toUpperCase()}).replace(/^-/,"")}function a(e,t){return!!~(""+e).indexOf(t)}function s(){return"function"!=typeof t.createElement?t.createElement(arguments[0]):z?t.createElementNS.call(t,"http://www.w3.org/2000/svg",arguments[0]):t.createElement.apply(t,arguments)}function l(e){var t=P.className,n=E._config.classPrefix||"";if(z&&(t=t.baseVal),E._config.enableJSClass){var r=new RegExp("(^|\\s)"+n+"no-js(\\s|$)");t=t.replace(r,"$1"+n+"js$2")}E._config.enableClasses&&(t+=" "+n+e.join(" "+n),z?P.className.baseVal=t:P.className=t)}function u(e,t){if("object"==typeof e)for(var n in e)T(e,n)&&u(n,e[n]);else{e=e.toLowerCase();var r=e.split("."),o=E[r[0]];if(2==r.length&&(o=o[r[1]]),void 0!==o)return E;t="function"==typeof t?t():t,1==r.length?E[r[0]]=t:(!E[r[0]]||E[r[0]]instanceof Boolean||(E[r[0]]=new Boolean(E[r[0]])),E[r[0]][r[1]]=t),l([(t&&0!=t?"":"no-")+r.join("-")]),E._trigger(e,t)}return E}function c(){var e=t.body;return e||(e=s(z?"svg":"body"),e.fake=!0),e}function f(e,n,r,o){var i,a,l,u,f="modernizr",d=s("div"),p=c();if(parseInt(r,10))for(;r--;)l=s("div"),l.id=o?o[r]:f+(r+1),d.appendChild(l);return i=s("style"),i.type="text/css",i.id="s"+f,(p.fake?p:d).appendChild(i),p.appendChild(d),i.styleSheet?i.styleSheet.cssText=e:i.appendChild(t.createTextNode(e)),d.id=f,p.fake&&(p.style.background="",p.style.overflow="hidden",u=P.style.overflow,P.style.overflow="hidden",P.appendChild(p)),a=n(d,e),p.fake?(p.parentNode.removeChild(p),P.style.overflow=u,P.offsetHeight):d.parentNode.removeChild(d),!!a}function d(e,t){return function(){return e.apply(t,arguments)}}function p(e,t,n){var r;for(var i in e)if(e[i]in t)return!1===n?e[i]:(r=t[e[i]],o(r,"function")?d(r,n||t):r);return!1}function m(t,n,r){var o;if("getComputedStyle"in e){o=getComputedStyle.call(e,t,n);var i=e.console;if(null!==o)r&&(o=o.getPropertyValue(r));else if(i){var a=i.error?"error":"log";i[a].call(i,"getComputedStyle returning null, its possible modernizr test results are inaccurate")}}else o=!n&&t.currentStyle&&t.currentStyle[r];return o}function v(t,o){var i=t.length;if("CSS"in e&&"supports"in e.CSS){for(;i--;)if(e.CSS.supports(r(t[i]),o))return!0;return!1}if("CSSSupportsRule"in e){for(var a=[];i--;)a.push("("+r(t[i])+":"+o+")");return a=a.join(" or "),f("@supports ("+a+") { #modernizr { position: absolute; } }",function(e){return"absolute"==m(e,null,"position")})}return n}function h(e,t,r,l){function u(){f&&(delete L.style,delete L.modElem)}if(l=!o(l,"undefined")&&l,!o(r,"undefined")){var c=v(e,r);if(!o(c,"undefined"))return c}for(var f,d,p,m,h,g=["modernizr","tspan","samp"];!L.style&&g.length;)f=!0,L.modElem=s(g.shift()),L.style=L.modElem.style;for(p=e.length,d=0;d<p;d++)if(m=e[d],h=L.style[m],a(m,"-")&&(m=i(m)),L.style[m]!==n){if(l||o(r,"undefined"))return u(),"pfx"!=t||m;try{L.style[m]=r}catch(e){}if(L.style[m]!=h)return u(),"pfx"!=t||m}return u(),!1}function g(e,t,n,r,i){var a=e.charAt(0).toUpperCase()+e.slice(1),s=(e+" "+N.join(a+" ")+a).split(" ");return o(t,"string")||o(t,"undefined")?h(s,t,r,i):(s=(e+" "+w.join(a+" ")+a).split(" "),p(s,t,n))}function y(e,t,r){return g(e,n,n,t,r)}var S=[],C={_version:"3.5.0",_config:{classPrefix:"",enableClasses:!0,enableJSClass:!0,usePrefixes:!0},_q:[],on:function(e,t){var n=this;setTimeout(function(){t(n[e])},0)},addTest:function(e,t,n){S.push({name:e,fn:t,options:n})},addAsyncTest:function(e){S.push({name:null,fn:e})}},E=function(){};E.prototype=C,E=new E;var b=C._config.usePrefixes?" -webkit- -moz- -o- -ms- ".split(" "):["",""];C._prefixes=b;var _=[],x="Moz O ms Webkit",w=C._config.usePrefixes?x.toLowerCase().split(" "):[];C._domPrefixes=w;var T;!function(){var e={}.hasOwnProperty;T=o(e,"undefined")||o(e.call,"undefined")?function(e,t){return t in e&&o(e.constructor.prototype[t],"undefined")}:function(t,n){return e.call(t,n)}}();var N=C._config.usePrefixes?x.split(" "):[];C._cssomPrefixes=N;var j=function(t){var r,o=b.length,i=e.CSSRule;if(void 0===i)return n;if(!t)return!1;if(t=t.replace(/^@/,""),(r=t.replace(/-/g,"_").toUpperCase()+"_RULE")in i)return"@"+t;for(var a=0;a<o;a++){var s=b[a];if(s.toUpperCase()+"_"+r in i)return"@-"+s.toLowerCase()+"-"+t}return!1};C.atRule=j;var P=t.documentElement,z="svg"===P.nodeName.toLowerCase();z||function(e,t){function n(e,t){var n=e.createElement("p"),r=e.getElementsByTagName("head")[0]||e.documentElement;return n.innerHTML="x<style>"+t+"</style>",r.insertBefore(n.lastChild,r.firstChild)}function r(){var e=_.elements;return"string"==typeof e?e.split(" "):e}function o(e,t){var n=_.elements;"string"!=typeof n&&(n=n.join(" ")),"string"!=typeof e&&(e=e.join(" ")),_.elements=n+" "+e,u(t)}function i(e){var t=b[e[C]];return t||(t={},E++,e[C]=E,b[E]=t),t}function a(e,n,r){if(n||(n=t),h)return n.createElement(e);r||(r=i(n));var o;return o=r.cache[e]?r.cache[e].cloneNode():S.test(e)?(r.cache[e]=r.createElem(e)).cloneNode():r.createElem(e),!o.canHaveChildren||y.test(e)||o.tagUrn?o:r.frag.appendChild(o)}function s(e,n){if(e||(e=t),h)return e.createDocumentFragment();n=n||i(e);for(var o=n.frag.cloneNode(),a=0,s=r(),l=s.length;a<l;a++)o.createElement(s[a]);return o}function l(e,t){t.cache||(t.cache={},t.createElem=e.createElement,t.createFrag=e.createDocumentFragment,t.frag=t.createFrag()),e.createElement=function(n){return _.shivMethods?a(n,e,t):t.createElem(n)},e.createDocumentFragment=Function("h,f","return function(){var n=f.cloneNode(),c=n.createElement;h.shivMethods&&("+r().join().replace(/[\w\-:]+/g,function(e){return t.createElem(e),t.frag.createElement(e),'c("'+e+'")'})+");return n}")(_,t.frag)}function u(e){e||(e=t);var r=i(e);return!_.shivCSS||v||r.hasCSS||(r.hasCSS=!!n(e,"article,aside,dialog,figcaption,figure,footer,header,hgroup,main,nav,section{display:block}mark{background:#FF0;color:#000}template{display:none}")),h||l(e,r),e}function c(e){for(var t,n=e.getElementsByTagName("*"),o=n.length,i=RegExp("^(?:"+r().join("|")+")$","i"),a=[];o--;)t=n[o],i.test(t.nodeName)&&a.push(t.applyElement(f(t)));return a}function f(e){for(var t,n=e.attributes,r=n.length,o=e.ownerDocument.createElement(w+":"+e.nodeName);r--;)t=n[r],t.specified&&o.setAttribute(t.nodeName,t.nodeValue);return o.style.cssText=e.style.cssText,o}function d(e){for(var t,n=e.split("{"),o=n.length,i=RegExp("(^|[\\s,>+~])("+r().join("|")+")(?=[[\\s,>+~#.:]|$)","gi"),a="$1"+w+"\\:$2";o--;)t=n[o]=n[o].split("}"),t[t.length-1]=t[t.length-1].replace(i,a),n[o]=t.join("}");return n.join("{")}function p(e){for(var t=e.length;t--;)e[t].removeNode()}function m(e){function t(){clearTimeout(a._removeSheetTimer),r&&r.removeNode(!0),r=null}var r,o,a=i(e),s=e.namespaces,l=e.parentWindow;return!T||e.printShived?e:(void 0===s[w]&&s.add(w),l.attachEvent("onbeforeprint",function(){t();for(var i,a,s,l=e.styleSheets,u=[],f=l.length,p=Array(f);f--;)p[f]=l[f];for(;s=p.pop();)if(!s.disabled&&x.test(s.media)){try{i=s.imports,a=i.length}catch(e){a=0}for(f=0;f<a;f++)p.push(i[f]);try{u.push(s.cssText)}catch(e){}}u=d(u.reverse().join("")),o=c(e),r=n(e,u)}),l.attachEvent("onafterprint",function(){p(o),clearTimeout(a._removeSheetTimer),a._removeSheetTimer=setTimeout(t,500)}),e.printShived=!0,e)}var v,h,g=e.html5||{},y=/^<|^(?:button|map|select|textarea|object|iframe|option|optgroup)$/i,S=/^(?:a|b|code|div|fieldset|h1|h2|h3|h4|h5|h6|i|label|li|ol|p|q|span|strong|style|table|tbody|td|th|tr|ul)$/i,C="_html5shiv",E=0,b={};!function(){try{var e=t.createElement("a");e.innerHTML="<xyz></xyz>",v="hidden"in e,h=1==e.childNodes.length||function(){t.createElement("a");var e=t.createDocumentFragment();return void 0===e.cloneNode||void 0===e.createDocumentFragment||void 0===e.createElement}()}catch(e){v=!0,h=!0}}();var _={elements:g.elements||"abbr article aside audio bdi canvas data datalist details dialog figcaption figure footer header hgroup main mark meter nav output picture progress section summary template time video",version:"3.7.3",shivCSS:!1!==g.shivCSS,supportsUnknownElements:h,shivMethods:!1!==g.shivMethods,type:"default",shivDocument:u,createElement:a,createDocumentFragment:s,addElements:o};e.html5=_,u(t);var x=/^$|\b(?:all|print)\b/,w="html5shiv",T=!h&&function(){var n=t.documentElement;return!(void 0===t.namespaces||void 0===t.parentWindow||void 0===n.applyElement||void 0===n.removeNode||void 0===e.attachEvent)}();_.type+=" print",_.shivPrint=m,m(t),"object"==typeof module&&module.exports&&(module.exports=_)}(void 0!==e?e:this,t);var k=function(e,t){var n=!1,r=s("div"),o=r.style;if(e in o){var i=w.length;for(o[e]=t,n=o[e];i--&&!n;)o[e]="-"+w[i]+"-"+t,n=o[e]}return""===n&&(n=!1),n};C.prefixedCSSValue=k;var A=function(){function e(e,t){var o;return!!e&&(t&&"string"!=typeof t||(t=s(t||"div")),e="on"+e,o=e in t,!o&&r&&(t.setAttribute||(t=s("div")),t.setAttribute(e,""),o="function"==typeof t[e],t[e]!==n&&(t[e]=n),t.removeAttribute(e)),o)}var r=!("onblur"in t.documentElement);return e}();C.hasEvent=A,C._l={},C.on=function(e,t){this._l[e]||(this._l[e]=[]),this._l[e].push(t),E.hasOwnProperty(e)&&setTimeout(function(){E._trigger(e,E[e])},0)},C._trigger=function(e,t){if(this._l[e]){var n=this._l[e];setTimeout(function(){var e;for(e=0;e<n.length;e++)(0,n[e])(t)},0),delete this._l[e]}},E._q.push(function(){C.addTest=u});var F=(C.testStyles=f,function(){var t=e.matchMedia||e.msMatchMedia;return t?function(e){var n=t(e);return n&&n.matches||!1}:function(t){var n=!1;return f("@media "+t+" { #modernizr { position: absolute; } }",function(t){n="absolute"==(e.getComputedStyle?e.getComputedStyle(t,null):t.currentStyle).position}),n}}());C.mq=F;var M={elem:s("modernizr")};E._q.push(function(){delete M.elem});var L={style:M.elem.style};E._q.unshift(function(){delete L.style});C.testProp=function(e,t,r){return h([e],n,t,r)};C.testAllProps=g,C.testAllProps=y;var $=C.prefixed=function(e,t,n){return 0===e.indexOf("@")?j(e):(-1!=e.indexOf("-")&&(e=i(e)),t?g(e,t,n):g(e,"pfx"))};C.prefixedCSS=function(e){var t=$(e);return t&&r(t)};!function(){var e,t,n,r,i,a,s;for(var l in S)if(S.hasOwnProperty(l)){if(e=[],t=S[l],t.name&&(e.push(t.name.toLowerCase()),t.options&&t.options.aliases&&t.options.aliases.length))for(n=0;n<t.options.aliases.length;n++)e.push(t.options.aliases[n].toLowerCase());for(r=o(t.fn,"function")?t.fn():t.fn,i=0;i<e.length;i++)a=e[i],s=a.split("."),1===s.length?E[s[0]]=r:(!E[s[0]]||E[s[0]]instanceof Boolean||(E[s[0]]=new Boolean(E[s[0]])),E[s[0]][s[1]]=r),_.push((r?"":"no-")+s.join("-"))}}(),delete C.addTest,delete C.addAsyncTest;for(var q=0;q<E._q.length;q++)E._q[q]();e.Modernizr=E}(window,document);