package com.painter_act.model;

import java.util.List;

public interface PainterActDAO_interface {
	
	//���ʤ��ʸ��(executeType -1:�R�� 1:�W�[)
	public void update(Integer executeType, PainterActVO painterActVO);
	
	//�d�ߦ����Ƿ|���I�F�Y�ӧ@�~��1:���w 2:����
	public List<PainterActVO> getAllByActType(Integer ptr_no, Integer act_type);
	
	//�d�ߵn�J���|���P�@�~�����ʾ��v
	public PainterActVO getOneByActType(Integer ptr_no, Integer act_type, String mem_id);
	
}
