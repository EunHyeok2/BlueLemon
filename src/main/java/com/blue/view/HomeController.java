package com.blue.view;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.blue.dto.AlarmVO;
import com.blue.dto.MemberVO;
import com.blue.dto.PostVO;
import com.blue.service.AlarmService;
import com.blue.service.PostService;

@Controller
@SessionAttributes("loginUser")
public class HomeController {

	@Autowired
	private AlarmService alarmService;
	@Autowired
	private PostService postService;
	
	@GetMapping(value="/faq")
	public String Faq(HttpSession session, Model model) {
		
		if(session.getAttribute("loginUser") == null) {
			//System.out.println("���ǰ� ����");
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
		
		String session_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();

		// �˶� ����Ʈ�� ��� �κ�
    	List<AlarmVO> alarmList = alarmService.getAllAlarm(session_Id);
    	
    	int alarmListSize = alarmList.size();
    	
    	// �˶��� ������ �ľ��ϴ� �κ�
    	for(int j=0; j<alarmList.size(); j++) {
    		int kind = alarmList.get(j).getKind();
    		if(kind == 1) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �ȷο� <br>�Ͽ����ϴ�.");
    		}else if(kind == 2) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>���ƿ並 �������ϴ�.");
    		}else if(kind == 3) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>����� �޾ҽ��ϴ�.");
    		}else if(kind == 4) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ ��ۿ� <br>���ƿ並 �������ϴ�.");
    		}else if(kind == 5) {
    			alarmList.get(j).setMessage("ȸ���Բ��� �����Ͻ� ������ <br>����� �޷Ƚ��ϴ�.");
    		}
    	}
    	
    	model.addAttribute("alarmList", alarmList);
		model.addAttribute("alarmListSize", alarmListSize);
		
		
		
		return "faq";
		
		}
	}
	
	@GetMapping(value="/404")
	public String a404() {
		
		return "404";
	}
	
	@GetMapping("/{statusCode}")
	public String getErrorPage(@PathVariable String statusCode) {
	    return statusCode;
	}
	
	@GetMapping(value="/editProfile")
	public String editProfile(HttpSession session, Model model) {
		
		if(session.getAttribute("loginUser") == null) {
			//System.out.println("���ǰ� ����");
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
			
		List<PostVO> hottestFeed = postService.getHottestFeed();
		String session_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();

		// �˶� ����Ʈ�� ��� �κ�
    	List<AlarmVO> alarmList = alarmService.getAllAlarm(session_Id);
    	
    	int alarmListSize = alarmList.size();
    	
    	// �˶��� ������ �ľ��ϴ� �κ�
    	for(int j=0; j<alarmList.size(); j++) {
    		int kind = alarmList.get(j).getKind();
    		if(kind == 1) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �ȷο� <br>�Ͽ����ϴ�.");
    		}else if(kind == 2) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>���ƿ並 �������ϴ�.");
    		}else if(kind == 3) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>����� �޾ҽ��ϴ�.");
    		}else if(kind == 4) {
    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ ��ۿ� <br>���ƿ並 �������ϴ�.");
    		}else if(kind == 5) {
    			alarmList.get(j).setMessage("ȸ���Բ��� �����Ͻ� ������ <br>����� �޷Ƚ��ϴ�.");
    		}
    	}
    	
    	MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
    	
    	String email = loginUser.getMember_Email();
		int atIndex = email.indexOf("@");
		String email_Id = email.substring(0, atIndex);
		String email_add = email.substring(atIndex + 1);
		
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("member_Email", email_Id);
		model.addAttribute("email_add", email_add);
    	model.addAttribute("alarmList", alarmList);
		model.addAttribute("alarmListSize", alarmListSize);
		model.addAttribute("hottestFeed", hottestFeed);
		
		return "edit_profile";
		
		}
	}
}
