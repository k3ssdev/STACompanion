package io.github.k3ssdev.stacompanion.data;

public class Starship {

    // Atributos
    private String name;
    private String serviceDate;
    private String missionProfile;
    private String designation;
    private String spaceFrame;
    private String refit;
    private String traits;

    private int engines;
    private int computers;
    private int weapons;
    private int structure;
    private int sensors;
    private int communication;

    private int breachEngines;
    private int breachComputers;
    private int breachWeapons;
    private int breachStructure;
    private int breachSensors;
    private int breachCommunications;

    private int scale;
    private int resistance;

    private int command;
    private int security;
    private int science;
    private int conn;
    private int engineering;
    private int medicine;

    private String talents;

    private int power;
    private int maxPower;
    private int shields;

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

