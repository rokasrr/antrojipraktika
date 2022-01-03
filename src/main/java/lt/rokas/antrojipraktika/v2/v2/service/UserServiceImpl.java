package lt.rokas.antrojipraktika.v2.v2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lt.rokas.antrojipraktika.v2.v2.dto.LoginUserDto;
import lt.rokas.antrojipraktika.v2.v2.entity.Grade;
import lt.rokas.antrojipraktika.v2.v2.entity.StudentsGroup;
import lt.rokas.antrojipraktika.v2.v2.entity.User;
import lt.rokas.antrojipraktika.v2.v2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void assignGroupForUser(StudentsGroup studentsGroup, User userById) {
        userById.setStudentsGroup(studentsGroup);
        saveUser(userById);
    }

    @Override
    public void unassignGroupFromUser(User userById) {
        userById.setStudentsGroup(null);
        saveUser(userById);
    }

    @Override
    public void unassignSubjectFromLector(User lector) {
        // TODO: Implement extra logic.
    }

    @Override
    public boolean checkLogin(LoginUserDto loginUser) {
        Optional<User> user = userRepository.findAll().stream().filter(usr -> usr.getFirstName().equals(loginUser.getFirstName())).findFirst();

        if (!user.isPresent()) {
            return false;
        } else if (user.get().getFirstName().equals(loginUser.getFirstName()) && user.get().getPassword().equals(loginUser.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User getUserByName(LoginUserDto loginUser) {
        return userRepository.findAll().stream().filter(usr -> usr.getFirstName().equals(loginUser.getFirstName())).findFirst().get();
    }
}