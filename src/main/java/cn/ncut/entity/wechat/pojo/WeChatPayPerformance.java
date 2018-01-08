package cn.ncut.entity.wechat.pojo;

import java.sql.Timestamp;

public class WeChatPayPerformance {
    private Integer performanceId;
    private String transactionName;
    private String transactionOperation;
    private long beginTime;
    private long endTime;
    private Timestamp operateTime;
    private String comment;
    public Integer getPerformanceId() {
        return performanceId;
    }
    public void setPerformanceId(Integer performanceId) {
        this.performanceId = performanceId;
    }
    public String getTransactionName() {
        return transactionName;
    }
    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }    
    public String getTransactionOperation() {
        return transactionOperation;
    }
    public void setTransactionOperation(String transactionOperation) {
        this.transactionOperation = transactionOperation;
    }
    public long getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }
    public long getEndTime() {
        return endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    public Timestamp getOperateTime() {
        return operateTime;
    }
    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * @param transactionName 事务名称
     * @param beginTime 开始时间戳
     * @param endTime 结束时间戳
     * @param comment 备注,可为空
     */
    public static WeChatPayPerformance savePayPerformance(String transactionName, String transactionOperation
            , long beginTime, long endTime, String comment) {
        WeChatPayPerformance weChatPayPerformance = new WeChatPayPerformance();
        
        weChatPayPerformance.setTransactionName(transactionName);
        if (transactionOperation != null) {
            weChatPayPerformance.setTransactionOperation(transactionOperation);
        } else {
            weChatPayPerformance.setTransactionOperation("N/A");
        }
        weChatPayPerformance.setBeginTime(beginTime);
        weChatPayPerformance.setEndTime(endTime);
        weChatPayPerformance.setOperateTime(getCurrentTimestamp());
        if (comment != null) {
            weChatPayPerformance.setComment(comment);
        } else {
            weChatPayPerformance.setComment("事务处理");
        }
        return weChatPayPerformance;
    }
    
    /**
     * 获取当前时刻对应的时间戳
     * @return Timestamp  时间戳
     * */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}