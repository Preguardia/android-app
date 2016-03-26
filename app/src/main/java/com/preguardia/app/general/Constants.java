package com.preguardia.app.general;

import com.preguardia.app.BuildConfig;

/**
 * @author amouly on 3/1/16.
 */
public class Constants {

    public static final String PREFERENCES_FIRST_START = "firstStart";

    public static final String PREFERENCES_USER_UID = "user_uid";
    public static final String PREFERENCES_USER_NAME = "user_name";
    public static final String PREFERENCES_USER_TOKEN = "user_token";
    public static final String PREFERENCES_USER_TYPE = "user_type";
    public static final String PREFERENCES_USER_MEDIC_PLATE = "user_medic_plate";
    public static final String PREFERENCES_USER_PATIENT_MEDICAL = "user_patient_medical";
    public static final String PREFERENCES_USER_BIRTH = "user_birth";

    public static final String FIREBASE_CONSULTATION_ID = "consultationId";
    public static final String FIREBASE_CONSULTATIONS = "consultations";
    public static final String FIREBASE_USERS = "users";
    public static final String FIREBASE_MESSAGES = "messages";
    public static final String FIREBASE_QUEUE = "queue";
    public static final String FIREBASE_TASKS = "tasks";

    public static final String FIREBASE_USER_MEDIC_ID = "medic/id";
    public static final String FIREBASE_USER_PATIENT_ID = "patient/id";

    public static final String FIREBASE_USER_TYPE_MEDIC = "medic";
    public static final String FIREBASE_USER_TYPE_PATIENT = "patient";

    public static final String FIREBASE_CONSULTATION_STATUS = "status";
    public static final String FIREBASE_CONSULTATION_STATUS_PENDING = "pending";
    public static final String FIREBASE_CONSULTATION_STATUS_ASSIGNED = "assigned";
    public static final String FIREBASE_CONSULTATION_STATUS_CLOSED = "closed";

    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_CONSULTATIONS = FIREBASE_URL + "/" + FIREBASE_CONSULTATIONS;
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_USERS;
    public static final String FIREBASE_URL_MESSAGES = FIREBASE_URL + "/" + FIREBASE_MESSAGES;
    public static final String FIREBASE_URL_QUEUE_TASKS = FIREBASE_URL + "/" + FIREBASE_QUEUE + "/" + FIREBASE_TASKS;

    public static final String EXTRA_CONSULTATION_ID = "extra:consultation:id";
    public static final String EXTRA_USER_TYPE = "extra:user:type";
}
