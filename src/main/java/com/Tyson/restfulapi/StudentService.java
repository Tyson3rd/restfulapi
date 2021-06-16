package com.Tyson.restfulapi;

import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.time.LocalDate;
        import java.util.List;
        import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addStudent(Student student){
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("Student already exists");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId){
        if(studentRepository.existsById(studentId)){
            studentRepository.deleteById(studentId);
        }else{
            throw new IllegalStateException("Student not found");
        }
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email, LocalDate dob){
        //Get Student if exist and change data if differs
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student not found"));
        if(!student.getName().equals(name)){ student.setName(name);}
        if(!student.getEmail().equals(email)){ student.setEmail(email);}
        if(!student.getDob().equals(dob)){ student.setDob(dob);}
        student.setName(name);
    }
}