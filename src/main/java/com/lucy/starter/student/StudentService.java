package com.lucy.starter.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        Optional<Student> foundStudent = studentRepository.findStudentByEmail(student.getEmail());
        if (foundStudent.isPresent()) {
            throw new IllegalStateException("Email was already taken!!!");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("Student with Id: " + studentId + " does not exist!");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with Id: " + studentId + " does not exist!"));

        if (name != null &&
                !name.isEmpty() &&
                !name.isBlank() &&
                !existingStudent.getName().equals(name)) {
            existingStudent.setName(name);
        }

        if (email != null &&
                !email.isEmpty() &&
                !email.isBlank() &&
                !existingStudent.getEmail().equals(email)) {
            Optional<Student> existingStudentByEmail = studentRepository.findStudentByEmail(email);
            if (existingStudentByEmail.isPresent()) {
                throw new IllegalStateException("Email was already taken by another user!!!");
            }

            existingStudent.setEmail(email);
        }
    }
}
