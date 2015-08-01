package lt.vkk.tvarkarastis.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by alius on 2015.07.21.
 */
@Table(name = "PaskaitosIrasas")
public class PaskaitosIrasas extends Model implements Parcelable {
    @Column(name = "remote_id", index = true)
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

    protected PaskaitosIrasas(Parcel in) {
        remoteId = in.readInt();
        savDiena = in.readInt();
        pradzia = in.readString();
        pabaiga = in.readString();
        dalykas = in.readString();
        auditorija = in.readString();
        pogrupis = in.readInt();
        pasikatojamumas = in.readInt();
        pasirenkamasis = in.readInt();
    }

    public static final Creator<PaskaitosIrasas> CREATOR = new Creator<PaskaitosIrasas>() {
        @Override
        public PaskaitosIrasas createFromParcel(Parcel in) {
            return new PaskaitosIrasas(in);
        }

        @Override
        public PaskaitosIrasas[] newArray(int size) {
            return new PaskaitosIrasas[size];
        }
    };

    public static List<PaskaitosIrasas> getAllList() {
        // This is how you execute a query
        return new Select()
                .from(PaskaitosIrasas.class)
                .orderBy("remote_id ASC")
                .execute();
    }

    public static List<PaskaitosIrasas> getAllbyGrupe(Grupe grupe) {
        // This is how you execute a query
        return new Select()
                .from(PaskaitosIrasas.class)
                .where("grupe = ?", grupe.getId())
                .orderBy("remote_id ASC")
                .execute();
    }

    public static List<PaskaitosIrasas> getAllbyGrupe(int grupe, int savDiena) {
        // This is how you execute a query
        return new Select()
                .from(PaskaitosIrasas.class)
                .where("grupe = ? AND savDiena = ?", grupe, savDiena)
                .orderBy("remote_id ASC")
                .execute();
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }

    public void setSavDiena(int savDiena) {
        this.savDiena = savDiena;
    }

    public void setPradzia(String pradzia) {
        this.pradzia = pradzia;
    }

    public void setPabaiga(String pabaiga) {
        this.pabaiga = pabaiga;
    }

    public void setGrupe(Grupe grupe) {
        this.grupe = grupe;
    }

    public void setDalykas(String dalykas) {
        this.dalykas = dalykas;
    }

    public void setDestytojas(Destytojas destytojas) {
        this.destytojas = destytojas;
    }

    public void setAuditorija(String auditorija) {
        this.auditorija = auditorija;
    }

    public void setPogrupis(int pogrupis) {
        this.pogrupis = pogrupis;
    }

    public void setPasikatojamumas(int pasikatojamumas) {
        this.pasikatojamumas = pasikatojamumas;
    }

    public void setPasirenkamasis(int pasirenkamasis) {
        this.pasirenkamasis = pasirenkamasis;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public int getSavDiena() {
        return savDiena;
    }

    public String getPradzia() {
        return pradzia;
    }

    public String getPabaiga() {
        return pabaiga;
    }

    public Grupe getGrupe() {
        return grupe;
    }

    public String getDalykas() {
        return dalykas;
    }

    public Destytojas getDestytojas() {
        return destytojas;
    }

    public String getAuditorija() {
        return auditorija;
    }

    public int getPogrupis() {
        return pogrupis;
    }

    public int getPasikatojamumas() {
        return pasikatojamumas;
    }

    public int getPasirenkamasis() {
        return pasirenkamasis;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(remoteId);
        dest.writeInt(savDiena);
        dest.writeString(pradzia);
        dest.writeString(pabaiga);
        dest.writeString(dalykas);
        dest.writeString(auditorija);
        dest.writeInt(pogrupis);
        dest.writeInt(pasikatojamumas);
        dest.writeInt(pasirenkamasis);
    }
}
