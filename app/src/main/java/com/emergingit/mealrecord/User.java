package com.emergingit.mealrecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String uname;
    private String email;
    private String password;
}
