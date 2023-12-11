package com.example.remoteService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomChartController {

    private static final CustomService customService = CustomService.getInstance();

    private final String BEGIN_PAGE = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <style>\n" +
            "      #line_graph {\n" +
            "          height: 300px;\n" +
            "          width: 600px;\n" +
            "      }\n" +
            "\n" +
            "      #tower_graph {\n" +
            "          height: 300px;\n" +
            "          width: 600px;\n" +
            "      }\n" +
            "\n" +
            "      #circle_graph {\n" +
            "          height: 300px;\n" +
            "          width: 600px;\n" +
            "      }\n" +
            "\n" +
            "      #double_line_graph {\n" +
            "          height: 300px;\n" +
            "          width: 600px;\n" +
            "      }\n" +
            "    </style>\n" +
            "    <title>GRAPHS</title>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "  <div style=\"display: flex; flex-wrap: wrap; width: 100%; justify-content: center;\">\n" +
            "    <div id=\"line_graph\">\n";

    private final String MIDDLE_PAGE = "</div>\n" +
            "\n" +
            "  </div>\n" +
            "\n" +
            "</body>\n" +
            "\n" +
            "<script>\n" +
            "let ns=\"http://www.w3.org/2000/svg\";function setAttributeNSmulti(e,n){for(let t=0;t<Object.keys(n).length;t++)e.setAttributeNS(null,Object.keys(n)[t],n[Object.keys(n)[t]])}function horGrid(t,n,l,r,i,s,o,a){for(let e=0;e<=t;e++){var u=document.createElementNS(ns,\"line\");setAttributeNSmulti(u,{x1:n,y1:r-e*s/o+l,x2:i+n,y2:r-e*s/o+l,style:\"stroke: rgb(145, 145, 145);\"}),a.append(u);let t=document.createElementNS(ns,\"text\");t.textContent=e*s,setAttributeNSmulti(t,{x:0,y:r-e*s/o+l+4}),a.append(t)}}function vertGrid(n,l,r,i,s,o){for(let e=0;e<n.length;e++){var a=document.createElementNS(ns,\"line\");setAttributeNSmulti(a,{x1:e*l/(n.length-1)+i,y1:r+s,x2:e*l/(n.length-1)+i,y2:s,style:\"stroke: rgb(145, 145, 145);\"});let t=document.createElementNS(ns,\"text\");t.textContent=e+1,setAttributeNSmulti(t,{x:e*l/(n.length-1)+i-4,y:r+2*s}),o.append(t),o.append(a)}}function linesGen(n,l,r,i,s,o,a,u){for(let e=1;e<n.length;e++){let t=document.createElementNS(ns,\"line\");null!=n[e-1]&&setAttributeNSmulti(t,{x1:e*l/(n.length-1)+i,y1:r-n[e]/o+s,x2:(e-1)*l/(n.length-1)+i,y2:r-n[e-1]/o+s}),\"single\"==u?n[e]>n[e-1]?t.setAttributeNS(null,\"style\",\"stroke: #6CABB7; stroke-width: 2px;\"):n[e]<n[e-1]?t.setAttributeNS(null,\"style\",\"stroke: #1F4C57; stroke-width: 2px;\"):t.setAttributeNS(null,\"style\",\"stroke: #306E7B; stroke-width: 2px;\"):\"double2\"==u?t.setAttributeNS(null,\"style\",\"stroke: #323A3A; stroke-width: 2px;\"):t.setAttributeNS(null,\"style\",\"stroke: #6CABB7; stroke-width: 2px;\"),a.append(t)}}function dotsNwindows(l,r,i,s,o,a,u,h){for(let e=0;e<l.length;e++){let n=document.createElementNS(ns,\"circle\");var d=\"single\"==h||\"double1\"==h?\"white\":\"rgb(190, 190, 190)\";setAttributeNSmulti(n,{cx:null!=l[e-1]?e*i/(l.length-1)+o:o,cy:r-l[e]/s+a,r:4,style:\"cursor: pointer; stroke: black; fill:\"+d+\";\"}),n.addEventListener(\"mouseenter\",function(t){let e=Array.prototype.slice.call(t.target.parentNode.childNodes);e[e.indexOf(n)+1].setAttributeNS(null,\"style\",\"stroke: black; fill: white; display: flex; stroke-width: 2px;\"),e[e.indexOf(n)+2].setAttributeNS(null,\"style\",\"display: flex;\")}),n.addEventListener(\"mouseout\",function(t){let e=Array.prototype.slice.call(t.target.parentNode.childNodes);e[e.indexOf(n)+1].setAttributeNS(null,\"style\",\"display: none;\"),e[e.indexOf(n)+2].setAttributeNS(null,\"style\",\"display: none;\")});d=document.createElementNS(ns,\"rect\");setAttributeNSmulti(d,{x:e*i/(l.length-1)+o-(8*String(l[e]).length+10)/2,y:r-l[e]/s+a-28,width:8*String(l[e]).length+10,height:20,style:\"display: none;\"});let t=document.createElementNS(ns,\"text\");setAttributeNSmulti(t,{x:e*i/(l.length-1)+o-8*String(l[e]).length/2,y:r-l[e]/s+a-12,style:\"display: none;\"}),t.textContent=l[e],u.append(n),u.append(d),u.append(t)}}function SGJ_line_graph(t,e,n){let l=document.querySelector(\"#\"+t);var r=l.offsetWidth,i=l.offsetHeight,s=document.createElementNS(ns,\"svg\");setAttributeNSmulti(s,{width:r,height:i});var o=Math.max(...e),a=String(o).length<String(n).length?10*String(n).length+5:10*String(o).length+5;i-=60;t=o%n==0?o/i:(o+(o-o%n+n)/n*n-o)/i;horGrid(o%n==0?o/n:o/n+1,a,30,i,r-=2*a,n,t,s),vertGrid(e,r,i,a,30,s),linesGen(e,r,i,a,30,t,s,\"single\"),dotsNwindows(e,i,r,t,a,30,s,\"single\"),l.appendChild(s)}function SGJ_tower_graph(t,l,n,r=1){let e=document.querySelector(\"#\"+t);e.style.transition=\"all ease 0.5s\";var i=e.offsetWidth,s=e.offsetHeight;let o=document.createElementNS(ns,\"svg\");setAttributeNSmulti(o,{width:i,height:s});var a=Math.max(...l),u=String(a).length<String(n).length?10*String(n).length+5:10*String(a).length+5;i-=2*u,s-=60;var h=a%n!=0?(a+(a-a%n+n)/n*n-a)/s:a/s,d=1==r?a%n==0?a/n:a/n+1:l.length;for(let e=0;e<=d;e++){var g=document.createElementNS(ns,\"line\");setAttributeNSmulti(g,{x1:u,y1:1==r?s-e*n/h+30:e==l.length?30+s:30+s-l[e]/h,x2:i+u,y2:1==r?s-e*n/h+30:e==l.length?30+s:30+s-l[e]/h,style:\"stroke: rgb(145, 145, 145);\"}),o.append(g);let t=document.createElementNS(ns,\"text\");t.textContent=1==r?e*n:e==l.length?0:l[e],setAttributeNSmulti(t,{x:0,y:1==r?s-e*n/h+30+4:e==l.length?30+s+4:30+s-l[e]/h+4}),o.append(t)}for(let n=0;n<l.length;n++){let t=document.createElementNS(ns,\"rect\");setAttributeNSmulti(t,{x:n*(i/(l.length+l.length-1))+u+n*(i/(l.length+l.length-1)),y:s-l[n]/h+30,width:i/(l.length+l.length-1),height:l[n]/h,style:\"transition: all ease 0.5s; fill: #6CABB7\"}),t.addEventListener(\"mouseenter\",function(t){t.target.setAttributeNS(null,\"style\",\"transition: all ease 0.5s; fill: #306E7B;\")}),t.addEventListener(\"mouseout\",function(t){t.target.setAttributeNS(null,\"style\",\"transition: all ease 0.5s; fill: #6CABB7;\")}),o.append(t);let e=document.createElementNS(ns,\"text\");e.textContent=l[n],setAttributeNSmulti(e,{x:n*(i/(l.length+l.length-1))*2+i/(l.length+l.length-1)/2-4*String(a).length+u,y:60+s}),o.append(e)}e.appendChild(o)}function SGJ_donut_graph(t,e){var r=Object.keys(e);let i=[];for(let t=0;t<r.length;t++)i.push(e[r[t]]);let n=document.querySelector(\"#\"+t);var s=n.offsetWidth,o=n.offsetHeight;let a=document.createElementNS(ns,\"svg\");setAttributeNSmulti(a,{width:s,height:o});var u=(u=t=>t.reduce((t,e)=>t+e,0))(i);let h=[\"#1F4C57\",\"#306E7B\",\"#6CABB7\",\"#AAB7B8\",\"#323A3A\"];for(;h.length<i.length;)h=h.concat(h);var d=o<s?o/4:s/4;let g=0,c=0;for(let l=0;l<i.length;l++){let t=document.createElementNS(ns,\"circle\");setAttributeNSmulti(t,{style:\"transition: all ease 0.5s; stroke: \"+h[l]+\"; stroke-dasharray: \"+2*Math.PI*d*i[l]/u+\" \"+2*Math.PI*d+\"; stroke-dashoffset: -\"+g+\"; fill: none; stroke-width:\"+d+\";\",r:d,cx:\"50%\",cy:\"50%\"});var S=g;t.addEventListener(\"mouseenter\",function(t){t.target.setAttributeNS(null,\"style\",\"transition: all ease 0.5s; stroke: \"+h[l]+\"; stroke-dasharray: \"+2*Math.PI*d*i[l]/u+\" \"+2*Math.PI*d+\"; stroke-dashoffset: -\"+S+\"; fill: none; stroke-width:\"+1.1*d+\";\"),setAttributeNSmulti(document.querySelector(\"#legendColor-\"+l),{width:15,height:15,y:37+20*l})}),t.addEventListener(\"mouseout\",function(t){t.target.setAttributeNS(null,\"style\",\"transition: all ease 0.5s; stroke: \"+h[l]+\"; stroke-dasharray: \"+2*Math.PI*d*i[l]/u+\" \"+2*Math.PI*d+\"; stroke-dashoffset: -\"+S+\"; fill: none; stroke-width:\"+d+\";\"),setAttributeNSmulti(document.querySelector(\"#legendColor-\"+l),{width:10,height:10,y:40+20*l})}),a.appendChild(t);var m=360/(u/i[l])/2,m=c+m;let e=document.createElementNS(ns,\"text\");e.textContent=Math.round(i[l]/u*100)+\"%\",setAttributeNSmulti(e,{x:s/2-String(i[l]).length-12+(d+.8*d)*Math.cos(m*Math.PI/180),y:o/2+8+(d+.8*d)*Math.sin(m*Math.PI/180)}),a.append(e),c+=360/(u/i[l]),g+=2*Math.PI*d*i[l]/u;m=document.createElementNS(ns,\"rect\");setAttributeNSmulti(m,{id:\"legendColor-\"+l,x:10,y:40+20*l,width:10,height:10,style:\"transition: all ease 0.5s; fill: \"+h[l]}),a.append(m);let n=document.createElementNS(ns,\"text\");n.textContent=r[l],setAttributeNSmulti(n,{x:30,y:50+20*l}),a.append(n)}n.appendChild(a)}function SGJ_double_line_graph(t,e,n,l){let r=document.querySelector(\"#\"+t);var i=r.offsetWidth,s=r.offsetHeight,o=document.createElementNS(ns,\"svg\");setAttributeNSmulti(o,{width:i,height:s});var a=Math.max(...e)>Math.max(...n)?Math.max(...e):Math.max(...n),u=String(a).length<String(l).length?10*String(l).length+5:10*String(a).length+5;s-=32;t=a%l==0?a/s:(a+(a-a%l+l)/l*l-a)/s;horGrid(a%l==0?a/l:a/l+1,u,16,s,i-=2*u,l,t,o),linesGen(e,i,s,u,16,t,o,\"double1\"),linesGen(n,i,s,u,16,t,o,\"double2\"),dotsNwindows(e,s,i,t,u,16,o,\"double1\"),dotsNwindows(n,s,i,t,u,16,o,\"double2\"),r.appendChild(o)}\n";
    private final String END_PAGE = "SGJ_line_graph(\"line_graph\", testData, 50)\n" +
            "    </script>\n" +
            "\n" +
            "</html>";
    private final String EMPTY_PAGE = "<html lang=\"en\">\n" +
            "            <span>\"Please, add version and id as query param. Example \"ver=v1&id=1\"\"</span>\n" +
            "\t\t\t</html>";
    @GetMapping("/chart")
    public String test(@RequestParam(required = false) String ver,
                       @RequestParam(required = false) Integer id){
        if(ver == null || id == null){
            return EMPTY_PAGE;
        }

        List<Double> values = customService.getValues3(ver, id);
        StringBuilder sb = new StringBuilder();
        sb.append(BEGIN_PAGE)
                .append("<div>").append(customService.getNameByKey(id)).append("</div>")
                .append(MIDDLE_PAGE)
                .append("let testData = [");
        values.stream().limit(values.size()-1)
                .forEach(e->sb.append(e).append(", "));
        sb.append(values.get(values.size()-1)).append("]\n").append(END_PAGE);
        return sb.toString();
    }
}
