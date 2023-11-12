package de.CypDasHuhn.TP.shared;

public class Finals {
    public enum ItemType {
        LOCATION("Location"),
        FOLDER("Folder");
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

    public enum Messages {
        NO_PERMISSION("no_permission"),
        ILLEGAL_NAME("illegal_name"),

        PERMISSION_SHORT_ARGS("permission_short_argument"),
        PERMISSION_ADDED("permission_added"),
        PERMISSION_REMOVED("permission_removed"),

        NO_LOCATION_NAME_TARGET_GIVEN("no_location_name_target_given"),
        NO_LOCATION_NAME_TARGET_FOUND("no_location_name_target_found"),
        LOCATION_NAME_EXISTS("location_name_exists"),

        NO_FOLDER_NAME_TARGET_GIVEN("no_folder_name_target_given"),
        NO_FOLDER_NAME_TARGET_FOUND("no_folder_name_target_found"),
        FOLDER_NAME_EXISTS("folder_name_exists"),

        NO_PLAYER_NAME_GIVEN("no_player_name_given"),
        NO_PLAYER_NAME_FOUND("no_player_name_found"),

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
