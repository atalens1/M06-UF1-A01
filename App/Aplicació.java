package App;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
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
                    AfegirEncarrec(reader1);

                    break;

                case "2":
                    System.out.println("opcio triada és 2");

                    break;
                
                case "3":

                    break;

            
                default:
                    System.out.println("Opció no vàlida");
                    ValidOpt = false;
            }

            System.out.println("opcio: "+ opcio);

            if (!(opcio.equals("3"))) {

                String continuar = "";

                if (ValidOpt) {
                    System.out.println("Vols fer cap altra acció? Indicar S en cas afirmatiu");
                    continuar = reader1.readLine(); 
                }

                if ((continuar.equals("S")) || (!(ValidOpt))) {
                    
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

    public static void AfegirEncarrec(BufferedReader reader) throws IOException {

        System.out.println("Introdueix les dades de l'encarrec: ");

        System.out.println("Nom del client: ");
        String nomCli = reader.readLine();

        System.out.println("Telèfon del client: ");
        String telCli = reader.readLine();

        System.out.println("Per quin dia el vols preparat (Dia (DD)/ Mes (MM) / Any (AAAA)?: ");
        String dataEncarrec = reader.readLine();

        ArrayList<Article> articles = new ArrayList<>();

        boolean addArticles = true;

        while (addArticles) {

            System.out.println("Quin article vols afegir?: ");
            String nomArticle = reader.readLine();
    
            System.out.println("Quantitat: ");

            float quantitat = 0;

            try {

                 quantitat = Float.parseFloat(reader.readLine());
                
            } catch (Exception e) {
                System.out.println("Format de nombre no és vàlid");
            } 
            
    
            System.out.println("Quina unitat (indicar Kg, g, ... o simplement u per unitats)? :");
            String unitat = reader.readLine();
    
            Article article = new Article(quantitat, unitat, nomArticle);
    
            articles.add(article);

            System.out.println("Vols introduir cap article més? Si (S) per més afegir més: ");

            if (!(reader.readLine().equals("S"))) {
                addArticles = false;
            }

        }


        String fileName = "C:\\Users\\accesadades\\" + "encarrecs_client_" + nomCli + "_"+ System.currentTimeMillis() + ".csv";

        System.out.println("En quin format vols escriure el fitxer?: ");
        System.out.println("1. text albarà");
        System.out.println("2. csv una línia");
        System.out.println("3. Binari");

        String tipusFich = reader.readLine();

        switch (tipusFich) {
            case "1":
                TextMultiLinea(nomCli,telCli,dataEncarrec,articles,fileName);
                break;
            
            case "2": 
                // Encarrec encarrec = new Encarrec(nomCli, telCli, dataEncarrec, articles);
                // csvLineaObjEn(encarrec, fileName);
                csvLinea(nomCli,telCli,dataEncarrec,articles,fileName);
                break;

            case "3":

                binari(nomCli,telCli,dataEncarrec,articles,fileName);
                break;
        
            default:
                break;
        }

    }

    public static void TextMultiLinea(String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles, String fileName) {

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
        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName))){

            bw1.write(encarrec.toCSV());
            
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    public static void binari (String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles, String fileName) {

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
