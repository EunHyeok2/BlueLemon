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
	  <!-- 파일 업로드 -->
	  <link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
	  <script src="https://code.jquery.com/jquery-latest.min.js"></script>
  	  <script src="https://code.jquery.com/ui/1.13.0/jquery-ui.min.js"></script>
  	  <!-- 해시태그  -->
  	  <script src="https://unpkg.com/@yaireo/tagify"></script>
	  <script src="https://unpkg.com/@yaireo/tagify/dist/tagify.polyfills.min.js"></script>
	  <link href="https://unpkg.com/@yaireo/tagify/dist/tagify.css" rel="stylesheet" type="text/css" />
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
                     <div class="bg-white rounded-4 shadow-sm profile">
                        <div class="d-flex align-items-center px-3 pt-3">
                           <img src="img/uploads/profile/${member.member_Profile_Image}" class="img-fluid rounded-circle" alt="profile-img">
                           <div class="ms-3">
                              <h6 class="mb-0 d-flex align-items-start text-body fs-6 fw-bold">${member.member_Id}</h6>
                              <p class="text-muted mb-0">${member.member_Name}</p>
                           </div>
                           <c:choose>
                              <c:when test="${member.member_Id == loginUser_Id}">                        
                              </c:when>
                              <c:otherwise>
		                           <div class="ms-auto btn-group" role="group" aria-label="Basic checkbox toggle button group">
		                              <c:choose>
		                                 <c:when test="${member.follow_Check eq 'n'}">
		                                    <input type="checkbox" class="btn-check" id="btncheck_${member.member_Id}">                              
			                                <label class="btn btn-outline-primary btn-sm px-3 rounded-pill" for="btncheck_${member.member_Id}" onclick ="changeFollow('${member.member_Id}')">
				                               <span class="follow">+ Follow</span>
				                               <span class="following d-none">Following</span>	                              
			                                </label>
		                                 </c:when>
		                                 <c:otherwise>
		                                    <input type="checkbox" class="btn-check" id="btncheck_${member.member_Id}" checked = "checked">                              
			                                <label class="btn btn-outline-primary btn-sm px-3 rounded-pill" for="btncheck_${member.member_Id}" onclick ="changeFollow('${member.member_Id}')">
				                               <span class="follow">+ Follow</span>
				                               <span class="following d-none">Following</span>	                              
			                                </label>
		                                 </c:otherwise>
		                              </c:choose>
		                           </div>                              
                              </c:otherwise>
                           </c:choose>                           
                        </div>
                        <div class="p-3" style = "margin-left : 70px;">
                           <p class="mb-2 fs-6">Birth Day : ${member.member_Birthday}</p>
                           <p class="mb-2 fs-6">Gender : ${member.member_Gender}</p>
                           <c:choose>
                              <c:when test="${empty member.member_Country}">
                              </c:when>
                              <c:otherwise>
                                 <p class="mb-2 fs-6">Country : ${member.member_Country}</p>
                              </c:otherwise>
                           </c:choose>
                           <c:choose>
                              <c:when test="${empty member.member_Mbti}">
                              </c:when>
                              <c:otherwise>
                                 <p class="mb-2 fs-6">MBTI : ${member.member_Mbti}</p>
                              </c:otherwise>
                           </c:choose>
                           <div style = "width : 500px;">
                           	  <hr>
                           </div>
                           <div class="d-flex followers" style = "margin-top : 10px;">                           	  
                              <div>
                                 <c:choose>
                                 	<c:when test = "${empty followers_Size}">
                                 		<p class="mb-0">0 <span class="text-muted">Followers</span></p>
                                 	</c:when>
                                 	<c:otherwise>
                                 		<p class="mb-0">${followers_Size} <span class="text-muted">Followers</span></p>
                                 	</c:otherwise>
                                 </c:choose>                                 
                                 <div class="d-flex">
                                 	<a href = "follow?member_Id=${member.member_Id}">
	                                 	<c:choose>
	                                 		<c:when test = "${followers_Size < 5}">
			                                 	<c:forEach items = "${followers}" var = "memberVO" begin = "0" end = "${followers_Size}">
			                                 		<img src="img/uploads/profile/${memberVO.member_Profile_Image}" class="img-fluid rounded-circle" alt="follower-img">
			                                 	</c:forEach>
	                                 		</c:when>
	                                 		<c:otherwise>
			                                 	<c:forEach items = "${followers}" var = "memberVO" begin = "0" end = "5">
			                                 		<img src="img/uploads/profile/${memberVO.member_Profile_Image}" class="img-fluid rounded-circle" alt="follower-img">
			                                 	</c:forEach>
	                                 		</c:otherwise>
	                                 	</c:choose>
                                 	</a>
                                 </div>
                              </div>
                              <div class="ms-5 ps-5">                              	
                              	 <c:choose>
                                 	<c:when test = "${empty followings_Size}">
                                 		<p class="mb-0">0 <span class="text-muted">Following</span></p>
                                 	</c:when>
                                 	<c:otherwise>
                                 		<p class="mb-0">${followings_Size} <span class="text-muted">Following</span></p>
                                 	</c:otherwise>
                                 </c:choose>
                                 <div class="d-flex">
                                 	<a href = "follow?member_Id=${member.member_Id}">
	                                 	<c:choose>
	                                 		<c:when test = "${followings_Size < 5}">
			                                 	<c:forEach items = "${followings}" var = "memberVO" begin = "0" end = "${followings_Size}">
			                                 		<img src="img/uploads/profile/${memberVO.member_Profile_Image}" class="img-fluid rounded-circle" alt="follower-img">
			                                 	</c:forEach>
	                                 		</c:when>
	                                 		<c:otherwise>
			                                 	<c:forEach items = "${followings}" var = "memberVO" begin = "0" end = "5">
			                                 		<img src="img/uploads/profile/${memberVO.member_Profile_Image}" class="img-fluid rounded-circle" alt="follower-img">
			                                 	</c:forEach>
	                                 		</c:otherwise>
	                                 	</c:choose>
                                 	</a>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                     <div class="tab-content" id="pills-tabContent" style = "margin-top : 20px;">
                        <div class="tab-pane fade show active" id="pills-feed" role="tabpanel" aria-labelledby="pills-feed-tab">
                           <div class="ms-1">
                              <div class="feeds">
                                 <!-- Feed Item -->
                                 <c:forEach var="postVO" items="${postlist}" varStatus="status" begin="0" end="9">
	                                 <div class="bg-white p-3 feed-item rounded-4 mb-3 shadow-sm">
	                                    <div class="d-flex">
	                                       <!-- 작성자의 프로필사진 -->
	                                       <img src="img/uploads/profile/${profileMap[postVO.member_Id]}"  class="img-fluid rounded-circle user-img" alt="profile-img">
	                                       <div class="d-flex ms-3 align-items-start w-100">
	                                          <div class="w-100">
	                                             <div class="d-flex align-items-center justify-content-between">
	                                                <a href = "profile?member_Id=${postVO.member_Id}" class="text-decoration-none" >
	                                                   <h6 class="fw-bold mb-0 text-body" style="font-size: 26px;">${postVO.member_Id}</h6>
	                                                </a>
	                                                <div class="d-flex align-items-center small">
	                                                   <p class="text-muted mb-0">${postVO.post_Date}</p>
	                                                   <c:choose>
	                                                      <c:when test="${loginUser_Id == postVO.member_Id}">	                                                      
		                                                     <div class="dropdown">
		                                                        <a href="#" class="text-muted text-decoration-none material-icons ms-2 md-20 rounded-circle bg-light p-1" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">more_vert</a>
		                                                        <ul class="dropdown-menu fs-13 dropdown-menu-end" aria-labelledby="dropdownMenuButton1">
		                                                         <li><button class="dropdown-item text-muted editbutton" onclick="postEditView(${postVO.post_Seq})" data-bs-toggle="modal" data-bs-target="#postModal2"><span class="material-icons md-13 me-1">edit</span>Edit</button></li>
		                                                         <li><button class="dropdown-item text-muted deletebutton" onclick="deletePost(${postVO.post_Seq})"><span class="material-icons md-13 me-1">delete</span>Delete</button></li>
		                                                        </ul>
		                                                     </div>
	                                                      </c:when>
	                                                      <c:otherwise>	                                                      
	                                                      </c:otherwise>
	                                                   </c:choose>
	                                                </div>
	                                             </div>
	                                             <div class="my-2">
	                                                <!-- 게시글의 사진 (클릭시 게시글 상세보기 모달창 출력) -->
	                                                <a id="openModalBtn" class="text-decoration-none" data-bs-toggle="modal" data-bs-target="#commentModal" onclick="modalseq(${postVO.post_Seq})">
		                                                <c:choose>
		                                                	<c:when test="${postVO.post_Image_Count == 0}">
		                                                		<br>
		                                                	</c:when>
		                                                	<c:otherwise>
		                                                		<img src="img/uploads/post/${postVO.post_Seq}-1.png" class="img-fluid rounded mb-3" alt="post-img">
		                                                	</c:otherwise>
		                                                </c:choose>
	                                                </a>
	                                                <br>      
                                             	<!-- 게시글 내용 -->                                        
                                                <p class="text-dark">${postVO.post_Content}</p>
                                                <br>
	                                                
	                                                <!-- 해시태그 -->
	                                                <c:forEach var="hash" items="${hashMap[postVO.post_Seq]}">
	                                                	<a id="hash" href="search_HashTag?tag_Content=${hash.tag_Content}" class="mb-3 text-primary">#${hash.tag_Content}</a>&nbsp;&nbsp;
	                                                </c:forEach>
	                                                <hr>
	                                                <!-- 게시글 바로 아래 좋아요, 댓글 버튼 부분 -->
	                                                <div class="d-flex align-items-center justify-content-between mb-2">
	                                                    <%-- 게시글 좋아요 버튼 (카운트) --%>
	                                                    <div class="like-group" role="group">
														    <c:choose>
														        <c:when test="${postVO.post_LikeYN eq 'N'}">
														            <button type="button" style = "border : none; background-color : white;" onclick="toggleLike('${postVO.post_Seq}')">
														                <img class="likeImage_${postVO.post_Seq}" src="img/like.png" width="20px" height="20px" data-liked = "false">
														            </button>
														            <p class ="post_Like_Count_${postVO.post_Seq}" style="display: inline; margin-left: 3px; font-size : 13px;">${postVO.post_Like_Count}</p>
														        </c:when>
														        <c:otherwise>
														            <button type="button" style = "border : none; background-color : white;" onclick="toggleLike('${postVO.post_Seq}')">
														                <img class="likeImage_${postVO.post_Seq}" src="img/unlike.png" width="20px" height="20px" data-liked = "true">
														            </button>
														            <p class ="post_Like_Count_${postVO.post_Seq}" style="display: inline; margin-left: 3px; font-size : 13px;">${postVO.post_Like_Count}</p>
														        </c:otherwise>
														    </c:choose>
														</div>
														
								                       <div>
	                                                   		<%-- 댓글 버튼 + 카운트 --%>
	      					                              	<div class="text-muted text-decoration-none d-flex align-items-start fw-light"><span class="material-icons md-20 me-2">chat_bubble_outline</span><span>${postVO.post_Reply_Count}</span></div>
	                                                    </div>
	                                                </div>
	                                                 
	                                                <!-- 댓글 입력창 부분 (클릭시 모달창) -->
	                                                <div class="d-flex align-items-center mb-3" data-bs-toggle="modal" data-bs-target="#commentModal2" onclick="replyModalseq(${postVO.post_Seq})">
	                                                   <span class="material-icons bg-white border-0 text-primary pe-2 md-36">account_circle</span>
	                                                   <input type="text" class="form-control form-control-sm rounded-3 fw-light" placeholder="Write Your comment">
	                                                </div>
	                                                
	                                                <!-- 댓글 출력 부분 -->
	                                                <div class="comments">
		                                                <c:set var="key" value="${status.index}"/>
		                                                <c:set var="value" value="${replyMap[key]}"/>
			                                            <c:forEach var="reply" items="${value}" begin="0" end="2">
	                                                 		<div class="d-flex mb-2">
	                                                 			<!-- 댓글 작성자 프로필 이미지 출력부분 -->
			                                                    <a href="#" class="text-dark text-decoration-none" data-bs-toggle="modal" data-bs-target="#commentModal2" onclick="replyModalseq(${reply.post_Seq})">
			                                                   		<img src="img/uploads/profile/${profileMap[reply.member_Id]}" class="img-fluid rounded-circle profile" alt="commenters-img">
			                                                    </a>
		                                                     	<div class="ms-2 small">
		                                                        	<a href="#" class="text-dark text-decoration-none" data-bs-toggle="modal" data-bs-target="#commentModal2" onclick="replyModalseq(${reply.post_Seq});">
			                                                        	<div class="bg-light px-3 py-2 rounded-4 mb-1 chat-text" style="display: inline-block;">
			                                                        		<p class="fw-500 mb-0">${reply.member_Id}</p>
			                                                            	<span class="text-muted">${reply.reply_Content}</span>
			                                                           	</div>
			                                                        </a>
			                                                           	<div class="reply-like-group" role="group" style="display: inline-block;">
			                                                        		<c:set var = "replySeq" value = "${reply.reply_Seq}"/>
			                                                            	<script>
			                                                            		var reply_Seq = ${replySeq};
			                                                            	</script>
																		    <c:choose>
																		        <c:when test="${reply.reply_LikeYN eq 'N'}">
																		            <button type="button" style = "border : none; background-color : white;" onclick="toggleReplyLike('${reply.post_Seq}', '${reply.reply_Seq}');">
																		                <img class="likeReplyImage_${reply.reply_Seq}" src="img/like.png" data-liked = "false">
																		            </button>
																		            <p class ="reply_Like_Count_${reply.reply_Seq}" style="display: inline; margin-left: 1px; font-size : 10px;">${reply.reply_Like_Count}</p>
																		        </c:when>
																		        <c:otherwise>
																		            <button type="button" style = "border : none; background-color : white;" onclick="toggleReplyLike('${reply.post_Seq}', '${reply.reply_Seq}');">
																		                <img class="likeReplyImage_${reply.reply_Seq}" src="img/unlike.png" data-liked = "true">
																		            </button>
																		            <p class ="reply_Like_Count_${reply.reply_Seq}" style="display: inline; margin-left: 1px; font-size : 10px;">${reply.reply_Like_Count}</p>
																		        </c:otherwise>
																		    </c:choose>
																		</div>
																		<div  style="display: inline-block;" width = "5px"></div>
			                                                            <span class="small text-muted">${reply.reply_WhenDid}</span>
		                                                        	</a>
		                                                         	<div class="d-flex align-items-center justify-content-between mb-2">
			                                                    	</div>
			                                                	</div>
	                                                   		</div>
		                                            	</c:forEach> <!-- 실질적인 댓글 추출 하는 반복문 -->
	                                                </div>
	                                             </div>
	                                          </div>
	                                       </div>
	                                    </div>
	                                 </div>
                                 </c:forEach> <!-- 게시글 출력 반복문 -->    
                                 <input type="hidden" value="${member_Id}" id="member_Id">
                                 <div id="profileFeed">
                                 </div>   
                              </div>
                           </div>
                        </div>
                        <div class="tab-pane fade" id="pills-people" role="tabpanel" aria-labelledby="pills-people-tab">
                           <!-- Feeds -->
                           <div class="feeds">
                              <!-- Feed Item -->
                              <div class="bg-white p-3 feed-item rounded-4 mb-3 shadow-sm">
                                 
                              </div>
                              <!-- Feed Item -->
                              
                        <div class="tab-pane fade" id="pills-trending" role="tabpanel" aria-labelledby="pills-trending-tab">
                           <!-- Feeds -->
                           <div class="feeds">
                              <!-- Feed Item -->
                              <div class="bg-white p-3 feed-item rounded-4 mb-3 shadow-sm">
                                 
                              </div>
                           </div>
                        </div>
                        <div class="tab-pane fade" id="pills-mentions" role="tabpanel" aria-labelledby="pills-mentions-tab">
                           <!-- Feeds -->
                           <div class="feeds">
                              <!-- Feed Item -->
                              <div class="bg-white p-3 feed-item rounded-4 mb-3 shadow-sm">
                                 
                              </div>
                              <!-- Feed Item -->
                              <div class="bg-white p-3 feed-item rounded-4 mb-3 shadow-sm">
                                  
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
                  
                  <div id="profileLoadingStop"></div>
                  <c:choose>
                  	<c:when test="${fn:length(postlist)<10}">
	                  	<div id="profileFeedEnd">
    	              		<br>
        	          			<h5 align="center">No Post To Show</h5>
            	      		<br>
            	      	</div>
                  	</c:when>
                  	<c:otherwise>
	                  	<div id="profileFeedInfinity">
							<div class="text-center mt-4">
							   <div class="spinner-border" role="status">
							      <span class="visually-hidden">Loading...</span>
							   </div>
							   <p class="mb-0 mt-2">Loading</p>
							</div>
						</div>
                  	</c:otherwise>
                  </c:choose>
                  
                  
               </main>
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
				           <c:choose>
						      <c:when test="${loginUser.member_Id == member.member_Id}">
						         <li class="nav-item">
                              	    <a href = "profile?member_Id=${sessionScope.loginUser.member_Id}" class="nav-link active"><img src = "img/uploads/profile/${loginUser.member_Profile_Image}" style = "width : 27px; height : 27px; border-radius : 50%; overfloiw : hidden;"> <span>&nbsp;&nbsp;&nbsp;${loginUser.member_Id}'s PROFILE</span></a>
			                     </li>
						      </c:when>
						      <c:otherwise>
						       	 <li class="nav-item">
                              	    <a href = "profile?member_Id=${sessionScope.loginUser.member_Id}" class="nav-link"><img src = "img/uploads/profile/${loginUser.member_Profile_Image}" style = "width : 27px; height : 27px; border-radius : 50%; overfloiw : hidden;"> <span>&nbsp;&nbsp;&nbsp;${loginUser.member_Id}'s PROFILE</span></a>
			                     </li>
						      </c:otherwise>
						   </c:choose>      
						   <c:choose>
						      <c:when test="${loginUser.member_Id == member.member_Id}">
						         <li class="nav-item">
                              	    <a href="/blue/editProfile" class="nav-link active"><span class="material-icons me-3">edit</span> <span>Edit Profile</span></a>
                           	     </li>
						      </c:when>
						      <c:otherwise>
						      </c:otherwise>
						   </c:choose>                                
                           <li class="nav-item">
                              <a href="follow?member_Id=${loginUser.member_Id}" class="nav-link"><span class="material-icons me-3">diversity_3</span> <span>follow</span></a>
                           </li>
                           <!-- Contact Us 드롭다운 항목 -->
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
                           <c:choose>
						        <c:when test="${loginUser.member_Id == member.member_Id}">
						            <li class="nav-item">
                              			<a href = "profile?member_Id=${sessionScope.loginUser.member_Id}" class="nav-link active"><img src = "img/uploads/profile/${loginUser.member_Profile_Image}" style = "width : 27px; height : 27px; border-radius : 50%; overfloiw : hidden;"> <span>&nbsp;&nbsp;&nbsp;${loginUser.member_Id}'s PROFILE</span></a>
			                        </li>
						        </c:when>
						        <c:otherwise>
						        	<li class="nav-item">
                              			<a href = "profile?member_Id=${sessionScope.loginUser.member_Id}" class="nav-link"><img src = "img/uploads/profile/${loginUser.member_Profile_Image}" style = "width : 27px; height : 27px; border-radius : 50%; overfloiw : hidden;"> <span>&nbsp;&nbsp;&nbsp;${loginUser.member_Id}'s PROFILE</span></a>
			                        </li>
						        </c:otherwise>
						   </c:choose>                           <c:choose>
						        <c:when test="${loginUser.member_Id == member.member_Id}">
						            <li class="nav-item">
			                           <a href="/blue/editProfile" class="nav-link"><span class="material-icons me-3">edit</span> <span>Edit Profile</span></a>
			                        </li>
						        </c:when>
						        <c:otherwise>
						        </c:otherwise>
						   </c:choose>            
                           <li class="nav-item">
                              <a href="follow?member_Id=${loginUser.member_Id}" class="nav-link"><span class="material-icons me-3">diversity_3</span> <span>follow</span></a>
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
      <!-- Insert Js -->
      <script src="js/modalAction.js"></script>
      <!-- Trending Js -->
      <script src="js/trending.js"></script>
      <!-- Infinite Js -->
      <script src="js/infinite.js"></script>
      <!-- Search Peple Js -->
      <script src="js/searchpeople.js"></script>
   </body>
</html>