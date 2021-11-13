package com.galvanize.prodman.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
public class Quote {
    @Id
    @Column(nullable = false, length = 50)
    private String currency;

    @Column(nullable = false, precision = 10, scale = 6)
    private Double value;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date updatedAt;
}
