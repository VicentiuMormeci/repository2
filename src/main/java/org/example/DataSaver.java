package org.example;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataSaver {

    public void saveData(Map<Course, List<Student>> date) throws IOException {
        saveCourses(date);
        saveStudents(date);
        saveMappings(date);
    }

    private void saveMappings(Map<Course, List<Student>> date) throws IOException {
        String continut = "";

        for (Map.Entry<Course,List<Student>> felie: date.entrySet()){
            int cursId = felie.getKey().getCourseId();
            for (Student student: felie.getValue()){
                int studentID = student.getStudentId();
                continut = continut.concat(studentID+","+cursId+"\n");
            }
        }
        System.out.println(continut);
        DataLoaderUtils.writeFile(DataLoaderUtils.MAPPING_FILE_PATH,continut);

    }

    private void saveStudents(Map<Course, List<Student>> date) throws IOException {
        String continut = "";
        for (List<Student>valoriDinMap: date.values()){
            for (Student student:valoriDinMap){
                continut = continut.concat(student.toString().concat("\n"));
            }
        }
        System.out.println(continut);
        DataLoaderUtils.writeFile(DataLoaderUtils.STUDENT_FILE_PATH,continut);
    }

    private void saveCourses(Map<Course, List<Student>> date) throws IOException {
        Set<Course> setCursuri = date.keySet();
        String continut = "";
        for (Course curs:setCursuri){
            continut = continut.concat(curs.toString().concat("\n"));
        }
        System.out.println(continut);
        DataLoaderUtils.writeFile(DataLoaderUtils.COURSE_FILE_PATH,continut);
    }

}
