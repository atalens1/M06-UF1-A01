package App;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Model.Article;
import Model.Encarrec;

public class Aplicació {


    public static void main(String[] args) {

        System.out.println("GESTIO D'ENCARRECS");
        System.out.println("======================");

        MainMenu();

        DemanarOpcio();

        
    }

    public static void MainMenu() {

        System.out.println("Quina de les següents opcions vols fer?");
        System.out.println("");
        System.out.println("1. Afegir nous encàrrecs");
        System.out.println("2. Mostrar per pantalla els encàrrecs");
        System.out.println("3. Sortir");
        System.out.println("");
    }

    public static void DemanarOpcio() {

        boolean ValidOpt = true;
        
        try (BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in))) {
            
            String opcio = reader1.readLine();

            switch (opcio) {
                case "1":
                    System.out.println("opcio triada és 1");
                    AfegirDadesEncarrec(reader1);
                    break;

                case "2":
                    System.out.println("opcio triada és 2");
                    MostrarEncarrec(reader1);
                    break;
                
                case "3":

                    break;

            
                default:
                    System.out.println("Opció no vàlida");
                    ValidOpt = false;
            }

            if (!(opcio.equals("3"))) {

                String continuar = "";

                if (ValidOpt) {
                    System.out.println("Vols fer cap altra acció? Indicar S en cas afirmatiu");
                    continuar = reader1.readLine(); 
                }

                if ((continuar.matches("[Ss]")) || (!(ValidOpt))) {
                    
                    MainMenu();

                    DemanarOpcio();
                } else {
                    System.out.println("Que tinguis un bon dia!");
                }

            } else {
                System.out.println("Que tinguis un bon dia!");
            }



        } catch (IOException e) {

            e.printStackTrace();
        } 
    }

    public static void AfegirDadesEncarrec(BufferedReader reader) throws IOException {

        System.out.println("Introdueix les dades de l'encarrec: ");

        System.out.println("Nom del client: ");
        String nomCli = reader.readLine();

        System.out.println("Telèfon del client: ");
        String telCli = reader.readLine();

        System.out.println("Per quin dia el vols preparat (Dia (DD)/ Mes (MM) / Any (AAAA)?: ");
        String dataEncarrec = reader.readLine();

//Cridem a la classe encarregada de gestionar els articles dels encàrrecs        
        GestioArticle articleList = new GestioArticle();

//Cridem al mètode de la classe que demana les dades per poder afegir els articles
        ArrayList<Article> articles = articleList.AfegirArticles(reader);

//Fem l'escriptura als fitxers
        EscriureFitxers(reader, nomCli, telCli, dataEncarrec, articles);

    }

    public static void EscriureFitxers(BufferedReader reader, String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles) 
        throws IOException {

        String extensio = "";

        String fileName = "C:\\Users\\accesadades\\" + "encarrecs_client_" + nomCli + "_"+ System.currentTimeMillis() + extensio;

        System.out.println("En quin format vols escriure el fitxer?: ");
        System.out.println("1. text albarà");
        System.out.println("2. csv una línia");
        System.out.println("3. Binari");

        String tipusFich = reader.readLine();

        switch (tipusFich) {
            case "1":
                extensio = ".txt";
                fileName = fileName.concat(extensio);
                TextMultiLinea(nomCli,telCli,dataEncarrec,articles,fileName);
                break;
            
            case "2": 
                // Encarrec encarrec = new Encarrec(nomCli, telCli, dataEncarrec, articles);
                // csvLineaObjEn(encarrec, fileName);
                extensio = ".csv";
                fileName = fileName.concat(extensio);
                csvLinea(nomCli,telCli,dataEncarrec,articles,fileName);
                break;

            case "3":
                extensio = ".dat";
                fileName = fileName.concat(extensio);
                binari(nomCli,telCli,dataEncarrec,articles,fileName);
                break;
        
            default:
                break;
        }

    }

    public static void MostrarEncarrec(BufferedReader reader) throws IOException {

        String folder = "C:\\Users\\accesadades\\";

        System.out.println("Quin tipus de fitxer voleu obrir?");
        System.out.println("1. Fitxer .csv");
        System.out.println("2. Fitxer binari .dat");

        String opcio = reader.readLine();
        
        System.out.println("Especifiqueu el nom del fitxer (i sols el nom) que voleu obrir sense la seva extensió");
        System.out.println("Assegureu que el fitxer està a la carpeta: " + folder);

        String fileName = reader.readLine();

        if (opcio.equals("1")) {
            FormatCSV(folder, fileName);
        } else if (opcio.equals("2")) {
            FormatBinari(folder, fileName);
        }

    }

    public static void FormatCSV(String folder, String fileName) {
        String filInputName = folder + fileName + ".csv";

        try (BufferedReader bwfilInp = new BufferedReader(new FileReader(filInputName))) {

            String linea = "";
            String[] contingut;

            while ((linea = bwfilInp.readLine()) != null){

                contingut = linea.split(";", 0);
                String nomCli = contingut[0];
                String telCli = contingut[1];
                String dataEncarrec = contingut[2];

                ArrayList<Article> articles = new ArrayList<>();

                int j = 3;

                while (j < contingut.length) {
                    Article a1 = new Article();
                    a1.setNomArticle(contingut[j]);
                    j++;
                    a1.setnombreUnitats(Float.parseFloat(contingut[j]));
                    j++;
                    a1.settipusUnitat(contingut[j]);
                    articles.add(a1);
                    j++;
                }

                FormatEncarrec(nomCli, telCli, dataEncarrec,articles);
            } 

        } catch (FileNotFoundException fn) {
                System.out.println ("No es troba el fitxer");         
        } catch (IOException io) {
        System.out.println ("Error d'E/S"); 
        }
    }

    public static void FormatBinari(String folder, String fileName) {

        String filInputName = folder + fileName + ".dat";
        ArrayList<Article> articles = new ArrayList<>();

        try (DataInputStream diStr1 = new DataInputStream(new FileInputStream(filInputName))) {

            String nomCli = diStr1.readUTF();
            String telCli = diStr1.readUTF();
            String dataEncarrec = diStr1.readUTF();

            try{

                while (diStr1.available()>0) {
                    String nomArticle = diStr1.readUTF();
                    float quantitat = diStr1.readFloat();
                    String unitat = diStr1.readUTF();
                    Article art = new Article(nomArticle,quantitat,unitat);
                    articles.add(art);   
                }

                FormatEncarrec(nomCli, telCli, dataEncarrec,articles);

            } catch (EOFException e) {
                System.out.println("Final de fitxer");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void FormatEncarrec(String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles) {
        System.out.printf(String.format("%n"));
        System.out.println("DETALL DE L'ENCARREC");
        System.out.println("====================================================");
        System.out.printf(String.format("%n"));
        System.out.println("Nom del client: " + nomCli);
        System.out.println("Telefon del client: " + telCli);
        System.out.println("Data de l'encarrec: " + dataEncarrec);
        System.out.printf(String.format("%n"));
        System.out.printf(String.format("%-15s %-10s %-15s%n", "Quantitat","Unitats","Article"));
        System.out.printf(String.valueOf("=").repeat(15)+" " +String.valueOf("=").repeat(10)+" "+String.valueOf("=").repeat(15));
        for (Article article:articles) {
            System.out.printf(String.format("%n"));
            System.out.printf(String.format("%-15s %-10s %-15s%n",article.getnombreUnitats(),article.gettipusUnitat(),article.getNomArticle()));
        }
    }

    public static void TextMultiLinea(String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles, String fileName) {
    /* Aquest mètode s'encarrega d'agafar els detalls de l'encarrec i formatar-lo a un fitxer pla,
     * el qual no és un csv sinó una mena de comprovant pel client
     */

        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName))) {
            
            bw1.write("Nom del client: " + nomCli);
            bw1.newLine();
            bw1.write("Telefon del client: " + telCli);
            bw1.newLine();
            bw1.write("Data de l'encarrec: " + dataEncarrec);
            bw1.newLine();
            bw1.write(String.format("%-15s %-10s %-15s%n", "Quantitat","Unitats","Article"));
            bw1.write(String.valueOf("=").repeat(15)+" " +String.valueOf("=").repeat(10)+" "+String.valueOf("=").repeat(15));
            bw1.newLine();
            for (Article article:articles) {
                bw1.write(String.format("%-15s %-10s %-15s%n",article.getnombreUnitats(),article.gettipusUnitat(),article.getNomArticle()));
                bw1.newLine();
            }

        } catch (Exception e) {
            System.out.println("Error");
        } 

    }

    public static void csvLinea(String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles, String fileName) {
    /* Aquest mètode s'encarrega d'agafar els detalls de l'encarrec i formatar-lo a un fitxer pla separat per comes (csv)
     */
        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName))){

            String csvArticles = "";

            String csvLinea = nomCli + ";" + telCli + ";" + dataEncarrec + ";";

            for (Article article : articles) {
                csvArticles =  csvArticles + 
                            article.getNomArticle() + ";" + 
                            article.getnombreUnitats() + ";" +
                            article.gettipusUnitat() + ";" ;
            }

            bw1.write(csvLinea + csvArticles);

            
        } catch (Exception e) {
            System.out.println("Error");
        }

    }

    public static void csvLineaObjEn(Encarrec encarrec, String fileName) {
    /* Aquest mètode és una alternativa per si es fa servir una classe anomenada Encarrec, la qual 
     * fa servir la classe Article
     */
        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName))){

            bw1.write(encarrec.toCSV());
            
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    public static void binari (String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles, String fileName) {
    /* Aquest mètode s'encarrega d'agafar els detalls de l'encarrec i formatar-lo a un fitxer binari
     */

        try (DataOutputStream ds1 = new DataOutputStream(new FileOutputStream(fileName))) {

            ds1.writeUTF(nomCli);
            ds1.writeUTF(telCli);
            ds1.writeUTF(dataEncarrec);
            
            for (Article article:articles) {
                ds1.writeUTF(article.getNomArticle());
                ds1.writeFloat(article.getnombreUnitats());
                ds1.writeUTF(article.gettipusUnitat());
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }
    
}
