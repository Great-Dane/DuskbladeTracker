package com.sethi.gurdane.duskbladetracker;

/**
 * Created by Dane on 10/19/2016.
 */

public class Spell {

    private int id;
    private int rulebook_id;
    private int page;
    private String name;
    private int school_id;
    private int sub_school_id;
    private boolean verbal_component;
    private boolean somatic_component;
    private boolean material_component;
    private boolean arcane_focus_component;
    private boolean divine_focus_component;
    private boolean xp_component;
    private String casting_time;
    private String range;
    private String target;
    private String effect;
    private String area;
    private String duration;
    private String saving_throw;
    private String spell_resistance;
    private String flavor_text;
    private boolean meta_breath_component;
    private boolean true_name_component;
    private String extra_components;
    private String description_html;
    private boolean corrupt_component;
    private int corrupt_level;
    private String rulebook_string;
    private String school_string;
    private String subschool_string;
    private String descriptors;
    private String level;
    private int duskbladeLevel;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDuskbladeLevel() {
        return duskbladeLevel;
    }

    public void setDuskbladeLevel(int duskbladeLevel) {
        this.duskbladeLevel = duskbladeLevel;
    }

    public Spell() {

        //Default spell contains no information
        id = -1;
        rulebook_id = -1;
        page = -1;
        name = null;
        school_id = -1;
        sub_school_id = -1;
        verbal_component = false;
        somatic_component = false;
        material_component = false;
        arcane_focus_component = false;
        divine_focus_component = false;
        xp_component = false;
        casting_time = null;
        range = null;
        target = null;
        effect = null;
        area = null;
        duration = null;
        saving_throw = null;
        spell_resistance = null;
        flavor_text = null;
        meta_breath_component = false;
        true_name_component = false;
        extra_components = null;
        description_html = null;
        corrupt_component = false;
        corrupt_level = -1;
        rulebook_string = null;
        school_string = null;
        subschool_string = null;
        descriptors = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRulebook_id() {
        return rulebook_id;
    }

    public void setRulebook_id(int rulebook_id) {
        this.rulebook_id = rulebook_id;
        createRulebook_string();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
        createSchool_string();
    }

    public int getSub_school_id() {
        return sub_school_id;
    }

    public void setSub_school_id(int sub_school_id) {
        this.sub_school_id = sub_school_id;
        createSubschool_string();
    }

    public boolean isVerbal_component() {
        return verbal_component;
    }

    public void setVerbal_component(int verbal_component) {
        if (verbal_component == 1)
            this.verbal_component = true;
        else
            this.verbal_component = false;
    }

    public boolean isSomatic_component() {
        return somatic_component;
    }

    public void setSomatic_component(int somatic_component) {
        if (somatic_component == 1)
            this.somatic_component = true;
        else
            this.somatic_component = false;
    }

    public boolean isMaterial_component() {
        return material_component;
    }

    public void setMaterial_component(int material_component) {
        if (material_component == 1)
            this.material_component = true;
        else
            this.material_component = false;
    }

    public boolean isArcane_focus_component() {
        return arcane_focus_component;
    }

    public void setArcane_focus_component(int arcane_focus_component) {
        if (arcane_focus_component == 1)
            this.arcane_focus_component = true;
        else
            this.arcane_focus_component = false;
    }

    public boolean isDivine_focus_component() {
        return divine_focus_component;
    }

    public void setDivine_focus_component(int divine_focus_component) {
        if (divine_focus_component == 1)
            this.divine_focus_component = true;
        else
            this.divine_focus_component = false;
    }

    public boolean isXp_component() {
        return xp_component;
    }

    public void setXp_component(int xp_component) {
        if (xp_component == 1)
            this.xp_component = true;
        else
            this.xp_component = false;
    }

    public String getCasting_time() {
        return casting_time;
    }

    public void setCasting_time(String casting_time) {
        this.casting_time = casting_time;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSaving_throw() {
        return saving_throw;
    }

    public void setSaving_throw(String saving_throw) {
        this.saving_throw = saving_throw;
    }

    public String getSpell_resistance() {
        return spell_resistance;
    }

    public void setSpell_resistance(String spell_resistance) {
        this.spell_resistance = spell_resistance;
    }

    public String getFlavor_text() {
        return flavor_text;
    }

    public void setFlavor_text(String flavor_text) {
        this.flavor_text = flavor_text;
    }

    public boolean isMeta_breath_component() {
        return meta_breath_component;
    }

    public void setMeta_breath_component(int meta_breath_component) {
        if (meta_breath_component == 1)
            this.meta_breath_component = true;
        else
            this.meta_breath_component = false;
    }

    public boolean isTrue_name_component() {
        return true_name_component;
    }

    public void setTrue_name_component(int true_name_component) {
        if (true_name_component == 1)
            this.true_name_component = true;
        else
            this.true_name_component = false;
    }

    public String getExtra_components() {
        return extra_components;
    }

    public void setExtra_components(String extra_components) {
        this.extra_components = extra_components;
    }

    public String getDescription_html() {
        return description_html;
    }

    public void setDescription_html(String description_html) {
        this.description_html = description_html;
    }

    public boolean isCorrupt_component() {
        return corrupt_component;
    }

    public void setCorrupt_component(int corrupt_component) {
        if (corrupt_component == 1)
            this.corrupt_component = true;
        else
            this.corrupt_component = false;
    }

    public int getCorrupt_level() {
        return corrupt_level;
    }

    public void setCorrupt_level(int corrupt_level) {
        this.corrupt_level = corrupt_level;
    }

    public String getRulebook_string() {
        return rulebook_string;
    }

    public void setRulebook_string(String rulebook_string) {
        this.rulebook_string = rulebook_string;
    }

    public void createRulebook_string() {
        switch (rulebook_id) {
            case 4:
                this.rulebook_string = "Dungeon Master's Guide v3.5";
                break;
            case 5:
                this.rulebook_string = "Monster Manual v3.5";
                break;
            case 6:
                this.rulebook_string = "Player's Handbook v.3.5";
                break;
            case 7:
                this.rulebook_string = "Dragonmarked";
                break;
            case 8:
                this.rulebook_string = "Faiths of Eberron";
                break;
            case 9:
                this.rulebook_string = "Magic of Eberron";
                break;
            case 10:
                this.rulebook_string = "Races of Eberron";
                break;
            case 11:
                this.rulebook_string = "Sharn: City of Towers";
                break;
            case 12:
                this.rulebook_string = "Eberron Campaign Setting";
                break;
            case 13:
                this.rulebook_string = "Player's Guide to Eberron";
                break;
            case 14:
                this.rulebook_string = "Secrets of Sarlona";
                break;
            case 15:
                this.rulebook_string = "Secrets of Xen'drik";
                break;
            case 16:
                this.rulebook_string = "City of Splendors: Waterdeep";
                break;
            case 17:
                this.rulebook_string = "Enemies and Allies";
                break;
            case 18:
                this.rulebook_string = "Faiths & Pantheons";
                break;
            case 19:
                this.rulebook_string = "Forgotten Realms Campaign Setting";
                break;
            case 20:
                this.rulebook_string = "Magic of Faerun";
                break;
            case 21:
                this.rulebook_string = "Monster Compendium: Monsters of Faerunn";
                break;
            case 22:
                this.rulebook_string = "Player's Guide to Faerun";
                break;
            case 23:
                this.rulebook_string = "Races of Faerun";
                break;
            case 24:
                this.rulebook_string = "Serpent Kingdoms";
                break;
            case 25:
                this.rulebook_string = "Shining South";
                break;
            case 26:
                this.rulebook_string = "Dragons of Faerun";
                break;
            case 27:
                this.rulebook_string = "Champions of Ruin";
                break;
            case 28:
                this.rulebook_string = "Champions of Valor";
                break;
            case 30:
                this.rulebook_string = "Lost Empires of Faerun";
                break;
            case 31:
                this.rulebook_string = "Power of Faerun";
                break;
            case 33:
                this.rulebook_string = "Unapproachable East";
                break;
            case 34:
                this.rulebook_string = "Underdark";
                break;
            case 35:
                this.rulebook_string = "Arms and Equipment Guide";
                break;
            case 36:
                this.rulebook_string = "Book of Challenges: Dungeon Rooms, Puzzles, and Traps";
                break;
            case 37:
                this.rulebook_string = "Book of Vile Darkness";
                break;
            case 38:
                this.rulebook_string = "Defenders of the Faith: A Guidebook to Clerics and Paladins";
                break;
            case 39:
                this.rulebook_string = "Deities and Demigods";
                break;
            case 40:
                this.rulebook_string = "Dungeon Master's Guide II";
                break;
            case 41:
                this.rulebook_string = "Epic Level Handbook";
                break;
            case 42:
                this.rulebook_string = "Fiend Folio";
                break;
            case 43:
                this.rulebook_string = "Manual of the Planes";
                break;
            case 44:
                this.rulebook_string = "Masters of the Wild: A Guidebook to Barbarians, Druids, and Rangers";
                break;
            case 45:
                this.rulebook_string = "Monster Manual II";
                break;
            case 46:
                this.rulebook_string = "Psionics Handbook 3.0";
                break;
            case 47:
                this.rulebook_string = "Savage Species";
                break;
            case 48:
                this.rulebook_string = "Song and Silence: A Guidebook to Bards and Rogues";
                break;
            case 49:
                this.rulebook_string = "Stronghold Builder's Guidebook";
                break;
            case 50:
                this.rulebook_string = "Sword and Fist: A Guidebook to Monks and Fighters";
                break;
            case 51:
                this.rulebook_string = "Tome and Blood: A Guidebook to Wizards and Sorcerers";
                break;
            case 52:
                this.rulebook_string = "Book of Exalted Deeds";
                break;
            case 53:
                this.rulebook_string = "CityScape";
                break;
            case 54:
                this.rulebook_string = "Complete Adventurer";
                break;
            case 55:
                this.rulebook_string = "Complete Arcane";
                break;
            case 56:
                this.rulebook_string = "Complete Divine";
                break;
            case 57:
                this.rulebook_string = "Complete Champion";
                break;
            case 58:
                this.rulebook_string = "Complete Mage";
                break;
            case 59:
                this.rulebook_string = "Complete Psionic";
                break;
            case 60:
                this.rulebook_string = "Complete Scoundrel";
                break;
            case 61:
                this.rulebook_string = "Complete Warrior";
                break;
            case 62:
                this.rulebook_string = "Dragon Magic";
                break;
            case 63:
                this.rulebook_string = "Dungeonscape";
                break;
            case 64:
                this.rulebook_string = "Exemplars of Evil";
                break;
            case 65:
                this.rulebook_string = "Expanded Psionics Handbook";
                break;
            case 66:
                this.rulebook_string = "Fiendish Codex I: Hordes of the Abyss";
                break;
            case 67:
                this.rulebook_string = "Fiendish Codex II: Tyrants of the Nine Hells";
                break;
            case 68:
                this.rulebook_string = "Frostburn";
                break;
            case 69:
                this.rulebook_string = "Heroes of Battle";
                break;
            case 70:
                this.rulebook_string = "Heroes of Horror";
                break;
            case 71:
                this.rulebook_string = "Libris Mortis: The Book of the Dead";
                break;
            case 72:
                this.rulebook_string = "Lords of Madness";
                break;
            case 73:
                this.rulebook_string = "Magic Item Compendium";
                break;
            case 74:
                this.rulebook_string = "Magic of Incarnum";
                break;
            case 75:
                this.rulebook_string = "Miniatures Handbook";
                break;
            case 76:
                this.rulebook_string = "Monster Manual III";
                break;
            case 77:
                this.rulebook_string = "Monster Manual IV";
                break;
            case 78:
                this.rulebook_string = "Monster Manual V";
                break;
            case 79:
                this.rulebook_string = "Planar Handbook";
                break;
            case 80:
                this.rulebook_string = "Player's Handbook II";
                break;
            case 81:
                this.rulebook_string = "Races of Destiny";
                break;
            case 82:
                this.rulebook_string = "Races of Stone";
                break;
            case 83:
                this.rulebook_string = "Races of the Dragon";
                break;
            case 84:
                this.rulebook_string = "Races of the Wild";
                break;
            case 85:
                this.rulebook_string = "Sandstorm";
                break;
            case 86:
                this.rulebook_string = "Spell Compendium";
                break;
            case 87:
                this.rulebook_string = "Stormwrack";
                break;
            case 88:
                this.rulebook_string = "Tome of Battle: The Book of Nine Swords";
                break;
            case 89:
                this.rulebook_string = "Tome of Magic";
                break;
            case 90:
                this.rulebook_string = "Unearthed Arcana";
                break;
            case 91:
                this.rulebook_string = "Weapons of Legacy";
                break;
            case 92:
                this.rulebook_string = "Draconomicon";
                break;
            case 93:
                this.rulebook_string = "Drow of the Underdark";
                break;
            case 94:
                this.rulebook_string = "Ghostwalk";
                break;
            case 95:
                this.rulebook_string = "Lords of Darkness";
                break;
            case 96:
                this.rulebook_string = "Oriental Adventures";
                break;
            case 97:
                this.rulebook_string = "Silver Marches";
                break;
            case 98:
                this.rulebook_string = "Dragonlance Campaign Setting";
                break;
            case 99:
                this.rulebook_string = "Expedition to the Demonweb Pits";
                break;
            case 100:
                this.rulebook_string = "Explorer's Handbook";
                break;
            case 101:
                this.rulebook_string = "Five Nations";
                break;
            case 102:
                this.rulebook_string = "Expedition to Castle Ravenloft";
                break;
            case 103:
                this.rulebook_string = "Return to the Temple of Elemental Evil";
                break;
            case 104:
                this.rulebook_string = "The Shattered Gates of Slaughtergarde";
                break;
            case 105:
                this.rulebook_string = "The Forge of War";
                break;
            case 106:
                this.rulebook_string = "Player's Handbook 3.0";
                break;
            case 107:
                this.rulebook_string = "Monster Manual";
                break;
            case 108:
                this.rulebook_string = "Web";
                break;
            case 109:
                this.rulebook_string = "Dragon Compendium";
                break;
            case 110:
                this.rulebook_string = "Elder Evils";
                break;
            case 111:
                this.rulebook_string = "Feats";
                break;
            case 112:
                this.rulebook_string = "Dragons of Ebberon";
                break;
            case 113:
                this.rulebook_string = "Eyes of the Lich Queen";
                break;
            case 114:
                this.rulebook_string = "Shadowdale: The Scouring of the Land";
                break;
            case 115:
                this.rulebook_string = "Red Hand of Doom";
                break;
            default:
                this.rulebook_string = "NO_SOURCE";
                break;
        }
    }

    public String getSchool_string() {
        return school_string;
    }

    public void setSchool_string(String school_string) {
        this.school_string = school_string;
    }

    public void createSchool_string() {
        switch(this.school_id) {
            case 1:
                this.school_string = "Abjuration";
                break;
            case 2:
                this.school_string = "Conjuration";
                break;
            case 3:
                this.school_string = "Divination";
                break;
            case 4:
                this.school_string = "Enchantment";
                break;
            case 5:
                this.school_string = "Evocation";
                break;
            case 6:
                this.school_string = "Necromancy";
                break;
            case 7:
                this.school_string = "Transmutation";
                break;
            case 8:
                this.school_string = "Illusion";
                break;
            case 9:
                this.school_string = "Universal";
                break;
            case 10:
                this.school_string = "Conjuration/Evocation";
                break;
            case 11:
                this.school_string = "Transmutation/Evocation";
                break;
            case 12:
                this.school_string = "Conjuration/Necromancy";
                break;
            case 13:
                this.school_string = "Divination/Evocation";
                break;
            case 14:
                this.school_string = "Evocation/Transmutation";
                break;
            case 15:
                this.school_string = "Transmutation/Divination";
                break;
            case 16:
                this.school_string = "Abjuration/Evocation";
                break;
            case 17:
                this.school_string = "Desert Wind";
                break;
            case 18:
                this.school_string = "Devoted Spirit";
                break;
            case 19:
                this.school_string = "Diamond Mind";
                break;
            case 20:
                this.school_string = "Iron Heart";
                break;
            case 21:
                this.school_string = "Setting Sun";
                break;
            case 22:
                this.school_string = "Shadow Hand";
                break;
            case 23:
                this.school_string = "Stone Dragon";
                break;
            case 24:
                this.school_string = "Tiger Claw";
                break;
            case 25:
                this.school_string = "White Raven";
                break;
            case 26:
                this.school_string = "Varied";
                break;
            default:
                this.school_string = "NO_SCHOOL";
                break;

        }
    }

    public String getSubschool_string() {
        return subschool_string;
    }

    public void setSubschool_string(String subschool_string) {
        this.subschool_string = subschool_string;
    }

    public void createSubschool_string() {
        switch (this.sub_school_id) {
            case 1:
                this.subschool_string = "Summoning";
                break;
            case 2:
                this.subschool_string = "Creation";
                break;
            case 3:
                this.subschool_string = "Calling";
                break;
            case 4:
                this.subschool_string = "Healing";
                break;
            case 5:
                this.subschool_string = "Compulsion";
                break;
            case 6:
                this.subschool_string = "Phantasm";
                break;
            case 7:
                this.subschool_string = "Glamer";
                break;
            case 8:
                this.subschool_string = "Teleportation";
                break;
            case 9:
                this.subschool_string = "Shadow";
                break;
            case 10:
                this.subschool_string = "Scrying";
                break;
            case 11:
                this.subschool_string = "Charm";
                break;
            case 12:
                this.subschool_string = "Figment";
                break;
            case 14:
                this.subschool_string = "Pattern";
                break;
            case 16:
                this.subschool_string = "Polymorph";
                break;
            case 20:
                this.subschool_string = "Creation or Calling";
                break;
            case 21:
                this.subschool_string = "Figment and Glamer";
                break;
            case 22:
                this.subschool_string = "Boost";
                break;
            case 23:
                this.subschool_string = "Counter";
                break;
            case 24:
                this.subschool_string = "Strike";
                break;
            case 25:
                this.subschool_string = "Stance";
                break;
            default:
                this.subschool_string = null;
        }
    }

    public String getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(String descriptors) {
        this.descriptors = descriptors;
    }

}
