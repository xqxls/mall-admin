package com.xqxls.mall.common.id;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 19:11
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 0L;

    /**
     * id生成器
     */
    private static IdWorker idWorker;

    public static void setIdWorker(IdWorker idWorker) {
        BaseEntity.idWorker = idWorker;
    }

    @Id
    @Column(name = "id")
    private Long id;

    public void setPrimaryId() {
        this.id = idWorker.nextId();
    }
}
