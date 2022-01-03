package lt.rokas.antrojipraktika.v2.v2.service;

import lt.rokas.antrojipraktika.v2.v2.entity.StudentsGroup;
import lt.rokas.antrojipraktika.v2.v2.entity.Subject;
import lt.rokas.antrojipraktika.v2.v2.entity.User;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubjects();

    Subject saveSubject(Subject subject);

    Subject getSubjectById(Long id);

    Subject updateSubject(Subject subject);

    void deleteSubjectById(Long id);

    void unassignLectorFromSubject(Subject subject);

    void assignLectorForSubject(Subject subject, User userById);
}
