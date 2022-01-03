package lt.rokas.antrojipraktika.v2.v2.controller;

import lt.rokas.antrojipraktika.v2.v2.dto.LoginUserDto;
import lt.rokas.antrojipraktika.v2.v2.entity.User;
import lt.rokas.antrojipraktika.v2.v2.service.GradeService;
import lt.rokas.antrojipraktika.v2.v2.service.SubjectService;
import lt.rokas.antrojipraktika.v2.v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    GradeService gradeService;
    @Autowired
    SubjectService subjectService;

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("userDto", new LoginUserDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(Model model, @ModelAttribute LoginUserDto userDto) {
        if (userService.checkLogin(userDto)) {
            User user = userService.getUserByName(userDto);
            if (user.getIsAdmin()) {
                model.addAttribute("users", userService.getAllUsers());
                return "users";
            } else if (user.getIsLector()) {
                model.addAttribute("subjects", subjectService.getAllSubjects());
                return "subjects";
            } else {
                model.addAttribute("userGrades", gradeService.getGradesForUser(user));
                return "my_grades";
            }
        }
        model.addAttribute("userDto", new LoginUserDto());
        return "login";
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
}
