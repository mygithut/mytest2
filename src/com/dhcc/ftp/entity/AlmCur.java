package com.dhcc.ftp.entity;



public class AlmCur  implements java.io.Serializable {

     private String curno;
     private String curname;
     private String curcode;
     private Integer curstate;

    public AlmCur() {
    }
    public AlmCur(String curno) {
        this.curno = curno;
    }
    public AlmCur(String curno, String curname, String curcode, Integer curstate) {
        this.curno = curno;
        this.curname = curname;
        this.curcode = curcode;
        this.curstate = curstate;
    }


    public String getCurno() {
        return this.curno;
    }
    
    public void setCurno(String curno) {
        this.curno = curno;
    }

    public String getCurname() {
        return this.curname;
    }
    
    public void setCurname(String curname) {
        this.curname = curname;
    }

    public String getCurcode() {
        return this.curcode;
    }
    
    public void setCurcode(String curcode) {
        this.curcode = curcode;
    }

    public Integer getCurstate() {
        return this.curstate;
    }
    
    public void setCurstate(Integer curstate) {
        this.curstate = curstate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AlmCur) ) return false;
		 AlmCur castOther = ( AlmCur ) other; 
         
		 return ( (this.getCurno()==castOther.getCurno()) || ( this.getCurno()!=null && castOther.getCurno()!=null && this.getCurno().equals(castOther.getCurno()) ) )
 && ( (this.getCurname()==castOther.getCurname()) || ( this.getCurname()!=null && castOther.getCurname()!=null && this.getCurname().equals(castOther.getCurname()) ) )
 && ( (this.getCurcode()==castOther.getCurcode()) || ( this.getCurcode()!=null && castOther.getCurcode()!=null && this.getCurcode().equals(castOther.getCurcode()) ) )
 && ( (this.getCurstate()==castOther.getCurstate()) || ( this.getCurstate()!=null && castOther.getCurstate()!=null && this.getCurstate().equals(castOther.getCurstate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCurno() == null ? 0 : this.getCurno().hashCode() );
         result = 37 * result + ( getCurname() == null ? 0 : this.getCurname().hashCode() );
         result = 37 * result + ( getCurcode() == null ? 0 : this.getCurcode().hashCode() );
         result = 37 * result + ( getCurstate() == null ? 0 : this.getCurstate().hashCode() );
         return result;
   }   





}