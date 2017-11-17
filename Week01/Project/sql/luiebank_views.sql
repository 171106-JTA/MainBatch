/**
 *  Author: Antony Lulciuc
 *  Defines views used throughout the system
 */
 
DROP VIEW BIGBOSS;

CREATE VIEW BIGBOSS AS
    SELECT * 
    FROM mb_user_info ui JOIN mb_user u
        ON (ui.user_id = u.id AND u.username = 'big.boss');