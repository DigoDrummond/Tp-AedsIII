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
                dos.writeBoolean(false);
                dos.writeShort(ba.length);
                dos.write(ba);
            }

            dos.close();
            arqByte.close();
            arq.close();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getLocalizedMessage());
        }
        
        System.out.println("#--------------- MENU ---------------#");
        System.out.println(" Escolha uma das opções: ");
        System.out.println("1) Adicionar novo registro na base de dados.\n2) Ler registro da base.\n3) Atualizar registro.\n4) Deletar registro. ");
        
        switch (sc.nextInt()) {
            case 1:
                System.out.println("#------------------------------------#\nAdicionar novo registro na base de dados.");
                create();

            case 2:
                System.out.println("#------------------------------------#\nLer registro da base.");
                read();

            case 3:
                System.out.println("#------------------------------------#\nAtualizar registro.");
                update();

            case 4:
                System.out.println("#------------------------------------#\nDeletar registro.");
                delete();
        
            default:
                break;
        }




        sc.close();
    }

    public static void create(){
        
    }

    public static void read(){

    }

    public static void update(){

    }

    public static void delete(){

    }

}
