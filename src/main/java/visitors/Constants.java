package visitors;

public enum Constants {
    OLD_IMPORT {
        @Override
        public String toString() {
            return "org.junit";
        }
    },

    NEW_IMPORT {
        @Override
        public String toString() {
            return "org.junit.jupiter.api";
        }
    }

}
