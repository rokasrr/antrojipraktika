package lt.rokas.antrojipraktika.v2.v2.service;

import lt.rokas.antrojipraktika.v2.v2.entity.StudentsGroup;
import lt.rokas.antrojipraktika.v2.v2.entity.Subject;
import lt.rokas.antrojipraktika.v2.v2.entity.User;
import lt.rokas.antrojipraktika.v2.v2.repository.StudentsGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{
    private StudentsGroupRepository studentsGroupRepository;

    public GroupServiceImpl(StudentsGroupRepository studentsGroupRepository) {
        super();
        this.studentsGroupRepository = studentsGroupRepository;
    }


    @Override
    public List<StudentsGroup> getAllGroups() {
        return studentsGroupRepository.findAll();
    }

    @Override
    public StudentsGroup saveGroup(StudentsGroup group) {
        return studentsGroupRepository.save(group);
    }

    @Override
    public StudentsGroup getGroupById(Long id) {
        return studentsGroupRepository.findById(id).get();
    }

    @Override
    public StudentsGroup updateGroup(StudentsGroup group) {
        return saveGroup(group);
    }

    @Override
    public void deleteGroupById(Long id) {
        studentsGroupRepository.deleteById(id);
    }

    @Override
    public void assignUserForGroup(StudentsGroup studentsGroup, User userById) {
        studentsGroup.getStudents().add(userById);
        saveGroup(studentsGroup);
    }

    @Override
    public void unassignUserFromGroup(User userById) {
        StudentsGroup group = userById.getStudentsGroup();
        group.getStudents().remove(userById);
        saveGroup(group);
    }

    @Override
    public void assignSubjectForGroup(StudentsGroup studentsGroup, Subject subjectById) {
        studentsGroup.getSubjects().add(subjectById);
        saveGroup(studentsGroup);
    }

    @Override
    public void unassignSubjectForGroup(StudentsGroup studentsGroup, Subject subjectById) {
        studentsGroup.getSubjects().remove(subjectById);
        saveGroup(studentsGroup);
    }
}
