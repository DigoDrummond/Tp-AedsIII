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
        String[] dados = linha.split(";");
        for(int i=0;i<dados.length;i++){
            if(dados[i] == ""){
                dados[i] = "não informado";
            }
        }

        String data[] = dados[4].split(" ");
        data[1].replace(",", "");
        if(data[1].length() == 1){
            data[1] = "0" + data[1];
        }

        switch (data[0]) {
            case "January":
                data[0] = "01";
                break;
        
            case "February":
                data[0] = "02";
                break;

            case "March":
                data[0] = "03";
                break;
            
            case "April":
                data[0] = "04";
                break;
            
            case "May":
                data[0] = "05";
                break;
            
            case "June":
                data[0] = "06";
                break;

            case "July":
                data[0] = "07";
                break;

            case "August":
                data[0] = "08";
                break;

            case "September":
                data[0] = "09";
                break;

            case "October":
                data[0] = "10";
                break;

            case "November":
                data[0] = "11";
                break;

            case "December":
                data[0] = "12";
                break;
            
            default:
                break;
        }

        String dataFormatada = data[1] + "-" + data[0] + "-" + data[2];
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dataFormatada2 = new Date();
        long unixTime = 0;
        try {
            dataFormatada2 = sdf.parse(dataFormatada);
            unixTime = dataFormatada2.getTime();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setId(Integer.parseInt(dados[0]));
        setType(dados[1]);
        setTitle(dados[2]);
        setDirector(dados[3]);
        setDate(unixTime);
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
            while (arq.ready()) {
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
