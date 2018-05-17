var VectorCanvas=function(t,e,i){if(this.mode=window.SVGAngle?"svg":"vml",this.params=i,"svg"===this.mode)this.createSvgNode=function(t){return document.createElementNS(this.svgns,t)};else{try{document.namespaces.rvml||document.namespaces.add("rvml","urn:schemas-microsoft-com:vml"),this.createVmlNode=function(t){return document.createElement("<rvml:"+t+' class="rvml">')}}catch(t){this.createVmlNode=function(t){return document.createElement("<"+t+' xmlns="urn:schemas-microsoft.com:vml" class="rvml">')}}document.createStyleSheet().addRule(".rvml","behavior:url(#default#VML)")}"svg"===this.mode?this.canvas=this.createSvgNode("svg"):(this.canvas=this.createVmlNode("group"),this.canvas.style.position="absolute"),this.setSize(t,e)};VectorCanvas.prototype={svgns:"http://www.w3.org/2000/svg",mode:"svg",width:0,height:0,canvas:null};var ColorScale=function(t,e,i,o){t&&this.setColors(t),e&&this.setNormalizeFunction(e),i&&this.setMin(i),i&&this.setMax(o)};ColorScale.prototype={colors:[]};var JQVMap=function(t){t=t||{};var e,i=this,o=JQVMap.maps[t.map];if(!o)throw new Error('Invalid "'+t.map+'" map parameter. Please make sure you have loaded this map file in your HTML.');this.selectedRegions=[],this.multiSelectRegion=t.multiSelectRegion,this.container=t.container,this.defaultWidth=o.width,this.defaultHeight=o.height,this.color=t.color,this.selectedColor=t.selectedColor,this.hoverColor=t.hoverColor,this.hoverColors=t.hoverColors,this.hoverOpacity=t.hoverOpacity,this.setBackgroundColor(t.backgroundColor),this.width=t.container.width(),this.height=t.container.height(),this.resize(),jQuery(window).resize(function(){var o=t.container.width(),s=t.container.height();if(o&&s){i.width=o,i.height=s,i.resize(),i.canvas.setSize(i.width,i.height),i.applyTransform();var r=jQuery.Event("resize.jqvmap");jQuery(t.container).trigger(r,[o,s]),e&&(jQuery(".jqvmap-pin").remove(),i.pinHandlers=!1,i.placePins(e.pins,e.mode))}}),this.canvas=new VectorCanvas(this.width,this.height,t),t.container.append(this.canvas.canvas),this.makeDraggable(),this.rootGroup=this.canvas.createGroup(!0),this.index=JQVMap.mapIndex,this.label=jQuery("<div/>").addClass("jqvmap-label").appendTo(jQuery("body")).hide(),t.enableZoom&&(jQuery("<div/>").addClass("jqvmap-zoomin").text("+").appendTo(t.container),jQuery("<div/>").addClass("jqvmap-zoomout").html("&#x2212;").appendTo(t.container)),i.countries=[];for(var s in o.paths){var r=this.canvas.createPath({path:o.paths[s].path});r.setFill(this.color),r.id=i.getCountryId(s),i.countries[s]=r,"svg"===this.canvas.mode?r.setAttribute("class","jqvmap-region"):jQuery(r).addClass("jqvmap-region"),jQuery(this.rootGroup).append(r)}if(jQuery(t.container).delegate("svg"===this.canvas.mode?"path":"shape","mouseover mouseout",function(e){var s=e.target,r=e.target.id.split("_").pop(),a=jQuery.Event("labelShow.jqvmap"),n=jQuery.Event("regionMouseOver.jqvmap");r=r.toLowerCase(),"mouseover"===e.type?(jQuery(t.container).trigger(n,[r,o.paths[r].name]),n.isDefaultPrevented()||i.highlight(r,s),t.showTooltip&&(i.label.text(o.paths[r].name),jQuery(t.container).trigger(a,[i.label,r]),a.isDefaultPrevented()||(i.label.show(),i.labelWidth=i.label.width(),i.labelHeight=i.label.height()))):(i.unhighlight(r,s),i.label.hide(),jQuery(t.container).trigger("regionMouseOut.jqvmap",[r,o.paths[r].name]))}),jQuery(t.container).delegate("svg"===this.canvas.mode?"path":"shape","click",function(e){var s=e.target,r=e.target.id.split("_").pop(),a=jQuery.Event("regionClick.jqvmap");if(r=r.toLowerCase(),jQuery(t.container).trigger(a,[r,o.paths[r].name]),!t.multiSelectRegion&&!a.isDefaultPrevented())for(var n in o.paths)i.countries[n].currentFillColor=i.countries[n].getOriginalFill(),i.countries[n].setFill(i.countries[n].getOriginalFill());a.isDefaultPrevented()||(i.isSelected(r)?i.deselect(r,s):i.select(r,s))}),t.showTooltip&&t.container.mousemove(function(t){if(i.label.is(":visible")){var e=t.pageX-15-i.labelWidth,o=t.pageY-15-i.labelHeight;e<0&&(e=t.pageX+15),o<0&&(o=t.pageY+15),i.label.css({left:e,top:o})}}),this.setColors(t.colors),this.canvas.canvas.appendChild(this.rootGroup),this.applyTransform(),this.colorScale=new ColorScale(t.scaleColors,t.normalizeFunction,t.valueMin,t.valueMax),t.values&&(this.values=t.values,this.setValues(t.values)),t.selectedRegions)if(t.selectedRegions instanceof Array)for(var a in t.selectedRegions)this.select(t.selectedRegions[a].toLowerCase());else this.select(t.selectedRegions.toLowerCase());if(this.bindZoomButtons(),t.pins&&(e={pins:t.pins,mode:t.pinMode},this.pinHandlers=!1,this.placePins(t.pins,t.pinMode)),t.showLabels){this.pinHandlers=!1;var n={};for(s in i.countries)"function"!=typeof i.countries[s]&&(t.pins&&t.pins[s]||(n[s]=s.toUpperCase()));e={pins:n,mode:"content"},this.placePins(n,"content")}JQVMap.mapIndex++};JQVMap.prototype={transX:0,transY:0,scale:1,baseTransX:0,baseTransY:0,baseScale:1,width:0,height:0,countries:{},countriesColors:{},countriesData:{},zoomStep:1.4,zoomMaxStep:4,zoomCurStep:1},JQVMap.xlink="http://www.w3.org/1999/xlink",JQVMap.mapIndex=1,JQVMap.maps={},function(){var t={colors:1,values:1,backgroundColor:1,scaleColors:1,normalizeFunction:1,enableZoom:1,showTooltip:1,borderColor:1,borderWidth:1,borderOpacity:1,selectedRegions:1,multiSelectRegion:1},e={onLabelShow:"labelShow",onLoad:"load",onRegionOver:"regionMouseOver",onRegionOut:"regionMouseOut",onRegionClick:"regionClick",onRegionSelect:"regionSelect",onRegionDeselect:"regionDeselect",onResize:"resize"};jQuery.fn.vmap=function(i){var o={map:"world_en",backgroundColor:"#a5bfdd",color:"#f4f3f0",hoverColor:"#c9dfaf",hoverColors:{},selectedColor:"#c9dfaf",scaleColors:["#b6d6ff","#005ace"],normalizeFunction:"linear",enableZoom:!0,showTooltip:!0,borderColor:"#818181",borderWidth:1,borderOpacity:.25,selectedRegions:null,multiSelectRegion:!1},s=this.data("mapObject");if("addMap"===i)JQVMap.maps[arguments[1]]=arguments[2];else{if("set"!==i||!t[arguments[1]]){if("string"==typeof i&&"function"==typeof s[i])return s[i].apply(s,Array.prototype.slice.call(arguments,1));jQuery.extend(o,i),o.container=this,this.css({position:"relative",overflow:"hidden"}),s=new JQVMap(o),this.data("mapObject",s),this.unbind(".jqvmap");for(var r in e)o[r]&&this.bind(e[r]+".jqvmap",o[r]);var a=jQuery.Event("load.jqvmap");return jQuery(o.container).trigger(a,s),s}s["set"+arguments[1].charAt(0).toUpperCase()+arguments[1].substr(1)].apply(s,Array.prototype.slice.call(arguments,2))}}}(jQuery),ColorScale.arrayToRgb=function(t){for(var e,i="#",o=0;o<t.length;o++)e=t[o].toString(16),i+=1===e.length?"0"+e:e;return i},ColorScale.prototype.getColor=function(t){"function"==typeof this.normalize&&(t=this.normalize(t));for(var e,i=[],o=0,s=0;s<this.colors.length-1;s++)e=this.vectorLength(this.vectorSubtract(this.colors[s+1],this.colors[s])),i.push(e),o+=e;var r=(this.maxValue-this.minValue)/o;for(s=0;s<i.length;s++)i[s]*=r;for(s=0,t-=this.minValue;t-i[s]>=0;)t-=i[s],s++;var a;for(a=s===this.colors.length-1?this.vectorToNum(this.colors[s]).toString(16):this.vectorToNum(this.vectorAdd(this.colors[s],this.vectorMult(this.vectorSubtract(this.colors[s+1],this.colors[s]),t/i[s]))).toString(16);a.length<6;)a="0"+a;return"#"+a},ColorScale.rgbToArray=function(t){return t=t.substr(1),[parseInt(t.substr(0,2),16),parseInt(t.substr(2,2),16),parseInt(t.substr(4,2),16)]},ColorScale.prototype.setColors=function(t){for(var e=0;e<t.length;e++)t[e]=ColorScale.rgbToArray(t[e]);this.colors=t},ColorScale.prototype.setMax=function(t){this.clearMaxValue=t,"function"==typeof this.normalize?this.maxValue=this.normalize(t):this.maxValue=t},ColorScale.prototype.setMin=function(t){this.clearMinValue=t,"function"==typeof this.normalize?this.minValue=this.normalize(t):this.minValue=t},ColorScale.prototype.setNormalizeFunction=function(t){"polynomial"===t?this.normalize=function(t){return Math.pow(t,.2)}:"linear"===t?delete this.normalize:this.normalize=t,this.setMin(this.clearMinValue),this.setMax(this.clearMaxValue)},ColorScale.prototype.vectorAdd=function(t,e){for(var i=[],o=0;o<t.length;o++)i[o]=t[o]+e[o];return i},ColorScale.prototype.vectorLength=function(t){for(var e=0,i=0;i<t.length;i++)e+=t[i]*t[i];return Math.sqrt(e)},ColorScale.prototype.vectorMult=function(t,e){for(var i=[],o=0;o<t.length;o++)i[o]=t[o]*e;return i},ColorScale.prototype.vectorSubtract=function(t,e){for(var i=[],o=0;o<t.length;o++)i[o]=t[o]-e[o];return i},ColorScale.prototype.vectorToNum=function(t){for(var e=0,i=0;i<t.length;i++)e+=Math.round(t[i])*Math.pow(256,t.length-i-1);return e},JQVMap.prototype.applyTransform=function(){var t,e,i,o;this.defaultWidth*this.scale<=this.width?(t=(this.width-this.defaultWidth*this.scale)/(2*this.scale),i=(this.width-this.defaultWidth*this.scale)/(2*this.scale)):(t=0,i=(this.width-this.defaultWidth*this.scale)/this.scale),this.defaultHeight*this.scale<=this.height?(e=(this.height-this.defaultHeight*this.scale)/(2*this.scale),o=(this.height-this.defaultHeight*this.scale)/(2*this.scale)):(e=0,o=(this.height-this.defaultHeight*this.scale)/this.scale),this.transY>e?this.transY=e:this.transY<o&&(this.transY=o),this.transX>t?this.transX=t:this.transX<i&&(this.transX=i),this.canvas.applyTransformParams(this.scale,this.transX,this.transY)},JQVMap.prototype.bindZoomButtons=function(){var t=this;this.container.find(".jqvmap-zoomin").click(function(){t.zoomIn()}),this.container.find(".jqvmap-zoomout").click(function(){t.zoomOut()})},JQVMap.prototype.deselect=function(t,e){if(t=t.toLowerCase(),e=e||jQuery("#"+this.getCountryId(t))[0],this.isSelected(t))this.selectedRegions.splice(this.selectIndex(t),1),jQuery(this.container).trigger("regionDeselect.jqvmap",[t]),e.currentFillColor=e.getOriginalFill(),e.setFill(e.getOriginalFill());else for(var i in this.countries)this.selectedRegions.splice(this.selectedRegions.indexOf(i),1),this.countries[i].currentFillColor=this.color,this.countries[i].setFill(this.color)},JQVMap.prototype.getCountryId=function(t){return"jqvmap"+this.index+"_"+t},JQVMap.prototype.getPin=function(t){return jQuery("#"+this.getPinId(t)).html()},JQVMap.prototype.getPinId=function(t){return this.getCountryId(t)+"_pin"},JQVMap.prototype.getPins=function(){var t=this.container.find(".jqvmap-pin"),e={};return jQuery.each(t,function(t,i){i=jQuery(i);var o=i.attr("for").toLowerCase(),s=i.html();e[o]=s}),JSON.stringify(e)},JQVMap.prototype.highlight=function(t,e){e=e||jQuery("#"+this.getCountryId(t))[0],this.hoverOpacity?e.setOpacity(this.hoverOpacity):this.hoverColors&&t in this.hoverColors?(e.currentFillColor=e.getFill()+"",e.setFill(this.hoverColors[t])):this.hoverColor&&(e.currentFillColor=e.getFill()+"",e.setFill(this.hoverColor))},JQVMap.prototype.isSelected=function(t){return this.selectIndex(t)>=0},JQVMap.prototype.makeDraggable=function(){var t,e,i=!1,o=this;o.isMoving=!1,o.isMovingTimeout=!1;var s,r,a,n,l,h,c;this.container.mousemove(function(s){return i&&(o.transX-=(t-s.pageX)/o.scale,o.transY-=(e-s.pageY)/o.scale,o.applyTransform(),t=s.pageX,e=s.pageY,o.isMoving=!0,o.isMovingTimeout&&clearTimeout(o.isMovingTimeout),o.container.trigger("drag")),!1}).mousedown(function(o){return i=!0,t=o.pageX,e=o.pageY,!1}).mouseup(function(){return i=!1,clearTimeout(o.isMovingTimeout),o.isMovingTimeout=setTimeout(function(){o.isMoving=!1},100),!1}).mouseout(function(){if(i&&o.isMoving)return clearTimeout(o.isMovingTimeout),o.isMovingTimeout=setTimeout(function(){i=!1,o.isMoving=!1},100),!1}),jQuery(this.container).bind("touchmove",function(t){var e,i,p,u,g=t.originalEvent.touches;if(1===g.length){if(1===s){if(h===g[0].pageX&&c===g[0].pageY)return;p=o.transX,u=o.transY,o.transX-=(h-g[0].pageX)/o.scale,o.transY-=(c-g[0].pageY)/o.scale,o.applyTransform(),p===o.transX&&u===o.transY||t.preventDefault(),o.isMoving=!0,o.isMovingTimeout&&clearTimeout(o.isMovingTimeout)}h=g[0].pageX,c=g[0].pageY}else 2===g.length&&(2===s?(i=Math.sqrt(Math.pow(g[0].pageX-g[1].pageX,2)+Math.pow(g[0].pageY-g[1].pageY,2))/n,o.setScale(l*i,r,a),t.preventDefault()):(e=jQuery(o.container).offset(),r=g[0].pageX>g[1].pageX?g[1].pageX+(g[0].pageX-g[1].pageX)/2:g[0].pageX+(g[1].pageX-g[0].pageX)/2,a=g[0].pageY>g[1].pageY?g[1].pageY+(g[0].pageY-g[1].pageY)/2:g[0].pageY+(g[1].pageY-g[0].pageY)/2,r-=e.left,a-=e.top,l=o.scale,n=Math.sqrt(Math.pow(g[0].pageX-g[1].pageX,2)+Math.pow(g[0].pageY-g[1].pageY,2))));s=g.length}),jQuery(this.container).bind("touchstart",function(){s=0}),jQuery(this.container).bind("touchend",function(){s=0})},JQVMap.prototype.placePins=function(t,e){var i=this;if((!e||"content"!==e&&"id"!==e)&&(e="content"),"content"===e?jQuery.each(t,function(t,e){if(0!==jQuery("#"+i.getCountryId(t)).length){var o=i.getPinId(t),s=jQuery("#"+o);s.length>0&&s.remove(),i.container.append('<div id="'+o+'" for="'+t+'" class="jqvmap-pin" style="position:absolute">'+e+"</div>")}}):jQuery.each(t,function(t,e){if(0!==jQuery("#"+i.getCountryId(t)).length){var o=i.getPinId(t),s=jQuery("#"+o);s.length>0&&s.remove(),i.container.append('<div id="'+o+'" for="'+t+'" class="jqvmap-pin" style="position:absolute"></div>'),s.append(jQuery("#"+e))}}),this.positionPins(),!this.pinHandlers){this.pinHandlers=!0;var o=function(){i.positionPins()};this.container.bind("zoomIn",o).bind("zoomOut",o).bind("drag",o)}},JQVMap.prototype.positionPins=function(){var t=this,e=this.container.find(".jqvmap-pin");jQuery.each(e,function(e,i){i=jQuery(i);var o=t.getCountryId(i.attr("for").toLowerCase()),s=jQuery("#"+o),r=document.getElementById(o).getBBox(),a=s.position(),n=t.scale,l=a.left+r.width/2*n-i.width()/2,h=a.top+r.height/2*n-i.height()/2;i.css("left",l).css("top",h)})},JQVMap.prototype.removePin=function(t){t=t.toLowerCase(),jQuery("#"+this.getPinId(t)).remove()},JQVMap.prototype.removePins=function(){this.container.find(".jqvmap-pin").remove()},JQVMap.prototype.reset=function(){for(var t in this.countries)this.countries[t].setFill(this.color);this.scale=this.baseScale,this.transX=this.baseTransX,this.transY=this.baseTransY,this.applyTransform()},JQVMap.prototype.resize=function(){var t=this.baseScale;this.width/this.height>this.defaultWidth/this.defaultHeight?(this.baseScale=this.height/this.defaultHeight,this.baseTransX=Math.abs(this.width-this.defaultWidth*this.baseScale)/(2*this.baseScale)):(this.baseScale=this.width/this.defaultWidth,this.baseTransY=Math.abs(this.height-this.defaultHeight*this.baseScale)/(2*this.baseScale)),this.scale*=this.baseScale/t,this.transX*=this.baseScale/t,this.transY*=this.baseScale/t},JQVMap.prototype.select=function(t,e){t=t.toLowerCase(),e=e||jQuery("#"+this.getCountryId(t))[0],this.isSelected(t)||(this.multiSelectRegion?this.selectedRegions.push(t):this.selectedRegions=[t],jQuery(this.container).trigger("regionSelect.jqvmap",[t]),this.selectedColor&&e&&(e.currentFillColor=this.selectedColor,e.setFill(this.selectedColor)))},JQVMap.prototype.selectIndex=function(t){t=t.toLowerCase();for(var e=0;e<this.selectedRegions.length;e++)if(t===this.selectedRegions[e])return e;return-1},JQVMap.prototype.setBackgroundColor=function(t){this.container.css("background-color",t)},JQVMap.prototype.setColors=function(t,e){if("string"==typeof t)this.countries[t].setFill(e),this.countries[t].setAttribute("original",e);else{var i=t;for(var o in i)this.countries[o]&&(this.countries[o].setFill(i[o]),this.countries[o].setAttribute("original",i[o]))}},JQVMap.prototype.setNormalizeFunction=function(t){this.colorScale.setNormalizeFunction(t),this.values&&this.setValues(this.values)},JQVMap.prototype.setScale=function(t){this.scale=t,this.applyTransform()},JQVMap.prototype.setScaleColors=function(t){this.colorScale.setColors(t),this.values&&this.setValues(this.values)},JQVMap.prototype.setValues=function(t){var e,i=0,o=Number.MAX_VALUE;for(var s in t)s=s.toLowerCase(),e=parseFloat(t[s]),isNaN(e)||(e>i&&(i=t[s]),e<o&&(o=e));o===i&&i++,this.colorScale.setMin(o),this.colorScale.setMax(i);var r={};for(s in t)s=s.toLowerCase(),e=parseFloat(t[s]),r[s]=isNaN(e)?this.color:this.colorScale.getColor(e);this.setColors(r),this.values=t},JQVMap.prototype.unhighlight=function(t,e){t=t.toLowerCase(),e=e||jQuery("#"+this.getCountryId(t))[0],e.setOpacity(1),e.currentFillColor&&e.setFill(e.currentFillColor)},JQVMap.prototype.zoomIn=function(){var t=this,e=(jQuery("#zoom").innerHeight()-12-30-6-7-6)/(this.zoomMaxStep-this.zoomCurStep);if(t.zoomCurStep<t.zoomMaxStep){t.transX-=(t.width/t.scale-t.width/(t.scale*t.zoomStep))/2,t.transY-=(t.height/t.scale-t.height/(t.scale*t.zoomStep))/2,t.setScale(t.scale*t.zoomStep),t.zoomCurStep++;var i=jQuery("#zoomSlider");i.css("top",parseInt(i.css("top"),10)-e),t.container.trigger("zoomIn")}},JQVMap.prototype.zoomOut=function(){var t=this,e=(jQuery("#zoom").innerHeight()-12-30-6-7-6)/(this.zoomMaxStep-this.zoomCurStep);if(t.zoomCurStep>1){t.transX+=(t.width/(t.scale/t.zoomStep)-t.width/t.scale)/2,t.transY+=(t.height/(t.scale/t.zoomStep)-t.height/t.scale)/2,t.setScale(t.scale/t.zoomStep),t.zoomCurStep--;var i=jQuery("#zoomSlider");i.css("top",parseInt(i.css("top"),10)+e),t.container.trigger("zoomOut")}},VectorCanvas.prototype.applyTransformParams=function(t,e,i){"svg"===this.mode?this.rootGroup.setAttribute("transform","scale("+t+") translate("+e+", "+i+")"):(this.rootGroup.coordorigin=this.width-e+","+(this.height-i),this.rootGroup.coordsize=this.width/t+","+this.height/t)},VectorCanvas.prototype.createGroup=function(t){var e;return"svg"===this.mode?e=this.createSvgNode("g"):(e=this.createVmlNode("group"),e.style.width=this.width+"px",e.style.height=this.height+"px",e.style.left="0px",e.style.top="0px",e.coordorigin="0 0",e.coordsize=this.width+" "+this.height),t&&(this.rootGroup=e),e},VectorCanvas.prototype.createPath=function(t){var e;if("svg"===this.mode)e=this.createSvgNode("path"),e.setAttribute("d",t.path),null!==this.params.borderColor&&e.setAttribute("stroke",this.params.borderColor),this.params.borderWidth>0&&(e.setAttribute("stroke-width",this.params.borderWidth),e.setAttribute("stroke-linecap","round"),e.setAttribute("stroke-linejoin","round")),this.params.borderOpacity>0&&e.setAttribute("stroke-opacity",this.params.borderOpacity),e.setFill=function(t){this.setAttribute("fill",t),null===this.getAttribute("original")&&this.setAttribute("original",t)},e.getFill=function(){return this.getAttribute("fill")},e.getOriginalFill=function(){return this.getAttribute("original")},e.setOpacity=function(t){this.setAttribute("fill-opacity",t)};else{e=this.createVmlNode("shape"),e.coordorigin="0 0",e.coordsize=this.width+" "+this.height,e.style.width=this.width+"px",e.style.height=this.height+"px",e.fillcolor=JQVMap.defaultFillColor,e.stroked=!1,e.path=VectorCanvas.pathSvgToVml(t.path);var i=this.createVmlNode("skew");i.on=!0,i.matrix="0.01,0,0,0.01,0,0",i.offset="0,0",e.appendChild(i);var o=this.createVmlNode("fill");e.appendChild(o),e.setFill=function(t){this.getElementsByTagName("fill")[0].color=t,null===this.getAttribute("original")&&this.setAttribute("original",t)},e.getFill=function(){return this.getElementsByTagName("fill")[0].color},e.getOriginalFill=function(){return this.getAttribute("original")},e.setOpacity=function(t){this.getElementsByTagName("fill")[0].opacity=parseInt(100*t,10)+"%"}}return e},VectorCanvas.prototype.pathSvgToVml=function(t){var e,i,o="",s=0,r=0;return t.replace(/([MmLlHhVvCcSs])((?:-?(?:\d+)?(?:\.\d+)?,?\s?)+)/g,function(t,a,n){n=n.replace(/(\d)-/g,"$1,-").replace(/\s+/g,",").split(","),n[0]||n.shift();for(var l=0,h=n.length;l<h;l++)n[l]=Math.round(100*n[l]);switch(a){case"m":s+=n[0],r+=n[1],o="t"+n.join(",");break;case"M":s=n[0],r=n[1],o="m"+n.join(",");break;case"l":s+=n[0],r+=n[1],o="r"+n.join(",");break;case"L":s=n[0],r=n[1],o="l"+n.join(",");break;case"h":s+=n[0],o="r"+n[0]+",0";break;case"H":s=n[0],o="l"+s+","+r;break;case"v":r+=n[0],o="r0,"+n[0];break;case"V":r=n[0],o="l"+s+","+r;break;case"c":e=s+n[n.length-4],i=r+n[n.length-3],s+=n[n.length-2],r+=n[n.length-1],o="v"+n.join(",");break;case"C":e=n[n.length-4],i=n[n.length-3],s=n[n.length-2],r=n[n.length-1],o="c"+n.join(",");break;case"s":n.unshift(r-i),n.unshift(s-e),e=s+n[n.length-4],i=r+n[n.length-3],s+=n[n.length-2],r+=n[n.length-1],o="v"+n.join(",");break;case"S":n.unshift(r+r-i),n.unshift(s+s-e),e=n[n.length-4],i=n[n.length-3],s=n[n.length-2],r=n[n.length-1],o="c"+n.join(",")}return o}).replace(/z/g,"")},VectorCanvas.prototype.setSize=function(t,e){if("svg"===this.mode)this.canvas.setAttribute("width",t),this.canvas.setAttribute("height",e);else if(this.canvas.style.width=t+"px",this.canvas.style.height=e+"px",this.canvas.coordsize=t+" "+e,this.canvas.coordorigin="0 0",this.rootGroup){for(var i=this.rootGroup.getElementsByTagName("shape"),o=0,s=i.length;o<s;o++)i[o].coordsize=t+" "+e,i[o].style.width=t+"px",i[o].style.height=e+"px";this.rootGroup.coordsize=t+" "+e,this.rootGroup.style.width=t+"px",this.rootGroup.style.height=e+"px"}this.width=t,this.height=e};