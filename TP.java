import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TP {
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

            arq.readLine();// le primeira linha do .csv que tem nomes dos atributos
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

    //função menu
    public static void menu() throws Exception{
        Scanner sc = new Scanner(System.in);

        System.out.println("#--------------- MENU ---------------#");
        System.out.print(
                "\n1) Adicionar novo registro na base de dados\n2) Ler registro da base\n3) Atualizar registro\n4) Deletar registro\n5) Sair\n Opção: ");

        switch (sc.nextInt()) {
            case 1:
                //Montando o objeto Netflix para enviar para a função create
                System.out.println("\n#------------------------------------#\nAdicionar novo registro na base de dados");
                sc.nextLine();
                String type;
                int selecao;
                //permite que usuário escolha somente uma das duas opções
                while(true){
                    System.out.print("Selecione o tipo [ TV Show(1) / Movie(2) ]: ");
                    selecao = sc.nextInt();
                    if(selecao == 1 || selecao == 2){
                        break;
                    } else {
                        System.out.println("Opção inválida.");
                    }
                }
                if(selecao == 1){
                    type = "TV Show";
                } else {
                    type = "Movie";
                }
                sc.nextLine();//pega \n entre leitura de int e string
                System.out.print("Titulo: ");
                String title = sc.nextLine();
                System.out.print("Diretor: ");
                String director = sc.nextLine();
                System.out.print("Data de lançamento(dd/MM/yyyy): ");
                // le data 
                String data = sc.nextLine();
                //formata data digitada
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dataFormatada = new Date();
                //trasforma data em long para ser armazenado no objeto
                long unixTime = 0;
                dataFormatada = sdf.parse(data);
                unixTime = dataFormatada.getTime();
                
                //coloca atributo tipo em vetor de tamanho fixo 
                char[] tipo = new char[7];
                for (int i = 0; i < type.length(); i++) {
                    tipo[i] = type.charAt(i);
                }
                //cria novo objeto Netflix
                Netflix novo = new Netflix(tipo, title, director, unixTime);
                
                //Chamando a função create com o objeto montado
                create(novo);
                menu();
                break;

            case 2:
                System.out.println("\n#------------------------------------#\nLer registro da base");
                System.out.print("Digite o id da série/filme que você deseja buscar na base de dados: ");
                    int id = lerInteiro(sc);
                    read(id);
                
                menu();
                break;

            case 3:
                System.out.println("\n#------------------------------------#\nAtualizar registro.");
                update();
                menu();
                break;

            case 4:
                System.out.println("\n#------------------------------------#\nDeletar registro.");
                System.out.print("Digite o id do registro que você deseja deletar: ");
                    id = lerInteiro(sc);
                    delete(id);
               
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
            arq.seek(0);
            int ultimoId = arq.readInt();
            ultimoId++;
            arq.seek(0);
            arq.writeInt(ultimoId);
            netflix.setId(ultimoId);
            
            arq.seek(arq.length());
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
            //move ponteiro do arquivo para priemeiro registro, pulando byts de registro do último id registrado
            arq.seek(4);
            long ptr = arq.getFilePointer();
            boolean idValido = false;

            while(arq.getFilePointer() < arq.length()){
                boolean lapide = arq.readBoolean();
                short tam = arq.readShort();
                ptr+=3;
                 //se for lapide pega tamanho do registro e pula para próximo
                if(lapide){
                    ptr+=tam;
                    arq.seek(ptr);
                } else {
                    int idArq = arq.readInt();
                    //se id for encontrado
                    if(idArq == id){
                        arq.seek(ptr); //move ponteiro para registro correto
                        byte[] ba = new byte[tam];
                        arq.read(ba);
                        Netflix programa = new Netflix();
                        programa.fromByteArray(ba);
                        System.out.println(programa.toString());
                        arq.seek(arq.length());
                        idValido = true;
                     // se não for igual ao procurado
                    } else {
                        ptr+=tam;
                        arq.seek(ptr);
                    }
                }
            }
            //se id procurado não for encontrado
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
        RandomAccessFile arq;
        try {
            arq = new RandomAccessFile("data.db", "rw");
            //move ponteiro do arquivo para priemeiro registro, pulando byts de registro do último id registrado
            arq.seek(4);
            long ptr = arq.getFilePointer();
            boolean idValido = false;

            while(arq.getFilePointer() < arq.length()){
                boolean lapide = arq.readBoolean();
                short tam = arq.readShort();
                ptr+=3;
                //se for lapide pega tamanho do registro e pula para próximo
                if(lapide){
                    ptr+=tam;
                    arq.seek(ptr);
                //se não for lápide
                } else {
                    int idArq = arq.readInt();
                    //se id for encontrado
                    if(idArq == id){
                        ptr-=3;
                        arq.seek(ptr);//move ponteiro para inicio do registro
                        arq.writeBoolean(true);
                        arq.seek(arq.length());
                        idValido = true;
                        System.out.println("\nRegistro deletado com sucesso.\n");
                    // se não for igual ao procurado
                    } else {
                        ptr+=tam;
                        arq.seek(ptr);
                    }
                }
            }
            //caso não encontre o id procurado
            if(idValido == false){
                System.out.println("\nID não encontrado.\n");
            }

            arq.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //função interna do programa para verificar se id digitado pelo usuário é valido
    //prende em looping até digitar valor válido
    private static int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Isso não é um número inteiro.");
            System.out.print("Insira um valor válido: ");
            scanner.next(); // Consume o valor não inteiro para evitar um loop infinito
        }
        return scanner.nextInt();
    }


}
