!function(e,t){"use strict";"function"==typeof define&&define.amd?define(t):"object"==typeof exports?module.exports=t():e.returnExports=t()}(this,function(){var e,t,n,r,o=Function.prototype.call,c=Object.prototype,i=o.bind(c.hasOwnProperty),f=o.bind(c.propertyIsEnumerable),u=o.bind(c.toString),l=i(c,"__defineGetter__");l&&(e=o.bind(c.__defineGetter__),t=o.bind(c.__defineSetter__),n=o.bind(c.__lookupGetter__),r=o.bind(c.__lookupSetter__)),Object.getPrototypeOf||(Object.getPrototypeOf=function(e){var t=e.__proto__;return t||null===t?t:"[object Function]"===u(e.constructor)?e.constructor.prototype:e instanceof Object?c:null});var b=function(e){try{return e.sentinel=0,0===Object.getOwnPropertyDescriptor(e,"sentinel").value}catch(e){return!1}};if(Object.defineProperty){var p=b({});if(!("undefined"==typeof document||b(document.createElement("div")))||!p)var a=Object.getOwnPropertyDescriptor}if(!Object.getOwnPropertyDescriptor||a){Object.getOwnPropertyDescriptor=function(e,t){if("object"!=typeof e&&"function"!=typeof e||null===e)throw new TypeError("Object.getOwnPropertyDescriptor called on a non-object: "+e);if(a)try{return a.call(Object,e,t)}catch(e){}var o;if(!i(e,t))return o;if(o={enumerable:f(e,t),configurable:!0},l){var u=e.__proto__,b=e!==c;b&&(e.__proto__=c);var p=n(e,t),O=r(e,t);if(b&&(e.__proto__=u),p||O)return p&&(o.get=p),O&&(o.set=O),o}return o.value=e[t],o.writable=!0,o}}if(Object.getOwnPropertyNames||(Object.getOwnPropertyNames=function(e){return Object.keys(e)}),!Object.create){var O,j=!({__proto__:null}instanceof Object),s=function(){if(!document.domain)return!1;try{return!!new ActiveXObject("htmlfile")}catch(e){return!1}},d=function(){var e,t;return t=new ActiveXObject("htmlfile"),t.write("<script><\/script>"),t.close(),e=t.parentWindow.Object.prototype,t=null,e},y=function(){var e,t=document.createElement("iframe"),n=document.body||document.documentElement;return t.style.display="none",n.appendChild(t),t.src="javascript:",e=t.contentWindow.Object.prototype,n.removeChild(t),t=null,e};O=j||"undefined"==typeof document?function(){return{__proto__:null}}:function(){var e=s()?d():y();delete e.constructor,delete e.hasOwnProperty,delete e.propertyIsEnumerable,delete e.isPrototypeOf,delete e.toLocaleString,delete e.toString,delete e.valueOf;var t=function(){};return t.prototype=e,O=function(){return new t},new t},Object.create=function(e,t){var n,r=function(){};if(null===e)n=O();else{if("object"!=typeof e&&"function"!=typeof e)throw new TypeError("Object prototype may only be an Object or null");r.prototype=e,n=new r,n.__proto__=e}return void 0!==t&&Object.defineProperties(n,t),n}}var _=function(e){try{return Object.defineProperty(e,"sentinel",{}),"sentinel"in e}catch(e){return!1}};if(Object.defineProperty){var w=_({}),v="undefined"==typeof document||_(document.createElement("div"));if(!w||!v)var m=Object.defineProperty,h=Object.defineProperties}if(!Object.defineProperty||m){Object.defineProperty=function(o,i,f){if("object"!=typeof o&&"function"!=typeof o||null===o)throw new TypeError("Object.defineProperty called on non-object: "+o);if("object"!=typeof f&&"function"!=typeof f||null===f)throw new TypeError("Property description must be an object: "+f);if(m)try{return m.call(Object,o,i,f)}catch(e){}if("value"in f)if(l&&(n(o,i)||r(o,i))){var u=o.__proto__;o.__proto__=c,delete o[i],o[i]=f.value,o.__proto__=u}else o[i]=f.value;else{if(!l&&("get"in f||"set"in f))throw new TypeError("getters & setters can not be defined on this javascript engine");"get"in f&&e(o,i,f.get),"set"in f&&t(o,i,f.set)}return o}}Object.defineProperties&&!h||(Object.defineProperties=function(e,t){if(h)try{return h.call(Object,e,t)}catch(e){}return Object.keys(t).forEach(function(n){"__proto__"!==n&&Object.defineProperty(e,n,t[n])}),e}),Object.seal||(Object.seal=function(e){if(Object(e)!==e)throw new TypeError("Object.seal can only be called on Objects.");return e}),Object.freeze||(Object.freeze=function(e){if(Object(e)!==e)throw new TypeError("Object.freeze can only be called on Objects.");return e});try{Object.freeze(function(){})}catch(e){Object.freeze=function(e){return function(t){return"function"==typeof t?t:e(t)}}(Object.freeze)}Object.preventExtensions||(Object.preventExtensions=function(e){if(Object(e)!==e)throw new TypeError("Object.preventExtensions can only be called on Objects.");return e}),Object.isSealed||(Object.isSealed=function(e){if(Object(e)!==e)throw new TypeError("Object.isSealed can only be called on Objects.");return!1}),Object.isFrozen||(Object.isFrozen=function(e){if(Object(e)!==e)throw new TypeError("Object.isFrozen can only be called on Objects.");return!1}),Object.isExtensible||(Object.isExtensible=function(e){if(Object(e)!==e)throw new TypeError("Object.isExtensible can only be called on Objects.");for(var t="";i(e,t);)t+="?";e[t]=!0;var n=i(e,t);return delete e[t],n})});