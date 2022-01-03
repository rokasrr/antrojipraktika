package lt.rokas.antrojipraktika.v2.v2.service;

import lt.rokas.antrojipraktika.v2.v2.repository.SubjectRepository;
import lt.rokas.antrojipraktika.v2.v2.entity.Subject;
import lt.rokas.antrojipraktika.v2.v2.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository)
    {
        this.subjectRepository=subjectRepository;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.getById(id);
    }

    @Override
    public Subject updateSubject(Subject subject) {
        return saveSubject(subject);
    }

    @Override
    public void deleteSubjectById(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public void unassignLectorFromSubject(Subject subject) {
        subject.setLector(null);
        subjectRepository.save(subject);
    }

    @Override
    public void assignLectorForSubject(Subject subject, User userById) {
        subject.setLector(userById);
        saveSubject(subject);
    }

}
