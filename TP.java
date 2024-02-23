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

        //Escrevendo dados do .csv no arquivo .db
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
        
        System.out.println("#--------------- MENU ---------------#");
        System.out.println(" Selecione uma das opções: ");
        System.out.println("1)Adicionar novo registro na base de dados.\n2)Ler registro da base.\n3)Atualizar registro.\n4)Deletar registro. ");
        





        sc.close();
    }
    /* 
    public static void create(Netflix netflix){

    }

    public static Netflix read(int id){

    }

    public static boolean update(Netflix netflix){

    }

    public static void delete(int id){

    }
    */

}
