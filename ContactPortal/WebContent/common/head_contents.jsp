<%@ page contentType="text/html;charset=UTF-8" errorPage="/common/error.jsp"%>
    <title>컨텍센터 스케줄링 포털</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/jquery.toast.min.css">
    <link rel="stylesheet" href="/includes/bootstrap-3.3.2/css/bootstrap.min.css">  
    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="/includes/bootstrap-3.3.2/js/bootstrap.min.js"></script>
    <script src="/js/jquery.toast.min.js"></script>
    <style> 
        /* Remove the navbar's default margin-bottom and rounded borders */
        .navbar {
            margin-bottom: 0; 
            border-radius: 0;
        }

        /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
        .row.content {height: 450px}

        /* Set gray background color and 100% height */
        .sidenav {
            padding-top: 20px;
            background-color: #f1f1f1;
            height: 100%;
        }

        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

		html, body, .container-fluid, .row {
		    height: 100%;
		}
		
        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }
            .row.content {height:auto;}
        }
    </style>

    <script language="JavaScript">
        // [메뉴, Level, Link]
        var menubars = {
            "근태정보" : [
                ["개인 근태 현황", "1", "#"],
                ["근태 신청/조회", "1", "#"],
                ["선호 근무 신청", "1", "#"],
                ["근태 승인", "1", "#"],
                ["월 스케줄 조회", "1", "#"],
            ],
            "월간 스케줄" : [
                ["월간 콜 예측", "1", "#"],
                ["월간 인력 계획", "1", "#"],
                ["월간 스케줄링", "1", "#"],
            ],
            "SKD 변경" : [
                ["SKD 변경", "1", "#"],
                ["관리자 메뉴", "1", "#"],
            ],
            "직원 정보" : [
                ["직원 조회", "1", "#"],
                ["상태 변경", "1", "#"],
                ["계정 등록/삭제", "1", "#"],
                ["변경 예약 조회", "1", "#"],
            ],
            "월간 일정" : [
                ["월간 일정 조회/설정", "1", "#"],
                ["교육 일정 설정", "1", "#"],
                ["이벤트 설정", "1", "#"],
            ],
            "ADMIN" : [
                ["안내 문구 관리", "1", "#"],
                ["SHT 코드 관리", "1", "#"],
                ["비밀번호 초기화", "1", "#"],
                ["근무신청 삭제", "1", "#"],
                ["근태신청 마감일", "1", "#"],
                ["공휴일 설정", "1", "#"],
            ],
            "테스트 샘플" : [
                ["READ", "1", "/sample/read.jsp"],
                ["WRITE", "1", "/sample/write.jsp"],
            ]
        }
        
        var cur_level=5
        var menu_index = [];
	     // build the index
	     for (var x in menubars) {
	    	 menu_index.push(x);
	     }

        var idx=0
        $(function () {
            var mainmenu = $("#mainmenus");
            mainmenu.empty()
            $.each(menubars, function(key, value) {
                mainmenu.append('<li><a href="#" id='+idx+'>'+key+'</a></li>');
                idx++;
            });
        });
        
        function showSubMenu(idx) {
        	//console.log(menu_index[idx])
        	submenu=$('#submenu')
        	submenu.empty();
        	submenu.append('<div>');
        	$.each(menubars[menu_index[idx]], function() {
        		if(this[1]<=cur_level) {
        			submenu.append("<p><a href='"+this[2]+"'>"+this[0]+"</a></p>")
        		}
        	});
        	submenu.append('</div>');
        }

        $(document).ready(function(){
	        $('#logout').on("click", function() {
	            console.log( $( this ).text() );
	            $.toast('TODO: 로그아웃 페이지 링크 필요');
	            //alert("TODO: 로그아웃 페이지 링크 필요")
	        });
	        // initial menu
	        showSubMenu(0);
	       // $(".menu2").hide();
	        $(".nav a").on("click", function(){
	            $(".nav").find(".active").removeClass("active");
	            $(this).parent().addClass("active");
	            showSubMenu($(this).attr('id'));
	            //console.log($(this).attr('id'))
	        });
        });
        
       	$(document).ready(function(){
       		// 페이지가 선택된 경우 Main Menu를 Active 상태로 
       		// TODO: Sub Menu도 Active Status (EX: 밝게)
           idx=0
           curPath="<%=request.getRequestURI()%>"
           if(curPath!="") {
           		$.each(menubars, function(key, value) {
           			//console.log("SEE : "+value[0][2]);
           			$.each(value , function(item) {
                		if(value[item][2]==curPath) {
            	       		$("#"+idx).parent().addClass("active");
            	       		showSubMenu(idx);
                		}
           			});
                    idx++;
            	});
            }
       	});

    </script> 
