import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Netflix {
    private int id;
    private String type;
    private String title;
    private String director;
    private long date;

    public Netflix() {

    }

    public Netflix(int i, String t, String n, String d, long a) {
        this.id = i;
        this.type = t;
        this.title = n;
        this.director = d;
        this.date = a;

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

    public long getDate() {
        return date;
    }

    public void setDate(long a) {
        this.date = a;
    }

    public String toString() {
        return "\nID: " + id +
                "\nNome: " + title +
                "\nTipo: " + type +
                "\nDiretor: " + director +
                "\nData: " + date;
    }

    public void ler(String linha) {
        int j = 0;
		char c = ';';
		int tmp = 0;
		String vetorStr[] = new String[5];
		for(int i=0; i<linha.length(); i++){
			if(linha.charAt(i) == c) {
				vetorStr[j] =linha.substring(tmp, i);
				tmp = i+1;
				if(vetorStr[j].intern() == ""){
					vetorStr[j] = "nao informado";
				}
				j++;
			}
		}
		vetorStr[4] =linha.substring(tmp, linha.length());
        if(vetorStr[4].intern() == ""){
            setDate(0);
		} else {
            String data = formatarData(vetorStr[4]);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataFormatada = new Date();
            long unixTime = 0;
            try{
                dataFormatada = sdf.parse(data);
                unixTime = dataFormatada.getTime();
            }catch(Exception e){
                e.printStackTrace();
                e.getMessage();
            }
            setDate(unixTime);
        }
        
        setId(Integer.parseInt(vetorStr[0]));
        setType(vetorStr[1]);
        setTitle(vetorStr[2]);
        setDirector(vetorStr[3]);

    }

    private static String formatarData(String dataOriginal) {
        String[] partes = dataOriginal.split(" ");
        if(partes[0].equals("")){
            partes[0] = partes[1];
            partes[1] = partes[2];
            partes[2] = partes[3];
            partes[3] = null;
        }
        String mes = partes[0];
        String dia = partes[1].replace(",", ""); // Remove a vírgula do dia
        String ano = partes[2];
        
        // Convertendo o mês para o formato numérico (assumindo inglês para português)
        switch (mes) {
            case "January":
                mes = "01";
                break;
            case "February":
                mes = "02";
                break;
            case "March":
                mes = "03";
                break;
            case "April":
                mes = "04";
                break;
            case "May":
                mes = "05";
                break;
            case "June":
                mes = "06";
                break;
            case "July":
                mes = "07";
                break;
            case "August":
                mes = "08";
                break;
            case "September":
                mes = "09";
                break;
            case "October":
                mes = "10";
                break;
            case "November":
                mes = "11";
                break;
            case "December":
                mes = "12";
                break;
            default:
                // Se o mês não estiver em inglês, retornar a data original
                return dataOriginal;
        }
        
        return dia + "/" + mes + "/" + ano;
    }
    
    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(id);
        dos.writeUTF(type);
        dos.writeUTF(title);
        dos.writeUTF(director);
        dos.writeLong(date);

        return baos.toByteArray();
    }
    
    // public void fromByteArray(byte ba[]) throws IOException{
    //     ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    //     DataInputStream dis = new DataInputStream(bais);

    //     idJogador=dis.readInt();
    //     nome=dis.readUTF();
    //     pontos=dis.readFloat();

    // }

}

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
