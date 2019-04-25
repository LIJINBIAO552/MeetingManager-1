package com.meeting.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Eattype {
    private Integer etnum;

    private Long mnum;
    //�ð����toJson����תΪjson��ʽ������ע��ת��ʱָ��Ϊdate���͵�ĳ���ض���ʽ
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date ettimestart;
    
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date ettimeend;

    private String etplace;

    private String ettype;

    public Integer getEtnum() {
        return etnum;
    }

    public void setEtnum(Integer etnum) {
        this.etnum = etnum;
    }

    public Long getMnum() {
        return mnum;
    }

    public void setMnum(Long mnum) {
        this.mnum = mnum;
    }

    public Date getEttimestart() {
        return ettimestart;
    }

    public void setEttimestart(Date ettimestart) {
        this.ettimestart = ettimestart;
    }

    public Date getEttimeend() {
        return ettimeend;
    }

    public void setEttimeend(Date ettimeend) {
        this.ettimeend = ettimeend;
    }

    public String getEtplace() {
        return etplace;
    }

    public void setEtplace(String etplace) {
        this.etplace = etplace == null ? null : etplace.trim();
    }

    public String getEttype() {
        return ettype;
    }

    public void setEttype(String ettype) {
        this.ettype = ettype == null ? null : ettype.trim();
    }
}