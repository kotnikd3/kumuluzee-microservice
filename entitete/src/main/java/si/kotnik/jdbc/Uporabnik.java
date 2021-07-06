package si.kotnik.jdbc;

public class Uporabnik extends Entiteta {
    private String ime;
    private String priimek;
    private String uporabniskoime;

    public Uporabnik(String ime, String priimek, String uporabniskoime) {
        this.ime = ime;
        this.priimek = priimek;
        this.uporabniskoime = uporabniskoime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getUporabniskoime() {
        return uporabniskoime;
    }

    public void setUporabniskoime(String uporabniskoime) {
        this.uporabniskoime = uporabniskoime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ime:" + ime);
        sb.append(" Priimek:" + priimek);
        sb.append(" Uporabnisko ime:" + uporabniskoime);
        return sb.toString();
    }
}
