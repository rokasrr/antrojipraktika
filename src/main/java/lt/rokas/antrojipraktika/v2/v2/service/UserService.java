package lt.rokas.antrojipraktika.v2.v2.service;

import lt.rokas.antrojipraktika.v2.v2.dto.LoginUserDto;
import lt.rokas.antrojipraktika.v2.v2.entity.Grade;
import lt.rokas.antrojipraktika.v2.v2.entity.StudentsGroup;
import lt.rokas.antrojipraktika.v2.v2.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User saveUser(User user);

    User getUserById(Long id);

    User updateUser(User user);

    void deleteUserById(Long id);

    void assignGroupForUser(StudentsGroup studentsGroup, User userById);

    void unassignGroupFromUser(User userById);

    void unassignSubjectFromLector(User lector);

    boolean checkLogin(LoginUserDto loginUser);

    User getUserByName(LoginUserDto loginUser);
}