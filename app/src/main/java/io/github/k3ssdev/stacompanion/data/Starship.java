package io.github.k3ssdev.stacompanion.data;

// TODO: IMPLEMENTAR. Esta clase representa una nave estelar.
public class Starship {

    // Atributos
    private String name;
    private final String serviceDate;
    private final String missionProfile;
    private final String designation;
    private final String spaceFrame;
    private final String refit;
    private final String traits;

    private final int engines;
    private final int computers;
    private final int weapons;
    private final int structure;
    private final int sensors;
    private final int communication;

    private final int breachEngines;
    private final int breachComputers;
    private final int breachWeapons;
    private final int breachStructure;
    private final int breachSensors;
    private final int breachCommunications;

    private final int scale;
    private final int resistance;

    private final int command;
    private final int security;
    private final int science;
    private final int conn;
    private final int engineering;
    private final int medicine;

    private final String talents;

    private final int power;
    private final int maxPower;
    private final int shields;

    // Constructor
    public Starship(String name, String serviceDate, String missionProfile, String designation, String spaceFrame,
                    String refit, String traits, int engines, int computers, int weapons, int structure, int sensors,
                    int communication, int breachEngines, int breachComputers, int breachWeapons, int breachStructure,
                    int breachSensors, int breachCommunications, int scale, int resistance, int command, int security,
                    int science, int conn, int engineering, int medicine, String talents, int power, int maxPower, int shields) {
        this.name = name;
        this.serviceDate = serviceDate;
        this.missionProfile = missionProfile;
        this.designation = designation;
        this.spaceFrame = spaceFrame;
        this.refit = refit;
        this.traits = traits;
        this.engines = engines;
        this.computers = computers;
        this.weapons = weapons;
        this.structure = structure;
        this.sensors = sensors;
        this.communication = communication;
        this.breachEngines = breachEngines;
        this.breachComputers = breachComputers;
        this.breachWeapons = breachWeapons;
        this.breachStructure = breachStructure;
        this.breachSensors = breachSensors;
        this.breachCommunications = breachCommunications;
        this.scale = scale;
        this.resistance = resistance;
        this.command = command;
        this.security = security;
        this.science = science;
        this.conn = conn;
        this.engineering = engineering;
        this.medicine = medicine;
        this.talents = talents;
        this.power = power;
        this.maxPower = maxPower;
        this.shields = shields;
    }

    // Métodos (puedes agregar más según sea necesario)
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Otros métodos getters y setters para los demás atributos

    @Override
    public String toString() {
        return "Starship{" +
                "name='" + name + '\'' +
                ", serviceDate='" + serviceDate + '\'' +
                // Agrega el resto de los atributos aquí
                '}';
    }
}

