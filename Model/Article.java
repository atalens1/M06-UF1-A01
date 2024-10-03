package Model;

public class Article {

    String nomArticle;
    float nombreUnitats;
    String tipusUnitat;


    public Article(float nombreUnitats, String tipusUnitat, String nomArticle) {
        this.nomArticle = nomArticle;
        this.nombreUnitats = nombreUnitats;
        this.tipusUnitat = tipusUnitat;

    }

    public float getnombreUnitats() {
        return nombreUnitats;
    
    }
    public void setnombreUnitats(float nombreUnitats) {
        this.nombreUnitats = nombreUnitats;
    }

    public String gettipusUnitat() {
        return tipusUnitat;
    }

    public void settipusUnitat(String tipusUnitat) {
        this.tipusUnitat = tipusUnitat;
    }

    public String getNomArticle() {
        return nomArticle;
    }
    
    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    @Override
    public String toString() {
        return "Article [article=" + nomArticle + ", nombreUnitats=" + nombreUnitats + ", tipusUnitat=" + tipusUnitat
                + "]";
    }

    public String toCSV() {
        return nomArticle + ";" + nombreUnitats + ";" + tipusUnitat + ";" ;
    }
    
}
