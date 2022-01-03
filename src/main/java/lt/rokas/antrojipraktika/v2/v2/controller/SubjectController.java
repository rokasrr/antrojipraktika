package lt.rokas.antrojipraktika.v2.v2.controller;

import lt.rokas.antrojipraktika.v2.v2.entity.Grade;
import lt.rokas.antrojipraktika.v2.v2.entity.StudentsGroup;
import lt.rokas.antrojipraktika.v2.v2.entity.Subject;
import lt.rokas.antrojipraktika.v2.v2.entity.User;
import lt.rokas.antrojipraktika.v2.v2.service.GradeService;
import lt.rokas.antrojipraktika.v2.v2.service.SubjectService;
import lt.rokas.antrojipraktika.v2.v2.service.SubjectServiceImpl;
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
public class SubjectController {

    SubjectService subjectService;
    @Autowired
    GradeService gradeService;

    @Autowired
    UserService userService;

    public SubjectController(SubjectService subjectService) {
        super();
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    public String listUsers(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjects";
    }

    @GetMapping("/subjects/new")
    public String createSubjectForm(Model model) {

        Subject subject = new Subject();
        model.addAttribute("subject", subject);
        return "create_subject";
    }

    @GetMapping("/subjects/{id}")
    public String deleteSubject(@PathVariable Long id, Model model) {
        subjectService.deleteSubjectById(id);
        return "redirect:/subjects";
    }

    @PostMapping("/subjects")
    public String saveSubject(@ModelAttribute("subject") Subject subject) {
        subjectService.saveSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/edit/{id}")
    public String editSubjectForm(@PathVariable Long id, Model model) {
        model.addAttribute("subject", subjectService.getSubjectById(id));
        return "edit_subject";
    }

    @PostMapping("/subjects/{id}")
    public String updateSubject(@PathVariable Long id,
                                @ModelAttribute("subject") Subject subject,
                                Model model) {

        Subject existingSubject = subjectService.getSubjectById(id);
        existingSubject.setId(id);
        existingSubject.setName(subject.getName());

        subjectService.updateSubject(existingSubject);
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/get/{id}")
    public String getSubject(@PathVariable Long id, Model model) {
        model.addAttribute("subject", subjectService.getSubjectById(id));
        return "subject";
    }

    @GetMapping("/subjects/unassign/{id}")
    public String unassignUser(@PathVariable Long id, Model model) {
        final Subject subject = subjectService.getSubjectById(id);
        userService.unassignSubjectFromLector(subject.getLector());
        subjectService.unassignLectorFromSubject(subject);
        model.addAttribute("subject", subject);
        return "subject";
    }


    @GetMapping("/subjects/assign/{id}")
    public String assignLectorForSubject(@PathVariable Long id, Model model) {
        model.addAttribute("subjectId", id);

        List<User> lectors = userService.getAllUsers().stream().filter(user -> user.getIsLector())
                .collect(Collectors.toList());
        model.addAttribute("users", lectors);

        return "select_lector";
    }

    @GetMapping("/subjects/assign/{id}/{subjectId}")
    public String assignUserForGroup(@PathVariable Long id, @PathVariable Long subjectId, Model model) {
        Subject subject = subjectService.getSubjectById(subjectId);
        model.addAttribute("subject", subject);
        subjectService.assignLectorForSubject(subject, userService.getUserById(id));
        return "subject";
    }

    @GetMapping("/grades/add/{id}")
    public String assignGradeForSubject(@PathVariable Long id, Model model) {
        Subject subject = subjectService.getSubjectById(id);
        gradeService.assignGradeForSubject(subject);
        model.addAttribute("subject", subject);
        return "subject";
    }

    @GetMapping("/grades/set/{id}")
    public String setGradeForGrade(@PathVariable Long id, Model model) {
        Subject subject = subjectService.getAllSubjects().stream().filter(sub -> sub.getGrades().contains(gradeService.getGradeById(id))).findFirst().get();
        model.addAttribute("subject", subject);
        model.addAttribute("grade", gradeService.getGradeById(id));
        return "create_grade";
    }

    @PostMapping("/grades/save/{id}/{gradeId}")
    public String saveGradeForGrade(@PathVariable Long id, @PathVariable Long gradeId, Model model, @ModelAttribute("grade") Grade grade) {
        Subject subject = subjectService.getSubjectById(id);
        Grade existingGrade = gradeService.getGradeById(gradeId);
        existingGrade.setGrade(grade.getGrade());
        existingGrade.setId(gradeId);
        gradeService.updateGrade(existingGrade);
        model.addAttribute("subject", subject);
        return "subject";
    }

    @GetMapping("/subjects/delete/grade/{gradeId}/{subjectId}")
    public String saveGradeForGrade(@PathVariable Long gradeId, @PathVariable Long subjectId, Model model) {
        subjectService.getSubjectById(subjectId).getGrades().remove(gradeService.getGradeById(gradeId));
        gradeService.deleteGrade(gradeId);
        model.addAttribute("subject", subjectService.getSubjectById(subjectId));
        return "subject";
    }

    @GetMapping("/subjects/assign/student/{id}/{subjectId}/{gradeId}")
    public String addStudentForGrade(@PathVariable Long id, @PathVariable Long subjectId, Model model, @PathVariable Long gradeId) {
        Grade grade = gradeService.getGradeById(gradeId);
        grade.setStudent(userService.getUserById(id));
        gradeService.updateGrade(grade);
        model.addAttribute("subject", subjectService.getSubjectById(subjectId));
        return "subject";
    }

    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
