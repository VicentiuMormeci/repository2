package org.example;

import javax.sound.midi.MetaMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.example.DataLoaderUtils.COURSE_FILE_PATH;

public class Main {
    public static void main(String[] args) throws IOException {

        //incarca date din fisier
        DataLoader dl = new DataLoader();
        DataSaver ds = new DataSaver();
        dl.loadData();
        Map<Course, List<Student>> date = dl.getDataMap();
        Metods metode = new Metods();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Puteti introduce urmatoarele optiuni:\n" +
                "0 – Ies din program.\n" +
                "1 – Afiseaza cursuri\n" +
                "2 – Introduceti un curs nou\n" +
                "3 – Introduceti un student nou si inrolati-l la curs\n" +
                "4 – Cautati un student dupa nume (OPTIONAL)\n" +
                "5 – Afiseaza studentii si cursul la care participa. (OPTIONAL)");

        while(true) {
            System.out.println("Va rugam sa introduceti o optiune: ");
            int optiune = Integer.parseInt(scanner.nextLine());


            switch (optiune) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Afiseaza cursuri");
                    metode.afisareCursuri(date);
                    break;
                case 2:
                    System.out.println("Introduceti un curs nou: ");
                    System.out.println("Introduceti id-ul cursului:");
                    int idCurs = Integer.parseInt(scanner.nextLine());
                    System.out.println("Introduceti numele cursului:");
                    String numeCurs = scanner.nextLine();
                    System.out.println("Introduceti pretul cursului:");
                    double pretCurs = Double.parseDouble(scanner.nextLine());
                    System.out.println("Introduceti data cursului in formatul: dd/MM/yyyy:");
                    String dataCursString = scanner.nextLine();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    //try catch pentru exceptie afisata cand data este incorecta
                    LocalDate dataCurs = LocalDate.parse(dataCursString,format);

                    Course cursNou = new Course(idCurs,numeCurs,pretCurs,dataCurs);
                    metode.adaugaCurs(cursNou, date);
                    metode.afisareCursuri(date);

                    ds.saveData(date);
                    break;
                case 3:
                    System.out.println("Introduceti un student nou si inrolati-l la curs");
                    //metode.afisareCursuri(date);

                    int cursuriDisponibile = 0;
                    for (Map.Entry<Course, List<Student>> felie: date.entrySet()) {
                        if (felie.getValue().size() < 2) {
                            System.out.println("Curse indisponibil:" + felie.getKey());
                            cursuriDisponibile++;
                        }
                    }
                    if (cursuriDisponibile == 0){
                        System.out.println("Ne pare rau, nu avem cursuri disponibile");
                        break;
                    }

                    System.out.println("Introduceti numele unui curs din lista de mai sus:");
                    String numeCursCautat = scanner.nextLine();

                    Course cursGasit = null;
                    LocalDate acum = LocalDate.now();

                    for(Course curs: date.keySet()){
                        if (curs.getCourseName().equals(numeCursCautat)){
                            System.out.println("Am gasit cursul");
                            cursGasit = curs;
                        }
                        if (cursGasit == null){
                            System.out.println("acest curs nu exista");
                            break;
                        }
                    }
                    System.out.println("introdu id-ul studentului: ");
                    int idStudent = Integer.parseInt(scanner.nextLine());

                    System.out.println("introdu numele studentului");
                    String numeStudent = scanner.nextLine();

                    System.out.println("Introdu bugetul studentului:");
                    double bugetStudent = Double.parseDouble(scanner.nextLine());

                    Student student = new Student(idStudent,numeStudent,bugetStudent);

                    System.out.println("ati introdus studentul: "+student);

                    try{
                        student.setBudget(bugetStudent - cursGasit.getPrice());
                        date.get(cursGasit).add(student);
                        System.out.println(date);
                        ds.saveData(date);
                    } catch (BugetInvalidException e){
                        System.out.println("Bugetul este invalid");
                    }

                    System.out.println(dl.getDataMap());
                    ds.saveData(dl.getDataMap());
                    break;

                case 4:
                    System.out.println("Cautati un student dupa nume");

                    System.out.println("Introduceti numele studentului:");
                    String numeStudentCautat = scanner.nextLine();

                    for (List<Student> lista : date.values()){
                        for (Student studentDinLista:lista){
                            if (studentDinLista.getStudentName().contains(numeStudentCautat)){
                                System.out.println("Am gasit studentul:" + studentDinLista);
                        //putem sa vedem cum putem afisa si cursul la care este inscris
                            }
                        }
                    }

                    break;
                case 5:
                    System.out.println("Afiseaza studentii si cursul la care participa");
                    for(Course curs: date.keySet()){
                        System.out.println("La cursul " + curs.getCourseName() + " participa urmatorii studenti: ");
                        for(Student studentLaCurs: date.get(curs)){
                            System.out.println(studentLaCurs.getStudentName());
                        }
                    }

                    break;
                default:
                    System.out.println("Aceasta optiune nu exista!");
            }
        }

    }
}