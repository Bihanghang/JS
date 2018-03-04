<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Calendar</title>
<script src="http://ajax.microsoft.com/ajax/jquery/jquery-1.4.min.js"></script>
<style type="text/css">
body{background:#f2f2f2; margin:40px; }
li{cursor: pointer; }
*{margin:0; padding:0; }
.BBox{width: 550px; height: 351px; margin-left:360px; margin-top:50px; }
.Select{width: 98px; height: 351px; float: right; }
.box1{width:98px; height: 117px; }
#box1-box{width: 60px; height: 36px; background: red; position: relative; top: 36px; left: 18px }
.box2{width:98px; height: 117px; }
#box2-box{width: 60px; height: 36px; background: blue; position: relative; top: 36px; left: 18px }
.box3{width:98px; height: 117px; }
#box3-box{width: 60px; height: 36px; background: yellow; position: relative; top: 36px; left: 18px }
.calendar{float: left; width:450px; height:350px; background:#fff; box-shadow:0px 1px 1px rgba(0,0,0,0.1); }
.title{height:70px; border-bottom:1px solid rgba(0,0,0,0.1); text-align:center; position:relative; }
#calendar-title{font-size:25px; font-family:arial; font-weight:bold; text-transform:uppercase; padding:14px 0 0 0; }
#calendar-year{font-size:15px; font-family:arial; font-weight:normal; }
#prev{text-indent:-9999px; position:absolute; left:0; top:0; width:60px; height:70px; background:url(prev.png) no-repeat 50% 50%; }
#next{text-indent:-9999px; position:absolute; right:0; top:0; width:60px; height:70px; background:url(next.png) no-repeat 50% 50%; }
.body{padding:auto;}
.body-list ul{width:450px; font-family:arial; font-weight:bold; font-size:14px;}
.body-list ul li{width:64.26px; height:36px; line-height:36px; list-style-type:none; display:block; box-sizing:border-box; float:left; text-align:center; }
.lightgrey{color:#a8a8a8; } .darkgrey{color:#565656; } .green{color:#6ac13c; } .background{background: '#FFFFFF' }
.greenbox{border:1px solid black; background:white; }
.blue{background: blue} .red{background: red} .yellow{background: yellow }
.top{width:100%;}
.one{text-indent: 560px; color: blue; font-size: 25px;}
.two{text-indent: 600px; color: black; font-size: 10px;}
</style>
</head>

<body>
	<div class="top">
	<p class="one">撸管日历</p>
	<p class="two" id="num"></p>
	</div>
<div class="BBox">
<div class="calendar">
  <div class="title">
    <h1 class="green" id="calendar-title">Month</h1>
    <h2 class="green small" id="calendar-year">Year</h2>
    <a href="" id="prev">Prev Month</a>
    <a href="" id="next">Next Month</a>
  </div>
  <div class="body">
    <div class="lightgrey body-list" onselectstart="return false;">
      <ul>
        <li>MON</li>
        <li>TUE</li>
        <li>WED</li>
        <li>THU</li>
        <li>FRI</li>
        <li>SAT</li>
        <li>SUN</li>
      </ul>
    </div>
    <div class="darkgrey body-list">
      <ul id="days" onselectstart="return false;">
      </ul>
    </div>
  </div>
</div>
<div class="Select">
	<div class="box1">
		<div id="box1-box">
			
		</div>
	</div>
	<div class="box2">
		<div id="box2-box">
			
		</div>
	</div>
	<div class="box3">
		<div id="box3-box">
			
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
var log = console.log.bind(console)

var month_olympic = [31,29,31,30,31,30,31,31,30,31,30,31];
var month_normal = [31,28,31,30,31,30,31,31,30,31,30,31];
var month_name = ["January","Febrary","March","April","May","June","July","Auguest","September","October","November","December"];
var holder = document.getElementById("days");
var prev = document.getElementById("prev");
var next = document.getElementById("next");
var ctitle = document.getElementById("calendar-title");
var cyear = document.getElementById("calendar-year");
var my_date = new Date();
var my_year = my_date.getFullYear();
var my_month = my_date.getMonth();
var my_day = my_date.getDate();
var box1_box = document.getElementById("box1-box")
var box2_box = document.getElementById("box2-box")
var box3_box = document.getElementById("box3-box")
var FirstBox = false, SecondBox = false, ThirdBox = false;
var num = document.getElementById("num");
var signMonth = new Array();
box1_box.onclick = function() {
	FirstBox = true
	SecondBox = false
	ThirdBox = false
}

box2_box.onclick = function() {
	FirstBox = false
	SecondBox = true
	ThirdBox = false
}

box3_box.onclick = function() {
	FirstBox = false
	SecondBox = false
	ThirdBox = true
}

prev.onclick = function(e){
	e.preventDefault();
	my_month--;
	if(my_month<0){
		my_year--;
		my_month = 11;
	}
	qiandao()
}
next.onclick = function(e){
	e.preventDefault();
	my_month++;
	if(my_month>11){
		my_year++;
		my_month = 0;
	}
	qiandao()
}

var qiandao = function(){  
	  $.getJSON("getAttend.do",
			  {
		  		month:my_month+1,
		  		year: my_year,
		  		day:my_day,
			  },
			  function(data){
				  signMonth = data;
				  refreshDate()
	     }); 
} 
var ifHasSigned = function(signList,day){  
    var signed = 0;  
    $.each(signList,function(index,item){  
     if(item.signDay == day) {  
      signed = item.status;
      return signed;  
     }  
    });  
    return signed ;  
   }
   
function refreshDate(){
	var str = "";
	var totalDay = daysMonth(my_month, my_year); //获取该月总天数
	var firstDay = dayStart(my_month, my_year); //获取该月第一天是星期几
	var myclass;
	var qianbg;
	var totalnum = 0;
	for (var i = 0; i < signMonth.length; i++) {
		totalnum+=parseInt(signMonth[i].status)
	}
	num.innerHTML=totalnum;
	for(var i=1; i<firstDay; i++){ 
		str += "<li></li>"; //为起始日之前的日期创建空白节点
	}
	if(firstDay == 0){
		for(var i=1;i<7; i++)
		str += "<li></li>";
	}
	for(var i=1; i<=totalDay; i++){
		if((i<my_day && my_year==my_date.getFullYear() && my_month==my_date.getMonth()) || my_year<my_date.getFullYear() || ( my_year==my_date.getFullYear() && my_month<my_date.getMonth())){ 
			myclass = "lightgrey background"; //当该日期在今天之前时，以浅灰色字体显示
		}else if (i==my_day && my_year==my_date.getFullYear() && my_month==my_date.getMonth()){
			myclass = "green greenbox"; //当该日期是当天时，以绿色背景突出显示
		}else{
			myclass = "darkgrey"; //当该日期在今后之后时，以深灰字体显示
		}
		if(ifHasSigned(signMonth,i) == 1){
			qianbg = "red";
		}else if(ifHasSigned(signMonth,i) == 2){
			qianbg = "blue"
		}else if(ifHasSigned(signMonth,i) == 3) {
			qianbg = "yellow"
		}else{
			qianbg = ""
		}
		str += "<li class='"+myclass+" "+qianbg+"' onclick='g(this.id)' ondblclick='db(this.id)' id="+i+">"+i+"</li>"; //创建日期节点
	}
	holder.innerHTML = str; //设置日期显示
	ctitle.innerHTML = month_name[my_month]; //设置英文月份显示
	cyear.innerHTML = my_year; //设置年份显示
}
//获取某年某月第一天是星期几
function dayStart(month, year) {
	var tmpDate = new Date(year, month, 1);
	return (tmpDate.getDay());
}

var take = function(x){
	 $.post("insert.do",{
    	 year:my_year,
    	 month:my_month+1,
		 day:x,
		 status:type(),
     });
}

var cancel = function(x){
	 $.post("delete.do",{
   	 	 year : my_year,
   	 	month : my_month+1,
	 	  day : x,
    });
}

var type = function(){
	if(FirstBox == true && SecondBox == false && ThirdBox == false){
		return 1;
	}else if(FirstBox == false && SecondBox == true && ThirdBox == false){
		return 2;
	}else if(FirstBox == false && SecondBox == false && ThirdBox == true){
		return 3;
	}else {
		return 0;
	}
}

function db(x) {
	  var d=document.getElementsByTagName('li')
	  cancel(x)
	  for(p=d.length;p--;){
    	 if(d[p].id!=x){;}
       else{
       	d[p].style.background="white"
       }
 	  } 
	  qiandao()
}

function g(x) {
    var d=document.getElementsByTagName('li')
    
    for(p=d.length;p--;){
      	 if(d[p].id!=x){;}
         else{
         		if (FirstBox == true) {
         		d[p].style.background='red'
         	} else if (SecondBox == true) {
         		d[p].style.background='blue'
         	} else if (ThirdBox == true) {
         		d[p].style.background='yellow'
         	} else {
         		d[p].style.background='#FFFFFF'
         	}
         }
    } 
    take(x)
    qiandao()
}

//计算某年是不是闰年，通过求年份除以4的余数即可
function daysMonth(month, year) {
	var tmp = year % 4;
	if (tmp == 0) {
		return (month_olympic[month]);
	} else {
		return (month_normal[month]);
	}
}

qiandao();
</script>
</body>
</html>
