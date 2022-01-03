package lt.rokas.antrojipraktika.v2.v2.service;

import java.util.List;

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
}