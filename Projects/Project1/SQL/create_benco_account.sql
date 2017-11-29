BEGIN
    dbms_output.put_line(TO_DATE(get_date(0), 'YYYY-MM-DD HH24:MI:SS'));
    create_user(
        'BENEFIT-COORDINATOR', 'big.boss', 'abc123',
        'Luie', 'Lulciuc', 'tonylulciuc@gmail.com',
        '2678938655','Luie St.','PA', 'PHL');

END;

select * from ear_user e join ear_user_info i
    on e.id = i.userid;