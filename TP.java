import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Netflix {
    private int id;
    private String type;
    private String title;
    private String director;
    private String added;

    public Netflix() {

    }

    public Netflix(int i, String t, String n, String d, String a) {
        this.id = i;
        this.type = t;
        this.title = n;
        this.director = d;
        this.added = a;

    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getType() {
        return type;
    }

    public void setType(String t) {
        this.type = t;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String n) {
        this.title = n;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String d) {
        this.director = d;
    }

    public String getDate() {
        return added;
    }

    public void setDate(String a) {
        this.added = a;
    }

    public String toString() {
        return "\nID: " + id +
                "\nNome: " + title +
                "\nTipo: " + type +
                "\nDiretor: " + director +
                "\nData: " + added;
    }

    public void ler(String linha) {

        String[] data = linha.split(";");
        for(int i=0;i<data.length;i++){
            if(data[i] == ""){
                data[i] = "não informado";
            }
        }

            setId(Integer.parseInt(data[0]));
            setType(data[1]);
            setTitle(data[2]);
            setDirector(data[3]);
            setDate(data[4]);
        

    }

}

public class TP {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Netflix> programas = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader("netflix.csv");
            BufferedReader arq = new BufferedReader(fileReader);
            arq.readLine();
            while (arq.ready()) {
                Netflix programa = new Netflix();
                programa.ler(arq.readLine());
                programas.add(programa);
                System.out.println(programa);
            }
            arq.close();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getLocalizedMessage());
        }

        sc.close();
    }
}
