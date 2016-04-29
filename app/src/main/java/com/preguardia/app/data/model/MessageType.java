package com.preguardia.app.data.model;

/**
 * @author amouly on 4/29/16.
 */
public enum MessageType {

    TEXT {
        public String toString() {
            return "text";
        }
    },

    PICTURE {
        public String toString() {
            return "picture";
        }
    }
}
