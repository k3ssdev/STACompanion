package io.github.k3ssdev.stacompanion.data;

public class SheetSettings {

    private int normalMilestones;
    private int spotlightMilestones;
    private int arcMilestones;

    private String sheetType;
    private String sheetColor;
    private String whisperType;

    private int reputation;
    private int privilege;
    private int responsibility;
    private String rank;
    private int influence;

    private int stress;
    private int stressBonus;
    private int stressMax;

    private int shields;
    private int shieldsBonus;
    private int shieldsMax;

    private int powerBonus;
    private int powerMax;

    private int crewBonus;
    private int crewMax;

    private String notes;

    // Constructor
    public SheetSettings(int normalMilestones, int spotlightMilestones, int arcMilestones,
                         String sheetType, String sheetColor, String whisperType,
                         int reputation, int privilege, int responsibility, String rank, int influence,
                         int stress, int stressBonus, int stressMax,
                         int shields, int shieldsBonus, int shieldsMax,
                         int powerBonus, int powerMax,
                         int crewBonus, int crewMax,
                         String notes) {
        this.normalMilestones = normalMilestones;
        this.spotlightMilestones = spotlightMilestones;
        this.arcMilestones = arcMilestones;
        this.sheetType = sheetType;
        this.sheetColor = sheetColor;
        this.whisperType = whisperType;
        this.reputation = reputation;
        this.privilege = privilege;
        this.responsibility = responsibility;
        this.rank = rank;
        this.influence = influence;
        this.stress = stress;
        this.stressBonus = stressBonus;
        this.stressMax = stressMax;
        this.shields = shields;
        this.shieldsBonus = shieldsBonus;
        this.shieldsMax = shieldsMax;
        this.powerBonus = powerBonus;
        this.powerMax = powerMax;
        this.crewBonus = crewBonus;
        this.crewMax = crewMax;
        this.notes = notes;
    }

    // Métodos (puedes agregar más según sea necesario)
    // Getters y setters para cada atributo

    @Override
    public String toString() {
        return "SheetSettings{" +
                "normalMilestones=" + normalMilestones +
                ", spotlightMilestones=" + spotlightMilestones +
                ", arcMilestones=" + arcMilestones +
                ", sheetType='" + sheetType + '\'' +
                ", sheetColor='" + sheetColor + '\'' +
                // Agrega el resto de los atributos aquí
                '}';
    }
}
