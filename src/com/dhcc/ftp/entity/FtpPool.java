package com.dhcc.ftp.entity;



public class FtpPool  implements java.io.Serializable {

    private Integer poolId;
	private String poolNo;
     private String poolName;
     private String poolType;

    public FtpPool() {
    }
    public FtpPool(Integer poolId) {
        this.poolId = poolId;
    }
    public FtpPool(Integer poolId,String poolNo, String poolName, String poolType) {
    	this.poolId=poolId;
        this.poolNo = poolNo;
        this.poolName = poolName;
        this.poolType = poolType;
    }

    public Integer getPoolId() {
        return this.poolId;
    }
    
    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
    }
    public String getPoolNo() {
        return this.poolNo;
    }
    
    public void setPoolNo(String poolNo) {
        this.poolNo = poolNo;
    }

    public String getPoolName() {
        return this.poolName;
    }
    
    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getPoolType() {
        return this.poolType;
    }
    
    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }

   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof FtpPool) ) return false;
		 FtpPool castOther = ( FtpPool ) other; 
         
		 return ( (this.getPoolId()==castOther.getPoolId()) || ( this.getPoolId()!=null && castOther.getPoolId()!=null && this.getPoolId().equals(castOther.getPoolId()) ) )
		 && ( (this.getPoolNo()==castOther.getPoolNo()) || ( this.getPoolNo()!=null && castOther.getPoolNo()!=null && this.getPoolNo().equals(castOther.getPoolNo()) ) )
 && ( (this.getPoolName()==castOther.getPoolName()) || ( this.getPoolName()!=null && castOther.getPoolName()!=null && this.getPoolName().equals(castOther.getPoolName()) ) )
 && ( (this.getPoolType()==castOther.getPoolType()) || ( this.getPoolType()!=null && castOther.getPoolType()!=null && this.getPoolType().equals(castOther.getPoolType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getPoolId() == null ? 0 : this.getPoolId().hashCode() );
         result = 37 * result + ( getPoolNo() == null ? 0 : this.getPoolNo().hashCode() );
         result = 37 * result + ( getPoolName() == null ? 0 : this.getPoolName().hashCode() );
         result = 37 * result + ( getPoolType() == null ? 0 : this.getPoolType().hashCode() );
         return result;
   }   





}