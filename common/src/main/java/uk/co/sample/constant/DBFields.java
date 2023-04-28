package uk.co.sample.constant;

/**
 * <H3>
 * DBFields
 * </H3>
 * Table and Field Name Constants
 *
 * @author AutoGenerate
 */
public class DBFields {

    public static class ANNOUNCEMENT_SAMPLE {

        public static final String ANNOUNCEMENT_SAMPLE_KEY = "ANNOUNCEMENT_SAMPLE.ANNOUNCEMENT_SAMPLE_KEY";
        public static final String REG_DATE                = "ANNOUNCEMENT_SAMPLE.REG_DATE";
        public static final String MOD_DATE                = "ANNOUNCEMENT_SAMPLE.MOD_DATE";
        public static final String VERSION                 = "ANNOUNCEMENT_SAMPLE.VERSION";
        public static final String TEXT                    = "ANNOUNCEMENT_SAMPLE.TEXT";
        public static final String STATUS                  = "ANNOUNCEMENT_SAMPLE.STATUS";

        private ANNOUNCEMENT_SAMPLE() {
        }
    }

    public static class DEMO_ANNOUNCE_INST {

        public static final String TEXT_ID          = "DEMO_ANNOUNCE_INST.TEXT_ID";
        public static final String INSTANT_NO       = "DEMO_ANNOUNCE_INST.INSTANT_NO";
        public static final String SET_ID           = "DEMO_ANNOUNCE_INST.SET_ID";
        public static final String REQUEST_DATETIME = "DEMO_ANNOUNCE_INST.REQUEST_DATETIME";
        public static final String SEQUENCE_NO      = "DEMO_ANNOUNCE_INST.SEQUENCE_NO";
        public static final String PLAY_DATETIME    = "DEMO_ANNOUNCE_INST.PLAY_DATETIME";
        public static final String STATUS           = "DEMO_ANNOUNCE_INST.STATUS";
        public static final String FAILURE_CODE     = "DEMO_ANNOUNCE_INST.FAILURE_CODE";
        public static final String FAILURE_DESC     = "DEMO_ANNOUNCE_INST.FAILURE_DESC";
        public static final String VERSION          = "DEMO_ANNOUNCE_INST.VERSION";

        private DEMO_ANNOUNCE_INST() {
        }
    }

    public static class DEMO_ANNOUNCE_SERIES {

        public static final String SERIES_ID            = "DEMO_ANNOUNCE_SERIES.SERIES_ID";
        public static final String UI_REST_RESPONSE_URL = "DEMO_ANNOUNCE_SERIES.UI_REST_RESPONSE_URL";
        public static final String UI_REST_TOKEN        = "DEMO_ANNOUNCE_SERIES.UI_REST_TOKEN";
        public static final String SUBMIT_DATETIME      = "DEMO_ANNOUNCE_SERIES.SUBMIT_DATETIME";
        public static final String CALLER_REF           = "DEMO_ANNOUNCE_SERIES.CALLER_REF";
        public static final String VERSION              = "DEMO_ANNOUNCE_SERIES.VERSION";

        private DEMO_ANNOUNCE_SERIES() {
        }
    }

    public static class DEMO_ANNOUNCE_SERIES_TOC {

        public static final String TOCREFSID = "DEMO_ANNOUNCE_SERIES_TOC.TOCREFSID";
        public static final String SERIES_ID = "DEMO_ANNOUNCE_SERIES_TOC.SERIES_ID";
        public static final String TOC       = "DEMO_ANNOUNCE_SERIES_TOC.TOC";
        public static final String VERSION   = "DEMO_ANNOUNCE_SERIES_TOC.VERSION";

        private DEMO_ANNOUNCE_SERIES_TOC() {
        }
    }

    public static class DEMO_ANNOUNCE_TEXT {

        public static final String TEXT_ID         = "DEMO_ANNOUNCE_TEXT.TEXT_ID";
        public static final String SET_ID          = "DEMO_ANNOUNCE_TEXT.SET_ID";
        public static final String ANNOUNCE_TEXT   = "DEMO_ANNOUNCE_TEXT.ANNOUNCE_TEXT";
        public static final String ANNOUNCE_LANG   = "DEMO_ANNOUNCE_TEXT.ANNOUNCE_LANG";
        public static final String SEQUENCE_NO     = "DEMO_ANNOUNCE_TEXT.SEQUENCE_NO";
        public static final String WAITFOR_TEXT_ID = "DEMO_ANNOUNCE_TEXT.WAITFOR_TEXT_ID";
        public static final String STATUS          = "DEMO_ANNOUNCE_TEXT.STATUS";
        public static final String VERSION         = "DEMO_ANNOUNCE_TEXT.VERSION";

        private DEMO_ANNOUNCE_TEXT() {
        }
    }

    public static class DEMO_ANNOUNCE_TRAINS {

