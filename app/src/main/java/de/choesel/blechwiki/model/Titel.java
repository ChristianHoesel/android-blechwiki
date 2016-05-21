package de.choesel.blechwiki.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.net.URL;
import java.util.UUID;

import de.choesel.blechwiki.orm.URLType;

/**
 * Created by christian on 05.05.16.
 */
@DatabaseTable(tableName = "titel")
public class Titel {

    @DatabaseField(generatedId = true)
    private UUID id;

    @DatabaseField(persisterClass = URLType.class)
    private URL imgURL;

    @DatabaseField(canBeNull = true)
    private String vorzeichen;

    @DatabaseField(canBeNull = true)
    private String besetzung;

    @DatabaseField(canBeNull = true)
    private String name;

    @DatabaseField(canBeNull = true)
    private String nummer;

    @DatabaseField(canBeNull = true)
    private String zusatz;

    @DatabaseField(canBeNull = true)
    private String komponist;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Buch buch;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public URL getImgURL() {
        return imgURL;
    }

    public void setImgURL(URL imgURL) {
        this.imgURL = imgURL;
    }

    public String getVorzeichen() {
        return vorzeichen;
    }

    public void setVorzeichen(String vorzeichen) {
        this.vorzeichen = vorzeichen;
    }

    public String getBesetzung() {
        return besetzung;
    }

    public void setBesetzung(String besetzung) {
        this.besetzung = besetzung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getZusatz() {
        return zusatz;
    }

    public void setZusatz(String zusatz) {
        this.zusatz = zusatz;
    }

    public String getKomponist() {
        return komponist;
    }

    public void setKomponist(String komponist) {
        this.komponist = komponist;
    }

    public Buch getBuch() {
        return buch;
    }

    public void setBuch(Buch buch) {
        this.buch = buch;
    }
}
