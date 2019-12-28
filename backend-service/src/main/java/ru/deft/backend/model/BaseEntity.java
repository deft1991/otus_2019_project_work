package ru.deft.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

/*
 * Created by sgolitsyn on 12/17/19
 */

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity {
    //    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator",
//            parameters = {@Parameter(name = "uuid_gen_strategy_class",
//                    value = "org.hibernate.id.uuid.CustomVersionOneStrategy")}
//    )
//    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private UUID id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;
    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;


    public void setCreatedBy(String createdBy) {
        if (createdBy == null || createdBy.equals("")){
            createdBy = "base user";
        } else {
            this.createdBy = createdBy;
        }
    }
}
