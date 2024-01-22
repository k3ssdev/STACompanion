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
    private String age;
    private String skin;
    private String hair;
    private String weight;
    private String height;
    private String eyes;

    // Habilidades del personaje
    private String control;
    private String fitness;
    private String presence;
    private String daring;
    private String insight;
    private String reason;
    private String command;
    private String security;
    private String science;
    private String conn;
    private String engineering;
    private String medicine;
    private String determination;

    // Rasgos del personaje
    private String reputation;
    private String privilege;
    private String responsibility;
    private String focuses;
    private String values;
    private String talents;
    private String attacks;
    private String equipment;

    // Datos básicos del personaje
    private String stress; // Atributo calculado
    private String maxStress; // Atributo calculado
    private String resistance; // Atributo calculado
    private String notesAndAwards;
    private String injuries;
    private String academy;
    private String career;
    private String event1;


    private long modificationTimestamp;


    public CharacterSheet(long modificationTimestamp, String id, String userId, String characterName, String pronouns, String species, String environment, String rank, String upbringing, String assignment, String traits,
                          String control, String fitness, String presence, String daring, String insight, String reason,
                          String command, String security, String science, String conn, String engineering, String medicine,
                          String age, String skin, String hair, String weight, String height, String eyes,
                          String reputation, String privilege, String responsibility, String focuses, String determination,
                          String values, String talents, String attacks, String equipment, String stress,
                          String maxStress, String resistance, String notesAndAwards, String injuries, String academy, String career, String event1, String event2) {
        this.modificationTimestamp = modificationTimestamp;
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

        this.maxStress = maxStress;
        this.resistance = resistance;
        this.notesAndAwards = notesAndAwards;
        this.injuries = injuries;
        this.academy = academy;
        this.career = career;
        this.event1 = event1;
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


    public long getModificationTimestamp() { return modificationTimestamp; }

    public void setModificationTimestamp(long modificationTimestamp) { this.modificationTimestamp = modificationTimestamp; }

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

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getFitness() {
        return fitness;
    }

    public void setFitness(String fitness) {
        this.fitness = fitness;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getDaring() {
        return daring;
    }

    public void setDaring(String daring) {
        this.daring = daring;
    }

    public String getInsight() {
        return insight;
    }

    public void setInsight(String insight) {
        this.insight = insight;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getScience() {
        return science;
    }

    public void setScience(String science) {
        this.science = science;
    }

    public String getConn() {
        return conn;
    }

    public void setConn(String conn) {
        this.conn = conn;
    }

    public String getEngineering() {
        return engineering;
    }

    public void setEngineering(String engineering) {
        this.engineering = engineering;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getFocuses() {
        return focuses;
    }

    public void setFocuses(String focuses) {
        this.focuses = focuses;
    }

    public String getDetermination() {
        return determination;
    }

    public void setDetermination(String determination) {
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
    public String getStress() {
        return stress;
    }

    public void setStress(String stress) {
        this.stress = stress;
    }

    public String getMaxStress() {
        return maxStress;
    }

    public void setMaxStress(String maxStress) {
        this.maxStress = maxStress;
    }

    public String getResistance() {
        return resistance;
    }

    public void setResistance(String resistance) {
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

}