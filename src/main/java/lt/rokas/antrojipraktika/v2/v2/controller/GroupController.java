package lt.rokas.antrojipraktika.v2.v2.controller;

import lt.rokas.antrojipraktika.v2.v2.entity.StudentsGroup;
import lt.rokas.antrojipraktika.v2.v2.entity.Subject;
import lt.rokas.antrojipraktika.v2.v2.entity.User;
import lt.rokas.antrojipraktika.v2.v2.service.GroupService;
import lt.rokas.antrojipraktika.v2.v2.service.SubjectService;
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
public class GroupController {

    private GroupService groupService;

    @Autowired
    private SubjectService subjectService;

    public GroupController(GroupService groupService) {
        super();
        this.groupService = groupService;
    }

    @GetMapping("/groups/new")
    public String createUserForm(Model model) {

        StudentsGroup studentsGroup = new StudentsGroup();
        model.addAttribute("studentsGroup", studentsGroup);
        return "create_studentsGroup";

    }

    @PostMapping("/groups")
    public String saveUser(@ModelAttribute("user") StudentsGroup studentsGroup) {
        groupService.saveGroup(studentsGroup);
        return "redirect:/groups";
    }

    @GetMapping("/groups")
    public String listGroups(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        return "groups";
    }

    @GetMapping("/groups/get/{id}")
    public String getGroup(@PathVariable Long id, Model model) {
        model.addAttribute("group", groupService.getGroupById(id));
        return "group";
    }

    @GetMapping("/groups/{id}")
    public String deleteUser(@PathVariable Long id) {
        groupService.deleteGroupById(id);
        return "redirect:/groups";
    }

    @GetMapping("/groups/edit/{id}")
    public String editGroupForm(@PathVariable Long id, Model model) {
        model.addAttribute("group", groupService.getGroupById(id));
        return "edit_group";
    }

    @PostMapping("/groups/{id}")
    public String updateGroup(@PathVariable Long id,
                              @ModelAttribute("subject") StudentsGroup group,
                              Model model) {

        StudentsGroup existingGroup = groupService.getGroupById(id);
        existingGroup.setId(id);
        existingGroup.setName(group.getName());

        groupService.updateGroup(existingGroup);
        return "redirect:/groups";
    }

    @GetMapping("/groups/assign/{id}")
    public String assignSubject(@PathVariable Long id, Model model) {
        model.addAttribute("groupId", id);
        List<Subject> subjects = subjectService.getAllSubjects().stream()
                .filter(subject -> !groupService.getGroupById(id).getSubjects().contains(subject))
                .collect(Collectors.toList());
        model.addAttribute("subjects", subjects);

        return "select_subject";
    }

    @GetMapping("/groups/assign/{id}/{groupId}")
    public String assignSubjectForGroup(@PathVariable Long id, @PathVariable Long groupId, Model model) {
        StudentsGroup studentsGroup = groupService.getGroupById(groupId);
        model.addAttribute("group", studentsGroup);
        groupService.assignSubjectForGroup(studentsGroup, subjectService.getSubjectById(id));
        return "group";
    }

    @GetMapping("/groups/unassign/{subjectId}/{groupId}")
    public String unassignSubjectFromGroup(@PathVariable Long subjectId, @PathVariable Long groupId, Model model) {
        StudentsGroup studentsGroup = groupService.getGroupById(groupId);
        model.addAttribute("group", studentsGroup);
        groupService.unassignSubjectForGroup(studentsGroup, subjectService.getSubjectById(subjectId));
        return "group";
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
}
