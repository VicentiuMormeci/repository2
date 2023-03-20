package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {

    private Map<Course, List<Student>> dataMap;
    // Java{1, java, 300} => (student 1, student 2, ..)
    private List<Student> students;

    public DataLoader() {
        dataMap = new HashMap<>();
        students = new ArrayList<>();

        //dataMap.get(Course).add(studentName);
    }

    public void loadData() throws IOException {
        loadCourses();
        loadStudents();
        mapStudentsToCourses();

    }

    private void loadCourses() throws IOException {
        List<String> courseInputData = DataLoaderUtils.readFile(DataLoaderUtils.COURSE_FILE_PATH);
        for (String courseInputDatum : courseInputData) {
            String[] data = courseInputDatum.split(",");
            Course cursNou = createCourse(data);
            dataMap.put(cursNou, new ArrayList<>());
        }
    }

    private Course createCourse(String[] data) {
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        double price = Double.parseDouble(data[2]);

        LocalDate dataInceput = LocalDate.parse(data[3]);
        return new Course(id, name, price,dataInceput);
    }

    private void loadStudents() throws IOException{
        List<String> studenti = DataLoaderUtils.readFile(DataLoaderUtils.STUDENT_FILE_PATH);

        for (String line: studenti){
            String[] dateStudent = line.split(",");
            Student student = createStudent(dateStudent);
            students.add(student);
        }

    }
    private Student createStudent(String[] dateStudent){
        int id = Integer.parseInt(dateStudent[0]);
        String nume = dateStudent[1];
        Double buget = Double.parseDouble(dateStudent[2]);

        Student student = new Student(id,nume,buget);
        return student;
    }


    private void mapStudentsToCourses() throws IOException {
        List<String> listaMapare = DataLoaderUtils.readFile(DataLoaderUtils.MAPPING_FILE_PATH);
        System.out.println(dataMap);
        System.out.println(students);

        for (String mapare: listaMapare){
            String[] iduri = mapare.split(",");

            int idStudent = Integer.parseInt(iduri[0]);
            int idCurs = Integer.parseInt(iduri[1]);

            Student studentGasit = null;
            Course cursGasit = null;
            for (Student student: students){
                if (student.getStudentId() == idStudent){
                    studentGasit = student;
                    break;
                }
            }
            for(Course curs: dataMap.keySet()){
                if (curs.getCourseId() == idCurs){
                    cursGasit = curs;
                    break;
                }
            }
            if (studentGasit != null && cursGasit != null){

                double bugetStudent = studentGasit.getBudget();
                double pretCurs = cursGasit.getPrice();

                double bugetRamas = bugetStudent - pretCurs;

                try {
                    // doar daca bugetul >= pretul cursului
                    studentGasit.setBudget(bugetRamas);
                    dataMap.get(cursGasit).add(studentGasit);
                } catch(BugetInvalidException e) {
                    System.out.println("Studentul nu poate fi adaugat");
                }
            }
        }
    }

    public Map<Course, List<Student>> getDataMap() {
        return dataMap;
    }


}