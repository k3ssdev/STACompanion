package io.github.k3ssdev.stacompanion.data;

public class CharacterSheet implements java.io.Serializable{

    private static final String TAG = "CharacterSheet";

    private String id;

    private String userId;

    // Atributos básicos del personaje
    private String characterName;

    private String pronouns;
    private String species;
    private String environment;
    private String rank;
    private String upbringing;
    private String assignment;
    private String traits;
    private int age;
    private String skin;
    private String hair;
    private int weight;
    private int height;
    private String eyes;

    // Habilidades del personaje
    private int control;
    private int fitness;
    private int presence;
    private int daring;
    private int insight;
    private int reason;
    private int command;
    private int security;
    private int science;
    private int conn;
    private int engineering;
    private int medicine;
    private int determination;

    // Rasgos del personaje
    private int reputation;
    private int privilege;
    private int responsibility;
    private String focuses;
    private String values;
    private String talents;
    private String attacks;
    private String equipment;

    // Datos básicos del personaje
    private int stress; // Atributo calculado
    private int currentStress; // Atributo calculado
    private int maxStress; // Atributo calculado
    private int resistance; // Atributo calculado
    private String notesAndAwards;
    private String injuries;
    private String academy;
    private String career;
    private String event1;
    private String event2;

    private long creationTimestamp;

    // Métodos para calcular los atributos
    public void calculateStress() {
        this.stress = this.fitness + this.security;
    }

    public void calculateResistance() {
        this.resistance = this.fitness;
    }

    public CharacterSheet( long creationTimestamp, String id, String userId, String characterName, String pronouns, String species, String environment, String rank, String upbringing, String assignment, String traits,
                          int control, int fitness, int presence, int daring, int insight, int reason,
                          int command, int security, int science, int conn, int engineering, int medicine,
                          int age, String skin, String hair, int weight, int height, String eyes,
                          int reputation, int privilege, int responsibility, String focuses, int determination,
                          String values, String talents, String attacks, String equipment, int stress, int currentStress,
                          int maxStress, int resistance, String notesAndAwards, String injuries, String academy, String career, String event1, String event2) {
        this.creationTimestamp = creationTimestamp;
        this.id = id;
        this.userId = userId;
        this.characterName = characterName;
        this.pronouns = pronouns;
        this.species = species;
        this.environment = environment;
        this.rank = rank;
        this.upbringing = upbringing;
        this.assignment = assignment;
        this.traits = traits;
        this.control = control;
        this.fitness = fitness;
        this.presence = presence;
        this.daring = daring;
        this.insight = insight;
        this.reason = reason;
        this.command = command;
        this.security = security;
        this.science = science;
        this.conn = conn;
        this.engineering = engineering;
        this.medicine = medicine;
        this.age = age;
        this.skin = skin;
        this.hair = hair;
        this.weight = weight;
        this.height = height;
        this.eyes = eyes;
        this.reputation = reputation;
        this.privilege = privilege;
        this.responsibility = responsibility;
        this.focuses = focuses;
        this.determination = determination;
        this.values = values;
        this.talents = talents;
        this.attacks = attacks;
        this.equipment = equipment;
        this.stress = stress;
        this.currentStress = currentStress;
        this.maxStress = maxStress;
        this.resistance = resistance;
        this.notesAndAwards = notesAndAwards;
        this.injuries = injuries;
        this.academy = academy;
        this.career = career;
        this.event1 = event1;
        this.event2 = event2;
;
    }


    // Getters and setters

    public CharacterSheet() {
        // Default constructor required for calls to DataSnapshot.getValue(CharacterSheet.class)
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }


    public long getCreationTimestamp() { return creationTimestamp; }

    public void setCreationTimestamp(long creationTimestamp) { this.creationTimestamp = creationTimestamp; }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getUpbringing() {
        return upbringing;
    }

    public void setUpbringing(String upbringing) {
        this.upbringing = upbringing;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getTraits() {
        return traits;
    }

    public void setTraits(String traits) {
        this.traits = traits;
    }

    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    public int getDaring() {
        return daring;
    }

    public void setDaring(int daring) {
        this.daring = daring;
    }

    public int getInsight() {
        return insight;
    }

    public void setInsight(int insight) {
        this.insight = insight;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getSecurity() {
        return security;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public int getConn() {
        return conn;
    }

    public void setConn(int conn) {
        this.conn = conn;
    }

    public int getEngineering() {
        return engineering;
    }

    public void setEngineering(int engineering) {
        this.engineering = engineering;
    }

    public int getMedicine() {
        return medicine;
    }

    public void setMedicine(int medicine) {
        this.medicine = medicine;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public int getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(int responsibility) {
        this.responsibility = responsibility;
    }

    public String getFocuses() {
        return focuses;
    }

    public void setFocuses(String focuses) {
        this.focuses = focuses;
    }

    public int getDetermination() {
        return determination;
    }

    public void setDetermination(int determination) {
        this.determination = determination;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getTalents() {
        return talents;
    }

    public void setTalents(String talents) {
        this.talents = talents;
    }

    public String getAttacks() {
        return attacks;
    }

    public void setAttacks(String attacks) {
        this.attacks = attacks;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }


    // Getters para los atributos calculados
    public int getStress() {
        return stress;
    }

    public void setStress(int stress) {
        this.stress = stress;
    }

    public int getCurrentStress() {
        return currentStress;
    }

    public void setCurrentStress(int currentStress) {
        this.currentStress = currentStress;
    }

    public int getMaxStress() {
        return maxStress;
    }

    public void setMaxStress(int maxStress) {
        this.maxStress = maxStress;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public String getNotesAndAwards() {
        return notesAndAwards;
    }

    public void setNotesAndAwards(String notesAndAwards) {
        this.notesAndAwards = notesAndAwards;
    }

    public String getInjuries() {
        return injuries;
    }

    public void setInjuries(String injuries) {
        this.injuries = injuries;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getEvent1() {
        return event1;
    }

    public void setEvent1(String event1) {
        this.event1 = event1;
    }

    public String getEvent2() {
        return event2;
    }

    public void setEvent2(String event2) {
        this.event2 = event2;
    }
}