package com.blue.view;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
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

	// ȸ������ ȭ�� ǥ��
	@RequestMapping("/join_view")
	public String joinView() {

		return "createAccount";
	}

	// ������ȣ �� �н����� ������ �̵�
	@RequestMapping("/changePassword")
	public String changePasswordPage(HttpSession session, Model model) {

		int num = (Integer) session.getAttribute("num");

		model.addAttribute("num", num);

		return "changePassword";
	}

	// ���̵� �н����� ã�� ȭ�� ǥ��
	@RequestMapping("/find_info")
	public String findpwview() {

		return "findidpw";
	}

	// ���̵� �ߺ�üũ
	@PostMapping("/checkDuplicate")
	public ResponseEntity<String> checkDuplicate(@RequestParam("member_Id") String member_Id) {
		int result = memberService.confirmID(member_Id);
		if (result == 1) {
			return ResponseEntity.ok("duplicate");

		} else {
			return ResponseEntity.ok("not-duplicate");

		}
	}

	// �н����� ��ġ ���� Ȯ��
	@PostMapping("/checkPassword")
	public ResponseEntity<String> checkPassword(@RequestParam("member_Id") String member_Id,
			@RequestParam("member_Password") String member_Password) {
		// �н����� ��ġ ���θ� Ȯ���ϴ� ������ �����մϴ�.
		boolean isMatch = memberService.checkPassword(member_Id, member_Password);
		if (isMatch) {
			return ResponseEntity.ok("match"); // �н����尡 ��ġ�ϴ� ���
		} else {
			return ResponseEntity.ok("not-match"); // �н����尡 ��ġ���� �ʴ� ���
		}
	}

	// ȸ������ ó��
	@PostMapping("create_form")
	public String joinAction(MemberVO vo, @RequestParam(value = "profile_Image") MultipartFile profilePhoto,
			@RequestParam(value = "email_add") String email_add, HttpSession session) {
		if (!profilePhoto.isEmpty()) {
			// ������ ������ ������ ��θ� �����մϴ�.
			String image_Path = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/profile/");
			// ������ ���ϸ��� �����մϴ�. ���ϸ��� member_Id�� Ȯ���ڸ��� �����մϴ�.
			String fileName = vo.getMember_Id() + ".png";
			// ������ ������ ��ο� �����մϴ�.

			try {
				profilePhoto.transferTo(new File(image_Path + fileName));
				// ����� ������ ��θ� MemberVO�� �����մϴ�.
				vo.setMember_Profile_Image(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		// �̹��� ���ε� ������ �⺻ �̹��� ���
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
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
			List<PostVO> hottestFeed = postService.getHottestFeed();
			String session_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
			
			String profileImage = memberService.getMemberInfo(session_Id).getMember_Profile_Image();
			// �˶� ����Ʈ�� ��� �κ�
	    	List<AlarmVO> alarmList = alarmService.getAllAlarm(session_Id);
	    	int alarmListSize = alarmList.size();
	    	
	    	// �˶��� ������ �ľ��ϴ� �κ�
	    	for(int j=0; j<alarmList.size(); j++) {
	    		int kind = alarmList.get(j).getKind();
	    		if(kind == 1) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �ȷο� <br>�Ͽ����ϴ�.");
	    		} else if(kind == 2) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>���ƿ並 �������ϴ�.");
	    		} else if(kind == 3) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>����� �޾ҽ��ϴ�.");
	    		} else if(kind == 4) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ ��ۿ� <br>���ƿ並 �������ϴ�.");
	    		} else if(kind == 5) {
	    			alarmList.get(j).setMessage("ȸ���Բ��� �����Ͻ� ������ <br>����� �޷Ƚ��ϴ�.");
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

	// ȸ������ ����
	@PostMapping("update_form")
	public String updateMember(MemberVO vo, HttpSession session, Model model,
			@RequestParam(value = "profile_Image") MultipartFile profilePhoto,
			@RequestParam(value = "email_add") String emailAdd) {
		
		// ���ο� ������ ������ �����մϴ�.
		if (!profilePhoto.isEmpty()) {
			
			// ���� ������ ������ �����մϴ�.
			String existingImagePath = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/profile/")
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
		}
		
		// �̸��� �ּҸ� �����մϴ�.
		String email = vo.getMember_Email() + "@" + emailAdd;
		vo.setMember_Email(email);
		
		memberService.updateMember2(vo);

		MemberVO refreshUser = new MemberVO();
		refreshUser = memberService.getMemberInfo(vo.getMember_Id());
		
		// ������ �α��� ȸ�� ������ ������Ʈ�մϴ�.
		session.setAttribute("loginUser", refreshUser);

		// ������ ȸ�� ������ �𵨿� �߰��Ͽ� JSP ���������� ����� �� �ֵ��� ��
		model.addAttribute("loginUser", refreshUser);

		// �̸��� ���̵�� �̸��� �ּҸ� �и��Ͽ� �𵨿� �߰��Ͽ� JSP ���������� ����� �� �ֵ��� ��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		int atIndex = email.indexOf("@");
		String email_Id = email.substring(0, atIndex);
		String email_add = email.substring(atIndex + 1);
		model.addAttribute("member_Email", email_Id);
		model.addAttribute("email_add", email_add);

		return "redirect:index";
	}

	// ȸ�� Ż�� post
	@PostMapping(value = "/memberDelete")
	public String memberDelete(MemberVO vo, HttpSession session, RedirectAttributes rttr) {

		// ��й�ȣ �˻�
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		String sessionPass = loginUser.getMember_Password();
		String voPass = vo.getMember_Password();
		
		// 1. �Խñ� �̹��� ������ ���� ���
		String postFolderPath = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/post/");
		File postFolder = new File(postFolderPath);
		File[] postFiles = postFolder.listFiles();
		
		// 1-1. ����ڰ� �ۼ��� �Խñ۵��� ���������� ������ ����
		List<Integer> memSeq = postService.seqForUser(loginUser.getMember_Id());
		
		// 2. ������ �̹��� ������ ���� ���
		String profileFolderPath = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/profile/");
		File profileFolder = new File(profileFolderPath);
		File[] profileFiles = profileFolder.listFiles();
		
		if (!(sessionPass.equals(voPass))) { // ��й�ȣ�� ��ġ���� ������
			rttr.addFlashAttribute("msg", "wrong");
			return "redirect:edit_profile";
			
		} else { // ��й�ȣ�� ��ġ�ϸ�
			postService.deleteOneMemsTag(loginUser.getMember_Id());
			memberService.deleteMember(loginUser.getMember_Id());
			
			// 1-2. ����ڰ� ���ε��� �Խñ� �̹������� ����
			if (postFiles != null) {
				for(int Seq : memSeq) {
				    for (File file : postFiles) {
				        String fileName = file.getPath().substring(file.getPath().lastIndexOf('\\') + 1);
				        String[] parts = fileName.split("-");
				        if (parts.length >= 2 && parts[0].equals(String.valueOf(Seq))) {
				            if (file.delete()) {
				            } else {
				            }
				        }
				    }
				}
		    }
			// 2-1. ����ڰ� ���ε��� ������ �̹������� ����
			if (profileFiles != null) {
			    for (File file : profileFiles) {
			        String fileName = file.getName();
			        if (fileName.equals(loginUser.getMember_Id())) {
			            if (file.delete()) {
			            	System.out.println("������ ����");
			            } else {
			            	System.out.println("������ ���� ����");
			            }
			        }
			    }
			}
			session.invalidate();
			rttr.addFlashAttribute("msg", "withdrawlSuccess");
			return "redirect:login";
		}
	}

	
	
	
	// ���̵� ã��
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

	// ��й�ȣ ã�� �׼�
	@PostMapping("pwdauth")
	@ResponseBody
	public Map<String, Object> pwdAuthAction(@RequestBody Map<String, String> requestBody, MemberVO vo,
			HttpSession session, Model model) {

		// ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();

		String member_Id = requestBody.get("inputId");
		String member_Email = requestBody.get("inputEmail_2");

		vo.setMember_Id(member_Id);
		vo.setMember_Email(member_Email);

		String pwd = memberService.selectPwdByIdNameEmail(vo);// ���̵�� �̸��Ϸ� ���̺��� ��ȸ
		if (pwd != null) {
			Random r = new Random();
			int num = 100000 + r.nextInt(900000); // ���� ���� ����
			session.setAttribute("email", vo.getMember_Email());
			session.setAttribute("Id", vo.getMember_Id());
			session.setAttribute("num", num);

			// ��й�ȣ ���� �̸��� ������
			EmailVO emailVO = new EmailVO();
			emailVO.setReceiveMail(vo.getMember_Email());
			emailVO.setSubject("[blueLemon] ��й�ȣ ���� ���� �̸����Դϴ�");
			String content = System.getProperty("line.separator") + "�ȳ��ϼ��� ȸ����" + System.getProperty("line.separator")
					+ "blueLemon ��й�ȣã��(����) ������ȣ�� " + num + " �Դϴ�." + System.getProperty("line.separator");
			emailVO.setMessage(content);

			System.out.println("<<<<<< ������ ������ ������ȣ : " + num + ">>>>>>");
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

	// �н����� ����
	@PostMapping("/updatePassword")
	public String updatePassword(MemberVO vo, HttpSession session, SessionStatus status) {

		String member_id = (String) session.getAttribute("Id");

		vo.setMember_Id(member_id);

		// ȸ�� ������ ������Ʈ�մϴ�.
		memberService.updatePassword(vo);

		status.setComplete();

		return "login";
	}

	// ȸ�� �˻�
	@GetMapping("/search_people")
	@ResponseBody
	public List<MemberVO> searchMembers(@RequestParam(value = "keyword") String keyword) {
		// Map<String, Object> retVal = new HashMap<String, Object>();
		if (keyword == null || keyword.trim().isEmpty()) {
			return Collections.emptyList();
		}

		// �˻�� ����Ͽ� ��� ���̵� �˻��ϰ� ����� ��ȯ�մϴ�.
		List<MemberVO> searchResults = memberService.searchMembers(keyword);

		return searchResults;
	}
	
	// PEOPLE �� List ��������
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
