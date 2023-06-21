package com.blue.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blue.dto.MemberVO;
import com.blue.dto.PostVO;
import com.blue.service.MemberService;
import com.blue.service.PostService;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private PostService postService;
	
	// ȸ������ ȭ�� ǥ��
	@RequestMapping("/join_view")
	public String joinView() {
		
		return "createAccount";
	}		
	
	// ���̵� �ߺ�üũ
	@PostMapping("/checkDuplicate")
	public ResponseEntity<String> checkDuplicate(@RequestParam("member_Id") String member_Id) {
	    int result = memberService.confirmID(member_Id);
	    if (result == 1) {
	    	//System.out.println("�ߺ��� ���̵� :" + result);
	        return ResponseEntity.ok("duplicate");
	        
	    } else {
	    	//System.out.println("��밡���� ���̵�:" + result);
	        return ResponseEntity.ok("not-duplicate");
	      
	    } 
	}

    // ȸ������ ó��
	@PostMapping("create_form")
    public String joinAction(MemberVO vo, @RequestParam(value="profile_Image") MultipartFile profilePhoto,
    									  @RequestParam(value="email_add") String email_add) {
        if (!profilePhoto.isEmpty()) {
             // ������ ������ ������ ��θ� �����մϴ�.
	          String image_Path = "C:/spring-workspace/BlueLemon/src/main/webapp/WEB-INF/template/img/uploads/profile/";
	          //System.out.println("������ ��� ���� = " + image_Path);
	          // ������ ���ϸ��� �����մϴ�. ���ϸ��� member_Id�� Ȯ���ڸ��� �����մϴ�.
	          String fileName = vo.getMember_Id() + ".png";
	          // System.out.println("������ ���ϸ� = " + fileName);
	          // ������ ������ ��ο� �����մϴ�.
	         
	          // System.out.println("add : " + email_add );
		
	          try {
	        	  profilePhoto.transferTo(new File(image_Path + fileName));
	        	  // System.out.println("profilePhoto �� = " + profilePhoto);
	        	  // ����� ������ ��θ� MemberVO�� �����մϴ�.
	        	  vo.setMember_Profile_Image(fileName);
	          } catch (IOException e) {
	        	  e.printStackTrace();
	        	  // ���� ó���� �����մϴ�.
	          } 
        // �̹��� ���ε� ������ �⺻ �̹��� ���   
        } else {
        	//System.out.println("���ε� �̹��� ���� > �⺻�̹��� ���");
        	vo.setMember_Profile_Image("default.png");
        }
        
        if(email_add.equals(email_add)){
        	//System.out.println("�̸��� �ּ� �Է�ĭ �Է�");
        	vo.setMember_Email(vo.getMember_Email() + "@" + email_add);
        } else {
        	//System.out.println("�̸��� �ּҰ� �Էµ��� �ʾҽ��ϴ�.");
        }        
        
        memberService.insertMember(vo);
        
        return "login";	
	}

	// ȸ������ ����
	@PostMapping("update_form")
	public String updateMember(MemberVO vo, HttpSession session, Model model,
							   @RequestParam(value="profile_Image") MultipartFile profilePhoto,
	                           @RequestParam(value="email_add") String emailAdd) {
		
	    // ���� ������ ������ �����մϴ�.
	    String existingImagePath = "C:/spring-workspace/BlueLemon/src/main/webapp/WEB-INF/template/img/uploads/profile/" 
	    							+ vo.getMember_Profile_Image();
	    File existingImage = new File(existingImagePath);
	    if (existingImage.exists()) {
	        existingImage.delete();
	    }
	    
	    // ���ο� ������ ������ �����մϴ�.
	    if (!profilePhoto.isEmpty()) {
	        String imagePath = "C:/spring-workspace/BlueLemon/src/main/webapp/WEB-INF/template/img/uploads/profile/";
	        String fileName = vo.getMember_Id() + ".png";
	        try {
	            profilePhoto.transferTo(new File(imagePath + fileName));
	            vo.setMember_Profile_Image(fileName);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	    } else {
	        // �̹��� ���ε尡 ���� �� �⺻ �̹����� ����մϴ�.
	        vo.setMember_Profile_Image("default.png");
	    }
	    
	    // �̸��� �ּҸ� �����մϴ�.
	    String email = vo.getMember_Email() + "@" + emailAdd;
	    vo.setMember_Email(email);
	    
	    memberService.updateMember(vo);

	    // ������ �α��� ȸ�� ������ ������Ʈ�մϴ�.
        session.setAttribute("loginUser", vo);
        
        // ������ ȸ�� ������ �𵨿� �߰��Ͽ� JSP ���������� ����� �� �ֵ��� ��
        model.addAttribute("loginUser", vo);
        
        // �̸��� ���̵�� �̸��� �ּҸ� �и��Ͽ� �𵨿� �߰��Ͽ� JSP ���������� ����� �� �ֵ��� ��
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        
        String email1 = loginUser.getMember_Email();
	    int atIndex = email1.indexOf("@");
	    String email_Id = email.substring(0, atIndex);
	    String email_add = email.substring(atIndex + 1);
	    model.addAttribute("member_Email", email_Id);
	    model.addAttribute("email_add", email_add);
	    
	    return "redirect:edit_profile";
	}
	
	// ȸ������ ���� �ҷ�����
	@GetMapping("/edit_profile")
	public String userProfile(MemberVO vo,HttpSession session, Model model) {
		//System.out.println("edit_profile getmapping�ؼ� ��Ʈ�ѷ��� ��");
	    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
	   
	    // �̸��� �ּҸ� @ �������� �и��Ͽ� ���� ���̵�� ���� ���������� ����
	    String email = loginUser.getMember_Email();
	    int atIndex = email.indexOf("@");
	    String email_Id = email.substring(0, atIndex);
	    String email_add = email.substring(atIndex + 1);
	    model.addAttribute("member_Email", email_Id);
	    model.addAttribute("email_add", email_add);	  
		// ȭ�� ���� Hottest Feed
		List<PostVO> hottestFeed = postService.getHottestFeed();
		model.addAttribute("hottestFeed", hottestFeed);	

	    if (loginUser != null) {
	        // ���� ������ �𵨿� �߰��Ͽ� JSP ���������� ����� �� �ֵ��� ��
	        model.addAttribute("loginUser", loginUser);
	        
	        return "edit_profile";
	    } else {
	        // �α��ε��� ���� ��� ó��
	        return "redirect:/login";
	    }
	    
	}

	// ȸ�� Ż�� post
	@PostMapping(value="/memberDelete")
	public String memberDelete(MemberVO vo, HttpSession session, RedirectAttributes rttr) {
		
		// ���ǿ� �ִ� member�� ������ member������ �־��ݴϴ�.
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		// ���ǿ��ִ� ��й�ȣ
		String sessionPass = loginUser.getMember_Password();
		//System.out.println("DB �� Pass = " + sessionPass);

		// �Է¹޾Ƽ� vo�� ������ ��й�ȣ
		String voPass = vo.getMember_Password();
		//System.out.println("�Է¹��� Pass = " +voPass);
		
		if(!(sessionPass.equals(voPass))) {
			// alert ������ edit_profile.jsp �� ���� ����
			rttr.addFlashAttribute("msg", "wrong");
			//System.out.println("��� Ʋ��");
			return "redirect:edit_profile";
		} else {
			memberService.deleteMember(loginUser);
			session.invalidate();
			//System.out.println("Ż�� �Ϸ�");
			rttr.addFlashAttribute("msg", "withdrawlSuccess");
			return "redirect:login";
		}
	}	
} 
