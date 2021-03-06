package cn.szu.edu.app.team.bean;

import java.io.Serializable;
import java.util.List;


import cn.szu.edu.app.bean.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("detail")
public class Detail extends Entity {
    @XStreamAlias("sun")
    private DayData sun;
    @XStreamAlias("thu")
    private DayData thu;
    @XStreamAlias("wed")
    private DayData wed;
    @XStreamAlias("tue")
    private DayData tue;
    @XStreamAlias("mon")
    private DayData mon;
    @XStreamAlias("fri")
    private DayData fri;
    @XStreamAlias("sat")
    private DayData sat;

    public class DayData implements Serializable {
        @XStreamImplicit(itemFieldName = "list")
        private List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

    }

    public DayData getSun() {
        return sun;
    }

    public void setSun(DayData sun) {
        this.sun = sun;
    }

    public DayData getThu() {
        return thu;
    }

    public void setThu(DayData thu) {
        this.thu = thu;
    }

    public DayData getWed() {
        return wed;
    }

    public void setWed(DayData wed) {
        this.wed = wed;
    }

    public DayData getTue() {
        return tue;
    }

    public void setTue(DayData tue) {
        this.tue = tue;
    }

    public DayData getMon() {
        return mon;
    }

    public void setMon(DayData mon) {
        this.mon = mon;
    }

    public DayData getFri() {
        return fri;
    }

    public void setFri(DayData fri) {
        this.fri = fri;
    }

    public DayData getSat() {
        return sat;
    }

    public void setSat(DayData sat) {
        this.sat = sat;
    }

}