        public static final String SET_ID                = "DEMO_ANNOUNCE_TRAINS.SET_ID";
        public static final String SERIES_ID             = "DEMO_ANNOUNCE_TRAINS.SERIES_ID";
        public static final String SCHEDULE_UID          = "DEMO_ANNOUNCE_TRAINS.SCHEDULE_UID";
        public static final String DAY_OF_RUN            = "DEMO_ANNOUNCE_TRAINS.DAY_OF_RUN";
        public static final String TRUST_ID              = "DEMO_ANNOUNCE_TRAINS.TRUST_ID";
        public static final String TRIGGER_TYPE          = "DEMO_ANNOUNCE_TRAINS.TRIGGER_TYPE";
        public static final String TRIGGER_LOCATION_TYPE = "DEMO_ANNOUNCE_TRAINS.TRIGGER_LOCATION_TYPE";
        public static final String TRIGGER_LOCATION      = "DEMO_ANNOUNCE_TRAINS.TRIGGER_LOCATION";
        public static final String BASE_TRIGGER_TIME     = "DEMO_ANNOUNCE_TRAINS.BASE_TRIGGER_TIME";
        public static final String NEXT_TRIGGER_TIME     = "DEMO_ANNOUNCE_TRAINS.NEXT_TRIGGER_TIME";
        public static final String REPEAT_SECONDS        = "DEMO_ANNOUNCE_TRAINS.REPEAT_SECONDS";
        public static final String REPEAT_UNTIL          = "DEMO_ANNOUNCE_TRAINS.REPEAT_UNTIL";
        public static final String REPEAT_COUNT          = "DEMO_ANNOUNCE_TRAINS.REPEAT_COUNT";
        public static final String MSISDN_REF            = "DEMO_ANNOUNCE_TRAINS.MSISDN_REF";
        public static final String DELETE_STATUS         = "DEMO_ANNOUNCE_TRAINS.DELETE_STATUS";
        public static final String VERSION               = "DEMO_ANNOUNCE_TRAINS.VERSION";

        private DEMO_ANNOUNCE_TRAINS() {
        }
    }

    public static class DEMO_TRAIN_IDX {

        public static final String ORG_TRAIN_ID     = "DEMO_TRAIN_IDX.ORG_TRAIN_ID";
        public static final String SCHEDULE_UID     = "DEMO_TRAIN_IDX.SCHEDULE_UID";
        public static final String ORG_TRUST_ID     = "DEMO_TRAIN_IDX.ORG_TRUST_ID";
        public static final String TRAIN_TERMINATED = "DEMO_TRAIN_IDX.TRAIN_TERMINATED";
        public static final String DAY_OF_RUN       = "DEMO_TRAIN_IDX.DAY_OF_RUN";
        public static final String VERSION          = "DEMO_TRAIN_IDX.VERSION";

        private DEMO_TRAIN_IDX() {
        }
    }

    // DB FIELDS Start [auto-generated]
    public static final String ANNOUNCE_LANG_ALIAS         = "ANNOUNCE_LANG";
    public static final String ANNOUNCE_TEXT_ALIAS         = "ANNOUNCE_TEXT";
    public static final String BASE_TRIGGER_TIME_ALIAS     = "BASE_TRIGGER_TIME";
    public static final String CALLER_REF_ALIAS            = "CALLER_REF";
    public static final String DAY_OF_RUN_ALIAS            = "DAY_OF_RUN";
    public static final String DELETE_STATUS_ALIAS         = "DELETE_STATUS";
    public static final String FAILURE_CODE_ALIAS          = "FAILURE_CODE";
    public static final String FAILURE_DESC_ALIAS          = "FAILURE_DESC";
    public static final String INSTANT_NO_ALIAS            = "INSTANT_NO";
    public static final String MSISDN_REF_ALIAS            = "MSISDN_REF";
    public static final String NEXT_TRIGGER_TIME_ALIAS     = "NEXT_TRIGGER_TIME";
    public static final String ORG_TRAIN_ID_ALIAS          = "ORG_TRAIN_ID";
    public static final String ORG_TRUST_ID_ALIAS          = "ORG_TRUST_ID";
    public static final String PLAY_DATETIME_ALIAS         = "PLAY_DATETIME";
    public static final String REPEAT_COUNT_ALIAS          = "REPEAT_COUNT";
    public static final String REPEAT_SECONDS_ALIAS        = "REPEAT_SECONDS";
    public static final String REPEAT_UNTIL_ALIAS          = "REPEAT_UNTIL";
    public static final String REQUEST_DATETIME_ALIAS      = "REQUEST_DATETIME";
    public static final String SCHEDULE_UID_ALIAS          = "SCHEDULE_UID";
    public static final String SEQUENCE_NO_ALIAS           = "SEQUENCE_NO";
    public static final String SERIES_ID_ALIAS             = "SERIES_ID";
    public static final String SET_ID_ALIAS                = "SET_ID";
    public static final String SUBMIT_DATETIME_ALIAS       = "SUBMIT_DATETIME";
    public static final String TEXT_ID_ALIAS               = "TEXT_ID";
    public static final String TOC_ALIAS                   = "TOC";
    public static final String TOCREFSID_ALIAS             = "TOCREFSID";
    public static final String TRAIN_TERMINATED_ALIAS      = "TRAIN_TERMINATED";
    public static final String TRIGGER_LOCATION_ALIAS      = "TRIGGER_LOCATION";
    public static final String TRIGGER_LOCATION_TYPE_ALIAS = "TRIGGER_LOCATION_TYPE";
    public static final String TRIGGER_TYPE_ALIAS          = "TRIGGER_TYPE";
    public static final String TRUST_ID_ALIAS              = "TRUST_ID";
    public static final String UI_REST_RESPONSE_URL_ALIAS  = "UI_REST_RESPONSE_URL";
    public static final String UI_REST_TOKEN_ALIAS         = "UI_REST_TOKEN";
    public static final String VERSION_ALIAS               = "VERSION";
    public static final String WAITFOR_TEXT_ID_ALIAS       = "WAITFOR_TEXT_ID";

    // DB FIELDS End [auto-generated]
    // DB FIELDS Start [manual]
    // DB FIELDS End [manual]
    //***** injection field *****
    //***** constructor *****
    private DBFields() {
    }
    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****
}
