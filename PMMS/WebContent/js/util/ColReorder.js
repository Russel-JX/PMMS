/*
 * File:        ColReorder.min.js
 * Version:     1.0.8
 * Author:      Allan Jardine (www.sprymedia.co.uk)
 *
 * Copyright 2010-2012 Allan Jardine, all rights reserved.
 *
 * This source file is free software, under either the GPL v2 license or a
 * BSD (3 point) style license, as supplied with this software.
 *
 * This source file is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the license files for details.
 */
(function(f,o,g){function m(a){for(var b=[],d=0,c=a.length;d<c;d++)b[a[d]]=d;return b}function j(a,b,d){b=a.splice(b,1)[0];a.splice(d,0,b)}function n(a,b,d){for(var c=[],e=0,f=a.childNodes.length;e<f;e++)1==a.childNodes[e].nodeType&&c.push(a.childNodes[e]);b=c[b];null!==d?a.insertBefore(b,c[d]):a.appendChild(b)}f.fn.dataTableExt.oApi.fnColReorder=function(a,b,d){var c,e,h,l,k=a.aoColumns.length,i;if(b!=d)if(0>b||b>=k)this.oApi._fnLog(a,1,"ColReorder 'from' index is out of bounds: "+b);else if(0>d||
d>=k)this.oApi._fnLog(a,1,"ColReorder 'to' index is out of bounds: "+d);else{var g=[];c=0;for(e=k;c<e;c++)g[c]=c;j(g,b,d);g=m(g);c=0;for(e=a.aaSorting.length;c<e;c++)a.aaSorting[c][0]=g[a.aaSorting[c][0]];if(null!==a.aaSortingFixed){c=0;for(e=a.aaSortingFixed.length;c<e;c++)a.aaSortingFixed[c][0]=g[a.aaSortingFixed[c][0]]}c=0;for(e=k;c<e;c++){i=a.aoColumns[c];h=0;for(l=i.aDataSort.length;h<l;h++)i.aDataSort[h]=g[i.aDataSort[h]]}c=0;for(e=k;c<e;c++)i=a.aoColumns[c],"number"==typeof i.mData&&(i.mData=
g[i.mData],i.fnGetData=a.oApi._fnGetObjectDataFn(i.mData),i.fnSetData=a.oApi._fnSetObjectDataFn(i.mData));if(a.aoColumns[b].bVisible){l=this.oApi._fnColumnIndexToVisible(a,b);i=null;for(c=d<b?d:d+1;null===i&&c<k;)i=this.oApi._fnColumnIndexToVisible(a,c),c++;h=a.nTHead.getElementsByTagName("tr");c=0;for(e=h.length;c<e;c++)n(h[c],l,i);if(null!==a.nTFoot){h=a.nTFoot.getElementsByTagName("tr");c=0;for(e=h.length;c<e;c++)n(h[c],l,i)}c=0;for(e=a.aoData.length;c<e;c++)null!==a.aoData[c].nTr&&n(a.aoData[c].nTr,
l,i)}j(a.aoColumns,b,d);j(a.aoPreSearchCols,b,d);c=0;for(e=a.aoData.length;c<e;c++)f.isArray(a.aoData[c]._aData)&&j(a.aoData[c]._aData,b,d),j(a.aoData[c]._anHidden,b,d);c=0;for(e=a.aoHeader.length;c<e;c++)j(a.aoHeader[c],b,d);if(null!==a.aoFooter){c=0;for(e=a.aoFooter.length;c<e;c++)j(a.aoFooter[c],b,d)}c=0;for(e=k;c<e;c++)f(a.aoColumns[c].nTh).unbind("click"),this.oApi._fnSortAttachListener(a,a.aoColumns[c].nTh,c);f(a.oInstance).trigger("column-reorder",[a,{iFrom:b,iTo:d,aiInvertMapping:g}]);"undefined"!=
typeof a.oInstance._oPluginFixedHeader&&a.oInstance._oPluginFixedHeader.fnUpdate()}};ColReorder=function(a,b){(!this.CLASS||"ColReorder"!=this.CLASS)&&alert("Warning: ColReorder must be initialised with the keyword 'new'");"undefined"==typeof b&&(b={});this.s={dt:null,init:b,fixed:0,dropCallback:null,mouse:{startX:-1,startY:-1,offsetX:-1,offsetY:-1,target:-1,targetIndex:-1,fromIndex:-1},aoTargets:[]};this.dom={drag:null,pointer:null};this.s.dt=a.oInstance.fnSettings();this._fnConstruct();a.oApi._fnCallbackReg(a,
"aoDestroyCallback",jQuery.proxy(this._fnDestroy,this),"ColReorder");ColReorder.aoInstances.push(this);return this};ColReorder.prototype={fnReset:function(){for(var a=[],b=0,d=this.s.dt.aoColumns.length;b<d;b++)a.push(this.s.dt.aoColumns[b]._ColReorder_iOrigCol);this._fnOrderColumns(a)},_fnConstruct:function(){var a=this,b,d;"undefined"!=typeof this.s.init.iFixedColumns&&(this.s.fixed=this.s.init.iFixedColumns);"undefined"!=typeof this.s.init.fnReorderCallback&&(this.s.dropCallback=this.s.init.fnReorderCallback);
b=0;for(d=this.s.dt.aoColumns.length;b<d;b++)b>this.s.fixed-1&&this._fnMouseListener(b,this.s.dt.aoColumns[b].nTh),this.s.dt.aoColumns[b]._ColReorder_iOrigCol=b;this.s.dt.oApi._fnCallbackReg(this.s.dt,"aoStateSaveParams",function(c,b){a._fnStateSave.call(a,b)},"ColReorder_State");var c=null;"undefined"!=typeof this.s.init.aiOrder&&(c=this.s.init.aiOrder.slice());this.s.dt.oLoadedState&&("undefined"!=typeof this.s.dt.oLoadedState.ColReorder&&this.s.dt.oLoadedState.ColReorder.length==this.s.dt.aoColumns.length)&&
(c=this.s.dt.oLoadedState.ColReorder);if(c)if(a.s.dt._bInitComplete)b=m(c),a._fnOrderColumns.call(a,b);else{var e=!1;this.s.dt.aoDrawCallback.push({fn:function(){if(!a.s.dt._bInitComplete&&!e){e=true;var b=m(c);a._fnOrderColumns.call(a,b)}},sName:"ColReorder_Pre"})}},_fnOrderColumns:function(a){if(a.length!=this.s.dt.aoColumns.length)this.s.dt.oInstance.oApi._fnLog(this.s.dt,1,"ColReorder - array reorder does not match known number of columns. Skipping.");else{for(var b=0,d=a.length;b<d;b++){var c=
f.inArray(b,a);b!=c&&(j(a,c,b),this.s.dt.oInstance.fnColReorder(c,b))}(""!==this.s.dt.oScroll.sX||""!==this.s.dt.oScroll.sY)&&this.s.dt.oInstance.fnAdjustColumnSizing();this.s.dt.oInstance.oApi._fnSaveState(this.s.dt)}},_fnStateSave:function(a){var b,d,c,e=this.s.dt;for(b=0;b<a.aaSorting.length;b++)a.aaSorting[b][0]=e.aoColumns[a.aaSorting[b][0]]._ColReorder_iOrigCol;aSearchCopy=f.extend(!0,[],a.aoSearchCols);a.ColReorder=[];b=0;for(d=e.aoColumns.length;b<d;b++)c=e.aoColumns[b]._ColReorder_iOrigCol,
a.aoSearchCols[c]=aSearchCopy[b],a.abVisCols[c]=e.aoColumns[b].bVisible,a.ColReorder.push(c)},_fnMouseListener:function(a,b){var d=this;f(b).bind("mousedown.ColReorder",function(a){a.preventDefault();d._fnMouseDown.call(d,a,b)})},_fnMouseDown:function(a,b){var d=this,c=this.s.dt.aoColumns,e="TH"==a.target.nodeName?a.target:f(a.target).parents("TH")[0],e=f(e).offset();this.s.mouse.startX=a.pageX;this.s.mouse.startY=a.pageY;this.s.mouse.offsetX=a.pageX-e.left;this.s.mouse.offsetY=a.pageY-e.top;this.s.mouse.target=
b;this.s.mouse.targetIndex=f("th",b.parentNode).index(b);this.s.mouse.fromIndex=this.s.dt.oInstance.oApi._fnVisibleToColumnIndex(this.s.dt,this.s.mouse.targetIndex);this.s.aoTargets.splice(0,this.s.aoTargets.length);this.s.aoTargets.push({x:f(this.s.dt.nTable).offset().left,to:0});for(var h=e=0,j=c.length;h<j;h++)h!=this.s.mouse.fromIndex&&e++,c[h].bVisible&&this.s.aoTargets.push({x:f(c[h].nTh).offset().left+f(c[h].nTh).outerWidth(),to:e});0!==this.s.fixed&&this.s.aoTargets.splice(0,this.s.fixed);
f(g).bind("mousemove.ColReorder",function(a){d._fnMouseMove.call(d,a)});f(g).bind("mouseup.ColReorder",function(a){d._fnMouseUp.call(d,a)})},_fnMouseMove:function(a){if(null===this.dom.drag){if(5>Math.pow(Math.pow(a.pageX-this.s.mouse.startX,2)+Math.pow(a.pageY-this.s.mouse.startY,2),0.5))return;this._fnCreateDragNode()}this.dom.drag.style.left=a.pageX-this.s.mouse.offsetX+"px";this.dom.drag.style.top=a.pageY-this.s.mouse.offsetY+"px";for(var b=!1,d=1,c=this.s.aoTargets.length;d<c;d++)if(a.pageX<
this.s.aoTargets[d-1].x+(this.s.aoTargets[d].x-this.s.aoTargets[d-1].x)/2){this.dom.pointer.style.left=this.s.aoTargets[d-1].x+"px";this.s.mouse.toIndex=this.s.aoTargets[d-1].to;b=!0;break}b||(this.dom.pointer.style.left=this.s.aoTargets[this.s.aoTargets.length-1].x+"px",this.s.mouse.toIndex=this.s.aoTargets[this.s.aoTargets.length-1].to)},_fnMouseUp:function(){f(g).unbind("mousemove.ColReorder");f(g).unbind("mouseup.ColReorder");null!==this.dom.drag&&(g.body.removeChild(this.dom.drag),g.body.removeChild(this.dom.pointer),
this.dom.drag=null,this.dom.pointer=null,this.s.dt.oInstance.fnColReorder(this.s.mouse.fromIndex,this.s.mouse.toIndex),(""!==this.s.dt.oScroll.sX||""!==this.s.dt.oScroll.sY)&&this.s.dt.oInstance.fnAdjustColumnSizing(),null!==this.s.dropCallback&&this.s.dropCallback.call(this),this.s.dt.oInstance.oApi._fnSaveState(this.s.dt))},_fnCreateDragNode:function(){var a=this;this.dom.drag=f(this.s.dt.nTHead.parentNode).clone(!0)[0];for(this.dom.drag.className+=" DTCR_clonedTable";0<this.dom.drag.getElementsByTagName("caption").length;)this.dom.drag.removeChild(this.dom.drag.getElementsByTagName("caption")[0]);
for(;0<this.dom.drag.getElementsByTagName("tbody").length;)this.dom.drag.removeChild(this.dom.drag.getElementsByTagName("tbody")[0]);for(;0<this.dom.drag.getElementsByTagName("tfoot").length;)this.dom.drag.removeChild(this.dom.drag.getElementsByTagName("tfoot")[0]);f("thead tr:eq(0)",this.dom.drag).each(function(){f("th",this).eq(a.s.mouse.targetIndex).siblings().remove()});f("tr",this.dom.drag).height(f("tr:eq(0)",a.s.dt.nTHead).height());f("thead tr:gt(0)",this.dom.drag).remove();f("thead th:eq(0)",
this.dom.drag).each(function(){this.style.width=f("th:eq("+a.s.mouse.targetIndex+")",a.s.dt.nTHead).width()+"px"});this.dom.drag.style.position="absolute";this.dom.drag.style.top="0px";this.dom.drag.style.left="0px";this.dom.drag.style.width=f("th:eq("+a.s.mouse.targetIndex+")",a.s.dt.nTHead).outerWidth()+"px";this.dom.pointer=g.createElement("div");this.dom.pointer.className="DTCR_pointer";this.dom.pointer.style.position="absolute";""===this.s.dt.oScroll.sX&&""===this.s.dt.oScroll.sY?(this.dom.pointer.style.top=
f(this.s.dt.nTable).offset().top+"px",this.dom.pointer.style.height=f(this.s.dt.nTable).height()+"px"):(this.dom.pointer.style.top=f("div.dataTables_scroll",this.s.dt.nTableWrapper).offset().top+"px",this.dom.pointer.style.height=f("div.dataTables_scroll",this.s.dt.nTableWrapper).height()+"px");g.body.appendChild(this.dom.pointer);g.body.appendChild(this.dom.drag)},_fnDestroy:function(){for(var a=0,b=ColReorder.aoInstances.length;a<b;a++)if(ColReorder.aoInstances[a]===this){ColReorder.aoInstances.splice(a,
1);break}f(this.s.dt.nTHead).find("*").unbind(".ColReorder");this.s=this.s.dt.oInstance._oPluginColReorder=null}};ColReorder.aoInstances=[];ColReorder.fnReset=function(a){for(var b=0,d=ColReorder.aoInstances.length;b<d;b++)ColReorder.aoInstances[b].s.dt.oInstance==a&&ColReorder.aoInstances[b].fnReset()};ColReorder.prototype.CLASS="ColReorder";ColReorder.VERSION="1.0.8";ColReorder.prototype.VERSION=ColReorder.VERSION;"function"==typeof f.fn.dataTable&&"function"==typeof f.fn.dataTableExt.fnVersionCheck&&
f.fn.dataTableExt.fnVersionCheck("1.9.3")?f.fn.dataTableExt.aoFeatures.push({fnInit:function(a){var b=a.oInstance;"undefined"==typeof b._oPluginColReorder?b._oPluginColReorder=new ColReorder(a,"undefined"!=typeof a.oInit.oColReorder?a.oInit.oColReorder:{}):b.oApi._fnLog(a,1,"ColReorder attempted to initialise twice. Ignoring second");return null},cFeature:"R",sFeature:"ColReorder"}):alert("Warning: ColReorder requires DataTables 1.9.3 or greater - www.datatables.net/download")})(jQuery,window,document);
