package com.epam.finalproject.repository.impl;

public interface SqlAliasConstants {
    String USER_ALIAS = " u.id as u_id , u.creation_date as u_creation_date , u.email as u_email ,"
            + " u.first_name as u_first_name , u.last_modified_by as u_last_modified_by ,"
            + " u.last_modified_date as u_last_modified_date , u.last_name as u_last_name , u.password as u_password ,"
            + " u.phone as u_phone , u.username as u_username ";
    String USER_MASTER_ALIAS = " m.id as m_id , m.creation_date as m_creation_date , m.email as m_email ," +
            " m.first_name as m_first_name , m.last_modified_by as m_last_modified_by ," +
            " m.last_modified_date as m_last_modified_date , m.last_name as m_last_name ," +
            " m.password as m_password , m.phone as m_phone , m.username as m_username ";
    String ROLES_ALIAS = " r.id as r_id , r.name as r_name ";
    String WALLETS_ALIAS = " w.id as w_id , w.money_amount as w_money_amount , w.name as w_name ," +
            " w.currency_id as w_currency_id , w.user_id as w_user_id ";
    String APP_CURRENCIES_ALIAS = " ac.id as ac_id , ac.code as ac_code ";

    String REPAIR_CATEGORY_ALIAS = " rc.id as rc_id , rc.key_name as rc_key_name ";

    String REPAIR_WORK_ALIAS = " rw.id as rw_id , rw.key_name as rw_key_name ";

    String RECEIPT_STATUS_FLOWS_ALIAS = " f.id as f_id , f.from_status_id as f_from_status_id ," +
            " f.role_id as f_role_id , f.to_status_id as f_to_status_id ";
    String RECEIPT_ALIAS = " r.id as r_id , r.created_by as r_created_by , r.creation_date as r_creation_date ," +
            " r.last_modified_by as r_last_modified_by , r.last_modified_date as r_last_modified_date ," +
            " r.note as r_note , r.total_price as r_total_price , r.category_id as r_category_id ," +
            " r.master_id as r_master_id , r.currency_id as r_currency_id ," +
            " r.receipt_status_id as r_receipt_status_id , r.user_id as r_user_id ";
    String RECEIPT_ITEMS_ALIAS = "  i.id as i_id , i.price_amount as i_price_amount ," +
            " i.receipt_id as i_receipt_id , i.repair_work_id as i_repair_work_id ";
}
