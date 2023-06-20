package com.blue.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostVO {

	private int 	post_Seq;            // �Խñ� ������ȣ
	private String  member_Id;		 // �Խñ� �ۼ���
	private String  post_Content;  		 // �Խñ� ����
	private String 	post_Date;			 // �Խñ� �ۼ���
	private String 	post_Update; 		 // �Խñ� ������
	private String  post_Public;		 // �Խñ� ���� ����
	private int 	post_Image_Count;    // �Խñ� ÷�� �̹��� ����
	private int 	post_Count;			 // �Խñ� ��ȸ��
	private int 	post_Like_Count;  	 // �Խñ� ���ƿ� ��
	private int 	post_Reply_Count;	 // �Խñ� ��� ��
	private String  post_Hashtag;		 // �Խñ� �ؽ��±�
	private String	post_LikeYN;		 // �Խñ� ���ƿ� ���� N = ���ƿ� �ȴ��� ���� Y = ���ƿ� ���� ����
}
