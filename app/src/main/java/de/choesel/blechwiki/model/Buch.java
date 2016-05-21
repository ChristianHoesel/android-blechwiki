package de.choesel.blechwiki.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import de.choesel.blechwiki.orm.URLType;

/**
 * Created by christian on 05.05.16.
 */
@DatabaseTable(tableName = "buch")
public class Buch {

    @DatabaseField(generatedId = true)
    private UUID id;

    @DatabaseField(canBeNull = false, uniqueIndex = true)
    private String buchId;

    @DatabaseField(canBeNull = true)
    private String titel;

    @DatabaseField(canBeNull = true)
    private String untertitel;

    @DatabaseField(canBeNull = true)
    private int erscheinjahr;

    @DatabaseField(canBeNull = true)
    private String herausgeber;

    @DatabaseField(canBeNull = true)
    private String herausgeberVorname;

    @DatabaseField(canBeNull = true)
    private String verlag;

    @DatabaseField(canBeNull = true)
    private String verlagsnummer;

    @DatabaseField(persisterClass = URLType.class)
    private URL imgURL;

    @ForeignCollectionField()
    private Collection<Titel> stuecke = new ArrayList<>();

    public String getBuchId() {
        return buchId;
    }

    public String getTitel() {
        return titel;
    }

    public String getUntertitel() {
        return untertitel;
    }

    public int getErscheinjahr() {
        return erscheinjahr;
    }

    public String getHerausgeber() {
        return herausgeber;
    }

    public String getHerausgeberVorname() {
        return herausgeberVorname;
    }

    public String getVerlag() {
        return verlag;
    }

    public String getVerlagsnummer() {
        return verlagsnummer;
    }

    public URL getImgURL() {
        return imgURL;
    }

    public void setBuchId(String buchId) {
        this.buchId = buchId;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setUntertitel(String untertitel) {
        this.untertitel = untertitel;
    }

    public void setErscheinjahr(int erscheinjahr) {
        this.erscheinjahr = erscheinjahr;
    }

    public void setHerausgeber(String herausgeber) {
        this.herausgeber = herausgeber;
    }

    public void setHerausgeberVorname(String herausgeberVorname) {
        this.herausgeberVorname = herausgeberVorname;
    }

    public void setVerlag(String verlag) {
        this.verlag = verlag;
    }

    public void setVerlagsnummer(String verlagsnummer) {
        this.verlagsnummer = verlagsnummer;
    }

    public void setImgURL(URL imgURL) {
        this.imgURL = imgURL;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Collection<Titel> getStuecke() {
        return stuecke;
    }
}
