define(["propertyParser"],function(t){function o(o){for(var n,i={},r=o.split("|"),a=r.length;a--;)n=e.exec(r[a]),i[n[1]]=t.parseProperties(n[2]);return i}var e=/^([^,]+),([^\|]+)\|?/;return{load:function(t,e,n,i){if(i.isBuild)n(null);else{var r=o(t);r.active=n,r.inactive=function(){n(!1)},e([("https:"===document.location.protocol?"https":"http")+"://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js"],function(){WebFont.load(r)})}}}});