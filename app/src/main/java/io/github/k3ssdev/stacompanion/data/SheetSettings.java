package io.github.k3ssdev.stacompanion.data;


// TODO: IMPLEMENTAR. Esta clase representa las configuraciones de la hoja de personaje.
public class SheetSettings {

    private final int normalMilestones;
    private final int spotlightMilestones;
    private final int arcMilestones;

    private final String sheetType;
    private final String sheetColor;
    private final String whisperType;

    private final int reputation;
    private final int privilege;
    private final int responsibility;
    private final String rank;
    private final int influence;

    private final int stress;
    private final int stressBonus;
    private final int stressMax;

    private final int shields;
    private final int shieldsBonus;
    private final int shieldsMax;

    private final int powerBonus;
    private final int powerMax;

    private final int crewBonus;
    private final int crewMax;

    private final String notes;

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
