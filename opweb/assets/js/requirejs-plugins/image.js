define(function(){function n(){}function r(n){return n=n.replace(o,""),(n+=n.indexOf("?")<0?"?":"&")+e+"="+Math.round(2147483647*Math.random())}var e="bust",o="!bust";return{load:function(r,e,o,t){var u;t.isBuild?o(null):(u=new Image,u.onerror=function(n){o.error(n)},u.onload=function(r){o(u);try{delete u.onload}catch(r){u.onload=n}},-1!==r.indexOf("!rel")?u.src=e.toUrl(r.replace("!rel","")):u.src=r)},normalize:function(n,e){return-1===n.indexOf(o)?n:r(n)}}});