package tqsgroup.chuchu.data.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tqsgroup.chuchu.data.entity.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {
    private String username;
    private String name;
    private Role role;
}
