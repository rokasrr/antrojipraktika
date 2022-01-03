package lt.rokas.antrojipraktika.v2.v2.service;

import lt.rokas.antrojipraktika.v2.v2.entity.Subject;
import lt.rokas.antrojipraktika.v2.v2.entity.Grade;
import lt.rokas.antrojipraktika.v2.v2.entity.User;
import lt.rokas.antrojipraktika.v2.v2.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {
    GradeRepository gradeRepository;

    GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public void assignGradeForSubject(Subject subject) {
        Grade grade = new Grade();
        subject.getGrades().add(grade);
        gradeRepository.save(grade);
    }

    public Grade getGradeById(Long id) {
        return gradeRepository.getById(id);
    }

    public void updateGrade(Grade grade) {
        saveGrade(grade);
    }

    private void saveGrade(Grade grade) {
        gradeRepository.save(grade);
    }

    public void deleteGrade(Long gradeId) {
        gradeRepository.deleteById(gradeId);
    }

    public List<Grade> getGradesForUser(User user) {
        return gradeRepository.findAll().stream().filter(grade -> grade.getStudent().getId().equals(user.getId())).collect(Collectors.toList());
    }
}
