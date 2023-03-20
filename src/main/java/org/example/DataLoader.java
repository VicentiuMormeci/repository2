package org.example;
//import com.itfactory.model.Course;
//import com.itfactory.model.Student;
//import com.itfactory.utils.DataLoaderUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

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
            //System.out.println(students);
            mapStudentsToCourses();
            System.out.println(dataMap);
        }

        private void loadCourses() throws IOException {
            List<String> courseInputData = DataLoaderUtils.readFile(DataLoaderUtils.COURSE_FILE_PATH);
            //System.out.println(courseInputData);

            for (String courseInputDatum : courseInputData) {
                String[] data = courseInputDatum.split(",");
                //System.out.println(data);
                Course cursNou = createCourse(data);
                dataMap.put(cursNou, new ArrayList<>());
                //System.out.println(dataMap);
            }
        }

        private Course createCourse(String[] data) {
            //System.out.println(data[0]);
            int id = Integer.parseInt(data[0]);
            //System.out.println(data[1]);
            String name = data[1];
            //System.out.println(data[2]);
            double price = Double.parseDouble(data[2]);
            //System.out.println(data[3]);

            LocalDate dataInceput = LocalDate.parse(data[3]);
            return new Course(id, name, price,dataInceput);
        }

        private void loadStudents() throws IOException{
            List<String> studenti = DataLoaderUtils.readFile(DataLoaderUtils.STUDENT_FILE_PATH);
            //System.out.println(studenti);

            for (String line: studenti){
                String[] dateStudent = line.split(",");
                Student student = createStudent(dateStudent);
                students.add(student);
            }

        }
        private Student createStudent(String[] dateStudent){
            int id = Integer.parseInt(dateStudent[0]);
            String nume = dateStudent[1];
            double buget = Double.parseDouble(dateStudent[2]);

            Student student = new Student(id,nume,buget);
            return student;
        }


        private void mapStudentsToCourses() throws IOException {
            List<String> listaMapare = DataLoaderUtils.readFile(DataLoaderUtils.MAPPING_FILE_PATH);

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
