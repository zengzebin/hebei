!function(){"use strict";var e,t,a,s,r="http://www.w3.org/",n=r+"1999/xhtml",c=window,l=document,i=c.SVGElement;!i||"classList"in l.createElementNS("http://www.w3.org/2000/svg","g")||(a=l.createElement("div"),null!==a.namespaceURI||a.classList||(s=l.createElement,l.createElement=function(e){return"div"===(e||"").toLowerCase()?l.createElementNS(n,e):s.apply(l,arguments)}),Object.defineProperty(i.prototype,"classList",{get:function(){var a=this;e||(e=l.createElementNS(n,"div"),t=e.children,e.addEventListener("DOMAttrModified",function(e){var t=e.target,a=t.__R;"class"===e.attrName&&a&&(a.__CORKED=!0,a.setAttribute("class",t.className),delete a.__CORKED)}));for(var s,r=0,c=t.length;r<c&&(s=t[r],s.__R!==a);++r)s=0;if(!s){s=l.createElementNS(n,"div"),s.className=a.getAttribute("class"),e.appendChild(s),s.__R=a;var i=a.setAttribute;a.setAttribute=function(e,t){"class"!==e||a.__CORKED||(s.className=t),i.call(a,e,t)}}return s.classList}}))}();