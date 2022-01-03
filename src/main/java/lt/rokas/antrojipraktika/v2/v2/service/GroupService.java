package lt.rokas.antrojipraktika.v2.v2.service;

import lt.rokas.antrojipraktika.v2.v2.entity.StudentsGroup;
import lt.rokas.antrojipraktika.v2.v2.entity.Subject;
import lt.rokas.antrojipraktika.v2.v2.entity.User;

import java.util.List;

public interface GroupService {
    List<StudentsGroup> getAllGroups();

    StudentsGroup saveGroup(StudentsGroup group);

    StudentsGroup getGroupById(Long id);

    StudentsGroup updateGroup(StudentsGroup group);

    void deleteGroupById(Long id);

    void assignUserForGroup(StudentsGroup studentsGroup, User userById);

    void unassignUserFromGroup(User userById);

    void assignSubjectForGroup(StudentsGroup studentsGroup, Subject subjectById);

    void unassignSubjectForGroup(StudentsGroup studentsGroup, Subject subjectById);
}
