package com.dreamchain.skeleton.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer",
        indexes = {
                @Index(name = "order_created_by_id_idx", columnList = "createdBy"),
                @Index(name = "order_updated_by_id_idx", columnList = "updatedBy"),
                @Index(name = "order_updated_by_id_idx", columnList = "customerCode")
        })
public class Order {

    private static final long serialVersionUID = -4060739788760795256L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    private long version;


    @NotEmpty
    @Column(name = "customerCode",unique = true,length = 6)
    private String customerCode;

    @NotEmpty
    @Column(name = "orderCode",unique = true,length = 11)
    private String orderCode;


    @NotEmpty
    @Column(name = "isActive")
    private boolean isActive;

    @JsonIgnore
    @Column
    private long createdBy;

    @JsonIgnore
    @Column
    private long updatedBy;

    @JsonIgnore
    @Column
    private Date createdOn;

    @JsonIgnore
    @Column
    private Date updatedOn;

}
