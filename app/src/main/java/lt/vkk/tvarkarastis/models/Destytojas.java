package lt.vkk.tvarkarastis.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by alius on 2015.07.21.
 */
@Table(name = "Destytojas", id = "remoteId")
public class Destytojas extends Model {
    public int remoteId;
    @Column(name = "Vardas", index = true)
    public String vardas;
    @Column(name = "Pavarde", index = true)
    public String pavarde;

    public Destytojas() {
        super();
    }

    public Destytojas(int remoteId, String vardas, String pavarde) {
        super();
        this.remoteId = remoteId;
        this.vardas = vardas;
        this.pavarde = pavarde;
    }

    public static Destytojas getSelected(int remoteId) {
        // This is how you execute a query
        return new Select()
                .from(Destytojas.class)
                .where("remoteId = ?", remoteId)
                .executeSingle();
    }

    public static List<Destytojas> getAllList() {
        return new Select()
                .from(Destytojas.class)
                .orderBy("Pavarde COLLATE NOCASE, Vardas COLLATE NOCASE")
                .execute();
    }

}
