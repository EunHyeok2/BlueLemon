<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- 필수 메타 태그 -->
	<meta charset="utf-8">
	<!-- 데스크톱, 태블릿, 모바일 등 화면 크기를 자동으로 조절해주는 곳 -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- 파비콘의 이미지를 지정하는 곳 -->
	<link rel="icon" type="image/png" href="img/logo2.png">
	<title>BlueLemon</title>
	<!-- description : 웹 페이지의 내용을 간략하게 설명하는 역할(검색 엔진 최적화, 웹 페이지의 구조화 와 관리) -->
	<meta name="description" content="BlueLemon">
	<!-- keywords 메타 태그를 선언하는 이유는 검색 엔진 최적화와 웹 사이트의 구조화와 관리를 위한 참고 자료를 제공하기 위해서 이다. -->
	<meta name="keywords" content="bootstrap5, e-learning, forum, games, online course, Social Community, social events, social feed, social media, social media template, social network html, social sharing, twitter">
	<!-- 부트스트랩 css -->
	<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<!-- Slich Slider -->
	<link href="vendor/slick/slick/slick.css" rel="stylesheet">
	<link href="vendor/slick/slick/slick-theme.css" rel="stylesheet">
	<!-- Icofont -->
	<link href="vendor/icofont/icofont.min.css" rel="stylesheet">
	<!-- Font Icons -->
	<link href="vendor/icons/css/materialdesignicons.min.css" rel="stylesheet" type="text/css">
	<!-- 수정해 볼수있는 css들 (index, slidebar 등등) -->
	<link href="css/style.css" rel="stylesheet">
	<!-- 아이콘 css -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body class="bg-light">   
	<div class = "goToTop">
		<a href = "#"><img src = "img/goToTop.png"></a>
	</div>
	<!-- 페이지 우측 하단에 고정되어있는 테마 변경 스위치  -->
	<div class="theme-switch-wrapper ms-3">
		<label class="theme-switch" for="checkbox">
			<input type="checkbox" id="checkbox">
			<span class="slider round"></span>
			<i class="icofont-ui-brightness"></i>
		</label>
		<em>Enable Dark Mode!</em>
	</div>      
    <!-- 브라우저 창의 크기가 줄어들때 오른쪽 위에 출력되는 메뉴펼치기 버튼 -->
    <div class="web-none d-flex align-items-center px-3 pt-3">
		<a href="index" class="text-decoration-none">
       		<img src="img/logo.png" class="img-fluid logo-mobile" alt="brand-logo">
       	</a>
       	<button class="ms-auto btn btn-primary ln-0" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">
       		<span class="material-icons">menu</span>
       	</button>
    </div>
	<!-- index페이지의 구성 요소들 -->
	<div class="py-4">
		<!-- 반응형 컨테이너 div -->
		<div class="container">
			<!-- row : 열(col)을 포함하는 행(row)를 생상하는데 사용되는 css프레임워크 클래스 -->
         	<!-- position-relative : 요소들의 위치를 상대적으로 설정 -->
            <div class="row position-relative">
				<!-- Main Content -->
               	<!-- index페이지의 센터 column -->
               	<main class="col col-xl-6 order-xl-2 col-lg-12 order-lg-1 col-md-12 col-sm-12 col-12">
               	  	<!-- 메인 컨테이너 요소들을 감싸고있는 div -->
                  	<div class="main-content">
                  	 	<!-- 피드, 피플, 트랜딩 버튼 -->
                     	<ul class="top-osahan-nav-tab nav nav-pills justify-content-center nav-justified mb-4 shadow-sm rounded-4 overflow-hidden bg-white sticky-sidebar2" id="pills-tab" role="tablist">
	                        <li class="nav-item" role="presentation">
	                           <button class="p-3 nav-link text-muted active" id="pills-feed-tab" data-bs-toggle="pill" data-bs-target="#pills-feed" type="button" role="tab" aria-controls="pills-feed" aria-selected="true">Following</button>
	                        </li>
	                        <li class="nav-item" role="presentation">
	                           <button class="p-3 nav-link text-muted" id="pills-people-tab" data-bs-toggle="pill" data-bs-target="#pills-people" type="button" role="tab" aria-controls="pills-people" aria-selected="false">Follower</button>
	                        </li>
                     	</ul>                     
                     	<!-- Following 버튼 클릭시 출력부분 -->
                     	<div class="tab-content" id="pills-tabContent">
                     	   	<div class="tab-pane fade show active" id="pills-feed" role="tabpanel" aria-labelledby="pills-feed-tab">
								<h6 class="mb-3 fw-bold text-body">Your Following</h6>
                           		<div class="bg-white rounded-4 overflow-hidden mb-4 shadow-sm">
             						<c:forEach items="${following}" var="memberVO" begin="0" end="9">
                              			<a href="profile?member_Id=${memberVO.member_Id}" class="p-3 d-flex text-dark text-decoration-none account-item pf-item">
	                                 		<img src="img/uploads/profile/${memberVO.member_Profile_Image}" class="img-fluid rounded-circle me-3" alt="profile-img">
	                                 		<div>
	                                    		<p class="fw-bold mb-0 pe-3 d-flex align-items-center">${memberVO.member_Id} </p>
	                                    		<div>
	                                       			<p class="text-muted fw-light mb-1 small">${memberVO.member_Name}</p>
	                                       			<span class="d-flex align-items-center small">${memberVO.member_Mbti}/${memberVO.member_Gender}/${memberVO.member_Email}</span>
	                                    		</div>
	                                 		</div>
	                                 		<div class="ms-auto">
	                                    		<div class="btn-group" role="group" aria-label="Basic checkbox toggle button group">
	                                       			<input type="checkbox" class="btn-check" name="btncheckbox" id="btncheck${memberVO.member_Id}" checked>
	                                       			<label class="btn btn-outline-primary btn-sm px-3 rounded-pill" for="btncheck${memberVO.member_Id}" onclick = "changeFollow('${memberVO.member_Id}')"><span class="following d-none">Following</span><span class="follow">+ Follow</span></label>
		                                		</div>
	                                 		</div>
                              			</a>
                              		</c:forEach>
                              		<div id="followingContainer"></div>
                              	</div>
                              	<div id="followingloadButton">
		                         	<c:choose>
			                         	<c:when test="${followingTotalPageNum<2}">			                         		
			                         	</c:when>
			                         	<c:otherwise>
							                <div class="ms-auto" align="center">
		                                 		<span class="btn btn-outline-primary btn-sm px-3 rounded-pill" id="followingload" onclick="followingload('${followingTotalPageNum}', '${followingPageNum}', '${member_Id}')">
		                                 			+ 더보기
		                                 		</span>
		                                	</div>
			                         	</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
                        <!-- Follower 탭 클릭시 -->
                        <div class="tab-pane fade" id="pills-people" role="tabpanel" aria-labelledby="pills-people-tab">
							<h6 class="mb-3 fw-bold text-body">Your Follower</h6>
                           	<div class="bg-white rounded-4 overflow-hidden mb-4 shadow-sm">
                           		<div>
                              		<!-- Account Item -->
                              		<c:forEach items="${follower}" var="memberVO" begin="0" end="9">
                              			<a href="profile?member_Id=${memberVO.member_Id}"  class="p-3 d-flex text-dark text-decoration-none account-item pf-item">
                                 			<img src="img/uploads/profile/${memberVO.member_Profile_Image}" class="img-fluid rounded-circle me-3" alt="profile-img">
                                 			<div>
                                    			<p class="fw-bold mb-0 pe-3 d-flex align-items-center">${memberVO.member_Id}</p>
                                    			<div>
                                       				<p class="text-muted fw-light mb-1 small">${memberVO.member_Name}</p>
                                       				<span class="d-flex align-items-center small">${memberVO.member_Mbti}/${memberVO.member_Gender}/${memberVO.member_Email}</span>
                                    			</div>
                                 			</div>
                                 			<div class="ms-auto" id="followerBtnContainer">
                                    			<div class="btn-group" role="group" aria-label="Basic checkbox toggle button group">
													<c:if test="${memberVO.bothFollow eq 1}">
										  				<!-- 숫자 1과 동일한 경우에 실행될 내용 -->
										  				<input type="checkbox" class="btn-check" name="btncheckbox" id="btncheck2${memberVO.member_Id}" checked="checked">
													</c:if>
													<c:if test="${memberVO.bothFollow ne 1}">
										  				<!-- 숫자 1과 다른 경우에 실행될 내용 -->
										  				<input type="checkbox" class="btn-check" name="btncheckbox" id="btncheck2${memberVO.member_Id}">
													</c:if>
                                      		 		<label class="btn btn-outline-primary btn-sm px-3 rounded-pill" for="btncheck2${memberVO.member_Id}" onclick = "changeFollow('${memberVO.member_Id}')"><span class="following d-none">Following</span><span class="follow">+ Follow</span></label>
	                                			</div>
                                 			</div>
                              			</a>
                              		</c:forEach>
                              	</div>
                              	<div id="followerContainer"></div>
                           	</div>
                           	<div id="followerloadButton">
                              	<c:choose>
		              				<c:when test="${followerTotalPageNum<2}">		                         		
		                         	</c:when>
		                         	<c:otherwise>
			                     		<div class="ms-auto" align="center">
	                                 		<span class="btn btn-outline-primary btn-sm px-3 rounded-pill" id="followerload" onclick="followerload('${followerTotalPageNum}', '${followerPageNum}', '${member_Id}')">
	                                 			+ 더보기
	                                 		</span>
	                                	</div>
		                         	</c:otherwise>
		               			</c:choose>	
		               		</div>
		               	</div>
					</div><!-- class="main-content" -->                  
               	</main> <!-- index페이지의 센터 column -->               
               	<!-- index페이지 왼쪽 사이드바 column -->
               	<aside class="col col-xl-3 order-xl-1 col-lg-6 order-lg-2 col-md-6 col-sm-6 col-12">
                  	<div class="p-2 bg-light offcanvas offcanvas-start" tabindex="-1" id="offcanvasExample">
                     	<div class="sidebar-nav mb-3">
                        	<!-- 좌측 상단의 홈페이지 로고 -->
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
                              		<a href="follow?member_Id=${loginUser.member_Id}" class="nav-link active"><span class="material-icons me-3">diversity_3</span> <span>follow</span></a>
                           		</li>
                           		<!-- PAGES 드롭다운 항목 -->
                           		<li class="nav-item dropdown">
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
	                           	   	<a href="follow?member_Id=${loginUser.member_Id}" class="nav-link active"><span class="material-icons me-3">diversity_3</span> <span>follow</span></a>
	                           	</li>
	                           	<li class="nav-item dropdown">
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
     			</aside>
     		
	     		<!-- index페이지 오른쪽 사이드바 column -->
	       		<aside class="col col-xl-3 order-xl-3 col-lg-6 order-lg-3 col-md-6 col-sm-6 col-12">
	    			<div class="fix-sidebar">
	           			<div class="side-trend lg-none">
	            			<!-- Search Tab -->
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
							                			<div>
								               				<p class="fw-bold mb-0 pe-3 text-dark">${postVO.post_Like_Count}'s Likes</p>
								               				<small class="text-muted">Posted by ${postVO.member_Id}</small>
								               				<br><div style = "height : 5%;"></div>
											               	<small class="text-muted">
											               		<c:choose>
											               			<c:when test="${fn:length(postVO.post_Content) > 25 }">
											               				<c:out value = "${fn:substring(postVO.post_Content, 0, 25)}"/> . . .
											               			</c:when>
											               			<c:otherwise>
											               				${postVO.post_Content}
											               			</c:otherwise>
											               		</c:choose>
											               	</small>
								               				<br><div style = "height : 5%;"></div>
								               				<c:choose>
											                  	<c:when test="${postVO.post_Hashtag eq null }">
											                  	</c:when>
								                  				<c:otherwise>
								                     				<small class="text-muted">
													               		<c:choose>
													               			<c:when test="${fn:length(postVO.post_Hashtag) > 22 }">
													               				<c:out value = "${fn:substring(postVO.post_Hashtag, 0, 22)}"/> . . .
													               			</c:when>
													               			<c:otherwise>
													               				${postVO.post_Hashtag}
													               			</c:otherwise>
													               		</c:choose>
								                     				</small>									                  
								                  				</c:otherwise>
								               				</c:choose>
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
															<br><div style = "height : 5%;"></div>
															<small class="text-muted">
																<c:out value = "${fn:substring(postVO.post_Content, 0, 15)}"/> . . .
															</small>
															<br><div style = "height : 5%;"></div>
															<c:choose>
																<c:when test="${postVO.post_Hashtag eq null }">
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
				</aside>
            </div> <!-- class="row position-relative" -->
		</div> <!-- class="container" -->
	</div> <!-- class="py-4" --> 
      
	<!-- left footer -->
	<!-- 좌측 하단 부분 -->
	<div class="py-3 bg-white footer-copyright">
		<div class="container">
			<div class="row align-items-center">
    			<div class="col-md-8">
					<span class="me-3 small">©2023 <b class="text-primary">BlueLemon</b>. All rights reserved</span>
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
    <!-- infinite Js -->
    <script src="js/infinite.js"></script>
    <!-- Search Peple Js -->
    <script src="js/searchpeople.js"></script>
	<!-- Follow Js -->
	<script src="js/follow.js"></script>
</body>
</html>