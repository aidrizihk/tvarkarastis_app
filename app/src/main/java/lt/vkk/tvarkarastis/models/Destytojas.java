package lt.vkk.tvarkarastis.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by alius on 2015.07.21.
 */
@Table(name = "Destytojas")
public class Destytojas extends Model {
    @Column(name = "remote_id", index = true)
    public int remoteId;
    @Column(name = "Vardas", index = true)
    public String vardas;
    @Column(name = "Pavarde", index = true)
    public String pavarde;

    public Destytojas() {
        super();
    }

    public static Destytojas getSelected(int remoteId) {
        return new Select()
                .from(Destytojas.class)
                .where("remote_id = ?", remoteId)
                .executeSingle();
    }

    public static List<Destytojas> getAllList() {
        return new Select()
                .from(Destytojas.class)
                .orderBy("Pavarde COLLATE NOCASE, Vardas COLLATE NOCASE")
                .execute();
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
    }
}
