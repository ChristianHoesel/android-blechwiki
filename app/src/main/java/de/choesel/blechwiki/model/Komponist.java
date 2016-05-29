package de.choesel.blechwiki.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * Created by christian on 05.05.16.
 */
@DatabaseTable(tableName = "komponist")
public class Komponist {

    @DatabaseField(generatedId = true)
    private UUID id;

    @DatabaseField(canBeNull = true, uniqueCombo = true)
    private String name;

    @DatabaseField(canBeNull = true)
    private String kurzname;

    @DatabaseField(canBeNull = true, uniqueCombo = true)
    private Integer geboren;

    @DatabaseField(canBeNull = true)
    private Integer gestorben;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKurzname() {
        return kurzname;
    }

    public Integer getGeboren() {
        return geboren;
    }

    public Integer getGestorben() {
        return gestorben;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKurzname(String kurzname) {
        this.kurzname = kurzname;
    }

    public void setGeboren(Integer geboren) {
        this.geboren = geboren;
    }

    public void setGestorben(Integer gestorben) {
        this.gestorben = gestorben;
    }
}
