import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TP {
    public static short ultimoId = 8810;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        FileOutputStream arqByte;
        DataOutputStream dos;

        // Escrevendo dados do .csv no arquivo .db
        try {
            FileReader fileReader = new FileReader("netflix.csv");
            BufferedReader arq = new BufferedReader(fileReader);

            arqByte = new FileOutputStream("data.db");
            dos = new DataOutputStream(arqByte);
            byte[] ba;

            arq.readLine();
            dos.writeInt(0);
            int idFinal = 0;
            while (arq.ready()) {
                Netflix programa = new Netflix();
                programa.ler(arq.readLine());

                ba = programa.toByteArray();
                dos.writeBoolean(false);
                dos.writeShort(ba.length);
                dos.write(ba);
                idFinal = programa.getId();
            }
            RandomAccessFile start = new RandomAccessFile("data.db", "rw");
            start.seek(0);
            start.writeInt(idFinal);
            start.close();

            dos.close();
            arqByte.close();
            arq.close();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getLocalizedMessage());
        }

        menu();
        sc.close();
    }

    public static void menu() throws Exception{
        Scanner sc = new Scanner(System.in);

        System.out.println("#--------------- MENU ---------------#");
        System.out.println(" Escolha uma das opções: ");
        System.out.println(
                "1) Adicionar novo registro na base de dados\n2) Ler registro da base\n3) Atualizar registro\n4) Deletar registro\n5) Sair\n");

        switch (sc.nextInt()) {
            case 1:
                System.out.println("\n#------------------------------------#\nAdicionar novo registro na base de dados");
                sc.nextLine();
                String type;
                while(true){
                    System.out.print("Tipo(TV Show / Movie): ");
                    type = sc.nextLine();
                    if(type.equals("TV Show") || type.equals("Movie"))
                        break;
                }
                System.out.print("Nome: ");
                String title = sc.nextLine();
                System.out.print("Diretor: ");
                String director = sc.nextLine();
                System.out.print("Data de lançamento(dd/MM/yyyy): ");
                String data = sc.nextLine();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dataFormatada = new Date();
                long unixTime = 0;
                try {
                    dataFormatada = sdf.parse(data);
                    unixTime = dataFormatada.getTime();
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }

                char[] tipo = new char[7];
                for (int i = 0; i < type.length(); i++) {
                    tipo[i] = type.charAt(i);
                }
                Netflix novo = new Netflix(tipo, title, director, unixTime);
                
                create(novo);
                menu();
                break;

            case 2:
                System.out.println("\n#------------------------------------#\nLer registro da base");
                System.out.print("Digite o id da série/filme que você deseja buscar na base de dados: ");
                try{
                    int id = sc.nextInt();
                    read(id);
                }catch(Exception e){
                    System.out.println("ID inválido.");
                }
                menu();
                break;

            case 3:
                System.out.println("\n#------------------------------------#\nAtualizar registro.");
                update();
                menu();
                break;

            case 4:
                System.out.println("\n#------------------------------------#\nDeletar registro.");
                System.out.println("Digite o id do registro que você deseja deletar: ");
                try {
                    int id = sc.nextInt();
                    delete(id);
                } catch (Exception e) {
                    System.out.println("ID inválido.");
                }
                menu();
                break;
            
            case 5:
                System.out.println("\n#------------------------------------#\nPrograma encerrado.");
                System.out.println("#------------------------------------#\n");
                System.exit(0);
                break;

            default:
                System.out.println("Opção inválida.");
                menu();
                break;
        }
        sc.close();
    }

    public static void create(Netflix netflix) throws Exception{
        RandomAccessFile arq;
        try {
            arq = new RandomAccessFile("data.db", "rw");
            byte[] ba;
            arq.seek(arq.length());
            ultimoId++;
            netflix.setId(ultimoId);
            ba = netflix.toByteArray();
            arq.writeBoolean(false);
            arq.writeShort(ba.length);
            arq.write(ba);
            
            arq.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void read(int id) throws Exception {
        RandomAccessFile arq;
        try {
            arq = new RandomAccessFile("data.db", "rw");
            arq.seek(4);
            long ptr = arq.getFilePointer();
            boolean lapide;
            boolean idValido = false;

            while(arq.getFilePointer() < arq.length()){
                lapide = arq.readBoolean();
                short tam = arq.readShort();
                ptr+=3;
                if(lapide){
                    ptr+=tam;
                    arq.seek(ptr);
                } else {
                    int idArq = arq.readInt();
                    if(idArq == id){
                        arq.seek(ptr);
                        byte[] ba = new byte[tam];
                        arq.read(ba);
                        Netflix programa = new Netflix();
                        programa.fromByteArray(ba);
                        System.out.println(programa.toString());
                        arq.seek(arq.length());
                        idValido = true;
                    } else {
                        ptr+=tam;
                        arq.seek(ptr);
                    }
                }
            }

            if(idValido == false){
                System.out.println("\nID não encontrado.\n");
            }

            arq.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void update() {

    }

    public static void delete(int id) throws Exception {
        
    }


}
