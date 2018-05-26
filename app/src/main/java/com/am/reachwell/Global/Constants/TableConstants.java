package com.am.reachwell.Global.Constants;

import android.provider.BaseColumns;

public interface TableConstants {

    interface TABLE_ASSET_CLASS extends BaseColumns {
        String TABLE_NAME = "asset_class";
        String CM_OU_ID = "cm_ou_id";
        String CM_ACCOUNT_HEAD_GUID = "cm_account_head_guid";
        String PARENT_ID = "parent_id";
        String ASSET_CLASS = "asset_Class";
        String ASSET_DESCRIPTION = "asset_description";
        String AM_ASSET_CLASS_CUSTOM1 = "am_asset_class_custom1";
        String AM_ASSET_CLASS_CUSTOM2 = "am_asset_class_custom2";
        String ACTIVE = "active";
        String CLASS_TYPE = "class_type";
    }

    interface TABLE_ASSET_STATUS extends BaseColumns {
        String TABLE_NAME = "asset_status";
        String CM_OU_ID = "cm_ou_id";
        String ASSET_STATUS = "asset_status";
        String ASSET_DESCRIPTION = "asset_description";
        String AM_ASSET_CLASS_CUSTOM1 = "am_asset_class_custom1";
        String AM_ASSET_CLASS_CUSTOM2 = "am_asset_class_custom2";
        String ACTIVE = "active";
        String OPERATIONAL_STATUS = "operational_status";
    }

    interface TABLE_ACCOUNT_HEAD extends BaseColumns {
        String TABLE_NAME = "account_head";
        String CM_OUGUID ="cm_ou_guid";
        String PARENT_GU_ID = "parent_guid";
        String ACCOUNT_HEAD_CODE = "account_head_code";
        String ACCOUNT_HEAD_NAME = "account_head_name";
        String ACCOUNT_HEAD_DESCRIPTION = "account_head_description";
        String AM_ACCOUNT_HEAD_CUSTOM1 = "am_account_head_custom1";
        String AM_ACCOUNT_HEAD_CUSTOM2 = "am_account_head_custom2";
        String ACTIVE = "active";
    }

    interface TABLE_CONTRACT_TYPE extends BaseColumns {
        String TABLE_NAME = "contract_type";
        String CM_OU_ID = "cm_ou_id";
        String ACTIVE = "active";
        String CONTRACT_TYPE = "contract_type";
        String DESCRIPTION = "description";
        String AM_CONTRACT_TYPE_CUSTOM1 = "am_contract_type_custom1";
        String AM_CONTRACT_TYPE_CUSTOM2 = "am_contract_type_custom2";
    }

    interface TABLE_ACQUISITION_METHOD extends BaseColumns {
        String TABLE_NAME = "acquisition_method";
        String ACTIVE = "active";
        String CM_OU_GUID = "CM_OU_GUID";
        String DESCRIPTION = "description";
        String ACQUISITION_METHOD = "acquisition_method";
    }

    interface TABLE_LOCATION extends BaseColumns {
        String TABLE_NAME = "account_head";
        String CM_OUGUID ="cm_ou_guid";
        String PARENT_GU_ID = "parent_guid";
        String CM_LOCATION_TYPE_GUID = "cm_location_type_guid";
        String LOCATION_CODE = "location_code";
        String LOCATION_NAME = "location_name";
        String LOCATION_DESCRIPTION = "location_description";
        String CM_LOCATION_CUSTOM1 = "cm_location_custom1";
        String BARCODE = "barcode";
        String AM_CONTRACT_LOC_GUID = "am_contract_loc_guid";
        String ACTIVE = "active";
    }

    interface TABLE_CREATE_ASSET extends BaseColumns {
        String TABLE_NAME = "assets";
        String ASSET_NAME ="asset_name";
        String ASSET_TAG = "asset_tag";
        String ASSET_DESC = "asset_desc";
        String ASSET_MAKE = "asset_make";
        String ASSET_MODEL = "asset_model";
        String ASSET_SL_NO = "asset_sl_no";
        String ASSET_CLASS = "asset_class";
        String ASSET_STATUS = "asset_status";
        String ASSET_USER = "asset_user";
        String ASSET_OWNER = "asset_owner   ";
        String ASSET_MAINTAINABLE = "asset_maintainable";
        String ASSET_SYNC = "asset_sync";
    }

