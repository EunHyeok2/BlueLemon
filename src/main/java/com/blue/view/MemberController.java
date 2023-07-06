package com.blue.view;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blue.dto.AlarmVO;
import com.blue.dto.MemberVO;
import com.blue.dto.PostVO;
import com.blue.service.AlarmService;
import com.blue.service.EmailService;
import com.blue.service.MemberService;
import com.blue.service.PostService;

import Util.EmailVO;

@Controller
@SessionAttributes("loginUser")
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private PostService postService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private AlarmService alarmService;

	// 회원가입 화면 표시
	@RequestMapping("/join_view")
	public String joinView() {

		return "createAccount";
	}

	// 인증번호 및 패스워드 폼으로 이동
	@RequestMapping("/changePassword")
	public String changePasswordPage(HttpSession session, Model model) {

		int num = (Integer) session.getAttribute("num");

		model.addAttribute("num", num);

		return "changePassword";
	}

	// 아이디 패스워드 찾기 화면 표시
	@RequestMapping("/find_info")
	public String findpwview() {

		return "findidpw";
	}

	// 아이디 중복체크
	@PostMapping("/checkDuplicate")
	public ResponseEntity<String> checkDuplicate(@RequestParam("member_Id") String member_Id) {
		int result = memberService.confirmID(member_Id);
		if (result == 1) {
			return ResponseEntity.ok("duplicate");

		} else {
			return ResponseEntity.ok("not-duplicate");

		}
	}

	// 패스워드 일치 여부 확인
	@PostMapping("/checkPassword")
	public ResponseEntity<String> checkPassword(@RequestParam("member_Id") String member_Id,
			@RequestParam("member_Password") String member_Password) {
		// 패스워드 일치 여부를 확인하는 로직을 구현합니다.
		boolean isMatch = memberService.checkPassword(member_Id, member_Password);
		if (isMatch) {
			return ResponseEntity.ok("match"); // 패스워드가 일치하는 경우
		} else {
			return ResponseEntity.ok("not-match"); // 패스워드가 일치하지 않는 경우
		}
	}

	// 회원가입 처리
	@PostMapping("create_form")
	public String joinAction(MemberVO vo, @RequestParam(value = "profile_Image") MultipartFile profilePhoto,
			@RequestParam(value = "email_add") String email_add, HttpSession session) {
		if (!profilePhoto.isEmpty()) {
			// 프로필 사진을 저장할 경로를 결정합니다.
			String image_Path = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/profile/");
			// 저장할 파일명을 생성합니다. 파일명에는 member_Id와 확장자명을 포함합니다.
			String fileName = vo.getMember_Id() + ".png";
			// 파일을 지정된 경로에 저장합니다.

			try {
				profilePhoto.transferTo(new File(image_Path + fileName));
				// 저장된 파일의 경로를 MemberVO에 설정합니다.
				vo.setMember_Profile_Image(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		// 이미지 업로드 없을시 기본 이미지 사용
		} else {
			vo.setMember_Profile_Image("default.png");
		}

		if (email_add.equals(email_add)) {
			vo.setMember_Email(vo.getMember_Email() + "@" + email_add);
		} else {
		}

		memberService.insertMember(vo);

		return "login";
	}
	
	@GetMapping(value="/editProfile")
	public String editProfile(HttpSession session, Model model) {
		
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("message", "로그인을 해주세요");
			return "login";
		} else {
			
		List<PostVO> hottestFeed = postService.getHottestFeed();
		String session_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		String profileImage = memberService.getMemberInfo(session_Id).getMember_Profile_Image();

		// 알람 리스트를 담는 부분
    	List<AlarmVO> alarmList = alarmService.getAllAlarm(session_Id);
    	
    	int alarmListSize = alarmList.size();
    	
    	// 알람의 종류를 파악하는 부분
    	for(int j=0; j<alarmList.size(); j++) {
    		int kind = alarmList.get(j).getKind();
    		if(kind == 1) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "님께서 회원님을 팔로우 <br>하였습니다.");
    		} else if(kind == 2) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "님께서 회원님의 게시글에 <br>좋아요를 눌렀습니다.");
    		} else if(kind == 3) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "님께서 회원님의 게시글에 <br>댓글을 달았습니다.");
    		} else if(kind == 4) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "님께서 회원님의 댓글에 <br>좋아요를 눌렀습니다.");
    		} else if(kind == 5) {
    			alarmList.get(j).setMessage("회원님께서 문의하신 질문에 <br>답글이 달렸습니다.");
    		}
    	}
    	
    	MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
    	
    	String email = loginUser.getMember_Email();
		int atIndex = email.indexOf("@");
		String email_Id = email.substring(0, atIndex);
		String email_add = email.substring(atIndex + 1);
		
		model.addAttribute("profileImage", profileImage);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("member_Email", email_Id);
		model.addAttribute("email_add", email_add);
    	model.addAttribute("alarmList", alarmList);
		model.addAttribute("alarmListSize", alarmListSize);
		model.addAttribute("hottestFeed", hottestFeed);
		
		return "edit_profile";
		
		}
	}

	// 회원정보 수정
		@PostMapping("update_form")
		public String updateMember(MemberVO vo, HttpSession session, Model model,
				@RequestParam(value = "profile_Image") MultipartFile profilePhoto,
				@RequestParam(value = "email_add") String emailAdd) {
			
			// 새로운 프로필 사진을 저장합니다.
			if (!profilePhoto.isEmpty()) {
				
				// 기존 프로필 사진을 삭제합니다.
				String existingImagePath = "/WEB-INF/template/img/uploads/profile/"
						+ vo.getMember_Profile_Image();
				File existingImage = new File(existingImagePath);
				if (existingImage.exists()) {
					existingImage.delete();
				}
				
				String imagePath = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/profile/");
				String fileName = vo.getMember_Id() + ".png";
				try {
					profilePhoto.transferTo(new File(imagePath + fileName));
					vo.setMember_Profile_Image(fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// 이메일 주소를 설정합니다.
				String email = vo.getMember_Email() + "@" + emailAdd;
				vo.setMember_Email(email);
				
				memberService.updateMember(vo);
				
			} else {
				// 이메일 주소를 설정합니다.
				String email = vo.getMember_Email() + "@" + emailAdd;
				vo.setMember_Email(email);

				memberService.updateMember2(vo);
			}

			

			// 세션의 로그인 회원 정보를 업데이트합니다.
			session.setAttribute("loginUser", vo);

			// 수정된 회원 정보를 모델에 추가하여 JSP 페이지에서 사용할 수 있도록 함
			model.addAttribute("loginUser", vo);

			// 이메일 아이디와 이메일 주소를 분리하여 모델에 추가하여 JSP 페이지에서 사용할 수 있도록 함
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

			String email = loginUser.getMember_Email();
			int atIndex = email.indexOf("@");
			String email_Id = email.substring(0, atIndex);
			String email_add = email.substring(atIndex + 1);
			model.addAttribute("member_Email", email_Id);
			model.addAttribute("email_add", email_add);

			return "redirect:index";
		}


	// 회원 탈퇴 post
	@PostMapping(value = "/memberDelete")
	public String memberDelete(MemberVO vo, HttpSession session, RedirectAttributes rttr) {

		// 세션에 있는 member를 가져와 member변수에 넣어줍니다.
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		// 세션에있는 비밀번호
		String sessionPass = loginUser.getMember_Password();
		// System.out.println("DB 상 Pass = " + sessionPass);

		// 입력받아서 vo로 들어오는 비밀번호
		String voPass = vo.getMember_Password();

		if (!(sessionPass.equals(voPass))) {
			// alert 관련은 edit_profile.jsp 맨 위에 있음
			rttr.addFlashAttribute("msg", "wrong");
			return "redirect:edit_profile";
		} else {
			postService.deleteOneMemsTag(loginUser.getMember_Id());
			memberService.deleteMember(loginUser.getMember_Id());
			session.invalidate();
			rttr.addFlashAttribute("msg", "withdrawlSuccess");
			return "redirect:login";
		}
	}

	// 아이디 찾기
	@PostMapping("/memberSearch")
	@ResponseBody
	public ResponseEntity<String> userIdSearch(@RequestBody Map<String, String> requestBody) {
		String member_Name = requestBody.get("inputName_1");
		String member_Phone = requestBody.get("inputPhone_1");

		MemberVO vo = new MemberVO();
		vo.setMember_Name(member_Name);
		vo.setMember_Phone(member_Phone);

		String result = memberService.searchId(vo);

		return ResponseEntity.ok(result);
	}

	// 비밀번호 찾기 액션
	@PostMapping("pwdauth")
	@ResponseBody
	public Map<String, Object> pwdAuthAction(@RequestBody Map<String, String> requestBody, MemberVO vo,
			HttpSession session, Model model) {

		// ajax요청에 대한 response값 전달을 위한 Map 변수 선언
		Map<String, Object> dataMap = new HashMap<>();

		String member_Id = requestBody.get("inputId");
		String member_Email = requestBody.get("inputEmail_2");

		vo.setMember_Id(member_Id);
		vo.setMember_Email(member_Email);

		String pwd = memberService.selectPwdByIdNameEmail(vo);// 아이디와 이메일로 테이블에서 조회
		if (pwd != null) {
			Random r = new Random();
			int num = 100000 + r.nextInt(900000); // 랜덤 난수 설정
			session.setAttribute("email", vo.getMember_Email());
			session.setAttribute("Id", vo.getMember_Id());
			session.setAttribute("num", num);

			// 비밀번호 변경 이메일 보내기
			EmailVO emailVO = new EmailVO();
			emailVO.setReceiveMail(vo.getMember_Email());
			emailVO.setSubject("[blueLemon] 비밀번호 변경 인증 이메일입니다");
			String content = System.getProperty("line.separator") + "안녕하세요 회원님" + System.getProperty("line.separator")
					+ "blueLemon 비밀번호찾기(변경) 인증번호는 " + num + " 입니다." + System.getProperty("line.separator");
			emailVO.setMessage(content);

			System.out.println("<<<<<< 서버가 생성한 인증번호 : " + num + ">>>>>>");
			emailService.sendMail(emailVO);
			dataMap.put("message", 1);
			dataMap.put("num", num);
		} else {
			dataMap.put("message", -1);
		}

		System.out.println(dataMap.get("message"));
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		return dataMap;
	}

	// 패스워드 변경
	@PostMapping("/updatePassword")
	public String updatePassword(MemberVO vo, HttpSession session, SessionStatus status) {

		String member_id = (String) session.getAttribute("Id");

		vo.setMember_Id(member_id);

		// 회원 정보를 업데이트합니다.
		memberService.updatePassword(vo);

		status.setComplete();

		return "login";
	}

	// 회원 검색
	@GetMapping("/search_people")
	@ResponseBody
	public List<MemberVO> searchMembers(@RequestParam(value = "keyword") String keyword) {
		// Map<String, Object> retVal = new HashMap<String, Object>();
		if (keyword == null || keyword.trim().isEmpty()) {
			return Collections.emptyList();
		}

		// 검색어를 사용하여 멤버 아이디를 검색하고 결과를 반환합니다.
		List<MemberVO> searchResults = memberService.searchMembers(keyword);

		return searchResults;
	}
	
	// PEOPLE 탭 List 가져오기
	@PostMapping("/moreSerachPeopleList")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getSerachPeopleList(@RequestBody Map<String, String> requestbody, HttpSession session, Model model){

		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();

	    String hashTag = requestbody.get("hashTag");

	    List<MemberVO> searchFollow = memberService.searchMembers(hashTag);

	    int searchFollowSize = searchFollow.size();

	    Map<String, Object> responseData = new HashMap<>();

	    int totalPageNum = 0;

	    if(searchFollowSize % 5 != 0 && searchFollowSize > 5) {
	    	totalPageNum = searchFollowSize / 5 + 1;
		} else if(searchFollowSize % 5 != 0 && searchFollowSize < 5) {
			totalPageNum = 0;
		} else if(searchFollowSize % 5 == 0) {
			totalPageNum = searchFollowSize / 5;
		}

	    List<MemberVO> myFollowing = memberService.getFollowings(member_Id);

	    for(int i=0; i<myFollowing.size(); i++) {
			for(int j=0; j<searchFollow.size(); j++) {
				if(myFollowing.get(i).getMember_Id().equals(searchFollow.get(j).getMember_Id())) {
					searchFollow.get(j).setBothFollow(1);
				}
			}
		}

	    responseData.put("totalPageNum", totalPageNum);
	    responseData.put("searchFollow", searchFollow);
	    responseData.put("searchFollowSize", searchFollowSize);

	    return ResponseEntity.ok(responseData);
	}
}
