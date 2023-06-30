<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
   <head>
      <!-- Required meta tags -->
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="icon" type="image/png" href="img/logo2.png">
      <title>BlueLemon</title>
      <meta name="description" content="BlueLemon">
      <meta name="keywords" content="bootstrap5, e-learning, forum, games, online course, Social Community, social events, social feed, social media, social media template, social network html, social sharing, twitter">
      <!-- Bootstrap CSS -->
      <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
      <!-- Slich Slider -->
      <link href="vendor/slick/slick/slick.css" rel="stylesheet">
      <link href="vendor/slick/slick/slick-theme.css" rel="stylesheet">
      <!-- Icofont -->
      <link href="vendor/icofont/icofont.min.css" rel="stylesheet">
      <!-- Font Icons -->
      <link href="vendor/icons/css/materialdesignicons.min.css" rel="stylesheet" type="text/css">
      <!-- Custom Css -->
      <link href="css/style.css" rel="stylesheet">
      <!-- Material Icons -->
      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
   </head>
   <style>
   		.qnaDiv {
   			border : 1px solid #c6c1c1;
   			padding : 9px;
   			border-radius : 10px;
   			color : #555568;
   		}
   </style>
	<script>
		function submitForm() {
        	// 입력 필드 값 가져오기
	        var member_Id = document.getElementById('member_Id').value;
	        var qna_Title = document.getElementById('qna_Title').value;
	        var qna_Message = document.getElementById('qna_Message').value;
	        
	        // 유효성 검사
	        if (member_Id.trim() === '' || qna_Title.trim() === '' || qna_Message.trim() === '') {
	            // 빈칸이 존재하면 알림 메시지 표시
	            alert('입력 필드를 모두 작성해주세요.');
	        } else {
	            // AJAX 요청 보내기
	            var xhr = new XMLHttpRequest();
	            xhr.open('POST', 'qna', true);
	            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	            xhr.onreadystatechange = function() {
	                if (xhr.readyState === 4) {
	                    if (xhr.status === 200) {
	                        // 응답 처리
	                        alert('문의사항 제출이 성공적으로 처리되었습니다.');
	                        window.location.href = 'contact';
	                    } else {
	                        alert('문의사항 제출 중 오류가 발생했습니다.');
	                    }
	                }
	            };
	            // 데이터 전송
	            var params = 'member_Id=' + encodeURIComponent(member_Id) +
	                         '&qna_Title=' + encodeURIComponent(qna_Title) +
	                         '&qna_Message=' + encodeURIComponent(qna_Message);
	            xhr.send(params);
	        }
	    }
	</script>
   <body class="bg-light">   
      <div class = "goToTop">
 	     <a href = "#"><img src = "img/goToTop.png"></a>
      </div>
      <div class="theme-switch-wrapper ms-3">
         <label class="theme-switch" for="checkbox">
            <input type="checkbox" id="checkbox">
            <span class="slider round"></span>
            <i class="icofont-ui-brightness"></i>
         </label>
         <em>Enable Dark Mode!</em>
      </div>
      <div class="web-none d-flex align-items-center px-3 pt-3">
         <a href="index" class="text-decoration-none">
         <img src="img/logo.png" class="img-fluid logo-mobile" alt="brand-logo">
         </a>
         <button class="ms-auto btn btn-primary ln-0" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">
         <span class="material-icons">menu</span>
         </button>
      </div>
      <div class="py-4">
      <div class="container">
         <div class="row position-relative">
            <!-- Main Content -->
            <main class="col col-xl-6 order-xl-2 col-lg-12 order-lg-1 col-md-12 col-sm-12 col-12">
               <div class="main-content">
                  <div class="mb-5">
                     <div class="feature bg-primary bg-gradient text-white rounded-4 mb-3"><i class="icofont-envelope"></i></div>
                     <h1 class="fw-bold text-black mb-1">How can we help?</h1>
                     <p class="lead fw-normal text-muted mb-0">We'd love to hear from you</p>
                  </div>
                  <!-- Feeds -->
                  <div class="feeds">
                     <!-- Feed Item -->
                     <div class="bg-white p-4 feed-item rounded-4 shadow-sm faq-page">
                        <div class="mb-3">
                           <h5 class="lead fw-bold text-body mb-0">Contact Form</h5>
                        </div>
                        <div class="row justify-content-center">
                           <div class="col-lg-12">
                           	  <!-- action으로 폼 데이터 처리 해야함. -->
                              <form class="form-floating-space" id="contactForm" data-sb-form-api-token="API_TOKEN" method = "post">                              
                                 <!-- Name input-->
                                 <div class="form-floating mb-3">
                                    <input class="form-control rounded-5" id="member_Id" name = "member_Id" type="text" value = "${sessionScope.loginUser.member_Id}" readonly>
                                    <label for="member_Id">Member ID</label>
                                 </div>
                                 
                                 <!-- Title input-->
                                 <div class="form-floating mb-3">
                                    <input class="form-control rounded-5" id="qna_Title" name = "qna_Title" type="text" data-sb-validations="required" maxlength="60">
                                    <label for="qna_Title">Title</label>
                                    <div class="invalid-feedback" data-sb-feedback="qna_Title:required">A Title is required.</div>
                                 </div>
                                 
                                 <!-- Message input-->
                                 <div class="form-floating mb-3">
                                    <textarea class="form-control rounded-5" id="qna_Message" name = "qna_Message" style="height: 10rem" data-sb-validations="required" maxlength="900"></textarea>
                                    <label for="qna_Message">Message</label>
                                    <div class="invalid-feedback" data-sb-feedback="qna_Message:required">A Message is required.</div>
                                 </div>
                                 
                                 <div class="d-none" id="submitSuccessMessage">
                                    <div class="text-center mb-3">
                                       <div class="fw-bolder">Form submission successful!</div>
                                    </div>
                                 </div>
                                 
                                 <div class="d-none" id="submitErrorMessage">
                                    <div class="text-center text-danger mb-3">Error sending message!</div>
                                 </div>
                                 
                                 <!-- Submit Button-->
                                 <div class="d-grid">
							        <button class="btn btn-primary w-100 rounded-5 text-decoration-none py-3 fw-bold text-uppercase m-0" id="submitButton" type="button" onclick="submitForm()">문의하기</button>
							     </div>
                              </form>
                           </div>
                        </div>
                     </div>
                        <!-- Contact form-->
                        <div class="bg-white p-4 feed-item rounded-4 shadow-sm faq-page" style = "margin-top : 10px; margin-bottom : 10px;">
	                        <div class="rounded-3">
	                           <div class="mb-3">
	                              <h5 class="lead fw-bold text-body mb-0">My Questions</h5>
	                           </div>
	                           <div class="row justify-content-center">
	                              <div class="col-lg-12">								     
								     <div class="accordion overflow-hidden bg-white" id="accordionExample">
									    <c:forEach items = "${qnaList}" var = "qna">
		                                   <div class="accordion-item">
		                                      <h3 class="accordion-header" id="heading_${qna.qna_Seq}">		                                      	 
		                                         <button class ="accordion-button collapsed fw-bold" style = "padding : 10px; width : 100%;"type="button" data-bs-toggle="collapse" data-bs-target="#collapse_${qna.qna_Seq}" aria-expanded="false" aria-controls="collapse_${qna.qna_Seq}">
												    <div style = "color : #555568; display: flex; flex-direction: column;">	
												    	<c:choose>
												    		<c:when test = "${qna.qna_Done eq '2'}">
												    			<div style = "font-size : 5px;">
												    				<p style = "display : inline;">[ Answered ]</p>&nbsp; ${qna.qna_Date}
												    			</div>
												    			<div style="font-size : 15px; text-align : left; margin-top : 5px; color : #0975e0;">
												    				[ Title ]
												    				<div style = "display : inline; overflow : hidden;">
												    					${qna.qna_Title}
												    				</div>
												    			</div>										
												    		</c:when>
												    		<c:otherwise>
												    			<div style = "font-size : 5px;">
												    				<p style = "display : inline;">[ Not Answered ]</p>&nbsp; ${qna.qna_Date}
												    			</div>
												    			<div style="font-size: 15px; text-align: left; margin-top : 5px;">
												    				[ Title ]
												    				<div style = "display : inline; overflow : hidden;">
												    					${qna.qna_Title}
												    				</div>
												    			</div>										
												    		</c:otherwise>
												    	</c:choose>			       
												    </div>
												</button>
		                                      </h3>
		                                      <div class="accordion-collapse collapse" id="collapse_${qna.qna_Seq}" aria-labelledby="heading_${qna.qna_Seq}" data-bs-parent="#accordionExample">
		                                         <div class="accordion-body" style = "padding : 10px;">
		                                            <div class = "qnaDiv d-flex" style = "width : 100%;">
		                                               <div style = "width : 10%; margin-right : 2%;">
		                                         	      <label style="display: flex; align-items: center; justify-content: center; height: 100%;">
		                                         	         Message
		                                         	      </label>
		                                               </div>
		                                         	   <div style = "width : 76%; border-left : 1px solid #d4d4d9;">
		                                         	      <div style = "margin-left : 2%; width : 100%; word-wrap: break-word;">${qna.qna_Message}</div>		
		                                         	   </div>
		                                         	   <div style = "width : 8%; margin-left : 2%; border-left : 1px solid #d4d4d9;">
		                                         	      <a href = "deleteQna?qna_Seq=${qna.qna_Seq}" style = "margin-left : 29%;">
		                                         	         <img src = "img/delete.png">
		                                         	      </a>
		                                         	   </div>
		                                         	</div>
		                                         	<c:choose>
		                                         	   <c:when test = "${qna.qna_Done eq '2'}">
		                                         	      <div class = "qnaDiv d-flex" style = "margin-top : 5px;">
		                                                     <div style = "width : 10%; margin-right : 2%;">
		                                         		        <label style="display: flex; align-items: center; justify-content: center; height: 100%;">
		                                         		           Answer
		                                         		        </label>
		                                         		     </div>
		                                         		     <div style = "width : 88%; border-left : 1px solid #d4d4d9;">
		                                         		        <div style = "margin-left : 2%;">${qna.qna_Answer}</div>
		                                         		     </div>  		                                         		
		                                         	      </div>
		                                         	   </c:when>
		                                         	   <c:otherwise>
		                                         	      <div class = "qnaDiv d-flex" style = "margin-top : 5px;">
		                                                     <div style = "width : 10%; margin-right : 2%;">
		                                         		        <label style="display: flex; align-items: center; justify-content: center; height: 100%;">
		                                         		           Answer
		                                         		        </label>
		                                         		     </div>
		                                         		     <div style = "width : 88%; border-left : 1px solid #d4d4d9;">
		                                         		        <div style = "margin-left : 2%;">Sorry, Now We Are Checking Your Question</div>
		                                         		     </div>                                         		
		                                         	      </div>
		                                         	   </c:otherwise> 
		                                         	</c:choose>
		                                         </div>
		                                      </div>
		                                   </div>     
		                                </c:forEach>	                                             
		                             </div>                       
	                              </div>
	                           </div>
	                        </div>
	                    </div>
	                 <div class="bg-white p-4 feed-item rounded-4 shadow-sm faq-page">
                        <div class="row row-cols-2 row-cols-lg-4 pt-5" style = "padding-top : 5px!important;">
                           <div class="col">
                              <div class="feature bg-primary bg-gradient text-white rounded-4 mb-3"><i class="icofont-chat"></i></div>
                              <div class="h6 mb-2 fw-bold text-black">Chat with us</div>
                              <p class="text-muted mb-0">Chat live with one of our support specialists.</p>
                           </div>
                           <div class="col">
                              <div class="feature bg-primary bg-gradient text-white rounded-4 mb-3"><i class="icofont-people"></i></div>
                              <div class="h6 fw-bold text-black">Ask the community</div>
                              <p class="text-muted mb-0">Explore our community forums and communicate with other users.</p>
                           </div>
                           <div class="col">
                              <div class="feature bg-primary bg-gradient text-white rounded-4 mb-3"><i class="icofont-question-circle"></i></div>
                              <div class="h6 fw-bold text-black">Support center</div>
                              <p class="text-muted mb-0">Browse FAQ's and support articles to find solutions.</p>
                           </div>
                           <div class="col">
                              <div class="feature bg-primary bg-gradient text-white rounded-4 mb-3"><i class="icofont-telephone"></i></div>
                              <div class="h6 fw-bold text-black">Call us</div>
                              <p class="text-muted mb-0">Call us during normal business hours at (555) 892-9403.</p>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </main>
            <aside class="col col-xl-3 order-xl-1 col-lg-6 order-lg-2 col-md-6 col-sm-6 col-12">
               <div class="p-2 bg-light offcanvas offcanvas-start" tabindex="-1" id="offcanvasExample">
                  <div class="sidebar-nav mb-3">
                     <div class="pb-4">
                        <a href="index" class="text-decoration-none">
                        <img src="img/logo.png" class="img-fluid logo" alt="brand-logo">
                        </a>
                     </div>
                     <!-- 사이드바의 항목들을 정의하는 부분 -->
                        <ul class="navbar-nav justify-content-end flex-grow-1">
                           <li class="nav-item">
                              <a href="index" class="nav-link"><span class="material-icons me-3">house</span> <span>Feed</span></a>
                           </li>
                           <li class="nav-item">
                              <a href = "profile?member_Id=${sessionScope.loginUser.member_Id}" class="nav-link"><img src = "img/uploads/profile/${loginUser.member_Profile_Image}" style = "width : 27px; height : 27px; border-radius : 50%; overfloiw : hidden;"> <span>&nbsp;&nbsp;&nbsp;${loginUser.member_Id}'s PROFILE</span></a>
                           </li>
                           <li class="nav-item">
                              <a href="follow?member_Id=${loginUser.member_Id}" class="nav-link"><span class="material-icons me-3">diversity_3</span> <span>follow</span></a>
                           </li>
                           <!-- PAGES 드롭다운 항목 -->
                           <li class="nav-item dropdown active">
                              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <span class="material-icons me-3">web</span> Contact Us
                              </a>
                              <ul class="dropdown-menu px-2 py-1 mb-2">
                                 <li><a class="dropdown-item rounded-3 px-2 py-1 my-1" href="contact">Contact</a></li>
                                 <li><a class="dropdown-item rounded-3 px-2 py-1 my-1" href="faq">FAQ</a></li>
                              </ul>
                           </li>
                           
                           <li class="nav-item">
                              <a href="logout" class="nav-link"><span class="material-icons me-3">logout</span> <span>Logout</span></a>
                           </li>
                           
                           <li class="nav-item dropdown">
                              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                              	<c:choose>
                              		<c:when test="${alarmListSize==0}">
                              		<span class="material-icons me-3"><span class="material-symbols-outlined">notifications</span></span> Notification
                              		</c:when>
                              		<c:otherwise>
                              		<span class="material-icons me-3"><span class="material-symbols-outlined">notifications_active</span></span> Notification  +${alarmListSize}
                              		</c:otherwise>
                              	</c:choose>
                              </a>
                              <ul class="dropdown-menu px-2 py-1 mb-2">
                              	<c:forEach var="alarmVO" items="${alarmList}" begin="0" end="10">
                              		<c:choose>
                              			<c:when test="${alarmVO.kind==1}">
                              				<li>
												<a class="dropdown-item rounded-3 px-2 py-1 my-1" href="/blue/alarmFollow?member_Id=${alarmVO.to_Mem}&alarm_Seq=${alarmVO.alarm_Seq}" style="font-size:11px; background-color: azure;">
													${alarmVO.message}
												</a>
											</li> 			
                              			</c:when>
                              			<c:when test="${alarmVO.kind==5}">
                              				<li>
												<a class="dropdown-item rounded-3 px-2 py-1 my-1" href="/blue/alarmContact?alarm_Seq=${alarmVO.alarm_Seq}" style="font-size:11px; background-color: azure;">
													${alarmVO.message}
												</a>
											</li> 
                              			</c:when>
                              			<c:otherwise>
                              				<li>
												<a class="dropdown-item rounded-3 px-2 py-1 my-1" href="/blue/alarmIndex?post_Seq=${alarmVO.post_Seq}&alarm_Seq=${alarmVO.alarm_Seq}" style="font-size:11px; background-color: azure;">
													${alarmVO.message}
												</a>
											</li>   
                              			</c:otherwise>
                              		</c:choose>
                                </c:forEach>
                              </ul>
                           </li>
                           
                        </ul>
                     </div>
                  </div>
                  
                  <!-- Sidebar -->
                  <!-- 브라우저 창의 크기가 줄어들때 나오는 메뉴버튼을 누르면 왼쪽에서 나타나는 사이드바 -->
                  <div class="ps-0 m-none fix-sidebar">
                     <div class="sidebar-nav mb-3">
                        <div class="pb-4 mb-4">
                           <a href="index" class="text-decoration-none">
                           <img src="img/logo.png" class="img-fluid logo" alt="brand-logo">
                           </a>
                        </div>
                        <ul class="navbar-nav justify-content-end flex-grow-1">
                           <li class="nav-item">
                              <a href="index" class="nav-link"><span class="material-icons me-3">house</span> <span>Feed</span></a>
                           </li>
                           <li class="nav-item">
                              <a href = "profile?member_Id=${sessionScope.loginUser.member_Id}" class="nav-link"><img src = "img/uploads/profile/${loginUser.member_Profile_Image}" style = "width : 27px; height : 27px; border-radius : 50%; overfloiw : hidden;"> <span>&nbsp;&nbsp;&nbsp;${loginUser.member_Id}'s PROFILE</span></a>
                           </li>
                           <li class="nav-item">
                              <a href="follow?member_Id=${loginUser.member_Id}" class="nav-link"><span class="material-icons me-3">diversity_3</span> <span>follow</span></a>
                           </li>
                           <li class="nav-item dropdown">
                              <a class="nav-link dropdown-toggle active" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <span class="material-icons me-3">web</span> Contact Us
                              </a>
                              <ul class="dropdown-menu px-2 py-1 mb-2">
                                 <li><a class="dropdown-item rounded-3 px-2 py-1 my-1 active" href="contact">Contact</a></li>
                                 <li><a class="dropdown-item rounded-3 px-2 py-1 my-1" href="faq">FAQ</a></li>
                              </ul>
                           </li>                           
                           <li class="nav-item">
                              <a href="logout" class="nav-link"><span class="material-icons me-3">logout</span> <span>Logout</span></a>
                           </li>                           
                           <li class="nav-item dropdown">
                              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                              	<c:choose>
                              		<c:when test="${alarmListSize==0}">
                              		<span class="material-icons me-3"><span class="material-symbols-outlined">notifications</span></span> Notification
                              		</c:when>
                              		<c:otherwise>
                              		<span class="material-icons me-3"><span class="material-symbols-outlined">notifications_active</span></span> Notification  +${alarmListSize}
                              		</c:otherwise>
                              	</c:choose>
                              </a>
                              <ul class="dropdown-menu px-2 py-1 mb-2">
                              	<c:forEach var="alarmVO" items="${alarmList}" begin="0" end="10">
                              		<c:choose>
                              			<c:when test="${alarmVO.kind==1}">
                              				<li>
												<a class="dropdown-item rounded-3 px-2 py-1 my-1" href="/blue/alarmFollow?member_Id=${alarmVO.to_Mem}&alarm_Seq=${alarmVO.alarm_Seq}" style="font-size:11px; background-color: azure;">
													${alarmVO.message}
												</a>
											</li> 			
                              			</c:when>
                              			<c:when test="${alarmVO.kind==5}">
                              				<li>
												<a class="dropdown-item rounded-3 px-2 py-1 my-1" href="/blue/alarmContact?alarm_Seq=${alarmVO.alarm_Seq}" style="font-size:11px; background-color: azure;">
													${alarmVO.message}
												</a>
											</li> 
                              			</c:when>
                              			<c:otherwise>
                              				<li>
												<a class="dropdown-item rounded-3 px-2 py-1 my-1" href="/blue/alarmIndex?post_Seq=${alarmVO.post_Seq}&alarm_Seq=${alarmVO.alarm_Seq}" style="font-size:11px; background-color: azure;">
													${alarmVO.message}
												</a>
											</li>   
                              			</c:otherwise>
                              		</c:choose>
                                </c:forEach>
                              </ul>
                           </li>
                           
                        </ul>
                     </div>
                  </div>
               </aside>
               
              <aside class="col col-xl-3 order-xl-3 col-lg-6 order-lg-3 col-md-6 col-sm-6 col-12">
                  <div class="fix-sidebar">
                     <div class="side-trend lg-none">
                        <!-- Search Tab -->
                        <div class="sticky-sidebar2 mb-3">
                           <!-- 우측 상단의 검색탭 -->
                           <div class="input-group mb-4 shadow-sm rounded-4 overflow-hidden py-2 bg-white">
                           <span class="input-group-text material-icons border-0 bg-white text-primary">search</span>
                           <form action="/blue/search_HashTag" method="get">
                           		<input type="text" class="form-control border-0 fw-light ps-1" placeholder="Search People" id="keyword" name="tag_Content" onkeyup="searchMembers()">
                           </form>
                        </div>
                        <!-- 검색 결과 리스트 -->
                        <div id="searchResults"></div>
                           <div class="bg-white rounded-4 overflow-hidden shadow-sm mb-4">
                              <!-- 실시간 인기 급상승 게시글 -->
                              <h6 class="fw-bold text-body p-3 mb-0 border-bottom">Hottest Feed</h6>
                              <!-- 트랜딩 아이템 -->
                              <!-- 표시할 최대 문자 수 -->
                              <c:set var = "maxChar" value = "50"/>                              
                              <c:forEach items="${hottestFeed}" var="postVO" begin="0" end="4">
                         	     <div class="p-3 border-bottom d-flex">
                         	        <c:choose>
									   <c:when test = "${postVO.post_Image_Count == 0}">
									      <a id="openModalBtn" class="text-decoration-none" data-bs-toggle="modal" data-bs-target="#commentModal2" onclick="replyModalseq(${postVO.post_Seq})" style = "width : 100%;">
								             <div class = "d-flex">
								                <div style = "width : 60%;">
									               <p class="fw-bold mb-0 pe-3 text-dark">${postVO.post_Like_Count}'s Likes</p>
									               <small class="text-muted">Posted by ${postVO.member_Id}</small>
									               <br><br>
									               <small class="text-muted">
									                  <c:out value = "${fn:substring(postVO.post_Content, 0, 15)}"/> . . .
									               </small>
									               <br>
									               <c:choose>
									                  <c:when test="${postVO.post_Hashtag eq '' }">
									                  </c:when>
									                  <c:otherwise>
									                     <small class="text-muted">
									                        <c:out value = "${fn:substring(postVO.post_Hashtag, 0, 15)}"/> . . .
									                     </small>									                  
									                  </c:otherwise>
									               </c:choose>
									            </div>
									            <div style = "width : 40%;">	         
									            </div>
									         </div>
									      </a>
									   </c:when>
									   <c:otherwise>
									      <a id="openModalBtn" class="text-decoration-none" data-bs-toggle="modal" data-bs-target="#commentModal" onclick="modalseq(${postVO.post_Seq})" style = "width : 100%;">
								             <div class = "d-flex">
								                <div style = "width : 60%;">
									               <p class="fw-bold mb-0 pe-3 text-dark">${postVO.post_Like_Count}'s Likes</p>
									               <small class="text-muted">Posted by ${postVO.member_Id}</small>
									               <br><br>
									               <small class="text-muted">
									                  <c:out value = "${fn:substring(postVO.post_Content, 0, 15)}"/> . . .
									               </small>
									               <br>
									               <c:choose>
									                  <c:when test="${postVO.post_Hashtag eq 'nothing' }">
									                  </c:when>
									                  <c:otherwise>
									                     <small class="text-muted">
									                        <c:out value = "${fn:substring(postVO.post_Hashtag, 0, 15)}"/> . . .
									                     </small>									                  
									                  </c:otherwise>
									               </c:choose>
									            </div>
									            <div style = "width : 40%;">								      									         
									      	       <img src="img/uploads/post/${postVO.post_Seq}-1.png" class="img-fluid rounded-4 ms-auto" width = "100" height = "100">									         
									            </div>
									         </div>
									      </a>
									   </c:otherwise>
									</c:choose>	
						         </div>
							  </c:forEach>
                           </div>
                        </div>
                     </div>
                  </div>
               </aside>
            </div>
         </div>
      </div>
      <div class="py-3 bg-white footer-copyright">
         <div class="container">
            <div class="row align-items-center">
               <div class="col-md-8">
                  <span class="me-3 small">Â©2023 <b class="text-primary">BlueLemon</b>. All rights reserved</span>
               </div>
               <div class="col-md-4 text-end">
                  <a target="_blank" href="#" class="btn social-btn btn-sm text-decoration-none"><i class="icofont-facebook"></i></a>
                  <a target="_blank" href="#" class="btn social-btn btn-sm text-decoration-none"><i class="icofont-twitter"></i></a>
                  <a target="_blank" href="#" class="btn social-btn btn-sm text-decoration-none"><i class="icofont-linkedin"></i></a>
                  <a target="_blank" href="#" class="btn social-btn btn-sm text-decoration-none"><i class="icofont-youtube-play"></i></a>
                  <a target="_blank" href="#" class="btn social-btn btn-sm text-decoration-none"><i class="icofont-instagram"></i></a>
               </div>
            </div>
         </div>
      </div>
      
      <%@ include file="modal.jsp" %>
      
      <!-- Jquery Js -->
      <script src="vendor/jquery/jquery.min.js"></script>
      <!-- Bootstrap Bundle Js -->
      <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
      <!-- Custom Js -->
      <script src="js/custom.js"></script>
      <!-- Slick Js -->
      <script src="vendor/slick/slick/slick.min.js"></script>
      <!-- Follow Js -->
      <script src="js/follow.js"></script>
      <!-- Like Js -->
      <script src="js/like.js"></script>
      <!-- Modal Js -->
      <script src="js/modal.js"></script>
      <!-- People Js -->
      <script src="js/people.js"></script>
      <!-- Trending Js -->
      <script src="js/trending.js"></script>
      <!-- Search Peple Js -->
      <script src="js/searchpeople.js"></script>
   </body>
</html>