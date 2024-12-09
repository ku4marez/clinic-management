package com.github.ku4marez.clinicmanagement.entity;

import com.github.ku4marez.clinicmanagement.entity.common.PersistentAuditedEntity;
import com.github.ku4marez.clinicmanagement.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
public class UserEntity extends PersistentAuditedEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
