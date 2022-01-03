package lt.rokas.antrojipraktika.v2.v2.controller;

import lt.rokas.antrojipraktika.v2.v2.entity.StudentsGroup;
import lt.rokas.antrojipraktika.v2.v2.entity.User;
import lt.rokas.antrojipraktika.v2.v2.service.GroupService;
import lt.rokas.antrojipraktika.v2.v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    private GroupService groupService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/new")
    public String createUserForm(Model model) {

        User user = new User();
        model.addAttribute("user", user);
        return "create_user";

    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user) {
        user.setPassword(user.getLastName());
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit_user";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute("user") User user,
                             Model model) {

        User existingUser = userService.getUserById(id);
        existingUser.setId(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        userService.updateUser(existingUser);
        return "redirect:/users";
    }


    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/users/unassign/{id}")
    public String unassignUser(@PathVariable Long id, Model model) {
        final StudentsGroup group = userService.getUserById(id).getStudentsGroup();
        groupService.unassignUserFromGroup(userService.getUserById(id));
        userService.unassignGroupFromUser(userService.getUserById(id));
        model.addAttribute("group", group);
        return "group";
    }

    @GetMapping("/users/assign/{id}")
    public String assignUser(@PathVariable Long id, Model model) {
        model.addAttribute("groupId", id);

        List<User> students = userService.getAllUsers().stream().filter(user -> user.getStudentsGroup()==null)
                .collect(Collectors.toList());
        model.addAttribute("users", students);

        return "select_user";
    }

    @GetMapping("/users/assign/{id}/{groupId}")
    public String assignUserForGroup(@PathVariable Long id, @PathVariable Long groupId, Model model) {
        StudentsGroup studentsGroup = groupService.getGroupById(groupId);
        model.addAttribute("group", studentsGroup);
        groupService.assignUserForGroup(studentsGroup, userService.getUserById(id));
        userService.assignGroupForUser(studentsGroup, userService.getUserById(id));
        return "group";
    }

    @GetMapping("/users/assign/student/{id}/{subjectId}")
    public String assignStudent(@PathVariable Long id,@PathVariable Long subjectId, Model model) {
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("gradeId", id);
        List<User> students = userService.getAllUsers().stream().filter(user -> user.getStudentsGroup()!=null)
                .filter(user -> user.getIsLector() == false)
                .collect(Collectors.toList());
        model.addAttribute("users", students);

        return "select_student";
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}