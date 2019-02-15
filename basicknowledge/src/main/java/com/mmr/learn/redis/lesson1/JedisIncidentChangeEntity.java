package com.mmr.learn.redis.lesson1;

/**
 * Description: Alert_故障变更通知 Redis中incidentChange对应的实体类
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月15日 14:32
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class JedisIncidentChangeEntity {
    private String severity; //告警级别（0:恢复，1:警告，2:错误，3:紧急 R12及以后版本）
    private String name; //告警名称
    private String alias;//告警别名
    private String entityName; //告警对象
    private String entityAddr; //告警IP
    private String firstOccurTime; //告警首次发生时间
    private String description; //告警描述
    private String count; //告警次数
    private String duration; //持续时间
    private String lastOccurTime; //告警最后发生时间
    private String status; //处理状态（0:未接手，150:处理中，190:已解决，255:已关闭）
    private String source; //告警来源系统标识（如果整合了多个监控平台的告警，可以为该监控平台简称）
    private String id; //告警的唯一标识
    private String resObjectId; //发生告警的CI名称或唯一标示
    private String orderId; //关联工单的工单ID
    private String owner; //负责人
    private String classCode; //资源类型（对应cmdb的CI的资源类型）
}
