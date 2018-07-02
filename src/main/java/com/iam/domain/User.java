package com.iam.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IAM_USER")
public class User {

    @Id
    private Long id;

}
