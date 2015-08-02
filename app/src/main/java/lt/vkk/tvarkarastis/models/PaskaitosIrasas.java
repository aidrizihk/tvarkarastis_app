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
    @Column(name = "remote_id", index = true)
    public int remoteId;
    @Column(name = "savDiena", index = true)
    public int savDiena;
    @Column(name = "Pradzia", index = true)
    public String pradzia;
    @Column(name = "Pabaiga", index = true)
    public String pabaiga;
    @Column(name = "Grupe_int", index = true)
    public int grupe_int;
    @Column(name = "Grupe", index = true)
    public Grupe grupe;
    @Column(name = "Dalykas", index = true)
    public String dalykas;
    @Column(name = "Destytojas_int", index = true)
    public int destytojas_int;
    @Column(name = "Destytojas", index = true)
    public Destytojas destytojas;
    @Column(name = "Auditorija", index = true)
    public String auditorija;
    public int pogrupis;
    public int pasikatojamumas;
    public int pasirenkamasis;

    public PaskaitosIrasas() {
        super();
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

    public static List<PaskaitosIrasas> getAllList() {
        return new Select()
                .from(PaskaitosIrasas.class)
                .orderBy("remote_id ASC")
                .execute();
    }

    public static List<PaskaitosIrasas> getAllbyGrupe(Grupe grupe) {
        return new Select()
                .from(PaskaitosIrasas.class)
                .where("grupe = ?", grupe.getId())
                .orderBy("remote_id ASC")
                .execute();
    }

    public static List<PaskaitosIrasas> getAllbyGrupe(int grupe, int savDiena) {
        return new Select()
                .from(PaskaitosIrasas.class)
                .where("Grupe_int = ? AND savDiena = ?", grupe, savDiena)
                .orderBy("remote_id ASC")
                .execute();
    }

    public static List<PaskaitosIrasas> getAllbyDestytojas(int destytojas, int savDiena) {
        return new Select()
                .from(PaskaitosIrasas.class)
                .where("Destytojas_int = ? AND savDiena = ?", destytojas, savDiena)
                .orderBy("remote_id ASC")
                .execute();
    }

    public int getGrupe_int() {
        return grupe_int;
    }

    public void setGrupe_int(int grupe_int) {
        this.grupe_int = grupe_int;
    }

    public int getDestytojas_int() {
        return destytojas_int;
    }

    public void setDestytojas_int(int destytojas_int) {
        this.destytojas_int = destytojas_int;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }

    public int getSavDiena() {
        return savDiena;
    }

    public void setSavDiena(int savDiena) {
        this.savDiena = savDiena;
    }

    public String getPradzia() {
        return pradzia;
    }

    public void setPradzia(String pradzia) {
        this.pradzia = pradzia;
    }

    public String getPabaiga() {
        return pabaiga;
    }

    public void setPabaiga(String pabaiga) {
        this.pabaiga = pabaiga;
    }

    public Grupe getGrupe() {
        return grupe;
    }

    public void setGrupe(Grupe grupe) {
        this.grupe = grupe;
    }

    public String getDalykas() {
        return dalykas;
    }

    public void setDalykas(String dalykas) {
        this.dalykas = dalykas;
    }

    public Destytojas getDestytojas() {
        return destytojas;
    }

    public void setDestytojas(Destytojas destytojas) {
        this.destytojas = destytojas;
    }

    public String getAuditorija() {
        return auditorija;
    }

    public void setAuditorija(String auditorija) {
        this.auditorija = auditorija;
    }

    public int getPogrupis() {
        return pogrupis;
    }

    public void setPogrupis(int pogrupis) {
        this.pogrupis = pogrupis;
    }

    public int getPasikatojamumas() {
        return pasikatojamumas;
    }

    public void setPasikatojamumas(int pasikatojamumas) {
        this.pasikatojamumas = pasikatojamumas;
    }

    public int getPasirenkamasis() {
        return pasirenkamasis;
    }

    public void setPasirenkamasis(int pasirenkamasis) {
        this.pasirenkamasis = pasirenkamasis;
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
