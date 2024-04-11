class DashBoardConstant{
    //Main Screen
    static const TRIP_SHEET                 = 0;
    static const FUEL_ENTRY                 = 1;
    static const WORK_ORDER                 = 2;
    static const SERVICE_REMINDER           = 3;
    static const RENEWAL                    = 4;
    static const ISSUES                     = 5;
    static const SERVICE_ENTRY              = 6;

    //Trip Sheet
    static const CREATED_TRIP_SHEET         = 0;
    static const OPEN_TRIP_SHEET            = 1;
    static const CLOSED_TRIP_SHEET          = 2;
    static const ACCOUNT_CLOSED             = 3;
    static const TOTAL_RUN_COUNT            = 4;
    static const MISSED_CLOSING_TRIP_SHEET  = 5;
    static const OLDEST_OPEN_TRIP_SHEET     = 6;

    static const TRIP_SHEET_LEFT_TO_CLOSE   = 0;
    static const TRIP_SHEET_DISPATCH_COUNT  = 1;
    static const TRIP_SHEET_SAVE_COUNT      = 2;

    //Fuel Entry
    static const CREATED_FUEL_ENTRY         = 0;
    static const BELOW                      = 1;
    static const BETWEEN                    = 2;
    static const ABOVE                      = 3;
    static const TOTAL_PRICE                = 4;
    static const TOTAL_LITERS               = 5;
    static const TOTAL_AVERAGE              = 6;

    static const VEHICLES_WITH_FE           = 1;
    static const VEHICLES_WITHOUT_FE        = 2;
    static const VEHICLES_WITHOUT_KMPL      = 3;

    static const VEHICLES_WITH_FE_TABLE      = 7;
    static const VEHICLES_WITHOUT_FE_TABLE   = 8;
    static const VEHICLES_WITHOUT_KMPL_TABLE = 9;

    //Work Order
    static const CREATED_WORK_ORDER         = 0;
    static const OPEN_WORK_ORDER            = 1;
    static const IN_PROCESS_WORK_ORDER      = 2;
    static const HOLD_WORK_ORDER            = 3;
    static const COMPLETED_WORK_ORDER       = 4;

    static const ALL_OPEN                   = 0;
    static const SEVEN_DAYS                 = 1;
    static const FIFTEEN_DAYS               = 2;
    static const FIFTEEEN_PLUS_DAYS         = 3;

    //Service Reminder
    static const VEHICLES_WITH_SR           = 0;
    static const VEHICLES_WITHOUT_SR        = 1;
    static const DUE_SOON_SR                = 2;
    
    static const OVER_DUE_SR                = 0;
    static const ZERO_TO_SEVEN_SR           = 1;
    static const EIGHT_TO_FIFTEEN_SR        = 2;
    static const FIFTEEN_PLUS_SR            = 3;

    static const VEHICLES_WITH_SR_TABLE      = 1;
    static const VEHICLES_WITHOUT_SR_TABLE   = 2;
    static const DUE_SOON_AND_OVERDUE_TABLE  = 3;
    

    //Renewal Reminder
    static const VEHICLES_WITH_RR           = 0;
    static const VEHICLES_WITHOUT_RR        = 1;
    static const DUE_SOON_RR                = 2;
    static const CRNT_MONTH_RR_EXPENSE      = 3;
    static const NEXT_MONTH_RR_EXPENSE      = 4;
    static const MANDATORY_NON_MANDATORY    = 5;

    static const OVER_DUE_RR                = 0;
    static const ZERO_TO_SEVEN_RR           = 1;
    static const EIGHT_TO_FIFTEEN_RR        = 2;
    static const FIFTEEN_PLUS_RR            = 3;

    static const VEHICLES_WITH_RR_TABLE      = 1;
    static const VEHICLES_WITHOUT_RR_TABLE   = 2;
    static const RR_DUE_SOON_AND_OVERDUE_TABLE  = 3;
    static const RR_MANDATORY_TABLE          = 4;

    //Issues
    static const CREATED_ISSUES             = 0;
    static const OPEN_ISSUES                = 1;
    static const IN_PROCESS_ISSUES          = 2;
    static const CLOSED_ISSUES              = 3;
    static const RESOLVED_ISSUES            = 4;
    static const REJECTED_ISSUES            = 5;
    static const OVERDUE_ISSUES             = 6;
    
    static const TOTAL_OVERDUE              = 0;
    static const ZERO_TO_SEVEN_ISSUES       = 1;
    static const EIGHT_TO_FIFTEEN_ISSUES    = 2;
    static const FIFTEEN_PLUS_ISSUES        = 3;

    //Service Entry
    static const CREATED_SERVICE_ENTRIES    = 0;
    static const OPEN_SERVICE_ENTRIES       = 1;
    static const IN_PROCESS_SERVICE_ENTRIES = 2;
    static const CLOSED_SERVICE_ENTRIES     = 3;
    
    static const SE_DUE_PAYMENT             = 0;
    static const All_OPEN_SERVICE_ENTRIES   = 1;
    static const ZERO_TO_SEVEN_S_ENTRIES    = 2;
    static const EIGHT_TO_FIFTEEN_S_ENTRIES = 3;
    static const FIFTEEN_PLUS_S_ENTRIES     = 4;
}