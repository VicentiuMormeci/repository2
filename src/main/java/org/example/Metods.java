package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Metods {
    public void afisareCursuri(Map<Course, List<Student>> dataMap){
        System.out.println(dataMap.keySet());
    }

    public void adaugaCurs(Course curs, Map<Course, List<Student>> dataMap){
        dataMap.put(curs, new ArrayList<>());
    }
}
