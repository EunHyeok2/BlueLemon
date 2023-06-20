package com.blue.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FollowVO {
	private String follower;
	private String following;
	
	private int	FollowingLocalPageFirstNum;	//���� ������ ���� �� �ѹ�
	private int	FollowingLocalPageLastNum;	//���� ������ ������ �� �ѹ�
	private int	FollowerLocalPageFirstNum;	//���� ������ ���� �� �ѹ�
	private int	FollowerLocalPageLastNum;	//���� ������ ������ �� �ѹ�
}
