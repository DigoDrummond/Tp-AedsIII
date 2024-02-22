import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Scanner;


public class TP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileOutputStream arqByte;
        DataOutputStream dos;

        try {
            FileReader fileReader = new FileReader("netflix.csv");
            BufferedReader arq = new BufferedReader(fileReader);

            arqByte = new FileOutputStream("data.db");
            dos = new DataOutputStream(arqByte);
            byte[] ba;
            

            arq.readLine();
            while(arq.ready()) {
                Netflix programa = new Netflix();
                programa.ler(arq.readLine());
                
                ba = programa.toByteArray();
                dos.writeInt(ba.length);
                dos.write(ba);
            }

            dos.close();
            arqByte.close();
            arq.close();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getLocalizedMessage());
        }

        sc.close();
    }
}
