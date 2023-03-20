package org.example;


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
        String continut2 = "";

        for (Map.Entry<Course,List<Student>> felie: date.entrySet()){
            Course curs = felie.getKey();

            int cursId = curs.getCourseId();
            for (Student student: felie.getValue()){
                int studentID = student.getStudentId();
                continut2 = continut2.concat(studentID+","+cursId+"\n");
            }
        }
        DataLoaderUtils.writeFile(DataLoaderUtils.MAPPING_FILE_PATH,continut2);

    }

    private void saveStudents(Map<Course, List<Student>> date) throws IOException {
        String continut1 = "";
        for (List<Student> valoriDinMap: date.values()){
            for (Student student:valoriDinMap){
                continut1 = continut1.concat(student.toString().concat("\n"));
            }
        }
        DataLoaderUtils.writeFile(DataLoaderUtils.STUDENT_FILE_PATH,continut1);
    }

    private void saveCourses(Map<Course, List<Student>> date) throws IOException {
        Set<Course> setCursuri = date.keySet();
        String continut = "";
        for (Course curs:setCursuri){
            continut = continut.concat(curs.toString().concat("\n"));
        }
        DataLoaderUtils.writeFile(DataLoaderUtils.COURSE_FILE_PATH,continut);
    }

}
