package com.qn.computersell.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "users")
@Data
public class UserEntity {
        private int id;
        private String username;
        private String password;
        private int age;

        /** getter and setter */

}
