package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        User user1 = new User("Igor", "Savelenko", (byte)33);
        User user2 = new User("Vasya", "Ivanov", (byte)21);
        User user3 = new User("Anna", "Snopko", (byte)10);
        User user4 = new User("Dima", "Eremenko", (byte)3);

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        userService.removeUserById(2);

        List<User> users = userService.getAllUsers();
        for(User u : users){
            System.out.println(u);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