    interface TABLE_CREATE_GRN extends BaseColumns {
        String TABLE_NAME = "grn";
        String GRN_DEXRIPTION ="grn_description";
        String ORDER_QTY = "order_qty";
        String UNIT = "unit";
        String RECIEVED_QTY = "recieved_qty";
        String GRN_REMARKS = "grn_remarks";
        String STATUS = "status";
        String ACTIVE = "active";
        String ITEM_STATUS = "item_status";
        String ASSET_SYNC = "asset_sync";
    }

    interface TABLE_LOCATION_TYPE extends BaseColumns {
        String TABLE_NAME = "location_type";
        String CM_OUGUID ="cm_ou_guid";
        String ACCOUNT_LOCATION_TYPE = "location_type";
        String DESCRIPTION = "description";
        String CM_LOCATIONTYPE_CUSTOM1 = "cm_location_type_custom1";
        String CM_LOCATIONTYPE_CUSTOM2 = "cm_location_type_custom2";
        String ACTIVE = "active";
    }

    interface TABLE_SUPPLIER extends BaseColumns {
        String TABLE_NAME = "supplier";
        String CM_OUGUID = "cm_ouguid";
        String PARENT_GUID = "parent_guid";
        String SUPPLIER_CODE = "supplier_code";
        String SUPPLIER_TYPE = "supplier_type";
        String SUPPLIER_NAME = "supplier_name";
        String ADDRESS = "address";
        String CM_CITY_GUID = "cm_city_guid";
        String CM_STATE_GUID = "cm_state_guid";
        String CM_COUNTRY_GUID = "cm_country_guid";
        String EMAIL_ID = "email_id";
        String PHONE_NUMBER = "phone_number";
    }

    interface TABLE_EMPLOYEE extends BaseColumns {
        String TABLE_NAME = "employee";
        String CM_OUGUID = "cm_ouguid";
        String CM_GROUPGUID = "cm_group_guid";
        String EMPLOYEE_CODE = "employee_code";
        String EMPLOYEE_TYPE = "employee_type";
        String FIRST_NAME = "first_name";
        String MIDDLE_NAME = "middle_name";
        String LAST_NAME = "last_name";
        String EMPLOYEE_STATUS = "employee_status";
    }

    interface TABLE_GOODS_RECIEVED extends BaseColumns{
        String TABLE_NAME = "goods_recieved";
        String CM_OUGUID = "cm_ouguid";
        String CONTACT_NUMBER = "contact_number";
        String CONTACT_DATE = "contact_date";
        String PO_NUMBER = "po_number";
        String PO_DATE = "po_date";
        String GRN_NUMBER = "grn_number";
        String GRN_DATE = "grn_date";
        String DC_NUMBER = "dc_number";
        String DC_DATE = "dc_date";
        String INVOICE_NUMBER = "invoice_number";
        String INVOICE_DATE = "invoice_date";
        String No_Of_Packets = "No_Of_Packets";
        String RECIEVED_BY = "recieved_by";
        String RECIEVED_DATE = "recieved_date";
        String SUPPLIED_BY = "supplied_by";
        String SUPPLIED_THROUGH = "supplied_through";
        String SYNC = "sync";
    }

    interface TABLE_GOODS_RECIEVEDD extends BaseColumns{
        String TABLE_NAME = "goods_recievedd";
        String ASSET_CLASS = "asset_class";
        String PM_GRNGUID = "pm_grn_guid";
        String ITEM_DESCRIPTION = "item_description";
        String ORDERED_QTY = "ordered_qty";
        String UNIT = "unit";
        String NOW_RECEIVING_QUANTITY = "now_receiving_quantity";
        String SYNC = "sync";
    }

    interface TABLE_ASSET_PICTURE extends BaseColumns{
        String TABLE_NAME = "asset_picture";
        String ASSET_ID = "asset_id";
        String FILE_NAME = "file_name";
        String FILE_PATH = "file_path";
    }
}
