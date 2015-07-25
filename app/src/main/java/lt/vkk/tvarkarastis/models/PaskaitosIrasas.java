package lt.vkk.tvarkarastis.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by alius on 2015.07.21.
 */
@Table(name = "PaskaitosIrasas", id = "remoteId")
public class PaskaitosIrasas extends Model {
    public int remoteId;
    @Column(name = "savDiena", index = true)
    public int savDiena;
    @Column(name = "Pradzia", index = true)
    public String pradzia;
    @Column(name = "Pabaiga", index = true)
    public String pabaiga;
    @Column(index = true)
    public Grupe grupe;
    @Column(name = "Dalykas", index = true)
    public String dalykas;
    @Column(index = true)
    public Destytojas destytojas;
    @Column(name = "Auditorija", index = true)
    public String auditorija;
    public int pogrupis;
    public int pasikatojamumas;
    public int pasirenkamasis;

    public PaskaitosIrasas() {
        super();
    }

    public PaskaitosIrasas(int remoteId, int savDiena, String pradzia, String pabaiga, Grupe grupe,
                           String dalykas, Destytojas destytojas, String auditorija, int pogrupis,
                           int pasikatojamumas, int pasirenkamasis) {
        super();
        this.remoteId = remoteId;
        this.savDiena = savDiena;
        this.pradzia = pradzia;
        this.pabaiga = pabaiga;
        this.grupe = grupe;
        this.dalykas = dalykas;
        this.destytojas = destytojas;
        this.auditorija = auditorija;
        this.pogrupis = pogrupis;
        this.pasikatojamumas = pasikatojamumas;
        this.pasirenkamasis = pasirenkamasis;
    }

    public static List<PaskaitosIrasas> getAll() {
        // This is how you execute a query
        return new Select()
                .from(PaskaitosIrasas.class)
                .orderBy("remoteId ASC")
                .execute();
    }

    public static List<PaskaitosIrasas> getAllbyGrupe(Grupe grupe) {
        // This is how you execute a query
        return new Select()
                .from(PaskaitosIrasas.class)
                .where("grupe = ?", grupe.getId())
                .orderBy("remoteId ASC")
                .execute();
    }

    public static List<PaskaitosIrasas> getAllbyGrupe(int grupe, int savDiena) {
        // This is how you execute a query
        return new Select()
                .from(PaskaitosIrasas.class)
                .where("grupe = ? AND savDiena = ?", grupe, savDiena)
                .orderBy("remoteId ASC")
                .execute();
    }
}
