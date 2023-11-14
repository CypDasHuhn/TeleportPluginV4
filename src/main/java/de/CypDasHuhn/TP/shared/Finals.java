package de.CypDasHuhn.TP.shared;

public class Finals {
    public enum ItemType {
        LOCATION("Location"),
        FOLDER("Folder"),
        TAG("Tag");
        public final String label;
        private ItemType(String label) {
            this.label = label;
        }
    }

    public enum Attributes {
        GLOBAL("-global"),
        PROPOSE("-propose"),
        FIND("-find");
        public final String label;
        private Attributes(String label) {
            this.label = label;
        }
    }

    public enum TagModes {
        ADD("add"),
        REMOVE("remove"),
        TOGGLE("toggle");
        public final String label;
        private TagModes(String label) {
            this.label = label;
        }
    }

    public enum Messages {
        NO_PERMISSION("no_permission"),
        ILLEGAL_NAME("illegal_name"),

        PERMISSION_ADDED("permission_added"),
        PERMISSION_REMOVED("permission_removed"),

        NO_LOCATION_NAME_TARGET_GIVEN("location_not_given_target"),
        NO_LOCATION_NAME_CREATED_GIVEN("location_not_given_custom"),
        NO_LOCATION_NAME_TARGET_FOUND("location_not_found"),
        LOCATION_NAME_EXISTS("location_name_exists"),

        NO_FOLDER_NAME_TARGET_GIVEN("folder_not_given_target"),
        NO_FOLDER_NAME_CREATED_GIVEN("folder_not_given_custom"),
        NO_FOLDER_NAME_TARGET_FOUND("folder_not_found"),
        FOLDER_NAME_EXISTS("folder_name_exists"),

        PLAYER_NOT_FOUND_CONFIG("player_not_found_config"),
        PLAYER_NOT_FOUND_ONLINE("player_not_found_online"),
        PLAYER_NOT_GIVEN("player_not_given"),

        MODE_NOT_FOUND("mode_not_found"),
        MODE_NOT_GIVEN("mode_not_given"),

        TAG_NOT_GIVEN("tag_not_given"),
        TAG_NOT_FOUND("tag_not_found"),

        TAG_MODE_NOT_GIVEN("tag_mode_not_given"),
        TAG_MODE_NOT_FOUND("tag_mode_not_found"),

        ITEM_HAS_TAG_ALREADY("item_has_tag_already"),
        ITEM_HAS_NOT_TAG("item_has_not_tag"),

        ITEM_TYPE_NOT_GIVEN("item_type_not_given"),
        ITEM_TYPE_NOT_FOUND("item_type_not_found"),

        TELEPORT_SET_SUCCESS("teleport_set_success"),
        TELEPORT_EDIT_SUCCESS("teleport_edit_success"),
        TELEPORT_DELETE_SUCCESS("teleport_delete_success"),

        LANGUAGE_SHORT_ARGS("language_short_argument"),
        LANGUAGE_NOT_FOUND("language_locale_not_existing"),
        LANGUAGE_ALREADY_SELECTED("language_already_selected"),
        LANGUAGE_SUCCESS("language_success");

        public final String label;
        private Messages(String label) {
            this.label = label;
        }
    }


    public static final String EMPTY = "EMPTY";
    public static final String DEFAULT_PARENT = "General";
    public static final String GLOBAL = "Global";
    public static final int NULL_INT = -1;
}
