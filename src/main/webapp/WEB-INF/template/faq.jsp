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
							<div class="feature bg-primary bg-gradient text-white rounded-4 mb-3"><i class="mdi mdi-help"></i></div>
							<h2 class="fw-bold text-black mb-1">Frequently Asked Questions</h2>
	                     	<p class="lead fw-normal text-muted mb-0">How can we help you?</p>
	                  	</div>
	                  	<!-- Feeds -->
	                  	<div class="feeds">
	                     	<!-- Feed Item -->
	                     	<div class="bg-white p-4 feed-item rounded-4 shadow-sm faq-page">
	                        	<!-- Contact form-->
	                        	<div class="rounded-3">
	                           		<div class="mb-3">
	                              		<h5 class="lead fw-bold text-body mb-0">Basics</h5>
	                           		</div>
	                           	<div class="row justify-content-center">
	                              	<div class="col-lg-12">
	                                 	<!-- FAQ Accordion 1-->
	                                 	<div class="accordion overflow-hidden bg-white" id="accordionExample">
	                                    	<div class="accordion-item">
	                                       		<h3 class="accordion-header" id="headingOne"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">프로필 변경 방법</button></h3>
	                                       		<div class="accordion-collapse collapse" id="collapseOne" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
	                                          		<div class="accordion-body">
	                                             		<p class="m-0">프로필의 변경은 로그인 후 좌측 사이드메뉴에서 Profile을 들어가신 후<br> Edit-Profile을 통해 변경할 수 있습니다.
	                                             		</p>
	                                         	 	</div>
	                                       		</div>
	                                    	</div>
	                                    	<div class="accordion-item">
	                                       		<h3 class="accordion-header" id="headingTwo"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">프로필 사진 업로드 방법</button></h3>
	                                       		<div class="accordion-collapse collapse" id="collapseTwo" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
	                                          		<div class="accordion-body">
	                                          	  		프로필의 변경은 로그인 후 좌측 사이드메뉴에서 Profile을 들어가신 후<br> Edit-Profile을 통해 변경할 수 있습니다.
	                                             		<strong>프로필 이미지는 PNG 확장자만 사용 가능합니다.</strong><br>
	                                             		폭력적이거나 선정적인 이미지는 제거되거나 계정 사용이 정지될 수 있습니다.
	                                          		</div>
	                                       		</div>
	                                    	</div>
	                                    	<div class="accordion-item">
	                                       		<h3 class="accordion-header" id="headingThree"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">게시글 작성 방법</button></h3>
	                                       		<div class="accordion-collapse collapse" id="collapseThree" aria-labelledby="headingThree" data-bs-parent="#accordionExample">
	                                          		<div class="accordion-body">
	                                             		메인 피드의 상단에 있는 텍스트창을 눌러 작성하실 수 있습니다.
	                                          		</div>
	                                       		</div>
	                                    	</div>
	                                    	<div class="accordion-item">
	                                       		<h3 class="accordion-header" id="headingFour"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">댓글 작성 방법</button></h3>
	                                       		<div class="accordion-collapse collapse" id="collapseFour" aria-labelledby="headingFour" data-bs-parent="#accordionExample">
	                                          		<div class="accordion-body">
	                                             		댓글을 게시하고 싶은 게시물을 눌러 들어간 다음<br> 하단의 입력창을 이용하여 댓글을 작성하실 수 있습니다.
	                                          		</div>
	                                       		</div>
	                                    	</div>
	                                    	<div class="accordion-item">
	                                       		<h3 class="accordion-header" id="headingFive"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">팔로우 방법</button></h3>
	                                       		<div class="accordion-collapse collapse" id="collapseFive" aria-labelledby="headingFive" data-bs-parent="#accordionExample">
	                                          		<div class="accordion-body">
	                                             		팔로우하고 싶은 상대방을 눌러 프로필로 들어간 다음 팔로우를 눌러서 팔로우할 수 있습니다.
	                                          		</div>
	                                       		</div>
	                                    	</div>
	                                    	<div class="accordion-item">
	                                       		<h3 class="accordion-header" id="headingSix"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSix" aria-expanded="false" aria-controls="collapseSix">로그 아웃 방법</button></h3>
	                                       		<div class="accordion-collapse collapse" id="collapseSix" aria-labelledby="headingSix" data-bs-parent="#accordionExample">
	                                          		<div class="accordion-body">
	                                             		좌측 사이드 메뉴의 로그아웃 버튼을 눌러 로그아웃하실 수 있습니다.
	                                          		</div>
	                                       		</div>
	                                    	</div>
	                                 	</div>
	                              	</div>
	                           	</div>
	                           	<div class="mb-3 mt-4 pt-2">
	                              	<h5 class="lead fw-bold text-body mb-0">Account</h5>
	                           	</div>
	                           	<div class="row justify-content-center">
	                              	<div class="col-lg-12">
	                                 	<!-- FAQ Accordion 1-->
	                                 	<div class="accordion overflow-hidden bg-white" id="accordionExample">
	                                    	<div class="accordion-item">
		                                       	<h3 class="accordion-header" id="headingTwo4"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo4" aria-expanded="false" aria-controls="collapseTwo4">아이디 찾기</button></h3>
		                                       	<div class="accordion-collapse collapse" id="collapseTwo4" aria-labelledby="headingTwo4" data-bs-parent="#accordionExample">
	                                          		<div class="accordion-body">
	                                             		로그인 메뉴의 하단에 있는 아이디/비밀번호 찾기를 이용하여 본인 확인 이후<br> 아이디를 확인하실 수 있습니다.
	                                          		</div>
	                                       		</div>
	                                    	</div>
	                                    	<div class="accordion-item">
	                                       		<h3 class="accordion-header" id="headingOne1"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne1" aria-expanded="false" aria-controls="collapseOne1">비밀번호 찾기</button></h3>
	                                       		<div class="accordion-collapse collapse" id="collapseOne1" aria-labelledby="headingOne1" data-bs-parent="#accordionExample">
	                                          		<div class="accordion-body">
		                                             	로그인 메뉴의 하단에 있는 아이디/비밀번호 찾기를 이용하여 본인 확인 이후<br> 비밀번호의 변경이 가능합니다.
		                                             	이전부터 쓰고 있던 비밀번호는 확인이 불가합니다.
	                                          		</div>
	                                       		</div>
	                                    	</div>
	                                    	<div class="accordion-item">
	                                       		<h3 class="accordion-header" id="headingTwo2"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo2" aria-expanded="false" aria-controls="collapseTwo2">회원탈퇴 방법</button></h3>
	                                       		<div class="accordion-collapse collapse" id="collapseTwo2" aria-labelledby="headingTwo2" data-bs-parent="#accordionExample">
		                                          	<div class="accordion-body">
		                                             	프로필 수정 페이지의 맨 하단에 있는 회원 탈퇴 페이지에서 탈퇴가 가능합니다.
		                                          	</div>
	                                       		</div>
	                                    	</div>
	                                    	<div class="accordion-item">
	                                     		<h3 class="accordion-header" id="headingThree3"><button class="accordion-button collapsed fw-bold" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree3" aria-expanded="false" aria-controls="collapseThree3">일대일 문의 방법</button></h3>
	                                       		<div class="accordion-collapse collapse" id="collapseThree3" aria-labelledby="headingThree3" data-bs-parent="#accordionExample">
		                                          	<div class="accordion-body">
		                                             	좌측 사이드메뉴의 Contact Us 메뉴의 Contact에서 문의가 가능합니다.
		                                          	</div>
	                                       		</div>
											</div>
										</div>
									</div>
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
								<a href = "profile?member_Id=${sessionScope.loginUser.member_Id}" class="nav-link"><img src = "img/uploads/profile/${profileImage}" style = "width : 27px; height : 27px; border-radius : 50%; overfloiw : hidden;"> <span>&nbsp;&nbsp;&nbsp;${loginUser.member_Id}'s PROFILE</span></a>
                            </li>
                            <li class="nav-item">
								<a href="follow?member_Id=${loginUser.member_Id}" class="nav-link"><span class="material-icons me-3">diversity_3</span> <span>follow</span></a>
                            </li>
                            <!-- PAGES 드롭다운 항목 -->
                            <li class="nav-item dropdown">
								<a class="nav-link dropdown-toggle active" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
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
                              	<a href = "profile?member_Id=${sessionScope.loginUser.member_Id}" class="nav-link"><img src = "img/uploads/profile/${profileImage}" style = "width : 27px; height : 27px; border-radius : 50%; overfloiw : hidden;"> <span>&nbsp;&nbsp;&nbsp;${loginUser.member_Id}'s PROFILE</span></a>
                            </li>
                            <li class="nav-item">
                               	<a href="follow?member_Id=${loginUser.member_Id}" class="nav-link"><span class="material-icons me-3">diversity_3</span> <span>follow</span></a>
                            </li>
                            <li class="nav-item dropdown">
                               	<a class="nav-link dropdown-toggle active" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
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
				</div>
			</aside>
		</div>
	</div> <!-- py-4 -->
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
      
	
	<!-- Jquery Js -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<!-- Bootstrap Bundle Js -->
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- Custom Js -->
	<script src="js/custom.js"></script>
	<!-- Slick Js -->
	<script src="vendor/slick/slick/slick.min.js"></script>
	<!-- Search Peple Js -->
	<script src="js/searchpeople.js"></script>
</body>
</html>