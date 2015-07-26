package lt.vkk.tvarkarastis.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by alius on 2015.07.21.
 */
@Table(name = "Grupe")
public class Grupe extends Model {
    @Column(name = "remote_id", index = true)
    public int remoteId;
    @Column(name = "Pavadinimas", index = true)
    public String pavadinimas;

    public Grupe() {
        super();
    }

    public Grupe(int remoteId, String pavadinimas) {
        super();
        this.remoteId = remoteId;
        this.pavadinimas = pavadinimas;
    }

    public static Grupe getSelected(int remoteId) {
        // This is how you execute a query
        return new Select()
                .from(Grupe.class)
                .where("remote_id = ?", remoteId)
                .executeSingle();
    }

    public static List<Grupe> getAllList() {
        return new Select()
                .from(Grupe.class)
                .orderBy("lower(Pavadinimas) ASC")
                .execute();
    }

    @Override
    public String toString() {
        return "remote_id: " + remoteId + ", pavadinimas: " + pavadinimas;
    }
}
