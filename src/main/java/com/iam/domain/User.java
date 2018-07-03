package com.iam.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name="IAM_USER")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @Column(name = "IU_ID")
    @GenericGenerator(strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", name = "IAM_USER_SEQ",
        parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "IAM_USER_SEQ"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
        })
    @GeneratedValue(generator="IAM_USER_SEQ")
    private Long id;

    @Column(name = "IU_NM", nullable = false, length = 100)
    private String name;

    @Column(name = "IU_EML", nullable = false, length = 50)
    private String email;

    @Column(name = "IU_PSSWD", nullable = false, length = 8)
    private String password;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name = "IU_CRTD")
    private Date created;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name = "IU_LST_MDF")
    private Date lastModified;

    @Temporal(TIMESTAMP)
    @Column(name = "IU_LST_LGN", nullable = false)
    private Date lastLogin;

    @Column(name = "IU_TKN", nullable = false)
    private String token;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Phone> phones;

    public User(){
        this.phones = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void addPhone(Phone phone) {
        if (!CollectionUtils.isEmpty(this.phones))
            this.phones.add(phone);
    }
}