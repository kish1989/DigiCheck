package com.example.kishan.digicheck3;

/**
 * Created by Kishan on 06-06-2015.
 */
public class PCInformation {
    //"rowid","regno","ptsno","doe","testactual","testreg","aadharno"
    private long rowid, aadharno;
    private String regno, ptsno, doe, testactual, testregulation;

    public long getRowid() {
        return rowid;
    }

    public void setRowid(long rowid) {
        this.rowid = rowid;
    }

    public long getAadharno() {
        return aadharno;
    }

    public void setAadharno(long aadharno) {
        this.aadharno = aadharno;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getPtsno() {
        return ptsno;
    }

    public void setPtsno(String ptsno) {
        this.ptsno = ptsno;
    }

    public String getDoe() {
        return doe;
    }

    public void setDoe(String doe) {
        this.doe = doe;
    }

    public String getTestactual() {
        return testactual;
    }

    public void setTestactual(String testactual) {
        this.testactual = testactual;
    }

    public String getTestregulation() {
        return testregulation;
    }

    public void setTestregulation(String testregulation) {
        this.testregulation = testregulation;
    }
}
