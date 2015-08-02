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
    private String pavadinimas;

    public Grupe() {
        super();
    }

    public static Grupe getSelected(int remoteId) {
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
        return "remote_id: " + remoteId + ", pavadinimas: " + getPavadinimas();
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }
    }
