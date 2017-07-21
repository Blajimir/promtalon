import org.junit.Test;
import ru.promtalon.entity.Role;
import ru.promtalon.entity.User;
import ru.promtalon.util.DataAccessUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TestClazzFieds {
    @Test
    public void testFields() throws IllegalAccessException {
        User user = new User();
        user.setId(1);
        user.setUsername("Astrew");
        user.setPassword("asasasas");
        user.setEnable(true);
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");
        roles.add(role);
        role = new Role();
        role.setId(2);
        role.setName("ROLE_MANGER");
        roles.add(role);
        user.setRoles(roles);
        System.out.println("user before update: " + user);
        User newData = new User();
        newData.setUsername("Wertsa");
        System.out.println("user object containing new data: " + newData);
        DataAccessUtil.updateFields(newData, user, new String[]{"username"});
        System.out.println("user after update: " + user);
        DataAccessUtil.updateIgnoreFields(newData, user, new String[]{"id","roles"});
        System.out.println("user after updateIgnoreFields: " + user);
    }

    public void getAllFieds(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            System.out.println(field.getName());
        }
    }
}
