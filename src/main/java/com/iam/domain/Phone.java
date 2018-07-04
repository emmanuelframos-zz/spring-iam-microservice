package com.iam.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IAM_PHONE")
public class Phone {

    @Id
    @Column(name = "IP_ID")
    @GenericGenerator(strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", name = "IAM_PHONE_SEQ",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "IAM_PHONE_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    @GeneratedValue(generator="IAM_PHONE_SEQ")
    private Long id;

    @Column(name = "IP_DDD", nullable = false, length = 2)
    private String ddd;

    @Column(name = "IP_NUMBER", nullable = false, length = 10)
    private String number;

    @ManyToOne
    @JoinColumn(name="IP_IU_ID", referencedColumnName="IU_ID")
    private User user;

    public Phone(){
        //Avoid errors on parsing
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}